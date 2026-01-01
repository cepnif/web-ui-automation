package hooks;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import config.ConfigReader;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Cucumber UI hooks responsible for Playwright lifecycle per scenario.
 *
 * <p>Creates a new browser context and page before each scenario, and closes all
 * resources after the scenario. Captures a screenshot and a Playwright trace on failure
 * (when enabled via configuration).</p>
 */
public class UiHooks {

    private static final Logger log = LoggerFactory.getLogger(UiHooks.class);

    /**
     * Initialises Playwright, Browser, BrowserContext and Page for the current scenario.
     *
     * @param scenario {@link Scenario} the executing scenario
     */
    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        ConfigReader.load();

        String browserName = ConfigReader.get("browser");
        boolean headless = ConfigReader.getBool("headless");
        int timeoutMs = ConfigReader.getInt("timeoutMs");
        int slowMoMs = ConfigReader.getInt("slowMoMs");

        log.info("START Scenario: {} (browser={}, headless={}, timeoutMs={}, slowMoMs={})",
                scenario.getName(), browserName, headless, timeoutMs, slowMoMs);

        Playwright playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMoMs);

        Browser browser = createBrowser(playwright, browserName, launchOptions);

        BrowserContext context = browser.newContext(new Browser.NewContextOptions());
        context.setDefaultTimeout(timeoutMs);

        Page page = context.newPage();

        DriverManager.set(playwright, browser, context, page);

        startTracingIfEnabled(context);
    }

    /**
     * Performs post-scenario actions: screenshot/trace on failure and resource cleanup.
     *
     * @param scenario {@link Scenario} the executed scenario
     */
    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        try {
            if (isFailureLike(scenario)) {
                captureScreenshotIfEnabled(scenario.getName());
                saveTraceIfEnabled(scenario.getName());
                log.error("FAILED Scenario: {} (status={})", scenario.getName(), scenario.getStatus());
            } else {
                stopTracingIfEnabled();
                log.info("END Scenario: {} (status={})", scenario.getName(), scenario.getStatus());
            }
        } finally {
            DriverManager.cleanup();
        }
    }

    /**
     * Creates a Playwright {@link Browser} instance for the requested browser name.
     *
     * @param playwright {@link Playwright} the Playwright instance
     * @param browserName {@link String} the browser name: chromium | firefox | webkit
     * @param launchOptions {@link BrowserType.LaunchOptions} the browser launch options
     * @return {@link Browser} the created browser
     */
    private Browser createBrowser(Playwright playwright,
                                  String browserName,
                                  BrowserType.LaunchOptions launchOptions) {

        String name = (browserName == null) ? "chromium" : browserName.toLowerCase();

        return switch (name) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> playwright.chromium().launch(launchOptions);
        };
    }

    /**
     * Starts Playwright tracing for the given {@link BrowserContext} if enabled by configuration.
     *
     * @param context {@link BrowserContext} the browser context to start tracing for
     */
    private void startTracingIfEnabled(BrowserContext context) {
        if (!ConfigReader.getBool("traceOnFailure")) {
            return;
        }

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        log.info("TRACE started");
    }

    /**
     * Saves the trace zip file if tracing is enabled.
     *
     * @param scenarioName {@link String} the scenario name used to build a safe file name
     */
    private void saveTraceIfEnabled(String scenarioName) {
        if (!ConfigReader.getBool("traceOnFailure")) {
            return;
        }

        String safe = scenarioName.replaceAll("[^a-zA-Z0-9-_]+", "_");
        Path path = Path.of("test-results", "traces", safe + ".zip");
        path.toFile().getParentFile().mkdirs();

        DriverManager.context().tracing().stop(new Tracing.StopOptions().setPath(path));

        log.error("TRACE saved: {}", path);
    }

    /**
     * Stops tracing without saving a file (used for non-failing scenarios).
     */
    private void stopTracingIfEnabled() {
        if (!ConfigReader.getBool("traceOnFailure")) {
            return;
        }

        try {
            DriverManager.context().tracing().stop();
            log.info("TRACE stopped (not saved)");
        } catch (Exception ignored) {
            // No action required
        }
    }

    /**
     * Captures a screenshot on failure if enabled by configuration.
     *
     * @param scenarioName {@link String} the scenario name used to build a safe file name
     */
    private void captureScreenshotIfEnabled(String scenarioName) {
        if (!ConfigReader.getBool("screenshotOnFailure")) {
            return;
        }

        Path path = buildScreenshotPath(scenarioName);

        DriverManager.page().screenshot(new Page.ScreenshotOptions()
                .setPath(path)
                .setFullPage(true));

        log.error("Screenshot saved: {}", path);
    }

    /**
     * Builds a safe file path for saving screenshots.
     *
     * @param scenarioName {@link String} the scenario name
     * @return {@link Path} the path where the screenshot should be written
     */
    private Path buildScreenshotPath(String scenarioName) {
        String safe = scenarioName.replaceAll("[^a-zA-Z0-9-_]+", "_");
        Path path = Path.of("test-results", "screenshots", safe + ".png");
        path.toFile().getParentFile().mkdirs();
        return path;
    }

    /**
     * Determines whether a scenario should be treated as a failure for artefact capture.
     *
     * <p>In CI/CD pipelines, undefined or pending steps must also be treated as failures,
     * so we capture screenshots and traces for troubleshooting.</p>
     *
     * @param scenario {@link Scenario} the executed scenario
     * @return {@code boolean} true if the scenario is failed, undefined, pending, or ambiguous
     */
    private boolean isFailureLike(Scenario scenario) {
        String status = String.valueOf(scenario.getStatus());
        return scenario.isFailed()
                || "UNDEFINED".equalsIgnoreCase(status)
                || "PENDING".equalsIgnoreCase(status)
                || "AMBIGUOUS".equalsIgnoreCase(status);
    }
}

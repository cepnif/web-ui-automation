package hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs each Gherkin step before and after execution.
 *
 * <p>This ensures end-to-end visibility of test progress during execution.</p>
 */
public class StepLoggingHooks {

    private static final Logger log = LoggerFactory.getLogger(StepLoggingHooks.class);

    /**
     * Logs the step text before the step executes.
     *
     * @param scenario {@link Scenario} the executing scenario (contains current step context)
     */
    @BeforeStep(order = 0)
    public void beforeStep(Scenario scenario) {
        log.info("STEP START: {}", scenario.getName());
        // Note: Cucumber's Scenario API does not expose the current step text directly.
        // We'll log the step text via a plugin later if you want exact step text in logs.
    }

    /**
     * Logs after each step completes.
     *
     * @param scenario {@link Scenario} the executing scenario
     */
    @AfterStep(order = 0)
    public void afterStep(Scenario scenario) {
        log.info("STEP END: {} (status={})", scenario.getName(), scenario.getStatus());
    }
}

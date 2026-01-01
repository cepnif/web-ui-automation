package helpers.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import driver.DriverManager;

/**
 * Centralised explicit waits for UI stability.
 */
public final class UiWaits {

    private UiWaits() {
        // Utility class
    }

    private static Page page() {
        Page page = DriverManager.page();
        if (page == null) {
            throw new IllegalStateException("Playwright Page is not initialised.");
        }
        return page;
    }

    /** Waits for DOM content loaded. */
    public static void waitForDomContentLoaded() {
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    /** Waits for network idle. */
    public static void waitForNetworkIdle() {
        page().waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Waits until a locator becomes visible.
     *
     * @param locator target locator
     */
    public static void waitForVisible(Locator locator) {
        locator.waitFor();
    }

    /**
     * Waits until the current URL path ends with the given suffix.
     *
     * @param suffix {@link String} URL suffix (e.g. "/sign-in")
     */
    public static void waitForUrlEndsWith(String suffix) {
        page().waitForFunction(
                "suffix => window.location.pathname.endsWith(suffix)",
                suffix
        );
    }


    /**
     * Waits until page title matches expected value.
     *
     * @param expectedTitle expected title
     */
    public static void waitForTitle(String expectedTitle) {
        page().waitForFunction(
                "expected => document.title === expected",
                expectedTitle
        );
    }
}

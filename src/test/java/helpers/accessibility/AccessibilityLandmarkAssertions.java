package helpers.accessibility;

import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccessibilityLandmarkAssertions {

    private AccessibilityLandmarkAssertions() {}

    private static Page page() {
        return DriverManager.page();
    }

    public static void assertHasMainLandmark() {
        assertThat(page().locator("main").count())
                .as("Main landmark must exist")
                .isGreaterThan(0);
    }

    public static void assertSingleMain() {
        assertThat(page().locator("main").count())
                .as("There must be exactly one <main>")
                .isEqualTo(1);
    }

    public static void assertSingleBanner() {
        assertThat(page().locator("header").count())
                .as("There must be exactly one banner (<header>)")
                .isEqualTo(1);
    }

    public static void assertAriaLandmarksPresent() {
        assertThat(page().locator("header, nav, main, footer").count())
                .as("ARIA landmarks must be present")
                .isGreaterThan(0);
    }
}

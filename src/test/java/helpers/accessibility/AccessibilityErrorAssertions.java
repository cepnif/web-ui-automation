package helpers.accessibility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Accessibility assertions related to GOV.UK error summaries.
 *
 * Covers WCAG:
 * - 3.3.1 Error Identification
 * - 3.3.3 Error Suggestion
 * - 4.1.3 Status Messages
 */
public final class AccessibilityErrorAssertions {

    private AccessibilityErrorAssertions() {
        // Utility class
    }

    private static Page page() {
        Page page = DriverManager.page();
        if (page == null) {
            throw new IllegalStateException("Playwright Page is not initialised.");
        }
        return page;
    }

    private static Locator errorSummary() {
        return page().locator(".govuk-error-summary").first();
    }

    /**
     * Asserts that an error summary is present and visible.
     */
    public static void assertErrorSummaryPresent() {
        Locator summary = errorSummary();

        assertThat(summary.count())
                .as("Error summary should be present")
                .isGreaterThan(0);

        assertThat(summary.isVisible())
                .as("Error summary should be visible")
                .isTrue();
    }

    /**
     * Asserts that the error summary has the expected ARIA role.
     *
     * @param expectedRole expected role (e.g. "alert")
     */
    public static void assertErrorSummaryRole(String expectedRole) {
        Locator summary = errorSummary();

        String role = summary.getAttribute("role");

        assertThat(role)
                .as("Error summary should have role '%s'", expectedRole)
                .isEqualToIgnoringCase(expectedRole);
    }

    /**
     * Asserts that the error summary contains links to invalid fields.
     *
     * <p>Checks that at least one anchor with a fragment identifier exists.</p>
     */
    public static void assertErrorSummaryLinksToInvalidFields() {
        Locator links = errorSummary().locator("a[href^=\"#\"]");

        assertThat(links.count())
                .as("Error summary should contain links to invalid fields")
                .isGreaterThan(0);
    }

    /**
     * Asserts that the error summary contains a link to a specific field.
     *
     * @param selector field selector (e.g. "#username", "#dob-day")
     */
    public static void assertErrorSummaryLinksTo(String selector) {
        Locator link = errorSummary().locator("a[href='" + selector + "']");

        assertThat(link.count())
                .as("Error summary should contain a link to %s", selector)
                .isGreaterThan(0);

        assertThat(link.first().isVisible())
                .as("Error summary link to %s should be visible", selector)
                .isTrue();
    }
}

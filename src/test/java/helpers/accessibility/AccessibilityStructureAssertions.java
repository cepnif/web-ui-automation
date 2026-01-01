package helpers.accessibility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccessibilityStructureAssertions {

    private AccessibilityStructureAssertions() {}

    private static Page page() {
        return DriverManager.page();
    }

    /** Page must have exactly one H1 */
    public static void assertSingleH1() {
        assertThat(page().locator("h1").count())
                .as("Page must contain exactly one <h1>")
                .isEqualTo(1);
    }

    /** H1 must be visible and readable */
    public static void assertH1IsVisibleAndReadable() {
        Locator h1 = page().locator("h1").first();
        assertThat(h1.isVisible())
                .as("Main heading (H1) must be visible")
                .isTrue();

        assertThat(h1.innerText())
                .as("Main heading must not be empty")
                .isNotBlank();
    }

    /** Page must have a title */
    public static void assertPageHasTitle() {
        assertThat(page().title())
                .as("Page title must be present")
                .isNotBlank();
    }

    /** Title must be descriptive */
    public static void assertTitleIsDescriptive() {
        String title = page().title();
        assertThat(title)
                .as("Page title should be descriptive")
                .doesNotContainIgnoringCase("page")
                .doesNotContainIgnoringCase("untitled");
    }

    public static void assertDateOfBirthGrouped() {
        int dobFieldsets = ((Number) page().evaluate("""
        () => {
            const fieldsets = Array.from(document.querySelectorAll('fieldset'));

            return fieldsets.filter(fs => {
                const legend = fs.querySelector('legend');
                if (!legend) return false;

                const text = legend.innerText.toLowerCase();
                return text.includes('date of birth') ||
                       text.includes('birth');
            }).length;
        }
    """)).intValue();

        assertThat(dobFieldsets)
                .as("Date of birth fields must be grouped using fieldset and legend")
                .isGreaterThan(0);
    }

}

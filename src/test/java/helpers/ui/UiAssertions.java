package helpers.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import driver.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Centralised UI assertions.
 */
public final class UiAssertions {

    private UiAssertions() {
        // Utility class
    }

    private static Page page() {
        Page page = DriverManager.page();
        if (page == null) {
            throw new IllegalStateException("Playwright Page is not initialised.");
        }
        return page;
    }

    public static void assertUrlEndsWith(String suffix) {
        assertThat(page().url()).endsWith(suffix);
    }

    public static void assertTitleIs(String expectedTitle) {
        assertThat(page().title()).isEqualTo(expectedTitle);
    }

    public static void assertVisible(Locator locator) {
        assertThat(locator.isVisible()).isTrue();
    }

    public static void assertTextContains(Locator locator, String expected) {
        assertThat(locator.innerText()).contains(expected);
    }

    public static void assertBodyContains(String text) {
        assertThat(page().locator("body").innerText()).contains(text);
    }

    public static void assertBodyDoesNotContain(String... fragments) {
        String body = page().locator("body").innerText();
        for (String fragment : fragments) {
            assertThat(body).doesNotContain(fragment);
        }
    }

    public static void assertControlVisible(String name) {
        boolean visible =
                page().getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName(name)).isVisible()
                        ||
                        page().getByRole(com.microsoft.playwright.options.AriaRole.LINK,
                                new Page.GetByRoleOptions().setName(name)).isVisible();

        assertThat(visible).isTrue();
    }

    public static void assertGovUkErrorSummaryContains(String expectedMessage) {
        Locator summary = UiLocators.errorSummary();
        assertThat(summary.innerText()).contains(expectedMessage);
    }

    /**
     * Asserts that authentication errors do not reveal which specific credential failed.
     *
     * <p>Allows generic messages like "username or password is incorrect"
     * but forbids messages that single out a specific field.</p>
     */
    public static void assertNoCredentialDisclosure() {
        String body = page().locator("body").innerText().toLowerCase();

        assertThat(body)
                .as("Authentication error must not disclose which credential is incorrect")
                .doesNotContain("username is incorrect")
                .doesNotContain("password is incorrect.")
                .doesNotContain("incorrect password")
                .doesNotContain("incorrect username")
                .doesNotContain("unknown username")
                .doesNotContain("invalid password")
                .doesNotContain("invalid username");

        // Positive safety check
        assertThat(body)
                .as("Generic authentication error must be present")
                .contains("username or password");
    }

    /**
     * Asserts that the GOV.UK error summary contains a link to a specific field.
     *
     * @param fieldId field anchor (e.g. "#email")
     */
    public static void assertErrorSummaryHasLinkTo(String fieldId) {
        Locator summary = page().locator(".govuk-error-summary");
        assertThat(summary.count())
                .as("Error summary should be present")
                .isGreaterThan(0);

        Locator link = summary.locator("a[href='" + fieldId + "']");
        assertThat(link.count())
                .as("Error summary should contain a link to %s", fieldId)
                .isGreaterThan(0);
    }
}

package helpers.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import config.ConfigReader;
import driver.DriverManager;

/**
 * Provides common user interaction actions on the UI.
 *
 * <p>This class must NOT contain assertions.</p>
 */
public final class UiActions {

    private UiActions() {
        // Utility class
    }

    /**
     * Returns the current scenario-scoped Playwright page.
     *
     * @return {@link Page} current page
     */
    public static Page page() {
        Page page = DriverManager.page();
        if (page == null) {
            throw new IllegalStateException("Playwright Page is not initialised.");
        }
        return page;
    }

    /**
     * Navigates to a relative path using baseUrl.
     *
     * @param path relative path (e.g. "/sign-in")
     */
    public static void goToPath(String path) {
        String baseUrl = ConfigReader.get("baseUrl");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Missing required configuration property: baseUrl");
        }
        page().navigate(baseUrl + path);
    }

    /**
     * Fills a form field.
     *
     * @param locator input locator
     * @param value value to type
     */
    public static void fill(Locator locator, String value) {
        locator.fill(value == null ? "" : value);
    }

    /**
     * Clicks a control (button or link) by accessible name.
     *
     * @param name visible control name
     */
    public static void click(String name) {
        Locator button = page().getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(name)
        );

        if (button.count() > 0 && button.first().isVisible()) {
            button.first().click();
            return;
        }

        Locator link = page().getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName(name)
        );

        if (link.count() > 0 && link.first().isVisible()) {
            link.first().click();
            return;
        }

        throw new AssertionError("No visible button or link found with name: " + name);
    }

    /**
     * Clicks a button explicitly.
     *
     * @param name button label
     */
    public static void clickButton(String name) {
        page().getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(name)
        ).first().click();
    }
}

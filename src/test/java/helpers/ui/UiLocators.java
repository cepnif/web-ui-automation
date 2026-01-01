package helpers.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

/**
 * Centralised GOV.UK UI locators.
 */
public final class UiLocators {

    private UiLocators() {
        // Utility class
    }

    /** Page body. */
    public static Locator body() {
        return UiActions.page().locator("body");
    }

    /** First H1 heading. */
    public static Locator h1() {
        return UiActions.page().locator("h1").first();
    }

    /** GOV.UK error summary container. */
    public static Locator errorSummary() {
        return UiActions.page().locator(".govuk-error-summary").first();
    }

    /** Error message inside GOV.UK error summary. */
    public static Locator errorSummaryMessage() {
        return errorSummary().locator(".govuk-error-summary__body");
    }

    /** Username input. */
    public static Locator username() {
        return inputById("username");
    }

    /** Password input. */
    public static Locator password() {
        return inputById("password");
    }

    /** Generic input by ID. */
    public static Locator inputById(String id) {
        return UiActions.page().locator("#" + id);
    }

    /** Button by accessible name. */
    public static Locator button(String name) {
        return UiActions.page().getByRole(
                AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName(name)
        );
    }

    /** Link by accessible name. */
    public static Locator link(String name) {
        return UiActions.page().getByRole(
                AriaRole.LINK,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName(name)
        );
    }

    /**
     * Email input field.
     *
     * @return {@link Locator} email input locator
     */
    public static Locator email() {
        return UiActions.page().locator("#email");
    }

    /**
     * Date of birthday input.
     */
    public static Locator dobDay() {
        return UiActions.page().locator("#dob-day");
    }

    /**
     * Date of birth - month input.
     */
    public static Locator dobMonth() {
        return UiActions.page().locator("#dob-month");
    }

    /**
     * Date of birth - year input.
     */
    public static Locator dobYear() {
        return UiActions.page().locator("#dob-year");
    }
}

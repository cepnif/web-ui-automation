package steps;

import config.ConfigReader;
import helpers.ui.UiActions;
import helpers.ui.UiAssertions;
import helpers.ui.UiLocators;
import helpers.ui.UiWaits;
import io.cucumber.java.en.Given;

/**
 * Common navigation and authentication steps shared across features.
 *
 * This class is intentionally separated from AccessibilitySteps
 * to avoid step duplication and regression.
 */
public class CommonNavigationSteps {


    /**
     * Verifies that the demo service is available.
     *
     * <p>The service is considered available if the Sign-in page
     * loads successfully.</p>
     */
    @Given("the Public Service Portal \\(Demo) is available")
    public void thePublicServicePortalDemoIsAvailable() {
        String baseUrl = ConfigReader.get("baseUrl");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Missing required configuration property: baseUrl");
        }

        UiActions.goToPath("/sign-in");
        UiWaits.waitForUrlEndsWith("/sign-in");
        UiAssertions.assertUrlEndsWith("/sign-in");
    }

    @Given("the user is signed in")
    public void theUserIsSignedIn() {
        UiActions.goToPath("/sign-in");
        UiActions.fill(UiLocators.username(), "citizen");
        UiActions.fill(UiLocators.password(), "password");
        UiActions.clickButton("Sign in");
    }

    @Given("the user is on the Signed in home page")
    public void theUserIsOnSignedInHomePage() {
        UiActions.goToPath("/signed-in");
    }

    /**
     * Ensures the user is on the Sign-in page.
     */
    @Given("the user is on the Sign in page")
    public void theUserIsOnTheSignInPage() {
        UiActions.goToPath("/sign-in");
        UiWaits.waitForUrlEndsWith("/sign-in");
        UiAssertions.assertUrlEndsWith("/sign-in");
    }

    @Given("the user is on the Forgotten username page")
    public void theUserIsOnTheForgottenUsernamePage() {
        UiActions.goToPath("/forgotten-username");
        UiWaits.waitForUrlEndsWith("/forgotten-username");
        UiAssertions.assertUrlEndsWith("/forgotten-username");
    }


    /**
     * Navigates the user to the Forgotten password page.
     */
    @Given("the user is on the Forgotten password page")
    public void theUserIsOnTheForgottenPasswordPage() {
        UiActions.goToPath("/forgotten-password");
        UiWaits.waitForUrlEndsWith("/forgotten-password");
        UiAssertions.assertUrlEndsWith("/forgotten-password");
    }
}

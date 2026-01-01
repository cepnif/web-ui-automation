package steps;

import helpers.ui.UiActions;
import helpers.ui.UiAssertions;
import helpers.ui.UiLocators;
import helpers.ui.UiWaits;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

/**
 * Step definitions for negative sign-in scenarios.
 *
 * <p>These steps validate that invalid credentials are rejected using a generic,
 * security-friendly error message and that the user remains on the Sign in page.</p>
 */
public class LoginInvalidCredentialsSteps {

    /**
     * Attempts to sign in using invalid credentials supplied via a data table.
     *
     * @param dataTable username and password values
     */
    @When("the user attempts to sign in with:")
    public void theUserAttemptsToSignInWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        UiActions.fill(UiLocators.username(), data.get("username"));
        UiActions.fill(UiLocators.password(), data.get("password"));
        UiActions.clickButton("Sign in");
    }

    /**
     * Verifies the user remains on the Sign-in page.
     */
    @Then("the user should remain on the Sign in page")
    public void theUserShouldRemainOnTheSignInPage() {
        UiWaits.waitForUrlEndsWith("/sign-in");
        UiAssertions.assertUrlEndsWith("/sign-in");
    }

    /**
     * Verifies the GOV.UK error summary contains the expected message.
     *
     * @param expectedMessage expected error message fragment
     */
    @Then("the page should display an error summary containing {string}")
    public void thePageShouldDisplayAnErrorSummaryContaining(String expectedMessage) {
        UiWaits.waitForVisible(UiLocators.errorSummary());
        UiAssertions.assertGovUkErrorSummaryContains(expectedMessage);
    }

    /**
     * Verifies the authentication error does not reveal which credential was incorrect.
     *
     * <p>The application must not disclose whether the username or password was invalid,
     * in line with security best practices.</p>
     */
    @Then("the authentication error should not reveal whether the username or password is incorrect")
    public void theAuthenticationErrorShouldNotRevealWhichFieldIsIncorrect() {
        UiAssertions.assertNoCredentialDisclosure();

        // Ensure the generic authentication message is present
        UiAssertions.assertBodyContains("Your username or password is incorrect");
    }

    @When("the user submits the sign in form with invalid credentials")
    public void submitSignInFormWithInvalidCredentials() {
        UiActions.fill(UiLocators.username(), "invalid-user");
        UiActions.fill(UiLocators.password(), "invalid-password");
        UiActions.clickButton("Sign in");
    }
}

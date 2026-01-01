package steps;

import helpers.ui.UiActions;
import helpers.ui.UiAssertions;
import helpers.ui.UiLocators;
import helpers.ui.UiWaits;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

/**
 * Step definitions for the Forgotten Password journey.
 *
 * <p>Steps express user intent only; UI interaction and assertions
 * are delegated to helper utility classes.</p>
 */
public class LoginResetPasswordSteps {


    /**
     * Submits the forgotten password form with provided data.
     *
     * @param dataTable username and date of birth fields
     */
    @When("the user submits the forgotten password form with:")
    public void theUserSubmitsTheForgottenPasswordFormWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        UiActions.fill(UiLocators.username(), data.get("username"));
        UiActions.fill(UiLocators.dobDay(), data.get("dobDay"));
        UiActions.fill(UiLocators.dobMonth(), data.get("dobMonth"));
        UiActions.fill(UiLocators.dobYear(), data.get("dobYear"));

        UiActions.clickButton("Send the link");
    }

    /**
     * Verifies the user remains on the Forgotten password page.
     */
    @Then("the user should remain on the Forgotten password page")
    public void theUserShouldRemainOnTheForgottenPasswordPage() {
        UiWaits.waitForUrlEndsWith("/forgotten-password");
        UiAssertions.assertUrlEndsWith("/forgotten-password");
    }

    @When("the user submits the forgotten password form with missing details")
    public void submitForgottenPasswordWithMissingDetails() {
        // Intentionally leave all fields empty
        UiActions.click("input[type='submit'], button[type='submit']");
    }
}

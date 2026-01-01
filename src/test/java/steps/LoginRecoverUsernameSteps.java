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
 * Step definitions for the Forgotten Username journey.
 *
 * <p>Focuses on user intent only; all UI logic is delegated to helpers.</p>
 */
public class LoginRecoverUsernameSteps {

    /**
     * Navigates the user to the Forgotten username page.
     */

    /**
     * Submits the forgotten username form with provided data.
     *
     * @param dataTable email address value
     */
    @When("the user submits the forgotten username form with:")
    public void theUserSubmitsTheForgottenUsernameFormWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        UiActions.fill(UiLocators.email(), data.get("email"));
        UiActions.clickButton("Send username");
    }

    /**
     * Verifies the user remains on the Forgotten username page.
     */
    @Then("the user should remain on the Forgotten username page")
    public void theUserShouldRemainOnTheForgottenUsernamePage() {
        UiWaits.waitForUrlEndsWith("/forgotten-username");
        UiAssertions.assertUrlEndsWith("/forgotten-username");
    }

    @When("the user submits the forgotten username form without an email address")
    public void submitForgottenUsernameWithoutEmail() {
        // Email intentionally left blank
        UiActions.clickButton("Send username");
    }
}

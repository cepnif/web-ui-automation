package steps;

import config.ConfigReader;
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
 * UI step definitions for the Public Service Portal (Demo).
 *
 * <p>This class expresses user intent only.
 * All UI interactions, waits and assertions are delegated
 * to dedicated helper utility classes.</p>
 */
public class LoginSuccessfulSteps {


    /**
     * Signs in using valid credentials provided via a data table.
     *
     * @param dataTable username and password values
     */
    @When("the user signs in with valid credentials:")
    public void theUserSignsInWithValidCredentials(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        UiActions.fill(UiLocators.username(), data.get("username"));
        UiActions.fill(UiLocators.password(), data.get("password"));
        UiActions.clickButton("Sign in");
    }

    /**
     * Verifies the user is redirected to the Signed in page.
     */
    @Then("the user should be redirected to the Signed in page")
    public void theUserShouldBeRedirectedToTheSignedInPage() {
        UiWaits.waitForUrlEndsWith("/signed-in");
        UiAssertions.assertUrlEndsWith("/signed-in");
    }

    /**
     * Verifies the user is redirected back to the Sign-in page.
     */
    @Then("the user should be redirected to the Sign in page")
    public void theUserShouldBeRedirectedToTheSignInPage() {
        UiWaits.waitForUrlEndsWith("/sign-in");
        UiAssertions.assertUrlEndsWith("/sign-in");
    }

    /**
     * Verifies the browser URL ends with the expected value.
     *
     * @param expectedSuffix expected URL suffix
     */
    @Then("the URL should end with {string}")
    public void theUrlShouldEndWith(String expectedSuffix) {
        UiAssertions.assertUrlEndsWith(expectedSuffix);
    }

    /**
     * Verifies the page title.
     *
     * @param expectedTitle expected page title
     */
    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        UiWaits.waitForTitle(expectedTitle);
        UiAssertions.assertTitleIs(expectedTitle);
    }

    /**
     * Verifies the main page heading (H1).
     *
     * @param expectedHeading expected heading text
     */
    @Then("the page heading should be {string}")
    public void thePageHeadingShouldBe(String expectedHeading) {
        UiAssertions.assertTextContains(UiLocators.h1(), expectedHeading);
    }

    /**
     * Verifies a visible message is displayed on the page.
     *
     * @param expectedMessage expected message text
     */
    @Then("the page should display the message {string}")
    public void thePageShouldDisplayTheMessage(String expectedMessage) {
        UiAssertions.assertBodyContains(expectedMessage);
    }

    /**
     * Verifies a control (link or button) is visible.
     *
     * @param controlName visible control label
     */
    @Then("the {string} control should be visible")
    public void theControlShouldBeVisible(String controlName) {
        UiAssertions.assertControlVisible(controlName);
    }

    /**
     * Clicks a control (link or button) by its visible label.
     *
     * @param controlName visible control label
     */
    @When("the user clicks {string}")
    public void theUserClicks(String controlName) {
        UiActions.click(controlName);
    }

    /**
     * Verifies the signed-out confirmation message.
     *
     * @param dataTable title and message values
     */
    @Then("a signed out confirmation should be displayed with:")
    public void aSignedOutConfirmationShouldBeDisplayedWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        UiAssertions.assertBodyContains(data.get("title"));
        UiAssertions.assertBodyContains(data.get("message"));
    }
}

package steps;

import helpers.accessibility.*;
import io.cucumber.java.en.Then;

/**
 * Accessibility-specific step definitions.
 *
 * IMPORTANT:
 * - This class MUST contain ONLY accessibility assertions.
 * - NO navigation, NO login, NO page-opening steps.
 * - Regression-safe: existing steps are preserved as-is.
 */
public class AccessibilitySteps {

    /* ================= PAGE METADATA ================= */

    @Then("the page should have a unique and descriptive title")
    public void pageHasDescriptiveTitle() {
        AccessibilityStructureAssertions.assertPageHasTitle();
        AccessibilityStructureAssertions.assertTitleIsDescriptive();
    }

    @Then("the page should contain exactly one main heading")
    public void pageHasSingleH1() {
        AccessibilityStructureAssertions.assertSingleH1();
    }

    @Then("the main heading should be accessible")
    public void mainHeadingAccessible() {
        AccessibilityStructureAssertions.assertH1IsVisibleAndReadable();
    }

    /* ================= FORMS & STRUCTURE ================= */

    @Then("all form fields should have associated labels")
    public void formFieldsHaveLabels() {
        AccessibilityAriaAssertions.assertAllFormFieldsHaveLabels();
    }

    @Then("the date of birth fields should be grouped accessibly")
    public void dobGroupedAccessibly() {
        AccessibilityStructureAssertions.assertDateOfBirthGrouped();
    }

    /* ================= LANDMARKS ================= */

    @Then("all navigation landmarks should be accessible")
    public void navigationLandmarksAccessible() {
        AccessibilityLandmarkAssertions.assertSingleBanner();
        AccessibilityLandmarkAssertions.assertSingleMain();
        AccessibilityLandmarkAssertions.assertHasMainLandmark();
    }

    @Then("the page should define appropriate ARIA landmarks")
    public void ariaLandmarksPresent() {
        AccessibilityLandmarkAssertions.assertAriaLandmarksPresent();
    }

    /* ================= INTERACTION ================= */

    @Then("all interactive elements should have accessible names")
    public void interactiveElementsHaveNames() {
        AccessibilityAriaAssertions.assertAllInteractiveElementsHaveAccessibleNames();
    }

    @Then("all interactive elements should be reachable using the keyboard")
    public void keyboardReachable() {
        AccessibilityKeyboardAssertions.assertFocusableElementsPresent();
    }

    @Then("the focus indicator should be visible when navigating with the keyboard")
    public void focusVisible() {
        AccessibilityKeyboardAssertions.assertFocusVisible();
    }

    /* ================= SCREEN READER ================= */

    @Then("important content sections should be announced correctly")
    public void sectionsAnnounced() {
        AccessibilityAriaAssertions.assertSectionsAreAnnounced();
    }

    @Then("decorative elements should be hidden from assistive technologies")
    public void decorativeHidden() {
        AccessibilityAriaAssertions.assertDecorativeImagesAreHidden();
    }

    /* ================= ERROR SUMMARY (LOGIN / FORMS) ================= */

    @Then("an accessible error summary should be displayed")
    public void accessibleErrorSummaryDisplayed() {
        AccessibilityErrorAssertions.assertErrorSummaryPresent();
    }

    @Then("the error summary should have role {string}")
    public void errorSummaryRole(String role) {
        AccessibilityErrorAssertions.assertErrorSummaryRole(role);
    }

    @Then("the error summary should include links to the invalid fields")
    public void errorSummaryLinksInvalidFields() {
        AccessibilityErrorAssertions.assertErrorSummaryLinksToInvalidFields();
    }

    @Then("the error summary should include a link to {string}")
    public void errorSummaryIncludesLink(String selector) {
        AccessibilityErrorAssertions.assertErrorSummaryLinksTo(selector);
    }

    @Then("the email input field should have an accessible label")
    public void emailFieldHasAccessibleLabel() {
        AccessibilityAriaAssertions.assertEmailFieldHasAccessibleLabel();
    }
}

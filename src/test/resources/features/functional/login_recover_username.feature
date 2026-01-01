@ui @functional @regression
Feature: Recover username

  As a user of the Public Service Portal (Demo)
  I want to recover my username
  So that I can sign in to my account again

  Description:
  This feature verifies the forgotten username journey for the Public Service Portal (Demo).
  It checks that users can request their username by providing an email address and that
  validation errors are shown in an accessible way when required information is missing.

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Forgotten username page

  @positive
  Scenario: User requests their username successfully
    When the user submits the forgotten username form with:
      | email | citizen@example.com |
    Then the user should be redirected to the Sign in page
    And the URL should end with "/sign-in"
    And the page title should be "Sign in - Public Service Portal (Demo)"
    And the page heading should be "Sign in to your account"

  @negative
  Scenario Outline: Missing email shows validation errors
    When the user submits the forgotten username form with:
      | email | <email> |
    Then the user should remain on the Forgotten username page
    And the URL should end with "/forgotten-username"
    And the page title should be "Error: Forgotten username - Public Service Portal (Demo)"
    And the page should display an error summary containing "Enter your email address"
    And the error summary should include a link to "#email"

    Examples:
      | email |
      |       |


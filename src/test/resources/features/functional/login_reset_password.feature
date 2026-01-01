@ui @functional @regression
Feature: Reset password

  As a user of the Public Service Portal (Demo)
  I want to reset my password
  So that I can regain access to my account securely

  Description:
  This feature verifies the forgotten password journey for the Public Service Portal (Demo).
  It checks that users can request a password reset link by providing their username and
  date of birth, and that accessible validation errors are shown when inputs are missing.

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Forgotten password page

  @positive
  Scenario: User requests a password reset link successfully
    When the user submits the forgotten password form with:
      | username | citizen |
      | dobDay   | 18      |
      | dobMonth | 9       |
      | dobYear  | 2012    |
    Then the user should be redirected to the Sign in page
    And the URL should end with "/sign-in"
    And the page title should be "Sign in - Public Service Portal (Demo)"
    And the page heading should be "Sign in to your account"

  @negative
  Scenario Outline: Missing required fields shows validation errors
    When the user submits the forgotten password form with:
      | username | <username> |
      | dobDay   | <dobDay>   |
      | dobMonth | <dobMonth> |
      | dobYear  | <dobYear>  |
    Then the user should remain on the Forgotten password page
    And the URL should end with "/forgotten-password"
    And the page title should be "Error: Forgotten password - Public Service Portal (Demo)"
    And the page should display an error summary containing "<errorMessage>"
    And the error summary should include a link to "<fieldId>"

    Examples:
      | username | dobDay | dobMonth | dobYear | errorMessage            | fieldId    |
      |          | 18     | 9        | 2012    | Enter your username     | #username  |
      | citizen  |        |          |         | Enter your date of birth| #dob-day   |
      | citizen  | 18     |          | 2012    | Enter your date of birth| #dob-day   |
      | citizen  | 18     | 9        |         | Enter your date of birth| #dob-day   |

  @negative
  Scenario: Missing username and date of birth shows multiple errors
    When the user submits the forgotten password form with:
      | username |       |
      | dobDay   |       |
      | dobMonth |       |
      | dobYear  |       |
    Then the user should remain on the Forgotten password page
    And the URL should end with "/forgotten-password"
    And the page should display an error summary containing "Enter your username"
    And the page should display an error summary containing "Enter your date of birth"
    And the error summary should include a link to "#username"
    And the error summary should include a link to "#dob-day"

@ui @functional @negative @regression
Feature: Sign in is rejected when credentials are invalid

  As a user of the Public Service Portal (Demo)
  I want to be told when my credentials are incorrect
  So that I can try again without the service revealing sensitive details

  Description:
  This feature verifies that the sign-in journey rejects invalid credentials using a generic,
  security-friendly error message. It ensures the user remains on the Sign in page and
  the correct error summary is shown.

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Sign in page

  Scenario Outline: Invalid credentials show a generic authentication error
    When the user attempts to sign in with:
      | username | <username> |
      | password | <password> |
    Then the user should remain on the Sign in page
    And the URL should end with "/sign-in"
    And the page should display an error summary containing "Your username or password is incorrect"
    And the authentication error should not reveal whether the username or password is incorrect

    Examples:
      | username       | password       |
      | wrongUser      | Password123!   |
      | citizen        | wrongPassword  |
      | wrongUser      | wrongPassword  |
      | CITIZEN        | Password123!   |
      | citizen        | Password123    |

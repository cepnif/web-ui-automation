@ui @functional @positive @smoke @regression
Feature: Successful sign in and then sign out

  As a user of the Public Service Portal (Demo)
  I want to sign in and sign out securely
  So that I can access my account and end my session safely

  Description:
  This feature validates that a user can sign in with valid credentials, land on the Signed in page,
  and then sign out successfully to return to the Sign in page.
  It asserts URL routing, page title, key on-screen messages, and the presence of the Sign out control.

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Sign in page

  Scenario: User signs in successfully and signs out
    When the user signs in with valid credentials:
      | username | citizen       |
      | password | Password123!  |
    Then the user should be redirected to the Signed in page
    And the URL should end with "/signed-in"
    And the page title should be "Signed in - Public Service Portal (Demo)"
    And the page heading should be "You are signed in"
    And the page should display the message "Welcome to the Public Service Portal (Demo)."
    And the "Sign out" control should be visible

    When the user clicks "Sign out"
    Then the user should be redirected to the Sign in page
    And the URL should end with "/sign-in"
    And the page title should be "Sign in - Public Service Portal (Demo)"
    And a signed out confirmation should be displayed with:
      | title   | Signed out                                               |
      | message | You have signed out of the Public Service Portal (Demo). |

@ui @accessibility @sign-in
Feature: Sign in page accessibility

  As a user relying on assistive technologies
  I want the Sign in page to be accessible
  So that I can sign in without barriers

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Sign in page

  Scenario: Sign in page meets basic accessibility standards
    Then the page should have a unique and descriptive title
    And the page should contain exactly one main heading
    And the main heading should be accessible
    And all form fields should have associated labels
    And all interactive elements should be reachable using the keyboard
    And the focus indicator should be visible when navigating with the keyboard

  Scenario: Error summary is accessible when sign in fails
    When the user submits the sign in form with invalid credentials
    Then an accessible error summary should be displayed
    And the error summary should have role "alert"
    And the error summary should include links to the invalid fields

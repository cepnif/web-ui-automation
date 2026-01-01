@ui @accessibility @forgotten-password
Feature: Forgotten password page accessibility

  As a user using assistive technologies
  I want the Forgotten password page to be accessible
  So that I can reset my password without difficulty

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Forgotten password page

  Scenario: Forgotten password page meets accessibility standards
    Then the page should have a unique and descriptive title
    And the page should contain exactly one main heading
    And all form fields should have associated labels
    And the date of birth fields should be grouped accessibly
    And all interactive elements should be reachable using the keyboard

  Scenario: Accessible error handling for missing information
    When the user submits the forgotten password form with missing details
    Then an accessible error summary should be displayed
    And the error summary should have role "alert"
    And the error summary should include links to the invalid fields

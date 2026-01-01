@ui @accessibility @forgotten-username
Feature: Forgotten username page accessibility

  As a user who has forgotten their username
  I want the Forgotten username page to be accessible
  So that I can recover my account details independently

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is on the Forgotten username page

  Scenario: Forgotten username page meets accessibility standards
    Then the page should have a unique and descriptive title
    And the page should contain exactly one main heading
    And the email input field should have an accessible label
    And all interactive elements should be reachable using the keyboard

  Scenario: Accessible validation errors for missing email
    When the user submits the forgotten username form without an email address
    Then an accessible error summary should be displayed
    And the error summary should have role "alert"
    And the error summary should include a link to "#email"


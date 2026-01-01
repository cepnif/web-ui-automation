@ui @accessibility @signed-in
Feature: Signed in home page accessibility

  As a signed-in user using assistive technologies
  I want the signed in home page to be accessible
  So that I can navigate available services independently

  Background:
    Given the Public Service Portal (Demo) is available
    And the user is signed in
    And the user is on the Signed in home page

  Scenario: Signed in home page meets basic accessibility standards
    Then the page should have a unique and descriptive title
    And the page should contain exactly one main heading
    And the main heading should be accessible
    And all navigation landmarks should be accessible
    And all interactive elements should have accessible names
    And all interactive elements should be reachable using the keyboard
    And the focus indicator should be visible when navigating with the keyboard

  Scenario: Page content is accessible to screen readers
    Then the page should define appropriate ARIA landmarks
    And important content sections should be announced correctly
    And decorative elements should be hidden from assistive technologies

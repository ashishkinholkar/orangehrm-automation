@ashish @regression @pim
Feature: PIM - Employee Management
  As an HR administrator
  I want to create, search, update and remove employees
  So that the employee directory stays accurate

  Background:
    Given the admin user is logged in
    And the admin navigates to the "PIM" module

  @smoke
  Scenario: Create a new employee and verify it is searchable
    When the admin adds a new employee with a generated name
    And the admin searches for the created employee
    Then the employee should appear in the search results

  Scenario: Create an employee with a profile image
    When the admin adds a new employee with a generated name
    And the admin uploads the profile image "profile.png"
    And the admin searches for the created employee
    Then the employee should appear in the search results

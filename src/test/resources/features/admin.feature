@ashishtest @regression @admin
Feature: Admin - User Management
  As a system administrator
  I want to create and search system users
  So that access is provisioned to the right people

  Background:
    Given the admin user is logged in
    And the admin navigates to the "Admin" module

  @smoke
  Scenario: Create a system user and verify it can be found
    When the admin creates a user with role "ESS" and status "Enabled" for employee "a"
    And the admin searches for the created user
    Then the user should be found in the system records

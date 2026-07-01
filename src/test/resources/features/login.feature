@ashish @regression @login
Feature: OrangeHRM Login and Dashboard
  As an HR administrator
  I want to securely log into OrangeHRM
  So that I can access the dashboard and manage HR operations

  @smoke
  Scenario: Successful login with valid admin credentials
    Given the user is on the OrangeHRM login page
    When the user logs in with valid admin credentials
    Then the dashboard should be displayed
    And the user profile menu and dashboard widgets should be visible

  Scenario: Login fails with invalid credentials
    Given the user is on the OrangeHRM login page
    When the user logs in with username "Admin" and password "wrongPass123"
    Then an invalid credentials error should be shown

  Scenario Outline: Login validation for empty fields
    Given the user is on the OrangeHRM login page
    When the user logs in with username "<username>" and password "<password>"
    Then a required field validation should be shown

    Examples:
      | username | password |
      |          |          |
      | Admin    |          |

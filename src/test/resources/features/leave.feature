@ashish @regression @leave
Feature: Leave Management
  As an employee and administrator
  I want to apply for and process leave requests
  So that absences are tracked and approved correctly

  Background:
    Given the admin user is logged in
    And the admin navigates to the "Leave" module

  Scenario: Search leave records
    When the admin searches the leave records
    Then at least one leave record should be present

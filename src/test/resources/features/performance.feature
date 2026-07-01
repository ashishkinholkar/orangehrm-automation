@ashish @regression @performance
Feature: Performance - KPI Management
  As an HR administrator
  I want to define KPIs for job titles
  So that employee performance can be evaluated consistently

  Background:
    Given the admin user is logged in
    And the admin navigates to the "Performance" module

  Scenario: Create a KPI for a job title
    When the admin creates a KPI "Quality of Work" for job title "QA Engineer" with rating "1" to "5"
    Then the KPI list should contain at least one entry

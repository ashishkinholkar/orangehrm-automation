@regression @recruitment
Feature: Recruitment - Candidate Management
  As a recruiter
  I want to add candidates with their resumes
  So that I can track applicants through the hiring pipeline

  Background:
    Given the admin user is logged in
    And the admin navigates to the "Recruitment" module

  Scenario: Add a candidate with a resume attachment
    When the admin adds a candidate with resume "resume.pdf"
    Then the candidate list should not be empty

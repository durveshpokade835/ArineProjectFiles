@PharmacistPortal @LogActionNote
Feature: Report-Specific Algorithms and Patients Query Service

      #PROD-19012  FD-8921
  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                           | password                                           | message |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | success |

  @Regression
  Scenario Outline:Verify_last_attempted_by_field_updates_correctly_without_stacking
    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And Search patient: "<PatientID1>"
    And Verify Patient Name: "<patientName>"
    And Click on tab: "Story"
    And Delete task "<notes>" if present
    And Click on tab button: "New Task"
    And Verify create new task popup
    Then Create new Task with below information:
      | Assigned to | Type          | Task                  | Due Date | NOTES   |
      | Pharmacist  | Review Upload | + MTM-Medication List | Urgent   | <notes> |
    And Verify newly created task for "<notes>"
    Then Click on Tasks Tab
    Then Click on tasks sub tab: "All"
    Then Sort column "Due Date" in "Descending" order
    And Verify newly created task on tasks tab for "<notes>"
    Then Click on newly created task for "<notes>" in tasks tab
    And Click on task action button: "Log Action"
    And Verify create new log action popup
    Then Create new log action with below information:
      | ACTION | TYPE     | STAKEHOLDER | OUTCOME                      | STEP(S) PERFORMED     | NOTE    |
      | Call   | Outbound | Patient     | Answered, Topic(s) Discussed | + MTM-Address Updated | <notes> |
    Then Click on tasks sub tab: "Urgent"
    And Click on tasks sub tab: "All"
    Then Click on newly created task for "<notes>" in tasks tab
    And Verify last attempted by information updated accurately and verify "<lastAttemptedByAndDate>"
    Then Click on Patient Tab
    And Click on tab: "Story"
    And Delete task "<notes>" if present
    Then Delete all log actions if present in patient timeline table
    Examples:
      | Organization                      | PatientID1                           | patientName                   | type          | task                  | notes                      | notes2                      | lastAttemptedByAndDate                 |
      | Admin Portal Test Patient Org DEV | 1b3de805-5d9a-4527-9223-a209b2d2472a | Test Ph-Arine2 Test PH-Arine2 | Review Upload | + MTM-Medication List | Task created by automation | Task created by automation2 | Last attempted by DevAdmin DevAdmin on |

  @Regression
  Scenario Outline:Verify_last_attempted_by_field_updates_correctly_without_stacking_through_Patients_tab
    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And Search patient: "<PatientID1>"
    And Verify Patient Name: "<patientName>"
    And Click on tab: "Story"
    And Delete task "<notes>" if present
    And Click on tab button: "New Task"
    And Verify create new task popup
    Then Create new Task with below information:
      | Assigned to | Type          | Task                  | Due Date | NOTES   |
      | Pharmacist  | Review Upload | + MTM-Medication List | Urgent   | <notes> |
    And Verify newly created task for "<notes>"
    Then Click on newly created task for "<notes>"
    And Click on task action button: "Log Action"
    And Verify create new log action popup
    Then Create new log action with below information:
      | ACTION | TYPE     | STAKEHOLDER | OUTCOME                      | STEP(S) PERFORMED     | NOTE    |
      | Call   | Outbound | Patient     | Answered, Topic(s) Discussed | + MTM-Address Updated | <notes> |
    And Click on tab: "Profile and Action Plan"
    Then Wait to page load
    And Click on tab: "Story"
    And Verify newly created task on tasks table is not present for "<notes>"
    Then Delete all log actions if present in patient timeline table
    Examples:
      | Organization                      | PatientID1                           | patientName                   | notes                      |
      | Admin Portal Test Patient Org DEV | 1b3de805-5d9a-4527-9223-a209b2d2472a | Test Ph-Arine2 Test PH-Arine2 | Task created by automation |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
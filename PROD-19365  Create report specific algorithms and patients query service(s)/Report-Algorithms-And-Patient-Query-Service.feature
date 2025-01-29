@PharmacistPortal @PatientQueryService
Feature: Report-Specific Algorithms and Patients Query Service

      #PROD-19365
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
  Scenario Outline: Verify_Algorithms_and_Patient_Recommendations_in_the_log_action_modal_from_Report_History
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
    Then Select records on Reported DRPS Table values
      | <patient1> |
    And User extracts patient "Algorithm" column data for following patient
      | <patient1> |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue2>" and click on "Apply" button
    Then Select records on Reported DRPS Table values
      | <patient2> |
    And User extracts patient "Algorithm" column data for following patient
      | <patient2> |
    And Verify the FaxProvider button enabled or not
    Then Click on button: "Fax Provider"
    Then User select DRP option "<whichDRPOption>" for follow up fax
    Then User select type of report: "<WhatTypeOfReportOption>"
    Then Enter fax number "<FaxNumber>" in input and select that fax number
    And Click on button "Preview and Send" which is in campaigns button
    Then Verify report viewer
    And Click on button "Send" which is in campaigns button
    Then Wait to page load
    And User click on "Show all" button to show all "Report History" table hidden columns
    Then Verify the sent column status of report history table
      | Current Date |
    Then Verify recently faxed log date will be displayed last in the report history table
    Then Verify recently faxed log date will be displayed last in the report history table and "<ColumnName>" column contains "<ColumnValue>" value
    And Click on first records pdf icon from report history table
    Then Verify report viewer
    Then Verify report contains: "<string1>"
    Then Verify report contains: "<string2>"
    And Click on button "Close" which is in campaigns button
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action   | Type   | Stakeholder   | Outcome   |
      | <Action> | <Type> | <Stakeholder> | <Outcome> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Verify patients Algorithm column data for respective patient in patient Recommendation table
    And selects All patient recommendations from the Report Discussion Selector table
    And clicks on the "Log Action" button and verify PopUp "<string>"
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    And Verify newly created practitioners log action "<Action>" is generated on current date and time
    Then Verify newly created log action for "<Type>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<Stakeholder>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<Outcome>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action

    Examples:
      | Organization                      | NPI        | CampaignDate    | searchFilterName   | filterValue1 | patient1               | filterValue2 | patient2              | whichDRPOption | WhatTypeOfReportOption | FaxNumber    | ColumnName | ColumnValue | string                                                                   | string1                | string2               | Action | Type     | Stakeholder     | Outcome  |
      | Admin Portal Test Patient Org DEV | 2323232323 | Test Campaign 1 | Patient First Name | TestMargaret | TestMargaret TestSmith | TestRussell  | TestRussell TestHeath | Selected DRPs  | Multi-patient Report   | 989-998-9889 | Patients   | 2           | Submitting this action will create story logs for the selected patients. | TESTMARGARET TESTSMITH | TESTRUSSELL TESTHEATH | Call   | Outbound | Medical Records | Answered |

#  @Regression
#  Scenario Outline: Verify_unique_DRPs_displayed_in_Algorithms_table_from_Report_History
#    Examples:
#
#  @Regression
#  Scenario Outline: Verify_Patient_Recommendations_table_for_selected_DRPs_from_Report_History
#    Examples:
#
#  @Regression
#  Scenario Outline: Verify_log_action_modal_displays_Algorithms_and_Patient_Recommendations_from_Campaigns_tab
#    Examples:
#
#  @Regression
#  Scenario Outline: Verify_unique_DRPs_displayed_in_Algorithms_table_from_Campaigns_tab
#    Examples:
#
#  @Regression
#  Scenario Outline: Verify_Patient_Recommendations_table_for_selected_DRPs_from_Campaigns_tab
#    Examples:
#
#
#  Scenario Outline:Test DEMO
#    Given User select organization: "<Organization>"
#    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    Then Verify the User able select the campaigns value as: "<CampaignDate>"
#    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
#    Then Select records on Reported DRPS Table values
#      | <Practitioner1> |
#    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    And Click on button "Fax Provider" which is in campaigns button
#    Then Select resend most recent reports option in send fax popup
#    Then Enter fax number "<FaxNumber>" in input and select that fax number
#    And Click on button "Preview and Send" which is in campaigns button
#    Then Verify report viewer
#    And Click on button "Send" which is in campaigns button
#    And Search patient: "<searchString>"
#    Given User select organization: "<Organization>"
#    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    Then Verify the User able select the campaigns value as: "<CampaignDate>"
#    Then User provider last log activity date will be displayed last in the timeline table
#    And Click on button "Story" which is in campaigns button
#    Then Verify newly created log action for "<string>" in story tab for prescriber analytics feature
#    And Verify newly created practitioners log action "<string>" is generated on current date and time
#    Then Delete newly created practitioner timeline log action
#    And Click on button "Follow Up" which is in campaigns button
#    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    And User select all reported DRPs
#    And User deselect all reported DRPs
#    Then Delete all log actions if present in practitioner timeline table
#    Examples:
#      | username                                           | password                                           | message | searchString                         | Organization                      | CampaignDate    | NPI        | string    | searchFilterName  | filterValue1 | Practitioner1   | FaxNumber    |
#      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | success | 5d83de75-8ac8-47d3-bf00-830bd1da7029 | Admin Portal Test Patient Org DEV | Test Campaign 1 | 1750393690 | Submitted | Patient Last Name | Lucy2802     | Miller Lucy2802 | 989-998-9889 |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
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

  @Regression @Smoke
  Scenario Outline: verify_drps_visibility_in_reports_as_per_implementation_status_criteria
    Given Click on Patient Tab
    When User select organization: "<Organization>"
    Then Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Verify user able to enable column filter modes and search "<Patient1>" in "<columnName>" column filter
    Then User select all reported DRPs
    Then User deselect all reported DRPs
    Then Select records on Reported DRPS Table values
#      | Miller Lucy20091 |
      | TestAmanda TestBerger |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus1>"
    Then User select all reported DRPs
    Then User deselect all reported DRPs
    Then Verify user able to enable column filter modes and search "<Patient2>" in "<columnName>" column filter
    Then Select records on Reported DRPS Table values
      | TestRobert TestCabrera |
#      | Miller Lucy2802  |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus2>"
    Then Wait to page load
    And Click on button "Fax Provider" which is in campaigns button
    Then Verify alert icon displays for all open drps option and its tooltip message is: "<AlertMessage>"
    Then User select DRP option "<whichDRPOption>" for follow up fax
    Then User select type of report: "<WhatTypeOfReportOption>"
    Then Enter fax number "<FaxNumber>" in input and select that fax number
    And Click on button "Preview and Send" which is in campaigns button
    Then Verify report viewer
    Then Verify report not contains: "<Patient1>"
    Then Verify report contains: "<Patient2>"
    And Click on button "Cancel" which is in campaigns button
    Then Verify user able to clear following column filters inboxes
      | <columnName> |
    Examples:
      | Organization                      | NPI        | CampaignDate    | ImplementationStatus1 | ImplementationStatus2 | columnName | whichDRPOption | WhatTypeOfReportOption | SearchPatient | Patient1              | Patient2               | FaxNumber  | AlertMessage                                                                                                |
#      | Admin Portal Test Patient Org DEV | 1750393690 | Test Campaign 1 | Will Not Implement    | Unknown               | Name       | Selected DRPs  | Multi-patient Report   | Miller Lucy   | MILLERLUCY20091 | MILLERLUCY2802 | 5012270710 | Implemented, Considering Implementation, No Longer Applicable, and Will Not Implement DRPs will be excluded |
      | Admin Portal Test Patient Org DEV | 2323232323 | Test Campaign 3 | Will Not Implement    | Unknown               | Name       | Selected DRPs  | Multi-patient Report   | Miller Lucy   | TESTAMANDA TESTBERGER | TESTROBERT TESTCABRERA | 5012270710 | Implemented, Considering Implementation, No Longer Applicable, and Will Not Implement DRPs will be excluded |

  #    PROD-15814
  @Regression @Smoke
  Scenario Outline: verify_user_able_to_see_pane_section_and_elements_in_pane_after_selecting_any_drp_form_drp_table
    Given User select organization: "<Organization>"
    Then Click on Patient Tab
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    And User click on "Reset order" button to reset table columns ordering
    And User click on "Show all" button to show all table hidden columns
    Then Verify user able to clear following column filters inboxes
      | <columnName> |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
    And User click on patient "<PatientName>" record
    Then User update drps implementation status from drps pane area: "<ImplementationStatus1>"
    And Verify implementation status column is updated with "<ImplementationStatus1>" for "<PatientName>" practitioner in campaign tab
#    Then Verify drps response date is updated as current date
    Then User update drps prior response date from drps pane area
    And Verify user not able to update drps response date for future date
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
    When User select practitioner "<PatientName2>" in campaign tab
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus1>"
    And Wait to page load
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus2>"
    And Verify implementation status column is updated with "<ImplementationStatus2>" for "<PatientName2>" practitioner in campaign tab
    And User click on patient "<PatientName2>" record
#    Then Verify drps response date is updated as current date
    Then Verify in drp pane "Patient" element has "<PatientName2>" value in practitioners tab
    Then Verify in drp pane "Campaign" element has "<CampaignDate>" value in practitioners tab
    Then Verify drps pane "Response Method" dropdown contains following options
      | Call (provider) |
      | Fax back        |
      | Claims          |
    Then Verify user able to enable column filter modes and search "<PatientForDOB>" in "<columnName>" column filter
    And Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    # Below Scenarios For PROD-15944 Below Scenarios
    Then Verify that the "<PatientForDOB>" patients DOB in the DRPs table under the Practitioners tab matches the DOB in the Patient tab
    When User select organization: "<Organization>"
    Then Click on Practitioners Tab
    And Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to clear following column filters inboxes
      | <columnName> |
    Examples:
      | Organization                      | NPI        | CampaignDate    | ImplementationStatus1 | columnName | ImplementationStatus2 | searchFilterName  | filterValue1 | PatientName      | PatientName2    | PatientForDOB       |
      | Admin Portal Test Patient Org DEV | 1750393690 | Test Campaign 1 | Will Not Implement    | Name       | Unknown               | Patient Last Name | Lucy280      | Miller Lucy28002 | Miller Lucy2802 | TEST22001 TEST22001 |
#      | Admin Portal Test Patient Org DEV | 2323232323 | Test Campaign 3 | Will Not Implement    | Name       | Unknown               | Patient First Name | TestAmanda   | TestAmanda TestMoon | TestAmanda TestBerger | TestAmanda TestKlein |

# PROD-19440
  Scenario Outline: Verify_response_date_column_present_in_the_reported_DRPs_table_on_practitioners_page
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And User click on "Reset order" button to reset table columns ordering
    And User click on "Show all" button to show all table hidden columns
    Then Verify "Reported DRPs" table has column
      | Response Date |
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  Scenario Outline: Verify_Implementation_Status_update_does_not_modify_Response_Date_unless_specified
    Given Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    When User selects practitioner "<PatientName1>" in practitioner tab
    And User gets the initial value of "<columnName>" for "<PatientName1>" in practitioner tab
    Then User updates "<DropDown Option>" dropdown to "<Value>" value in practitioner tab
    And Verify "<DropDown Option>" column is updated with "<Value>" for "<PatientName1>" in practitioner tab
    And User verifies initial value of "<columnName>" for "<PatientName1>" remains unchanged in practitioner tab
    When User selects practitioner "<PatientName1>" in practitioner tab
    And User update drps current response date from drps pane area
    Then Verify drps response date is updated as current date
    And Verify "<columnName>" date column is updated as current date for "<PatientName1>" in practitioner tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | NPI        | searchFilterName  | filterValue | PatientName1       | columnName    | DropDown Option       | Value       |
      | 1750393690 | Patient Last Name | Steven30410 | Miller Steven30410 | Response Date | Implementation Status | Implemented |

  Scenario Outline: Verify_Implementation_Status_update_via_bulk_action_does_not_modify_Response_Date_unless_specified
    Given Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    And Select first "2" practitioners in practitioner tab and update "<Option Value>" via bulk action and verify it in table
    Then User deselect all reported DRPs
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | NPI        | Option Value | searchFilterName  | filterValue |
      | 1750393690 | Implemented  | Patient Last Name | Steven      |

  Scenario Outline: Verify_response_method_selection_when_Response_Date_is_not_null
    Given User select organization: "<Organization>"
    Given Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    When User selects practitioner "<PatientName1>" in practitioner tab
    Then Verify fields are editable in practioner Tab
      | Response Method |
    And User tries to change "Response Method" and verifies it
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization                      | NPI        | PatientName1       | searchFilterName  | filterValue |
      | Admin Portal Test Patient Org DEV | 1750393690 | Miller Steven30410 | Patient Last Name | Steven30410 |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
@PharmacistPortal @PractitionerTabPrescriberAnalytics @PAFeature
Feature: Prescriber Analytics practitioner tab Detailed Analysis Feature

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
  Scenario Outline: verify_elements_and_tables_in_practitioners_tab
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on button "Follow Up" which is in campaigns button
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify the User able select the campaigns value as: "All"
    And Verify "Report History" table displayed in practitioner tab
    And Verify "Practitioner Timeline" table displayed in practitioner tab
    And Verify "Reported DRPs" table displayed in practitioner tab
    And User click on "Show all" button to show all table hidden columns
    And User click on "Show all" button to show all "Report History" table hidden columns
    And Verify "Report History" table has column
      | Created  |
      | Sent     |
      | Event    |
      | Type     |
      | Patients |
      | PDF      |
      | Log      |
    And Verify "Practitioner Timeline" table has column
      | Date              |
      | Logged by         |
      | Action            |
      | Type              |
      | Step(s) Performed |
      | Outcome           |
    And User click on "Show all" button to show all table hidden columns
    Then Verify user able to hide columns which are present in reports table
      | First Detected |
      | Resolved       |
    Then Verify user able to show columns which are present in reports table
      | First Detected |
      | Resolved       |
    And Verify "Reported DRPs" table has column
      | Name                  |
      | DoB                   |
      | Risk                  |
      | Algorithm             |
      | Impact                |
      | Implementation Status |
      | Resolved              |
      | First Detected        |
      | In Report?            |
      | Report Created        |
      | Report Sent           |
    Then Verify the Fax and Call toggle buttons
    #PRODPROD-14173
    Then Verify on "Select Action" dropDown "Bulk Patient Comment" option is not present it removed from the dropdown
    Then Verify DRPs Table count values: "2500"
    Then Verify that the jump to page text box does not allow entry of a number greater than the page count
    Then Verify the advance filter section field component
      | Patient First Name    |
      | Patient Last Name     |
      | Algorithm             |
      | Algorithm Impact      |
      | Implementation Status |
      | Resolved              |
      | Apply                 |
      | Cancel                |
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  @Regression @Smoke
  Scenario Outline: verify_log_action_function_in_practitioners_tab
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Verify "Practitioner Timeline" table displayed in practitioner tab
     # Below 4 Lines For PROD-14208
    Then Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Verify in pa feature "Log Action" button is enabled.
    Then Verify the User able select the campaigns value as: "All"
    Then Verify in pa feature "Log Action" button is enabled.
    And Click on button "Log Action" which is in campaigns button
    Then Create new log action with below information:
      | ACTION | TYPE     | STAKEHOLDER     | OUTCOME  | STEP(S) PERFORMED      |
      | Call   | Outbound | Medical Records | Answered | Confirmed Fax Received |
    Then User provider last log activity date will be displayed last in the timeline table
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  @Regression @Smoke
  Scenario Outline: verify_the_Reported_DRPS_implementation_status_functionality
    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    Then Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And User click on "Reset order" button to reset table columns ordering
    And User click on "Show all" button to show all table hidden columns
#    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    When User select practitioner "<Practitioner>" in campaign tab
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus>"
    And Verify implementation status column is updated with "<ImplementationStatus>" for "<Practitioner>" practitioner in campaign tab
#    Then Verify user able to clear following column filters inboxes
#      | <columnName> |
    Examples:
      | NPI        | ImplementationStatus       | Practitioner    | columnName | searchFilterName  | filterValue |
      | 1750393690 | Unknown                    | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |
      | 1750393690 | Implemented                | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |
      | 1750393690 | Plan to Implement          | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |
      | 1750393690 | Considering Implementation | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |
      | 1750393690 | No Longer Applicable       | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |
      | 1750393690 | Will Not Implement         | Miller Lucy2802 | Name       | Patient Last Name | Lucy2802    |

  @Regression @Smoke
  Scenario Outline: verify_the_ADVANCE_FILTER_in_DRPS_Table
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
#    PROD-14122
    Then Verify in PA feature "<searchFilterName>" advance filtered selected value is: "<filterSelectedValue>"
    And Verify "<verifyFilter>" column contains only "<filterValueToVerify>" values in reports drp table
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization                      | NPI        | searchFilterName      | filterValue         | filterValueToVerify | verifyFilter          | filterSelectedValue |
      | Admin Portal Test Patient Org DEV | 1750393690 | Patient First Name    | Miller              | Miller              | Name                  | Miller              |
      | Admin Portal Test Patient Org DEV | 1750393690 | Patient Last Name     | Steven              | Steven              | Name                  | Steven              |
      | Admin Portal Test Patient Org DEV | 1750393690 | Resolved              | Yes                 | Y                   | Resolved              | True                |
      | Admin Portal Test Patient Org DEV | 1750393690 | Report Created Date   | Date Range          | Date Range          | Report Created        | Date Range          |
      | Admin Portal Test Patient Org DEV | 1750393690 | Report Sent Date      | Date Range          | Date Range          | Report Sent           | Date Range          |
      | Admin Portal Test Patient Org DEV | 1750393690 | Algorithm             | DRP NAME 9332555954 | DRP NAME 9332555954 | Algorithm             | DRP NAME 9332555954 |
      | Admin Portal Test Patient Org DEV | 1750393690 | Algorithm Impact      | Low                 | Low                 | Impact                | Low                 |
      | Admin Portal Test Patient Org DEV | 1750393690 | Implementation Status | Unknown             | Unknown             | Implementation Status | Unknown             |

  @Regression @Smoke
  Scenario Outline: verify_the_Enable_Filter_Column_DRPs_Table
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Enable the Filter colum and verify the Move and Action colum and Filter mode for "Name" header
#    Then Enter the Patient name "CA32" in Filter by name section "Name" header
    Then Verify th column resize DRPS table header "DoB" colum
    Then Click on show hide column and verify the header section
      | Reset order |
      | Unpin all   |
      | Show all    |
    Then Verify user able to clear following column filters inboxes
      | Name |
      | DoB  |
    Then Verify following options are present in columns show hide popup:
      | Hide all              |
      | Reset order           |
      | Unpin all             |
      | Show all              |
      | Name                  |
      | DoB                   |
      | Risk                  |
      | Algorithm             |
      | Impact                |
      | Resolved              |
      | First Detected        |
      | Last Detected         |
      | In Report?            |
      | Report Created        |
      | Report Sent           |
      | Implementation Status |
    Examples:
      | NPI        |
      | 1750393690 |

  @Regression
  Scenario Outline: verify_after_enabling_tables_all_columns_header_should_show_move_and_column_action_icon_on_drps_table
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    Then Verify in drp table all column heading should have "move column" icons
    Then Verify in drp table all column heading should have "Column Action" icons
    And Click on columns filter show hide button and make sure button should: "Off"
    Then Wait to page load
    Then Verify in drp table all column heading should not show "move column" icons
    Then Verify in drp table all column heading should not show "Column Action" icons
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

#    Commented Scenarios Need to fix These later
#  @Regression
#  Scenario Outline: verify_pagination_bar_shows_the_count_of_resulted_records_after_applying_filter_in_drp_table
#    Given User select organization: "<Organization>"
#    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    Then Wait to page load
#    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    Then Verify user able to clear following column filters inboxes
#      | Name |
#    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
#    Then Wait to page load
#    Then Verify pagination count after applying filter on drps table
#    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    Examples:
#      | Organization                      | NPI        | searchFilterName  | filterValue  |
#      | Admin Portal Test Patient Org DEV | 1750393690 | Patient Last Name | Steven30410  |

#    PROD-14206
  @Regression
  Scenario Outline: verify_user_is_able_to_column_sorting_on_practitioners_reported_drps_table
    Then User logout from the application
    When User login with "<username>" and "<password>"
    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    Then Verify user able to clear following column filters inboxes
      | Name |
    And User click on "Show all" button to show all table hidden columns
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And verify "Ascending" for practitioners reported drps table column "<columnName>"
    And verify "Descending" for practitioners reported drps table column "<columnName>"
    And verify "Ascending" for practitioners reported drps table column "Risk"
    And verify "Descending" for practitioners reported drps table column "DoB"
    Examples:
      | username                                           | password                                           | message | Organization                      | NPI        | columnName |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | success | Admin Portal Test Patient Org DEV | 1750393690 | Name       |

#    PROD-14300 = 1 Scenario
  @Regression
  Scenario Outline: verify_user_is_able_to_column_sorting_on_report_history_table_in_practitioners_tab
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    Then Verify the User able select the campaigns value as: "Test Campaign 1"
    And verify "Ascending" for practitioners report history table column "Created"
    And verify "Descending" for practitioners report history table column "Created"
    And verify "Ascending" for practitioners report history table column "Event"
    And verify "Descending" for practitioners report history table column "Event"
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  @Regression
  Scenario Outline: verify_user_able_to_drag_and_move_drps_table_columns
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    And User click on "Show all" button to show all table hidden columns
    Then Verify user able to move drps table columns: "<Column1>" to "<Column2>"
    And Click on columns filter show hide button and make sure button should: "Off"
    And User click on "Reset order" button to reset table columns ordering
    Examples:
      | Organization                      | NPI        |  Column1 | Column2   |
      | Admin Portal Test Patient Org DEV | 1750393690 | Name    | Algorithm |

  @Regression
  Scenario Outline: verify_show_hide_modal_popup_includes_below_options
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify following options are present in columns show hide popup:
      | Name                  |
      | DoB                   |
      | Risk                  |
      | Algorithm             |
      | Impact                |
      | Implementation Status |
      | Resolved              |
      | First Detected        |
      | Last Detected         |
      | In Report?            |
      | Report Created        |
      | Report Sent           |
    Examples:
      | Organization                      | NPI        | Column1 | Column2   |
      | Admin Portal Test Patient Org DEV | 1750393690 | Name    | Algorithm |

    #    Commented Scenarios Need to fix These later
#  @Regression
#  Scenario Outline: verify_user_able_to_move_drps_table_columns_through_show_hide_popup
#    Given User select organization: "<Organization>"
#    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    And User click on "Show all" button to show all table hidden columns
#    And User click on "Reset order" button to reset table columns ordering
#    Then Verify user able to move drps table columns through show hide column popup: "<Column1>" move to "<Column2>"
#    Examples:
#      | Organization                      | NPI        | Column1               | Column2  |
#      | Admin Portal Test Patient Org DEV | 1750393690 | Implementation Status | Resolved |

  @Regression
  Scenario Outline: verify_user_able_to_see_following_elements_in_column_actions_popup_from_providers_table_columns
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    Then Verify in drp table all column heading should have "move column" icons
    Then Verify in drp table all column heading should have "Column Action" icons
    Then User clicks on "<Column>" columns column actions icon and verify following elements:
      | Sort by Name ascending  |
      | Sort by Name descending |
      | Filter by Name          |
      | Group by Name           |
      | Pin to left             |
      | Pin to right            |
      | Unpin                   |
    And Click on columns filter show hide button and make sure button should: "Off"
    Examples:
      | Organization                      | NPI        | Column |
      | Admin Portal Test Patient Org DEV | 1750393690 | Name   |

  @Regression
  Scenario Outline: verify_when_click_on_patient_name_in_drp_table_user_redirect_to_patient_tab
    Given User logout from the application
    When User login with "<username>" and "<password>"
    Then Wait to page load
    And Click on button "OK" present in confirmation popUp
    Then User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "All"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName>"
    Then Verify Patient Name: "Steven30410 Miller"
    Examples:
      | username                                           | password                                           | Organization                      | NPI        | searchFilterName  | filterValue | PatientName        |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | Admin Portal Test Patient Org DEV | 1750393690 | Patient Last Name | Steven30410 | Miller Steven30410 |

    #    Commented Scenarios Need to fix These later
#  @Regression
#  Scenario Outline: verify_user_able_to_pin_unpin_columns_ordering_through_show_hide_popup
#    Given User select organization: "<Organization>"
#    When Click on Practitioners Tab
#    And User search practitioner by npi: "<NPI>"
#    And User click on "Show all" button to show all table hidden columns
#    And User click on "Unpin all" button to unpin all table columns
#    Then Verify user able to "Pin To Right" column "<Column>" in drps table
#    And User search practitioner by npi: "<NPI>"
#    And User click on "Show all" button to show all table hidden columns
#    And User click on "Unpin all" button to unpin all table columns
#    Then Verify user able to "Pin To Left" column "<Column>" in drps table
#    And User search practitioner by npi: "<NPI>"
#    And User click on "Unpin all" button to unpin all table columns
#    And User click on "Reset order" button to reset table columns ordering
#    And Click on columns filter show hide button and make sure button should: "Off"
#    Examples:
#      | Organization                      | NPI        | Column                |
#      | Admin Portal Test Patient Org DEV | 1750393690 | Implementation Status |

  @Regression @Smoke
  Scenario Outline: verify_the_FaxProvider_Practitioner_selector_tab
#    Then User logout from the application
#    When User login with "<username>" and "<password>"
#    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Verify the FaxProvider button enabled or not
    Then Verify that If no DRPs are selected and which are default values are selected
      | Resend most recent report? |
      | Selected DRPs (0)          |
      | Multi-patient Report       |
      | Single-patient Report(s)   |
    Then Verify If Resend most recent report selected and DRP and report are enabled or not
      | Resend most recent report? |
      | Selected DRPs (0)          |
      | Multi-patient Report       |
      | Single-patient Report(s)   |
    Then Verify the Fax Provider section below details available or not
      | Resend most recent report? |
      | Selected DRPs (0)          |
      | Multi-patient Report       |
      | Single-patient Report(s)   |
      | What fax number?           |
      | Which DRPS?                |
      | What type of report?       |
    Examples:
      | username                                           | password                                           | Organization                      | NPI        |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | Admin Portal Test Patient Org DEV | 1750393690 |

  @Regression @Smoke
  Scenario Outline: verify_the_Patient_page_Reported_DRPS_selector_tab
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    Then Verify the User able select the campaigns value as: "All"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    When User select practitioner "<PatientName>" in campaign tab
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus>"
    And Verify mouse over on Reported DRPS table column value
      | Miller Lucy28002    |
      | Will Not Implement  |
#      | DRP NAME 4217621271 |
    And User click on patient "<PatientName>" record
    Then Verify the DRP detail pane contains
      | <PatientName>   |
      | Test Campaign 1 |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization                      | NPI        | searchFilterName  | filterValue | PatientName      | ImplementationStatus |
      | Admin Portal Test Patient Org DEV | 1750393690 | Patient Last Name | Lucy28002   | Miller Lucy28002 | Will Not Implement   |

  @Regression @Smoke
  Scenario Outline: verify_log_activity_functionality_in_campaigns_tab_select_bulk_action_redirect_to_practitioner_tab
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    Then User clicks on "Select Bulk Action" dropDown in campaigns page and select "Log Activity" option
    And Verify text on screen "Log New Action for <Practitioner>"
    And Wait to page load
    Then Create new log action with below information:
      | ACTION | TYPE     | STAKEHOLDER | OUTCOME  | STEP(S) PERFORMED      |
      | Call   | Outbound | Patient     | Answered | Confirmed Fax Received |
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    #Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then User provider last log activity date will be displayed last in the timeline table
    Then Verify user able to enable column filter modes and search "Miller Lucy2802" in "Name" column filter
    Then Select records on Reported DRPS Table values
      | Miller Lucy2802  |
      | Miller Lucy28002 |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "Unknown"
    Then Send the fax selecting DRPs to this fax number "5012270710" and note is "Test Fax" and click to "Send" button
    Then Verify the sent column status of report history table
      | Current Date  |
      | Queued        |
      | Multi-Patient |
    Then Verify user able to clear following column filters inboxes
      | Name |
    Examples:
      | Organization                      | CampaignDate    | RunDate    | Practitioner | columnName |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 03/22/2023 | JOHN HEAD    | Recipient  |

  @Regression @Smoke
  Scenario Outline: verify_comment_functionality_in_campaigns_tab_redirect_to_practitioner_tab
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    Then User clicks on "Select Bulk Action" dropDown in campaigns page and select "Add Practitioner Comment" option
    And Verify text on screen "Add Practitioner Comment for <Practitioner>"
    Then User add comment "<Comment>" through bulk action
    #And User click on provider "<Practitioner>" record
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "Miller Lucy2802" in "Name" column filter
    Then Select records on Reported DRPS Table values
      | Miller Lucy2802  |
      | Miller Lucy28002 |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "Unknown"
    Then Send the fax selecting DRPs to this fax number "5012270710" and note is "Test Fax" and click to "Send" button
    Then Verify the sent column status of report history table
      | Current Date |
      | Queued       |
    And User click on patient "<PatientName>" record
    And Verify comment "<Comment>"
    And Delete all comments if present
    Then Verify user able to clear following column filters inboxes
      | Name |
    When Click on campaigns tab
    Then Verify user able to clear following column filters inboxes
      | <columnName> |
    Examples:
      | Organization                      | CampaignDate    | RunDate    | Practitioner | columnName | Comment                 | PatientName |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 03/22/2023 | JOHN HEAD     | Recipient  | PractitionerTestComment | Miller Lucy2802 |

#  PROD- PROD-13073, 16492, 17698 , 15521
  @Regression @Smoke
  Scenario Outline: verify_when_user_select_campaign_as_all_in_practitioner_tab_fax_provider_tab_should_be_disable
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "All"
    Then Verify in pa feature "Fax Provider" button is disabled.
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  @Regression @Smoke
  Scenario Outline: verify_when_user_select_campaign_as_other_than_all_in_practitioner_tab_fax_provider_tab_should_be_enable_and_clickable
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "Test Campaign 1"
    And Click on button "Fax Provider" which is in campaigns button
    And Verify text on screen "Provider - Follow Up Fax"
    And Click on button "Cancel" which is in campaigns button
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

    ##INFRA-119
  @Regression @Smoke
  Scenario Outline: verify_log_created_in_story_tab_practitioners_timeline_table_as_submit_after_sending_a_fax
#    When User logout from the application
#    When User login with "<username>" and "<password>"
#    And Verify Login message: "<message>"
#    And Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
    Then Select records on Reported DRPS Table values
      | <Practitioner1>  |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on button "Fax Provider" which is in campaigns button
    Then Select resend most recent reports option in send fax popup
    Then Enter fax number "<FaxNumber>" in input and select that fax number
    And Click on button "Preview and Send" which is in campaigns button
    Then Verify report viewer
    And Click on button "Send" which is in campaigns button
    And Search patient: "<searchString>"
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then User provider last log activity date will be displayed last in the timeline table
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string>" in story tab for prescriber analytics feature
    And Verify newly created practitioners log action "<string>" is generated on current date and time
    Then Delete newly created practitioner timeline log action
    And Click on button "Follow Up" which is in campaigns button
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And User select all reported DRPs
    And User deselect all reported DRPs
    Then Delete all log actions if present in practitioner timeline table
    Examples:
      | username                                           | password                                           | message | searchString                         | Organization                      | CampaignDate    | NPI        | string    | searchFilterName  | filterValue1 | Practitioner1   | FaxNumber    |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | success | 5d83de75-8ac8-47d3-bf00-830bd1da7029 | Admin Portal Test Patient Org DEV | Test Campaign 1 | 1750393690 | Submitted | Patient Last Name | Lucy2802     | Miller Lucy2802 | 989-998-9889 |

  @Regression @Smoke
  Scenario Outline: verify_sent_status_as_queued_after_sending_fax_with_selected_drps_option
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select records on Reported DRPS Table values
      | <Practitioner>  |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on button "Fax Provider" which is in campaigns button
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
      | Queued       |
    And User deselect all reported DRPs
    Then Click on practitioner search icon
    Then Delete all log actions if present in practitioner timeline table
    Examples:
      | Organization                      | CampaignDate               | NPI        | Practitioner                      | FaxNumber    | whichDRPOption    | WhatTypeOfReportOption |
      | Admin Portal Test Patient Org DEV | Test PPOK-Arine Q3-Q4 2023 | 1750393690 | Lucy-DEV-9528 Miller-Admin-Portal | 989-998-9889 | Selected DRPs (1) | Multi-patient Report   |

  @Regression @Smoke
  Scenario Outline: verify_patients_column_count_and_pdf_functionality_in_report_history_table_after_sending_a_fax_for_multiple_patients
    When User logout from the application
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    And Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue1>" and click on "Apply" button
    Then Select records on Reported DRPS Table values
      | <Practitioner1>  |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Wait to page load
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue2>" and click on "Apply" button
    Then Select records on Reported DRPS Table values
      | <Practitioner2>  |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "Unknown"
    Then Send the fax selecting DRPs to this fax number "5012270710" and note is "Test Fax" and click to "Send" button
    Then Verify the sent column status of report history table
      | Current Date |
      | Queued       |
    Then Verify recently faxed log date will be displayed last in the report history table
    Then Verify recently faxed log date will be displayed last in the report history table and "<ColumnName>" column contains "<ColumnValue>" value
    And Click on first records pdf icon from report history table
    Then Verify report viewer
    Then Verify report contains: "<string1>"
    Then Verify report contains: "<string2>"
    And Click on button "Close" which is in campaigns button
    Examples:
      | username                                           | password                                           | message | Organization                      | CampaignDate    | NPI        | string1         | string2        | ColumnName | ColumnValue | searchFilterName  | filterValue1 | filterValue2 | Practitioner1   | Practitioner2    |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | success | Admin Portal Test Patient Org DEV | Test Campaign 1 | 1750393690 | MILLERLUCY28002 | MILLERLUCY2802 | Patients   | 2           | Patient Last Name | Lucy2802     | Lucy28002    | Miller Lucy2802 | Miller Lucy28002 |

  @Regression @Smoke
  Scenario Outline: verify_log_action_display_in_story_tabs_practitioner_timeline_table_after_sending_a_fax
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string>" in story tab for prescriber analytics feature
    Then Delete newly created practitioner timeline log action
    And Click on button "Follow Up" which is in campaigns button
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization                      | NPI        | string    |
      | Admin Portal Test Patient Org DEV | 1750393690 | Submitted |

#  Below Two Scenarios Are related to = PROD-13073
  @Regression @Smoke
  Scenario Outline: verify_practitioner_story_tab_information
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify practitioner Name: "<PractitionerName>"
    And Click on button "Story" which is in campaigns button
    Then Verify practitioners task details
    And Verify practitioners timeline details
    Examples:
      | Organization                      | NPI        | PractitionerName |
      | Admin Portal Test Patient Org DEV | 1750393690 | JOHN HEAD        |

# Below Two Scenarios Are related to = PROD-17698
  @Regression @Smoke
  Scenario Outline: verify_log_action_display_in_story_tabs_practitioner_timeline_table_after_changing_follow_up_status
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on button "Follow Up" which is in campaigns button
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Verify practitioner Name: "<PractitionerName>"
    Then Click on practitioner name link
    Then User update practitioner follow up status: "<FollowUpStatus_1>"
    Then Wait to page load
    Then User update practitioner follow up status: "<FollowUpStatus_2>"
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "Edit" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<LogAction>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<LogAction2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<LogAction3>" in story tab for prescriber analytics feature
#    Then Delete all log actions if present in practitioner timeline table
    Then Delete newly created practitioner timeline log action
    And Click on button "Follow Up" which is in campaigns button
    Examples:
      | Organization                      | NPI        |  CampaignDate   | PractitionerName | FollowUpStatus_1 | FollowUpStatus_2 | LogAction                | LogAction2  | LogAction3  |
      | Admin Portal Test Patient Org DEV | 1750393690 | Test Campaign 1 | JOHN HEAD        | New              | In Progress      | Updated Follow Up Status | CurrentDate | Edit        |

#  PROD-15521
  @Regression @Smoke
  Scenario Outline: verify_alert_icon_and_its_tooltip_for_all_open_drps_option
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on button "Follow Up" which is in campaigns button
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    And Click on button "Fax Provider" which is in campaigns button
    Then Verify alert icon displays for all open drps option and its tooltip message is: "<AlertMessage>"
    And Click on button "Cancel" which is in campaigns button
    Then Click on Patient Tab
    And User select organization: "<Organization2>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate2>"
    And Click on button "Fax Provider" which is in campaigns button
    Then Verify alert icon not displays for all open drps option
    And Click on button "Cancel" which is in campaigns button
    Then Verify the User able select the campaigns value as: "<CampaignDate3>"
    And Click on button "Fax Provider" which is in campaigns button
    Then Verify alert icon displays for all open drps option and its tooltip message is: "No Longer Applicable DRPs will be excluded"
    And Click on button "Cancel" which is in campaigns button
    Then Click on Patient Tab
    Then User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to clear following column filters inboxes
      | Name |
    And User select all reported DRPs
    And Click on Practitioners Tab
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus>"
    And Click on button "Fax Provider" which is in campaigns button
    Then User select DRP option "All Open DRPs" for follow up fax
    Then User select type of report: "Multi-patient Report"
    Then Enter fax number "<FaxNumber>" in input and select that fax number
    And Click on button "Preview and Send" which is in campaigns button
    Then Verify alert message "All DRPs are excluded."
    Then Verify alert message "This report could not be generated due to DRP exclusion criteria."
    And Click on button "Cancel" which is in campaigns button
    And User deselect all reported DRPs
    Examples:
      | Organization                      | NPI        | Organization2 | CampaignDate2  | CampaignDate3 | CampaignDate    | FaxNumber  | AlertMessage                                                                                                | ImplementationStatus |
      | Admin Portal Test Patient Org DEV | 1750393690 | ENG TEST      |  2023 Campaign | 2022 Campaign | Test Campaign 1 | 5012270710 | Implemented, Considering Implementation, No Longer Applicable, and Will Not Implement DRPs will be excluded | Will Not Implement   |

## Below One Scenario is related to = PROD-16492, PROD-17079
  @Regression @Smoke
  Scenario Outline: verify_user_able_to_send_fax_for_selected_drps_option
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    And User click on "Show all" button to show all table hidden columns
    Then Verify user able to enable column filter modes and search "<SearchPatient>" in "<columnName>" column filter
    And User click on "Reset order" button to reset table columns ordering
    Then Verify user able to enable column filter modes and search "<Patient1>" in "<columnName>" column filter
    Then Select records on Reported DRPS Table values
      | Miller Lucy2802  |
#      | Miller Lucy20091 |
    Then Verify user able to enable column filter modes and search "<Patient2>" in "<columnName>" column filter
    Then Select records on Reported DRPS Table values
#      | Miller Lucy2802  |
      | Miller Lucy20091 |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus>"
    Then Wait to page load
    And Click on button "Fax Provider" which is in campaigns button
    Then User select DRP option "<whichDRPOption>" for follow up fax
    Then User select type of report: "<WhatTypeOfReportOption>"
    Then Enter fax number "<FaxNumber>" in input and select that fax number
    And Click on button "Preview and Send" which is in campaigns button
    Then Verify report viewer
    Then Verify report contains: "<Patient1>"
    Then Verify report contains: "<Patient2>"
    And Click on button "Send" which is in campaigns button
    Then Verify user able to clear following column filters inboxes
      | <columnName> |
    Then User provider last log activity date will be displayed last in the timeline table
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        | columnName | ImplementationStatus |   whichDRPOption | WhatTypeOfReportOption | CampaignDate    | SearchPatient | Patient1       | Patient2        | FaxNumber  |
      | Admin Portal Test Patient Org DEV | 1750393690 | Name       |  Unknown             | Selected DRPs    | Multi-patient Report   | Test Campaign 1 |  Miller Lucy  | MILLERLUCY2802 | MILLERLUCY20091 | 5012270710 |

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
      | Miller Lucy20091 |
#      | Miller Lucy2802  |
    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus1>"
    Then User select all reported DRPs
    Then User deselect all reported DRPs
    Then Verify user able to enable column filter modes and search "<Patient2>" in "<columnName>" column filter
    Then Select records on Reported DRPS Table values
#      | Miller Lucy20091 |
      | Miller Lucy2802  |
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
      | Organization                      | NPI        | CampaignDate    | ImplementationStatus1 | ImplementationStatus2 | columnName | whichDRPOption | WhatTypeOfReportOption | SearchPatient | Patient1        | Patient2       | FaxNumber  | AlertMessage                                                                                                |
      | Admin Portal Test Patient Org DEV | 1750393690 | Test Campaign 1 | Will Not Implement    | Unknown               | Name       | Selected DRPs  | Multi-patient Report   | Miller Lucy   | MILLERLUCY20091 | MILLERLUCY2802 | 5012270710 | Implemented, Considering Implementation, No Longer Applicable, and Will Not Implement DRPs will be excluded |

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

    #PROD-14317
  @Regression
  Scenario Outline: verify_advance_filter_option_visibility_status_after_change_show_hide_status_of_table_column
    When User logout from the application
    When User login with "<username>" and "<password>"
    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Verify the User able select the campaigns value as: "<CampaignDate>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    And User click on "Reset order" button to reset table columns ordering
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<filterName1>" as "<filterValue1>" and click on "Apply" button
    Then Wait to page load
     Then Select reported drp table filter "<filterName2>" as "<filterValue2>" and click on "Apply" button
    Then Verify "<filterName1>" applied advance filtered options are visible in PA feature
    Then Verify "<filterName2>" applied advance filtered options are visible in PA feature
    Then Verify user able to hide columns which are present in reports table
      | <filterName1> |
    Then Verify user not able to see following option in advance filter popup
      | <filterName1> |
    Then Verify "<filterName1>" applied advance filtered option is not visible in PA feature
    And User click on "Show all" button to show all table hidden columns
    Then Verify "<filterName1>" applied advance filtered option is not visible in PA feature
    Then Verify user able to see following option in advance filter popup
      | <filterName1> |
    Then Verify "<filterName2>" applied advance filtered options are visible in PA feature
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | username                                           | password                                           | Organization                      |   NPI      | CampaignDate    | filterName1 | filterValue1        | filterName2           | filterValue2 |
      | $practitioner-tab-reports-analytics.user1.username | $practitioner-tab-reports-analytics.user1.password | Admin Portal Test Patient Org DEV | 1750393690 | Test Campaign 1 | Algorithm   | DRP NAME 9332555954 | Implementation Status | Unknown      |

#    PROD-16852, PROD-12799
  @Regression @Smoke
  Scenario Outline: verify_user_able_go_back_to_the_practitioners_and_campaigns_tab_with_selecting_original_org
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Wait to page load
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Wait to page load
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    # Go Back to the Campaigns Tab With Selecting Original Org
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    And User click on "Reset order" button to reset table columns ordering
#    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<ColumnName>" column filter
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
#    Then Verify user redirect to practitioners tab when click on provider name: "Test Demo"
    Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to clear following column filters inboxes
      | Name |
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName>"
    Then Verify Patient Name: "Steven30410 Miller"
    Then Verify searched patient organization name: "<Organization1>"
    And Verify "Practitioners" button contains dropdown and it contains following organizations
      | <Organization1> |
      | <Organization2> |
    Then Click on "Practitioners" button dropdown and select "<Organization2>" organization
    Then Verify user is on "Practitioners" global tab
    Then Verify searched patient organization name: "<Organization2>"
    # Go Back to the Campaigns Tab With Selecting Original Org
    When Click on campaigns tab
    Then Wait to page load
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName>"
    Then Verify Patient Name: "Steven30410 Miller"
    Then Verify searched patient organization name: "<Organization1>"
    And Verify "Campaigns" button contains dropdown and it contains following organizations
      | <Organization1> |
      | <Organization2> |
    Then Click on "Campaigns" button dropdown and select "<Organization2>" organization
    Then Verify user is on "Campaigns" global tab
    Then Verify searched patient organization name: "<Organization2>"
    Examples:
      | Organization                      | CampaignDate    | RunDate    | Practitioner | ColumnName | searchFilterName  | filterValue | PatientName        | Organization1                                 | Organization2                     |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 03/22/2023 | JOHN HEAD    | Recipient  | Patient Last Name | Steven30410 | Miller Steven30410 | Test1 Department of Health and Human Services | Admin Portal Test Patient Org DEV |

#    Also Covered PROD-16529
  @Regression @Smoke
  Scenario Outline: verify_user_able_go_back_to_the_practitioners_and_campaigns_tab_with_selecting_other_org
    Given User select organization: "<Organization>"
    # Go Back To The Campaigns And Practitioners Tab With selecting Another Org
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to clear following column filters inboxes
      | Name |
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName>"
    Then Verify Patient Name: "Steven30410 Miller"
    Then Verify searched patient organization name: "<Organization1>"
    Then Click on "Campaigns" button dropdown and select "<Organization1>" organization
    Then Verify user is on "Reports" global tab
    Then Verify searched patient organization name: "<Organization1>"
    And Verify "Practitioners" button contains dropdown and it contains following organizations
      | <Organization1> |
      | <Organization2> |
    Then Click on "Practitioners" button dropdown and select "<Organization2>" organization
    Then Verify user is on "Practitioners" global tab
    Then Verify searched patient organization name: "<Organization2>"
    # Go Back To The Practitioners With selecting Go Back To The Practitioners tab
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue2>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName2>"
    Then Verify Patient Name: "Adam20088 Miller"
    Then Verify searched patient organization name: "<Organization3>"
    Then Verify button: "Go Back to Practitioners"
    Then Click on button: "Go Back to Practitioners"
    And Wait to page load
    Then Verify user is on "Practitioners" global tab
    # Go Back To The Campaigns With selecting Go Back To The Campaigns tab
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue3>" and click on "Apply" button
    Then Verify user redirect to patient tab when click on patient name from drp table: "<PatientName3>"
    Then Verify Patient Name: "Steven26277 Miller"
    Then Verify searched patient organization name: "<Organization4>"
    Then Verify button: "Go Back to Campaigns"
    Then Click on button: "Go Back to Campaigns"
    And Wait to page load
    Then Verify user is on "Campaigns" global tab
    Examples:
      | Organization                      | CampaignDate    | RunDate    | Practitioner | searchFilterName  | filterValue | PatientName        | Organization1                                 | Organization2                     | filterValue2 | PatientName2     | Organization3 | filterValue3 |   PatientName3     | Organization4      |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 03/22/2023 | JOHN HEAD    | Patient Last Name | Steven30410 | Miller Steven30410 | Test1 Department of Health and Human Services | Admin Portal Test Patient Org DEV | Adam20088    | Miller Adam20088 | P3HP Test     | Steven26277  | Miller Steven26277 | Health New England |

## PROD-19440
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

#    PROD-19437
  @Regression @Smoke
  Scenario Outline: Verify_confirmation_dialog_and_practitioner_story_log_generation_when_patient_recommendations_are_discussed
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
    And Verify the User able select the campaigns value as: "All"
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Discussed Practitioner Report" from Steps Performed ComboBox
    And selects patient recommendations from the Report Discussion Selector table
    And clicks on the "Log Action" button and verify PopUp "<string>"
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    And Verify newly created practitioners log action "<string1>" is generated on current date and time
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        | string1 | string2  | string3         | string4  |string                                                                   |
      | Admin Portal Test Patient Org DEV | 1750393690 | Call    | Outbound | Medical Records | Answered | Submitting this action will create story logs for the selected patients. |

  @Regression @Smoke
  Scenario Outline: Verify_no_confirmation_dialog_when_patient_recommendations_are_not_discussed
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Confirmed Fax Received" from Steps Performed ComboBox
    And clicks on the "Log Action" button and verify PopUp "<string>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    And Verify newly created practitioners log action "<string1>" is generated on current date and time
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        | string1 | string2  | string3         | string4  |string                                                                   |
      | Admin Portal Test Patient Org DEV | 1750393690 | Call    | Outbound | Medical Records | Answered | Submitting this action will create story logs for the selected patients. |

  @Regression @Smoke
  Scenario Outline:Verify_confirmation_dialog_and_practitioner_story_log_generation_in_a_campaign_tab
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Discussed Practitioner Report" from Steps Performed ComboBox
    And selects patient recommendations from the Report Discussion Selector table
    And clicks on the "Log Action" button and verify PopUp "<string>"
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    And Verify newly created practitioners log action "<string1>" is generated on current date and time
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | CampaignDate    | RunDate    | string1 | string2  | string3         | string4  | Practitioner                                       |string                                                                   |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 05/11/2024 | Call    | Outbound | Medical Records | Answered | Bichael TestJordan_specialist_in_nephrologist_0001 | Submitting this action will create story logs for the selected patients. |

  @Regression @Smoke
  Scenario Outline: Verify_no_confirmation_dialog_in_campaign_tab_workflow_when_recommendations_are_not_discussed
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Confirmed Fax Received" from Steps Performed ComboBox
    And clicks on the "Log Action" button and verify PopUp "<string>"
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    And Verify newly created practitioners log action "<string1>" is generated on current date and time
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | CampaignDate    | RunDate    | string1 | string2  | string3         | string4  | Practitioner                                       | string                                                                   |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 05/11/2024 | Call    | Outbound | Medical Records | Answered | Bichael TestJordan_specialist_in_nephrologist_0001 | Submitting this action will create story logs for the selected patients. |


  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
@PharmacistPortal @PrescriberAnalytics @PAFeature
Feature: Prescriber Analytics Campaign tab Detailed Analysis Feature

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                          | password                                          | message |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | success |

  @Regression @Smoke
  Scenario Outline: verify_elements_present_in_campaign_page
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Verify "Campaign" dropDown input displayed
    Then Verify "Run" dropDown input displayed
    And Verify reports table displayed in global campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    Then Wait to page load
    And User select campaign run date as: "<RunDate>"
    And Verify cohort size option displayed in global campaigns tab
    Then Verify following buttons are present in select bulk action dropdown
      | Edit Follow Up Status    |
      | Download Selected        |
      | Log Activity             |
      | Add Practitioner Comment |
    Then Verify following options are present in followup status
      | New                         |
      | Support Follow Up Needed    |
      | Pharmacist Call Needed      |
      | Pharmacist Follow Up Needed |
      | Additional Outreach Needed  |
      | Outreach Complete           |
      | Unable to Reach             |
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    |
      | ENG TEST     | 2022 Campaign | 02/09/2022 |

  @Regression
  Scenario Outline: verify_reports_table_columns_feature_in_campaign_pageExamples
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user able to hide columns which are present in reports table
      | PDF       |
      | Recipient |
    Then Verify user able to show columns which are present in reports table
      | PDF       |
      | Recipient |
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    And User click on "Reset order" button to reset table columns ordering
    Then Verify columns present in reports table which is present in campaigns tab
      | PDF             |
      | Recipient       |
      | NPI             |
      | Risk            |
      | Address         |
      | Phone Number    |
      | Last Fax Number |
      | Last Faxed      |
      | Quiet?          |
      | Log             |
    #      | Do Not Fax      |
    Examples:
      | Organization | CampaignDate  | RunDate    |
      | ENG TEST     | 2022 Campaign | 02/09/2022 |

  @Regression
  Scenario Outline: verify_bulk_edit_follow_up_status_functionality
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And Verify reports table displayed in global campaigns tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    Then User clicks on "Select Bulk Action" dropDown and select "Edit Follow Up Status" option and edit follow up status as: "<FollowUpStatus>"
    And Verify follow up status column is updated with "<FollowUpStatus>" for "<Practitioner>" practitioner in campaign tab
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    | FollowUpStatus              | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | New                         | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Support Follow Up Needed    | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Pharmacist Call Needed      | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Pharmacist Follow Up Needed | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Additional Outreach Needed  | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Outreach Complete           | Mackenci Hudiburg | Recipient  |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Unable to Reach             | Mackenci Hudiburg | Recipient  |

#  PROD-14917
  @Regression
  Scenario Outline: verify_bulk_edit_follow_up_status_functionality_for_multiple_practitioners
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And Verify reports table displayed in global campaigns tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select first "2" practitioners in campaigns tab and update its followup status "<FollowupStatus>" from bulk action and verify it in table
    Then Select first "2" practitioners in campaigns tab and update its followup status "<FollowupStatus2>" from bulk action and verify it in table
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    | FollowupStatus         | FollowupStatus2 |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Pharmacist Call Needed | New             |

##  PROD-14167
  @Regression @Smoke
  Scenario Outline: verify_user_is_able_to_select_and_deselect_multiple_tasks_by_selecting_checkbox
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify select multiple prescribers by selecting checkbox
    And User deselect all existing practitioners
    Examples:
      | Organization | CampaignDate  | RunDate    |
      | ENG TEST     | 2022 Campaign | 02/09/2022 |

  @Regression
  Scenario Outline: verify_single_practitioners_report_download_functionality
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    And User deletes existing files with name "MACKENCI HUDIBURG.pdf"
    Then User clicks on "Select Bulk Action" dropDown in campaigns page and select "Download Selected" option
    And Verify Downloaded file has file name: "MACKENCI HUDIBURG"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_multiple_practitioners_reports_download_functionality
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Select multiple practitioners in campaigns tab
      | <Practitioner_1> |
      | <Practitioner_2> |
    Then User clicks on "Select Bulk Action" dropDown in campaigns page and select "Download Selected" option
    And Verify text on screen "Multiple PDFs selected"
    And Verify text on screen "Do you want to combine PDF files into one file?"
    And User deletes existing files with name "combined_reports.pdf"
    And Click on button "Yes" which is in campaigns button
    And Verify Downloaded file has file name: "combined_reports"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner_1    | Practitioner_2 |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | KAN ZHANG      |

  @Regression
  Scenario Outline: verify_when_click_on_provider_name_in_table_user_redirect_to_practitioners_tab
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: Verify_followup_status_is_configurable_by_admin_portal_patient_org
    When User logout from the application
    Given User go to application "$admin_portal_url"
    And Wait to page load
    When User refreshes page
    When Admin User login with "<AdminUsername>" and "<AdminPassword>"
    When User refreshes page
    Then User click on button organizations and go to "Patient Organizations"
    Then Verify text on screen "Patient Organizations"
    And Wait to page load
    And User clicks on reset button
    And User select organization from patient organizations tab dropDown
      | <Organization> |
    When User clicks on search button
    And Select "<Organization>" organization from search result and open edit Configuration model
    Then Wait to page load
    Then Wait to page load
    Then Verify text on screen "Edit Org Config"
    Then User select "<FieldToUpdate>" field from dropdown and update it with the data: "<fileForUpdateOrgData>"
    And Wait to page load
    Then Admin User logout from the application
    Then User go to application "$pharmacist_portal_url"
    When User login with "<AdminUsername>" and "<AdminPassword>"
    And Verify Login message: "<message>"
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Wait to page load
    Then Verify following options are present in followup status
      | New                                |
      | Support Follow Up Needed           |
      | Pharmacist Call Needed             |
      | Pharmacist Follow Up Needed        |
      | Additional Outreach Needed         |
      | Outreach Complete                  |
      | Unable to Reach                    |
      | Added By Automation Need To Remove |
    Given User go to application "$admin_portal_url"
    When Admin User login with "<AdminUsername>" and "<AdminPassword>"
    Then User click on button organizations and go to "Patient Organizations"
    Then Verify text on screen "Patient Organizations"
    And Wait to page load
    And User clicks on reset button
    And User select organization from patient organizations tab dropDown
      | <Organization> |
    When User clicks on search button
    And Select "<Organization>" organization from search result and open edit Configuration model
    Then Wait to page load
    Then Verify text on screen "Edit Org Config"
    Then User select "<FieldToUpdate>" field from dropdown and update it with the data: "<fileForUpdateOrgDefaultData>"
    When Admin User logout from the application
    Examples:
      | AdminUsername                                     | AdminPassword                                     | message | Organization | CampaignDate  | RunDate    | FieldToUpdate | TicketNo  | ConfirmMessage                     | fileForUpdateOrgData                       | fileForUpdateOrgDefaultData                |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | success | ENG TEST     | 2022 Campaign | 02/09/2022 | campaign      | PROD-2523 | Change For Test PA Followup Status | temp/campaignFollowUpStatusUpdatedJson.txt | temp/campaignFollowUpStatusDefaultJson.txt |

  @Regression
  Scenario Outline: verify_log_activity_functionality_in_campaigns_tab_select_bulk_action
    Then User go to application "$pharmacist_portal_url"
    When User login with "<Username>" and "<Password>"
    And Verify Login message: "<message>"
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
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
    Then Verify practitioner Name: "<Practitioner>"
    Then User provider last log activity date will be displayed last in the timeline table
    And Delete newly created practitioner timeline log action
    Examples:
      | Username                                          | Password                                          | message | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | success | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_comment_functionality_in_campaigns_tab_select_bulk_action
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    Then User clicks on "Select Bulk Action" dropDown in campaigns page and select "Add Practitioner Comment" option
    And Verify text on screen "Add Practitioner Comment for <Practitioner>"
    Then User add comment "<Comment>" through bulk action
    And User click on provider "<Practitioner>" record
    Then Verify practitioner details section
    And Verify comment "<Comment>"
    And Delete all comments if present
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName | Comment           |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  | PABulkTestComment |

  @Regression
  Scenario Outline: verify_pdf_popup_should_be_open_when_user_click_on_pdf_icon_in_reports_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And User click on the pdf icon in the table for the prescriber: "<Practitioner>"
    Then Verify pdf viewer in campaigns tab
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_edit_practitioner_address_details
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And User click on provider "<Practitioner>" record
    Then User updates practitioner address : "<Address>"
    And Verify "Address" for practitioner in campaigns tab "<Practitioner>" is updated as "<Address>"
    Then User updates practitioner address : "<PreviousAddress>"
    And Verify "Address" for practitioner in campaigns tab "<Practitioner>" is updated as "<PreviousAddress>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName | Address                               | PreviousAddress |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  | PA Feature Updated by Test Automation | 700 Ne 13Th St  |

  @Regression
  Scenario Outline: verify_edit_practitioner_phone_number_details
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And User click on provider "<Practitioner>" record
    And Update practitioner in campaigns tab "Preferred Phone" as "727-327-7656"
    And Verify "Phone Number" for practitioner in campaigns tab "<Practitioner>" is updated as "727-327-7656"
    And Update practitioner in campaigns tab "Preferred Phone" as "405-271-4701"
    And Verify "Phone Number" for practitioner in campaigns tab "<Practitioner>" is updated as "405-271-4701"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_edit_practitioner_followup_status_details
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And User click on provider "<Practitioner>" record
    Then User update practitioner follow up status: "<FollowUpStatus_1>"
    And Verify "Follow Up Status" for practitioner in campaigns tab "<Practitioner>" is updated as "<FollowUpStatus_1>"
    Then User update practitioner follow up status: "<FollowUpStatus_2>"
    And Verify "Follow Up Status" for practitioner in campaigns tab "<Practitioner>" is updated as "<FollowUpStatus_2>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName | FollowUpStatus_1 | FollowUpStatus_2       |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  | New              | Pharmacist Call Needed |

  @Regression
  Scenario Outline: verify_user_able_to_see_patient_count_and_drp_count_to_practitioner_in_patient_column
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Reset order" button to reset table columns ordering
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And Verify "Patients" for practitioner in campaigns tab "<Practitioner>" is updated as "<PatientCount>"
    And Verify "DRPs" for practitioner in campaigns tab "<Practitioner>" is updated as "<DRPCount>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName | PatientCount | DRPCount |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  | 219          | 301      |

  @Regression
  Scenario Outline: verify_user_able_to_see_last_fax_number_last_fax_date_to_practitioner_in_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And Verify "Last Fax Number" for practitioner in campaigns tab "<Practitioner>" is updated as "<LastFaxNumber>"
    And Verify "Last Faxed" for practitioner in campaigns tab "<Practitioner>" is updated as "<LastFaxedDate>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName | LastFaxNumber | LastFaxedDate |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  | 772-660-5175  | 04/09/2023    |

  @Regression
  Scenario Outline: verify_user_able_to_see_last_fax_number_and_last_faxed_is_null_when_last_fax_campaign_fail
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And Verify "Last Fax Number" for practitioner in campaigns tab "<Practitioner>" is updated as " "
    And Verify "Last Faxed" for practitioner in campaigns tab "<Practitioner>" is updated as " "
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | KAN ZHANG    | Recipient  |

#   Commented because of  PROD-15582
#  @Regression
#  Scenario Outline: verify_user_able_to_see_Yes_in_do_not_fax_when_provider_is_set_as_do_not_fax_and_if_not_it_is_showing_null
#    Given User select organization: "<Organization>"
#    When Click on campaigns tab
##    Then User select campaigns date as: "<CampaignDate>"
##    And User select campaign run date as: "<RunDate>"
#    And User deselect all existing practitioners
#    Then Verify user able to enable column filter modes and search "<PractitionerDoNotFaxNull>" in "<columnName>" column filter
#    And Verify "Do Not Fax" for practitioner in campaigns tab "<PractitionerDoNotFaxNull>" is updated as " "
#    Then Verify user able to enable column filter modes and search "<PractitionerDoNotFaxYes>" in "<columnName>" column filter
#    And Verify "Do Not Fax" for practitioner in campaigns tab "<PractitionerDoNotFaxYes>" is updated as "Yes"
#    Examples:
#      | Organization | CampaignDate  | RunDate    | PractitionerDoNotFaxNull | PractitionerDoNotFaxYes | columnName |
#      | ENG TEST     | 2022 Campaign | 02/09/2022 | KAN ZHANG                | Jack Rose               | Recipient  |

# # PROD-17699
  @Regression
  Scenario Outline: verify_user_able_to_see_y_and_tooltip_in_Quiet_column_when_provider_is_in_quiet_period
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    And Verify "Quiet?" for practitioner in campaigns tab "<Practitioner>" is updated as ""
#    Then Verify tooltip "<TooltipMessage>" when mouse hover on "<Practitioner>" providers "Quiet?" column value
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner   | columnName | QuietStatus | TooltipMessage     |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | RICHARD OXHORN | Recipient  | Y           | Through 06/16/2026 |

    #    Commented Scenarios Need to fix These later
### PROD-16888 Scenarios Covered in Below Three Scenarios
#  @Regression
#  Scenario Outline: verify_log_activity_functionality_by_clicking_on_log_icon_which_is_in_table
#    Given User select organization: "<Organization>"
#    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
#    And User deselect all existing practitioners
#    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
#    When User select practitioner "<Practitioner>" in campaign tab
#    And User click on the log icon in the table for the prescriber: "<Practitioner>"
#    And Verify text on screen "Log New Action for"
#    And Wait to page load
#    Then Create new log action in campaigns tab with below information:
#      | ACTION | TYPE     | STAKEHOLDER | OUTCOME  | STEP(S) PERFORMED      |
#      | Call   | Outbound | Patient     | Answered | Confirmed Fax Received |
#    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
#    Then Verify practitioner Name: "<Practitioner>"
#    Then User provider last log activity date will be displayed last in the timeline table
#    And Delete newly created practitioner timeline log action
#    Examples:
#      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
#      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_when_user_perform_the_log_activity_with_call_action_then_last_contacted_date_should_be_update_in_campaigns_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    When User select practitioner "<Practitioner>" in campaign tab
    And User click on the log icon in the table for the prescriber: "<Practitioner>"
    And Verify text on screen "Log New Action for"
    And Wait to page load
    Then Create new log action in campaigns tab with below information:
      | ACTION | TYPE     | STAKEHOLDER | OUTCOME  | STEP(S) PERFORMED      |
      | Call   | Outbound | Patient     | Answered | Confirmed Fax Received |
    When Click on Practitioners Tab
    Then Click on campaigns tab
    Then Verify in table "Last Contacted" date is updated as current date for practitioner: "<Practitioner>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression @Smoke
  Scenario Outline: verify_when_user_perform_the_log_activity_in_practitioners_tab_then_last_contacted_date_should_be_update_in_campaigns_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User deselect all existing practitioners
    Then Verify user able to enable column filter modes and search "<Practitioner>" in "<columnName>" column filter
    Then Wait to page load
    Then Verify user redirect to practitioners tab when click on provider name: "<Practitioner>"
    Then Verify practitioner Name: "<Practitioner>"
    And Click on button "Log Action" which is in campaigns button
    Then Create new log action with below information:
      | ACTION | TYPE     | STAKEHOLDER     | OUTCOME  | STEP(S) PERFORMED      |
      | Call   | Outbound | Medical Records | Answered | Confirmed Fax Received |
    Then User provider last log activity date will be displayed last in the timeline table
    When Click on campaigns tab
    Then Verify in table "Last Contacted" date is updated as current date for practitioner: "<Practitioner>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | Organization | CampaignDate  | RunDate    | Practitioner      | columnName |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Mackenci Hudiburg | Recipient  |

  @Regression
  Scenario Outline: verify_filter_option_icon_is_present_and_when_click_on_it_advance_filters_popup_should_display
    Then User logout from the application
    When User login with "<username>" and "<password>"
    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Click on campaigns reports filter icon
    And Verify text on screen "Advanced Filters"
    Then Click on button "Cancel" which is in campaigns button
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Examples:
      | username                                          | password                                          | Organization | CampaignDate  | RunDate    |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | ENG TEST     | 2022 Campaign | 02/09/2022 |

  @Regression
  Scenario Outline: verify_after_applying_advance_filters_results_should_reflect_accordingly_in_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User click on "Show all" button to show all table hidden columns
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select campaigns reports table filter "<filterName>" as "<filterValue>" and click on "Apply" button
    #    PROD-14122
    Then Verify in PA feature "<filterName>" advance filtered selected value is: "<filterValue>"
    And Verify "<filterName>" column contains only "<filterValue>" values in reports table
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization | CampaignDate  | RunDate    | filterName       | filterValue       |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Recipient        | Mackenci Hudiburg |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Specialty        | PHYSICIAN         |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | NPI              | 1255720355        |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Follow Up Status | New               |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Last Faxed       | Date Range        |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Last Fax Number  | 772-660-5175      |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Last Contacted   | Date Range        |
#      | ENG TEST     | 2022 Campaign | 02/09/2022 | Do Not Fax       | Yes               |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Address          | 700 Ne 13Th St    |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Phone Number     | 405-271-4701      |

  @Regression
  Scenario Outline: verify_user_is_able_to_column_sorting_on_providers_report_table
#    Then User logout from the application
#    When User login with "<username>" and "<password>"
#    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    And User click on "Show all" button to show all table hidden columns
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And verify "Ascending" for providers report table column "<columnName>"
    And verify "Descending" for providers report table column "<columnName>"
    And verify "Descending" for providers report table column "Follow Up Status"
    And verify "Ascending" for providers report table column "Risk"
    Examples:
      | username                                          | password                                          | Organization | CampaignDate  | RunDate    | columnName |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | ENG TEST     | 2022 Campaign | 02/09/2022 | Recipient  |

  @Regression
  Scenario Outline: verify_that_cancel_button_closes_advance_filter_popup_and_it_does_not_filter_data_in_providers_report_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    And User click on "Show all" button to show all table hidden columns
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select campaigns reports table filter "<filterName>" as "<filterValue>" and click on "Apply" button
    And Verify applied advance filtered options are visible in campaigns tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select campaigns reports table filter "<filterName>" as "<filterValue>" and click on "Cancel" button
    And Verify applied advance filtered options are not visible in campaigns tab
    Examples:
      | Organization | CampaignDate  | RunDate    | filterName | filterValue       |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Recipient  | Mackenci Hudiburg |

  @Regression
  Scenario Outline: verify_pagination_bar_shows_the_count_of_resulted_records_after_applying_filter_in_campaigns_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select campaigns reports table filter "<filterName>" as "<filterValue>" and click on "Apply" button
    Then Verify pagination count after applying filter on campaign table
    Examples:
      | Organization | CampaignDate  | RunDate    | filterName | filterValue       |
      | ENG TEST     | 2022 Campaign | 02/09/2022 | Recipient  | Mackenci Hudiburg |

  @Regression
  Scenario Outline: verify_jump_to_page_functionality_on_campaigns_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Verify that the jump to page text box does not allow entry of a number greater than the page count
    Examples:
      | Organization | CampaignDate  | RunDate    |
      | ENG TEST     | 2023 Campaign | 03/22/2023 |

  @Regression
  Scenario Outline: verify_after_enabling_tables_all_columns_header_should_show_move_and_column_action_icon_on_campaigns_table
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    Then Verify user able to clear following column filters inboxes
      | Recipient |
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    Then Verify in campaigns table all column heading should have "move column" icons
    Then Verify in campaigns table all column heading should have "Column Action" icons
    And Click on columns filter show hide button and make sure button should: "Off"
    Then Wait to page load
    Then Verify in campaigns table all column heading should not show "move column" icons
    Then Verify in campaigns table all column heading should not show "Column Action" icons
    Examples:
      | Organization | CampaignDate  | RunDate    |
      | ENG TEST     | 2023 Campaign | 03/22/2023 |

  @Regression
  Scenario Outline: verify_user_able_to_drag_and_move_providers_table_columns
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    Then Verify in campaigns table all column heading should have "move column" icons
    And User click on "Show all" button to show all table hidden columns
    Then Verify user able to move providers table columns: "<Column1>" to "<Column2>"
    And Click on columns filter show hide button and make sure button should: "Off"
    And User click on "Reset order" button to reset table columns ordering
    Examples:
      | Organization | CampaignDate  | RunDate    | Column1 | Column2 |
      | ENG TEST     | 2023 Campaign | 03/22/2023 | NPI     | Address |

  @Regression
  Scenario Outline: verify_user_able_to_move_providers_table_columns_through_show_hide_popup
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Reset order" button to reset table columns ordering
    Then Verify user able to move providers table columns through show hide column popup: "<Column1>" move to "<Column2>"
    Examples:
      | Organization | CampaignDate  | RunDate    | Column1   | Column2 |
      | ENG TEST     | 2023 Campaign | 03/22/2023 | Recipient | Address |

  @Regression
  Scenario Outline: verify_show_hide_modal_popup_includes_below_options
    Then User logout from the application
    When User login with "<username>" and "<password>"
    Then Click on button "OK" present in confirmation popUp
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Verify following options are present in columns show hide popup:
      | Reset order     |
      | Unpin all       |
      | Show all        |
      | PDF             |
      | Recipient       |
      | NPI             |
      | Risk            |
      | Address         |
      | Phone Number    |
      | Last Fax Number |
#      | Last Faxed      |
      | Quiet?          |
#      | Do Not Fax      |
      | Log             |
    Then Verify following options are not present in columns show hide popup:
      | Do Not Fax              |
    Examples:
      | username                                          | password                                          | Organization | CampaignDate  | RunDate    |
      | $campaign-tab-prescriber-analytics.user1.username | $campaign-tab-prescriber-analytics.user1.password | ENG TEST     | 2023 Campaign | 03/22/2023 |

  @Regression
  Scenario Outline: verify_user_able_to_reset_columns_ordering_through_show_hide_popup
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Reset order" button to reset table columns ordering
    Then Verify user able to reset column ordering by clicking: "Reset order"
    And Click on columns filter show hide button and make sure button should: "Off"
    Examples:
      | Organization | CampaignDate  | RunDate    | Column1   | Column2 |
      | ENG TEST     | 2023 Campaign | 03/22/2023 | Recipient | Address |

  @Regression
  Scenario Outline: verify_user_able_to_pin_unpin_columns_ordering_through_show_hide_popup
    Given User select organization: "<Organization>"
    When Click on campaigns tab
#    Then User select campaigns date as: "<CampaignDate>"
#    And User select campaign run date as: "<RunDate>"
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    Then Verify user able to "Pin To Right" column "<Column>" in providers table
    And User click on "Unpin all" button to unpin all table columns
    Then Verify user able to "Pin To Left" column "<Column>" in providers table
    And User click on "Unpin all" button to unpin all table columns
    And Click on columns filter show hide button and make sure button should: "Off"
    Examples:
      | Organization | CampaignDate  | RunDate    | Column    |
      | ENG TEST     | 2023 Campaign | 03/22/2023 | Recipient |

  @Regression
  Scenario Outline: verify_user_able_to_see_following_elements_in_column_actions_popup_from_providers_table_columns
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    And Click on columns filter show hide button and make sure button should: "On"
    Then Verify in campaigns table all column heading should have "move column" icons
    Then Verify in campaigns table all column heading should have "Column Action" icons
    Then User clicks on "<Column>" columns column actions icon and verify following elements:
      | Sort by Address ascending  |
      | Sort by Address descending |
      | Clear filter               |
      | Filter by Address          |
      | Group by Address           |
      | Pin to left                |
      | Pin to right               |
      | Unpin                      |
      | Reset column size          |
      | Hide Address column        |
      | Show all columns           |
    Examples:
      | Organization | CampaignDate  | RunDate    | Column  |
      | ENG TEST     | 2023 Campaign | 03/22/2023 | Address |

  #  PROD-14558
  @Regression
  Scenario Outline: verify_user_able_to_see_tooltip_for_providers_report_table_<ColumnHeading>_column_in_campaigns_tab
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    And User click on "Show all" button to show all table hidden columns
    And User click on "Reset order" button to reset table columns ordering
    Then Verify "<ColumnHeading>" column heading name contains tooltip message: "<TooltipMessage>"
    Examples:
      | Organization | ColumnHeading    | TooltipMessage                                                                                              |
#      | ENG TEST     | PDF              | View report generated from this run.                                                                        |
      | ENG TEST     | Risk             | Computed risk score based on patient recommendations identified for the practitioner from this campaign run |
      | ENG TEST     | Phone Number     | Practitioner's preferred phone number                                                                       |
      | ENG TEST     | Patients         | Number of unique patients identified from this campaign run for the practitioner                            |
      | ENG TEST     | DRPs             | Number of unique algorithms identified from this campaign run for the practitioner                          |
      | ENG TEST     | Follow Up Status | Follow up status set for the practitioner for this campaign                                                 |
      | ENG TEST     | Last Fax Number  | Fax number used for last successful fax sent for this campaign                                              |
      | ENG TEST     | Last Faxed       | Sent date for last successful fax sent for this campaign                                                    |
      | ENG TEST     | Last Faxed       | Sent date for last successful fax sent for this campaign                                                    |
      | ENG TEST     | Last Contacted   | Date of the last call to this prescriber for this campaign                                                  |
#      | ENG TEST     | Log              | Log an action for the provider                                                                              |

  #PROD-14317
  @Regression
  Scenario Outline: verify_advance_filter_option_visibility_status_after_change_show_hide_status_of_table_column
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    And User click on "Reset order" button to reset table columns ordering
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Select campaigns reports table filter "<filterName1>" as "<filterValue1>" and click on "Apply" button
    Then Wait to page load
    Then Select campaigns reports table filter "<filterName2>" as "<filterValue2>" and click on "Apply" button
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
    Examples:
      | Organization | filterName1 | filterValue1      | filterName2  | filterValue2   |
      | ENG TEST     | Recipient   | Mackenci Hudiburg | Phone Number | 405-271-4701 |

#    PROD-15582
  @Regression
  Scenario Outline: verify_do_not_fax_element_removed_from_PA_feature
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    And User click on "Show all" button to show all table hidden columns
    And User click on "Unpin all" button to unpin all table columns
    And User click on "Reset order" button to reset table columns ordering
    Then Verify following options are not present in columns show hide popup:
      | Do Not Fax              |
    Then Verify columns not present in reports table which is present in campaigns tab
      | Do Not Fax              |
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Wait to page load
    Then Verify the Fax toggle button not present in practitioners tab
    Examples:
      | Organization |  NPI       |
      | ENG TEST     | 1750393690 |

#    PROD-15573
  @Regression
  Scenario Outline: verify_ordering_of_run_dates_in_run_dropdown
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    Then Verify that the run dates in the select run dropdown are in "descending order"
    Examples:
      | Organization | CampaignDate  |
      | ENG TEST     | 2022 Campaign |

  ## Below two scenarios are PROD-26021 Ticket Scenarios
  @Regression
  Scenario Outline: verify_discussed_practitioner_report_option_in_campaigns_tab_lab_popup
    Given User select organization: "<Organization>"
    When Click on campaigns tab
    And User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
    Then Wait to page load
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Then Wait to page load
    And User click on the log icon in the table for the prescriber: "<Practitioner>"
    And Wait to page load
    And Verify text on screen "What was discussed from this report ? (required)"
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    And Wait to page load
    And Verify text on screen "This section is intentionally blank"
    Then Select "Confirmed Fax Received" from Steps Performed ComboBox
    And Wait to page load
    And Verify text on screen "This section is intentionally blank"
    Examples:
      | Organization                         | CampaignDate    | RunDate    | Practitioner                                       |
      | Admin Portal Test Patient Org DEV    | Test Campaign 1 | 05/11/2024 | Bichael TestJordan_specialist_in_nephrologist_0001 |

  @Regression
  Scenario Outline: verify_discussed_practitioner_report_option_in_practitioners_tab_lab_popup
    Given User select organization: "<Organization>"
    When Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And Verify "Report History" table displayed in practitioner tab
    And User click on the log icon in the Report History table of row : "1"
    And Wait to page load
    And Verify text on screen "What was discussed from this report ? (required)"
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    And Wait to page load
    And Verify text on screen "This section is intentionally blank"
    Then Select "Other Batch Follow-up" from Steps Performed ComboBox
    And Wait to page load
    And Verify text on screen "This section is intentionally blank"
    Examples:
      | Organization                         | NPI        |
      | Admin Portal Test Patient Org DEV    | 1750393690 |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser

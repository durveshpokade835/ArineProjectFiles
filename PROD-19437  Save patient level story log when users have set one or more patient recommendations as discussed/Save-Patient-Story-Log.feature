@PharmacistPortal @PatientStory
Feature: Save Story Logs for Discussed Patient Recommendations

 #    PROD - 19437
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
  Scenario Outline: Verify_confirmation_dialog_and_practitioner_story_log_generation_when_patient_recommendations_are_discussed
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
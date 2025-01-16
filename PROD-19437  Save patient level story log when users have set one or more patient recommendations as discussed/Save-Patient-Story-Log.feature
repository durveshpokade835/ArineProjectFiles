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
#    And Click on log action icon button from report history table
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Discussed Practitioner Report" from Steps Performed ComboBox
    And selects patient recommendations from the Report Discussion Selector table
    And clicks on the Log Action button and verify PopUp
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string1>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        | string1 | string2  | string3         | string4  |
      | Admin Portal Test Patient Org DEV | 1750393690 | Call    | Outbound | Medical Records | Answered |

  @Regression @Smoke
  Scenario Outline: Verify_no_confirmation_dialog_when_patient_recommendations_are_not_discussed
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
#    And Click on log action icon button from report history table
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Confirmed Fax Received" from Steps Performed ComboBox
    And clicks on the Log Action button and verify PopUp
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string1>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | NPI        | string1 | string2  | string3         | string4  |
      | Admin Portal Test Patient Org DEV | 1750393690 | Call    | Outbound | Medical Records | Answered |

  @Regression @Smoke
  Scenario Outline:Verify_confirmation_dialog_and_practitioner_story_log_generation_in_a_campaign_tab
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
#    And Click on log action icon button from report history table
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Discussed Practitioner Report" from Steps Performed ComboBox
    And selects patient recommendations from the Report Discussion Selector table
    And clicks on the Log Action button and verify PopUp
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string1>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      | CampaignDate    | RunDate    | NPI        | string1 | string2  | string3         | string4  |
      | Admin Portal Test Patient Org DEV | Test Campaign 1 | 05/11/2024 | 1750393690 | Call    | Outbound | Medical Records | Answered |

  @Regression @Smoke
  Scenario Outline: Verify_no_confirmation_dialog_in_campaign-based_workflow_when_recommendations_are_not_discussed
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    Then User select campaigns date as: "<CampaignDate>"
    And User select campaign run date as: "<RunDate>"
#    And Click on log action icon button from report history table
    And Click on log action button from report history table
    And Verify text on screen "Log New Action"
    Then User update log action with below information:
      | Action    | Type      | Stakeholder | Outcome   |
      | <string1> | <string2> | <string3>   | <string4> |
    Then Remove "Discussed Practitioner Report" from Steps Performed ComboBox
    Then Select "Confirmed Fax Received" from Steps Performed ComboBox
    And clicks on the Log Action button and verify PopUp
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    And Click on practitioner search icon
    Then Wait to page load
    And Click on button "Story" which is in campaigns button
    Then Verify newly created log action for "<string1>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string2>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string3>" in story tab for prescriber analytics feature
    Then Verify newly created log action for "<string4>" in story tab for prescriber analytics feature
    And Delete newly created practitioner timeline log action
    Examples:
      | Organization                      |CampaignDate    | RunDate    | string1 | string2  | string3         | string4  |
      | Admin Portal Test Patient Org DEV |Test Campaign 1 | 05/11/2024 | Call    | Outbound | Medical Records | Answered |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
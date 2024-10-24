@PharmacistPortal @PrescriberAnalytics @PAFeature
Feature: Verify Ascending and descending sort of date columns for Campaigns and Practitioners tab

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
  Scenario Outline: verify_descending_sort_of_date_columns_for_Practitioners
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And verify "Descending" for practitioners reported drps table column "<columnName>"
    Examples:
      | Organization                      | NPI        |columnName |
      | Admin Portal Test Patient Org DEV | 1750393690 |DoB       |

  @Regression @Smoke
  Scenario Outline: verify_descending_sort_of_date_columns_for_Campaigns
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    And User select campaigns date as: "<CampaignDate>"
    Then Wait to page load
    And User select campaign run date as: "<RunDate>"
    And verify "Descending" for providers report table column "<columnName>"
    Examples:
      | Organization | CampaignDate  | RunDate    |columnName|
      | ENG TEST     | 2023 Campaign | 03/22/2023 |Last Faxed|


  @Regression @Smoke
  Scenario Outline: verify_ascending_sort_of_date_columns_for_Practitioners
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And verify "Ascending" for practitioners reported drps table column "<columnName>"
    Examples:
      | Organization                      | NPI        |columnName |
      | Admin Portal Test Patient Org DEV | 1750393690 |DoB       |

  @Regression @Smoke
  Scenario Outline: verify_ascending_sort_of_date_columns_for_Campaigns
    Given User select organization: "<Organization>"
    And Click on campaigns tab
    And User select campaigns date as: "<CampaignDate>"
    Then Wait to page load
    And User select campaign run date as: "<RunDate>"
    And verify "Ascending" for providers report table column "<columnName>"
    Examples:
      | Organization | CampaignDate  | RunDate    |columnName|
      | ENG TEST     | 2023 Campaign | 03/22/2023 |Last Faxed|


  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
@PharmacistPortal @PrescriberAnalytics @PAFeature
Feature:Verify when the user selects a date range within a month for the Report Sent Date and Report Created Date filters in the Advanced Filter section of the Reported DRPs table, the Report Created and Report Sent columns display data between the selected date range.


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
  Scenario Outline: verify_same_month_date_range_discrepancy
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And Select reported drp table date filter "<searchFilterName>" as "<filterValueToVerify>" and click on "Apply" button
    Then the "<Column Name>" column in the DRPs table should display dates within the selected range

    Examples:
      | Organization                      | NPI        | searchFilterName    | filterValueToVerify       | Column Name    |
      | Admin Portal Test Patient Org DEV | 1750393690 | Report Created Date | 02 Jan 2024 - 30 Jan 2024 | Report Created |
      | Admin Portal Test Patient Org DEV | 1750393690 | Report Sent Date    | 02 Jan 2024 - 30 Jan 2024 | Report Sent    |


  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
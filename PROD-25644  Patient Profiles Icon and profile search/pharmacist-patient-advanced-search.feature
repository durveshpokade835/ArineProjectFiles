@PharmacistPortal @AdvancedSearch
Feature: Verify Pharmacist Layout Feature

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                               | password                                               | message |
      | $pharmacist-portal-non-adherance-delete.user1.username | $pharmacist-portal-non-adherance-delete.user1.password | success |

#  PROD-25643 :TC-01
  @Regression @Smoke
  Scenario Outline: Verify_results_of_status_column_in_the_patient_advanced_search
    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And User click on ADVANCE search
    Then Search patient by MBI: "<MBI>"
    And Verify "Status" column in search results includes:
      | ExpectedStatus |
      | Active         |
      | Deceased       |
      | Termed:        |
    Examples:
      | Organization                      | MBI         |
      | Admin Portal Test Patient Org DEV | 2PN2VH5JX97 |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
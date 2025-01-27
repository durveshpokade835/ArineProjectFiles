@PharmacistPortal @FaxProvider
Feature: Sending a report with a user-defined fax number

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
  Scenario Outline:Verify_the_request_payload_contains_the_manually_entered_fax_number
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
    And Click on first records fax icon from report sent table
    And Verify text on screen "Please select or enter the number to re-fax to"
    And Select "<Fax Number>" Fax Number to send a fax
    Then Enter "<Custom Fax Number>" custom fax number
    And Click on button: "Send Fax"
#    Then Verify "<string>" hard stop error popup
    And Click on the button present in pop-up: "OK"
    Examples:
      | Organization                                  | NPI        | Custom Fax Number | Fax Number     | string |
      | Test1 Department of Health and Human Services | 1386711034 | 111-111-1111      | otherFaxNumber |        |

  Scenario Outline: Verify_that_the_request_payload_updates_with_manually_entered_fax_numbers_after_switching_from_an_existing_fax_number
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Click on button "Follow Up" which is in campaigns button
    And Click on first records fax icon from report sent table
    And Verify text on screen "Please select or enter the number to re-fax to"
    And Select "<Fax Number 1>" Fax Number to send a fax
    Then Enter "<Custom Fax Number>" custom fax number
    And Select "<Fax Number 2>" Fax Number to send a fax
    And Select "<Fax Number 1>" Fax Number to send a fax
    And Click on button: "Send Fax"
#    Then Verify "<string>" hard stop error popup
    And Click on the button present in pop-up: "OK"
    Examples:
      | Organization                                  | NPI        | Custom Fax Number | Fax Number 1   | Fax Number 2  | string |
      | Test1 Department of Health and Human Services | 1386711034 | 1111111111        | otherFaxNumber | 132-132-14440 |        |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
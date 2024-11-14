@PharmacistPortal @MedicationTestCases @MedlistAndQualityTabsScenarios
Feature: Verify assign medication to the DRP functionality

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                   | password                                   | message |
      | $humana-report-verification.user1.username | $humana-report-verification.user1.password | success |

  @Regression @Smoke
  #PROD-28970 :TC-01
  Scenario Outline: Verify_that_appropriate_message_is_displayed_when_the_user_activates_a_single_medication_that_is_already_active
    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Click on tab: "Med List"
    And User selects single already active medicine
    Then Click on Active Selected Button
    And Verify "selected medication has already been activated" error message
    Then Click on tab: "Quality"
    And User selects single already active medicine
    Then Click on Active Selected Button
    And Verify "selected medication has already been activated" error message
    Examples:
      | searchString                         | Organization |
      | b2434d2f-59f8-4ea1-9cd9-05931aa3a3a0 | Humana Test  |

  @Regression @Smoke
    #PROD-28970 :TC-02
  Scenario Outline: Verify_that_appropriate_message_is_displayed_when_the_user_activates_all_the_medications_that_are_already_active
    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Click on tab: "Med List"
    And User selects all the already active medicines
    Then Click on Active Selected Button
    And Verify "selected medications have already been activated" error message
    Then Click on tab: "Quality"
    And User selects all the already active medicines
    Then Click on Active Selected Button
    And Verify "selected medications have already been activated" error message
    Examples:
      | searchString                         | Organization |
      | b2434d2f-59f8-4ea1-9cd9-05931aa3a3a0 | Humana Test  |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
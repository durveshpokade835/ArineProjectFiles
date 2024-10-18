@PharmacistPortal @nonAdherance
Feature: Verify Delete in non adherance feature

  @Setup
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                               | password                                               | message |
      | $pharmacist-portal-non-adherance-delete.user1.username | $pharmacist-portal-non-adherance-delete.user1.password | success |


  Scenario: verify visibility of selected elements in the dropDown menu
    When Click on Patient Tab
    And Search patient: "3620b636-5b8e-44b3-8461-130983b7dc4e"
    And Click on tab: "Med List"
    And Click on patient first medicine record
    And User clicks on dropDown in Medlist page and select "Adherent - filled through cash" option
#    And User clicks on "Select or Type..." dropDown in Medlist page and select "Adherent - filled through other insurance" option
    And Verify "Adherent - filled through cash" option in dropdown field present in Medlist tab
    And Verify "Adherent - filled through cash" option in dropdown list present in Medlist tab
    And Verify "Adherent - filled through cash" option is not visible in dropdown list present in Medlist tab

  @Setup
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser


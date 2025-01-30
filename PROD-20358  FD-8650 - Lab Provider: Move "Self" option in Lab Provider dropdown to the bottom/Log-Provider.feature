@PharmacistPortal @LogProvider
Feature: Lab Provider Dropdown Modification

      #PROD-20358  FD-8650
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
  Scenario Outline: Verify_the_Add_Lab_button_opens_the_modal_and_the_order_of_options_in_the_Provider_dropdown
    Given User select organization: "<Organization>"
    And Click on Patient Tab
    And Search patient: "<Patient Id>"
    And Click on tab: "Lab"
    And Click on add new lab icon
    And Verify create new lab popup
    Then Verify the "Provider" dropdown has following options
      |                                     |
      | Unknown                             |
      | TestDavid TestScheven null          |
      | TestZane TestKartchner null         |
      | TestNathaniel TestReyes null        |
      | TestSandra TestMahoney Cardiologist |
      | TestMichael TestAlloway null        |
      | undefined undefined null            |
      | TestAdam TestSaunter PCP            |
      | TestCandice TestLewis null          |
      | TestMatthew TestBell null           |
      | TestRaj TestBose null               |
      | Self                                |
    Examples:
      | Organization                      | Patient Id                           |
      | Admin Portal Test Patient Org DEV | 2b98a9c4-c8fb-4867-9f8a-240e28b9674e |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
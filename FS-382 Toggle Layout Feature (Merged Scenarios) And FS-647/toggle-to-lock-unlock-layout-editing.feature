@PharmacistPortal @VerifyPharmacistLayout
Feature: Verify Pharmacist Layout Feature

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                             | password                                             | message |
      | $toggle-to-lock-unlock-layout-editing.user1.username | $toggle-to-lock-unlock-layout-editing.user1.password | success |

  @Regression @Smoke
    #FS-382:TC-01
  Scenario: Verify_options_of_initials_dropdown
    Given User select organization: "ENG TEST"
    When User clicks on user's initials dropdown
    And Verify initials dropdown has option
      | Manage Access |
      | Layout        |
      | Sign Out      |

  @Regression @Smoke
    #FS-382:TC-02
  Scenario: Verify_options_of_manage_access_field
    Given User clicks on user's initials dropdown
    When User perform mouse hovering action on "Manage Access"
    Then Verify Manage Access has option
      | MFA Settings             |
      | Register SSO with GOOGLE |

  @Regression @Smoke
    #FS-382:TC-03
  Scenario Outline: Verify_options_of_layout_field
    Given User clicks on user's initials dropdown
    When User perform mouse hovering action on "Layout"
    Then Verify Layout has option "<Layout Options>" when Layout is "<Layout State>" and "Reset Layout"
    Examples:
      | Layout Options | Layout State |
      | Unlock Layout  | Locked       |
      | Lock Layout    | Unlocked     |

  @Regression @Smoke
    #FS-382:TC-04 and TC-06
  Scenario Outline: Verify_user_able_to_see_correct_visible_text_with_respect_to_active_layout_state
    Given User clicks on user's initials dropdown
    When User perform mouse hovering action on "Layout"
    Then The menu option should display "<Visible Text>" when Layout is "<Layout State>"
    Examples:
      | Visible Text  | Layout State |
      | Unlock Layout | Locked       |
      | Lock Layout   | Unlocked     |

  @Regression @Smoke
    #FS-382:TC-05
  Scenario: Verify_user_able_to_move_the_Layout_when_layout_is_unlocked
    When User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    And User clicks on "Unlock Layout" option
    Then The layout should be movable when unlocked

  @Regression @Smoke
    #FS-382:TC-07
  Scenario: Verify_Layout_is_locked_and_not_editable
    Given User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    When User clicks on "Lock Layout" option
    And User clicks on "Reset Layout" option
    Then The layout should not be movable when Locked

  @Regression @Smoke
    #FS-382:TC-08
  Scenario: Verify_Layout_elements_resets_when_reset_option_is_clicked
    Given User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    When User clicks on "Unlock Layout" option
    And User checks for initial layout position
    Then User moves the layout
    Then User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    And User clicks on "Reset Layout" option
    Then Verify Layout is reset to initial layout position

  @Regression @Smoke
    #FS-382:TC-09
  Scenario Outline: Verify_users_layout_changes_are_saved
    Given User select organization: "<Organization>"
    And Click on Tasks Tab
    And User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    And User clicks on "Unlock Layout" option
    And User checks for initial layout position
    Then User moves the layout
    Then User logout from the application
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    And User select organization: "<Organization>"
    And Click on Tasks Tab
    And Wait to page load
    Then Verify layout is in saved position
    Examples:
      | username                                             | password                                             | message | Organization |
      | $toggle-to-lock-unlock-layout-editing.user1.username | $toggle-to-lock-unlock-layout-editing.user1.password | success | ENG TEST     |

  @Regression @Smoke
    #FS-647: TC-01
  Scenario Outline:Verify_Portal_gets_stuck_on_cursor_when_changing_a_provider
    Given User select organization: "<Organization>"
    And Click on Patient Tab
    And Search patient: "<Patient Id>"
    And Click on tab: "Profile and Action Plan"
    Then Check the layout state
    And User add new medicine "<Medicine Name>"
    Then User Select the recently added medication "<Medicine Name>" and try to change the Prescriber "<Prescriber Option>" from the details pane
    Then Click on patient name link
    And User Check the cursor does not get stuck
    And User deletes newly Added Medicine "<Medicine Name>"
    Examples:
      | Organization          | Patient Id                           | Medicine Name | Prescriber Option |
      | UHC Level-Funded Test | 29020cce-e113-4e6c-8b01-eeee3c568939 | TESTOZOLE     | Self              |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
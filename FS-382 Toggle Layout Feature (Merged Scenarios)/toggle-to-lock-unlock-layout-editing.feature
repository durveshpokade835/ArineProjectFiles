@PharmacistPortal @VerifyPharmacistLayout
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

  @Regression @Smoke
    #FS-382:TC-01
  Scenario: Verify_that_the_user_can_see_the_following_options_in_the_upper_right_with_the_users_initials_1_Manage_Access_2_Layout_3_Sign_Out
    When User clicks on user's initials dropdown
    Then Verify that the user can see the options in the upper right "Manage Access, Layout, Sign Out"

  @Regression @Smoke
    #FS-382:TC-02
  Scenario: Verify_that_the_user_can_see_the_following_options_by_hovering_on_the_Manage_Access_field_1_MFA_Settings_2_Register_SSO_with_Google
    When User clicks on Manage Access field
    Then Verify that the user can see the options by hovering on the Manage Access field "MFA Settings" and "Register SSO with Google"

  @Regression @Smoke
    #FS-382:TC-03
  Scenario: Verify_that_the_user_can_see_the_following_options_by_hovering_on_the_Layout_field_1_Lock_or_Unlock_Layout_2_Reset_layout
#    When User clicks on user's initials dropdown
    When User perform mouse hovering action on "Layout"
    Then Verify that the user can see the options by hovering on the Layout field "Unlock Layout" and "Reset layout"

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
#    And User clicks on user's initials dropdown
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
      | username                                               | password                                               | message | Organization |
      | $pharmacist-portal-non-adherance-delete.user1.username | $pharmacist-portal-non-adherance-delete.user1.password | success | ENG TEST     |

  @Regression @Smoke
    #FS-647: TC-01
  Scenario Outline:Verify_Portal_gets_stuck_on_cursor_when_changing_a_provider
    Given User select organization: "<Organization>"
    And Click on Patient Tab
    And Search patient: "<Patient Id>"
    And Click on tab: "Profile and Action Plan"
    Then Check the layout state
    And User add new medicine
    Then User Select the recently added medication and try to change the Prescriber from the details pane
    And User Check the cursor does not get stuck
    Examples:
      | Organization          | Patient Id                           |
      | UHC Level-Funded Test | 29020cce-e113-4e6c-8b01-eeee3c568939 |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
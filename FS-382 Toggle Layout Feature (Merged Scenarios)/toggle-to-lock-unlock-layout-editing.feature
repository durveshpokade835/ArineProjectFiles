@PharmacistPortal @ToggleLayoutFeature
Feature: Toggle to lock/unlock layout editing
  This feature verifies that the 'Layout' menu option displays the correct lock/unlock status.

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
  Scenario: Verify that the user can see the following options in the upper right with the user’s initials 1.Manage Access,2.Layout,3.Sign Out
#    When User clicks on "user’s initials" dropDown
    When User clicks on the initials dropdown
    Then Verify that the user can see the options in the upper right "Manage Access, Layout, Sign Out"

  @Regression @Smoke
    #FS-382:TC-02
  Scenario: Verify that the user can see the following options by hovering on the Manage Access field: 1.MFA Settings, 2.Register SSO with Google
    When User clicks on Manage Access field
    Then Verify that the user can see the options by hovering on the Manage Access field "MFA Settings" and "Register SSO with Google"


  @Regression @Smoke
    #FS-382:TC-03
  Scenario: Verify that the user can see the following options by hovering on the 'Layout' field 1.Lock/Unlock Layout, 2.Reset layout
#    When User clicks on "user’s initials" dropDown
    And User clicks on "Layout" field
    Then Verify that the user can see the options by hovering on the Layout field "Unlock Layout" and "Reset layout"

  @Regression @Smoke
    #FS-382:TC-04 AND #FS-382:TC-06
  Scenario Outline: Verify_user_able_to_see_correct_visible_text_with_respect_to_active_layout_state
    When User clicks on the initials dropdown
    And User hovers over the "Layout" menu option
    Then The menu option should display "<Visible Text>" when Layout is "<Layout State>"
    Examples:
      | Visible Text  | Layout State |
      | Unlock Layout | Locked       |
      | Lock Layout   | Unlocked     |

  @Regression @Smoke
    #FS-382:TC-05
    Scenario: Verify_user_able_to_move_the_Layout_when_layout_is_unlocked
      When User clicks on the initials dropdown
      And User hovers over the "Layout" menu option
      And User clicks on the "Unlock Layout" button
      Then The layout should be movable when unlocked

  @Regression @Smoke
  Scenario: Verify_Layout_is_locked_and_not_editable
    Given User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    When User clicks on "Lock Layout" option
    And User clicks on "Reset Layout" option
    Then The layout should not be movable when Locked

  @Regression @Smoke
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
      | username                                            | password                                            | message | Organization |
      | $pharmacist-portal-non-adherance-delete.user1.username | $pharmacist-portal-non-adherance-delete.user1.password | success | ENG TEST     |


  @Setup
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
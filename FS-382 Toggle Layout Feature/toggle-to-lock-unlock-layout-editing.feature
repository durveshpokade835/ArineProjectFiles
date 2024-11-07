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
  Scenario Outline: Verify_user_able_to_see_correct_visible_text_with_respect_to_active_layout_state
    When User clicks on the initials dropdown
    And User hovers over the "Layout" menu option
    Then The menu option should display "<Visible Text>" when Layout is "<Layout State>"
    Examples:
      | Visible Text  | Layout State |
      | Unlock Layout | Locked       |
      | Lock Layout   | Unlocked     |

  @Regression @Smoke
    Scenario: Verify_user_able_to_move_the_Layout_when_layout_is_unlocked
      When User clicks on the initials dropdown
      And User hovers over the "Layout" menu option
      And User clicks on the "Unlock Layout" button
      Then The layout should be movable when unlocked

  @Setup
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
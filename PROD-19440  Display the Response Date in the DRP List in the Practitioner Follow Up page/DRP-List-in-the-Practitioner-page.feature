@PharmacistPortal @DRPList
Feature: Verify Search DRP functionality for Pharmacist user and Support user.

 #    PROD - 19440
  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                          | password                                          | message |
      | $DRP-List-in-the-Practitioner-page.user1.username | $DRP-List-in-the-Practitioner-page.user1.password | success |

  Scenario Outline: Verify_response_date_column_present_in_the_reported_DRPs_table_on_practitioners_page
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    Then Verify "Reported DRPs" table has column
      | Response Date |
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  Scenario Outline: Verify_Implementation_Status_update_does_not_modify_Response_Date_unless_specified
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    When User select practitioner "<PatientName1>" in practitioner tab
#    And User gets the initial value of "<Verification Value>" of "<PatientName1>" in practitioner tab
    And Get data "<columnName>" of "<PatientName1>"
    Then User clicks on "<DropDown Option>" dropDown and select "<Value>" option in practitioner tab
#    Then User clicks on "Select Action" dropDown and select "Edit Implementation Status" option and edit implementation status as: "<ImplementationStatus>"
    And Verify "<DropDown Option>" column is updated with "<Value>" for "<PatientName1>" practitioner in practitioner tab
    And Verify "<columnName>" of "<PatientName1>" value remains unchanged
#    And Verify implementation status column is updated with "<ImplementationStatus>" for "<Practitioner>" practitioner in campaign tab
    Examples:
      | NPI        | PatientName1       | DropDown Option       | Value       | columnName    | Verification Value |
      | 1750393690 | Miller Steven30410 | Implementation Status | Implemented | Response Date | Response Date      |

  Scenario Outline: Verify_Implementation_Status_update_via_bulk_action_does_not_modify_Response_Date_unless_specified
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And Select first "2" practitioners in practitioner tab and update its "<Option Value>" value from bulk action and verify it in table

#    And Select first "2" practitioners in campaigns tab and update its followup status "<FollowupStatus>" from bulk action and verify it in table
    Examples:
      | Organization                      | NPI        | Option Value |
      | Admin Portal Test Patient Org DEV | 1750393690 | Implemented  |

  Scenario Outline: Verify_response_method_selection_when_Response_Date_is_not_null
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    When User select practitioner "<PatientName1>" in practitioner tab
#    And User gets the initial value of "<Verification Value>" of "<PatientName1>" in practitioner tab
    And Get data "<columnName>" of "<PatientName1>"

    Examples:
      | Organization                      | NPI        | PatientName1       | columnName    |
      | Admin Portal Test Patient Org DEV | 1750393690 | Miller Steven30410 | Response Date |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
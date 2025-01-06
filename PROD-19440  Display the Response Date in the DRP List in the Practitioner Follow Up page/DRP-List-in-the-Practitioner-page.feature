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
    And User click on "Reset order" button to reset table columns ordering
    And User click on "Show all" button to show all table hidden columns
    Then Verify "Reported DRPs" table has column
      | Response Date |
    Examples:
      | Organization                      | NPI        |
      | Admin Portal Test Patient Org DEV | 1750393690 |

  Scenario Outline: Verify_Implementation_Status_update_does_not_modify_Response_Date_unless_specified
#    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
#    And User click on "Reset order" button to reset table columns ordering
#    And User click on "Show all" button to show all table hidden columns
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button

    When User selects practitioner "<PatientName1>" in practitioner tab
#    And Get data "<columnName>" for "<PatientName1>"
    And User gets the initial value of "<columnName>" for "<PatientName1>" in practitioner tab

    Then User updates "<DropDown Option>" dropdown to "<Value>" value in practitioner tab
    And Verify "<DropDown Option>" column is updated with "<Value>" for "<PatientName1>" in practitioner tab
#    And Verify "<columnName>" for "<PatientName1>" remains unchanged
    And User verifies initial value of "<columnName>" for "<PatientName1>" remains unchanged in practitioner tab

    When User selects practitioner "<PatientName1>" in practitioner tab
    And User update drps current response date from drps pane area

    Then Verify drps response date is updated as current date

    Then Verify "<columnName>" date column is updated as current date for "<PatientName1>" in practitioner tab

    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
    Examples:
      | Organization                      | NPI        | searchFilterName  | filterValue | PatientName1       |columnName    | DropDown Option       | Value       |
      | Admin Portal Test Patient Org DEV | 1750393690 | Patient Last Name | Steven30410 | Miller Steven30410 |Response Date | Implementation Status | Implemented |

  Scenario Outline: Verify_Implementation_Status_update_via_bulk_action_does_not_modify_Response_Date_unless_specified
#    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
#    And User click on "Reset order" button to reset table columns ordering
#    And User click on "Show all" button to show all table hidden columns
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    And Select first "2" practitioners in practitioner tab and update "<Option Value>" via bulk action and verify it in table
    Then User deselect all reported DRPs
    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button
#    And Select first "2" practitioners in campaigns tab and update its followup status "<FollowupStatus>" from bulk action and verify it in table
    Examples:
      | Organization                      | NPI        | Option Value | searchFilterName  | filterValue |
      | Admin Portal Test Patient Org DEV | 1750393690 | Implemented  | Patient Last Name | Steven      |

  Scenario Outline: Verify_response_method_selection_when_Response_Date_is_not_null
    Given User select organization: "<Organization>"
    And Click on Practitioners Tab
    And User search practitioner by npi: "<NPI>"
    Then Wait to page load
    And Verify the User able select the campaigns value as: "Test Campaign 1"
    And User click on "Reset order" button to reset table columns ordering
    And User click on "Show all" button to show all table hidden columns
    Then Select reported drp table filter "<searchFilterName>" as "<filterValue>" and click on "Apply" button
    When User selects practitioner "<PatientName1>" in practitioner tab
    Then Verify fields are editable in practioner Tab
      | Response Method |
    And User tries to change "Response Method" and verifies it

#    Then User updates "<DropDown Option>" dropdown to "<Value>" value in practitioner tab
#    Then User updates "<DropDown Option>" dropdown to "<Value2>" value in practitioner tab

    Then Remove all previous applied filters from the campaigns tab by clicking the "Clear Filters" button

    Examples:
      | Organization                      | NPI        | PatientName1       | searchFilterName  | filterValue | DropDown Option | Value  | Value2          |
      | Admin Portal Test Patient Org DEV | 1750393690 | Miller Steven30410 | Patient Last Name | Steven30410 | Response Method | Claims | Call (provider) |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
@PharmacistPortal @VerifyMTMReport
Feature: Verify Pharmacist Layout Feature

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                      | password                                      | message |
      | $pharmacist-MTM-patient-report.user1.username | $pharmacist-MTM-patient-report.user1.password | success |

  Scenario Outline: Verify_Patient_MTM_fields_read_only_while_draft_MTM_reports_exist.

    Given User select organization: "<Organization>"
    When Click on Patient Tab
    And Search patient: "<Patient Id>"

    And Click on tab: "Profile and Action Plan"
    And Click on button: "Rerun Analysis"
    And Click on tab: "Med List"
    And User update all medications sig text with different random sig
    And Click on tab: "Reports"
    And Click on button: "Existing Reports"
    Then User archive all existing reports
    And Click on tab: "Med List"
    Then Select all medication and discontinue and keep only first "2" medication active
    Then User update all medications condition value
    And Click on tab: "Reports"
    And Click on button: "Make MTM Reports"
    Then Select review type: "CMR"
    When User add new DRP record if not present on hardStop for report tab
      | PatientId    | ReportButtonName | DrugRelatedProblem | PatientAssessment | PatientRecommendation | ProviderAssessment          | ProviderRecommendation          |
      | <Patient Id> | Make MTM Reports | DRPForTest         | Test Assessment   | Test Recommendation   | Testing Provider Assessment | Testing Provider Recommendation |
    And Click on patient name link
    Then Click on patient allergies input
    And User update patient allergy: "Advair"
    And Verify patient allergy: "Advair"
    Then Click on patient allergies input
    And Verify popup "Edit Patient Allergies"
    And User update patient allergy: "NKDA"
    Then Select blank value from "<mtmOPTOutReason>" dropDown
    And User clicks on "<ImpairedDropDown>" dropDown and select "Unknown" value
    And User clicks on "<LTCDropDown>" dropDown and select "Unknown" value
    And User clicks on "<RecipientDropDown>" dropDown and select "02 – Beneficiary's prescriber" value
    And User update caregiver on cmr call with: "caregiver"

    Then Wait to page load

    And Click on tab: "Profile and Action Plan"
    And Click on button: "Rerun Analysis"
    And Click on tab: "Reports"
    And Click on button: "Make MTM Reports"
    Then Select review type: "CMR"
    And Click on button: "Generate Reports"
    Then Select current date as conversation from generate reports popup
    And Click on button "Generate" present in generate reports confirmation popup
    Then Wait to page load
    And Click on patient name link

    Then Verify fields are disabled and tooltip contains: "You cannot change this field when there are draft CMR patient letters. To edit, please delete or archive the draft CMR patient letters."
      | longTermCare     |
      | reviewType       |
      | impaired         |
      | rationale        |
      | cmrRecipientType |
      | careof           |
    Then Verify fields are disabled
      | practiceGroup |
      | optOutDate    |
    Then Verify fields are editable
      | address1      |
      | address2      |
      | city          |
      | zipcode       |
      | statecode     |
      | phone1        |
      | phone2        |
      | phone3        |
      | use1          |
      | use2          |
      | use3          |
      | email         |
      | vm1           |
      | vm2           |
      | vm3           |
      | vm4           |
      | patientStatus |
      | Allergies     |
      | optOut        |

    Then Click on complete CMR
    And Wait to page load
    And Click on button "Complete CMR" present in confirmation popUp
    And Click on button "OK" present in confirmation popUp
    And Click on button: "Existing Reports"
    Then User archive all existing reports

    And Click on patient name link
    And User clicks on "<ImpairedDropDown>" dropDown and select "Yes" value
    Then Verify user able to select rationale value as "BIMS Score" in "Please enter rationale" popup
    Then Verify fields are editable and no tooltip message is displayed
      | longTermCare | reviewType | impaired | rationale | cmrRecipientType | careof |
    Examples:
      | Organization         | Patient Id                           | mtmOPTOutReason | LTCDropDown  | ImpairedDropDown | RecipientDropDown | Message                           |
      | Priority Health test | fe2b1e12-76f6-4c8e-96a9-820e456cfc43 | optOut          | longTermCare | impaired         | cmrRecipientType  | The CMR has been marked completed |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser
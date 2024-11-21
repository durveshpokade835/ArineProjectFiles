@PharmacistPortal @PatientMultipleProfileSection
Feature: Verify Patients search multiple profile section Feature
#PROD-27505, PROD-25644
  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                         | password                                         | message |
      | $multiple-patient-profile-section.user1.username | $multiple-patient-profile-section.user1.password | success |

  @Regression @Smoke
  Scenario Outline: verify_patients_multiple_profile_icon_visibility
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Verify patient's "Name": "<Patient1Name>"
    Then Verify icon for multiple patient profile and verify the tooltip: "<tooltipMessage>"
    Examples:
      | searchString                         | Patient1Name | tooltipMessage                                        |
      | a67d600a-a676-483e-a17c-14440012c21f | Lucy Miller  | Click to see all available profiles for this patient. |

  @Regression @Smoke
  Scenario Outline: verify_patients_multiple_profile_icon_opens_when_click_on_it_and_it_shows_available_users
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Wait to page load
    Then Verify patient's "Name": "<Patient1Name>"
    Then Verify icon for multiple patient profile and verify the tooltip: "<tooltipMessage>"
    Then User click on patient profile icon and verify the popup heading text: "Patient Profile Selection: <Patient1Name>"
    And Click on user "<Patient2Name>" which is present on patient profile selection popup
    And Verify Patient Name: "<Patient2Name>"
    Then User click on patient profile icon and verify the popup heading text: "Patient Profile Selection: <Patient2Name>"
    And Click on user "<Patient1Name>" which is present on patient profile selection popup
    And Verify Patient Name: "<Patient1Name>"
    Examples:
      | searchString                         | Patient1Name | Patient2Name | tooltipMessage                                        |
      | a67d600a-a676-483e-a17c-14440012c21f | Lucy Miller  | Adam Miller  | Click to see all available profiles for this patient. |

  @Regression @Smoke
  Scenario Outline: verify_patients_multiple_profile_modal_closes_when_click_on_"CLOSE"_or_"X"_button
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Verify patient's "Name": "<Patient1Name>"
    Then Verify icon for multiple patient profile and verify the tooltip: "<tooltipMessage>"
    Then User click on patient profile icon and verify the popup heading text: "Patient Profile Selection: <Patient1Name>"
    And User closes the modal by clicking on "<Button>"
    Examples:
      | searchString                         | Patient1Name | tooltipMessage                                        | Button |
      | a67d600a-a676-483e-a17c-14440012c21f | Lucy Miller  | Click to see all available profiles for this patient. | Close  |
      | f6161e23-6300-4cbd-b1c3-c05ac80ff1e1 | Adam Miller  | Click to see all available profiles for this patient. | X      |

  @Regression @Smoke
  Scenario Outline: verify_patients_multiple_profile_icon_is_invisible
    When Click on Patient Tab
    And Search patient: "<searchString>"
    Then Verify patient's "Name": "<Patient1Name>"
    And Verify icon for multiple patient profile is not present
    Examples:
      | searchString                         | Patient1Name |
      | 8ddb5c27-5ac7-40ac-88e7-f03d93e995b0 | Steven Lopez |

  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser

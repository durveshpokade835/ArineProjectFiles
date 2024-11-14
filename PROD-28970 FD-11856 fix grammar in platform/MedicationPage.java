package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;


public class MedicationPage {

    private int activeElementCount;

    private static final String ACTIVE_MED_ICON = "//*[contains(@class,'MedDetailTable')]//tbody/tr[.//input[not(@disabled)] and not(.//span[contains(@class, 'discontinued')])]//*[contains(@class,'mantine-Checkbox-inner')]";
    private static final String ACTIVATE_SELECTED_MEDICATIONS = "//*[contains(@class,'iconRecoverBin')]";

//    private static final String ACTIVE_MEDICINE_ICON = "//table[contains(@class, 'MedDetailTable')]//tbody/tr[" +
//                                                        "contains(@class, 'medication-row') and " +                 // Optional: Filter for rows with a specific class (if any)
//                                                        ".//input[not(@disabled)] and " +                           // Ensure the input is not disabled
//                                                        "not(.//span[contains(@class, 'discontinued')])]" +         // Exclude rows with discontinued medicines
//                                                        "//span[contains(@class, 'mantine-Checkbox-inner')]";       // Finally, find the checkbox

    public void selectSingleActiveMedicine() throws AutomationException {
        try {
            // Fetch the list of active medicines
            List<WebElement> activeElements = driverUtil.getWebElements(ACTIVE_MED_ICON);

            // Verify there is at least one active medicine
            if (activeElements.isEmpty()) {
                throw new AutomationException("No active medicines found to select.");
            }

            // Click the first active medicine in the list
            activeElements.get(0).click();
            activeElementCount = 1;
            System.out.println("Selected a single active medicine.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Error selecting a single active medicine: " + e.getMessage());
        }
    }

    public void selectAlreadyActiveMedicines() throws AutomationException {
        try {
            // Fetch the list of all active medicines
            List<WebElement> activeElements = driverUtil.getWebElements(ACTIVE_MED_ICON);
            activeElementCount = activeElements.size();

            // Check if no active medicines are available
            if (activeElements.isEmpty()) {
                throw new AutomationException("No active medicines found to select.");
            }

            // Click each active medicine in the list
            for (WebElement activeEle : activeElements) {
                if (activeEle != null && activeEle.isEnabled()) {
                    activeEle.click();
                } else {
                    System.out.println("Skipping null or disabled element.");
                }
            }
            System.out.println("Selected all already active medicines.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Error selecting already active medicines: " + e.getMessage());
        }
    }

    public void clickActiveSelectedButton() throws AutomationException {
        try {
            // Fetch the "Activate Selected" button element
            WebElement activateSelectedMedButton = driverUtil.getWebElement(ACTIVATE_SELECTED_MEDICATIONS);

            // Verify that the button is found and clickable
            if (activateSelectedMedButton == null) {
                throw new AutomationException("Activate Selected Medication Button is not found.");
            }

            if (activateSelectedMedButton.isEnabled()) {
                activateSelectedMedButton.click();
                System.out.println("Activate Selected Button clicked successfully.");
            } else {
                throw new AutomationException("Activate Selected Medication Button is disabled.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Error clicking Activate Selected Button: " + e.getMessage());
        }
    }

    public void verifyMessage(String message) throws AutomationException {
        try {
            // Wait briefly and then for the page to load completely
            WebDriverUtil.waitForAWhile(1);
            driverUtil.waitForLoadingPage();

            // Locate the message popup based on the expected text
            String messageLocator = String.format("//*[@role='dialog' and .//*[contains(text(),'%s')]]", activeElementCount + " " + message);
            WebElement messagePopup = driverUtil.getWebElement(messageLocator);

            // Verify if the message popup is found
            if (messagePopup == null) {
                throw new AutomationException("Unable to find error message popup with text: " + activeElementCount + " " + message);
            }
            System.out.println("Message popup verified with text: " + activeElementCount + " " + message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Error verifying message popup: " + e.getMessage());
        }
    }
}
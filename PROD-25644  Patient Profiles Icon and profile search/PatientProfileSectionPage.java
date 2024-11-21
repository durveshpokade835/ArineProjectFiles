package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.PatientPage.GLOBAL_DETAILS_TAB_MAPPING;

public class PatientProfileSectionPage {

    CommonSteps common = new CommonSteps();

    //    private static final String MULTIPLE_PATIENT_PROFILE_ICON = "//*[contains(@class,'PatientBanner-__patientNameCell')]//..//*[contains(@class,'icon-tabler-users')]";
    private static final String MULTIPLE_PATIENT_PROFILE_ICON = "//*[contains(@class,'PatientBanner-__patientNameCell')]//..//*[contains(@class,'tabler-icon-users') or contains(@class,'icon-tabler-users')]";

    public void verifyMultiplePatientProfileIcon(String toolTipMessage) throws AutomationException {
        Actions action = new Actions(DriverFactory.drivers.get());
        WebElement multipleProfileIcon = driverUtil.getWebElement(MULTIPLE_PATIENT_PROFILE_ICON);
        if (multipleProfileIcon == null)
            throw new AutomationException("Multiple Patient Users Icon Not Visible In Patient info tables patient name cell");
        action.moveToElement(multipleProfileIcon).perform();
        common.verifyTextOnScreen(toolTipMessage);
    }

    public void verifyMultiplePatientProfilePopup(String popupTitle) throws AutomationException {
        Actions action = new Actions(DriverFactory.drivers.get());
        WebElement multipleProfileIcon = driverUtil.getWebElement(MULTIPLE_PATIENT_PROFILE_ICON);
        if (multipleProfileIcon == null)
            throw new AutomationException("Multiple Patient Users Icon Not Visible In Patient info tables patient name cell");
        action.moveToElement(multipleProfileIcon).perform();
        multipleProfileIcon.click();
        driverUtil.waitForLoadingPage();
        common.verifyTextOnScreen(popupTitle);
    }

    public void clickOnButton(String button) throws AutomationException {
        try {
            String buttonLocator = button.equalsIgnoreCase("Close")
                    ? "//*[contains(text(),'Close')]"
                    : "//*[contains(@class,'mantine-Modal-close')]";
            WebElement buttonElement = driverUtil.getWebElement(buttonLocator);
            if (buttonElement == null) {
                throw new AutomationException("Button '" + button + "' is not found on the page.");
            }
            buttonElement.click();
            System.out.println("Button '" + button + "' clicked successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Failed to click on the '" + button + "' button: " + e.getMessage());
        }
    }

    public void verifyMultiplePatientProfileIconVisibility() throws AutomationException {
        try {
            WebElement multipleProfileIcon = driverUtil.getWebElement(MULTIPLE_PATIENT_PROFILE_ICON);
            if (multipleProfileIcon != null && multipleProfileIcon.isDisplayed()) {
                throw new AutomationException("Multiple Patient Profile Icon is visible, but it should be hidden.");
            }
            System.out.println("Multiple Patient Profile Icon is not visible as expected.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Error while verifying Multiple Patient Profile Icon visibility: " + e.getMessage());
        }
    }
}

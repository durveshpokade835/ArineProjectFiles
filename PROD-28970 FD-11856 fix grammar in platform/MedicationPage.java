package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class MedicationPage {

//    private static final String SELECT_ICON ="//*[contains(@class,'MedDetailTable')]// tr[td//*[text()='TESTOZOLE']]//*[contains(@class,'mantine-Checkbox-inner')]";
//    private static final String ACTIVE_MED_ICON= "//*[contains(@class,'MedDetailTable')]// tr[td//input[not(@disabled)]]//*[contains(@class,'mantine-Checkbox-inner')]";
//    "//table[contains(@class,'MedDetailTable')]//tr[.//input[not(@disabled)] and not(.//span[contains(@class, 'discontinued')])]//*[contains(@class,'mantine-Checkbox-inner')]";
//    //*[contains(@class,'MedDetailTable')]//tr[.//input[not(@disabled)] and not(.//span[contains(@class, 'discontinued')])]//*[contains(@class,'mantine-Checkbox-inner') and not(position()=1)]
//    //*[contains(@class,'MedDetailTable')]// tr[(td//input[not(@disabled)]) and (td//span[not(contains(@class, 'discontinued'))])]//*[contains(@class,'mantine-Checkbox-inner')]


    private static final String ACTIVE_MED_ICON = "//*[contains(@class,'MedDetailTable')]//tbody/tr[.//input[not(@disabled)] and not(.//span[contains(@class, 'discontinued')])]//*[contains(@class,'mantine-Checkbox-inner')]";

    private static final String ACTIVE_MEDICINE_ICON = "//table[contains(@class, 'MedDetailTable')]//tbody/tr[" +
            "contains(@class, 'medication-row') and " +   // Optional: Filter for rows with a specific class (if any)
            ".//input[not(@disabled)] and " +            // Ensure the input is not disabled
            "not(.//span[contains(@class, 'discontinued')])]" +   // Exclude rows with discontinued medicines
            "//span[contains(@class, 'mantine-Checkbox-inner')]"; // Finally, find the checkbox

    private static final String ACTIVATE_SELECTED_MEDICATIONS = "//*[contains(@class,'iconRecoverBin')]";
    private int activeElementCount;

    public void selectAlreadyActiveMedicine() throws AutomationException {
        List<WebElement> ActiveElements = driverUtil.getWebElements(ACTIVE_MED_ICON);
        activeElementCount = ActiveElements.size();
        for (WebElement activeEle : ActiveElements) {
            activeEle.click();
        }
    }

    public void clickActiveSelectedButton() throws AutomationException {
        WebElement ActivateSelectedMedButton = driverUtil.getWebElement(ACTIVATE_SELECTED_MEDICATIONS);
        if (ActivateSelectedMedButton == null)
            throw new AutomationException("Activate Selected Medication Button is not found");
        ActivateSelectedMedButton.click();

    }
    public void verifyMessage(String message) throws AutomationException {
        WebDriverUtil.waitForAWhile(1);
        driverUtil.waitForLoadingPage();
        WebElement messagePopup = driverUtil.getWebElement(String.format("//*[@role='dialog' and .//*[contains(text(),\"%s\")]]",activeElementCount+" "+message));
        if(messagePopup==null)
            throw new AutomationException("Unable to find any error message popup with:"+activeElementCount+" "+message);
    }
}

package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class PatientStoryLogPage {
    CommonSteps common = new CommonSteps();
    public static final String LOG_ACTION_FIELDS_LOCATOR = "//*[contains(@class,'mantine-Stack-root')]//following::input[contains(@placeholder,'Select %s')]";
    public static final String LOG_ACTION_FIELDS_VALUES = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static final String PATIENT_RECOMMENDATION_FIELD = "//*[contains(text(),'Patient Recommendations')]/ following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select row')][1]";
    public static final String LOG_ACTION_BUTTON_LOCATOR = "//*[@type='button']//*[contains(text(),'Continue') or contains(text(),'%s')]";

    public void updateLogAction(DataTable dataTable) throws AutomationException {
        driverUtil.waitForLoadingPage();
        List<List<String>> data = dataTable.asLists(String.class);
        List<String> fields = data.get(0);
        List<String> values = data.get(1);
        if (fields.size() != values.size()) {
            throw new AutomationException("Mismatch between number of fields and values in the DataTable");
        }
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String value = values.get(i);
            WebElement inputField = driverUtil.getWebElement(String.format(LOG_ACTION_FIELDS_LOCATOR, field));
            if (inputField == null)
                throw new AutomationException("Unable to find input field for " + field);
            inputField.click();
            WebElement fieldValue = driverUtil.getWebElement(String.format(LOG_ACTION_FIELDS_VALUES, value));
            if (fieldValue == null)
                throw new AutomationException("Unable to find value for input field " + field);
            fieldValue.click();
        }
    }

    public void userSelectsPatientRecommendations() throws AutomationException {
        driverUtil.waitForLoadingPage();
        WebElement selectButton = driverUtil.getWebElement(PATIENT_RECOMMENDATION_FIELD);
        if (selectButton == null)
            throw new AutomationException("Unable to find select field for Patient Recommendation Table");
        if (!selectButton.isSelected()) {
            driverUtil.waitForElementToBeClickable(PATIENT_RECOMMENDATION_FIELD);
            selectButton.click();
        }
    }

    public void clickOnLogActionButton(String button, String verificationMsg) throws AutomationException {
        boolean isDiscussedPractitionerSelected = false;
        WebElement stepsPerformedValue = driverUtil.getWebElement("//*[contains(@class,'mantine-MultiSelect-value') and @value='Discussed Practitioner Report']");
        if (stepsPerformedValue != null) {
            isDiscussedPractitionerSelected = stepsPerformedValue.isDisplayed();
        }
        WebElement LogAction = driverUtil.getWebElement(String.format(LOG_ACTION_BUTTON_LOCATOR,button));
        if (LogAction == null)
            throw new AutomationException("Unable to find continue or Log Action Button");
        LogAction.click();
        verifyMessage(isDiscussedPractitionerSelected,verificationMsg);
    }

    public void verifyMessage(boolean isDiscussedPractitionerSelected, String verificationMsg) throws AutomationException {
        if (isDiscussedPractitionerSelected && popUpScreenVerifcation()) {
            CommonSteps.takeScreenshot();
            WebElement textField = driverUtil.getWebElement("//*[contains(@class,'mantine-Card-root')]/div[contains(@class,'mantine-Text-root')]");
            if (textField == null)
                throw new AutomationException("Unable to find Pop Up message Field");
            String ActualText = textField.getText();
            if (!ActualText.contains(verificationMsg))
                throw new AutomationException(String.format("Expected pop up message is %s but found %s", verificationMsg, ActualText));
            common.logInfo("Confirmation dialog Should displayed As Expected");
            WebElement confirmButton = driverUtil.getWebElement("//*[@type='button']//*[contains(text(),'Yes')]");
            if (confirmButton == null)
                throw new AutomationException("Unable to find confirmation button on popUp Screen");
            confirmButton.click();
            driverUtil.waitForLoadingPage();
        } else if (!isDiscussedPractitionerSelected && !popUpScreenVerifcation()) {
            CommonSteps.takeScreenshot();
            common.logInfo("System does not display a confirmation dialog as Expected");
            driverUtil.waitForLoadingPage();
        }
    }

    public boolean popUpScreenVerifcation() throws AutomationException {
        boolean isPopUp = false;
        CommonSteps.takeScreenshot();
        WebElement popUpScreen = driverUtil.getWebElement("//*[contains(@class,'mantine-Card-root')]");
        if (popUpScreen != null) {
             isPopUp = popUpScreen.isDisplayed();
        }
        return isPopUp;
    }
}

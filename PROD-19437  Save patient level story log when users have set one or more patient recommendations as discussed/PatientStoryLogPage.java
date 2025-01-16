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
    public static final String LOG_ACTION_BUTTON_LOCATOR = "//*[@type='button']//*[contains(text(),'Continue') or contains(text(),'Log Action')]";

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

    public void clickOnLogActionButton() throws AutomationException {
        boolean isDiscussedPractitionerSelected = false;
        WebElement stepsPerformedValue = driverUtil.getWebElement("//*[contains(@class,'mantine-MultiSelect-value') and @value='Discussed Practitioner Report']");
        if (stepsPerformedValue != null) {
            isDiscussedPractitionerSelected = stepsPerformedValue.isDisplayed();
        }
        WebElement LogAction = driverUtil.getWebElement(LOG_ACTION_BUTTON_LOCATOR);
        if (LogAction == null)
            throw new AutomationException("Unable to find continue or Log Action Button");
        LogAction.click();
        verifyMessage(isDiscussedPractitionerSelected);
    }

    public void verifyMessage(boolean isDiscussedPractitionerSelected) throws AutomationException {
        if (isDiscussedPractitionerSelected && popUpScreenVerifcation()) {
            CommonSteps.takeScreenshot();
            WebElement textField = driverUtil.getWebElement("//*[contains(@class,'mantine-Card-root')]/div[contains(@class,'mantine-Text-root')]");
            if (textField == null)
                throw new AutomationException("Unable to find Pop Up message Field");
            String ExpectedText = "Submitting this action will create story logs for the selected patients.";
            String ActualText = textField.getText();
            if (!ActualText.contains(ExpectedText))
                throw new AutomationException(String.format("Expected pop up message is %s but found %s", ExpectedText, ActualText));
            common.logInfo("Confirmation dialog Should displayed As Expected");
            System.out.println("Confirmation dialog is displayed when the user logs the Practitioner story, and the user has indicated that patient recommendations were discussed.");
            WebElement confirmButton = driverUtil.getWebElement("//*[@type='button']//*[contains(text(),'Yes')]");
            if (confirmButton == null)
                throw new AutomationException("Unable to find confirmation button on popUp Screen");
            confirmButton.click();
            driverUtil.waitForLoadingPage();
        } else if (!isDiscussedPractitionerSelected && !popUpScreenVerifcation()) {
            CommonSteps.takeScreenshot();
            common.logInfo("System does not display a confirmation dialog as Expected");
            System.out.println("System does not display a confirmation dialog when the user logs the Practitioner story, and the user has not indicated that patient recommendations were discussed.");
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

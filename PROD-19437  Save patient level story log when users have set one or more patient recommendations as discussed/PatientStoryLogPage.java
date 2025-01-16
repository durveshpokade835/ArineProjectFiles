package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;

public class PatientStoryLogPage {

//    public static final String LOG_ACTION_ICON_IN_REPORT_HISTORY_TABLE = "//*[text()='Report History']/..//..//..// table// tr[1]// td//..//*[contains(@class,'tabler-icon-file-pencil')]";
    public static final String LOG_ACTION_ICON_IN_REPORT_HISTORY_TABLE = "//*[contains(text(),'Report')]/..//..//..// table// tr[1]// td//..//*[contains(@class,'tabler-icon-file-pencil')]";
    //    public static final String LOG_ACTION_FIELDS_LOCATOR = "//*[contains(@class,'mantine-Stack-root')]//label[contains(text(),'%s')]/following::input[contains(@class,'mantine-Select-input')]";
    public static final String LOG_ACTION_FIELDS_LOCATOR = "//*[contains(@class,'mantine-Stack-root')]//following::input[contains(@placeholder,'Select %s')]";
    public static final String LOG_ACTION_FIELDS_VALUES = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    //    public static final String abc = "//*[contains(text(),'Patient Recommendations')]";
//    public static final String PATIENT_RECOMMENDATION_FIELD = "//*[contains(text(),'Patient Recommendations')]/following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select row')]";
    public static final String PATIENT_RECOMMENDATION_FIELD = "//*[contains(text(),'Patient Recommendations')]/ following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select row')][1]";
    public static final String LOG_ACTION_BUTTON_LOCATOR = "//*[@type='button']//*[contains(text(),'Continue') or contains(text(),'Log Action')]";

    public void clickOnLogActionIconFromReportHistoryTable() throws AutomationException {
        WebElement button = driverUtil.getWebElement(LOG_ACTION_ICON_IN_REPORT_HISTORY_TABLE);
        if (button == null)
            throw new AutomationException("Unable to find log Action button in reports History table or it might taking too long time to load!");
        button.click();
        waitForLoadingPage();
    }

    public void updateLogAction(DataTable dataTable) throws AutomationException {
        driverUtil.waitForLoadingPage();
        // Convert the DataTable to a list of lists
        List<List<String>> data = dataTable.asLists(String.class);
        // The first row contains the field names (keys)
        List<String> fields = data.get(0);
        // The second row contains the corresponding values
        List<String> values = data.get(1);
        if (fields.size() != values.size()) {
            throw new AutomationException("Mismatch between number of fields and values in the DataTable");
        }
        // Loop through each field and value
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
            //*[contains(text(),'Submitting this action will create story logs for the selected patients.')]
            WebElement textField = driverUtil.getWebElement("//*[contains(@class,'mantine-Card-root')]/div[contains(@class,'mantine-Text-root')]");
            if (textField == null)
                throw new AutomationException("Unable to find Pop Up message Field");
            String ExpectedText = "Submitting this action will create story logs for the selected patients.";
            String ActualText = textField.getText();
            if (!ActualText.contains(ExpectedText))
                throw new AutomationException(String.format("Expected pop up message is %s but found %s", ExpectedText, ActualText));
            System.out.println("confirmation dialog is displayed when the user logs the Practitioner story, and the user has indicated that patient recommendations were discussed.");
            WebElement confirmButton = driverUtil.getWebElement("//*[@type='button']//*[contains(text(),'Yes')]");
            if (confirmButton == null)
                throw new AutomationException("Unable to find confirmation button on popUp Screen");
            confirmButton.click();
            driverUtil.waitForLoadingPage();

        } else if (!isDiscussedPractitionerSelected && !popUpScreenVerifcation()) {
            System.out.println("the system does not display a confirmation dialog when the user logs the Practitioner story, and the user has not indicated that patient recommendations were discussed.");
            driverUtil.waitForLoadingPage();
        }
    }

    public boolean popUpScreenVerifcation() throws AutomationException {
        boolean isPopUp = false;
        WebElement popUpScreen = driverUtil.getWebElement("//*[contains(@class,'mantine-Card-root')]");
        if (popUpScreen != null) {
             isPopUp = popUpScreen.isDisplayed();
        }
        return isPopUp;
    }
}

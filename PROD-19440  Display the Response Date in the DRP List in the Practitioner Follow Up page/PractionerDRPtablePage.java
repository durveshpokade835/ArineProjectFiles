package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;
import static com.arine.automation.pages.BasePage.WAIT_5_SECOND;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PatientPage.scrollToTop;
import static com.arine.automation.pages.PractitionersPage.getReportedDRPTableColumnHeadingIndex;
import static com.arine.automation.pages.PrescriberAnalyticsPage.PRACTITIONER_FOLLOW_UP_STATUS_IN_CAMPAIGNS_TAB;
import static com.arine.automation.pages.PrescriberAnalyticsPage.getTableColumnHeadingIndex;

public class PractionerDRPtablePage {

    private static final String INPUT_FIELD_LOCATOR = "//div[contains(@class,'mantine-Grid-col')]/*[contains(text(),'%s')]/following::input[contains(@class,'mantine-Input-input')][1]";
    private static final String TABLE_ITEM_LOCATOR = "(//table//thead//..//tr[%s]//td[2]//*[contains(@class,'mantine-Text')])[1]";
    private static final String FIELD_LOCATOR = "//*[contains(@placeholder,'%s')]";
    private static final String DROPDOWN_OPTION_LOCATOR = "//div[contains(@class,'mantine-Select-item')]/*[text()='%s']";
    private static final String PRACTITIONER_LOCATOR = "//table//tbody//td//*[text()='%s']//following::td[contains(@class,'mantine')][1]";
    private static final String PRACTITIONER_SELECT_ICON = "//table//tbody//td//*[text()='%s']//preceding::div[contains(@class,'mantine-Checkbox-inner')][1]";
    public static final String PRACTITIONER_DETAIL_PANE_LOCATOR = "//*[contains(@class,'mantine-Grid-root')]//*[contains(text(),'Implementation Status')]";

    public static String INITIAL_VALUE;

//    public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
//        waitForLoadingPage();
//        ////table//tbody//td//*[text()='Miller Steven30410']//following::td[contains(@class,'mantine')][1]
//        WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR,practitioner));
//        WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll("//*[contains(@class,'mantine-Grid-root')]//*[contains(text(),'Implementation Status')]");
//        WebElement selectPractitioner = driverUtil.findElement("//table//tbody//td//*[text()='"+practitioner+"']//preceding::td[contains(@class,'mantine')][1]");
//        if (selectPractitioner == null)
//            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
//        if (!practitionerDetailPane.isDisplayed()) {
//            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR,practitioner));
//            selectPractitioner.click();
//        }
//    }
//public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
//    waitForLoadingPage();
//
//    // Locate the "selectPractitioner" button
//    WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
//    if (selectPractitioner == null) {
//        throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
//    }
//
//    // Locate the "practitionerDetailPane"
//    WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll("//*[contains(@class,'mantine-Grid-root')]//*[contains(text(),'Implementation Status')]");
//
//    // Click the "selectPractitioner" button
//    driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//    selectPractitioner.click();
//
//    // Check if "practitionerDetailPane" is displayed
//    if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
//        // If not displayed, click the "selectPractitioner" button again
//        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//        selectPractitioner.click();
//
//        // Check one more time if the pane is displayed after the second click
//        if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
//            throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
//        }
//    }
//}

//    public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
//        waitForLoadingPage();
//        // Locate the "selectPractitioner" button
//        WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
//        if (selectPractitioner == null) {
//            throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
//        }
//        // Click the "selectPractitioner" button
//        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//        selectPractitioner.click();
//
//        // Add an explicit wait for "practitionerDetailPane" to be visible
////        WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), Duration.ofSeconds(10)); // Adjust timeout as needed
//        // Locate the "practitionerDetailPane"
//        try {
//            driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
//        } catch (Exception e) {
//            // If not displayed after waiting, click the "selectPractitioner" button again
//            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//            selectPractitioner.click();
//
//            driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
//            WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);
//
//            // Check one more time if the pane is displayed after the second click
//            if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
//                throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
//            }
//        }
//    }

public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
    waitForLoadingPage();
    // Locate the "selectPractitioner" button
    WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
    if (selectPractitioner == null) {
        throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
    }

    // Click the "selectPractitioner" button
    driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
    selectPractitioner.click();

    // Check if "practitionerDetailPane" is displayed
    driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
    WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);

    // If the detail pane is not displayed, click the button again and re-check
    if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
        // Click the "selectPractitioner" button again
        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
        selectPractitioner.click();

        // Re-check for the practitioner detail pane
        driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
        practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);

        // If still not displayed, throw an exception
        if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
            throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
        }
    }
}

    public void userSelectPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
        waitForLoadingPage();
        ////table//tbody//td//*[text()='Miller Steven30410']//following::td[contains(@class,'mantine')][1]
//        WebElement selectPractitioner = driverUtil.findElement("//table//tbody//td//*[text()='"+practitioner+"']//following::td[contains(@class,'mantine')][1]");
        WebElement selectPractitioner = driverUtil.findElement(String.format(PRACTITIONER_SELECT_ICON,practitioner));
        if (selectPractitioner == null)
            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
        if (!selectPractitioner.isSelected()) {
            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_SELECT_ICON,practitioner));
            selectPractitioner.click();
        }
    }

//    public void userSelectPractitionerInCampaignTabTable(String practitioner) throws AutomationException {
//        waitForLoadingPage();
//        WebElement selectPractitioner = driverUtil.findElement("//table//tbody//td//*[text()='" + practitioner + "']//preceding::input[contains(@aria-label,'select row')][1]");
//        if (selectPractitioner == null)
//            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
//        if (!selectPractitioner.isSelected()) {
//            selectPractitioner.click();
//        }
//    }

    public void userClicksOnDropDownAndSelectsValue(String dropDownName, String value) throws AutomationException {
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        //div[contains(@class,'mantine-Grid-col')]/*[contains(text(),'Response Method')]/following::input[contains(@class,'mantine-Input-input')][1]
        WebElement selectDropdown = driverUtil.getWebElement(String.format(INPUT_FIELD_LOCATOR, dropDownName));
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: %s", dropDownName));
        selectDropdown.click();

        WebElement selectDropdownOption = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_LOCATOR, value));
        if (selectDropdownOption == null)
            throw new AutomationException(String.format("Unable to find %s option in DropDown %s", value, dropDownName));
        selectDropdownOption.click();
        waitForLoadingPage();

    }

    public void verifyUpdatedColumnStatusInPractitionerTable(String columnName, String value, String practitioner) throws AutomationException {
        waitForLoadingPage();
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        int columnIndex = getReportedDRPTableColumnHeadingIndex(columnName) - 2;
        WebElement selectedPractitioner = driverUtil.getWebElementAndScroll("//table//tbody//*[text()='" + practitioner + "']//following::td[" + columnIndex + "]");
        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " value: " + value + " in " + columnName);
        String valueInTable = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable ==" + valueInTable);

        if (!value.equals(valueInTable))
            throw new AutomationException(String.format("Expected Updated %s column for %S practitioner is %s but its shows: %s", columnName, practitioner, value, valueInTable));
    }

    public void getValueInPractitionerTable(String columnName, String practitioner) throws AutomationException {
        waitForLoadingPage();
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        int columnIndex = getReportedDRPTableColumnHeadingIndex(columnName) - 2;
        WebElement selectedPractitioner = driverUtil.getWebElementAndScroll("//table//tbody//*[text()='" + practitioner + "']//following::td[" + columnIndex + "]");
        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + columnName + " for " + practitioner);
        INITIAL_VALUE = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable ==" + INITIAL_VALUE);
//        return INITIAL_VALUE;
    }

    public void verifyBulkActionForMultipleRecords(int numberOfRecord, String followupStatus) throws AutomationException {
        WebElement practitioner = null;
        String practitionerName;
        //Update Status
        for (int i = 1; i <= numberOfRecord; i++) {
            practitioner = driverUtil.findElement(String.format(TABLE_ITEM_LOCATOR, i));
            waitForLoadingPage();
            practitionerName = practitioner.getText();
            // Select Searched Practitioner
//            userSelectPractitionerInCampaignTabTable(practitionerName);

            userSelectPractitionerInPractitionerTabTable(practitionerName);
        }
        userClicksOnDropDownAndEditFollowUpStatus("Select Action", "Edit Implementation Status", followupStatus);

        //Verify Status Updated
        for (int i = 1; i <= numberOfRecord; i++) {
            practitioner = driverUtil.findElement(String.format(TABLE_ITEM_LOCATOR, i));
            practitionerName = practitioner.getText();
            verifyUpdatedFollowUpStatusInFollowUpColumnInPractitioner(followupStatus, practitionerName);
        }
    }

    public boolean userClicksOnDropDownAndEditFollowUpStatus(String dropDownName, String value, String followUpOption) throws AutomationException {
        scrollToTop();
        WebElement selectDropdown = driverUtil.getWebElement("//*[text()='" + dropDownName + "']");
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: %s", dropDownName));
        selectDropdown.click();

        WebElement selectDropdownOption = driverUtil.getWebElement("//*[text()='" + value + "']");
        if (selectDropdownOption == null)
            throw new AutomationException(String.format("Unable to find %s option in DropDown %s", value, dropDownName));
        selectDropdownOption.click();

        WebElement editFollowUpOption = driverUtil.getWebElement(String.format("//*[text()='Select Implementation Status']/..// button/..//*[text()='%s']", followUpOption));
        if (editFollowUpOption == null)
            throw new AutomationException(String.format("Unable to find %s edit follow up option in DropDown %s", value, dropDownName));
        editFollowUpOption.click();
        waitForLoadingPage();
        takeScreenshot();
        return true;
    }

    public void verifyUpdatedFollowUpStatusInFollowUpColumnInPractitioner(String followUpStatus, String practitioner) throws AutomationException {
        waitForLoadingPage();
        scrollToTop();
        waitForLoadingPage();

//        int followUpStatusColumnIndex = getTableColumnHeadingIndex("Response Date") - 20;
        int followUpStatusColumnIndex = getReportedDRPTableColumnHeadingIndex("Response Date") - 1;
        int followUpStatusCellIndex = followUpStatusColumnIndex - 3;
        System.out.println("followUpStatusCellIndex== " + followUpStatusCellIndex);
        WebElement selectedPractitioner = driverUtil.getWebElement(String.format("(//table// tbody//*[text()='%s']/..//..// following-sibling::td[%s])[1]", practitioner, followUpStatusCellIndex));

        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " followup status in followup status column");
        String followUpStatusInTable = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable =====" + followUpStatusInTable);

        if (!followUpStatus.equals(followUpStatusInTable))
            throw new AutomationException(String.format("Expected Updated Follow Up Status for %S practitioner is %s but its shows: %s", practitioner, followUpStatus, followUpStatusInTable));
    }

//    public void isFieldDisabled(String fieldName) throws AutomationException {
//        WebElement element = driverUtil.getWebElement(FIELD_LOCATOR);
//        if (element == null)
//            throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");
//
//        String disabledAttribute = element.getAttribute("disabled");
//
//        if (disabledAttribute == null) {
//            throw new AutomationException("Field '" + fieldName + "' is not disabled.");
//        }
//    }


    public void isFieldEnabled(DataTable dataTable) throws AutomationException {

        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
//            driverUtil.waitForElementToBeClickable(String.format(FIELD_LOCATOR, fieldName));
            driverUtil.waitForElement(By.xpath(String.format(FIELD_LOCATOR, fieldName)),5);
            WebElement element = driverUtil.getWebElement(String.format(FIELD_LOCATOR, fieldName));
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute != null) {
                throw new AutomationException("Field '" + fieldName + "' is disabled.");
            }
        }
    }
}

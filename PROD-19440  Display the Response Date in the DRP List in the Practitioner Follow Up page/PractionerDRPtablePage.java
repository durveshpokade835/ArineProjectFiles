package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;
import static com.arine.automation.pages.BasePage.WAIT_5_SECOND;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PatientPage.scrollToTop;
import static com.arine.automation.pages.PractitionersPage.getReportedDRPTableColumnHeadingIndex;
import static com.arine.automation.pages.PrescriberAnalyticsPage.*;


public class PractionerDRPtablePage {

    public static final String PRACTITIONER_DETAIL_PANE_LOCATOR = "//*[contains(@class,'mantine-Grid-root')]//*[contains(text(),'Implementation Status')]";
    private static final String INPUT_FIELD_LOCATOR = "//div[contains(@class,'mantine-Grid-col')]/*[contains(text(),'%s')]/following::input[contains(@class,'mantine-Input-input')][1]";
    private static final String TABLE_ITEM_LOCATOR = "(//table//thead//..//tr[%s]//td[2]//*[contains(@class,'mantine-Text')])[1]";
    private static final String FIELD_LOCATOR = "//*[contains(@placeholder,'%s')]";
    private static final String DROPDOWN_OPTION_LOCATOR = "//div[contains(@class,'mantine-Select-item')]/*[text()='%s']";
    private static final String PRACTITIONER_LOCATOR = "//table//tbody//td//*[text()='%s']//following::td[contains(@class,'mantine')][1]";
    private static final String PRACTITIONER_SELECT_ICON = "//table//tbody//td//*[text()='%s']//preceding::div[contains(@class,'mantine-Checkbox-inner')][1]";
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

    /// /        WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), Duration.ofSeconds(10)); // Adjust timeout as needed
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
//    public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
//        waitForLoadingPage();
//        // Locate the "selectPractitioner" button
//        WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
//        if (selectPractitioner == null) {
//            throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
//        }
//
//        // Click the "selectPractitioner" button
//        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//        selectPractitioner.click();
//
//        // Check if "practitionerDetailPane" is displayed
////        driverUtil.waitForLoadingPage();
//        WebDriverUtil.waitForInvisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
////        WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
////        driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
//        WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);
//
//        // If the detail pane is not displayed, click the button again and re-check
//        if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
//            // Click the "selectPractitioner" button again
//            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
//            selectPractitioner.click();
//
//            // Re-check for the practitioner detail pane
////            driverUtil.waitForLoadingPage();
////            WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
//            WebDriverUtil.waitForInvisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
////            driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
//            practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);
//
//            // If still not displayed, throw an exception
//            if (practitionerDetailPane == null || !practitionerDetailPane.isDisplayed()) {
//                throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
//            }
//        }
//    }

public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
    waitForLoadingPage();

    // Locate the practitioner in the table
    WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
    if (selectPractitioner == null) {
        throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
    }

    // Click the practitioner element
    driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
    selectPractitioner.click();

    // Validate the practitioner detail pane visibility
    if (!isPractitionerDetailPaneDisplayed()) {
        // Retry clicking the practitioner if the pane is not displayed
        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
        selectPractitioner.click();

        if (!isPractitionerDetailPaneDisplayed()) {
            throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
        }
    }
}

    /**
     * Checks if the practitioner detail pane is displayed.
     *
     * @return true if the pane is visible; false otherwise.
     */
    private boolean isPractitionerDetailPaneDisplayed() throws AutomationException {
        WebDriverUtil.waitForInvisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
        WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);
        return practitionerDetailPane != null && practitionerDetailPane.isDisplayed();
    }

    public void userSelectPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
        waitForLoadingPage();
        ////table//tbody//td//*[text()='Miller Steven30410']//following::td[contains(@class,'mantine')][1]
//        WebElement selectPractitioner = driverUtil.findElement("//table//tbody//td//*[text()='"+practitioner+"']//following::td[contains(@class,'mantine')][1]");
        WebElement selectPractitioner = driverUtil.findElement(String.format(PRACTITIONER_SELECT_ICON, practitioner));
        if (selectPractitioner == null)
            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
        if (!selectPractitioner.isSelected()) {
            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_SELECT_ICON, practitioner));
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
            driverUtil.waitForElement(By.xpath(String.format(FIELD_LOCATOR, fieldName)), 5);
            WebElement element = driverUtil.getWebElement(String.format(FIELD_LOCATOR, fieldName));
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute != null) {
                throw new AutomationException("Field '" + fieldName + "' is disabled.");
            }
        }
    }

    public void verifyUpdatedDateStatusInPractitionerTable(String columnName, String practitioner) throws AutomationException {


        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MM/DD/YYYY");
        System.out.println("Current Date= " + currentDateFormat);
        String currentDate = LocalDateTime.now().format(currentDateFormat);
        System.out.println("Current Date= " + currentDateFormat);

        //----------------------------------------------------------------
        waitForLoadingPage();
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        int columnIndex = getReportedDRPTableColumnHeadingIndex(columnName) - 2;
        WebElement selectedPractitioner = driverUtil.getWebElementAndScroll("//table//tbody//*[text()='" + practitioner + "']//following::td[" + columnIndex + "]");
        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " value in " + columnName);

        String responseDatevalueInTable = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable ==" + responseDatevalueInTable);


        if (!currentDate.equals(responseDatevalueInTable))
            throw new AutomationException(String.format("Expected Updated %s column for %S practitioner is %s but its shows: %s", columnName, practitioner, currentDate, responseDatevalueInTable));
    }


    //    public void changeValueOfField(String fieldName) throws AutomationException {
//
//        driverUtil.waitForElement(By.xpath(String.format(FIELD_LOCATOR, fieldName)),5);
//        WebElement field = driverUtil.getWebElementAndScroll(String.format(FIELD_LOCATOR, fieldName));
//
//        String initialValueOfField = field.getText();
//        System.out.println("Initial Value of "+fieldName+" == "+initialValueOfField);
//
//        field.click();
//        List<WebElement> options = driverUtil.getWebElements("//*[contains(@role,'option')]");
//
//
//    }
    public void changeValueOfField(String fieldName) throws AutomationException {
        // Wait for the field element to appear
        driverUtil.waitForElement(By.xpath(String.format(INPUT_FIELD_LOCATOR, fieldName)), 5);

        // Get the field WebElement and scroll to it
        WebElement field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");

        // Get the initial value of the field
        String originalFieldValue = field.getAttribute("value");
        System.out.println("Initial Value of " + fieldName + " == " + originalFieldValue);

        // Click on the field to display the dropdown options
        field.click();

        // Get all dropdown options
        List<WebElement> options = driverUtil.getWebElements("//*[contains(@role,'option')]");
        if (options == null || options.isEmpty()) {
            throw new AutomationException("No options available for the field: " + fieldName);
        }

        // Select a new value that is not equal to the original value
        String newFieldValue = null;
        for (WebElement option : options) {
            String optionText = option.getText();
            if (!optionText.equals(originalFieldValue)) {
                newFieldValue = optionText;
                option.click();
                break;
            }
        }

        // If no new value was found, throw an exception
        if (newFieldValue == null) {
            throw new AutomationException("No alternate value found to select for the field: " + fieldName);
        }

        System.out.println("New Value of " + fieldName + " == " + newFieldValue);

        // Verify the new value has been updated successfully
        WebDriverUtil.waitForInvisibleElement(By.xpath("//*[contains(@class,'mantine-Overlay-root')]"), 5);
//        WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
//        driverUtil.waitForLoadingPage();
//        driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);

        field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");

        String updatedValue = field.getAttribute("value");
        if (!updatedValue.equals(newFieldValue)) {
            throw new AutomationException("Field value did not update successfully. Expected: " + newFieldValue + ", Actual: " + updatedValue);
        }

        // Reset the field value back to the original value
        field.click();
        options = driverUtil.getWebElements("//*[contains(@role,'option')]");
        if (options == null || options.isEmpty()) {
            throw new AutomationException("No options available for the field: " + fieldName);
        }
        for (WebElement option : options) {
            if (option.getText().equals(originalFieldValue)) {
                option.click();
                break;
            }
        }

        // Verify the field value has been reset to the original value
//        driverUtil.waitForCondition(() -> field.getText().equals(originalFieldValue), 5);
        WebDriverUtil.waitForInvisibleElement(By.xpath("//*[contains(@class,'mantine-Overlay-root')]"), 5);
//        WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
//        driverUtil.waitForLoadingPage();
//        driverUtil.waitForElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);

        field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");

        String resetValue = field.getAttribute("value");
        if (!resetValue.equals(originalFieldValue)) {
            throw new AutomationException("Field value did not reset successfully. Expected: " + originalFieldValue + ", Actual: " + resetValue);
        }

        System.out.println("Field value reset to original: " + resetValue);
    }

//    public void userUpdateDRPsCurrentResponseDateFromDRPsPaneArea() throws AutomationException, ParseException {
//        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
//        DateTimeFormatter priorDateDayFormat = DateTimeFormatter.ofPattern("d");
//        String priorDate = LocalDateTime.now().minusDays(1).format(currentDateFormat);
//        String priorDateDay = LocalDateTime.now().minusDays(1).format(priorDateDayFormat);
//        DateTimeFormatter currentMonthAndYearFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
//        String currentMonthYear = LocalDateTime.now().format(currentMonthAndYearFormat);
//
//        WebElement responseDateInput = driverUtil.getWebElementAndScroll(RESPONSE_DATE_INPUT);
//        if (responseDateInput == null)
//            throw new AutomationException(String.format("Response date input not displayed on screen"));
//        responseDateInput.click();
//
//        WebElement currentMonthInputElement = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
//        Date currentMonthInput = new SimpleDateFormat("MMMM yyyy").parse(currentMonthInputElement.getText());
//        if (currentMonthInput == null)
//            throw new AutomationException("Current Month and Year is not being displayed in advance task filter popup!");
//        String currentMonthInputElements = currentMonthInputElement.getText();
//
//        System.out.println("currentMonthInputElements == "+ currentMonthInputElements);
//
//        Date expectedDateInMonths = new SimpleDateFormat("MMMM yyyy").parse(priorDate);
//        System.out.println("expectedDateInMonths== "+ expectedDateInMonths);
//        System.out.println("currentMonthYear== "+ currentMonthYear);
//
//        while (!currentMonthYear.equals(currentMonthInputElements)) {
//            if (expectedDateInMonths.before(currentMonthInput)) {
//                WebElement previousMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-previous='true']");
//                if (previousMonthYearChangeButton == null)
//                    throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
//                previousMonthYearChangeButton.click();
//            }
//
//            else if(expectedDateInMonths.after(currentMonthInput)){
//                WebElement NextMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
//                if (NextMonthYearChangeButton == null)
//                    throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
//                NextMonthYearChangeButton.click();
//            }
//            break;
//        }
//        driverUtil.waitForLoadingPage();
//        WebElement priorDayDate = driverUtil.getWebElement("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='"+priorDateDay+"']");
//        if (priorDayDate == null)
//            throw new AutomationException("Unable to locate current date in calendar!");
//        driverUtil.clickUsingJavaScript("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='"+priorDateDay+"']");
//        driverUtil.waitForLoadingPage();
//        responseDateInput = driverUtil.getWebElement(RESPONSE_DATE_INPUT);
//        String responseDateInputValue = responseDateInput.getAttribute("value");
//        System.out.println("responseDateInputValue= "+responseDateInputValue);
//        Date expectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(LocalDateTime.now().format(currentDateFormat));
//        Date currentDateInput = new SimpleDateFormat("MMMM d, yyyy").parse(responseDateInputValue);
//
//        if (!currentDateInput.before(expectedDate))
//            throw new AutomationException(String.format("Response date is not changed '"+priorDate+"' but we expect it should be changed"));
//    }

    public void userUpdateDRPsCurrentResponseDateFromDRPsPaneArea() throws AutomationException, ParseException {
        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter currentDayFormat = DateTimeFormatter.ofPattern("d");
        String currentDate = LocalDateTime.now().format(currentDateFormat);
        String currentDay = LocalDateTime.now().format(currentDayFormat);
        DateTimeFormatter currentMonthAndYearFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
        String currentMonthYear = LocalDateTime.now().format(currentMonthAndYearFormat);

        WebElement responseDateInput = driverUtil.getWebElementAndScroll(RESPONSE_DATE_INPUT);
        if (responseDateInput == null) {
            throw new AutomationException("Response date input not displayed on screen");
        }
        responseDateInput.click();

        WebElement currentMonthInputElement = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
        if (currentMonthInputElement == null) {
            throw new AutomationException("Current Month and Year is not being displayed in calendar popup!");
        }
        String displayedMonthYear = currentMonthInputElement.getText();

        // Navigate to the correct month and year if needed
        while (!currentMonthYear.equals(displayedMonthYear)) {
            WebElement nextMonthYearButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
            if (nextMonthYearButton == null) {
                throw new AutomationException("Next Month button is not displayed or is disabled!");
            }
            nextMonthYearButton.click();
            driverUtil.waitForLoadingPage();
            displayedMonthYear = currentMonthInputElement.getText();
        }

        // Select the current day in the calendar
        WebElement currentDayElement = driverUtil.getWebElement("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='" + currentDay + "']");
        if (currentDayElement == null) {
            throw new AutomationException("Unable to locate the current date in the calendar!");
        }
        driverUtil.clickUsingJavaScript("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='" + currentDay + "']");
        driverUtil.waitForLoadingPage();

        // Validate the selected date
        responseDateInput = driverUtil.getWebElement(RESPONSE_DATE_INPUT);
        String responseDateInputValue = responseDateInput.getAttribute("value");
        System.out.println("responseDateInputValue = " + responseDateInputValue);

        Date expectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(currentDate);
        Date selectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(responseDateInputValue);

        if (!selectedDate.equals(expectedDate)) {
            throw new AutomationException(String.format("Response date is not changed to '%s' as expected.", currentDate));
        }
    }
}

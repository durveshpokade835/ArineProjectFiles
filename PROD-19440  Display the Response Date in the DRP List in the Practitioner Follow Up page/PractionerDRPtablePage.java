package com.arine.automation.pages;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;
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

public void userClicksPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
    waitForLoadingPage();
    WebElement selectPractitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_LOCATOR, practitioner));
    if (selectPractitioner == null) {
        throw new AutomationException(String.format("Unable to find practitioner select box: %s", practitioner));
    }
    driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
    selectPractitioner.click();
    if (!isPractitionerDetailPaneDisplayed()) {
        driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_LOCATOR, practitioner));
        selectPractitioner.click();
        if (!isPractitionerDetailPaneDisplayed()) {
            throw new AutomationException("Practitioner detail pane is not displayed even after the second click.");
        }
    }
}
    private boolean isPractitionerDetailPaneDisplayed() throws AutomationException {
        WebDriverUtil.waitForInvisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR));
        WebElement practitionerDetailPane = driverUtil.getWebElementAndScroll(PRACTITIONER_DETAIL_PANE_LOCATOR);
        return practitionerDetailPane != null && practitionerDetailPane.isDisplayed();
    }

    public void userSelectPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
        waitForLoadingPage();
        WebElement selectPractitioner = driverUtil.findElement(String.format(PRACTITIONER_SELECT_ICON, practitioner));
        if (selectPractitioner == null)
            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
        if (!selectPractitioner.isSelected()) {
            driverUtil.waitForElementToBeClickable(String.format(PRACTITIONER_SELECT_ICON, practitioner));
            selectPractitioner.click();
        }
    }

    public void userClicksOnDropDownAndSelectsValue(String dropDownName, String value) throws AutomationException {
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
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
    }

    public void verifyBulkActionForMultipleRecords(int numberOfRecord, String followupStatus) throws AutomationException {
        WebElement practitioner = null;
        String practitionerName;
        for (int i = 1; i <= numberOfRecord; i++) {
            practitioner = driverUtil.findElement(String.format(TABLE_ITEM_LOCATOR, i));
            waitForLoadingPage();
            practitionerName = practitioner.getText();
            userSelectPractitionerInPractitionerTabTable(practitionerName);
        }
        userClicksOnDropDownAndEditFollowUpStatus("Select Action", "Edit Implementation Status", followupStatus);
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

    public void isFieldEnabled(DataTable dataTable) throws AutomationException {
        List<String> fieldNames = dataTable.asList();
        for (String fieldName : fieldNames) {
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

    public void changeValueOfField(String fieldName) throws AutomationException {
        driverUtil.waitForElement(By.xpath(String.format(INPUT_FIELD_LOCATOR, fieldName)), 5);
        WebElement field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");
        String originalFieldValue = field.getAttribute("value");
        System.out.println("Initial Value of " + fieldName + " == " + originalFieldValue);
        field.click();

        List<WebElement> options = driverUtil.getWebElements("//*[contains(@role,'option')]");
        if (options == null || options.isEmpty()) {
            throw new AutomationException("No options available for the field: " + fieldName);
        }
        String newFieldValue = null;
        for (WebElement option : options) {
            String optionText = option.getText();
            if (!optionText.equals(originalFieldValue)) {
                newFieldValue = optionText;
                option.click();
                break;
            }
        }
        if (newFieldValue == null) {
            throw new AutomationException("No alternate value found to select for the field: " + fieldName);
        }
        System.out.println("New Value of " + fieldName + " == " + newFieldValue);
        WebDriverUtil.waitForInvisibleElement(By.xpath("//*[contains(@class,'mantine-Overlay-root')]"), 5);
        WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);

        field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");
        String updatedValue = field.getAttribute("value");
        if (!updatedValue.equals(newFieldValue)) {
            throw new AutomationException("Field value did not update successfully. Expected: " + newFieldValue + ", Actual: " + updatedValue);
        }
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
        WebDriverUtil.waitForInvisibleElement(By.xpath("//*[contains(@class,'mantine-Overlay-root')]"), 5);
        WebDriverUtil.waitForVisibleElement(By.xpath(PRACTITIONER_DETAIL_PANE_LOCATOR), 10);
        field = driverUtil.getWebElementAndScroll(String.format(INPUT_FIELD_LOCATOR, fieldName));
        if (field == null)
            throw new AutomationException("Unable to find " + fieldName + " field in DRP detail pane");
        String resetValue = field.getAttribute("value");
        if (!resetValue.equals(originalFieldValue)) {
            throw new AutomationException("Field value did not reset successfully. Expected: " + originalFieldValue + ", Actual: " + resetValue);
        }
        System.out.println("Field value reset to original: " + resetValue);
    }

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
        while (!currentMonthYear.equals(displayedMonthYear)) {
            WebElement nextMonthYearButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
            if (nextMonthYearButton == null) {
                throw new AutomationException("Next Month button is not displayed or is disabled!");
            }
            nextMonthYearButton.click();
            driverUtil.waitForLoadingPage();
            displayedMonthYear = currentMonthInputElement.getText();
        }
        WebElement currentDayElement = driverUtil.getWebElement("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='" + currentDay + "']");
        if (currentDayElement == null) {
            throw new AutomationException("Unable to locate the current date in the calendar!");
        }
        driverUtil.clickUsingJavaScript("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='" + currentDay + "']");
        driverUtil.waitForLoadingPage();
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

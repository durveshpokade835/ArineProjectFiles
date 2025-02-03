package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import com.arine.automation.util.WebDriverUtil;
import com.google.common.collect.Ordering;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PrescriberAnalyticsPage extends PatientPage {
    public static final String REPORTS_TABLE_IN_GLOBAL_CAMPAIGNS_TAB = "//*[text()='Reports:']//..//../..//descendant::table";
    public static final String CAMPAIGNS_TAB_BUTTONS = "//button/..//*[text()='%s']";
    public static final String REPORTS_TABLE_COLUMNS_HEADING_NAME = "//*[contains(@class,'TableHeadCell') and @title='%s']";
    //    public static final String HIDE_SHOW_COLUM_BUTTON = "//button[@aria-label='Show/Hide columns']";
    public static final String HIDE_SHOW_COLUM_BUTTON = "//button[@aria-label='Show/Hide columns' and not(@aria-expanded='true')]";
    public static final String HIDE_SHOW_ICON_TABLE_COLUM_BUTTON = "//*[text()='%s']//..//..//button[@aria-label='Show/Hide columns' and not(@aria-expanded='true')]";
    public static final String COLUMN_OPTIONS_HIDE_SHOW_BUTTON = "//*[text()='%s']/..//preceding-sibling::*[contains(@class,'mantine-Switch-track')]/../input";
    public static final String HIDE_SHOW_TOGGLE_BUTTON = "//*[text()='%s']/..//preceding-sibling::*[contains(@class,'mantine-Switch-track')]";
    public static final String SELECT_ALL_PROVIDER = "//th//input[contains(@class,'Checkbox') and contains(@aria-label,'select all')]";
    public static String CAMPAIGNS_RUN_SELECT_DROPDOWN = "//input[contains(@class,'Select-input') and @placeholder='Select Run']";
    public static String CAMPAIGNS_SELECT_DROPDOWN = "//input[contains(@class,'Select-input') and @placeholder='Select Campaign']";
    public static String CAMPAIGNS_SELECT_OPTION_IN_DROPDOWN = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static String CAMPAIGNS_RUN_SELECT_OPTION_IN_DROPDOWN = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static String COHORT_SIZE = "//*[contains(text(),'Cohort Size:')]//..//*[contains(@class,'campaignCohortSize')]";
    public static final String PRACTITIONER_NAME_CELL = "//table//tbody//*[text()='%s']";
    public static final String SELECTED_ACTIVE_GLOBAL_TAB = "//*[contains(@class,'activeButton')]//ancestor::*[text()='%s']";
    public static final String CAMPAIGNS_PRACTITIONER_RECORD = "//table//tbody//tr[.//*[text()='%s']]";
    public static final String COMMENT_INPUT = "//*[contains(text(),'Add Practitioner Comment')]//following::*[contains(@class,'Textarea-input')]";
    public static final String PDF_ICON_IN_CAMPAIGNS_TAB_REPORTS_TABLE = "//table//tbody//td//*[text()='%s']//preceding::*[contains(@class,'CampaignsReport-__clickableDiv')]";
//    public static final String PDF_ICON_IN_CAMPAIGNS_TAB_REPORTS_TABLE = "//table//tbody//td//*[text()='%s']//preceding::*[contains(@class,'tabler-file-description')]";
    public static final String LOG_ICON_IN_CAMPAIGNS_TAB_REPORTS_TABLE = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String REPORT_PDF_VIEWER_IN_CAMPAIGNS_TAB = "//*[contains(@class,'mantine-Modal-content')]";
    public static final String PRACTITIONER_ADDRESS_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_FOLLOW_UP_STATUS_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_PHONE_NUMBER_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_PERSONAL_INFO_INPUT = "//*[contains(@class,'PractitionerInput')]//input[@name='%s']";
    public static final String PRACTITIONER_FOLLOW_UP_STATUS_SELECT = "//select[@name='followUpStatus']";
    public static final String PRACTITIONER_PATIENT_COUNT_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_DRP_COUNT_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_LAST_FAX_NUMBER_IN_CAMPAIGNS_TAB = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_LAST_FAXED_DATE_COLUMN = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_QUIET_STATUS_COLUMN = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_DO_NOT_FAX_STATUS_COLUMN = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_LAST_CONTACTED_COLUMN = "//table//tbody//*[text()='%s']/..//..//following-sibling::td[%s]";
    public static final String PRACTITIONER_TIMELINE_FIRST_RECORD_DELETE = "(//*[contains(@class,'PractitionerStoryTable') and //*[text()='Practitioner Timeline']]/..//table)//tr[1]/td[last()]/*[1]";
    public static final String DELETE_PRACTITIONER_LOG_ACTION_CONFIRMATION_POPUP_DELETE_BUTTON = "//button/..//*[text()='Delete']";
    public static final String CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON = "//*[contains(@class,'tabler-icon-search') or contains(@class,'tabler-icon-list-search')]";
    public static final String EDIT_FOLLOWUP_OPTION = "//*[text()='Select Follow Up Status']/..//button/..//*[text()='%s']";
    public static final String CAMPAIGNS_ADVANCE_FILTER_INPUT = "//*[text()='%s']//following-sibling::*/input[contains(@placeholder,'%s')]";
    public static final String CAMPAIGNS_ADVANCE_FILTER_DROPDOWN = "//*[text()='%s']//following-sibling::*//input[contains(@placeholder,'%s')]";
    public static final String CAMPAIGNS_ADVANCE_FILTER_DATE_SELECTOR = "//*[text()='%s']//following-sibling::*//button";
    public static final String CAMPAIGNS_ADVANCE_FILTER_DROPDOWN_OPTION = "//*[@role='option' and text()='%s']";
    public static final String REPORT_TABLE_COLUMN_HEADING_NAMES = "//table//thead//..//*[contains(@class,'TableHeadCell-Content-Labels')]//div[contains(@class,'TableHeadCell-Content-Wrapper')]";
    public static final String REPORT_TABLE_COLUMN_DATA = "//table//thead//..//td[%s]";
    public static final String ADVANCE_FILTERED_OPTIONS = "//*[contains(@class,'styles-modules_badgeGroup')]/div";
    public static final String COLUMN_ACTIONS_ELEMENTS = "//*[contains(@class,'Menu-dropdown')]//button//..//*[text()='%s']";
    public static final String PDF_ICON_IN_REPORT_HISTORY_TABLE = "//*[text()='Report History']/..//..//..// table// tr[1]// td[7]// descendant::*[contains(@class,'tabler-icon-file-description')]";
    public static final String REPORT_HISTORY_TABLE_COLUMN_HEADING_NAMES = "//*[text()='Report History']//..//..//..//*[contains(@class,'TableHeadCell-Content-Labels')]//div";
    public static final String COLUMN_FIRST_VALUE_IN_REPORT_HISTORY_TABLE = "//*[text() = 'Report History']/../..//parent::*//*[contains(@class,'Table-root mantine')]// tr[1]// td[%s]";
    public static final String VERIFY_PRACTITIONER_TIMELINE_STORY_FIRST_RECORD = "(//*[contains(@class,'PractitionerStoryTable') and //*[text()='Practitioner Timeline']]/..//table)//tr[1]/td[contains(text(),'%s')]";
    public static final String PRACTITIONER_TASK_TABLE_HEADER = "//*[contains(@class,'tableHeader') and text()='Practitioner Tasks']";
    public static final String PRACTITIONER_TIMELINE_TABLE_HEADER = "//*[contains(@class,'tableHeader') and text()='Practitioner Timeline']";
    public static final String PRACTITIONER_LOG_ACTION_DELETE_ICON = "//*[contains(@class,'PractitionerStoryTable-__iconBin')]";
    public static final String ALL_OPEN_DRPS_OPTIONS_ALERT_ICON = "//*[text()='All Open DRPs']//..//following::*[contains(@class,'tabler-icon-alert')]";
    public static final String ALL_OPEN_DRP_OPTION_FOR_FOLLOW_UP_FAX = "//*[text()='Which DRPS?']//..//descendant::*//*[contains(text(),'%s')]//..//../parent::*//..//input";
    public static final String SELECTED_DRP_OPTION_FOR_FOLLOW_UP_FAX = "//*[text()='Which DRPS?']//..//descendant::*//*[contains(text(),'%s')]//../parent::*//..//input";
    public static final String REPORT_TYPE_OPTION_FOR_FOLLOW_UP_FAX = "//*[text()='What type of report?']//..//descendant::*//*[contains(text(),'%s')]//..//..//parent::*//input";
    public static final String FAX_INPUT_NUMBER="//*[contains(@class,'mantine-TextInput-input') and @placeholder='Enter a fax number...']";
    public static final String SELECT_ALL_REPORTED_DRPS="//th//input[contains(@class,'Checkbox') and contains(@aria-label,'Toggle select all')]";
    public static final String IMPLEMENTATION_STATUS_DROPDOWN="//*[text()='Implementation Status']/..//..//child::input[contains(@class,'Select-input')]";
    public static final String IMPLEMENTATION_STATUS_OPTION_IN_DROPDOWN="//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static final String RESPONSE_DATE_INPUT="//*[text()='Response Date']//following::input[contains(@class,'DateInput-input')]";
    public static final String FUTURE_RESPONSE_DATE="//*[contains(@class,'DateInput-monthCell')]//..//button[@data-today='true']//../following-sibling::*//button";
    public static final String COLUMN_HEADING_NAME="//*[contains(@class,'TableHeadCell-Content-Labels')]//div[contains(@class,'TableHeadCell-Content-Wrapper')]/..//*[text()='%s']";
    public static final String APPLIED_ADVANCE_FILTERED_OPTIONS="//*[contains(@class,'styles-modules_badgeGroup')]/div[contains(text(),'%s')]";
    public static final String OPTIONS_IN_ADVANCE_FILTERED_POPUP="//*[@role='dialog']/..//*[text()='Advanced Filters']//following::*//label[text()='%s']";
    public static final String TABS_DROPDOWN_XPATH = "//button[@type='button']/..//*[text()='%s']";
    public static final String BUTTON_OTIONS_IN_GLOBAL_TAB_DROPDOWN = "//*[@role='menuitem']/..//*[text()='%s']";
    public static final String FAX_TOGGLE="//*[contains(@class,'LandingPage-components-iconComponents-fax')]";
    public static final String RUN_DROPDOWN_OPTIONS="//*[contains(@class,'mantine-Select-item') and @role='option']";
    public static final String COMBOBOX_BUTTON = "//*[@placeholder='Select Step(s) Performed']";
//    public static final String SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR = "//input[contains(@title,'%s')]";
    public static final String SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR = "//*[contains(text(),'Report')]// following::table// thead//input[contains(@title,'%s')]";

    Actions actions = new Actions(DriverFactory.drivers.get());
    String expectedLastFaxedDate1 = "08/09/2023";
    String expectedLastFaxedDate2 = "24/09/2023";
    String toggleOffButtonBlueColor = "#5c99cf";
    String toggleOffButtonGrayColor = "#e9ecef";
    String alertIconGrayColor = "#808080";

    public static Map<String, String> PRACTITIONER_DETAILS_INPUT_MAPPING = new HashMap<>();

    static {
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Address 1", "address1");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Address 2", "address2");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("City", "city");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("State", "statecode");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Zip Code", "zipcode");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Preferred Phone", "phone1");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Second Phone", "phone2");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("Fax", "faxValue");
        PRACTITIONER_DETAILS_INPUT_MAPPING.put("E-mail", "email");
    }

    public void clickOnButtonPresentInCampaignsTab(String text) throws AutomationException {
        waitForLoadingPage();
        WebElement button = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_TAB_BUTTONS, text));
        if (button == null)
            throw new AutomationException(String.format("Unable to find %s button on patient tab or it might taking too long time to load!", text));
        button.click();
        waitForLoadingPage();
    }

    public void clickOnButtonToShowAllHiddenColumnsInReportsTable(String text) throws AutomationException {
        clickOnColumnShowHideButton();
//        WebElement button = driverUtil.getWebElementAndScroll("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        WebElement button = driverUtil.getElementUsingJavascript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        if (button != null)
            driverUtil.clickUsingJavaScript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
//            button.click();
        Actions actions = new Actions(DriverFactory.drivers.get());
        WebDriverUtil.waitForAWhile(3);
        actions.sendKeys(Keys.ESCAPE).build().perform();
        closeShowHidePopup();
        waitForLoadingPage();
    }

    public void verifyDropDownInputDisplayed(String dropDown) throws AutomationException {
        WebElement element = driverUtil.findElement("//*[contains(text(),'" + dropDown + "')]/..//input[contains(@aria-label,'" + dropDown.toLowerCase() + "')]");
        if (element == null)
            throw new AutomationException("Unable to find dropDown: " + dropDown + " on screen!");
        takeScreenshot();
    }


    public void closeShowHidePopup() throws AutomationException {
        WebElement openedShowHideColumnIcon = driverUtil.getWebElement("//button[@aria-label='Show/Hide columns' and @aria-expanded='true']");
        if(openedShowHideColumnIcon != null)
            openedShowHideColumnIcon.click();
    }


    public void verifyReportsTableInGlobalCampaignsTab() throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElement(REPORTS_TABLE_IN_GLOBAL_CAMPAIGNS_TAB);
        if (element == null)
            throw new AutomationException("Reports table not displayed in global reports tab");
    }

    public void verifyColumnAvailableInTheReportsTableList(List<String> columnName) throws AutomationException {
        WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
        if (filterOn != null) {
            filterOn.click();
        }
        for (String columnNamesInTable : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(REPORTS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element == null)
                throw new AutomationException(String.format("column %s not displayed in list table", columnNamesInTable));
        }
    }

    public static void clickOnColumnShowHideButton() throws AutomationException {
        waitForLoadingPage();
        scrollToTop();
        WebElement hideShowButton = driverUtil.getWebElement(HIDE_SHOW_COLUM_BUTTON);
        if (hideShowButton == null)
            throw new AutomationException(String.format("Hide Show button not displayed for reports table"));
        hideShowButton.click();
    }

    public void verifyUserAbleToHideColumnAvailableInTheReportsTable(List<String> columnName) throws AutomationException {
        clickOnColumnShowHideButton();
        for (String columnNamesInTable : columnName) {
            WebElement hideShowButtonOptionButton = driverUtil.findElement(String.format(COLUMN_OPTIONS_HIDE_SHOW_BUTTON, columnNamesInTable, 10));
            if (hideShowButtonOptionButton == null)
                throw new AutomationException(String.format("Hide Show toggle button not displayed for " + columnNamesInTable + " column name"));
            if (hideShowButtonOptionButton.isSelected()) {
                WebElement toggleOnOffButton = driverUtil.findElement(String.format(HIDE_SHOW_TOGGLE_BUTTON, columnNamesInTable, 10));
//                toggleOnOffButton.click();
                driverUtil.clickUsingJavaScript(String.format(HIDE_SHOW_TOGGLE_BUTTON, columnNamesInTable));

                String toggleOffButtonColor = toggleOnOffButton.getCssValue("background-color");
                String toggleOffButtonColorHexForm = Color.fromString(toggleOffButtonColor).asHex();
                System.out.println("toggleOffButtonColorHexForm= "+toggleOffButtonColorHexForm);
                if(!toggleOffButtonColorHexForm.equalsIgnoreCase(toggleOffButtonGrayColor))
                    throw new AutomationException(String.format("Off toggle button colour should show " + toggleOffButtonGrayColor + " But its actually shows "+toggleOffButtonColorHexForm));
            }
        }
        for (String columnNamesInTable : columnName) {
            Actions actions = new Actions(DriverFactory.drivers.get());
            actions.sendKeys(Keys.ESCAPE).build().perform();
            waitForLoadingPage();
            WebElement element = driverUtil.getWebElement(String.format(REPORTS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element != null)
                throw new AutomationException(String.format("column %s displayed in list table but it should be hide", columnNamesInTable));
        }
    }

    public void verifyUserNotAbleToSeeColumnAvailableInTheReportsTable(List<String> columnName) throws AutomationException {
        for (String columnNamesInTable : columnName) {
            Actions actions = new Actions(DriverFactory.drivers.get());
            actions.sendKeys(Keys.ESCAPE).build().perform();
            waitForLoadingPage();
            WebElement element = driverUtil.getWebElement(String.format(REPORTS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element != null)
                throw new AutomationException(String.format("column %s displayed in list table but it should be hide", columnNamesInTable));
        }
    }

    public void verifyUserAbleToShowColumnAvailableInTheReportsTable(List<String> columnName) throws AutomationException {
        clickOnColumnShowHideButton();
        for (String columnNamesInTable : columnName) {
            WebElement hideShowButtonOptionButton = driverUtil.findElement(String.format(COLUMN_OPTIONS_HIDE_SHOW_BUTTON, columnNamesInTable, 10));
            if (hideShowButtonOptionButton == null)
                throw new AutomationException(String.format("Hide Show button not displayed for " + columnNamesInTable + " column name"));

            if (!hideShowButtonOptionButton.isSelected()) {
                WebElement toggleOnOffButton = driverUtil.findElement(String.format(HIDE_SHOW_TOGGLE_BUTTON, columnNamesInTable, 10));
                if (toggleOnOffButton == null)
                    throw new AutomationException(String.format("Hide Show toggle button not displayed for " + columnNamesInTable + " column name"));
                toggleOnOffButton.click();

                String toggleOnButtonColor = toggleOnOffButton.getCssValue("background-color");
                String toggleOnButtonColorHexForm = Color.fromString(toggleOnButtonColor).asHex();
                System.out.println("toggleOnButtonColorHexForm= "+toggleOnButtonColorHexForm);
                if(!toggleOnButtonColorHexForm.equalsIgnoreCase(toggleOffButtonBlueColor))
                    throw new AutomationException(String.format("On toggle button colour should show " + toggleOffButtonBlueColor + " But its actually shows "+toggleOnButtonColorHexForm));
            }
        }
        for (String columnNamesInTable : columnName) {
            Actions actions = new Actions(DriverFactory.drivers.get());
            actions.sendKeys(Keys.ESCAPE).build().perform();
            waitForLoadingPage();
            WebElement element = driverUtil.getWebElement(String.format(REPORTS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element == null)
                throw new AutomationException(String.format("column %s not displayed in list table", columnNamesInTable));
        }
    }

    public void selectCampaignDate(String campaignDate) throws AutomationException {
        waitForLoadingPage();
        WebElement campaignButton = driverUtil.getWebElement(CAMPAIGNS_SELECT_DROPDOWN);
        if (campaignButton == null)
            throw new AutomationException(String.format("Campaigns dropdown not displayed on screen"));
        campaignButton.click();

        WebElement campaignDateElement = driverUtil.getWebElement(String.format(CAMPAIGNS_SELECT_OPTION_IN_DROPDOWN, campaignDate));
        if (campaignDateElement == null)
            throw new AutomationException(String.format("%s Campaigns date not present in campaigns dropdown", campaignButton));
        campaignDateElement.click();
    }

    public void selectCampaignRunDate(String campaignDate) throws AutomationException {
        waitForLoadingPage();
        WebElement campaignButton = driverUtil.getWebElement(CAMPAIGNS_RUN_SELECT_DROPDOWN);
        if (campaignButton == null)
            throw new AutomationException(String.format("Campaigns dropdown not displayed on screen"));
        campaignButton.click();

        WebElement campaignDateElement = driverUtil.getWebElement(String.format(CAMPAIGNS_RUN_SELECT_OPTION_IN_DROPDOWN, campaignDate));
        if (campaignDateElement == null)
            throw new AutomationException(String.format("%s Campaigns date not present in campaigns dropdown", campaignButton));
        campaignDateElement.click();
        waitForLoadingPage();
    }

    public void userSelectPractitionerInCampaignTabTable(String practitioner) throws AutomationException {
        waitForLoadingPage();
        WebElement selectPractitioner = driverUtil.findElement("//table//tbody//td//*[text()='" + practitioner + "']//preceding::input[contains(@aria-label,'select row')][1]");
        if (selectPractitioner == null)
            throw new AutomationException(String.format("Unable to practitioner select box: %s", practitioner));
        if (!selectPractitioner.isSelected()) {
            selectPractitioner.click();
        }
    }

    public void verifyCohortSizeDisplayedInCampaignsTab() throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElement(COHORT_SIZE);
        if (element == null)
            throw new AutomationException("Cohort size option not displayed in global campaigns tab");
    }

    public boolean clicksOnDropDownAndSelectValue(String dropDownName, String value) throws AutomationException {
        WebElement selectDropdown = driverUtil.getWebElementAndScroll("//*[text()='" + dropDownName + "']");
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: %s", dropDownName));
        selectDropdown.click();

        WebElement selectDropdownOption = driverUtil.getWebElementAndScroll("//*[text()='" + value + "']");
        if (selectDropdownOption == null)
            throw new AutomationException(String.format("Unable to find %s option in DropDown %s", value, dropDownName));
        selectDropdownOption.click();
        takeScreenshot();
        waitForLoadingPage();
        return true;
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

        WebElement editFollowUpOption = driverUtil.getWebElement(String.format(EDIT_FOLLOWUP_OPTION, followUpOption));
        if (editFollowUpOption == null)
            throw new AutomationException(String.format("Unable to find %s edit follow up option in DropDown %s", value, dropDownName));
        editFollowUpOption.click();
        waitForLoadingPage();
        takeScreenshot();
        return true;
    }

    public void verifyUserAbleToEnablePractitionerSearchFiler(String practitioner, String columnName) throws AutomationException {
        WebElement filterOff = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments-off')]");
        if (filterOff != null) {
            filterOff.click();
            WebElement searchColumnFilterElement = driverUtil.findElement(String.format(SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR, columnName));
            if (searchColumnFilterElement == null)
                throw new AutomationException("Unable to find " + columnName + " column search Filter");
            searchColumnFilterElement.click();
            searchColumnFilterElement.clear();
            searchColumnFilterElement.sendKeys(practitioner);
        } else {
            WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
            if (filterOn != null) {
                WebElement searchColumnFilterElement = driverUtil.findElement(String.format(SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR, columnName));
                if (searchColumnFilterElement == null)
                    throw new AutomationException("Unable to find " + columnName + " column search Filter");
                searchColumnFilterElement.click();
                searchColumnFilterElement.clear();
                searchColumnFilterElement.sendKeys(practitioner);
            } else
                throw new AutomationException("Unable to find column filter mode and action disable enable button on campaigns tab");
        }
        takeScreenshot();
        WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
        if (filterOn != null) {
            filterOn.click();
        }
    }

    public void verifyUpdatedFollowUpStatusInFollowUpColumnInCampaign(String followUpStatus, String practitioner) throws AutomationException {
        waitForLoadingPage();
        scrollToTop();
        waitForLoadingPage();

        int followUpStatusColumnIndex = getTableColumnHeadingIndex("Follow Up Status");
        int followUpStatusCellIndex = followUpStatusColumnIndex - 3;
        System.out.println("followUpStatusCellIndex== " + followUpStatusCellIndex);
        WebElement selectedPractitioner = driverUtil.getWebElement(String.format(PRACTITIONER_FOLLOW_UP_STATUS_IN_CAMPAIGNS_TAB, practitioner, followUpStatusCellIndex));

        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " followup status in followup status column");
        String followUpStatusInTable = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable =====" + followUpStatusInTable);

        if (!followUpStatus.equals(followUpStatusInTable))
            throw new AutomationException(String.format("Expected Updated Follow Up Status for %S practitioner is %s but its shows: %s", practitioner, followUpStatus, followUpStatusInTable));
    }

    public void userDeselectAllPractitioners() throws AutomationException {
        waitForLoadingPage();
        WebElement selectDeselectAllCheckbox = driverUtil.getWebElement(SELECT_ALL_PROVIDER);
        if (selectDeselectAllCheckbox == null) {
            throw new AutomationException("Unable to find select all practitioner select box in table");
        }
        if (selectDeselectAllCheckbox.isSelected()) {
            selectDeselectAllCheckbox.click();
            if (selectDeselectAllCheckbox.isSelected())
                throw new AutomationException("All Practitioners were Selected but it should not selected");
        }
    }

    public void selectMultiplePractitioners(DataTable dataTable) throws AutomationException {
        List<String> nameOfPractitionersToSelect = dataTable.asList(String.class);
        for (String nameOfPractitionerToSelect : nameOfPractitionersToSelect) {
            waitForLoadingPage();
            // Search Practitioner For filtered Out Practitioner
            verifyUserAbleToEnablePractitionerSearchFiler(nameOfPractitionerToSelect, "Recipient");
            // Select Searched Practitioner
            userSelectPractitionerInCampaignTabTable(nameOfPractitionerToSelect);
        }
    }

    public void selectAllPractitionersCheckBox() throws AutomationException {
        WebElement AllPractitionersSelectCheckbox = driverUtil.getElementUsingJavascript(SELECT_ALL_PROVIDER);
        if (AllPractitionersSelectCheckbox == null)
            throw new AutomationException("checkbox for Select all tasks not displayed on Task table");
        WebDriverUtil.waitForAWhile(3);
        driverUtil.waitForElementToBeClickable(SELECT_ALL_PROVIDER);
        if (!AllPractitionersSelectCheckbox.isSelected()) {
            driverUtil.clickUsingJavaScript(SELECT_ALL_PROVIDER);
            if (!AllPractitionersSelectCheckbox.isSelected())
                throw new AutomationException("All Practitioners were not Selected");
        }
        takeScreenshot();
    }

    public void verifyButtonsPresentInBulkActionDropDownTab(DataTable dataTable) throws AutomationException {
        WebElement selectDropdown = driverUtil.getWebElementAndScroll("//*[text()='Select Bulk Action']");
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: Select Bulk Action"));
        selectDropdown.click();
        List<String> nameOfPractitionersToSelect = dataTable.asList(String.class);
        for (String nameOfPractitionerToSelect : nameOfPractitionersToSelect) {
            WebElement selectDropdownOption = driverUtil.getWebElement("//*[text()='" + nameOfPractitionerToSelect + "']");
            if (selectDropdownOption == null)
                throw new AutomationException(String.format("Unable to find %s option in Select Bulk Action DropDown", nameOfPractitionerToSelect));
            takeScreenshot();
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void verifyButtonsPresentInFollowupStatus(DataTable dataTable) throws AutomationException {
        clickOnButtonPresentInCampaignsTab("Select Bulk Action");
        clickOnButtonPresentInCampaignsTab("Edit Follow Up Status");
        List<String> followupStatus = dataTable.asList(String.class);
        for (String followupStatusOption : followupStatus) {
            WebElement editFollowupOption = driverUtil.getWebElement(String.format(EDIT_FOLLOWUP_OPTION, followupStatusOption));
            if (editFollowupOption == null)
                throw new AutomationException(String.format("Unable to find %s followup status option in Select Bulk Action DropDown", followupStatusOption));
            takeScreenshot();
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void verifyUserAbleToClearPractitionerTableColumnFilter(DataTable dataTable) throws AutomationException {
        waitForLoadingPage();
        List<String> nameOfPractitionersToSelect = dataTable.asList(String.class);
        for (String nameOfColumnFilterInboxInTable : nameOfPractitionersToSelect) {
            WebElement filterOff = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments-off')]");
            if (filterOff != null) {
                filterOff.click();
                WebElement searchColumnFilterElement = driverUtil.findElement(String.format(SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR, nameOfColumnFilterInboxInTable));
                if (searchColumnFilterElement == null)
                    throw new AutomationException("Unable to find " + nameOfColumnFilterInboxInTable + " column search Filter");
                searchColumnFilterElement.click();
                searchColumnFilterElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE, Keys.ENTER));
            } else {
                WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
                if (filterOn != null) {
                    WebElement searchColumnFilterElement = driverUtil.findElement(String.format(SEARCH_COLUMN_FILTER_ELEMENT_LOCATOR, nameOfColumnFilterInboxInTable));
                    if (searchColumnFilterElement == null)
                        throw new AutomationException("Unable to find " + nameOfColumnFilterInboxInTable + " column search Filter");
                    searchColumnFilterElement.click();
                    searchColumnFilterElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE, Keys.ENTER));
                } else
                    throw new AutomationException("Unable to find column filter mode and action disable enable button on campaigns tab");
            }
            takeScreenshot();
        }
        WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
        if (filterOn != null) {
            filterOn.click();
        }
    }

    public void verifyAfterSelectPractitionerUserRedirectToPractitionersTab(String practitionerName) throws AutomationException {
        waitForLoadingPage();
        WebElement practitionerRecord = driverUtil.getWebElement(String.format(PRACTITIONER_NAME_CELL, practitionerName));
        if (practitionerRecord == null)
            throw new AutomationException(String.format("Report Sent record not displayed in reports table"));
        waitForLoadingPage();
        practitionerRecord.click();
        waitForLoadingPage();
        practitionerRecord = driverUtil.getWebElement(String.format(PRACTITIONER_NAME_CELL, practitionerName));
        if(practitionerRecord!=null){
            practitionerRecord.click();
        }
        waitForLoadingPage();
        waitForLoadingPage();
        WebDriverUtil.waitForAWhile(WebDriverUtil.DEFAULT_ELEMENT_WAIT);

        WebElement practitionersTabSelected = driverUtil.getWebElement(String.format(SELECTED_ACTIVE_GLOBAL_TAB, "Practitioners"));
        if (practitionersTabSelected == null)
            throw new AutomationException(String.format("After selecting Report Sent record user not redirect to practitioners tab but it should be redirect to practitioners tab!"));
    }

    public void verifyLastUpdatedDateDisplayedLastInTimeLineTable() throws AutomationException {
        WebElement element = driverUtil.findElement("//*[text()='Practitioner Timeline']//following::thead//..//*[text()='Date']//..//button//..//*[contains(@class,'sort-descending')]");
        if (element == null) {
            driverUtil.findElement("//*[text()='Date' and contains(@class,'TableHeadCell')]/..//button").click();
            element = driverUtil.findElement("//*[text()='Practitioner Timeline']//following::thead//..//*[text()='Date']//..//button//..//*[contains(@class,'sort-descending')]");
            if (element == null) {
                driverUtil.findElement("//*[text()='Date' and contains(@class,'TableHeadCell')]/..//button").click();
            }
        }

        WebElement lastUpdatedDate = driverUtil.findElement("(//*[contains(@class,'PractitionerStoryTableV2-__container')]//ancestor::table//tbody//td[1])[1]");
        if (lastUpdatedDate == null)
            throw new AutomationException("Unable to find last updated date in timeline table");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String currentDate = LocalDateTime.now().format(format);

        System.out.println("currentDate= " + currentDate);
        if (!lastUpdatedDate.getText().contains(currentDate))
            throw new AutomationException("Last updated date in table is '" + lastUpdatedDate.getText() + "' but we expected it as '" + currentDate + "'");
    }

    public void deleteCommentsIfPresent() throws AutomationException {
        while (true) {
            WebElement deleteTaskButton = driverUtil.findElement("(//textarea/../..//*[@id='deleted'])[1]");
            if (deleteTaskButton == null)
                break;
            driverUtil.moveToElementAndClick(deleteTaskButton);
            clickOnConfirmationDialogDeleteButton();
            waitForLoadingPage();
        }
    }

    public void clickOnCampaignsPractitionerRecord(String name) throws AutomationException {
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_PRACTITIONER_RECORD, name));
        if (practitioner == null)
            throw new AutomationException("No practitioner found with the name: " + name);
        practitioner.click();
        waitForLoadingPage();
    }

    public void addPractitionerBulkComment(String comment) throws AutomationException {
        WebElement commentInput = driverUtil.getWebElement(COMMENT_INPUT);
        if (commentInput == null)
            throw new AutomationException("Comment text area is not being displayed after clicking on Add Practitioner Comment!");
        commentInput.sendKeys(comment);
        clickOnButtonPresentInCampaignsTab("Add Comment");
    }

    public void clickOnPDFIconInCampaignsTable(String prescriber) throws AutomationException {
        WebElement pdfIconInTable = driverUtil.getWebElement(String.format(PDF_ICON_IN_CAMPAIGNS_TAB_REPORTS_TABLE, prescriber));
        if (pdfIconInTable == null)
            throw new AutomationException("PDF icon is not being displayed in reports table PDF column!");
        driverUtil.waitForLoadingPage();
        pdfIconInTable.click();
        driverUtil.waitForLoadingPage();
    }

    public void verifyReportViewerInCampaignsTab() throws AutomationException {
        driverUtil.waitForElement(By.xpath(REPORT_PDF_VIEWER_IN_CAMPAIGNS_TAB),10);
        WebElement reportPDFViewerPopup = driverUtil.getWebElement(REPORT_PDF_VIEWER_IN_CAMPAIGNS_TAB);
        if (reportPDFViewerPopup == null)
            throw new AutomationException("No Report PDF viewer is being displayed!");
        WebDriverUtil.waitForAWhile(10);
    }

    public void verifyPractitionerInCampaignsTabTable(String columnToVerify, String practitionerName, String expectedColumnValue) throws AutomationException {
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_PRACTITIONER_RECORD, practitionerName));
        if (practitioner == null)
            throw new AutomationException("No practitioner found with the name: " + practitionerName);
        practitioner.click();
        waitForLoadingPage();

        switch (columnToVerify.toUpperCase()) {
            case "ADDRESS":
                int columnIndex = getTableColumnHeadingIndex(columnToVerify);
                int cellIndex = columnIndex - 3;
                WebElement address = driverUtil.getWebElement(String.format(PRACTITIONER_ADDRESS_IN_CAMPAIGNS_TAB, practitionerName, cellIndex));
                if (address == null)
                    throw new AutomationException("Couldn't locate element for address of practitioner : '" + practitionerName + "'");
                if (!address.getText().contains(expectedColumnValue))
                    throw new AutomationException("We expected address to be '" + expectedColumnValue + "' but found " + address.getText());
                break;
            case "FOLLOW UP STATUS":
                int followUpStatusColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int followUpStatusCellIndex = followUpStatusColumnIndex - 3;
                WebElement element = driverUtil.getWebElement(String.format(PRACTITIONER_FOLLOW_UP_STATUS_IN_CAMPAIGNS_TAB, practitionerName, followUpStatusCellIndex));
                if (element == null)
                    throw new AutomationException("Couldn't locate element for follow up status of practitioner : '" + practitionerName + "'");
                if (!element.getText().contains(expectedColumnValue))
                    throw new AutomationException("We expected follow up status to be '" + expectedColumnValue + "' but found " + element.getText());
                break;
            case "PHONE NUMBER":
                int phoneNumberColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int phoneNumberCellIndex = phoneNumberColumnIndex - 3;
                WebElement phoneNumber = driverUtil.getWebElement(String.format(PRACTITIONER_PHONE_NUMBER_IN_CAMPAIGNS_TAB, practitionerName, phoneNumberCellIndex));
                if (phoneNumber == null)
                    throw new AutomationException("Couldn't locate element for phone number of practitioner : '" + practitionerName + "'");
                if (!phoneNumber.getText().contains(expectedColumnValue))
                    throw new AutomationException("We expected phone number to be '" + expectedColumnValue + "' but found " + phoneNumber.getText());
                break;
            case "PATIENTS":
                int patientColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int patientCellIndex = patientColumnIndex - 3;
                WebElement patientNumber = driverUtil.getWebElement(String.format(PRACTITIONER_PATIENT_COUNT_IN_CAMPAIGNS_TAB, practitionerName, patientCellIndex));
                if (patientNumber == null)
                    throw new AutomationException("Couldn't locate element for patients count of practitioner : '" + practitionerName + "'");
                if (!patientNumber.getText().contains(expectedColumnValue))
                    throw new AutomationException("We expected patients count to be '" + expectedColumnValue + "' but found " + patientNumber.getText());
                break;
            case "DRPS":
                int drpColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int drpCellIndex = drpColumnIndex - 3;
                WebElement drpNumber = driverUtil.getWebElement(String.format(PRACTITIONER_DRP_COUNT_IN_CAMPAIGNS_TAB, practitionerName, drpCellIndex));
                if (drpNumber == null)
                    throw new AutomationException("Couldn't locate element for drp's count of practitioner : '" + practitionerName + "'");
                System.out.println("address get= " + drpNumber.getText());
                System.out.println("expectedColumnValue= " + expectedColumnValue);
                if (!drpNumber.getText().contains(expectedColumnValue))
                    throw new AutomationException("We expected drp's count to be '" + expectedColumnValue + "' but found " + drpNumber.getText());
                break;
            case "LAST FAX NUMBER":
                int lastFaxNumberColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int lastFaxNumberCellIndex = lastFaxNumberColumnIndex - 3;
                if (!expectedColumnValue.isBlank()) {
                    WebElement lastFaxNumber = driverUtil.getWebElement(String.format(PRACTITIONER_LAST_FAX_NUMBER_IN_CAMPAIGNS_TAB, practitionerName, lastFaxNumberCellIndex));
                    if (lastFaxNumber == null)
                        throw new AutomationException("Couldn't locate element for role of practitioner : '" + practitionerName + "'");
                    if (!lastFaxNumber.getText().contains(expectedColumnValue))
                        throw new AutomationException("We expected last fax number to be '" + expectedColumnValue + "' but found " + lastFaxNumber.getText());
                } else if (expectedColumnValue.isBlank()) {
                    lastFaxNumberColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                    lastFaxNumberCellIndex = lastFaxNumberColumnIndex - 3;
                    WebElement lastFaxNumber = driverUtil.getWebElement(String.format(PRACTITIONER_LAST_FAX_NUMBER_IN_CAMPAIGNS_TAB, practitionerName, lastFaxNumberCellIndex));
                    if (lastFaxNumber == null)
                        throw new AutomationException("Couldn't locate element for last fax number of practitioner : '" + practitionerName + "'");
                    if (!lastFaxNumber.getText().isBlank())
                        throw new AutomationException("We expected last fax number to be null but found " + lastFaxNumber.getText());
                }
                break;
            case "LAST FAXED":
                int lastFaxedColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int lastFaxedCellIndex = lastFaxedColumnIndex - 3;
                if (!expectedColumnValue.isBlank()) {
                    WebElement lastFaxedDate = driverUtil.getWebElement(String.format(PRACTITIONER_LAST_FAXED_DATE_COLUMN, practitionerName, lastFaxedCellIndex));
                    if (lastFaxedDate == null)
                        throw new AutomationException("Couldn't locate element for role of practitioner : '" + practitionerName + "'");
                    if (!lastFaxedDate.getText().contains(expectedColumnValue))
                        throw new AutomationException("We expected last faxed to be '" + expectedColumnValue + "' but found " + lastFaxedDate.getText());
                } else if (expectedColumnValue.isBlank()) {
                    lastFaxedColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                    lastFaxedCellIndex = lastFaxedColumnIndex - 3;
                    WebElement lastFaxedDate = driverUtil.getWebElement(String.format(PRACTITIONER_LAST_FAXED_DATE_COLUMN, practitionerName, lastFaxedCellIndex));
                    if (lastFaxedDate == null)
                        throw new AutomationException("Couldn't locate element for role of practitioner : '" + practitionerName + "'");
                    if (!lastFaxedDate.getText().isBlank())
                        throw new AutomationException("We expected last faxed date to be null but found " + lastFaxedDate.getText());
                }
                break;
            case "QUIET?":
                int quietColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int quietCellIndex = quietColumnIndex - 3;
                WebElement quietStatus = driverUtil.getWebElement(String.format(PRACTITIONER_QUIET_STATUS_COLUMN, practitionerName, quietCellIndex));
                if (quietStatus == null)
                    throw new AutomationException("Couldn't locate element for Quiet status of practitioner : '" + practitionerName + "'");
                System.out.println("address get= " + quietStatus.getText());
                System.out.println("expectedColumnValue= " + expectedColumnValue);
                String quietStatusText =quietStatus.getText();
                if (quietStatusText != null && !quietStatusText.contains(expectedColumnValue))
                    throw new AutomationException("We expected Quiet status to be '" + expectedColumnValue + "' but found " + quietStatus.getText());
                break;
            case "DO NOT FAX":
                int doNotFaxColumnIndex = getTableColumnHeadingIndex(columnToVerify);
                int doNotFaxCellIndex = doNotFaxColumnIndex - 3;
                if (!expectedColumnValue.isBlank()) {
                    WebElement doNotFaxStatus = driverUtil.getWebElement(String.format(PRACTITIONER_DO_NOT_FAX_STATUS_COLUMN, practitionerName, doNotFaxCellIndex));
                    if (doNotFaxStatus == null)
                        throw new AutomationException("Couldn't locate element for last fax status of practitioner : '" + practitionerName + "'");
                    if (!doNotFaxStatus.getText().contains(expectedColumnValue))
                        throw new AutomationException("We expected last faxed to be '" + expectedColumnValue + "' but found " + doNotFaxStatus.getText());
                } else if (expectedColumnValue.isBlank()) {
                    WebElement doNotFaxStatus = driverUtil.getWebElement(String.format(PRACTITIONER_DO_NOT_FAX_STATUS_COLUMN, practitionerName, doNotFaxCellIndex));
                    if (doNotFaxStatus == null)
                        throw new AutomationException("Couldn't locate element for last fax status of practitioner : '" + practitionerName + "'");
                    if (!doNotFaxStatus.getText().isBlank())
                        throw new AutomationException("We expected last fax status is to be null but found " + doNotFaxStatus.getText());
                }
                break;
        }
    }

    public void updatePractitionerPersonaDetailsInCampaigns(String label, String value) throws AutomationException {
        String nameAttribute = PRACTITIONER_DETAILS_INPUT_MAPPING.get(label);
        WebElement infoInput = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_PERSONAL_INFO_INPUT, nameAttribute));
        if (infoInput == null)
            throw new AutomationException(String.format("%s input box is not being displayed!", label));
        String tagName = infoInput.getTagName();
        switch (tagName) {
            case "input":
                infoInput.click();
                infoInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                infoInput.sendKeys(value, Keys.ENTER);
                infoInput.clear();
                break;
            case "select":
                Select select = new Select(infoInput);
                select.selectByVisibleText(value);
                break;
            default:
                break;
        }
        WebDriverUtil.waitForAWhile(10);
    }

    public void updatePractitionerFollowUpStatus(String roleName) throws AutomationException {
        Select roleDropDown = new Select(driverUtil.getWebElement(PRACTITIONER_FOLLOW_UP_STATUS_SELECT));
        roleDropDown.selectByVisibleText(roleName);
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        waitForLoadingPage();
    }

    public void verifyToolTipMessageForQuietTableColumn(String message, String practitioner, String columnName) throws AutomationException {
        Actions action = new Actions(DriverFactory.drivers.get());
        switch (columnName.toUpperCase()) {
            case "QUIET?":
                int quietColumnIndex = getTableColumnHeadingIndex(columnName);
                int quietCellIndex = quietColumnIndex - 3;
                WebElement quietStatus = driverUtil.findElementAndScroll(String.format(PRACTITIONER_QUIET_STATUS_COLUMN, practitioner, quietCellIndex));
                if (quietStatus == null)
                    break;
//                    throw new AutomationException("Couldn't locate element for Quiet status of practitioner : '" + practitioner + "'");
                action.moveToElement(quietStatus).perform();
                WebElement tolTipMessage = driverUtil.getWebElement("//*[@role='tooltip']");
                if (tolTipMessage == null)
                    throw new AutomationException("Couldn't locate the Tooltip for Quiet status or it might become invisible: '");
                System.out.println("TolTip Message= " + tolTipMessage.getText());

                if (!tolTipMessage.getText().equals(message))
                    throw new AutomationException("Expected tooltip '" + message + "' not contains when mouse hover action perform on column: '" + columnName + "'");
                break;
        }
    }

    public void clickOnLogIconInCampaignsTable(String prescriber) throws AutomationException {
        int columnIndex = getTableColumnHeadingIndex("Log");
        int cellIndex = columnIndex - 3;
        System.out.println("cellIndex"+cellIndex);
        WebElement pdfIconInTable = driverUtil.getWebElement(String.format(LOG_ICON_IN_CAMPAIGNS_TAB_REPORTS_TABLE, prescriber, cellIndex));
        if (pdfIconInTable == null)
            throw new AutomationException("Log Action icon is not being displayed in reports table Log column!");
        Actions action = new Actions(DriverFactory.drivers.get());
        action.moveToElement(pdfIconInTable).perform();
        pdfIconInTable.click();
        waitForLoadingPage();
    }

    public void deleteNewlyCreatedPractitionerLogAction() throws AutomationException {
        waitForLoadingPage();
        WebElement deleteStoryButton = driverUtil.getWebElement(PRACTITIONER_TIMELINE_FIRST_RECORD_DELETE);
        if (deleteStoryButton == null)
            throw new AutomationException("Unable to find delete story button or it might taking too long time to load!");
        deleteStoryButton.click();
        WebElement deleteConfirmationButton = driverUtil.getWebElement(DELETE_PRACTITIONER_LOG_ACTION_CONFIRMATION_POPUP_DELETE_BUTTON);
        if (deleteConfirmationButton == null)
            throw new AutomationException("Unable to find delete log action button on confirmation popup or it might taking too long time to load!");
        deleteConfirmationButton.click();
        waitForLoadingPage();
    }

    public void clickOnCampaignsReportsFilterFilterIcon() throws AutomationException {
        WebElement selectReportsFilterIcon = driverUtil.getWebElement(CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectReportsFilterIcon == null)
            throw new AutomationException("No Reports filter search icon visible in Campaigns page!");
        selectReportsFilterIcon.click();
    }

    public void verifyColumnIsUpdatedWithCurrentDateInCampaignsTable(String columnToVerify, String practitionerName) throws AutomationException {
        waitForLoadingPage();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String currentDate = LocalDateTime.now().format(format);
        System.out.println("currentDate= " + currentDate);
        switch (columnToVerify.toUpperCase()) {
            case "LAST CONTACTED":
                int columnIndex = getTableColumnHeadingIndex(columnToVerify);
                int cellIndex = columnIndex - 3;
                WebElement lastContactedColumn = driverUtil.getWebElement(String.format(PRACTITIONER_LAST_CONTACTED_COLUMN, practitionerName, cellIndex));
                if (lastContactedColumn == null)
                    throw new AutomationException("Couldn't locate element for last contacted of practitioner : '" + practitionerName + "'");
                System.out.println("lastContactedColumn= " + lastContactedColumn.getText());
                if (!lastContactedColumn.getText().contains(currentDate))
                    throw new AutomationException("We expected last contacted to be '" + currentDate + "' but found " + lastContactedColumn.getText());
                break;
        }
    }

    public void removeAllPreviousAppliedFiltersInCampaignsTab(String button) throws AutomationException {
        WebElement removeFilterButton = driverUtil.getWebElement(String.format(CAMPAIGNS_TAB_BUTTONS, button));
        if (removeFilterButton != null) {
            removeFilterButton.click();
            waitForLoadingPage();
        }
    }

    public void selectDateRangeFromDatePicker(String filterName) throws AutomationException, ParseException {
        if (filterName.equalsIgnoreCase("Last Contacted")) {
            DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("d");
            String lastContactedCurrentDate = LocalDateTime.now().format(currentDateFormat);
            String lastContactedFutureDate = LocalDateTime.now().plusDays(5).format(currentDateFormat);
            DateTimeFormatter futureDateFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
            String futureDateInMonths = LocalDateTime.now().plusDays(5).format(futureDateFormat);

            WebElement rangeDate_1 = driverUtil.getWebElement("//button[contains(@class,'DatePickerInput') and not(@data-outside) and text()='" + lastContactedCurrentDate + "']");
            rangeDate_1.click();
            WebElement currentMonthInput = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
            if (currentMonthInput == null)
                throw new AutomationException("Current Month and Year is not being displayed in advance task filter popup!");
            if (!currentMonthInput.getText().equals(futureDateInMonths)) {
                WebElement nextMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
                if (nextMonthYearChangeButton == null)
                    throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
                nextMonthYearChangeButton.click();
            }
            WebElement rangeDate_2 = driverUtil.getWebElement("//button[contains(@class,'DatePickerInput') and not(@data-outside) and text()='" + lastContactedFutureDate + "']");
            rangeDate_2.click();
            WebDriverUtil.waitForAWhile();
        } else if (filterName.equalsIgnoreCase("Last Faxed")) {
            SimpleDateFormat df = new SimpleDateFormat("d");
            Date expectedDateRange1 = new SimpleDateFormat("d").parse(expectedLastFaxedDate1);
            Date expectedDateRange2 = new SimpleDateFormat("d").parse(expectedLastFaxedDate2);
            String formattedDateStringRange1 = df.format(expectedDateRange1);
            String formattedDateStringRange2 = df.format(expectedDateRange2);
            Date expectedDateInMonths = new SimpleDateFormat("MMMM yyyy").parse("Sep 2023");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String expectedDateInMonth = dateFormat.format(expectedDateInMonths);

            WebElement currentMonthInputElement = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
            Date currentMonthInput = new SimpleDateFormat("MMMM yyyy").parse(currentMonthInputElement.getText());
            if (currentMonthInput == null)
                throw new AutomationException("Current Month and Year is not being displayed in advance task filter popup!");
            String currentMonthInputElements = currentMonthInputElement.getText();
            while (!expectedDateInMonth.equals(currentMonthInputElements)) {
                if (expectedDateInMonths.before(currentMonthInput)) {
                    WebElement previousMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-previous='true']");
                    if (previousMonthYearChangeButton == null)
                        throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
                    previousMonthYearChangeButton.click();
                } else if (expectedDateInMonths.after(currentMonthInput)) {
                    WebElement nextMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
                    if (nextMonthYearChangeButton == null)
                        throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
                    nextMonthYearChangeButton.click();
                }
                currentMonthInputElement = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
                currentMonthInputElements = currentMonthInputElement.getText();
                break;
            }
            WebElement rangeDate_1 = driverUtil.getWebElement("//button[contains(@class,'DatePickerInput') and not(@data-outside) and text()='" + formattedDateStringRange1 + "']");
            rangeDate_1.click();
            WebElement rangeDate_2 = driverUtil.getWebElement("//button[contains(@class,'DatePickerInput') and not(@data-outside) and text()='" + formattedDateStringRange2 + "']");
            rangeDate_2.click();
            WebDriverUtil.waitForAWhile();
        }
    }

    public void selectTaskFilter(String filterName, String filterOption, String buttonName) throws AutomationException, ParseException {
        Actions actions = new Actions(DriverFactory.drivers.get());
        WebElement selectTaskFilterIcon = driverUtil.getWebElement(CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectTaskFilterIcon == null)
            throw new AutomationException("No filter search icon visible in Campaigns tab!");
        selectTaskFilterIcon.click();

        if (filterName.equalsIgnoreCase("Do Not Fax") || filterName.equalsIgnoreCase("Follow Up Status")) {
            WebElement campaignAdvanceFilterDropDown = driverUtil.getWebElement(String.format(CAMPAIGNS_ADVANCE_FILTER_DROPDOWN, filterName, filterName));
            if (campaignAdvanceFilterDropDown == null)
                throw new AutomationException("No '" + filterName + "' advance filter dropdown defined in campaign filter options!");
            campaignAdvanceFilterDropDown.click();
            WebElement campaignAdvanceFilterDropDownOption = driverUtil.getWebElement(String.format(CAMPAIGNS_ADVANCE_FILTER_DROPDOWN_OPTION, filterOption));
            if (campaignAdvanceFilterDropDownOption == null)
                throw new AutomationException("No '" + filterName + "' campaign Advance Filter DropDown Option defined in campaign filter options!");
            campaignAdvanceFilterDropDownOption.click();
            actions.sendKeys(Keys.ESCAPE).build().perform();
        } else if (filterName.equalsIgnoreCase("Last Faxed") || filterName.equalsIgnoreCase("Last Contacted")) {
            WebElement campaignAdvanceFilterDateSelector = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_ADVANCE_FILTER_DATE_SELECTOR, filterName));
            if (campaignAdvanceFilterDateSelector == null)
                throw new AutomationException("No '" + filterName + "' filter DateSelector defined in campaign Advance Filter options!");
            campaignAdvanceFilterDateSelector.click();
            selectDateRangeFromDatePicker(filterName);
        } else {
            WebElement campaignAdvanceFilterInput = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_ADVANCE_FILTER_INPUT, filterName, filterName));
            if (campaignAdvanceFilterInput == null)
                throw new AutomationException("No '" + filterName + "' filter dropdown defined in Task page filter options!");
            campaignAdvanceFilterInput.click();
            campaignAdvanceFilterInput.clear();
            campaignAdvanceFilterInput.sendKeys(filterOption);
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
        clickOnButtonPresentInCampaignsTab(buttonName);
        waitForLoadingPage();
        scrollToTop();
    }

    public static int getTableColumnHeadingIndex(String columnName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        List columnHeadingNameList = new ArrayList();
        int indexNumber = 0;
        for (int i = 0; i <= columnNames.size(); i++) {
            indexNumber++;
            columnHeadingNameList.add(columnNames.get(i).getText());
            if (columnNames.get(i).getText().equalsIgnoreCase(columnName))
                break;
        }
        return indexNumber;
    }

    public void verifyColumnContainsValuesInReportsTable(String columnName, String filterOption) throws AutomationException, ParseException {
        WebElement selectTaskFilterIcon = driverUtil.getWebElement("//table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper') and @title='" + columnName + "']");
        if (selectTaskFilterIcon == null)
            throw new AutomationException(columnName + " Column Name not visible in reports table!");
        int columnIndex = getTableColumnHeadingIndex(columnName);
        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_TABLE_COLUMN_DATA, columnIndex));
        List columnCellDataList = new ArrayList();
        System.out.println("columnIndex== " + columnIndex);
        for (int i = 0; i < columnData.size(); i++) {
            columnCellDataList.add(columnData.get(i).getText());
            String columnCellData = columnData.get(i).getText();

            if (columnName.equalsIgnoreCase("Last Faxed") || columnName.equalsIgnoreCase("Last Contacted")) {
                Date columnCellDateData = new SimpleDateFormat("dd/MM/yyyy").parse(columnCellData);
                if (columnName.equalsIgnoreCase("Last Faxed")) {
                    Date expectedDateRange1 = new SimpleDateFormat("dd/MM/yyyy").parse(expectedLastFaxedDate1);
                    Date expectedDateRange2 = new SimpleDateFormat("dd/MM/yyyy").parse(expectedLastFaxedDate2);
                    Date expectedDateRange1PreviousDay = new Date(expectedDateRange1.getTime() - 1);
                    Date expectedDateRange2PreviousDay = new Date(expectedDateRange2.getTime() + 1);

                    if (columnCellDateData != null && !columnCellDateData.after(expectedDateRange1PreviousDay) && !columnCellDateData.before(expectedDateRange2PreviousDay))
                        throw new AutomationException("In " + columnName + " Column only " + filterOption + " this data should be displayed but we found " + columnData.get(i).getText() + " as well");
                } else if (columnName.equalsIgnoreCase("Last Contacted")) {
                    DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String lastContactedCurrentDate = LocalDateTime.now().format(currentDateFormat);
                    String lastContactedFutureDate = LocalDateTime.now().plusDays(5).format(currentDateFormat);

                    Date expectedDateRange1 = new SimpleDateFormat("dd/MM/yyyy").parse(lastContactedCurrentDate);
                    Date expectedDateRange2 = new SimpleDateFormat("dd/MM/yyyy").parse(lastContactedFutureDate);
                    Date expectedDateRange1PreviousDay = new Date(expectedDateRange1.getTime() - 1);
                    Date expectedDateRange2PreviousDay = new Date(expectedDateRange2.getTime() + 1);

                    if (!columnCellDateData.after(expectedDateRange1PreviousDay) && !columnCellDateData.before(expectedDateRange2PreviousDay) && columnCellDateData != null)
                        throw new AutomationException("In " + columnName + " Column only in between date " + expectedDateRange1PreviousDay + " to " + expectedDateRange1PreviousDay + " this data range should be displayed but we found " + columnData.get(i).getText() + " as well");
                }
            } else if (columnCellData != null && !columnCellData.contains(filterOption))
                throw new AutomationException("In " + columnName + " Column only " + filterOption + " this data should be displayed but we found " + columnData.get(i).getText() + " as well");
        }
    }

    public static List<String> getColumnDataFromSearchResult(String columnNameToSort) throws AutomationException {
//        WebElement selectTaskFilterIcon = driverUtil.getWebElement("//table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper') and text()='" + columnNameToSort + "']");
        WebElement selectTaskFilterIcon = driverUtil.getWebElement("//table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper')]//ancestor-or-self::*[text()='" + columnNameToSort + "']");
        if (selectTaskFilterIcon == null)
            throw new AutomationException(columnNameToSort + " Column Name not visible in reports table!");
        int columnIndex = getTableColumnHeadingIndex(columnNameToSort);
        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_TABLE_COLUMN_DATA, columnIndex));
        if (columnData == null)
            throw new AutomationException("Total User record count not displayed or page is taking long time to load");
        List<String> columnCellDataList = new ArrayList();
        System.out.println("columnIndex== " + columnIndex);

        for (int i = 0; i < columnData.size(); i++) {
            columnCellDataList.add(columnData.get(i).getText().trim().toLowerCase());
        }
        System.out.println("columnCellDataList== " + columnCellDataList);
        return columnCellDataList;
    }

    public void sortColumn(String sortType, String columnNameToSort) throws AutomationException {
        boolean ordered;
        WebElement element;
        if (sortType.equalsIgnoreCase("Ascending")) {
            element = driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[contains(@aria-label,'ascending')]");
            if (element == null) {
                driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[@aria-label='Unsorted' or contains(@aria-label,'descending')]").click();
                driverUtil.waitForLoaderToDisappear();
                element = driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[contains(@aria-label,'ascending')]");
                if (element == null) {
                    driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[@aria-label='Unsorted' or contains(@aria-label,'descending')]").click();
                    driverUtil.waitForLoaderToDisappear();
                }
            }
        } else {
            element = driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[contains(@aria-label,'descending')]");
            if (element == null) {
                driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[@aria-label='Unsorted' or contains(@aria-label,'ascending')]").click();
                driverUtil.waitForLoaderToDisappear();
                element = driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[contains(@aria-label,'descending')]");
                if (element == null) {
                    driverUtil.findElement("//table//*[contains(@class,'TableHeadCell')]/..//*[text()='" + columnNameToSort + "']/../..//button[@aria-label='Unsorted' or contains(@aria-label,'ascending')]").click();
                    driverUtil.waitForLoaderToDisappear();
                }
            }
        }
        CommonSteps.takeScreenshot();
        if (sortType.equalsIgnoreCase("ASCENDING")) {
            ordered = Ordering.natural().isOrdered(getColumnDataFromSearchResult(columnNameToSort));
            if (!ordered)
                throw new AutomationException(columnNameToSort + " was expected to be sorted in ascending order but its not");
        } else {
            ordered = Ordering.natural().reverse().isOrdered(getColumnDataFromSearchResult(columnNameToSort));
            if (!ordered)
                throw new AutomationException(columnNameToSort + " was expected to be sorted in descending order but its not");
        }
    }

    public void verifyAppliedAdvancedFilterOptionsNotVisible() throws AutomationException {
        WebElement filteredOptions = driverUtil.getWebElement(ADVANCE_FILTERED_OPTIONS);
        if (filteredOptions != null)
            throw new AutomationException("Advanced filtered options should not be visible, but they are visible!");
    }

    public void verifyAppliedAdvancedFilterOptionsVisible() throws AutomationException {
        WebElement filteredOptions = driverUtil.getWebElement(ADVANCE_FILTERED_OPTIONS);
        if (filteredOptions == null)
            throw new AutomationException("Advanced filtered options should be visible, but they are not.");
    }

    public void verifyPaginationInCampaignTable() throws AutomationException {
        int countAfterFilter = getColumnDataFromSearchResult("Recipient").size();
        WebElement filterPagination = driverUtil.getWebElementAndScrollWithoutWait("//div[@class='mantine-Text-root mantine-8cp6g0']");
        if (filterPagination == null)
            throw new AutomationException("Pagination option is not visible!");
        String filterPaginationText = filterPagination.getText().trim();
        String paginationMatchText = "Total " + countAfterFilter;
        if (!filterPaginationText.trim().equalsIgnoreCase(paginationMatchText))
            throw new AutomationException("Pagination count not get match after apply filter!");
        takeScreenshot();
    }

    public void verifyJumpToPageForCampaignTable() throws AutomationException {
        waitForLoadingPage();
            WebElement filterPaginationNextPageButton = driverUtil.getWebElementAndScrollWithoutWait("//*[contains(@d,'M8.781')]//ancestor::button");
        if (filterPaginationNextPageButton == null)
            throw new AutomationException("Pagination next button not displayed!");

        while (true) {
            WebElement filterPaginationNextPageButtonDisable = driverUtil.getWebElementAndScrollWithoutWait("//*[contains(@d,'M8.781')]//ancestor::button[@data-disabled='true']");
            if (filterPaginationNextPageButtonDisable==null) {
                filterPaginationNextPageButton.click();
                waitForLoadingPage();
            }
            break;
        }
        waitForLoadingPage();
        WebElement lastPageSelectedButtonText = driverUtil.getWebElementAndScrollWithoutWait("//button[@aria-current='page' and @data-active='true']");
        if (lastPageSelectedButtonText == null)
            throw new AutomationException("Pagination selected page option is not visible or might be there is no any page is selected!");
        String selectedPageButtonText = lastPageSelectedButtonText.getText();
        int selectedPageNumber = Integer.parseInt(selectedPageButtonText) + 1;
        WebElement jumpToPageInput = driverUtil.getWebElementAndScrollWithoutWait("//*[text()='Jump to page']//following::input");
        if (jumpToPageInput == null)
            throw new AutomationException("Pagination Jump to the page input is not visible!");
        jumpToPageInput.sendKeys(String.valueOf(selectedPageNumber));
        driverUtil.clickUsingJavaScript("Go");
        jumpToPageInput = driverUtil.getWebElementAndScrollWithoutWait("//*[text()='Jump to page']//following::input");
        String jumpInputText = jumpToPageInput.getAttribute("value");

        if (jumpInputText.isBlank() && jumpInputText.equals(selectedPageNumber))
            throw new AutomationException("Jump to page textBox, does not allow user to enter a page number greater than available pages, but its excepts greater than page count in input or it is blank!");
    }

    public static void clickOnShowHideColumnsButtonAndOnOrOffIt(String buttonStatus) throws AutomationException {
        switch (buttonStatus.toUpperCase()) {
            case "ON":
                WebElement filterOff = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments-off')]");
                if (filterOff != null)
                    filterOff.click();
                break;

            case "OFF":
                WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
                if (filterOn != null)
                    filterOn.click();
                break;
        }
    }

    public void verifyAllColumnHeadingShouldHaveColumnActionsAndMoveIcon(String iconName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        int columnNamesCount = Integer.parseInt(String.valueOf(columnNames.size())) - 1;
        switch (iconName.toUpperCase()) {
            case "COLUMN ACTION":
                List<WebElement> columnActionIcons = driverUtil.getWebElements("//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                if (columnNamesCount != columnActionIcons.size())
                    throw new AutomationException("In Reports table " + columnNamesCount + " column header are visible but in table only " + columnActionIcons.size() + " column actions icons are present");
                break;

            case "MOVE COLUMN":
                List<WebElement> moveColumnIcons = driverUtil.getWebElements("//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                if (columnNamesCount != moveColumnIcons.size())
                    throw new AutomationException("In Reports table " + columnNamesCount + " column header are visible but in table only " + moveColumnIcons.size() + " column move icons are present");
                break;
        }
    }

    public void verifyAllColumnHeadingShouldNotHaveColumnActionsAndMoveIcon(String iconName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        switch (iconName.toUpperCase()) {
            case "COLUMN ACTION":
                WebElement columnActionIcons = driverUtil.getWebElement("//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                if (columnActionIcons != null)
                    throw new AutomationException("In Reports table when column filter is hide then COLUMN ACTION icon should not visible but it is present in table");
                break;

            case "MOVE COLUMN":
                WebElement moveColumnIcons = driverUtil.getWebElement("//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                if (moveColumnIcons != null)
                    throw new AutomationException("In Reports table when column filter is hide then MOVE COLUMN icon should not visible but it is present in table");
                break;
        }
    }

    public void verifyUserAbleToMoveColumns(String column1, String column2) throws AutomationException, AWTException {
        clickOnShowHideColumnsButtonAndOnOrOffIt("On");
        int column1IndexBeforeMove = getTableColumnHeadingIndex(column1);
        WebElement column1MoveIcon = driverUtil.getWebElement("//*[text()='" + column1 + "']//..//..//button[@draggable='true']");
        if (column1MoveIcon == null)
            throw new AutomationException("Column Move Icon not visible for column: " + column1);
        WebElement column2MoveIcon = driverUtil.getWebElement("//*[text()='" + column2 + "']//..//..//button[@draggable='true']");
        if (column2MoveIcon == null)
            throw new AutomationException("Column Move Icon not visible for column: " + column2);

        WebDriverUtil.dragAndDropUsingJavaScript(column1MoveIcon, column2MoveIcon);

        int column1IndexAfterMove = getTableColumnHeadingIndex(column1);
        System.out.println("column1IndexBeforeMove== " + column1IndexBeforeMove);
        System.out.println("column1IndexAfterMove== " + column1IndexAfterMove);
        if (column1IndexBeforeMove == column1IndexAfterMove)
            throw new AutomationException("user not able to drag columns from providers table");
    }

    public void clickOnButtonToResetColumnsInReportsTable(String text) throws AutomationException {
        clickOnColumnShowHideButton();
//        WebElement button = driverUtil.getWebElementAndScroll("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        WebElement button = driverUtil.getElementUsingJavascript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        if (button != null)
//            button.click();
            driverUtil.clickUsingJavaScript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        actions.sendKeys(Keys.ESCAPE).build().perform();
        closeShowHidePopup();
        takeScreenshot();
    }

    public void clickOnButtonToUnpinAllColumnsInReportsTable(String text) throws AutomationException {
        waitForLoadingPage();
        clickOnColumnShowHideButton();
        WebElement button = driverUtil.findElementAndScroll("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        if (button != null)
//            button.click();
            driverUtil.clickUsingJavaScript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        WebDriverUtil.waitForAWhile(3);
        actions.sendKeys(Keys.ESCAPE, Keys.ESCAPE).build().perform();
        closeShowHidePopup();
        takeScreenshot();
    }

    public void verifyButtonElementsArePresentInColumnShowHidePopup(List<String> buttons) throws AutomationException {
        clickOnColumnShowHideButton();
        for (String button : buttons) {
//            WebElement element = driverUtil.getWebElementAndScroll(String.format(CAMPAIGNS_TAB_BUTTONS,button));
            WebElement element = driverUtil.getElementUsingJavascript(String.format(CAMPAIGNS_TAB_BUTTONS, button));
            if (element == null)
                throw new AutomationException(button + " Button not present in column show hide popup");
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void verifyButtonElementsAreNotPresentInColumnShowHidePopup(List<String> buttons) throws AutomationException {
        clickOnColumnShowHideButton();
        for (String button : buttons) {
            WebElement element = driverUtil.getElementUsingJavascript(String.format(CAMPAIGNS_TAB_BUTTONS, button));
            if (element != null)
                throw new AutomationException(button + "Button present in column show hide popup but it should Remove from popup");
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void     verifyUserAbleToMoveColumnsFromShowHideTabPopup(String column1, String column2) throws AutomationException, AWTException {
        int column1IndexBeforeMove = getTableColumnHeadingIndex(column1);
        clickOnColumnShowHideButton();
        WebElement column1MoveIcon = driverUtil.getWebElement("//*[text()='" + column1 + "']//ancestor::button//child::button[@draggable='true']");
        if (column1MoveIcon == null)
            throw new AutomationException("Column Move Icon in column show hide popup is not visible for column: " + column1);
        WebElement column2MoveIcon = driverUtil.getWebElement("//*[text()='" + column2 + "']//ancestor::button//child::button[@draggable='true']");
        if (column2MoveIcon == null)
            throw new AutomationException("Column Move Icon in column show hide popup is not visible for column: " + column2);

        WebDriverUtil.dragAndDropUsingJavaScript(column1MoveIcon, column2MoveIcon);
        WebDriverUtil.waitForAWhile(5);
        takeScreenshot();
        actions.sendKeys(Keys.ESCAPE).build().perform();

        int column1IndexAfterMove = getTableColumnHeadingIndex(column1);
        if (column1IndexBeforeMove == column1IndexAfterMove)
            throw new AutomationException("Unable able to move columns from providers table through show hide tab popup!");
    }

    public void verifyUserAbleToResetColumnOrdering(String button) throws AutomationException, AWTException {
        String column1 = "Recipient";
        String column2 = "Address";
        int column1IndexBeforeMove = getTableColumnHeadingIndex(column1);

        verifyUserAbleToMoveColumns(column1, column2);

        clickOnButtonToResetColumnsInReportsTable(button);
        waitForLoadingPage();
        int column1IndexAfterMove = getTableColumnHeadingIndex(column1);
        if (column1IndexBeforeMove != column1IndexAfterMove)
            throw new AutomationException("Unable able to move columns from providers table through show hide tab popup!");
    }

    public void verifyUserAbleToPinUnpinColumn(String status, String column) throws AutomationException, AWTException {
        int columnIndex;
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        int totalColumnIndex = columnNames.size();
        clickOnColumnShowHideButton();
        switch (status.toUpperCase()) {
            case "PIN TO RIGHT":
                WebElement pinToRightTab = driverUtil.getWebElement("//*[text()='" + column + "']//ancestor::button//*[contains(@style,'rotate(-90deg)')]");
                if (pinToRightTab == null)
                    throw new AutomationException("Pin to Right button not present for Column " + column);
                pinToRightTab.click();
                waitForLoadingPage();

                WebElement openedShowHideColumnIcon = driverUtil.getWebElement("//button[@aria-label='Show/Hide columns' and @aria-expanded='true']");
                if (openedShowHideColumnIcon != null)
                    openedShowHideColumnIcon.click();

                actions.sendKeys(Keys.ESCAPE).build().perform();
                columnIndex = getTableColumnHeadingIndex(column);

                System.out.println("columnIndex=== " + columnIndex);
                System.out.println("columnNames=== " + columnNames.size());
                if (columnIndex != totalColumnIndex)
                    throw new AutomationException("Pined " + column + " is not showing Right to the table!");
                break;

            case "PIN TO LEFT":
                WebElement pinToLeft = driverUtil.getWebElement("//*[text()='" + column + "']//ancestor::button//*[contains(@style,'rotate(90deg)')]");
                if (pinToLeft == null)
                    throw new AutomationException("Pin to left button not present for Column " + column);
                pinToLeft.click();
                waitForLoadingPage();

                openedShowHideColumnIcon = driverUtil.getWebElement("//button[@aria-label='Show/Hide columns' and @aria-expanded='true']");
                if (openedShowHideColumnIcon != null)
                    openedShowHideColumnIcon.click();

                actions.sendKeys(Keys.ESCAPE).build().perform();
                waitForLoadingPage();
                columnIndex = getTableColumnHeadingIndex(column);
                System.out.println("columnIndex" + columnIndex);
                if (columnIndex != 1)
                    throw new AutomationException("Pined " + column + " is not showing Left to the table!");
                break;
        }
    }

    public void verifyElementsArePresentInColumnActionPopup(String columnName, List<String> buttons) throws AutomationException {
        clickOnShowHideColumnsButtonAndOnOrOffIt("On");

        WebElement columnActionIcon = driverUtil.getWebElement("//*[text()='" + columnName + "']//..//..//button[@aria-label='Column Actions']");
        if (columnActionIcon == null)
            throw new AutomationException("Column " + columnName + " dose not have Column Actions Icon");
        columnActionIcon.click();
        for (String button : buttons) {
//            WebElement element = driverUtil.getWebElement(String.format(COLUMN_ACTIONS_ELEMENTS,button));
            WebElement element = driverUtil.getElementUsingJavascript(String.format(COLUMN_ACTIONS_ELEMENTS, button));
            if (element == null)
                throw new AutomationException(button + " Button not present in column actions popup");
        }
        takeScreenshot();
        actions.sendKeys(Keys.ESCAPE, Keys.ESCAPE).build().perform();
    }

    public void clickOnPDFIconFromReportHistoryTable() throws AutomationException {
        WebElement button = driverUtil.getWebElement(PDF_ICON_IN_REPORT_HISTORY_TABLE);
        if (button == null)
            throw new AutomationException("Unable to find PDF button in reports History table or it might taking too long time to load!");
        button.click();
        waitForLoadingPage();
    }

    public void verifyLastFaxedDateDisplayedLastInReportHistoryTable() throws AutomationException {
        WebElement element = driverUtil.findElement("//*[text()='Report History']//following::thead//..//*[text()='Date']//..//button//..//*[contains(@class,'sort-descending')]");
        if (element == null) {
            driverUtil.findElement("//*[text()='Created' and contains(@class,'TableHeadCell')]/..//button").click();
            element = driverUtil.findElement("//*[text()='Report History']//following::thead//..//*[text()='Date']//..//button//..//*[contains(@class,'sort-descending')]");
            if (element == null) {
                driverUtil.findElement("//*[text()='Created' and contains(@class,'TableHeadCell')]/..//button").click();
            }
        }

        WebElement lastUpdatedDate = driverUtil.findElement("(//*[contains(@class,'Table-root mantine')]//tr[1]//td[1]//descendant-or-self::div)[1]");
        if (lastUpdatedDate == null)
            throw new AutomationException("Unable to find last updated date in report history table");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String currentDate = LocalDateTime.now().format(format);

        System.out.println("currentDate= " + currentDate);
        System.out.println("lastUpdatedDate= " + lastUpdatedDate.getText());
        if (!lastUpdatedDate.getText().contains(currentDate))
            throw new AutomationException("Last updated date in table is '" + lastUpdatedDate.getText() + "' but we expected it as '" + currentDate + "'");
    }

    public static int getHistoryTableColumnHeadingIndex(String columnName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_HISTORY_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        List columnHeadingNameList = new ArrayList();
        int indexNumber = 0;
        for (int i = 0; i <= columnNames.size(); i++) {
            indexNumber++;
            columnHeadingNameList.add(columnNames.get(i).getText());
            if (columnNames.get(i).getText().equalsIgnoreCase(columnName))
                break;
        }
        return indexNumber;
    }

    public void verifyLastFaxedDateDisplayedLastInReportHistoryTableAndVerifyColumnUpdatedValues(String columnName,String columnValue) throws AutomationException {
        verifyLastFaxedDateDisplayedLastInReportHistoryTable();
        int followUpStatusColumnIndex = getHistoryTableColumnHeadingIndex(columnName);
        System.out.println("followUpStatusCellIndex== " + followUpStatusColumnIndex);
        WebElement firstFaxedLogRecordInReportHistoryTable = driverUtil.getWebElement(String.format(COLUMN_FIRST_VALUE_IN_REPORT_HISTORY_TABLE, followUpStatusColumnIndex));

        if (firstFaxedLogRecordInReportHistoryTable == null)
            throw new AutomationException("Unable to find " + columnValue + " status in "+columnName+" column");
        String statusInTable = firstFaxedLogRecordInReportHistoryTable.getText();
        System.out.println("followUpStatusInTable =====" + statusInTable);

        if (!columnValue.equals(statusInTable))
            throw new AutomationException(String.format("Expected Updated column Status for %s column is %s but its shows: %s", columnName, columnValue, statusInTable));
    }

    public void verifyNewlyCreatedLogActionForPAFeature(String identifier) throws AutomationException {
        waitForLoadingPage();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String currentDate = LocalDateTime.now().format(format);
        System.out.println("currentDate == "+currentDate);

        if (identifier.equalsIgnoreCase("CurrentDate"))
            identifier = currentDate;

        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PRACTITIONER_TIMELINE_STORY_FIRST_RECORD, identifier));
        if (taskRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created log action on first index & unable to find newly created task for Practitioner: %s", identifier));
    }

    public void verifyPractitionerTaskDetailsSection() throws AutomationException {
        WebElement patientStory = driverUtil.getWebElement(PRACTITIONER_TASK_TABLE_HEADER);
        takeScreenshot();
        if (patientStory == null)
            throw new AutomationException("Practitioners Task details is not being displayed or it might taking too long time to load!");
    }

    public void verifyPractitionersTimelineDetailsSection() throws AutomationException {
        WebElement patientTimeline = driverUtil.getWebElement(PRACTITIONER_TIMELINE_TABLE_HEADER);
        if (patientTimeline == null)
            throw new AutomationException("Practitioners Timeline details is not being displayed or it might taking too long time to load!");
    }

    public void deleteAllLogActionsIfPresentInPractitionerTimeLineTable() throws AutomationException {
        List<WebElement> listOfItems = driverUtil.getWebElements(PRACTITIONER_LOG_ACTION_DELETE_ICON);
        if (listOfItems != null) {
            while (true) {
                WebElement listOfItem = driverUtil.getWebElement("//*[contains(@class,'PractitionerStoryTable-__iconBin')][1]");
                if (listOfItem == null)
                    break;
                listOfItem.click();
                WebElement deleteConfirmationButton = driverUtil.getWebElementAndScroll(DELETE_LOG_ACTION_CONFIRMATION_POPUP_DELETE_BUTTON);
                if (deleteConfirmationButton == null)
                    throw new AutomationException("Unable to find delete log action button on confirmation popup or it might taking too long time to load!");
                deleteConfirmationButton.click();
                waitForLoadingPage();
            }
        }
    }

    public void verifyAlertIconForOpenAllDRPsOptionAndItsTooltip(String tooltipMessage) throws AutomationException {
        WebElement patientTimeline = driverUtil.getWebElement(ALL_OPEN_DRPS_OPTIONS_ALERT_ICON);
        if (patientTimeline == null)
            throw new AutomationException("Unable to find Alert Icon for All DRP's Option or it might taking too long time to load!");

        actions.moveToElement(patientTimeline).perform();

        WebElement tooltipElement = driverUtil.findElement("//*[text()='All Open DRPs']//following::*[contains(@class,'tabler-icon-alert')]//..//..//*[@role='tooltip']");
        if (tooltipElement == null)
            throw new AutomationException("Unable to find Alert Icon tooltip message for All DRP's Option or it might taking too long time to load!");
        String tooltipText = tooltipElement.getText();

        System.out.println("TooltipMessage= " + tooltipText);
        if (!tooltipMessage.equalsIgnoreCase(tooltipText))
            throw new AutomationException("For Alert Icon we expect tooltip message is '" + tooltipMessage + "' but we found '" + tooltipText + "' this message");

        try {
            String alertIconColor = patientTimeline.getCssValue("stroke");
            String alertIconColorHexForm = Color.fromString(alertIconColor).asHex();
            System.out.println("alertIconColorHexForm= " + alertIconColorHexForm);
            if (!alertIconColorHexForm.equalsIgnoreCase(alertIconGrayColor))
                throw new AutomationException(String.format("Off toggle button colour should show " + alertIconGrayColor + " But its actually shows " + alertIconColorHexForm));
        } catch (Exception ex) {
        }
    }

    public void verifyAlertIconNotDisplayedForOpenAllDRPsOption() throws AutomationException {
        WebElement patientTimeline = driverUtil.getWebElement(ALL_OPEN_DRPS_OPTIONS_ALERT_ICON);
        if (patientTimeline != null)
            throw new AutomationException("We except Alert icon should not display but its visible for All Open DRPs Option!");
    }

    public void userSelectDRPOptionInFollowUpFaxPopup(String drpOption) throws AutomationException {
        if(drpOption.contains("Selected DRP")) {
            WebElement patientTimeline = driverUtil.getWebElement(String.format(SELECTED_DRP_OPTION_FOR_FOLLOW_UP_FAX, drpOption));
            if (patientTimeline == null)
                throw new AutomationException(drpOption + " Which DRP option not displayed on Provider - Follow Up Fax Pop-up");
            patientTimeline.click();
        }
        else {
            WebElement patientTimeline = driverUtil.getWebElement(String.format(ALL_OPEN_DRP_OPTION_FOR_FOLLOW_UP_FAX, drpOption));
            if (patientTimeline == null)
                throw new AutomationException(drpOption + " Which DRP option not displayed on Provider - Follow Up Fax Pop-up");
            patientTimeline.click();
        }
    }

    public void userSelectReportTypeInFollowUpFaxPopup(String reportType) throws AutomationException {
        WebElement patientTimeline = driverUtil.getWebElement(String.format(REPORT_TYPE_OPTION_FOR_FOLLOW_UP_FAX,reportType));
        if (patientTimeline == null)
            throw new AutomationException(reportType+" Report Type option not displayed on Provider - Follow Up Fax Pop-up");
        patientTimeline.click();
    }

    public void enterFaxNumberAndSelectThat(String faxNumber) throws AutomationException {
        WebElement element = driverUtil.getWebElement(FAX_INPUT_NUMBER, 5);
        if (element == null)
            throw new AutomationException("Fax input number is not displayed Fax Provider section: ");
        element.click();
        element.clear();
        element.sendKeys(faxNumber);

        WebElement radioButton = driverUtil.getWebElement("//*[contains(@class,'mantine-TextInput-input') and @placeholder='Enter a fax number...']//preceding::input[1]", 10);
        if (radioButton == null)
            throw new AutomationException("Fax input number radio button is not displayed Fax Provider section: ");
        radioButton.click();
    }

    public void userDeselectAllReportedDRPs() throws AutomationException {
        waitForLoadingPage();
        WebElement selectDeselectAllReportedDRPs = driverUtil.getWebElement(SELECT_ALL_REPORTED_DRPS);
        if (selectDeselectAllReportedDRPs == null) {
            throw new AutomationException("Unable to find select all Reported DRPs select box in table");
        }
        if (selectDeselectAllReportedDRPs.isSelected()) {
            selectDeselectAllReportedDRPs.click();
            if (selectDeselectAllReportedDRPs.isSelected())
                throw new AutomationException("All PReported DRPs were Selected but it should not selected");
        }
    }

    public void userSelectAllReportedDRPs() throws AutomationException {
        waitForLoadingPage();
        WebElement selectDeselectAllReportedDRPs = driverUtil.getWebElement(SELECT_ALL_REPORTED_DRPS);
        if (selectDeselectAllReportedDRPs == null) {
            throw new AutomationException("Unable to find select all Reported DRPs select box in table");
        }
        if (!selectDeselectAllReportedDRPs.isSelected()) {
            selectDeselectAllReportedDRPs.click();
            if (!selectDeselectAllReportedDRPs.isSelected())
                throw new AutomationException("All PReported DRPs were not selected but it should Selected!");
        }
        waitForLoadingPage();
    }

    public void userUpdateDRPsImplementationStatusFromDRPsPaneArea(String implementationStatus) throws AutomationException {
        waitForLoadingPage();
        WebElement implementationStatusDropDown = driverUtil.getWebElementAndScroll(IMPLEMENTATION_STATUS_DROPDOWN);
        if (implementationStatusDropDown == null)
            throw new AutomationException(String.format("implementation Status dropdown not displayed on screen"));
        implementationStatusDropDown.click();

        WebElement implementationStatusOption = driverUtil.getWebElement(String.format(IMPLEMENTATION_STATUS_OPTION_IN_DROPDOWN, implementationStatus));
        if (implementationStatusOption == null)
            throw new AutomationException(String.format("%s option not present in implementation Status dropdown", implementationStatus));
        implementationStatusOption.click();
    }

    public void verifyDRPsResponseDateIsUpdatedAsCurrentDate() throws AutomationException {
        WebElement responseDateInput = driverUtil.getWebElementAndScroll(String.format(RESPONSE_DATE_INPUT));
        if (responseDateInput == null)
            throw new AutomationException(String.format("Response date input not displayed on screen"));
        String responseDateInputValue = responseDateInput.getAttribute("value");

        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        System.out.println("Current Date= "+currentDateFormat);
        String currentDate = LocalDateTime.now().format(currentDateFormat);
        System.out.println("Current Date= "+currentDateFormat);

        if(!responseDateInputValue.equalsIgnoreCase(currentDate))
            throw new AutomationException(String.format("Response date input Value is not current date "+currentDate+" it showing: "+responseDateInputValue));
    }

    public void userUpdateDRPsPriorResponseDateFromDRPsPaneArea() throws AutomationException, ParseException {
        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter priorDateDayFormat = DateTimeFormatter.ofPattern("d");
        String priorDate = LocalDateTime.now().minusDays(1).format(currentDateFormat);
        String priorDateDay = LocalDateTime.now().minusDays(1).format(priorDateDayFormat);
        DateTimeFormatter currentMonthAndYearFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
        String currentMonthYear = LocalDateTime.now().format(currentMonthAndYearFormat);

        WebElement responseDateInput = driverUtil.getWebElementAndScroll(RESPONSE_DATE_INPUT);
        if (responseDateInput == null)
            throw new AutomationException(String.format("Response date input not displayed on screen"));
        responseDateInput.click();

        WebElement currentMonthInputElement = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderLevel')]");
        Date currentMonthInput = new SimpleDateFormat("MMMM yyyy").parse(currentMonthInputElement.getText());
        if (currentMonthInput == null)
            throw new AutomationException("Current Month and Year is not being displayed in advance task filter popup!");
        String currentMonthInputElements = currentMonthInputElement.getText();

        System.out.println("currentMonthInputElements == "+ currentMonthInputElements);

        Date expectedDateInMonths = new SimpleDateFormat("MMMM yyyy").parse(priorDate);
        System.out.println("expectedDateInMonths== "+ expectedDateInMonths);
        System.out.println("currentMonthYear== "+ currentMonthYear);

        while (!currentMonthYear.equals(currentMonthInputElements)) {
            if (expectedDateInMonths.before(currentMonthInput)) {
                WebElement previousMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-previous='true']");
                if (previousMonthYearChangeButton == null)
                    throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
                previousMonthYearChangeButton.click();
            }

            else if(expectedDateInMonths.after(currentMonthInput)){
                WebElement NextMonthYearChangeButton = driverUtil.getWebElement("//button[contains(@class,'CalendarHeader-calendarHeaderControl') and @data-next='true']");
                if (NextMonthYearChangeButton == null)
                    throw new AutomationException("Change Month and Year to next month year button is not being displayed Or it is in disabled state!");
                NextMonthYearChangeButton.click();
            }
            break;
        }
        driverUtil.waitForLoadingPage();
        WebElement priorDayDate = driverUtil.getWebElement("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='"+priorDateDay+"']");
        if (priorDayDate == null)
            throw new AutomationException("Unable to locate current date in calendar!");
        driverUtil.clickUsingJavaScript("//button[contains(@class,'DateInput') and not(@data-disabled) and text()='"+priorDateDay+"']");
        driverUtil.waitForLoadingPage();
        responseDateInput = driverUtil.getWebElement(RESPONSE_DATE_INPUT);
        String responseDateInputValue = responseDateInput.getAttribute("value");
        System.out.println("responseDateInputValue= "+responseDateInputValue);
        Date expectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(LocalDateTime.now().format(currentDateFormat));
        Date currentDateInput = new SimpleDateFormat("MMMM d, yyyy").parse(responseDateInputValue);

        if (!currentDateInput.before(expectedDate))
            throw new AutomationException(String.format("Response date is not changed '"+priorDate+"' but we expect it should be changed"));
    }

    public void verifyUserNotAbleToUpdateDRPsFutureResponseDateFromDRPsPaneArea() throws AutomationException {
        WebElement responseDateInput = driverUtil.getWebElement(String.format(RESPONSE_DATE_INPUT));
        if (responseDateInput == null)
            throw new AutomationException(String.format("Response date input not displayed on screen"));
        responseDateInput.click();

        List<WebElement> responseDateFutureDates = driverUtil.getWebElements(FUTURE_RESPONSE_DATE);
        if (responseDateFutureDates == null)
            throw new AutomationException("Unable to locate Response future date");
        for(int i=1; i<responseDateFutureDates.size(); i++){
            String statusOfFutureDate = responseDateFutureDates.get(i).getAttribute("disabled");
            if(statusOfFutureDate==null)
                throw new AutomationException(String.format("Response date input Calendar future value is not disabled and it is selectable by User"));
        }
    }

    public void verifyPatientsTabPatientDOBIsSameAsDRPsTableInPractitionersTab(String practitioner) throws AutomationException, ParseException {
        waitForLoadingPage();
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        int columnIndex = PractitionersPage.getReportedDRPTableColumnHeadingIndex("DoB")-2;
        WebElement selectedPractitioner = driverUtil.getWebElementAndScroll("//table//tbody//*[text()='"+ practitioner +"']//following::td["+columnIndex+"]");
        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " Implementation status in Implementation status column");
        String doBInTable = selectedPractitioner.getText();
        System.out.println("doBInTable =====" + doBInTable);

        Date expectedDateInMonths = new SimpleDateFormat("M/d/yyyy").parse(doBInTable);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        String formattedDate = dateFormat.format(expectedDateInMonths);

        System.out.println("Formatted Date: " + formattedDate);
        PageFactory.practitionersPage().verifyAfterSelectPractitionerUserRedirectToPatientTab(practitioner);
        PageFactory.patientPage().verifyPatientInfo("Name",practitioner);
        PageFactory.patientPage().verifyPatientInfo("DOB", formattedDate);
    }

    public void verifyDRPsPaneElementsAndItsValue(String element,String value) throws AutomationException {
        WebElement drpsPaneElementAndValue = driverUtil.getWebElement("//*[contains(@class,'react-draggable')]/following::*//child::*[contains(text(),'"+element+"')]//..//..//*[text()='"+value+"']");
        if (drpsPaneElementAndValue == null)
            throw new AutomationException(String.format(element+" This Element not present in DRP's pane area or Element dose not contains '"+value+"' value"));
    }

    public void verifyOptionsPresentInDRPsPaneDropDown(String dropdownName,List<String> option) throws AutomationException {
        WebElement btn = driverUtil.getWebElement("//*[" + driverUtil.ignoreCase(dropdownName) + "]//following::*//child::input[contains(@class,'Select-input')]");
//        WebElement btn = driverUtil.getWebElement("//*[contains(text(),'Response Method')]//following::*//child::input[contains(@class,'Select-input')]");
        if (btn == null)
            throw new AutomationException("Unable to locate " + dropdownName + " button or it is in disabled state");
        btn.click();
        for (String optionsInDropDown : option)
        {
            WebElement optionPresentInDropDown = driverUtil.getWebElement(String.format(CAMPAIGNS_ADVANCE_FILTER_DROPDOWN_OPTION, optionsInDropDown));
            if (optionPresentInDropDown == null)
                throw new AutomationException(String.format("Option %s not displayed in dropDown", optionsInDropDown));
        }
        btn.click();
    }

    public void verifyTableHeadingColumnsNameContainsTooltip(String columnName,String tooltipMessage) throws AutomationException {
        waitForLoadingPage();
        WebDriverUtil.waitForAWhile(3);
        WebElement columnHeadingName = driverUtil.findElementAndScroll(String.format(COLUMN_HEADING_NAME, columnName));
        if (columnHeadingName == null)
            throw new AutomationException("Couldn't locate column '" + columnName + "' in table");
        actions.moveToElement(columnHeadingName).perform();
        WebElement tolTipMessage = driverUtil.getWebElement("//*[@role='tooltip']");
        if (tolTipMessage == null)
            throw new AutomationException("Couldn't locate the Tooltip for '"+columnHeadingName+"' column or it might become invisible: '");
        System.out.println("TolTip Message= " + tolTipMessage.getText());

        if (!tolTipMessage.getText().equals(tooltipMessage))
            throw new AutomationException("Expected tooltip '" + tooltipMessage + "' not contains when mouse hover action perform on column: '" + columnName + "'");
    }

    public void verifyFollowingAppliedAdvancedFilterOptionsVisible(String appliedFilter) throws AutomationException {
        WebElement filteredOptions = driverUtil.getWebElement(String.format(APPLIED_ADVANCE_FILTERED_OPTIONS,appliedFilter));
        if (filteredOptions == null)
            throw new AutomationException("Advanced filtered options should be visible, but they are not.");
    }

    public void verifyFollowingAppliedAdvancedFilterOptionsNotVisible(String appliedFilter) throws AutomationException {
        WebElement filteredOptions = driverUtil.getWebElement(String.format(APPLIED_ADVANCE_FILTERED_OPTIONS,appliedFilter));
        if (filteredOptions != null)
            throw new AutomationException("Advanced filtered options should not be visible, but it is visible!");
    }

    public void verifyUserNotAbleToSeeOptionsInTheAdvanceFilterPopup(List<String> columnNames) throws AutomationException {
        WebElement selectTaskFilterIcon = driverUtil.getWebElement(CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectTaskFilterIcon == null)
            throw new AutomationException("No filter search icon visible in Campaigns tab!");
        selectTaskFilterIcon.click();

        try{
        for(String filterName :columnNames) {
            WebElement campaignAdvanceFilterInput = driverUtil.getWebElement(String.format(OPTIONS_IN_ADVANCE_FILTERED_POPUP, filterName));
            if (campaignAdvanceFilterInput != null)
                throw new AutomationException(filterName + " filter option defined in advance filter popup but it should not be there!");
        }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            actions.sendKeys(Keys.ESCAPE).build().perform();
            clickOnButtonPresentInCampaignsTab("Cancel");
        }
    }
    public void verifyUserAbleToSeeOptionsInTheAdvanceFilterPopup(List<String> columnNames) throws AutomationException {
        WebElement selectTaskFilterIcon = driverUtil.getWebElement(CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectTaskFilterIcon == null)
            throw new AutomationException("No filter search icon visible in Campaigns tab!");
        selectTaskFilterIcon.click();

        try{
        for(String filterName :columnNames) {
            WebElement campaignAdvanceFilterInput = driverUtil.getWebElementAndScroll(String.format(OPTIONS_IN_ADVANCE_FILTERED_POPUP, filterName));
            if (campaignAdvanceFilterInput == null)
                throw new AutomationException("No '" + filterName + "' filter option defined in advance filter popup!");
        }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            actions.sendKeys(Keys.ESCAPE).build().perform();
            clickOnButtonPresentInCampaignsTab("Cancel");
        }
    }

    public void verifyGlobalTabContainsDropDownAndItContainFollowingOrgs(String globalTab, List<String> orgName) throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElementAndScroll(String.format(TABS_DROPDOWN_XPATH, globalTab));
        if (element == null) {
            element = driverUtil.getWebElementAndScroll(String.format(TABS_DROPDOWN_XPATH, "Reports"));
            if (element == null)
                throw new AutomationException(String.format(globalTab + " Global Tab is not present!"));
            element.click();
        }
        element.click();

        for (String optionsInDropDown : orgName) {
            WebElement optionPresentInDropDown = driverUtil.getWebElement(String.format(BUTTON_OTIONS_IN_GLOBAL_TAB_DROPDOWN, optionsInDropDown));
            if (optionPresentInDropDown == null)
                throw new AutomationException(String.format("Option %s not displayed in %s dropDown", optionsInDropDown, globalTab));
        }
    }

    public void clickOnGlobalTabAndSelectDropDownOrgOption(String globalTab, String orgName) throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElementAndScroll(String.format(TABS_DROPDOWN_XPATH, globalTab));
        if (element == null) {
            element = driverUtil.getWebElementAndScroll(String.format(TABS_DROPDOWN_XPATH, "Reports"));
            if (element == null)
                throw new AutomationException(String.format(globalTab + " Global Tab is not present!"));
            element.click();
        }
        element.click();

        WebElement optionPresentInDropDown = driverUtil.getWebElement(String.format(BUTTON_OTIONS_IN_GLOBAL_TAB_DROPDOWN, orgName));
        if (optionPresentInDropDown == null)
            throw new AutomationException(String.format("Option %s not displayed in %s dropDown", orgName, globalTab));
        optionPresentInDropDown.click();
        waitForLoadingPage();
    }

    public void verifyGlobalTabSelectedTab(String globalTab) throws AutomationException {
        waitForLoadingPage();
        if (!PageFactory.homePage().isMenuSelected(globalTab))
            throw new AutomationException(String.format("%s Global tab is not displayed", globalTab));
    }

    public void verifyFaxToggleButtonRemovedFromPractitionersTab() throws AutomationException {
        WebElement element = driverUtil.getWebElement(FAX_TOGGLE);
        if (element != null)
            throw new AutomationException("User able to find Red color fax toggle button But it should not present!");
    }

    public void verifyColumnNotAvailableInTheReportsTableList(List<String> columnName) throws AutomationException {
        WebElement filterOn = driverUtil.findElement("//button//*[contains(@class,'tabler-icon tabler-icon-adjustments') and not(contains(@class,'off'))]");
        if (filterOn != null) {
            filterOn.click();
        }
        for (String columnNamesInTable : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(REPORTS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element != null)
                throw new AutomationException(String.format("The column %s is visible in the list table, however, it should not appear in the table.", columnNamesInTable));
        }
    }

    public void verifyBulkActionForMultipleRecords(int numberOfRecord, String followupStatus) throws AutomationException {
        WebElement practitioner = null;
        String practitionerName ;
        //Update Status
        for(int i=1; i<=numberOfRecord; i++){
            practitioner = driverUtil.findElement("//table//thead//..//tr["+i+"]//td[3]//*[contains(@class,'mantine-Text')]");
                waitForLoadingPage();
            practitionerName = practitioner.getText();
                // Select Searched Practitioner
                userSelectPractitionerInCampaignTabTable(practitionerName);
        }
        userClicksOnDropDownAndEditFollowUpStatus("Select Bulk Action","Edit Follow Up Status",followupStatus);

        //Verify Status Updated
        for(int i=1; i<=numberOfRecord; i++){
            practitioner = driverUtil.findElement("//table//thead//..//tr["+i+"]//td[3]//*[contains(@class,'mantine-Text')]");
           practitionerName = practitioner.getText();
           verifyUpdatedFollowUpStatusInFollowUpColumnInCampaign(followupStatus,practitionerName);
        }
    }

    public void verifyRunDropdownDataOrdering(String dataOrdering) throws AutomationException {
        waitForLoadingPage();
        WebElement campaignButton = driverUtil.getWebElement(CAMPAIGNS_RUN_SELECT_DROPDOWN);
        if (campaignButton == null)
            throw new AutomationException(String.format("Campaigns dropdown not displayed on screen"));
        campaignButton.click();

        List<WebElement> campaignDateElement = driverUtil.getWebElements(RUN_DROPDOWN_OPTIONS);
        if (campaignDateElement == null)
            throw new AutomationException(String.format("Campaigns dates not present in campaigns dropdown", campaignButton));

        List<Date> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (WebElement dateStr : campaignDateElement) {
            try {
                dates.add(sdf.parse(String.valueOf(dateStr.getText())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        System.out.println("campaignDateElement dates= "+dates);
        if (dataOrdering.equalsIgnoreCase("DESCENDING") || dataOrdering.toLowerCase().contains("descending")) {
            for (int i = 0; i < dates.size() - 1; i++) {
                if (dates.get(i).before(dates.get(i + 1))) {
                    throw new AutomationException(" - Run Dates Dropdown Values was expected to be sorted in descending order but its not");
                }
            }
        }
        if (dataOrdering.equalsIgnoreCase("ASCENDING") || dataOrdering.toLowerCase().contains("ascending")) {
            for (int i = 0; i < dates.size() - 1; i++) {
                if (dates.get(i).after(dates.get(i + 1))) {
                    throw new AutomationException(" -  Run Dates Dropdown Values was expected to be sorted in descending order but its not");
                }
            }
        }
    }

    public void userReduceScreenResolution(int zoomPercentage) throws AutomationException {
        try {
            WebDriver driver = DriverFactory.getInstance().initDriver("chrome");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom = '" + zoomPercentage + "%'");
        } catch (Exception ex) {
            //do Nothing
        }
    }


//


    public void removeOption(String option) throws AutomationException {
        WebElement button = driverUtil.getWebElement("//*[contains(text(),'"+option+"')]/following-sibling::button");
        button.click();
    }

    public void selectOption(String selectOption) throws AutomationException {
        WebElement btn = driverUtil.getWebElement(COMBOBOX_BUTTON);
        btn.click();

        List<WebElement> options = driverUtil.getWebElements("//*[@role='option']");
        for (WebElement option : options) {
            if (option.getText().equals(selectOption)) {
                option.click();
                break;
            }
        }

    }

    public void clickOnLogIconInPractitionerTable(Integer rowNum) throws AutomationException {
        WebElement logBtn = driverUtil.getWebElement("//*[contains(text(),'Report History')]/parent::div/parent::div/following-sibling::div//table//tr["+rowNum+"]/td[last()]//div//div");
        logBtn.click();
    }

    public static void clickOnColumnShowHideIconButton(String tableName) throws AutomationException {
        waitForLoadingPage();
        scrollToTop();

        WebElement dotButton = driverUtil.findElement("//*[text()='"+tableName+"']//..//..//button[contains(@class,'mantine-ActionIcon') and @aria-haspopup='dialog']");
        if(dotButton!=null)
            dotButton.click();
        WebElement hideShowButton = driverUtil.getWebElement(String.format(HIDE_SHOW_ICON_TABLE_COLUM_BUTTON,tableName));
        if (hideShowButton == null)
            throw new AutomationException(String.format("Hide Show button not displayed for reports table"));
        hideShowButton.click();
        driverUtil.waitForLoadingPage();
    }

    public void clickOnButtonToShowAllHiddenColumnsInTable(String text,String tableName) throws AutomationException {
        clickOnColumnShowHideIconButton(tableName);
        driverUtil.waitForLoadingPage();
        WebElement button = driverUtil.getElementUsingJavascript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
        if (button != null)
            driverUtil.clickUsingJavaScript("//*[text()='" + text + "']//ancestor::button[not(@disabled)]");
//            button.click();
        driverUtil.waitForLoadingPage();
        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.sendKeys(Keys.ESCAPE).build().perform();
        closeShowHidePopup();

        WebElement dotButton = driverUtil.findElement("//*[text()='"+tableName+"']//..//..//button[contains(@class,'mantine-ActionIcon') and @aria-haspopup='dialog' and @aria-expanded='true']");
        if(dotButton!=null)
            dotButton.click();
        waitForLoadingPage();
    }
}
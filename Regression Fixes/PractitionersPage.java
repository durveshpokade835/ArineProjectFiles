package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import com.arine.automation.util.WebDriverUtil;
import com.google.common.collect.Ordering;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;
import static com.arine.automation.pages.AdminPortalHomePage.driverUtil;
import static com.arine.automation.pages.PatientMTMFieldPage.FIELD_LOCATOR;
import static com.arine.automation.pages.PatientPage.scrollToTop;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class PractitionersPage extends BasePage {
    CommonSteps common = new CommonSteps();
    public static final String PRACTITIONERS_SEARCH_INPUT = "//input[@name='nameInput']";
    public static final String PRACTITIONER_DETAILS = "//*[contains(@class,'Practitioners-__topTable')]//td";
    public static final String PRACTITIONER_NAME_INDEX = "[1]";
    public static final String PRACTITIONER_NPI_INDEX = "[2]";
    public static final String PRACTITIONER_SPECIALITY_INDEX = "[3]";
    public static final String PRACTITIONER_ADDRESS_INDEX = "[4]";
    public static final String PRACTITIONER_LAST_CONSULTATION_INDEX = "[5]";
    public static final String PRACTITIONER_PROFILE_DISPLAY = "//*[contains(@class,'LandingPage-components-units-PractitionerInput-__container')]//form";
    public static final String PRACTITIONER_PERSONAL_INFO_LABEL = "//*[contains(@class,'PractitionerInput')]//*[text()='%s']";
    public static final String PRACTITIONER_PERSONAL_INFO_INPUT = "//*[contains(@class,'PractitionerInput')]//input[@name='%s']";
    public static final String PRACTITIONER_GLOBAL_DETAILS = "//*[contains(@class,'__topTable')]//td[%s]";
    public static final String SENT_RECS_TABLE_COLUMNS_HEADING_NAME = "//*[contains(@class,'GlobalPractMiddleTable-__headerTable')]/..//th[text()='%s']";

    public static final String SENT_RECS_TABLE = "//*[text()='Sent Recs']/../..//*[contains(@class,'GlobalPractMiddleTable-__tableContainer')]";
    public static final String TIMELINE_TABLE_IN_PRACTITIONERS_TAB = "//*[contains(@class,'GlobalPractMiniTableTimeline')]";
    public static final String COLUMN_IN_TIMELINE_TABLE = "//*[contains(@class,'GlobalPractMiniTableTimeline')]//tr//th[text()='%s']";
    public static final String INPUT_OPTION_IN_PRACTITIONERS_INFO_PANEL = "//*[contains(@class,'PractitionerInput') and text()='%s']";
    public static final String CONFIRMED_INPUT_IN_PRACTITIONERS_INFO = "(//*[contains(@name,'confirmed')]/../*[contains(@class,'PractitionerInput-__checkbox')])[1]";
    public static final String DATE_RECORD_IN_DATE_COLUMN = "//*[contains(@class,'TableTimeline-__tableContainer')]//ancestor::table//tbody/tr//td[1]";
    public static final String REPORT_SENT_TABLE_IN_PRACTITIONERS_TAB = "//*[contains(@class,'GlobalPractMiniTableReportsSent')]";
    public static final String COLUMN_IN_REPORTS_SENT_TABLE = "//*[contains(@class,'GlobalPractMiniTableReportsSent')]//tr//th[text()='%s']";
    public static final String FIRST_RECORD_FROM_REPORTS_TABLE_IN_REPORTS_TAB = "(//*[contains(@class,'ReportTable-__resptablerow')]//ancestor::*[contains(@class,'tablebodycellName')])[1]";
    public static final String SELECTED_ACTIVE_GLOBAL_TAB = "//*[contains(@class,'activeButton')]//ancestor::*[text()='%s']";
    public static final String DATE_RECORD_IN_CREATED_COLUMN = "//*[contains(@class,'TableReportsSent-__tableContainer')]//ancestor::table//tbody/tr//td[1]";
    public static final String FIRST_RECORD_FROM_ADVANCED_PRACTITIONER_SEARCH_TABLE = "//*[contains(@class,'SearchPractitioners-__tableContainer')]//tr[1]//td[1]";
    public static final String REPORTS_TAB_TABLE_COLUMN_HEADING = "//*[contains(@class,'ReportTable-__tableHeader')]/..//th[text()='Fax']";

    public static final String CAMPAIGNS_SELECT_DROPDOWN = "//input[contains(@class,'Select-input') and @placeholder='Pick one']";
    public static String CAMPAIGNS_SELECT_OPTION_IN_DROPDOWN = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static final String REPORTS_HISTORY_TABLE_IN_TAB = "//*[contains(text(),'Report')]// following::table// thead//*[contains(@class,'mantine-TableHeadCell')]//div[text()='%s']";
    public static final String TABLE_IN_PRACTITIONERS_TAB = "//*[text()='%s']/../../..//table";
    public static final String LOG_ACTION_ICON_IN_REPORT_HISTORY_TABLE = "//*[contains(text(),'Report')]/..//..//..// table// tr[1]// td//..//*[contains(@class,'tabler-icon-file-pencil')]";
    public static final String EVENT_TYPE_STATUS_IN_REPORT_HISTORY_TABLE = "//*[contains(@class,'Table-root mantine-1tbzx6m')]//tbody//tr//td[text()='%s']";
    //public static final String SENT_STATUS_IN_REPORT_HISTORY_TABLE = "//*[contains(@class,'Table-root mantine-1tbzx6m')]//tbody//tr//td//div[text()='%s']";
    public static final String SENT_STATUS_IN_REPORT_HISTORY_TABLE = "//*[contains(@class,'Table-root mantine')]//tr[1]//td//descendant-or-self::*[text()='%s']";
    public static final String DELETE_ICON_PRACTITIONER_TIME = "(//*[contains(@class,'PractitionerStoryTableV2-__clickableDiv___2m2gk')])[%s]";
    public static final String DELETE_ICON_CONFIRMATION = "//*[contains(@class,'mantine-Button-root mantine')]//span[text()='%s']";
    public static final String REPORTED_DSR_COLUM = "(//*[contains(@class,'mantine-')]//div[text()='%s'])[1]";
    public static final String PATIENT_DETAILS = "//*[contains(@class,'mantine-Grid-root mantine')]//div[text()='%s']";
    public static final String FAX_PROVIDER_BUTTON="//*[contains(@class,'mantine-Button-root')]//span[text()='Fax Provider']";
    public static final String FAX_PROVIDER_RADIO_BUTTON="//*[contains(@class,'mantine-Stack-root')]//label[text()='%s']//preceding::input[1]";
    public static final String ALL_OPEN_DRP_RADIO_BUTTON="//*[contains(@class,'mantine-Stack-root')]//div[text()='DRPs from Most Recent Run']//preceding::input[1]";
    public static final String CLOSE_BUTTON_FAX_PROVIDER="//button[contains(@class,'mantine-CloseButton-root mantine-Modal-close')]";
    public static final String SEND_CANCEL_BUTTON_FAX_PROVIDER="//*[contains(@class,'mantine-Group-root mantine')]//span[text()='%s']";
    public static final String FAX_INPUT_NUMBER="//*[contains(@class,'mantine-TextInput-input') and @placeholder='Enter a fax number...']";
    public static final String FAX_NOTE_TEXT="//*[contains(@class,'mantine-Textarea') and @placeholder='']";
    public static final String FAX_PROVIDER_HEADER_DETAILS="//*[contains(@class,'mantine-Stack-root')]//div[text()='%s']";
    public static final String DRPS_TABLE_CHECK_BOX="//*[contains(@class,'mantine-')]//div[text()='%s']//preceding::input[@type='checkbox'][1]";
    public static final String SEARCH_BUTTON="//*[contains(@class,'tabler-icon-search')]";
    public static final String SEARCH_NAME_FIELD="//*[contains(@class,'mantine-InputWrapper-root mantine-TextInput-root')]//label[text()='%s']";
    public static final String SEARCH_ALGORITHM_FIELD="//*[contains(@class,'mantine-InputWrapper-root mantine')]//label[text()='%s']";
//    public static final String CLEAR_FILTER="//*[contains(@class,'mantine-Badge-root')]//span[text()='%s']";
    public static final String CLEAR_FILTER="//span[text()='%s']//..//descendant::button[contains(@class,'mantine-UnstyledButton-root')]";
    public static final String DATE_BUTTON="//*[contains(@class,'mantine-DatePickerInput')]//label[text()='%s']";
    public static final String CALENDER_BUTTON="//*[contains(@class,'mantine-DatePickerInput-calendarHeaderLevel')]";
    public static final String YEAR_SELECTION="//*[contains(@class,'mantine-DatePickerInput-yearsList')]/..//button[text()='%s']";
    public static final String MONT_DATE_SELECTION="//*[contains(@class,'mantine-DatePickerInput-month')]/..//button[text()='%s']";
    public static final String CALL_TOGGEL="//*[contains(@class,'iconComponents-phone-__mainIcon')]";
    public static final String CALL_TOGGEL_COLOR="//*[contains(@style,'%s') and contains(@class,'iconComponents-phone-__indicatorIcon') ]";
    public static final String FAX_TOGGEL="//*[contains(@class,'LandingPage-components-iconComponents-fax')]";
    public static final String FAX_TOGGEL_COLOR="//*[contains(@style,'%s') and contains(@class,'iconComponents-fax-__indicatorIcon') ]";
    public static final String COLUMN_FILTER="//*[contains(@class,'tabler-icon tabler-icon-adjustments')]";
    public static final String SHOW_HIDE_COLUMN="//*[contains(@class,'tabler-icon tabler-icon-columns')]";
    public static final String SHOW_HIDE_COLUMN_HEADER="//*[contains(@class,'mantine-Button-label') and text()='%s']";
    public static final String SHOW_HIDE_PIN_UNPIN="//*[contains(@class,'mantine-Switch-label') and text()='%s']";
    public static final String JUMP_PAGE="//*[contains(@class,'mantine-TextInput-input') and @aria-label='jump to page input']";
    public static final String GO_BUTTON="//*[contains(@class,'mantine-1wa7zwr')]//following-sibling::button";
    public static final String RADIO_BUTTON_LOG="//*[@id='%s']";
    public static final String REPORTED_DRP_ADVANCE_FILTER_DROPDOWN = "//*[text()='%s']//following-sibling::*//input[contains(@placeholder,'%s')]";
    public static final String REPORTED_DRP_ADVANCE_FILTER_INPUT = "//*[text()='%s']//following-sibling::*/input[contains(@placeholder,'%s')]";

    public static final String REPORTED_DRP_ADVANCE_FILTER_DROPDOWN_OPTION = "//*[@role='option' and text()='%s']";
    public static final String REPORT_TABLE_COLUMN_HEADING_NAMES = "//*[text()='Reported DRPs']//following::table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper')]";
    public static final String REPORT_HISTORY_TABLE_COLUMN_HEADING_NAMES = "//*[text()='Report History']//following::table[1]//thead//div[contains(@class,'TableHeadCell-Content-Wrapper')]";
    public static final String REPORT_DRP_TABLE_COLUMN_DATA = "//*[text()='Reported DRPs']//following::table//thead//..//td[%s]";
    public static final String REPORT_HISTORY_TABLE_COLUMN_DATA = "//*[text()='Report History']/following::table[1]//thead//..//td[%s]";
//    String dateRange2,dateRange1 = null;
    String finalDate,fromDate = null, currentDay=null;

    public static final String MOVE_BUTTON="(//*[text()='%s']//preceding::button[3])[2]";
    public static final String COLUM_RESIZE="//*[text()='%s']//following::th[contains(@class,'mantine')][1]";
    public static final String PRACTITIONERS_TAB_TABLE_COLUMNS="//*[text()='%s']//following::table//*[contains(@class,'TableHeadCell') and @title='%s']";
    public static final String PATIENT_NAME_CELL_IN_DRP_TABLE = "//table//tbody//*[text()='%s']";
    public static final String PA_FEATURE_ADVANCE_FILTERED_SELECTED_OPTION = "//*[contains(@class,'badgeGroup')]/descendant::div[contains(text(),'%s')]/descendant::span[contains(@class,'badgeText')]";
    public static final String PRACTITIONER_DETAIL_PANE_LOCATOR = "//*[contains(@class,'mantine-Grid-root')]//*[contains(text(),'Implementation Status')]";
    private static final String PRACTITIONER_LOCATOR = "//table//tbody//td//*[text()='%s']//following::td[contains(@class,'mantine')][1]";
    public static String INITIAL_VALUE;
    private static final String INPUT_FIELD_LOCATOR = "//div[contains(@class,'mantine-Grid-col')]/*[contains(text(),'%s')]/following::input[contains(@class,'mantine-Input-input')][1]";
    private static final String DROPDOWN_OPTION_LOCATOR = "//div[contains(@class,'mantine-Select-item')]/*[text()='%s']";
    private static final String PRACTITIONER_SELECT_ICON = "//table//tbody//td//*[text()='%s']//preceding::div[contains(@class,'mantine-Checkbox-inner')][1]";
    private static final String TABLE_ITEM_LOCATOR = "(//table//thead//..//tr[%s]//td[2]//*[contains(@class,'mantine-Text')])[1]";
    public static final String LOG_ACTION_FIELDS_LOCATOR = "//*[contains(@class,'mantine-Stack-root')]//following::input[contains(@placeholder,'Select %s')]";
    public static final String LOG_ACTION_FIELDS_VALUES = "//*[contains(@class,'mantine-Select-item') and text()='%s']";
    public static final String PATIENT_RECOMMENDATION_FIELD = "//*[contains(text(),'Patient Recommendations')]/ following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select row')][1]";
    public static final String LOG_ACTION_BUTTON_LOCATOR = "//*[@type='button']//*[contains(text(),'Continue') or contains(text(),'%s')]";


    public static Map<String, Integer> GLOBAL_DETAILS_TAB_MAPPING = new HashMap<>();

    static {
        GLOBAL_DETAILS_TAB_MAPPING.put("Name", 1);
        GLOBAL_DETAILS_TAB_MAPPING.put("NPI", 2);
        GLOBAL_DETAILS_TAB_MAPPING.put("Speciality", 3);
        GLOBAL_DETAILS_TAB_MAPPING.put("Address", 4);
        GLOBAL_DETAILS_TAB_MAPPING.put("Last Consultation", 5);
    }

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

    public void searchPractitioner(String id) throws AutomationException {
        PageFactory.homePage().gotoMenu("Practitioners");
        WebElement searchInput = driverUtil.getWebElement(PRACTITIONERS_SEARCH_INPUT);
        if (searchInput == null)
            throw new AutomationException("Unable to locate practitioners search input!");
        String existing = searchInput.getAttribute("value");
        if (existing == null || !existing.equalsIgnoreCase(id)) {
            searchInput.sendKeys(Keys.CONTROL + "a");
            searchInput.sendKeys(Keys.BACK_SPACE);
            searchInput.clear();
            searchInput.sendKeys(id, Keys.ENTER);
            if (!checkErrorPopup()) {
                WebDriverUtil.waitForVisibleElement(By.xpath(HomePage.SEARCH_BUTTON));
                driverUtil.waitForLoadingPage();
            }
        }

    }

    public String getPractitionerDetail(String type) throws AutomationException {
        return driverUtil.getWebElementAndScroll(PRACTITIONER_DETAILS + type, WebDriverUtil.MAX_ELEMENT_WAIT).getText();
    }

    public void verifyPractitionerDetailsSection() throws AutomationException {
        WebElement patientDetailsSection = driverUtil.getWebElementAndScroll(PRACTITIONER_PROFILE_DISPLAY, WebDriverUtil.NO_WAIT);
        if (patientDetailsSection == null)
            clickOnNameLink();
        patientDetailsSection = driverUtil.getWebElementAndScroll(PRACTITIONER_PROFILE_DISPLAY);
        if (patientDetailsSection == null)
            throw new AutomationException("Practitioner details section is not being displayed!");
    }

    public void clickOnPractitionerSearchIcon() throws AutomationException {
        WebDriverUtil.waitForVisibleElement(By.xpath(HomePage.SEARCH_BUTTON));
        WebElement searchIcon = driverUtil.getWebElement(HomePage.SEARCH_BUTTON);
        if (searchIcon == null)
            throw new AutomationException(String.format("Patient Search Icon not displayed on screen"));
        searchIcon.click();
        waitForLoadingPage();
    }

    public void veryPractitionerPersonalDetail(String label, String expectedValue) throws AutomationException {
        verifyPractitionerDetailsSection();
        String nameAttribute = PRACTITIONER_DETAILS_INPUT_MAPPING.get(label);
        WebElement infoInput = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_PERSONAL_INFO_INPUT, nameAttribute));
        if (infoInput == null)
            throw new AutomationException(String.format("%s input box is not being displayed!", label));
        String actualValue = infoInput.getAttribute("value");
        if (!actualValue.toLowerCase().contains(expectedValue.toLowerCase()))
            throw new AutomationException(String.format("%s details is not being matched! Expected value: %s but found: %s", label, expectedValue, actualValue));
    }

    public void updatePractitionerPersonalDetail(String label, String value) throws AutomationException {
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
        clickOnNameLink();
    }

    public void clickOnNameLink() throws AutomationException {
        int index = GLOBAL_DETAILS_TAB_MAPPING.get("Name");
        WebElement patientInfo = driverUtil.findElementAndScroll(String.format(PRACTITIONER_GLOBAL_DETAILS, index));
        if (patientInfo == null)
            throw new AutomationException("Unable to find name link!");
        patientInfo.click();
        WebDriverUtil.waitForAWhile(2);
    }

    public void verifyColumnsAvailableInSentRecsTable(List<String> columnName) throws AutomationException {
        for (String columnNamesInTable : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(SENT_RECS_TABLE_COLUMNS_HEADING_NAME, columnNamesInTable));
            if (element == null)
                throw new AutomationException(String.format("column %s not displayed in sent recs table", columnNamesInTable));
        }
    }

    public void verifySentRecsTableInPractitionersPage() throws AutomationException {
        WebElement element = driverUtil.getWebElement(SENT_RECS_TABLE, WebDriverUtil.WAIT_5_SEC);
        if (element == null)
            throw new AutomationException(String.format("Sent recs table not displayed in practitioners tab"));
    }

    public void verifyColumnNotDisplayedInSentRecsTable(String columnName) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(SENT_RECS_TABLE_COLUMNS_HEADING_NAME, columnName));
        if (element != null)
            throw new AutomationException(String.format("column %s displayed in sent recs table, But it should not be displayed", columnName));
    }

    public void verifyTimelineTableInPractitionersTab() throws AutomationException {
        WebElement element = driverUtil.getWebElement(TIMELINE_TABLE_IN_PRACTITIONERS_TAB, WebDriverUtil.WAIT_5_SEC);
        if (element == null)
            throw new AutomationException(String.format("Timeline table not displayed in practitioners tab"));
    }

    public void verifyColumnInTimelineTable(String columnName) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(COLUMN_IN_TIMELINE_TABLE, columnName));
        if (element == null)
            throw new AutomationException(String.format("%s Column has not present in Timeline table!", columnName));
    }

    public void verifyInputPresentInPractitionersInfoPanel(String columnName) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(INPUT_OPTION_IN_PRACTITIONERS_INFO_PANEL, columnName));
        if (element == null)
            throw new AutomationException(String.format("%s Column has not present in Timeline table!", columnName));
    }

    public void verifyPractitionersConfirmationDateInTimelineTable() throws AutomationException {
        WebElement element = driverUtil.getWebElementAndScroll(CONFIRMED_INPUT_IN_PRACTITIONERS_INFO);
        if (element == null)
            throw new AutomationException("Practitioners Confirmed input has not present in practitioners information panel!");
        element.click();
        ArrayList<String> updatedDateList = new ArrayList<>();
        List<WebElement> dateRecords = driverUtil.getWebElements(DATE_RECORD_IN_DATE_COLUMN);
        for (int i = 0; i < dateRecords.size(); i++) {
            System.out.println("List size= " + dateRecords.get(i).getText());
            updatedDateList.add(dateRecords.get(i).getText());
        }
        updatedDateList.removeAll(Arrays.asList(" ", null));
        System.out.println("Modified List Size = " + (updatedDateList.size()));
        WebElement lastUpdatedDate = driverUtil.findElementAndScroll("(//*[contains(@class,'TableTimeline-__tableContainer')]//ancestor::table//tbody//td[1])[" + updatedDateList.size() + "]");
        if (lastUpdatedDate == null)
            throw new AutomationException("Unable to find last updated date in timeline table");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");
        String currentDate = LocalDateTime.now().format(format);

        if (!lastUpdatedDate.getText().equals(currentDate))
            throw new AutomationException("Last updated date in table is '" + lastUpdatedDate.getText() + "' but we expected it as '" + currentDate + "'");
    }

    public void verifyLastUpdatedDateDisplayedLastInDateColumn() throws AutomationException {
        WebElement element = driverUtil.getWebElementAndScroll(CONFIRMED_INPUT_IN_PRACTITIONERS_INFO);
        if (element == null)
            throw new AutomationException("Practitioners Confirmed input has not present in practitioners information panel!");
        element.click();
        ArrayList<String> updatedDateList = new ArrayList<>();
        List<WebElement> dateRecords = driverUtil.getWebElements(DATE_RECORD_IN_DATE_COLUMN);
        for (int i = 0; i < dateRecords.size(); i++) {
            System.out.println("List size= " + dateRecords.get(i).getText());
            updatedDateList.add(dateRecords.get(i).getText());
        }
        updatedDateList.removeAll(Arrays.asList(" ", null));
        WebElement lastUpdatedDate = driverUtil.findElementAndScroll("(//*[contains(@class,'TableTimeline-__tableContainer')]//ancestor::table//tbody//td[1])[" + updatedDateList.size() + "]");
        if (lastUpdatedDate == null)
            throw new AutomationException("Unable to find last updated date in timeline table");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");
        String currentDate = LocalDateTime.now().format(format);

        if (!lastUpdatedDate.getText().equals(currentDate))
            throw new AutomationException("Last updated date in table is '" + lastUpdatedDate.getText() + "' but we expected it as '" + currentDate + "'");
    }

    public void verifyReportSentTableInPractitionerTab() throws AutomationException {
        WebElement element = driverUtil.getWebElement(REPORT_SENT_TABLE_IN_PRACTITIONERS_TAB, WebDriverUtil.WAIT_5_SEC);
        if (element == null)
            throw new AutomationException(String.format("Report Sent table not displayed in practitioners tab"));
    }

    public void verifyColumnInReportsSentTable(String columnName) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(COLUMN_IN_REPORTS_SENT_TABLE, columnName));
        if (element == null)
            throw new AutomationException(String.format("'" + columnName + "' Column has not present in Reports Sent table!"));
    }

    public void verifyAfterSelectFirstRecordUserRedirectToPractitionersTab() throws AutomationException {
        waitForLoadingPage();
        WebElement firstReportSent = driverUtil.getWebElement(FIRST_RECORD_FROM_REPORTS_TABLE_IN_REPORTS_TAB);
        if (firstReportSent == null)
            throw new AutomationException(String.format("Report Sent record not displayed in reports table"));
        firstReportSent.click();
        waitForLoadingPage();

        WebElement practitionersTabSelected = driverUtil.getWebElement(String.format(SELECTED_ACTIVE_GLOBAL_TAB, "Practitioners"));
        if (practitionersTabSelected == null)
            throw new AutomationException(String.format("After selecting Report Sent record user not redirect to practitioners tab but it should be redirect to practitioners tab!"));
    }

    public void verifyReportCreationDatedDisplayedOldestToNewestForm() throws AutomationException {
        List<String> datesInTable = new ArrayList<>();
        List<WebElement> dateRecords = driverUtil.getWebElements(DATE_RECORD_IN_CREATED_COLUMN);
        for (int i = 0; i < dateRecords.size(); i++) {
            System.out.println("Date List= " + dateRecords.get(i).getText());
            datesInTable.add(dateRecords.get(i).getText().trim().toLowerCase());
        }
        datesInTable.removeAll(Arrays.asList(" ", null));

        List<String> sortedDates = new ArrayList<>();
        sortedDates = datesInTable;
        Collections.sort(sortedDates);
        if (!datesInTable.equals(sortedDates))
            throw new AutomationException("Created dates was expected to be displayed oldest to newest Chronologically but its not");
    }

    public String selectFirstPractitionerFromAdvancedSearchTableAndVerify() throws AutomationException {
        waitForLoadingPage();
        WebElement firstPractitionerNameInTable = driverUtil.getWebElement(FIRST_RECORD_FROM_ADVANCED_PRACTITIONER_SEARCH_TABLE);
        if (firstPractitionerNameInTable == null)
            throw new AutomationException(String.format("Practitioner record not displayed in advanced search practitioner table"));
        String firstRecord=firstPractitionerNameInTable.getText().trim();
        firstPractitionerNameInTable.click();
        waitForLoadingPage();

        return firstRecord;
    }

    public void verifyColumnNotPresentInReportsTableColumn(String columnName) throws AutomationException {
        WebElement tableHeadings = driverUtil.getWebElement(String.format(REPORTS_TAB_TABLE_COLUMN_HEADING, columnName));
        if (tableHeadings != null)
            throw new AutomationException("Fax Column was not expected to be displayed in Reports table but its present in table");
    }

    public void verifyTableInGlobalCampaignsTab(String tableName) throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElement(String.format(TABLE_IN_PRACTITIONERS_TAB,tableName));
        if (element == null)
            throw new AutomationException(tableName+" table not displayed in global practitioners tab");
    }

    public void selectCampaignValue(String campaignDate) throws AutomationException {
        waitForLoadingPage();
        WebElement campaignButton = driverUtil.getWebElement(CAMPAIGNS_SELECT_DROPDOWN);
        if (campaignButton == null)
            throw new AutomationException(String.format("Campaigns dropdown not displayed on screen"));
        campaignButton.click();

        WebElement campaignDateElement = driverUtil.getWebElement(String.format(CAMPAIGNS_SELECT_OPTION_IN_DROPDOWN, campaignDate));
        if (campaignDateElement == null)
            throw new AutomationException(String.format("%s Campaigns Value not present in campaigns dropdown", campaignButton));
        campaignDateElement.click();
        waitForLoadingPage();
    }

    public void clickOnLogActionIconFromReportHistoryTable() throws AutomationException {
        WebElement button = driverUtil.getWebElement(LOG_ACTION_ICON_IN_REPORT_HISTORY_TABLE);
        if (button == null)
            throw new AutomationException("Unable to find log Action button in reports History table or it might taking too long time to load!");
        button.click();
        waitForLoadingPage();
    }

    public void verifyPractitionersTabTableHasColumn(String tableName,List<String> columnName) throws AutomationException {
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(PRACTITIONERS_TAB_TABLE_COLUMNS,tableName, column));
            System.out.println("Elements in :"+element);
            if (element == null)
                throw new AutomationException("Column header not present in report history table : " + column);
        }
    }
    public void verifySentStatusReportTable(List<String> sentStaus) throws AutomationException {
        WebElement element = driverUtil.findElement("//*[text()='Report History']//following::thead//..//*[text()='Created']//..//button//..//*[contains(@class,'sort-descending')]");
        if (element == null) {
            driverUtil.findElement("//*[text()='Created' and contains(@class,'TableHeadCell')]/..//button").click();
            element = driverUtil.findElement("//*[text()='Report History']//following::thead//..//*[text()='Created']//..//button//..//*[contains(@class,'sort-descending')]");
            if (element == null) {
                driverUtil.findElement("//*[text()='Created' and contains(@class,'TableHeadCell')]/..//button").click();
            }
        }

        for (String sent : sentStaus
        ) {
            if(sent.equalsIgnoreCase("Current Date")){
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                sent = LocalDateTime.now().format(format);
            }

            Boolean flag=false;
           // WebElement element = driverUtil.getElementByJavascript(String.format(SENT_STATUS_IN_REPORT_HISTORY_TABLE, sent));

//            Actions actions=new Actions(DriverFactory.drivers.get());
//            actions.moveToElement(driverUtil.getWebElement(String.format(SENT_STATUS_IN_REPORT_HISTORY_TABLE, sent)));
            WebElement requiredElement = driverUtil.getWebElement(String.format(SENT_STATUS_IN_REPORT_HISTORY_TABLE, sent));
            if (requiredElement == null)
                throw new AutomationException(String.format("Status %s not displayed in Report history table", sent));
        }
    }

    public void verifyEventStatusReportTable(List<String> EventStatus) throws AutomationException {
        for (String event : EventStatus
        ) {
            WebElement element = driverUtil.getElementByJavascript(String.format(EVENT_TYPE_STATUS_IN_REPORT_HISTORY_TABLE, event));
            //WebElement element = driverUtil.getWebElement(String.format(EVENT_TYPE_STATUS_IN_REPORT_HISTORY_TABLE, event));
            //WebElement element = driverUtil.findElementAndScroll(String.format(EVENT_TYPE_STATUS_IN_REPORT_HISTORY_TABLE, event));
            if (element == null)
                throw new AutomationException(String.format(" %s not displayed in Report history table", event));
        }
    }

    public void verifyDeleteIconTableHasColumn(List<String> columnName) throws AutomationException {
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(DELETE_ICON_PRACTITIONER_TIME, column), 10);
            if (element == null)
                throw new AutomationException("Delete icon practitioner time table has column : " + column);
        }
    }

    public void DeleteTheRecordPractitionerTimeTable(List<String> columnName) throws AutomationException {
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(DELETE_ICON_PRACTITIONER_TIME, column), 10);

            if (element == null)
                throw new AutomationException("Delete the record practitioner time table : " + column);
            else
                element.click();
        }
    }
    public void areYouSureDeleteRecord(String value) throws AutomationException {

          WebElement element = driverUtil.getWebElement(String.format(DELETE_ICON_CONFIRMATION, value), 10);

            if (element == null)
                throw new AutomationException("Are you sure you want delete the record : " + value);
            else
                element.click();

    }
    public boolean userClicksOnDropDownAndEditImplementationStatus(String dropDownName, String value,String followUpOption) throws AutomationException {
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        WebElement selectDropdown = driverUtil.getWebElement("//*[text()='" + dropDownName + "']");
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: %s", dropDownName));
        selectDropdown.click();

        WebElement selectDropdownOption = driverUtil.getWebElement("//*[text()='" + value + "']");
        if (selectDropdownOption == null)
            throw new AutomationException(String.format("Unable to find %s option in DropDown %s", value, dropDownName));
        selectDropdownOption.click();

        WebElement editFollowUpOption = driverUtil.getWebElement("//*[text()='Select Implementation Status']/..//button/..//*[text()='"+followUpOption+"']");
        if (editFollowUpOption == null)
            throw new AutomationException(String.format("Unable to find %s edit Implementation option in DropDown %s", value, dropDownName));
        editFollowUpOption.click();
        waitForLoadingPage();
        takeScreenshot();
        return true;
    }

    public void verifyUpdatedImplementationStatusInImplementationColumn(String followUpStatus, String practitioner) throws AutomationException {
        waitForLoadingPage();
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
        int columnIndex = getReportedDRPTableColumnHeadingIndex("Implementation Status")-2;
        WebElement selectedPractitioner = driverUtil.getWebElementAndScroll("//table//tbody//*[text()='"+ practitioner +"']//following::td["+columnIndex+"]");
        if (selectedPractitioner == null)
            throw new AutomationException("Unable to find " + practitioner + " Implementation status in Implementation status column");
        String followUpStatusInTable = selectedPractitioner.getText();
        System.out.println("followUpStatusInTable =====" + followUpStatusInTable);

        if (!followUpStatus.equals(followUpStatusInTable))
            throw new AutomationException(String.format("Expected Updated Implementation Status for %S practitioner is %s but its shows: %s", practitioner, followUpStatus, followUpStatusInTable));
    }
    public void verifyMouseOVerReportedTablColumn(List<String> columnName) throws AutomationException {
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(REPORTED_DSR_COLUM, column), 10);
            if (element == null){
                throw new AutomationException("Column values not present in report DRPS table : " + column);
            }else{
                Actions action = new Actions(DriverFactory.drivers.get());
                action.moveToElement(element).build().perform();
            }

        }
    }

    public void ClickOnReportDRPSTableValue(String columnName) throws AutomationException {

            WebElement element = driverUtil.getWebElement(String.format(REPORTED_DSR_COLUM, columnName), 10);
            if (element == null)
                throw new AutomationException("Column values not present in report DRPS table : " + columnName);
            else
                element.click();

        takeScreenshot();

    }

    public void userSelectPatientRowInPractitionersTabTable(String name) throws AutomationException {
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(PrescriberAnalyticsPage.CAMPAIGNS_PRACTITIONER_RECORD, name));
        if (practitioner == null)
            throw new AutomationException("No Patient found with the name: " + name);
        practitioner.click();
        waitForLoadingPage();
    }

    public void verifyPainDetailsDRPTabel(List<String> columnName) throws AutomationException {
        waitForLoadingPage();
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElementAndScroll(String.format(PATIENT_DETAILS, column), 10);
            if (element == null)
                throw new AutomationException("DRPS pane Component values not available : " + column);

        }
        PageFactory.patientPage().scrollToTop();
        waitForLoadingPage();
    }
    public void VerifyDRPSTableCountValue(String expectedCount) throws AutomationException {
        List<WebElement> rowsInTable = driverUtil.getWebElements("//*[text()='Reported DRPs']//..//..//..//table//tbody//tr");

        List<List<String>> tableData = new ArrayList<>();
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.drivers.get();

        Actions actions = new Actions(DriverFactory.drivers.get());

        WebElement table = driverUtil.findElement("//*[text()='Reported DRPs']//..//..//..//table//tbody");
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        while (true) {
            // Scroll down to trigger more data loading
            js.executeScript("arguments[0].scrollIntoView();", rows.get(rows.size() - 1));

            // Wait for some time to allow data to load
            try {
                Thread.sleep(1000); // Adjust the wait time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check if more rows are loaded
            List<WebElement> newRows = table.findElements(By.tagName("tr"));
            if (newRows.size() == rows.size()) {
                break; // No more new rows loaded, exit loop
            } else {
                rows = newRows; // Update rows to include newly loaded rows
            }
        }
        System.out.println("tableData===="+rows.size());
            if (rows.size()>=Integer.parseInt(expectedCount))
                throw new AutomationException("DRPS total count is greater than %s : " + expectedCount);

//        System.out.println("rowsInTable= "+rowsInTable.size());
//        WebElement element = driverUtil.getWebElementAndScroll("//div[contains(text(),'Total ')]", 10);
//        if (element == null){
//            throw new AutomationException("DRPS total not present in report DRPS table : " + expectedCount);
//        }else{
//            String text[]=element.getText().split(" ");
//            int count= Integer.parseInt(text[1]);
//            if (rowsInTable.size()>=Integer.parseInt(expectedCount))
//                throw new AutomationException("DRPS total count is greater than %s : " + expectedCount);
//
//        }
    }
    public void verifyTheFaxProviderButtonEnabledOrNot() throws AutomationException {
        WebElement element = driverUtil.getWebElement(FAX_PROVIDER_BUTTON, 10);
        if (element == null){
            throw new AutomationException("Fax Provider Button not available in practitioner tab : ");
        }else{
            if(element.isEnabled())
                common.logInfo("Fax Provider Button is Enabled");
            else
                common.logInfo("Fax Provider Button is Disabled");
        }
    }

    public void verifyDefaultValuesAreSelected(List<String> columnName) throws AutomationException {
       // driverUtil.findElement(String.valueOf(By.xpath(FAX_PROVIDER_BUTTON))).click();
        WebElement elements = driverUtil.getWebElement(FAX_PROVIDER_BUTTON, 10);
        elements.click();
        for (String column : columnName
        ) {

            WebElement element = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, column), 10);

            if (element == null) {
                throw new AutomationException("Verify the Resend most recent report selected and which are default values are selected : ");
            } else {
                if (element.isSelected())
                    common.logInfo(column+" Radio Button is selected");
                else
                    common.logInfo(column+" Radio Button is not selected");
            }
        }
        elements = driverUtil.getWebElement(ALL_OPEN_DRP_RADIO_BUTTON, 10);
        if(elements.isSelected())
            common.logInfo("All Open DRPs Radio Button is selected");
        else
            common.logInfo("All Open DRPs Radio Button is not selected");
    }
    public void verifyResendCheckBoxSelected(List<String> columnName) throws AutomationException {
        // driverUtil.findElement(String.valueOf(By.xpath(FAX_PROVIDER_BUTTON))).click();
        WebElement elements = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, "Resend most recent report?"), 10);
        elements.click();
        for (String column : columnName
        ) {

            WebElement element = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, column), 10);

            if (element == null) {
                throw new AutomationException("Verify that If no DRPs are selected and which are default values are selected : ");
            } else {
                if (element.isEnabled())
                    common.logInfo(column+" Radio Button is Enabled");
                else
                    common.logInfo(column+" Radio Button is Disabled");
            }
        }
        elements = driverUtil.getWebElement(ALL_OPEN_DRP_RADIO_BUTTON, 10);
        if(elements.isEnabled())
            common.logInfo("All Open DRPs Radio Button is Enabled");
        else
            common.logInfo("All Open DRPs Radio Button is Disabled");

        elements = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, "Resend most recent report?"), 10);
        elements.click();
        for (String column : columnName
        ) {

            WebElement element = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, column), 10);

            if (element == null) {
                throw new AutomationException("Verify that If nResend most recent report un clicked and two input option is enabled : ");
            } else {
                if (element.isEnabled())
                    common.logInfo(column+" Radio Button is Enabled");
                else
                    common.logInfo(column+" Radio Button is Disabled");
            }
        }
        elements = driverUtil.getWebElement(ALL_OPEN_DRP_RADIO_BUTTON, 10);
        if(elements.isEnabled())
            common.logInfo("All Open DRPs Radio Button is Enabled");
        else
            common.logInfo("All Open DRPs Radio Button is Disabled");

        elements = driverUtil.getWebElement(CLOSE_BUTTON_FAX_PROVIDER,  10);
        if(elements==null)
            throw new AutomationException("Cancel button is not displayed : ");
        else
            common.logInfo("Cancel button is displayed in fax provider section");

        elements.click();
    }

    public void verifyTheFaxProviderSectionDetails(List<String> columnName) throws AutomationException {
        // driverUtil.findElement(String.valueOf(By.xpath(FAX_PROVIDER_BUTTON))).click();
        WebElement elements = driverUtil.getWebElement(FAX_PROVIDER_BUTTON, 10);
        elements.click();
        for (String column : columnName
        ) {

            WebElement element = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, column), 10);
            WebElement element1 = driverUtil.getWebElement(String.format(FAX_PROVIDER_HEADER_DETAILS, column), 10);

            if (element != null) {
                if (element.isEnabled())
                    common.logInfo(column+" Radio doesn't haveFax Provider follow up section ");
                else
                    common.logInfo(column+" Radio button available in Fax Provider follow up section ");

            }else if(element1 != null){
                common.logInfo(column+" available in Fax Provider follow up section ");
            }else{
                throw new AutomationException(column+"doesn't have  in haveFax Provider follow up section ");
            }
        }

        elements = driverUtil.getWebElement(ALL_OPEN_DRP_RADIO_BUTTON, 10);
        if(elements.isEnabled())
            common.logInfo("All Open DRPs Radio Button available in Fax Provider follow up section ");
        else
            common.logInfo("All Open DRPs Radio Button available in Fax Provider follow up section");


        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, "Preview and Send"), 10);

        if(elements==null)
            throw new AutomationException("Preview and Send button is not displayed Fax Provider section: ");

        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, "Cancel"), 10);

        if(elements==null)
            throw new AutomationException("Cancel button is not displayed Fax Provider section: ");

        elements.click();
    }
    public void verifyTheFaxProviderSectionDetails(String faxNumber, String comments, String button) throws AutomationException {
        WebElement elements = driverUtil.getWebElement(FAX_PROVIDER_BUTTON, 10);
        elements.click();

        elements = driverUtil.getWebElement("//*[contains(@class,'mantine-Textarea') and @placeholder='']", 10);
        if(elements==null)
            throw new AutomationException("Fax input number is not displayed Fax Provider section: ");
        else
            elements.sendKeys(comments);

       elements = driverUtil.getWebElement(FAX_INPUT_NUMBER, 10);
       if(elements==null)
           throw new AutomationException("Fax input number is not displayed Fax Provider section: ");
       else
           elements.sendKeys(faxNumber);

       elements = driverUtil.getWebElement("//*[contains(@class,'mantine-TextInput-input') and @placeholder='Enter a fax number...']//preceding::input[1]", 10);
        if(elements==null)
            throw new AutomationException("Fax input number radio button is not displayed Fax Provider section: ");
        else
            elements.click();

        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, "Preview and Send"), 10);

        elements.click();
        waitForLoadingPage();
        takeScreenshot();

        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, button), 10);
        elements.click();
        waitForLoadingPage();


    }
    public void sendTheFaxSelectingDRPs(String faxNumber, String comments, String button) throws AutomationException {
        WebElement elements =driverUtil.getWebElement(FAX_PROVIDER_BUTTON, 10);
        elements.click();

        elements = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON,"Selected DRPs (2)"), 10);
        elements.click();

        elements = driverUtil.getWebElement(FAX_NOTE_TEXT, 10);
        if(elements==null)
            throw new AutomationException("Fax input number is not displayed Fax Provider section: ");
        else
            elements.sendKeys(comments);

        elements = driverUtil.getWebElement(FAX_INPUT_NUMBER, 10);
        if(elements==null)
            throw new AutomationException("Fax input number is not displayed Fax Provider section: ");
        else
            elements.sendKeys(faxNumber);

        elements = driverUtil.getWebElement("//*[contains(@class,'mantine-TextInput-input') and @placeholder='Enter a fax number...']//preceding::input[1]", 10);
        if(elements==null)
            throw new AutomationException("Fax input number radio button is not displayed Fax Provider section: ");
        else
            elements.click();

        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, "Preview and Send"), 10);
        if(elements==null)
            throw new AutomationException("Preview and Send button is not displayed Fax Provider section: ");
        elements.click();
        waitForLoadingPage();
        takeScreenshot();

        elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, button), 10);
        elements.click();
        waitForLoadingPage();


    }
    public void selectTheRecordDRPsTable(List<String> columnName) throws AutomationException {
        for (String column : columnName
        ) {
            WebElement element = driverUtil.getWebElement(String.format(DRPS_TABLE_CHECK_BOX, column), 10);
            if (element == null)
                throw new AutomationException("unable to Select records on Reported DRPS Table values: ");
            else
                if(!element.isSelected()) {
                    element.click();
                }
        }
        PageFactory.patientPage().scrollToTop();
    }

    public void verifyFilterSectionDRPsTable(List<String> columnName) throws AutomationException {
        WebElement elements= driverUtil.getWebElement(SEARCH_BUTTON,10);
        elements.click();
        int i=0;
        for (String column : columnName
        ) {

            WebElement element = driverUtil.getWebElement(String.format(SEARCH_NAME_FIELD, column), 2);
            WebElement element1 = driverUtil.getWebElement(String.format(SEARCH_ALGORITHM_FIELD, column), 2);
            elements = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, column), 2);

            if (element == null && i<3)
                throw new AutomationException(column+"unable to find records on Reported DRPS Table Advanced filter: ");
            else if(element1 == null   && i>3 && i<6)
                throw new AutomationException(column+"unable to find records on Reported DRPS Table Advanced filter: ");
            else if(elements == null  && i>6)
                throw new AutomationException(column+"unable to find records on Reported DRPS Table Advanced filter: ");
            i++;
           }
        elements.click();
    }

    public void clickOnButtonPresentInPractitionersTab(String text) throws AutomationException {
        WebElement button = driverUtil.getWebElementAndScroll(String.format(PrescriberAnalyticsPage.CAMPAIGNS_TAB_BUTTONS, text));
        if (button == null)
            throw new AutomationException(String.format("Unable to find %s button on patient tab or it might taking too long time to load!", text));
        button.click();
        waitForLoadingPage();
    }

    public static int getReportedDRPTableColumnHeadingIndex(String columnName) throws AutomationException {
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
        System.out.println("columnHeadingNameList== "+columnHeadingNameList);
        return indexNumber;
    }

    public static int getReportHistoryTableColumnHeadingIndex(String columnName) throws AutomationException {
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
        System.out.println("columnHeadingNameList== "+columnHeadingNameList);
        return indexNumber;
    }

    public void verifyUserAbleToMoveDRPsTableColumns(String column1,String column2) throws AutomationException, AWTException {
        PrescriberAnalyticsPage.clickOnShowHideColumnsButtonAndOnOrOffIt("On");
        int column1IndexBeforeMove = getReportedDRPTableColumnHeadingIndex(column1);
        WebElement column1MoveIcon = driverUtil.getWebElement("//*[text()='"+column1+"']//..//..//button[@draggable='true']");
        if (column1MoveIcon == null)
            throw new AutomationException("Column Move Icon not visible for column: " + column1);
        WebElement column2MoveIcon = driverUtil.getWebElement("//*[text()='" + column2 + "']//..//..//button[@draggable='true']");
        if (column2MoveIcon == null)
            throw new AutomationException("Column Move Icon not visible for column: " + column2);

        WebDriverUtil.dragAndDropUsingJavaScript(column1MoveIcon, column2MoveIcon);

        int column1IndexAfterMove = getReportedDRPTableColumnHeadingIndex(column1);
        System.out.println("column1IndexBeforeMove== "+column1IndexBeforeMove);
        System.out.println("column1IndexAfterMove== "+column1IndexAfterMove);
        if(column1IndexBeforeMove == column1IndexAfterMove)
            throw new AutomationException("user not able to drag columns from providers table");
    }

    public void selectTaskFilter(String filterName, String filterOption, String buttonName) throws AutomationException, ParseException {
        Actions actions = new Actions(DriverFactory.drivers.get());
        WebElement selectTaskFilterIcon = driverUtil.getWebElement(PageFactory.prescriberAnalyticsPage().CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectTaskFilterIcon == null)
            throw new AutomationException("No filter search icon visible in Campaigns tab!");
        selectTaskFilterIcon.click();

        if (filterName.equalsIgnoreCase("Algorithm Impact") || filterName.equalsIgnoreCase("Resolved") || filterName.equalsIgnoreCase("Implementation Status")) {
            WebElement campaignAdvanceFilterDropDown = driverUtil.getWebElement(String.format(REPORTED_DRP_ADVANCE_FILTER_DROPDOWN, filterName, filterName));
            if (campaignAdvanceFilterDropDown == null)
                throw new AutomationException("No '" + filterName + "' advance filter dropdown defined in campaign filter options!");
            campaignAdvanceFilterDropDown.click();
            WebElement campaignAdvanceFilterDropDownOption = driverUtil.getWebElement(String.format(REPORTED_DRP_ADVANCE_FILTER_DROPDOWN_OPTION, filterOption));
            if (campaignAdvanceFilterDropDownOption == null)
                throw new AutomationException("No '" + filterName + "' campaign Advance Filter DropDown Option defined in campaign filter options!");
            campaignAdvanceFilterDropDownOption.click();
            actions.sendKeys(Keys.ESCAPE).build().perform();
        }
        else if (filterName.equalsIgnoreCase("Report Sent Date") || filterName.equalsIgnoreCase("Report Created Date")) {
           WebElement element = driverUtil.getWebElement(String.format(DATE_BUTTON+"//following-sibling::div/button", filterName), 10);
//            element = driverUtil.getWebElement(String.format(DATE_BUTTON+"//following-sibling::div/button", dateType), 10);

            if (element != null) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                String currentDate= formatter.format(date);
                Calendar cal=Calendar.getInstance();
                if(filterOption.equalsIgnoreCase("future date")){
                    cal.add(Calendar.DAY_OF_MONTH, +2);
                    Date nextDate = cal.getTime();
                    SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
                    currentDate=format.format(nextDate);
                }else{
                    cal.add(Calendar.DAY_OF_MONTH, -(365-2));
                    Date frmDate = cal.getTime();
                    SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
                    fromDate=format.format(frmDate);
                }
                finalDate=fromDate+"-"+currentDate;
                element.click();
                String dateRange[]=finalDate.split("-");
                for(int i=0;i<dateRange.length;i++) {
                    String dateVal=dateRange[i];
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                    String yearValue = DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).getText();
                    String years[] = yearValue.split(" – ");
                    String values[] = dateVal.split("\\s");
                    if (Integer.parseInt(values[2].trim()) > Integer.parseInt(years[1].trim()))
                        DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//following-sibling::button")).click();
                    else if (Integer.parseInt(values[2].trim()) < Integer.parseInt(years[0].trim()))
                        DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//preceding-sibling::button")).click();

                    DriverFactory.drivers.get().findElement(By.xpath(String.format(YEAR_SELECTION, values[2]))).click();
                    DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, values[1]))).click();
                    if(values[0].startsWith("0"))
                        currentDay=values[0].replace("0","");
                    else
                        currentDay=values[0];
                    DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, currentDay))).click();
                }
            }

            else{
                throw new AutomationException(filterName+" unable to find records on Reported DRPS Table Advanced filter: ");
            }
        }
        else {
            WebElement campaignAdvanceFilterInput = driverUtil.getWebElementAndScroll(String.format(REPORTED_DRP_ADVANCE_FILTER_INPUT, filterName, filterName));
            if (campaignAdvanceFilterInput == null)
                throw new AutomationException("No '" + filterName + "' filter dropdown defined in Task page filter options!");
            campaignAdvanceFilterInput.click();
            campaignAdvanceFilterInput.clear();
            campaignAdvanceFilterInput.sendKeys(filterOption);
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
        clickOnButtonPresentInPractitionersTab(buttonName);
        waitForLoadingPage();
    }

    public void verifyPAFeatureSelectedFilterOption(String filterName,String filterOption) throws AutomationException {
        waitForLoadingPage();
        if(!filterName.equalsIgnoreCase("Report Created Date") && !filterName.equalsIgnoreCase("Report Sent Date") && !filterName.equalsIgnoreCase("Last Faxed") && !filterName.equalsIgnoreCase("Last Contacted")){
            WebElement advanceFilteredRecord = driverUtil.getWebElement(String.format(PA_FEATURE_ADVANCE_FILTERED_SELECTED_OPTION, filterName));
            if (advanceFilteredRecord == null)
                throw new AutomationException(String.format("Advance filtered record not displayed for table"));
            String filteredText = advanceFilteredRecord.getText();
            System.out.println("filteredText = " + filteredText);

            if (!filteredText.equalsIgnoreCase(filterOption))
                throw new AutomationException(String.format("After selecting " + filterName + " advance filter " + filterOption + " option should be able to visible as selected in advance filter!"));
        }
    }

    public void verifyAfterSelectPractitionerUserRedirectToPatientTab(String practitionerName) throws AutomationException {
        waitForLoadingPage();
        WebElement practitionerRecord = driverUtil.getWebElement(String.format(PATIENT_NAME_CELL_IN_DRP_TABLE, practitionerName));
        if (practitionerRecord == null)
            throw new AutomationException(String.format("Report Sent record not displayed in reports table"));
        practitionerRecord.click();
        waitForLoadingPage();
        WebElement popup = driverUtil.findElementByText("OK");
        if (popup != null)
            driverUtil.findElementByTextAndClick("OK");
        waitForLoadingPage();
        WebElement practitionersTabSelected = driverUtil.getWebElement(String.format(SELECTED_ACTIVE_GLOBAL_TAB, "Patient"));
        if (practitionersTabSelected == null)
            throw new AutomationException(String.format("After selecting Report Sent record user not redirect to Patient tab but it should be redirect to patient tab!"));
    }

    public void verifyUserAbleToMoveDRPsColumnsFromShowHideTabPopup(String column1,String column2) throws AutomationException, AWTException {
        int column1IndexBeforeMove = getReportedDRPTableColumnHeadingIndex(column1);
        PrescriberAnalyticsPage.clickOnColumnShowHideButton();
        WebElement column1MoveIcon = driverUtil.getWebElement("//label[text()='"+column1+"']//ancestor::button//child::button[@draggable='true']");
        if (column1MoveIcon == null)
            throw new AutomationException("Column Move Icon in column show hide popup is not visible for column: " + column1);
        WebElement column2MoveIcon = driverUtil.findElementAndScroll("//label[text()='"+column2+"']//ancestor::button//child::button[@draggable='true']");
        if (column2MoveIcon == null)
            throw new AutomationException("Column Move Icon in column show hide popup is not visible for column: " + column2);

        WebDriverUtil.dragAndDropUsingJavaScript(column1MoveIcon, column2MoveIcon);
        WebDriverUtil.waitForAWhile(5);
        takeScreenshot();
        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.sendKeys(Keys.ESCAPE).build().perform();

        int column1IndexAfterMove = getReportedDRPTableColumnHeadingIndex(column1);
        if(column1IndexBeforeMove == column1IndexAfterMove)
            throw new AutomationException("Unable to move columns from providers table through show hide tab popup!");
    }


    public void verifyColumnContainsValuesInReportsDRPTable(String columnName, String filterOption) throws AutomationException, ParseException {
        WebElement tableHeading = driverUtil.getWebElement("//table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper') and text()='" + columnName + "']");
        if (tableHeading == null)
            throw new AutomationException(columnName + " Column Name not visible in reports table!");
        int columnIndex = getReportedDRPTableColumnHeadingIndex(columnName);
        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_DRP_TABLE_COLUMN_DATA, columnIndex));
        List columnCellDataList = new ArrayList();
        System.out.println("columnIndex== " + columnIndex);
        for (int i = 0; i < columnData.size(); i++) {
            columnCellDataList.add(columnData.get(i).getText());
            String columnCellData = columnData.get(i).getText();

            if (filterOption.equalsIgnoreCase("Report Sent Date") || filterOption.equalsIgnoreCase("Report Created Date")) {
                Date columnCellDateData = new SimpleDateFormat("dd/MM/yyyy").parse(columnCellData);
                if (columnName.equalsIgnoreCase("Last Faxed")) {
                    Date expectedDateRange1 = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
                    Date expectedDateRange2 = new SimpleDateFormat("dd/MM/yyyy").parse(finalDate);
                    Date expectedDateRange1PreviousDay = new Date(expectedDateRange1.getTime() - 1);
                    Date expectedDateRange2PreviousDay = new Date(expectedDateRange2.getTime() + 1);

                    if (!columnCellDateData.after(expectedDateRange1PreviousDay) && !columnCellDateData.before(expectedDateRange2PreviousDay) && columnCellDateData!=null)
                        throw new AutomationException("In " + columnName + " Column only " + filterOption + " this data should be displayed but we found " + columnData.get(i).getText() + " as well");
                } else if (!columnCellData.contains(filterOption) && !columnCellData.isEmpty())
                    throw new AutomationException("In " + columnName + " Column only " + filterOption + " this data should be displayed but we found " + columnData.get(i).getText() + " as well");
            }
        }
    }

    public static List<String> getColumnDataInPractitionersTabTable(String columnNameToSort) throws AutomationException {
        WebElement selectTaskFilterIcon = driverUtil.getWebElement("//table//thead//div[contains(@class,'TableHeadCell-Content-Wrapper')]//ancestor-or-self::*[text()='" + columnNameToSort + "']");
        if (selectTaskFilterIcon == null)
            throw new AutomationException(columnNameToSort + " Column Name not visible in reports table!");
        int columnIndex = getReportedDRPTableColumnHeadingIndex(columnNameToSort);
        System.out.println("columnIndex= "+columnIndex);

        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_DRP_TABLE_COLUMN_DATA, columnIndex));
        List columnCellDataList = new ArrayList();
        for (int i = 0; i < columnData.size(); i++) {
            columnCellDataList.add(columnData.get(i).getText().trim().toLowerCase());
        }

        System.out.println("columnCellDataList== " + columnCellDataList);
        return columnCellDataList;
    }

    public void verifyPractitionersTabTableSort(String sortType, String columnNameToSort) throws AutomationException {
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
        try {
            if (sortType.equalsIgnoreCase("ASCENDING")) {
                ordered = Ordering.natural().isOrdered(getColumnDataInPractitionersTabTable(columnNameToSort));
                if (!ordered)
                    throw new AutomationException(columnNameToSort + " was expected to be sorted in ascending order but its not");
            } else {
                ordered = Ordering.natural().reverse().isOrdered(getColumnDataInPractitionersTabTable(columnNameToSort));
                if (!ordered)
                    throw new AutomationException(columnNameToSort + " was expected to be sorted in descending order but its not");
            }
        } catch (Exception ex) {
            if (sortType.equalsIgnoreCase("ASCENDING") && !(columnNameToSort.contains("DoB") || columnNameToSort.contains("Date"))) {
                List<String> tableData = getColumnDataInPractitionersTabTable(columnNameToSort);
                List<String> sortedData = getColumnDataInPractitionersTabTable(columnNameToSort);
                Collections.sort(sortedData, Comparator.reverseOrder());
                if (!tableData.equals(sortedData))
                    throw new AutomationException(columnNameToSort + " column was expected to be sorted in ascending order but its not");
            }
                if (sortType.equalsIgnoreCase("DESCENDING") && !(columnNameToSort.contains("DoB") || columnNameToSort.contains("Date"))) {
                List<String> tableData = getColumnDataInPractitionersTabTable(columnNameToSort);
                List<String> sortedData = getColumnDataInPractitionersTabTable(columnNameToSort);
                Collections.sort(sortedData);
                if (!tableData.equals(sortedData))
                    throw new AutomationException(columnNameToSort + " column was expected to be sorted in descending order but its not");
            }
            if(columnNameToSort.contains("DoB") || columnNameToSort.contains("Date")){
                List<String> dateStrings = getColumnDataInPractitionersTabTable(columnNameToSort);
                List<Date> dates = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                for (String dateStr : dateStrings) {
                    try {
                        dates.add(sdf.parse(dateStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (sortType.equalsIgnoreCase("DESCENDING")) {
                    for (int i = 0; i < dates.size() - 1; i++) {
                        if (dates.get(i).before(dates.get(i + 1))) {
                            throw new AutomationException(columnNameToSort + " - column was expected to be sorted in descending order but its not");
                        }
                    }
                }
                if (sortType.equalsIgnoreCase("ASCENDING")) {
                    for (int i = 0; i < dates.size() - 1; i++) {
                        if (dates.get(i).after(dates.get(i + 1))) {
                            throw new AutomationException(columnNameToSort + " - column was expected to be sorted in descending order but its not");
                        }
                    }
                }
            }
        }
    }

    public void verifyPaginationInDRPsTable() throws AutomationException {
        int columnIndex = getReportedDRPTableColumnHeadingIndex("Name");
        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_DRP_TABLE_COLUMN_DATA, columnIndex));

        WebElement filterPagination = driverUtil.getWebElementAndScrollWithoutWait("//div[@class='mantine-Text-root mantine-8cp6g0']");
        if (filterPagination == null)
            throw new AutomationException("Pagination option is not visible!");
        String filterPaginationText = filterPagination.getText().trim();
        System.out.println("filterPaginationText=="+ filterPaginationText);

        String paginationMatchText = "Total " + columnData.size();
        System.out.println("paginationMatchText= "+paginationMatchText);
        if (!filterPaginationText.trim().equalsIgnoreCase(paginationMatchText))
            throw new AutomationException("Pagination count not get match after apply filter!");
        takeScreenshot();
    }

    public void enterTheNameInFilterSectionDRPsTable(String filedName,String dropdown, String dateType, String value,String dropDwonValue,String dateValue,String button) throws AutomationException {
        WebElement element= driverUtil.getWebElement(SEARCH_BUTTON,10);
        element.click();

        element = driverUtil.getWebElement(String.format(SEARCH_NAME_FIELD+"//following::input[1]", filedName), 2);

        if (element != null)
            element.sendKeys(value);
        else
            throw new AutomationException("unable to find records on Reported DRPS Table Advanced filter: ");

        element = driverUtil.getWebElement(String.format(SEARCH_ALGORITHM_FIELD+"//following::input[2]", dropdown), 2);

        if (element != null) {
            element.sendKeys(dropDwonValue);
            element.sendKeys(Keys.ARROW_DOWN);
            element.sendKeys(Keys.ENTER);
        }
        else{
            throw new AutomationException(dropDwonValue+" unable to find records on Reported DRPS Table Advanced filter: ");
        }

        element = driverUtil.getWebElement(String.format(DATE_BUTTON+"//following-sibling::div/button", dateType), 10);

        if (element != null) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String currentDate= formatter.format(date);
            Calendar cal=Calendar.getInstance();
            String finalDate,fromDate = null,currentDay=null;
            if(dateType.equalsIgnoreCase("future date")){
                cal.add(Calendar.DAY_OF_MONTH, +2);
                Date nextDate = cal.getTime();
                SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
                currentDate=format.format(nextDate);
            }else{
                cal.add(Calendar.DAY_OF_MONTH, -(365-2));
                Date frmDate = cal.getTime();
                SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
                fromDate=format.format(frmDate);
            }
            finalDate=fromDate+"-"+currentDate;
            element.click();
            String dateRange[]=finalDate.split("-");
            for(int i=0;i<dateRange.length;i++) {
                String dateVal=dateRange[i];
                DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                String yearValue = DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).getText();
                String years[] = yearValue.split(" – ");
                String values[] = dateVal.split("\\s");
                if (Integer.parseInt(values[2].trim()) > Integer.parseInt(years[1].trim()))
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//following-sibling::button")).click();
                else if (Integer.parseInt(values[2].trim()) < Integer.parseInt(years[0].trim()))
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//preceding-sibling::button")).click();

                DriverFactory.drivers.get().findElement(By.xpath(String.format(YEAR_SELECTION, values[2]))).click();
                DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, values[1]))).click();
                if(values[0].startsWith("0"))
                    currentDay=values[0].replace("0","");
                else
                    currentDay=values[0];
                DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, currentDay))).click();
            }
        }
        else{
            throw new AutomationException(dropDwonValue+" unable to find records on Reported DRPS Table Advanced filter: ");
        }

        element = driverUtil.getWebElement(String.format(SEND_CANCEL_BUTTON_FAX_PROVIDER, button), 10);
        element.click();
        waitForLoadingPage();
    }

    public void verifyTheClearFilterSection(List<String> columnName) throws AutomationException {
        WebElement element=null;
        for (String column : columnName
        ) {
             if(column != null) {
                 element = driverUtil.getWebElement(String.format(CLEAR_FILTER, column), 10);
                 if (element == null)
                     throw new AutomationException(column + " unable to find records on Reported DRPS Table Clear filter  section: ");
                 common.logInfo(column + " component available in Clear filter  section");
                 element.click();
             }
        }
    }
    public void verifyTheFaxAndCallToggle() throws AutomationException {
        Actions action=new Actions(DriverFactory.drivers.get());
        common.logInfo(" Do Not Call is set");
       WebElement element = driverUtil.getWebElement(CALL_TOGGEL,2);
        if (element != null){
            action.moveToElement(element).build().perform();
            takeScreenshot();
            WebElement elementGreen = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"green"),2);
            if(elementGreen==null) {
                WebElement elementRed = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"red"),2);
                if(elementRed==null)
                    throw new AutomationException(" unable to find red color Call toggel button: ");
                elementRed.click();
                elementGreen = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"green"),2);
                if(elementGreen==null)
                throw new AutomationException("Unable to find green color Call toggel button: ");
            }

            action.moveToElement(element).build().perform();
            takeScreenshot();
            WebElement elementRed = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"red"),2);
            if(elementGreen==null) {
                elementGreen = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"green"),2);
                if(elementGreen==null)
                    throw new AutomationException(" unable to find green color Call toggel button: ");
                elementGreen.click();
                elementRed = driverUtil.getWebElement(String.format(CALL_TOGGEL_COLOR,"red"),2);
                if(elementGreen==null)
                    throw new AutomationException("Unable to find red color Call toggel button: ");
            }
        }else{
            throw new AutomationException("unable to find Call toggel button: ");
        }
        element.click();
//        element = driverUtil.getWebElement(FAX_TOGGEL,2);
//        if (element != null){
//            action.moveToElement(element).build().perform();
//            takeScreenshot();
//            WebElement element1 = driverUtil.getWebElement(String.format(FAX_TOGGEL_COLOR,"green"),2);
//            if(element1==null)
//                throw new AutomationException(" unable to find green color Call toggel button: ");
//
//            element.click();
//            action.moveToElement(element).build().perform();
//            takeScreenshot();
//            element1 = driverUtil.getWebElement(String.format(FAX_TOGGEL_COLOR,"red"),2);
//            if(element1==null)
//                throw new AutomationException(" unable to find Red color fax toggel button: ");
//
//        }else{
//            throw new AutomationException("unable to find Call fax button:");
//        }
//        element.click();
    }
    public void enableTheFilterColumAndVerifyTheHeader(String headerName) throws AutomationException {
        PageFactory.patientPage().scrollToTop();
        WebElement element=driverUtil.getWebElement(COLUMN_FILTER,10);
        if(element==null)
            throw new AutomationException("unable to find filter column option in DRP table header");
        else
            element.click();

        element=driverUtil.getWebElement(String.format(REPORTS_HISTORY_TABLE_IN_TAB+"//following::div[1]//*[@class='tabler-icon tabler-icon-grip-horizontal']",headerName),3);
        System.out.println("ele:"+element);
        if(element==null)
            throw new AutomationException("Move Action option unable to find filter header column in"+headerName);

        element=driverUtil.getWebElement(String.format(REPORTS_HISTORY_TABLE_IN_TAB+"//following::button[@aria-label='Change filter mode'][1]",headerName),3);
        System.out.println("ele:"+element);
        if(element==null)
            throw new AutomationException("Change Filter option unable to find filter header column in"+headerName);

        element=driverUtil.getWebElement(String.format(REPORTS_HISTORY_TABLE_IN_TAB+"//following::div[1]//*[@class='tabler-icon tabler-icon-dots-vertical']",headerName),3);
        System.out.println("ele:"+element);
        if(element==null)
            throw new AutomationException("Column Action option unable to find filter header column in"+headerName);


        takeScreenshot();
    }

    public void verifyFilterColum(String values,String headerName) throws AutomationException {
        WebElement element=driverUtil.getWebElement(String.format(REPORTS_HISTORY_TABLE_IN_TAB,headerName),3);

        if(element==null)
            throw new AutomationException("Change Filter option unable to find filter header column in"+headerName);
        else
            element.click();

        takeScreenshot();
        element=driverUtil.getWebElement(String.format(REPORTS_HISTORY_TABLE_IN_TAB+"//following::div[1]//following::input[@placeholder='Filter by "+headerName+"']",headerName),3);

        if(element==null)
            throw new AutomationException("Filter By Name unable to find filter header column in"+headerName);
        else
            element.sendKeys(values);

        element.clear();
        takeScreenshot();
        element=driverUtil.getWebElement(COLUMN_FILTER,10);
        if(element==null)
            throw new AutomationException("unable to find filter column option in DRP table header");
        else
            element.click();


    }
    public void verifyTheShowHideSection(List<String> values) throws AutomationException {
        driverUtil.scrollTo("150");
        WebElement element=driverUtil.getWebElement(SHOW_HIDE_COLUMN,10);
        if(element ==null)
            throw new AutomationException("Unable to find the SHOW HIDE column");
        else
            element.click();
        for (String column : values
        ) {
            element=driverUtil.getWebElement(String.format(SHOW_HIDE_COLUMN_HEADER,column),2);
            if (element==null)
                throw new AutomationException(column+" Unable to find the header in SHOW HIDE column");
        }
    }
    public void verifyTheMoveButtonShowHideSection(List<String> values) throws AutomationException {

        WebElement Sourcelocator=driverUtil.getWebElement(String.format(MOVE_BUTTON,values.get(0)));
        WebElement Destinationlocator=driverUtil.getWebElement(String.format(MOVE_BUTTON,values.get(1)));
        //

//        WebDriverUtil.dragAndDrop(Sourcelocator,Destinationlocator);
//        Actions actions=new Actions(DriverFactory.drivers.get());
//        actions.dragAndDrop(Sourcelocator,Destinationlocator).build().perform();

        WebDriverUtil.dragAndDropUsingJavaScript(Sourcelocator,Destinationlocator);
        /*Action dragAndDrop = actions.clickAndHold(Sourcelocator)
                .moveToElement(Destinationlocator)
                .release(Destinationlocator)
                .build();
        dragAndDrop.perform();*/

    }
    public void verifyPinAndUnpin(String field) throws AutomationException {
        WebElement element=driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN+"//preceding::button[contains(@class,'mantine-ActionIcon-root mantine')][1]",field),10);
        if(element ==null)
            throw new AutomationException("Unable to find the Right Pin button for :"+field);
        else
            element.click();
        takeScreenshot();

        element=driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN+"//preceding::button[contains(@class,'mantine-ActionIcon-root mantine')][1]",field),10);
        if(element ==null)
            throw new AutomationException("Unable to find the Right UnPin button for :"+field);
        else
            element.click();

        element=driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN+"//preceding::button[contains(@class,'mantine-ActionIcon-root mantine')][2]",field),10);
        if(element ==null)
            throw new AutomationException("Unable to find the Left Pin button for :"+field);
        else
            element.click();
        takeScreenshot();
        element=driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN+"//preceding::button[contains(@class,'mantine-ActionIcon-root mantine')][1] ",field),10);
        if(element ==null)
            throw new AutomationException("Unable to find the Left UnPin button for :"+field);
        else
            element.click();
    }
    public void verifySelectAndUnselect(String field) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN +"//preceding::div[contains(@class,'mantine-Switch-trackLabel')][1]", field), 4);
        if (element == null)
            throw new AutomationException("Unable to find the Select button for :" + field);
        else
            element.click();
        takeScreenshot();
        element = driverUtil.getWebElement(String.format(SHOW_HIDE_PIN_UNPIN + "//preceding::div[contains(@class,'mantine-Switch-trackLabel')][1]", field), 4);
        if (element == null)
            throw new AutomationException("Unable to find the Unselect  button for :" + field);
        else
            element.click();
    }
    public void verifyTheShowHideSectionButtons(List<String> values) throws AutomationException {
        WebElement elements=driverUtil.getWebElement("//*[contains(@class,'mantine-Switch-label') and text()='Resolved']//preceding::button[contains(@class,'mantine-ActionIcon-root mantine')][2]",10);
        if (elements == null)
            throw new AutomationException("Unable to find the Unselect  button for :Resolved" );
        else
            elements.click();
        for (String column : values
        ) {
          WebElement  element=driverUtil.getWebElement(String.format(SHOW_HIDE_COLUMN_HEADER,column),4);
            if (element==null)
                throw new AutomationException(column+" Unable to find the button in header SHOW HIDE section");
            else
                element.click();
        }
    }

    public void verifyAllColumnHeadingShouldHaveColumnActionsAndMoveIconInDrpTable(String iconName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        int columnNamesCount = Integer.parseInt(String.valueOf(columnNames.size()))-1;
        switch (iconName.toUpperCase()) {
            case "COLUMN ACTION":
//                List<WebElement> columnActionIcons = driverUtil.getWebElements("//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                List<WebElement> columnActionIcons = driverUtil.getWebElements("//*[text()='Reported DRPs']// following::table// thead//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                if(columnNamesCount != columnActionIcons.size())
                    throw new AutomationException("In Reports table "+columnNamesCount+" column header are visible but in table only "+columnActionIcons.size()+" column actions icons are present");
                break;

            case "MOVE COLUMN":
//                List<WebElement> moveColumnIcons = driverUtil.getWebElements("//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                List<WebElement> moveColumnIcons = driverUtil.getWebElements("//*[text()='Reported DRPs']// following::table// thead//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                if(columnNamesCount != moveColumnIcons.size())
                    throw new AutomationException("In Reports table "+columnNamesCount+" column header are visible but in table only "+moveColumnIcons.size()+" column move icons are present");
                break;
        }
    }

    public void verifyUserAbleToPinUnpinColumn(String status, String column) throws AutomationException, AWTException {
        int columnIndex;
        Actions actions=new Actions(DriverFactory.drivers.get());
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        int totalColumnIndex = columnNames.size();
        PrescriberAnalyticsPage.clickOnColumnShowHideButton();
        switch (status.toUpperCase()) {
            case "PIN TO RIGHT":
                WebElement pinToRightTab = driverUtil.getWebElement("//label[text()='" + column + "']//ancestor::button//*[contains(@style,'rotate(-90deg)')]");
                if (pinToRightTab == null)
                    throw new AutomationException("Pin to Right button not present for Column " + column);
                pinToRightTab.click();
                waitForLoadingPage();

                WebElement openedShowHideColumnIcon = driverUtil.getWebElement("//button[@aria-label='Show/Hide columns' and @aria-expanded='true']");
                if(openedShowHideColumnIcon != null)
                    openedShowHideColumnIcon.click();

                actions.sendKeys(Keys.ESCAPE).build().perform();
                columnIndex = getReportedDRPTableColumnHeadingIndex(column);

                System.out.println("columnIndex=== "+columnIndex);
                System.out.println("columnNames=== "+columnNames.size());
                if(columnIndex != totalColumnIndex)
                    throw new AutomationException("Pined " + column +" is not showing Right to the table!");
                break;

            case "PIN TO LEFT":
                WebElement pinToLeft = driverUtil.getWebElement("//label[text()='" + column + "']//ancestor::button//*[contains(@style,'rotate(90deg)')]");
                if (pinToLeft == null)
                    throw new AutomationException("Pin to left button not present for Column " + column);
                pinToLeft.click();
                waitForLoadingPage();

                openedShowHideColumnIcon = driverUtil.getWebElement("//button[@aria-label='Show/Hide columns' and @aria-expanded='true']");
                if(openedShowHideColumnIcon != null)
                    openedShowHideColumnIcon.click();

                actions.sendKeys(Keys.ESCAPE).build().perform();
                waitForLoadingPage();
                columnIndex = getReportedDRPTableColumnHeadingIndex(column);
                System.out.println("columnIndex"+columnIndex);
                if(columnIndex != 1)
                    throw new AutomationException("Pined " + column +" is not showing Left to the table!");
                break;
        }
    }

    public void verifyAllColumnHeadingShouldNotHaveColumnActionsAndMoveIcon(String iconName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(REPORT_TABLE_COLUMN_HEADING_NAMES);
        if (columnNames == null)
            throw new AutomationException("column header not fount in reports table ot it might be takes time to load");
        switch (iconName.toUpperCase()) {
            case "COLUMN ACTION":
//                WebElement columnActionIcons = driverUtil.getWebElement("//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                WebElement columnActionIcons = driverUtil.getWebElement("//*[text()='Reported DRPs']// following::table// thead//*[contains(@class,'TableHeadCell')]//button[@aria-label='Column Actions']");
                if(columnActionIcons != null)
                    throw new AutomationException("In Reports table when column filter is hide then COLUMN ACTION icon should not visible but it is present in table");
                break;

            case "MOVE COLUMN":
//                WebElement moveColumnIcons = driverUtil.getWebElement("//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                WebElement moveColumnIcons = driverUtil.getWebElement("//*[text()='Reported DRPs']// following::table// thead//*[contains(@class,'TableHeadCell')]//button[@draggable='true']");
                if(moveColumnIcons != null)
                    throw new AutomationException("In Reports table when column filter is hide then MOVE COLUMN icon should not visible but it is present in table");
                break;
        }
    }

    public void verifyTheJumpButtonAndEnterPageNoClickOnGoButton(String pageNo) throws AutomationException {
        WebElement  element=driverUtil.getWebElement(JUMP_PAGE,4);
        if (element==null)
            throw new AutomationException(" Unable to find the jump to page option");
        else
            element.sendKeys(pageNo);

        element=driverUtil.getWebElement(GO_BUTTON,4);
        if (element==null)
            throw new AutomationException(" Unable to find the Go button option");
        else
            element.click();
    }
    public void clickTheLogIconAndPerformTheLogAction(List<String> values) throws AutomationException {
        WebElement  element=driverUtil.getWebElement("//*[text()='Log Action']",4);
        if (element==null)
            throw new AutomationException(" Unable to find the jump to page option");
        else
            element.click();

        for (String column : values
        ) {
            element=driverUtil.getWebElement(String.format(RADIO_BUTTON_LOG,column),4);
            if (element==null)
                throw new AutomationException(column+" Unable to find the button in Log section");
            else
                element.click();
        }
        element=driverUtil.getWebElement("(//*[contains(@class,'components-units-RightTable-__plusIcon') and @data-icon='calendar-plus'])[2]",4);
        if (element==null)
            throw new AutomationException(" Unable to find the add plus Comment Option");
        else
            element.click();

        element=driverUtil.getWebElement("(//*[contains(@class,'components-units-RightTable-__activeNotes')])[2]",4);
        if (element==null)
            throw new AutomationException(" Unable to find the Comment Option");
        else
            element.click();
        element.sendKeys("Automation");

        element=driverUtil.getWebElement(String.format(RADIO_BUTTON_LOG,"logAction"),4);
        if (element==null)
            throw new AutomationException(" Unable to find the logged action  button");
        else
            element.click();

        takeScreenshot();
    }

    public void verifyColumResize(String columName) throws AutomationException {
       WebElement Sourcelocator=driverUtil.getWebElement(String.format(COLUM_RESIZE,columName),4);
       System.out.println("Sourcelocator:"+Sourcelocator);
        System.out.println("Sourcelocator1:"+Sourcelocator.getSize());
        System.out.println("Sourcelocator2:"+Sourcelocator.getSize().getWidth());
        if (Sourcelocator==null)
            throw new AutomationException(columName+" Unable to find the header in DRPs Table");



        Actions actions=new Actions(DriverFactory.drivers.get());
        actions.dragAndDropBy(Sourcelocator,190,15).perform();
        /*Action dragAndDrop = actions.clickAndHold(Sourcelocator)
                .moveToElement(Destinationlocator)
                .release(Destinationlocator)
                .build();
        dragAndDrop.perform();*/
    }

    public void verifyInPAFeatureButtonIsDisabled(String buttonToVerify) throws AutomationException {
        WebElement element = driverUtil.getWebElement("//*[text()=" + driverUtil.ignoreCase(buttonToVerify) + "]//..//parent::button[@data-disabled='true']");
        if (element == null)
            throw new AutomationException(buttonToVerify + " Button is enabled");
        CommonSteps.takeScreenshot();
    }

    public void verifyInPAFeatureButtonIsEnabled(String buttonToVerify) throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElement("//*[text()=" + driverUtil.ignoreCase(buttonToVerify) + "]//ancestor-or-self::button[not(disabled)]");
        if (element == null)
            throw new AutomationException(buttonToVerify + " Button is disabled");
        CommonSteps.takeScreenshot();
    }

    public void verifyDropDownOptionsRemoved(String dropDownName,String value) throws AutomationException {
        WebElement selectDropdown = driverUtil.getWebElementAndScroll("//*[text()='" + dropDownName + "']");
        if (selectDropdown == null)
            throw new AutomationException(String.format("Unable to find DropDown: %s", dropDownName));
        selectDropdown.click();

        WebElement selectDropdownOption = driverUtil.getWebElementAndScroll("//*[text()='" + value + "']");
        if (selectDropdownOption != null)
            throw new AutomationException(String.format("%s option is present in DropDown %s but it should not be there its removed from %s DropDown", value, dropDownName, dropDownName));
    }

    public static List<String> getColumnDataInPractitionersReportHistoryTable(String columnNameToSort) throws AutomationException {
        WebElement selectTaskFilterIcon = driverUtil.getWebElement("//*[text()='Report History']/following::table[1]//thead//div[contains(@class,'TableHeadCell-Content-Wrapper')]//ancestor-or-self::*[text()='" + columnNameToSort + "']");
        if (selectTaskFilterIcon == null)
            throw new AutomationException(columnNameToSort + " Column Name not visible in reports table!");
        int columnIndex = getReportHistoryTableColumnHeadingIndex(columnNameToSort);
        System.out.println("columnIndex= "+columnIndex);

        List<WebElement> columnData = driverUtil.getWebElements(String.format(REPORT_HISTORY_TABLE_COLUMN_DATA, columnIndex));
        List columnCellDataList = new ArrayList();
        for (int i = 0; i < columnData.size(); i++) {
            columnCellDataList.add(columnData.get(i).getText().trim().toLowerCase());
        }

        System.out.println("columnCellDataList== " + columnCellDataList);
        return columnCellDataList;
    }

    public void verifyPractitionersReportHistoryTableSort(String sortType, String columnNameToSort) throws AutomationException {
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
        try {
            if (sortType.equalsIgnoreCase("ASCENDING")) {
                ordered = Ordering.natural().isOrdered(getColumnDataInPractitionersReportHistoryTable(columnNameToSort));
                if (!ordered)
                    throw new AutomationException(columnNameToSort + " was expected to be sorted in ascending order but its not");
            } else {
                ordered = Ordering.natural().reverse().isOrdered(getColumnDataInPractitionersReportHistoryTable(columnNameToSort));
                if (!ordered)
                    throw new AutomationException(columnNameToSort + " was expected to be sorted in descending order but its not");
            }
        } catch (Exception ex) {
            if (sortType.equalsIgnoreCase("ASCENDING") && !(columnNameToSort.contains("DoB") || columnNameToSort.contains("Date"))) {
                List<String> tableData = getColumnDataInPractitionersReportHistoryTable(columnNameToSort);
                List<String> sortedData = getColumnDataInPractitionersReportHistoryTable(columnNameToSort);
                Collections.sort(sortedData, Comparator.reverseOrder());
                if (!tableData.equals(sortedData))
                    throw new AutomationException(columnNameToSort + " column was expected to be sorted in ascending order but its not");
            }
            if (sortType.equalsIgnoreCase("DESCENDING") && !(columnNameToSort.contains("DoB") || columnNameToSort.contains("Date"))) {
                List<String> tableData = getColumnDataInPractitionersReportHistoryTable(columnNameToSort);
                List<String> sortedData = getColumnDataInPractitionersReportHistoryTable(columnNameToSort);
                Collections.sort(sortedData);
                if (!tableData.equals(sortedData))
                    throw new AutomationException(columnNameToSort + " column was expected to be sorted in descending order but its not");
            }
            if(columnNameToSort.contains("Created") || columnNameToSort.contains("Date")){
                List<String> dateStrings = getColumnDataInPractitionersReportHistoryTable(columnNameToSort);
                List<Date> dates = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                for (String dateStr : dateStrings) {
                    try {
                        dates.add(sdf.parse(dateStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (sortType.equalsIgnoreCase("DESCENDING")) {
                    for (int i = 0; i < dates.size() - 1; i++) {
                        if (dates.get(i).before(dates.get(i + 1))) {
                            throw new AutomationException(columnNameToSort + " - column was expected to be sorted in descending order but its not");
                        }
                    }
                }
                if (sortType.equalsIgnoreCase("ASCENDING")) {
                    for (int i = 0; i < dates.size() - 1; i++) {
                        if (dates.get(i).after(dates.get(i + 1))) {
                            throw new AutomationException(columnNameToSort + " - column was expected to be sorted in descending order but its not");
                        }
                    }
                }
            }
        }
    }


    public void userSelectResendMostRecentReportOptionInFaxPopup() throws AutomationException {
        WebElement elements = driverUtil.getWebElement(String.format(FAX_PROVIDER_RADIO_BUTTON, "Resend most recent report?"), 10);
        if(elements==null)
          throw new AutomationException("Resend Most Recent Report? Option Not present in Send Fax Popup");
        elements.click();
    }


    
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

    public void userUpdateDRPsCurrentResponseDateFromDRPsPaneArea() throws AutomationException, ParseException {
        DateTimeFormatter currentDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter currentDayFormat = DateTimeFormatter.ofPattern("d");
        String currentDate = LocalDateTime.now().format(currentDateFormat);
        String currentDay = LocalDateTime.now().format(currentDayFormat);
        DateTimeFormatter currentMonthAndYearFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
        String currentMonthYear = LocalDateTime.now().format(currentMonthAndYearFormat);
        WebElement responseDateInput = driverUtil.getWebElementAndScroll(PrescriberAnalyticsPage.RESPONSE_DATE_INPUT);
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
        responseDateInput = driverUtil.getWebElement(PrescriberAnalyticsPage.RESPONSE_DATE_INPUT);
        String responseDateInputValue = responseDateInput.getAttribute("value");
        System.out.println("responseDateInputValue = " + responseDateInputValue);
        Date expectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(currentDate);
        Date selectedDate = new SimpleDateFormat("MMMM d, yyyy").parse(responseDateInputValue);
        if (!selectedDate.equals(expectedDate)) {
            throw new AutomationException(String.format("Response date is not changed to '%s' as expected.", currentDate));
        }
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

        @Override
    String getName() {
        return "Practitioners";
    }
}

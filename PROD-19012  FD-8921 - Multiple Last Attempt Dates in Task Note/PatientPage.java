package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import com.arine.automation.models.DRPInfo;
import com.arine.automation.models.Diagnosis;
import com.arine.automation.models.SigTranslation;
import com.arine.automation.util.*;
import com.aspose.pdf.internal.imaging.StringFormat;
import com.lowagie.text.pdf.PdfReader;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.arine.automation.drivers.DriverFactory.OS;
import static com.arine.automation.drivers.DriverFactory.WINDOWS;
import static com.arine.automation.glue.CommonSteps.*;
import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.MedListPage.PATIENT_DISCONTINUED_MEDICATION_RECORDS;
import static com.arine.automation.pages.MedListPage.PATIENT_MEDICATION_RECORDS;
import static com.arine.automation.pages.PageFactory.prescriberAnalyticsPage;
import static com.arine.automation.util.WebDriverUtil.DEFAULT_ELEMENT_WAIT;
import static com.arine.automation.util.WebDriverUtil.waitForAWhile;

public class PatientPage extends BasePage {
    CommonSteps common = new CommonSteps();
    public static String RELATED_TO = "Related To:";
    public static final String RELATED_TO_RADIO_BUTTON = "//label[contains(text(),'%s')]//../input[@name='relatedTo']";
    public static final String MAKE_REPORT_BUTTON = "Make Reports";
    public static final String PATIENT_NAME_INDEX = "[1]";
    public static final String PATIENT_DOB_INDEX = "[2]";
    public static final String SEARCH_PATIENT_ID_INPUT = "//*[contains(@class,'inputTextPatientId')]/..//input[contains(@class,'Input')]";
    public static final String ADVANCE_SEARCH_FIRST_NAME_INPUT = "//input[@name='firstname']";
    public static final String ADVANCE_SEARCH_LAST_NAME_INPUT = "//input[@name='lastname']";
    public static final String ADVANCE_SEARCH_PATIENT_RECORD = "//*[contains(@class,'SearchPatient-__container')]//*[contains(text(),'%s')]/..";
    public static final String ADVANCE_SEARCH_DATA_ICON = "//form//*[@data-icon='search']";
    public static final String ADVANCE_SEARCH_BUTTON = "//*[contains(@class,'advancedSearch') and .//*[text()='ADVANCED']]";
    public static final String PATIENT_GLOBAL_DETAILS_HEADER = "//*[contains(@class,'TabsGlobal-Patients-__topTable')]//th[text()='%s']";
    public static final String PATIENT_GLOBAL_DETAILS = "//*[contains(@class,'TabsGlobal-Patients-__detailTable') or contains(@class,'PatientBanner-__detailTable') or contains(@class,'TabsGlobal-Practitioners-__topTable')]//td[%s]";
    public static final String PATIENT_DETAILS = "//*[contains(@class,'PatientBanner') or contains(@class,'Patients-__detailTable')]/td";
    public static final String PATIENT_GLOBAL_TABS = "//*[contains(@class,'TabsGlobal-Patients') and contains(text(),'%s')]";
    public static final String ACTIVE_CLASS_ATTRIBUTE = "activeButton";
    public static final String PATIENT_PROFILE_DISPLAY = "//*[contains(@class,'LandingPage-components-composites-PatientProfile')]/*";
    public static final String PATIENT_PERSONAL_INFO_LABEL = "//*[contains(@class,'PatientInput')]//label[text()='%s']";
    public static final String PATIENT_PERSONAL_INFO_INPUT = "//*[contains(@class,'PatientInput')]//label[text()='%s']/..//input";
    //    public static final String PATIENT_PERSONAL_INFO_INPUT = "//*[contains(@class,'PatientInput')]//label[text()='%s']/following-sibling::input";
    public static final String PATIENT_MEDICINE_FIRST_RECORD = "//*[contains(@class,'MedDetailTable')]//tbody//tr[1]";
    public static final String SIG_INPUT = "//*[@name='sig']";
    public static final String QUESTION_INPUT = "//*[@name='question']";
    public static final String NOTES_INPUT = "//*[@name='note']";
    public static final String MEDICATION_SIG_TEXT_VALUE = "//div[ancestor::*[contains(@class,'medDetail__sigText')]]";
    public static final String MEDICATION_SIG_TEXT_SPANISH_INPUT = "//input[@name='sigText_es']";
    public static final String COMMENTS_TABLE = "//*[contains(@class,'RightTable-__header') and .//*[text()='Comments']]";
    public static final String PATIENT_MEDICATION_TABLE = "//*[contains(@class,'MedDetailTable')]//..//table";
    public static final String PATIENT_DIAGNOSIS_TABLE = "//*[contains(@class,'LeftTable-__headerTable') and .//*[text()='Medical Diagnosis']]";
    public static final String PATIENT_DRP_TABLE = "//*[contains(@class,'RecommendationTable-__headerTable') and .//*[text()='DRP']]";
    public static final String FIRST_COMMENT_NOTE = "(//textarea[contains(@class,'Notes')])[1]";
    public static final String LOG_ACTION_POPUP_ADD_NEW_COMMENT_BUTTON = "//*[contains(@class,'PatientStoryInput')]//*[@title='add date to note']";
    public static final String STORY_TAB_ADD_NEW_COMMENT_BUTTON = "//*[@title='add date to note']";
    public static final String PATIENT_TASK_TABLE_HEADER = "//*[contains(@class,'tableHeader') and text()='Patient Tasks']";
    public static final String PATIENT_TIMELINE_TABLE_HEADER = "//*[contains(@class,'tableTextHeader') and text()='Patient Timeline']";
    public static final String PATIENT_BUTTON = "//*[text()='%s']";
    public static final String PATIENT_TAB_DEFAULT_BUTTON = "//*[contains(@class,'defaultButton') and text()='%s']";
    public static final String LOG_NEW_ACTION_POPUP_LOG_BUTTON = "//button[@id='logAction']";
    public static final String CREATE_NEW_TASK_POPUP = "//*[contains(@class,'TaskNew-__popUp')]";
    public static final String CREATE_NEW_LAB_POPUP = "//*[contains(@class,'LabsNew-__popup')]";
    public static final String TASK_LOG_NEW_ACTION_POPUP = "//*[contains(@class,'PatientStoryInput-__popUp')]";
    public static final String CREATE_NEW_LOG_ACTION_POPUP = "//*[contains(@class,'PatientStoryInput-__popUp')]";
    public static final String NEW_TASK_ASSIGNED_ROLE_RADIO_BUTTON = "//input[@name='assignedRole' and @value='%s']";
    public static final String NEW_TASK_TYPE_RADIO_BUTTON = "//input[@name='taskType' and @value='%s']";
    public static final String NEW_TASK_NAME_RADIO_BUTTON = "//input[@name='taskName' and @value='%s']";
    public static final String NEW_TASK_URGENT_DUE_DATE_CHECKBOX = "//input[@type='checkbox' and @name='urgent']/..";
    public static final String NEW_TASK_NOTE_INPUT = "//textarea[@name='note']";
    public static final String NEW_TASK_SUBMIT_BUTTON = "//button[@type='submit' and text()='Create Task']";
    public static final String EDIT_TASK_STATUS_RADIO_BUTTON = "//input[@name='status' and @value='%s']";
    public static final String VERIFY_PATIENT_TASK_RECORD = "(//*[contains(@class,'tableHeader') and text()='Patient Tasks']/..//table)[3]//tr/td[text()='%s']";
    public static final String VERIFY_PATIENT_TASK_DATA = "(//*[contains(@class,'tableHeader') and text()='Patient Tasks']/..//table)[3]//tr/td[text()='%s']";
    public static final String PATIENT_TASK_RECORD_DELETE = "(//*[contains(@class,'tableHeader') and text()='Patient Tasks']/..//table)[3]//tr//td[contains(text(),'%s')]/../td[last()]/*[1]";
    public static final String DELETE_TASK_CONFIRMATION_POPUP_DELETE_BUTTON = "//button[text()='Delete this task']";
    public static final String NEW_FOR_TASK_RADIO_BUTTON = "//input[@name='relatedTaskId' and ..//label[text()='%s']]";
    public static final String NEW_LOG_ACTION_RADIO_BUTTON = "//input[@name='action' and @value='%s']";
    public static final String NEW_LOG_ACTION_TYPE_RADIO_BUTTON = "//input[@name='type' and @value='%s']";
    public static final String NEW_LOG_ACTION_STAKEHOLDER_RADIO_BUTTON = "//input[@name='stakeholder' and @value='%s']";
    public static final String NEW_LOG_ACTION_OUTCOME_RADIO_BUTTON = "//input[@name='outcome' and @value='%s']";
    public static final String NEW_LOG_ACTION_STEP_PERFORMED_CHECKBOX = "//input[contains(@name,'%s')]";
    public static final String NEW_LOG_ACTION_SUBMIT_BUTTON = "//button[@id='logAction']";
    public static final String NEW_LOG_ACTION_SUBMIT_BUTTON2 = "//button[@id='logAction' or text()='Log Action']";
    public static final String NEW_LOG_ACTION_SUBMIT_BUTTON_IN_CAMPAIGNS_TAB = "//*[@id='logAction' or text()='Log Action']";
    public static final String VERIFY_PATIENT_STORY_FIRST_RECORD = "(//*[contains(@class,'PatientStoryTable') and //*[text()='Patient Timeline']]/..//table)//tr[2]/td[text()='%s']";
    public static final String VERIFY_PATIENT_STORY_SECOND_RECORD = "(//*[contains(@class,'PatientStoryTable') and //*[text()='Patient Timeline']]/..//table)//tr[3]/td[text()='%s']";
    public static final String PATIENT_STORY_FIRST_RECORD_DELETE = "(//*[contains(@class,'PatientStoryTable') and //*[text()='Patient Timeline']]/..//table)//tr[2]/td[last()]/*[1]";
    public static final String DELETE_LOG_ACTION_CONFIRMATION_POPUP_DELETE_BUTTON = "//button[text()='Delete']";
    public static final String REPORT_TYPE_CHECKBOX = "//*[@for='%s' or contains(text(),'%s')]//../span[contains(@class,'checkbox') or contains(@class,'reportGroupDisplayName')]";
    //    public static final String REPORT_TYPE_CHECKBOX = "//*[@for='%s' or contains(text(),'%s')]";
    public static final String REPORT_FIRST_DRP_PRACTITIONER_CHECKBOX = "(//*[contains(@class,'MakeReportTable-__detailTable')]/td)[2]/label";
    public static final String SELECT_CALL_DATE_BUTTON = "//button[contains(text(),'Call') or contains(text(),'Review') or contains(text(),'OTHER Date')]";
    public static final String PATIENT_REPORT_RECORD = "//*[contains(@class,'ViewLetter-__tableContainer')]//table//tr[1]//td[text()='%s']";
    public static final String PATIENT_REPORT_VIEW_LINK = "//*[contains(@class,'existingReportTable')]//table//tr//a[text()='%s']";
    public static final String PATIENT_REPORT_WITH_ROLE_VIEW_LINK = "//*[contains(@class,'ViewLetter-__tableContainer')]//table//tr[.//td[contains(text(),'%s')]]//a[text()='%s']";
    public static final String REPORT_PDF_VIEWER = "//*[contains(@class,'ReportPopUp-__pdf') or contains(@class,'mantine-56obpe')]";
    public static final String REPORT_PDF_DATA = "//*[contains(@class,'ReportPopUp-__pdf') or contains(@class,'mantine')]/object";
    public static final String CLOSE_PDF_VIEWER_BUTTON = "//*[contains(@class,'ReportPopUp-__button')]";
    public static final String TASK_DETAILS_EDIT_CONTAINER = "//*[contains(@class,'LandingPage-components-units-TaskInput-__container')]";
    public static final String TASK_ACTION_BUTTON = "//button[.//*[text()='%s']]";
    public static final String ADD_NEW_LAB_ICON = "//*[contains(@class,'LabsTable-__iconAdd')]";
    public static final String SEARCH_LAB_NAME = "//*[@list='labsName']";
    public static final String USER_NAME_OPTION = "//*[text()='%s']";
    public static final String LAB_NAME_OPTION = "//option[text()='%s']";
    public static final String LAB_VALUE_INPUT = "//input[contains(@name,'value')]";
    public static final String LAB_DATE_INPUT = "//*[@name='labDate']";
    public static final String LAB_DATE_CURRENT_DATE = "//*[contains(@class,'datepicker__day--keyboard-selected')]";
    public static final String LAB_RECORD = "//*[contains(@class,'LabsTable-__stickyTable')]//th[text()='%s']/../td[1]";
    public static final String LAB_RECORD_DELETE_BUTTON = "//*[contains(@class,'LabsTable-__stickyTable')]//th[text()='%s']/../td[1]//*[contains(@data-tip,'delete lab')]";
    public static final String LAB_RECORD_DELETE_POPUP = "//*[contains(@class,'LabsRemove-__popup')]";
    public static final String LAB_RECORD_DELETE_CHOICES = "//*[contains(@class,'LabsRemove-__choice')]/td[last()]/*";
    public static final String REPORT_DRP_RECORD_CHECKBOX = "//tr[.//*[text()='%s']]//span[contains(@class,'MakeReportTable-__checkbox')]";
    public static final String REPORT_DRP_RECORD_DELETE = "//tr[.//*[text()='%s']]//td[last()]/*";
    public static final String FIND_COMMENT = "//textarea[contains(@class,'Notes') and contains(text(),'%s')]";
    public static final String DELETE_COMMENT_BUTTON = "//textarea[contains(text(),'%s')]/../..//*[@id='deleted']";
    public static final String PINNED_COMMENT_BUTTON = "//textarea[contains(text(),'%s')]/../..//*[@id='pinned']";
    public static final String LOCKED_COMMENT_BUTTON = "//textarea[contains(text(),'%s')]/../..//*[@id='locked']";
    public static final String HIGHLIGHT_COMMENT_BUTTON = "//textarea[contains(text(),'%s')]/../..//*[@id='highlight']";
    public static final String SHOW_HIDE_COMMENTS_TOGGLE_BUTTON = "//*[contains(@class,'IphoneToggle-__toggleShape')]";
    public static final String MEDICATION_DISCONTINUE_BUTTON = "//*[contains(@class,'TableHeadCell')]//*[contains(@class,'MedDetailTable-__iconBin') and not(contains(@class,'iconRecoverBin'))]";
    public static final String MEDICATION_ACTIVE_BUTTON = "//*[contains(@class,'TableHeadCell')]//*[contains(@class,'MedDetailTable-__iconBin') and contains(@class,'iconRecoverBin')]";
    public static final String PATIENT_MEDICATION_RECORD = "//*[contains(@class,'MedDetailTable-__tableContainer')]//tr[.//td[text()='%s']]";
    public static final String PATIENT_MEDICATION_RECORD_CHECKBOX = "//tr[.//td[.//text()='%S']]/td//input[contains(@class,'Checkbox')]";
    public static final String SELECT_ALL_MED_RECORD_CHECKBOX = "//*[contains(@class,'TableHeadCell')]//input[contains(@class,'Checkbox') and contains(@aria-label,'select all')]";
    public static final String PATIENT_DIAGNOSIS_RECORDS = "//table[.//thead[.//*[text()='Medical Diagnosis']]]/../..//tr";
    public static final String PATIENT_MEDICAL_DIAGNOSIS_TABLE = "//*[contains(@class,'LeftTable-__headerTable')]//th[text()='%s']";
    public static final String INFORMATION_DIALOG = "//*[@role='dialog' and .//*[contains(text(),\"%s\")]]";
    public static final String PATIENT_CARE_TEAM_ADD_PRACTITIONER = "//*[text()='Practitioners']/following::table[1]//span//*[name()='svg' and (contains(@class, 'tabler-icon-text-plus') or contains(@class, 'icon-tabler-text-plus'))]";
    public static final String PATIENT_CARE_TEAM_ADD_PHARMACY = "//*[contains(@class,'PharmacyTable-__iconAdd')]";
    public static final String ADD_NEW_PRACTITIONER_POPUP = "//*[contains(@class,'SelectPractitioner-__wrapperLoaded') and .//*[contains(text(),'%s')]]";
    public static final String ADD_NEW_PHARMACY_POPUP = "//*[contains(@class,'SelectPharmacy-__wrapperLoaded') and .//*[contains(text(),'%s')]]";
    public static final String SEARCH_PRACTITIONER_FIRST_NAME_INPUT = "//input[@name='searchFirstName']";
    public static final String SEARCH_PRACTITIONER_NPI_INPUT = "//input[@name='searchNPI']";
    public static final String SEARCH_PRACTITIONER_PHONE_NUMBER_INPUT = "//input[@name='searchPhone']";
    public static final String SEARCH_PRACTITIONER_LAST_NAME_INPUT = "//input[@name='searchLastName']";
    public static final String SEARCH_PRACTITIONER_PHONE_INPUT = "//input[@name='searchPhone']";
    public static final String SPINNER_ICON = "//*[@data-icon='spinner']";
    public static final String PRACTITIONER_CHOICE_RECORD = "//*[contains(@class,'SelectPractitioner-__choiceWrapper') and .//*[text()='%s']]";
    public static final String CARE_TEAM_PRACTITIONER_RECORD = "//*[text()='Practitioners']/following-sibling::*//table//tr[.//*[text()='%s']]";
    public static final String CARE_TEAM_PHARMACY_RECORD = "//*[contains(@class,'PharmacyTable-__tableContainer')]//tr[.//*[text()='%s']]";
    public static final String CARE_TEAM_PRACTITIONER_DELETE = "//*[text()='Practitioners']/following::table[1]//tr[.//*[text()='%s']]/td[last()]/*";
    public static final String CARE_TEAM_PHARMACY_DELETE = "//*[contains(@class,'PharmacyTable-__tableContainer')]//tr[.//*[text()='%s']]/td[last()]/*";
    public static final String CARE_TEAM_PRACTITIONER_DELETE_CONFIRMATION = "//button[text()='Remove this pratitioner']";
    public static final String CARE_TEAM_PHARMACY_DELETE_CONFIRMATION = "//button[text()='Remove this pharmacy']";
    public static final String PRACTITIONER_ROLE_SELECT = "//select[@name='role']";
    public static final String PATIENT_PREFERRED_LANGUAGE_DROPDOWN = "//*[@id='preferredLanguage']";
    public static final String PATIENT_REPORT_LANGUAGE_DROPDOWN = "//*[@id='reportLanguage']";
    public static final String REPORT_UPLOAD_FILES_INPUT = "//input[@type='file']";
    public static final String REPORT_FILE_RENAME_INPUT = "//input[@name='%s']";
    public static final String REPORT_POPUP_DATA_OBJECT = "//*[contains(@class,'ReportPopUp')]/object";
    public static final String CREATE_TASK_PARAM_TASK_NAME_HEADER = "//*[contains(@class,'TaskNew-__stepsListh')]//*[contains(text(),'%s')]";
    public static final String PATIENT_ALLERGIES_INPUT = "//*[@id='allergies']";
    public static final String SCHEDULE_APPOINTMENT_FRAME = "//*[@class='calendly-inline-widget']/iframe";
    public static final String PATIENT_NEXT_DAY_APPOINTMENT_DATE = "//tbody[contains(@class,'calendar-table')]//button/..";
    public static final String PATIENT_APPOINTMENT_CALENDER_NEXT_MONTH_BUTTON = "//button[@aria-label='Go to next month']";
    public static final String PATIENT_APPOINTMENT_WINDOW_CLOSE = "//*[contains(@class,'Calendly-__close')]";
    public static final String PATIENT_APPOINTMENT_FIRST_AVAILABLE_SLOT = "(//*[@data-container='time-button'])[1]";
    public static final String PATIENT_APPOINTMENT_CONFIRM_SLOT_BUTTON = "(//button[contains(text(),'Confirm')])[1]";
    public static final String PATIENT_APPOINTMENT_SCHEDULE_EVENT_BUTTON = "//button[@type='submit' and .//*[text()='Schedule Event']]";
    public static final String PATIENT_APPOINTMENT_UPDATE_EVENT_BUTTON = "//button[@type='submit' and .//*[text()='Update Event']]";
    public static final String PATIENT_APPOINTMENT_EVENT_CANCEL = "//button[@type='submit' and .//*[text()='Cancel Event']]";
    public static final String PATIENT_APPOINTMENT_SCHEDULED_TIME = "//*[contains(@class,'TabsGlobal-Patients-__apptTime')]";
    public static final String PATIENT_APPOINTMENT_SCHEDULED_CANCEL = "//*[contains(@class,'TabsGlobal-Patients-__btnAdd') and text()='Cancel']";
    public static final String PATIENT_APPOINTMENT_RESCHEDULED = "//*[contains(@class,'TabsGlobal-Patients-__btnAdd') and text()='Reschedule']";
    public static final String PATIENT_ALLERGY_OPTION = "//*[contains(@class,'AllergiesEdit-__radioName') and .//*[text()='%s']]";
    public static final String PATIENT_ALLERGY_UPDATE_BUTTON = "//button[contains(@class,'AllergiesEdit-__button') and text()='Update']";
    public static final String PATIENT_ALLERGY_DRUG_NAME_INPUT = "//input[@list='allergyDrugNames']";
    public static final String PATIENT_ALLERGIES = "//input[@id='allergies']";
    public static final String RERUN_ANALYSIS = "//button[text()='Rerun Analysis']";
    public static final String PRACTITIONER_SEARCH_RESULT_NAME_COLUMN = "//*[contains(@class,'SelectPractitioner') or contains(@class,'SelectPharmacy')]//tbody/tr/td[2]";
    //    public static final String PRACTITIONER_SEARCH_RESULT_PHONE_COLUMN = "//*[contains(@class,'SelectPractitioner') or contains(@class,'SelectPharmacy')]//tbody/tr/td[6]";
    public static final String PRACTITIONER_SEARCH_RESULT_PHONE_COLUMN = "//*[contains(@class,'SelectPractitioner') or contains(@class,'SelectPharmacy')]//tbody/tr/td[%s]";
    public static final String REPORT_NAMES = "//*[contains(@class,'ExistingReportsTable')]//a";
    public static final String RESTORE_ARCHIVED_REPORT = "//tr//*[text()='%s']/..//..//*[@data-icon='trash-restore']";
    public static final String REPORT_SELECTION_CHECKBOX = "//*[text()='%s']/ancestor::tr//td//div[contains(@class, 'Checkbox-body')]";
    public static final String MTM_REPORT_PREVIEW = "//*[contains(@class,'PreviewReport-__container')]//table";
    public static final String SELECT_ALL_REPORT = "//*[contains(@class,'existingReportTable')]//*[contains(@class,'TableHeadCell')]//*[contains(@class,'Checkbox-body')]";
    //    public static final String SELECT_ALL_REPORT = "//*[@name='toggleAll']/../span";
    public static final String BUTTON_ARCHIVE_SELECTED = "//button[.//text()='Archive Selected' or .//text()='Delete Selected']";
    public static final String REPORT_RECORDS = "//table//*[contains(@class,'ViewLetter-ExistingReports')]//..//a";
    //    public static final String REPORT_RECORDS = "//table//td[contains(@class,'reportViewlink')]";
    public static final String REPORT_TABS_DRP = "//div[contains(@class,'MakeReportTable-__tableContainer')]/table//td[1]//span";
    public static final String REPORT_TABS_DRP_PROVIDER_ASSESSMENT = "//*[@id='provAsmt']";
    public static final String REPORT_TABS_DRP_PROVIDER_RECOMMENDATION = "//*[@id='provRec']";
    public static final String REPORT_TABS_DRP_PATIENT_ASSESSMENT = "//*[@id='patAsmt']";
    public static final String REPORT_TABS_DRP_PATIENT_RECOMMENDATION = "//*[@id='patRec']";
    public static final String REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX = "//*[@name='CAR1']";
    public static final String REPORT_TABS_SELECT_ALL_PCP_CHECKBOX = "//*[@name='PCP1']";
    public static final String REPORT_TABS_SELECT_ALL_PATIENT_CHECKBOX = "//*[@name='PATIENT']";
    //public static final String REPORT_TABS_SELECT_ALL_PCP_CHECKBOX = "//*[@name='PCP1']/following-sibling::span[1]";
    private static final Pattern TAG_REGEX = Pattern.compile("<span>(.+?)<br>", Pattern.DOTALL);
    private static final String LOG_STORY_HEADER_COLUMN = "//*[contains(@class,'PatientStoryTable-__headerTable')]//table//th[%s]";
    private static final String TASK_HEADER_COLUMN = "//*[contains(@class,'PatientTriageTable-__headerTable')]//table//th[%s]";
    private static final String LOG_STORY_TABLE = "//*[contains(@class,'PatientStoryTable-__tableContainer')]//table";
    private static final String PATIENT_FIRST_AUTO_LOG_RECORD = "//*[contains(@class,'PatientStoryTable-__tableContainer')]//table//tr[@class='nonDraggableArea'][1]";
    private static final String TASK_STORY_TABLE = "//*[contains(@class,'PatientTriageTable-__tableContainer')]//table";
    private static final String LOG_STORY_TABLE_FILTER_INPUT = "//*[contains(@class,'PatientStoryTable-__tableContainer')]//table//tr[1]/td[%s]/input";
    private static final String TASK_TABLE_FILTER_INPUT = "//*[contains(@class,'PatientTriageTable-__tableContainer')]//table//tr[1]/td[%s]/input";
    private static final String LOG_STORY_TABLE_COLUMN = "//*[contains(@class,'PatientStoryTable-__tableContainer')]//table//tr[@class='nonDraggableArea']/td[%s]";
    private static final String TASK_TABLE_COLUMN = "//*[contains(@class,'PatientTriageTable-__tableContainer')]//table//tr[contains(@class,'PatientTriageTable')]/td[%s]";
    public static Map<String, Integer> GLOBAL_DETAILS_TAB_MAPPING = new HashMap<>();
    public static List<DRPInfo> drpInfoList = new ArrayList<>();
    public static String NEW_TASK_ASSIGNED_TO = "Assigned to";
    public static String NEW_TASK_ASSIGNED_TO_USER_DROPDOWN = "//label[@for='xassignedUsers']";
    public static String NEW_TASK_TYPE = "Type";
    public static String NEW_TASK_TASK = "Task";
    public static String NEW_TASK_DUE_DATE = "Due Date";
    public static String NEW_TASK_NOTES = "NOTES";
    public static String STATUS = "Status";
    public static String NOTE = "Note";
    public static String NEW_FOR_TASKS = "FOR TASK(S)";
    public static String NEW_LOG_ACTION = "ACTION";
    public static String NEW_LOG_ACTION_TYPE = "TYPE";
    public static String NEW_LOG_ACTION_STAKEHOLDER = "STAKEHOLDER";
    public static String NEW_LOG_ACTION_OUTCOME = "OUTCOME";
    public static String NEW_LOG_ACTION_STEP_PERFORMED = "STEP(S) PERFORMED";
    public static String NEW_LOG_ACTION_COMMENT = "COMMENT";
    public static String NEW_LOG_ACTION_NOTE = "NOTE";
    public static String DATE_PERFORMED = "DATE PERFORMED";
    private static Map<String, Integer> LOG_DATA_HEADER_MAPPING = new HashMap<>();
    private static Map<String, Integer> TASK_DATA_HEADER_MAPPING = new HashMap<>();
    private static final String I_UNDERSTAND_BUTTON = "//*[contains(text(),'I understand')]";
    private static final String DELETE_DRP_RECORD = "//tr[contains(@class,'MakeReportTable-__detailTable')]//td[last()]/*";

    private static final String LAB_RECORD_COLUMN_HEADER = "//*[contains(@class,'LabsTable-__stickyTable')]//th[.//text()='%s']";
    private static final String STATE_DROPDOWN_BUTTON = "//*[@name='statecode' and not(@disabled)]";
    private static final String STATE_DROPDOWN_OPTIONS = "//*[@name='statecode' and option='%s']";
    private static final String PATIENT_BIRTH_DATE_INPUT = "//input[@name='birthDate']";
    private static final String REPORT_TABS_SELECT_ALL_DRP_CHECKBOX = "//*[contains(text(),'%s')]/..//ancestor::th//span[contains(@class,'checkbox')]";
    private static final String GENERATED_REPORT_ROLE = "//*[contains(@class,'existingReportTable')]//td//a[text()='%s']/..//../..//following-sibling::td[2]";
    private static final String GENERATED_REPORT_PRACTITIONER = "//*[contains(@class,'existingReportTable')]//td//a[text()='%s']/..//../..//preceding-sibling::td[1]";
    private static final String REPORTS_TABLE_IN_GLOBAL_REPORTS_TAB = "//*[contains(@class,'ReportTable')]//table";
    private static final String SOURCE_DROPDOWN_OPTIONS = "//*[@id='labsSource']/..//*[text()='%s']";
    private static final String ADD_NEW_LAB_SOURCE_DROPDOWN = "//*[@list='labsSource']";
    private static final String SPECIALTY_DROPDOWN_BUTTON = "//*[@name='specialty' and not(@disabled)]";
    private static final String SPECIALTY_DROPDOWN_OPTIONS = "//*[@name='specialty']/..//option[@value='%s']";
    private static final String ROLE_DROPDOWN_OPTIONS = "//*[@name='role' and option='%s']";
    private static final String NEW_LOG_ACTION_STEP_PERFORMED_NAME_HEADER = "//*[contains(@class,'PatientStoryInput-__stepsList')]//*[contains(text(),'%s')]";
    private static final String ADVANCE_SEARCH_PATIENT_COUNT = "//*[contains(@class,'SearchPatient-__container')]/..//tbody//..//tr[not(th)]";
    private static final String GENERATED_REPORT_TYPE = "//*[contains(@class,'tableContainer')]//tr//a[text()='%s']/../..//preceding-sibling::td[4]";
    private static final String GENERATED_REPORT_LANGUAGE_COLUMN = "//*[contains(@class,'tableContainer')]//tr//a[text()='%s']/../..//following-sibling::td[1]";
    private static final String REPORT_DRP_RECORD_CHECKED_CHECKBOX = "//span[text()='%s']/../..//input[@checked]//following-sibling::span[contains(@class,'MakeReportTable-__checkbox')]";
    private static final String DRP_RECORD_CHECKBOX = "//span[text()='%s']/../..//input[not(@checked)]//following-sibling::span[contains(@class,'MakeReportTable-__checkbox')]";
    public static final String ADVANCE_SEARCH_BIRTH_DATE_INPUT = "//input[@name='dob']";
    public static final String ADVANCE_SEARCH_POPUP_INPUT = "//*[contains(@class,'SearchPatient') and text()='%s']/..//following-sibling::input";
    public static final String SEARCH_PATIENT_PHONE_INPUT = "//input[contains(@name,'phone')]";
    public static final String SEARCH_PATIENT_LAST_NAME_INPUT = "//input[contains(@name,'lastname')]";
    public static final String SEARCH_PATIENT_FIRST_NAME_INPUT = "//input[contains(@name,'firstname')]";
    public static final String SEARCH_PATIENT_HPID_INPUT = "//input[contains(@name,'hpid')]";
    public static final String SEARCH_PATIENT_ZIP_CODE_INPUT = "//input[contains(@name,'zip_code')]";
    public static final String SEARCH_PATIENT_STATE_NAME_INPUT = "//input[contains(@name,'state')]";
    public static final String SEARCH_PATIENT_CITY_NAME_INPUT = "//input[contains(@name,'city')]";
    public static final String SEARCH_PATIENT_ICON = "//*[@id='searchPatient']";
    public static final String ALL_DRAFT_REPORTS_CHECKBOX = "//*[contains(@class,'ExistingReportsTable')]//a//..//*[text()='DRAFT']/../../../../..//*[contains(@class,'Checkbox-body')]";
    public static final String PROVIDER_DROPDOWN_INPUT = "//*[contains(@class,'labProvider')]//..//input[contains(@class,'Select-input')]";
    public static final String PROVIDER_DROPDOWN_OPTIONS_IN_LAB_TAB = "//*[contains(@class,'labProvider')]//..//*[@role='option']";
    public static final String PROVIDER_DROPDOWN_OPTIONS_IN_MED_LIST_TAB = "//*[contains(text(),'Prescriber')]//..//*[@role='option']";

    private static final String PATIENT_PLAN_INPUT = "//input[@name='planName']";
    private static final String PATIENT_PLAN_INPUT_DISABLED = "//input[@name='planName' and @disabled]";


    private static final String DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB = "//label[text()='%s']/..//input[@type='search']";
    private static final String DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP = "//*[@role='option' and text()='%s']";
    private static final String DRPS_TRIANGLE_ICON_IN_DRP_TABLE = "//tr[.//*[contains(@class,'drp') and text()='%s']]//*[contains(@data-for,'highPriorityDrp')]";
    private static final String DRPS_LIGHTNING_BOLT_ICON_IN_DRP_TABLE = "//tr[.//*[contains(@class,'drp') and text()='%s']]//*[contains(@data-for,'roiDrp')]";
    private static final String SHOW_ONLY_HIGH_OR_ROI_DRPS_ICON = "//th[contains(@class,'RecommendationTable-')]//*[contains(@data-for,'showOnlyHighOrROI')]";
    private static final String MULTIPLE_PATIENT_PROFILE_ICON = "//*[contains(@class,'PatientBanner-__patientNameCell')]//..//*[contains(@class,'icon-tabler-users') or contains(@class,'tabler-icon-users')]";
    private static final String PATIENT_RECORD_IN_PROFILE_SELECTION_RECORD_TABLE = "//*[contains(@class,'PatientProfilesSelector')]//..//table//tbody//tr//td[text()='%s']";
    private static final String PHARMACIES_TABLE_COLUMN_HEADING_NAMES_IN_SEARCH_POPUP = "//*[contains(@class,'SelectPractitioner-') or contains(@class,'SelectPharmacy')]//table//th";
    private static final String DELETE_MED_ICON_BUTTON = "//*[contains(@class,'nameWithIcon')]//*[contains(@class,'MedDetailTable-__iconBin')]";

    static {
        GLOBAL_DETAILS_TAB_MAPPING.put("Name", 1);
        GLOBAL_DETAILS_TAB_MAPPING.put("DOB", 2);
        GLOBAL_DETAILS_TAB_MAPPING.put("Sex", 3);
        GLOBAL_DETAILS_TAB_MAPPING.put("Age", 4);
        GLOBAL_DETAILS_TAB_MAPPING.put("30d Hospital", 5);
        GLOBAL_DETAILS_TAB_MAPPING.put("Medication Allergies", 6);
        GLOBAL_DETAILS_TAB_MAPPING.put("Prev CMR Compl Date", 7);
    }

    static {
        LOG_DATA_HEADER_MAPPING.put("Date", 1);
        LOG_DATA_HEADER_MAPPING.put("Logged by", 2);
        LOG_DATA_HEADER_MAPPING.put("Action", 3);
        LOG_DATA_HEADER_MAPPING.put("Type", 4);
        LOG_DATA_HEADER_MAPPING.put("Stakeholder", 5);
        LOG_DATA_HEADER_MAPPING.put("Step(s) Performed", 6);
        LOG_DATA_HEADER_MAPPING.put("Outcome", 7);

        TASK_DATA_HEADER_MAPPING.put("Due Date", 1);
        TASK_DATA_HEADER_MAPPING.put("Priority", 2);
        TASK_DATA_HEADER_MAPPING.put("Status", 3);
        TASK_DATA_HEADER_MAPPING.put("Task", 4);
        TASK_DATA_HEADER_MAPPING.put("Name", 5);
        TASK_DATA_HEADER_MAPPING.put("Plan", 6);
        TASK_DATA_HEADER_MAPPING.put("Phone", 7);
        TASK_DATA_HEADER_MAPPING.put("Note", 8);
        TASK_DATA_HEADER_MAPPING.put("Assigned To", 9);
        TASK_DATA_HEADER_MAPPING.put("Related To Role", 10);
        TASK_DATA_HEADER_MAPPING.put("Related To Name", 11);
        TASK_DATA_HEADER_MAPPING.put("Language", 12);
    }

    private ThreadLocal<String> lastUsedTimeStamp = new ThreadLocal<>();
    private ThreadLocal<String> lastScheduledAppointTime = new ThreadLocal<>();

    private static List<String> getAllDatesValues(final String str) {
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    public static Date getLatestDate(Set<String> dates) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yy");
        String latest = null;
        for (String dateStr : dates) {
            if (latest == null) {
                latest = dateStr;
            } else {
                Date latestDate = simpleFormat.parse(latest);
                Date newDate = simpleFormat.parse(dateStr);
                if (latestDate.before(newDate))
                    latest = dateStr;
            }
        }
        return simpleFormat.parse(latest);
    }

    public static boolean verifyDiagnosisOrder(List<String> actualDiagnosisOrder, List<Diagnosis> actualData) {
        Collections.sort(actualData);
        for (int i = 0; i < actualData.size(); i++) {
            if (!actualDiagnosisOrder.get(i).equalsIgnoreCase(actualData.get(i).title))
                return false;
        }
        return true;
    }

    public void searchPatient(String id) throws AutomationException {
        PageFactory.homePage().gotoMenu("Patient");
        WebElement searchInput = driverUtil.getWebElement(SEARCH_PATIENT_ID_INPUT);
        if (searchInput == null)
            throw new AutomationException("Unable to locate patient search input!");
        String existing = searchInput.getAttribute("value");
        driverUtil.waitForLoadingPage();
        if (existing == null || !existing.equalsIgnoreCase(id)) {
            searchInput.clear();
            searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            searchInput.sendKeys(id, Keys.ENTER);
            if (!checkErrorPopup())
                driverUtil.waitForLoadingPage();
            WebElement element = driverUtil.getWebElement("//*[contains(@class,'Autocomplete-itemsWrapper')]");
            if (element != null) {
                Actions actions = new Actions(DriverFactory.drivers.get());
                actions.sendKeys(Keys.ESCAPE).build().perform();
            }
        }
    }

    public String getPatientDetail(String type) throws AutomationException {
        driverUtil.waitForAWhile(WAIT_3_SECOND);
        return driverUtil.getWebElement(PATIENT_DETAILS + type, WebDriverUtil.MAX_ELEMENT_WAIT).getText();
    }

    public boolean verifyGlobalTab(String tabName) throws AutomationException {
        WebElement globalTab = driverUtil.findElement(String.format(PATIENT_GLOBAL_TABS, tabName));
        if (globalTab == null)
            throw new AutomationException(String.format("Global tab %s is not being displayed!", tabName));
        return true;
    }

    public boolean verifyActiveGlobalTab(String tabName) throws AutomationException {
        WebElement globalTab = driverUtil.findElement(String.format(PATIENT_GLOBAL_TABS, tabName));
        if (globalTab == null)
            throw new AutomationException(String.format("Global tab %s is not being displayed!", tabName));
        String classAttributes = globalTab.getAttribute("class");
        if (classAttributes.contains(ACTIVE_CLASS_ATTRIBUTE))
            return true;
        return false;
    }

    public boolean clickOnGlobalTab(String tabName) throws AutomationException {
        WebElement globalTab = driverUtil.findElement(String.format(PATIENT_GLOBAL_TABS, tabName));
        if (!verifyActiveGlobalTab(tabName)) {
            driverUtil.waitForElementToBeClickable(String.format(PATIENT_GLOBAL_TABS, tabName));
            globalTab.click();
            driverUtil.waitForLoadingPage();
        }
        return true;
    }

    public String getPatientInfo(String attribute) throws AutomationException {
        int index = GLOBAL_DETAILS_TAB_MAPPING.get(attribute);
        WebElement patientInfo = driverUtil.findElement(String.format(PATIENT_GLOBAL_DETAILS, index));
        if (patientInfo == null)
            throw new AutomationException(String.format("Unable to locate patient information about %s", attribute));
        return patientInfo.getText();
    }

    public boolean verifyPatientInfo(String attribute, String value) throws AutomationException {
        int index = GLOBAL_DETAILS_TAB_MAPPING.get(attribute);
        WebElement patientInfo = driverUtil.findElement(String.format(PATIENT_GLOBAL_DETAILS, index));
        if (patientInfo == null)
            throw new AutomationException(String.format("Unable to locate patient information about %s", attribute));
        if (!value.equalsIgnoreCase(patientInfo.getText()))
            throw new AutomationException(String.format("Patient information does not match! We supposed to get patient %s as: %s", attribute, value));
        return true;
    }

    public void clickOnNameLink() throws AutomationException {
        int index = GLOBAL_DETAILS_TAB_MAPPING.get("Name");
        WebElement patientInfo = driverUtil.findElementAndScroll(String.format(PATIENT_GLOBAL_DETAILS, index));
        if (patientInfo == null)
            throw new AutomationException("Unable to find name link!");
        patientInfo.click();
        WebDriverUtil.waitForAWhile(2);
    }

    public void clickOnPatientAllergiesInput() throws AutomationException {
        WebElement patientAllergiesInput = driverUtil.findElementAndScroll(PATIENT_ALLERGIES_INPUT);
        if (patientAllergiesInput == null)
            throw new AutomationException("Unable to find patient Allergies input!");
        try {
            patientAllergiesInput.click();
        } catch (Exception e) {
            driverUtil.clickUsingJavaScript(PATIENT_ALLERGIES_INPUT);
        }
    }

    public void verifyPatientDetailsSection() throws AutomationException {
        WebElement patientDetailsSection = driverUtil.getWebElementAndScroll(PATIENT_PROFILE_DISPLAY);
        if (patientDetailsSection == null)
            throw new AutomationException("Patient details section is not being displayed!");
    }

    public void verifyPatientInfoLabel(String label) throws AutomationException {
        WebElement labelElement = driverUtil.getWebElementAndScroll(String.format(PATIENT_PERSONAL_INFO_LABEL, label));
        if (labelElement == null)
            throw new AutomationException(String.format("Patient %s label is not being displayed", label));
    }

    public void verifyPatientInfoInput(String label) throws AutomationException {
        WebElement labelElement = driverUtil.getWebElementAndScroll(String.format(PATIENT_PERSONAL_INFO_INPUT, label));
        if (labelElement == null)
            throw new AutomationException(String.format("Patient %s is not being displayed", label));
    }

    public String getPatientPersonalDetail(String label) throws AutomationException {
        WebElement infoInput = driverUtil.getWebElementAndScroll(String.format(PATIENT_PERSONAL_INFO_INPUT, label));
        if (infoInput == null)
            throw new AutomationException(String.format("%s input box is not being displayed!", label));
        return infoInput.getText();
    }

    public void updatePatientPersonalDetail(String label, String value) throws AutomationException {
        clickOnNameLink();
        WebElement infoInput = driverUtil.getWebElementAndScroll(String.format(PATIENT_PERSONAL_INFO_INPUT, label));
        if (infoInput == null)
            throw new AutomationException(String.format("%s input box is not being displayed!", label));
        String tagName = infoInput.getTagName();
        switch (tagName) {
            case "input":
//                infoInput.click();
                driverUtil.clickUsingJavaScript(String.format(PATIENT_PERSONAL_INFO_INPUT, label));
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

    public void veryPatientPersonalDetail(String label, String expectedValue) throws AutomationException {
        WebElement infoInput = driverUtil.getWebElementAndScroll(String.format(PATIENT_PERSONAL_INFO_INPUT, label));
        if (infoInput == null)
            throw new AutomationException(String.format("%s input box is not being displayed!", label));
        String actualValue = infoInput.getAttribute("value");
        if (!actualValue.contains(expectedValue))
            throw new AutomationException(String.format("%s details is not being matched! Expected value: %s but found: %s", label, expectedValue, actualValue));
    }

    public void clickOnMedicineFirstRecord() throws AutomationException {
        WebElement record = driverUtil.getWebElement(PATIENT_MEDICINE_FIRST_RECORD);
        if (record == null)
            throw new AutomationException("No Records available in patient medicine information table!");
        record.click();
    }

    public void verifySigDetails() throws AutomationException {
        WebElement sig = driverUtil.findElementAndScroll(SIG_INPUT);
        if (sig == null)
            throw new AutomationException("SIG details is not being displayed!");
    }

    public void verifyAllSigAbbreviations() throws AutomationException {
        StringBuilder unmatched = new StringBuilder();
        String unmatchedKey = null;
        try {
            Pattern pattern = Pattern.compile("(?i)\\s*\\(\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?\\s*mg\\)\\s*");
            WebElement sig = driverUtil.findElementAndScroll(SIG_INPUT);
            if (sig == null)
                throw new AutomationException("Unable to locate SIG input box!");
            Map<String, Object> mapping = JSONUtil.readAbbreviations(JSONUtil.ABBREVIATIONS_FILE_PATH);
            CommonSteps.REPORT_LOGGER.log("Verify SIG Abbreviations:");
            CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
            for (String key : mapping.keySet()) {
                CommonSteps.REPORT_LOGGER.log(String.format("SIG abbreviation: [%s : %s]", key, mapping.get(key).toString()));
                sig.click();
                sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                sig.sendKeys(key, Keys.ENTER);
                WebDriverUtil.waitForAWhile();
                WebElement sigTextInput = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_VALUE, WebDriverUtil.NO_WAIT);
                if (sigTextInput == null)
                    throw new AutomationException("SIG Text details is not being displayed for: " + key + "!");
//                String sigText= sigTextInput.getAttribute("value");
                String sigText = sigTextInput.getText();
                Matcher matcher = pattern.matcher(sigText);
                if (matcher.find()) {
                    sigText = sigText.replaceAll("(?i)\\s*\\(\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?\\s*mg\\)\\s*", " ").trim();
                }
//                waitForLoadingPage();
                if (!sigText.equalsIgnoreCase(mapping.get(key).toString())) {
                    unmatched.append(String.format("SIG Text: <b>'%s'</b>  is not being matched for: [<b>%s : %s</b>] <br>", sigText, key, mapping.get(key)));
                    unmatched.append(System.getProperty("line.separator"));
                    unmatchedKey = key;
                }
            }
            if (unmatched.length() > 0) {
                sig.click();
                sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                sig.sendKeys(unmatchedKey, Keys.ENTER);
                WebDriverUtil.waitForAWhile();
                throw new AutomationException(unmatched.toString());
            }
            CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        } catch (Exception ex) {
            throw new AutomationException(ex.getMessage());
        }
    }

    public void updateSigDetails(String value) throws AutomationException {
        if (value == null)
            value = EMPTY_STRING;
        WebElement sig = driverUtil.findElementAndScroll(SIG_INPUT);
        if (sig == null)
            throw new AutomationException("SIG details is not being displayed!");
        WebDriverUtil.waitForAWhile(2);
        sig.click();
        WebDriverUtil.waitForAWhile(2);
        sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        if (!value.isEmpty())
            sig.sendKeys(value, Keys.ENTER);
        WebDriverUtil.waitForAWhile(2);
        WebElement sigText = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_VALUE, WebDriverUtil.NO_WAIT);
        if (sigText == null)
            throw new AutomationException("SIG Text details is not being displayed!");
        if (!value.isEmpty())
            sigText.click();
    }

    public void updateSigDetailsAsBlank() throws AutomationException {
        WebElement sig = driverUtil.findElementAndScroll(SIG_INPUT);
        if (sig == null)
            throw new AutomationException("SIG Input is not being displayed!");
        sig.click();
        sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public void verifySigText(String value) throws AutomationException {
        WebElement sig = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_VALUE);
        if (sig == null)
            throw new AutomationException("SIG Text details is not being displayed!");
//        String sigText= sig.getAttribute("value");
        String sigText = sig.getText();
        waitForLoadingPage();
        if (!sigText.contains(value))
            throw new AutomationException(String.format("SIG Text details is not being matched! We supposed to get %s but we are getting %s", value, sigText));
    }

    public void clearSigInformation() throws AutomationException {
        WebElement sig = driverUtil.getWebElement(SIG_INPUT);
        if (sig == null)
            throw new AutomationException("SIG details is not being displayed!");
        sig.click();
        sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        WebDriverUtil.waitForAWhile(2);
    }

    @Override
    public String getName() {
        return "Patient";
    }

    public void verifyCommentsTable() throws AutomationException {
        WebElement commentsTable = driverUtil.getWebElement(COMMENTS_TABLE);
        if (commentsTable == null)
            throw new AutomationException("Comments section is not being displayed or it might taking too long time to load!");
    }

    public void addNewComment(String text) throws AutomationException {
        WebElement newLogActionPopup = driverUtil.getWebElement(CREATE_NEW_LOG_ACTION_POPUP);
        WebElement addButton;
        String addButtonXpath;
        if (newLogActionPopup != null) {
            addButton = driverUtil.getWebElement(LOG_ACTION_POPUP_ADD_NEW_COMMENT_BUTTON);
            addButtonXpath = LOG_ACTION_POPUP_ADD_NEW_COMMENT_BUTTON;
        } else {
            addButton = driverUtil.getWebElement(STORY_TAB_ADD_NEW_COMMENT_BUTTON);
            addButtonXpath = LOG_ACTION_POPUP_ADD_NEW_COMMENT_BUTTON;
        }
        if (addButton == null)
            throw new AutomationException("Add new comment icon is not being displayed or it might taking too long time to load!");
        WebDriverUtil.waitForElementClickable(By.xpath(addButtonXpath), 10);
        Actions action = new Actions(DriverFactory.drivers.get());
        action.moveToElement(addButton).perform();
        WebDriverUtil.waitForAWhile(2);
        addButton.click();
        WebDriverUtil.waitForAWhile(2);
        WebElement commentNote = driverUtil.getWebElement(FIRST_COMMENT_NOTE, 30);
        if (commentNote == null)
            throw new AutomationException("Comment text area is not being displayed after clicking on add new button!");
        commentNote.sendKeys(text);
        WebDriverUtil.waitForAWhile(3);
    }

    public void verifyComment(String text, boolean isVisible) throws AutomationException {
        WebElement comment = driverUtil.getWebElement(String.format(FIND_COMMENT, text));
        if (isVisible && comment == null)
            throw new AutomationException(String.format("No comment found with the given text: %s but we supposed it should be visible", text));
        if (!isVisible && comment != null)
            throw new AutomationException(String.format("Comment found with the given text: %s but we supposed it should not visible", text));
    }

    public void verifyMedicationRecordIsDisplayed(String text, boolean isVisible) throws AutomationException {
        WebElement record = driverUtil.getWebElement(String.format(PATIENT_MEDICATION_RECORD, text.toUpperCase()));
        if (isVisible && record == null)
            throw new AutomationException(String.format("No medication found with the given text: %s but we supposed it should be visible", text));
        if (!isVisible && record != null)
            throw new AutomationException(String.format("Medication found with the given text: %s but we supposed it should not visible", text));
    }

    public void discontinueMedication(String text) throws AutomationException {
        WebElement recordCheckbox = driverUtil.getWebElement(String.format(PATIENT_MEDICATION_RECORD_CHECKBOX, text.toUpperCase()));
        if (recordCheckbox == null)
            throw new AutomationException(String.format("No medication found with the given text: %s but we supposed it should be visible", text));
        recordCheckbox.click();
        WebElement discontinueButton = driverUtil.findElement(MEDICATION_DISCONTINUE_BUTTON);
        if (discontinueButton == null)
            throw new AutomationException("Unable to locate discontinue medication button!");
        discontinueButton.click();
    }

    public void discontinueAllMedAndActiveSelectiveMed(int medicationCount) throws AutomationException {
        WebElement allMedRecordSelectCheckbox = driverUtil.getWebElement(SELECT_ALL_MED_RECORD_CHECKBOX);
        if (allMedRecordSelectCheckbox == null)
            throw new AutomationException("No select all medication record Checkbox found");
        allMedRecordSelectCheckbox.click();
        WebElement discontinueButton = driverUtil.findElement(MEDICATION_DISCONTINUE_BUTTON);
        if (discontinueButton == null)
            throw new AutomationException("Unable to locate discontinue medication button!");
        discontinueButton.click();
        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Confirm");
        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Ok");

        List<WebElement> discontinuedMedications = driverUtil.getWebElements(PATIENT_DISCONTINUED_MEDICATION_RECORDS);
        if (discontinuedMedications == null)
            throw new AutomationException("Discontinue medication not present it seems all present medications are Active or medications not present in table!");

        if (allMedRecordSelectCheckbox.isSelected()) {
            allMedRecordSelectCheckbox.click();
        }
        int discontinueMedicationCount = discontinuedMedications.size();

        // Select Medication records
        if (discontinueMedicationCount > medicationCount) {
            for (int i = 1; i <= medicationCount; i++) {
                WebElement medicationSelectBox = driverUtil.getWebElement("(//tr[td/*[contains(@class,'MedDetailTable')] and td//*[contains(@class,'discontinued')]]//input[contains(@class,'Checkbox')])[" + i + "]");
                medicationSelectBox.click();
                if (!medicationSelectBox.isSelected())
                    driverUtil.clickUsingJavaScript("(//tr[td/*[contains(@class,'MedDetailTable')] and td//*[contains(@class,'discontinued')]]//input[contains(@class,'Checkbox')])[" + i + "]");
                waitForLoadingPage();
            }
        } else {
            WebElement medicationSelectBox = driverUtil.getWebElement("(//tr[td/*[contains(@class,'MedDetailTable')] and td//*[contains(@class,'discontinued')]]//input[contains(@class,'Checkbox')])[1]");
            medicationSelectBox.click();
            if (!medicationSelectBox.isSelected())
                driverUtil.clickUsingJavaScript("(//tr[td/*[contains(@class,'MedDetailTable')] and td//*[contains(@class,'discontinued')]]//input[contains(@class,'Checkbox')])[1]");
            waitForLoadingPage();
        }
        WebElement activeMedRecordButton = driverUtil.getWebElement(MEDICATION_ACTIVE_BUTTON);
        if (activeMedRecordButton == null)
            throw new AutomationException("Active medication button not present or it seems disable!");
        activeMedRecordButton.click();
        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Confirm");
        waitForLoadingPage();
    }

    public void discontinueAllMed() throws AutomationException {
        WebElement allMedRecordSelectCheckbox = driverUtil.getWebElement(SELECT_ALL_MED_RECORD_CHECKBOX);
        if (allMedRecordSelectCheckbox == null)
            throw new AutomationException("No select all medication record Checkbox found");
        allMedRecordSelectCheckbox.click();
        WebElement discontinueButton = driverUtil.findElement(MEDICATION_DISCONTINUE_BUTTON);
        if (discontinueButton == null)
            throw new AutomationException("Unable to locate discontinue medication button!");
        discontinueButton.click();
        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Confirm");
        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Ok");
    }

    public void selectComment(String text) throws AutomationException {
        WebElement comment = driverUtil.getWebElement(String.format(FIND_COMMENT, text));
        if (comment != null)
            driverUtil.clickUsingJavaScript(String.format(FIND_COMMENT, text));
    }

    public void deleteComment(String text) throws AutomationException {
        selectComment(text);
        WebElement deleteButton = driverUtil.getWebElement(String.format(DELETE_COMMENT_BUTTON, text));
        if (deleteButton == null)
            throw new AutomationException(String.format("Unable to locate delete button for the give comment text: %s", text));
        deleteButton.click();
        clickOnConfirmationDialogDeleteButton();
    }

    public void deleteCommentIfPresent(String text) throws AutomationException {
        selectComment(text);
        WebElement deleteButton = driverUtil.getWebElementAndScroll(String.format(DELETE_COMMENT_BUTTON, text));
        if (deleteButton != null) {
            driverUtil.moveToElementAndClick(deleteButton);
            clickOnConfirmationDialogDeleteButton();
        }
    }

    public void verifyPatientTaskDetailsSection() throws AutomationException {
        WebElement patientStory = driverUtil.getWebElement(PATIENT_TASK_TABLE_HEADER);
        takeScreenshot();
        if (patientStory == null)
            throw new AutomationException("Patient Task details is not being displayed or it might taking too long time to load!");
    }

    public void verifyPatientTimelineDetailsSection() throws AutomationException {
        WebElement patientTimeline = driverUtil.getWebElement(PATIENT_TIMELINE_TABLE_HEADER);
        if (patientTimeline == null)
            throw new AutomationException("Patient Timeline details is not being displayed or it might taking too long time to load!");
    }

    public void clickOnButton(String text) throws AutomationException {
//        if(text.equalsIgnoreCase("Download Selected")){
//            try {
//                FileUtils.cleanDirectory(new File(System.getProperty("user.dir").replace("\\", "/") + File.separator + "downloads"));
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
        driverUtil.waitForAWhile(3);
        driverUtil.waitForLoadingPage();
        WebElement button = driverUtil.getWebElementAndScroll(String.format(PATIENT_BUTTON, text));
        if (button == null)
            throw new AutomationException(String.format("Unable to find %s button on patient tab or it might taking too long time to load!", text));
        driverUtil.waitForElementClickable(By.xpath(String.format(PATIENT_BUTTON, text)));
        try {
            driverUtil.clickUsingJavaScript(String.format(PATIENT_BUTTON, text));
        } catch (Exception e) {
            driverUtil.waitForAWhile(3);
            button.click();
        }
        waitForLoadingPage();
    }

    public void clickOnTabButton(String text) throws AutomationException {
        WebElement button = driverUtil.getWebElement(String.format(PATIENT_TAB_DEFAULT_BUTTON, text));
        if (button == null)
            throw new AutomationException(String.format("Unable to find %s button on patient tab or it might taking too long time to load!", text));
        //driverUtil.waitForElementClickable(By.xpath(String.format(PATIENT_TAB_BUTTON, text)));
        button.click();
    }

    public void verifyCreateNewTaskPopup() throws AutomationException {
        WebElement newTaskPopup = driverUtil.getWebElement(CREATE_NEW_TASK_POPUP);
        if (newTaskPopup == null)
            throw new AutomationException("No Create new task popup is being displayed or it might taking too long time to load!");
    }

    public void verifyCreateNewLogActionPopup() throws AutomationException {
        WebElement newLogActionPopup = driverUtil.getWebElement(CREATE_NEW_LOG_ACTION_POPUP, 60);
        if (newLogActionPopup == null)
            throw new AutomationException("No Create new log action popup is being displayed or it might taking too long time to load!");
    }

    public void createNewTask(DataTable data) throws AutomationException {
        Map<String, String> taskDetails = new HashMap<>();
        List<List<String>> rows = data.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                taskDetails.put(rows.get(0).get(j), row.get(j));
            }
        }

        String value = taskDetails.get(NEW_TASK_ASSIGNED_TO);
        if (value.contains(":")) {
            String[] userDetails = value.split(":");
            String userType = userDetails[0];
            String userName = userDetails[1];
            driverUtil.getWebElement(NEW_TASK_ASSIGNED_TO_USER_DROPDOWN, WebDriverUtil.NO_WAIT, "Unable to find assigned to user option!").click();
            WebDriverUtil.waitForAWhile();
            driverUtil.getWebElementAndScroll(String.format(USER_NAME_OPTION, userName), WebDriverUtil.NO_WAIT,
                    String.format("Unable to find user: %s in assigned to options!", userName)).click();
        } else {
            WebElement input = driverUtil.getWebElement(String.format(NEW_TASK_ASSIGNED_ROLE_RADIO_BUTTON, value));
            if (input == null)
                throw new AutomationException(String.format("No Assigned to option for %s is being displayed or it might taking too long time to load!", value));
            input.click();
        }

        value = taskDetails.get(NEW_TASK_TYPE);
        WebElement input = driverUtil.getWebElement(String.format(NEW_TASK_TYPE_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Type option for %s is being displayed or it might taking too long time to load!", value));
        input.click();

        value = taskDetails.get(NEW_TASK_TASK);
        String taskListHeader = null;
        String taskName = value;
        if (value.contains("+")) {
            taskListHeader = value.substring(0, value.indexOf("-"));
            taskName = value.substring(1, value.length()).trim();
            driverUtil.getWebElement(String.format(CREATE_TASK_PARAM_TASK_NAME_HEADER, taskListHeader)).click();
        }
        input = driverUtil.getWebElement(String.format(NEW_TASK_NAME_RADIO_BUTTON, taskName));
        if (input == null) {
            input = driverUtil.getWebElement("//label[text()='" + taskName + "']//../input[@name='taskName']");
            if (input == null)
                throw new AutomationException(String.format("No Task Name option for %s is being displayed or it might taking too long time to load!", taskName));
        }
        input.click();

        value = taskDetails.get(RELATED_TO);
        if (value != null) {
            input = driverUtil.getWebElement(String.format(RELATED_TO_RADIO_BUTTON, value));
            if (input == null)
                throw new AutomationException(String.format("No Related to option for %s is being displayed or it might taking too long time to load!", value));
            input.click();
        }

        value = taskDetails.get(NEW_TASK_DUE_DATE);
        if ("urgent".equalsIgnoreCase(value)) {
            input = driverUtil.getWebElement(NEW_TASK_URGENT_DUE_DATE_CHECKBOX);
            input.click();
        }

        value = taskDetails.get(NEW_TASK_NOTES);
        if (value != null && value.length() > 0) {
            input = driverUtil.getWebElement(NEW_TASK_NOTE_INPUT);
            input.sendKeys(value);
        }

        WebElement submitButton = driverUtil.getWebElement(NEW_TASK_SUBMIT_BUTTON);
        if (submitButton == null)
            throw new AutomationException("Create Task button is being displayed on Create new task popup or it might taking too long time to load!");
        if (submitButton.isDisplayed())
            submitButton.click();
        else
            throw new AutomationException("Unable to click on Create Task button as it is disabled!");
        driverUtil.waitForInvisibleElement(By.xpath(CREATE_NEW_TASK_POPUP));
    }

    public void editNewlyCreatedTask(DataTable data) throws AutomationException {
        Map<String, String> taskDetails = new HashMap<>();
        List<List<String>> rows = data.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                taskDetails.put(rows.get(0).get(j), row.get(j));
            }
        }

        String value = taskDetails.get(STATUS);
        WebElement input = driverUtil.getWebElement(String.format(EDIT_TASK_STATUS_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Status option for %s is being displayed or it might taking too long time to load!", value));
        input.click();
        WebDriverUtil.waitForAWhile(3);
        value = taskDetails.get(NOTE);
        if (value != null && value.length() > 0) {
            input = driverUtil.getWebElement(NEW_TASK_NOTE_INPUT);
            input.click();
            input.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            WebDriverUtil.waitForAWhile(1);
            input.sendKeys(value);
        }
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_3_SEC, TimeUnit.SECONDS);
        clickOnNameLink();
    }

    public void verifyUpdatedTaskDetails(DataTable data) throws AutomationException {
        Map<String, String> taskDetails = new HashMap<>();
        List<List<String>> rows = data.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                taskDetails.put(rows.get(0).get(j), row.get(j));
            }
        }

        String status = taskDetails.get(STATUS);
        WebElement statusElement = driverUtil.getWebElement(String.format(VERIFY_PATIENT_TASK_DATA, status));
        takeScreenshot();
        if (statusElement == null)
            throw new AutomationException(String.format("Status is not being matched: %s", status));

        String note = taskDetails.get(NOTE);
        WebElement noteElement = driverUtil.getWebElement(String.format(VERIFY_PATIENT_TASK_DATA, note));
        if (noteElement == null)
            throw new AutomationException(String.format("Note is not being matched: %s", note));
    }

    public static void scrollToTop() throws AutomationException {
        driverUtil.getWebElementAndScroll(String.format(HomePage.TABS_XPATH, "Patient"));

    }

    public void verifyNewlyCreatedTask(String taskName) throws AutomationException {
        scrollToTop();
        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PATIENT_TASK_RECORD, taskName));
        takeScreenshot();
        if (taskRecord == null)
            throw new AutomationException(String.format("Unable to find task for Patient: %s", taskName));
    }

    public void verifyNewlyCreatedTaskDeleted(String taskName) throws AutomationException {
        scrollToTop();
        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PATIENT_TASK_RECORD, taskName));
        takeScreenshot();
        if (taskRecord != null)
            throw new AutomationException(String.format("We supposed task should be deleted: %s", taskName));
    }

    public void clickOnNewlyCreatedTask(String taskName) throws AutomationException {
        scrollToTop();
        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PATIENT_TASK_RECORD, taskName));
        if (taskRecord == null)
            throw new AutomationException(String.format("Unable to find newly created task for Patient: %s", taskName));
        taskRecord.click();
    }

    public void deleteNewlyCreatedTask(String taskName) throws AutomationException {
        scrollToTop();
        while (true) {
            WebElement deleteTaskButton = driverUtil.getWebElement(String.format(PATIENT_TASK_RECORD_DELETE, taskName));
            if (deleteTaskButton == null)
                break;
            deleteTaskButton.click();
            driverUtil.waitForLoadingPage();
            WebElement deleteConfirmationButton = driverUtil.getWebElement(DELETE_TASK_CONFIRMATION_POPUP_DELETE_BUTTON);
            if (deleteConfirmationButton == null)
                throw new AutomationException("Unable to find delete task button on confirmation popup or it might taking too long time to load!");
            deleteConfirmationButton.click();
            WebDriverUtil.waitForAWhile();
            takeScreenshot();
        }
    }

    public void deleteTaskIfPresent(String taskName) throws AutomationException {
        scrollToTop();
        WebElement deleteTaskButton = driverUtil.getWebElement(String.format(PATIENT_TASK_RECORD_DELETE, taskName));
        if (deleteTaskButton != null) {
            deleteTaskButton.click();
            WebElement deleteConfirmationButton = driverUtil.getWebElement(DELETE_TASK_CONFIRMATION_POPUP_DELETE_BUTTON);
            if (deleteConfirmationButton == null)
                throw new AutomationException("Unable to find delete task button on confirmation popup or it might taking too long time to load!");
            deleteConfirmationButton.click();
            WebDriverUtil.waitForAWhile();
            takeScreenshot();
        }
    }

    public void verifyTaskDetails() throws AutomationException {
        WebElement container = driverUtil.getWebElementAndScroll(TASK_DETAILS_EDIT_CONTAINER);
        if (container == null)
            throw new AutomationException("Task details is not being displayed!");
    }

    public void clickOnTaskAction(String action) throws AutomationException {
        WebElement actionButton = driverUtil.getWebElementAndScroll(String.format(TASK_ACTION_BUTTON, action));
        if (actionButton == null)
            throw new AutomationException(String.format("%s button is not being displayed!", action));
        actionButton.click();
    }

    public void verifyLogNewActionPopup() throws AutomationException {
        WebElement popup = driverUtil.getWebElement(TASK_LOG_NEW_ACTION_POPUP);
        takeScreenshot();
        if (popup == null)
            throw new AutomationException("No Log new Action popup is being displayed!");
    }

    public void closeLogNewActionPopup() throws AutomationException {
        WebElement closeButton = driverUtil.getWebElement(CLOSE_POPUP_BUTTON);
        if (closeButton == null)
            throw new AutomationException("No close button is being displayed on popup!");
        closeButton.click();
    }

    public void createNewLogAction(DataTable data) throws AutomationException {
        driverUtil.waitForLoadingPage();
        Map<String, String> taskDetails = new HashMap<>();
        List<List<String>> rows = data.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                taskDetails.put(rows.get(0).get(j), row.get(j));
            }
        }

        String value = taskDetails.get(NEW_FOR_TASKS);
        if (value != null && !value.isEmpty()) {
            WebElement input = driverUtil.getWebElement(String.format(NEW_FOR_TASK_RADIO_BUTTON, value));
            if (input == null)
                throw new AutomationException(String.format("No For Task(s) for %s is being displayed or it might taking too long time to load!", value));
            input.click();
        }

        value = taskDetails.get(NEW_LOG_ACTION);
        WebElement input = driverUtil.getWebElement(String.format(NEW_LOG_ACTION_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Action option for %s is being displayed or it might taking too long time to load!", value));
        input.click();

        value = taskDetails.get(NEW_LOG_ACTION_TYPE);
        input = driverUtil.getWebElement(String.format(NEW_LOG_ACTION_TYPE_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Type option for %s is being displayed or it might taking too long time to load!", value));
        input.click();

        value = taskDetails.get(NEW_LOG_ACTION_STAKEHOLDER);
        input = driverUtil.getWebElement(String.format(NEW_LOG_ACTION_STAKEHOLDER_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Stakeholder option for %s is being displayed or it might taking too long time to load!", value));
        input.click();

        value = taskDetails.get(NEW_LOG_ACTION_OUTCOME);
        input = driverUtil.getWebElement(String.format(NEW_LOG_ACTION_OUTCOME_RADIO_BUTTON, value));
        if (input == null)
            throw new AutomationException(String.format("No Outcome option for %s is being displayed or it might taking too long time to load!", value));
        input.click();

        value = taskDetails.get(NEW_LOG_ACTION_STEP_PERFORMED);
        String taskListHeader = null;
        String taskName = value;
        if (value.contains("+")) {
            char ch = '+';
            int cnt = 0;
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == ch)
                    cnt++;
            }
            if (cnt >= 2) {
                taskListHeader = value.substring(0, value.indexOf("+", 2)).trim();
                driverUtil.getWebElement(String.format(NEW_LOG_ACTION_STEP_PERFORMED_NAME_HEADER, taskListHeader)).click();
                taskListHeader = value.substring(value.indexOf("+", 2), value.indexOf("-")).trim();
                taskName = value.substring(value.indexOf("-"), value.length()).trim();
                driverUtil.getWebElement(String.format(NEW_LOG_ACTION_STEP_PERFORMED_NAME_HEADER, taskListHeader)).click();
            }
            if (cnt < 2) {
                taskListHeader = value.substring(0, value.indexOf("-"));
                taskName = value.substring(1, value.length()).trim();
                driverUtil.getWebElement(String.format(NEW_LOG_ACTION_STEP_PERFORMED_NAME_HEADER, taskListHeader)).click();
            }
        }
        input = driverUtil.getWebElementAndScroll(String.format(NEW_LOG_ACTION_STEP_PERFORMED_CHECKBOX, taskName));
        if (input == null)
            throw new AutomationException(String.format("No Step performed option for %s is being displayed or it might taking too long time to load!", value));
        Actions action = new Actions(DriverFactory.drivers.get());
        action.moveToElement(input).perform();
        waitForAWhile(WAIT_3_SECOND);
        input.click();

        value = taskDetails.get(NEW_LOG_ACTION_COMMENT);
        if (value != null && !value.isEmpty()) {
            addNewComment(value);
        }

        value = taskDetails.get(NEW_LOG_ACTION_NOTE);
        if (value != null && !value.isEmpty()) {
            input = driverUtil.getWebElement(NEW_TASK_NOTE_INPUT);
            input.sendKeys(value);
        }

        WebElement submitButton = driverUtil.getWebElement(NEW_LOG_ACTION_SUBMIT_BUTTON);
        if (submitButton == null) {
            submitButton = driverUtil.getWebElement(NEW_LOG_ACTION_SUBMIT_BUTTON2);
            if (submitButton == null)
                throw new AutomationException("Log Action button is being displayed on Create new task popup or it might taking too long time to load!");
            submitButton.click();
        } else {
            if (submitButton.isDisplayed())
                submitButton.click();
            else
                throw new AutomationException("Unable to click on Log Action button as it is disabled!");
            driverUtil.waitForInvisibleElement(By.xpath(CREATE_NEW_LOG_ACTION_POPUP));
        }
    }

    public void createNewLogActionInCampaignsTab(DataTable data) throws AutomationException {
        driverUtil.waitForLoadingPage();
        Map<String, String> taskDetails = new HashMap<>();
        List<List<String>> rows = data.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                taskDetails.put(rows.get(0).get(j), row.get(j));
            }
        }


        String value = taskDetails.get(DATE_PERFORMED);
        if (value != null && !value.isEmpty()) {

        }

        value = taskDetails.get(NEW_LOG_ACTION);
        WebElement input = driverUtil.getWebElement(String.format(DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB, "Action"));
        if (input == null)
            throw new AutomationException(String.format("No Action option for %s is being displayed or it might taking too long time to load!", value));
        input.click();
        WebElement optionValue = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP, value));
        if (optionValue == null)
            throw new AutomationException(String.format("No Action option is present in Action Dropdown", value));
        optionValue.click();

        value = taskDetails.get(NEW_LOG_ACTION_TYPE);
        input = driverUtil.getWebElement(String.format(DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB, "Type"));
        if (input == null)
            throw new AutomationException(String.format("No Type option for %s is being displayed or it might taking too long time to load!", value));
        input.click();
        optionValue = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP, value));
        if (optionValue == null)
            throw new AutomationException(String.format("No Type option is present in Type Dropdown", value));
        optionValue.click();


        value = taskDetails.get(NEW_LOG_ACTION_STAKEHOLDER);
        input = driverUtil.getWebElement(String.format(DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB, "Stakeholder"));
        if (input == null)
            throw new AutomationException(String.format("No Stakeholder option for %s is being displayed or it might taking too long time to load!", value));
        input.click();
        optionValue = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP, value));
        if (optionValue == null)
            throw new AutomationException(String.format("No Stakeholder option is present in Stakeholder Dropdown", value));
        optionValue.click();

        value = taskDetails.get(NEW_LOG_ACTION_OUTCOME);
        input = driverUtil.getWebElement(String.format(DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB, "Outcome"));
        if (input == null)
            throw new AutomationException(String.format("No Outcome option for %s is being displayed or it might taking too long time to load!", value));
        input.click();
        optionValue = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP, value));
        if (optionValue == null)
            throw new AutomationException(String.format("No Outcome option is present in Outcome Dropdown", value));
        optionValue.click();

        value = taskDetails.get(NEW_LOG_ACTION_STEP_PERFORMED);
        if (value != null && !value.isEmpty()) {
            WebElement defaultStepsPerformedValue = driverUtil.findElement("//*[contains(@class,'defaultValueRemove')]");
            if (defaultStepsPerformedValue != null)
                defaultStepsPerformedValue.click();
            waitForLoadingPage();
            input = driverUtil.getWebElement(String.format(DROP_DOWN_IN_LOG_ACTION_POPUP_IN_CAMPAIGNS_TAB, "Step(s) Performed"));
            if (input == null)
                throw new AutomationException(String.format("No Step(s) Performed option for %s is being displayed or it might taking too long time to load!", value));
            input.click();
            optionValue = driverUtil.getWebElement(String.format(DROPDOWN_OPTION_VALUE_IN_LOG_ACTION_POPUP, value));
            if (optionValue == null)
                throw new AutomationException(String.format("No Step(s) Performed option is present in Step(s) Performed Dropdown", value));
            optionValue.click();
        }

        value = taskDetails.get(NEW_LOG_ACTION_COMMENT);
        if (value != null && !value.isEmpty()) {
            addNewComment(value);
        }

        WebElement submitButton = driverUtil.getWebElement(NEW_LOG_ACTION_SUBMIT_BUTTON_IN_CAMPAIGNS_TAB);
        if (submitButton == null) {
            submitButton = driverUtil.getWebElement(NEW_LOG_ACTION_SUBMIT_BUTTON_IN_CAMPAIGNS_TAB);
            if (submitButton == null)
                throw new AutomationException("Log Action button is being displayed on Create new task popup or it might taking too long time to load!");
            submitButton.click();
        } else {
            if (submitButton.isDisplayed())
                submitButton.click();
            else
                throw new AutomationException("Unable to click on Log Action button as it is disabled!");
            driverUtil.waitForInvisibleElement(By.xpath(CREATE_NEW_LOG_ACTION_POPUP));
        }
    }


    public void verifyNewlyCreatedLogAction(String identifier) throws AutomationException {
        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PATIENT_STORY_FIRST_RECORD, identifier));
        if (taskRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created log action on first index & unable to find newly created task for Patient: %s", identifier));
    }

    public void verifyNewlyCreatedLogActionInSecondRow(String identifier) throws AutomationException {
        WebElement taskRecord = driverUtil.getWebElement(String.format(VERIFY_PATIENT_STORY_SECOND_RECORD, identifier));
        if (taskRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created log action on first index & unable to find newly created task for Patient: %s", identifier));
    }

    public void deleteNewlyCreatedLogAction() throws AutomationException {
        WebElement deleteStoryButton = driverUtil.getWebElementAndScroll(PATIENT_STORY_FIRST_RECORD_DELETE);
        if (deleteStoryButton == null)
            throw new AutomationException("Unable to find delete story button or it might taking too long time to load!");
        deleteStoryButton.click();
        WebElement deleteConfirmationButton = driverUtil.getWebElement(DELETE_LOG_ACTION_CONFIRMATION_POPUP_DELETE_BUTTON);
        if (deleteConfirmationButton == null)
            throw new AutomationException("Unable to find delete log action button on confirmation popup or it might taking too long time to load!");
        deleteConfirmationButton.click();
    }

    public void selectReportTypes(String... types) throws AutomationException {
        for (String type : types) {
            WebElement element = driverUtil.getWebElement(String.format(REPORT_TYPE_CHECKBOX, type, type));
            if (element == null)
                throw new AutomationException(String.format("Unable to find report type:%s", type));
            driverUtil.moveToElementAndClick(element);
            WebElement button = driverUtil.getWebElement(String.format(PATIENT_BUTTON, "Generate Selected Reports"));
            if (button == null)
                driverUtil.moveToElementAndClick(element);
        }
    }

    public void selectFirstPractitionerToSendReport() throws AutomationException {
        WebElement reportFirstDrpPractitionerCheckbox = driverUtil.getWebElement(REPORT_FIRST_DRP_PRACTITIONER_CHECKBOX);
        if (reportFirstDrpPractitionerCheckbox != null && !reportFirstDrpPractitionerCheckbox.getAttribute("innerHTML").contains("checked"))
            reportFirstDrpPractitionerCheckbox.findElement(By.xpath("span")).click();

    }

    public void clickOnSetCallDateButton() throws AutomationException {
        WebDriverUtil.waitForAWhile(3);
        WebElement button = driverUtil.getWebElement(SELECT_CALL_DATE_BUTTON);
        if (button == null)
            throw new AutomationException("Unable to find Set Call Date button or it might taking too long time to load!");
        button.click();
        verifyInfoPopupAndClose();
        button = driverUtil.getWebElement(SELECT_CALL_DATE_BUTTON);
        if (!button.getText().contains(":")) {
            button.click();
            verifyInfoPopupAndClose();
        }

    }

    public void userEnterConversationDate(String date) throws AutomationException {
        WebElement conversationDateInput = driverUtil.findElement(HardStopPage.CONVERSATION_DATE_INPUT);
        if (conversationDateInput == null)
            throw new AutomationException("Date picker to set call date not displayed");
        conversationDateInput.click();
        conversationDateInput.clear();
        conversationDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        conversationDateInput.sendKeys(date);
    }

    public void clickOnSetCallDateButtonAndVerifyMessage(String message) throws AutomationException {
        WebDriverUtil.waitForAWhile(3);
        WebElement button = driverUtil.getWebElement(SELECT_CALL_DATE_BUTTON);
        if (button == null)
            throw new AutomationException("Unable to find Set Call Date button or it might taking too long time to load!");
        button.click();
        WebElement msg = driverUtil.findElementByText(message);
        if (msg == null)
            throw new AutomationException("We expected message '" + message + " to be displayed");
        verifyInfoPopupAndClose();
    }

    public void selectReportDRP(String drpName) throws AutomationException {
        try {
            selectAllCardiologist();
            deselectAllCardiologist();
            WebElement checkbox = driverUtil.getWebElementAndScroll(String.format(REPORT_DRP_RECORD_CHECKBOX, drpName));
            if (checkbox == null)
                throw new AutomationException(String.format("Unable to find DRP: %s or it might taking too long time to load!", drpName));
            WebDriverUtil.waitForAWhile();
            checkbox.click();
            WebDriverUtil.waitForAWhile();
            takeScreenshot();
        } catch (StaleElementReferenceException ex) {
            WebElement checkbox = driverUtil.getWebElementAndScroll(String.format(REPORT_DRP_RECORD_CHECKBOX, drpName));
            if (checkbox == null)
                throw new AutomationException(String.format("Unable to find DRP: %s or it might taking too long time to load!", drpName));
            WebDriverUtil.waitForAWhile();
            if (!checkbox.isSelected())
                checkbox.click();
        }
    }

    public void deleteReportDRP(String drpName) throws AutomationException {
        closeReportPopup();
        clickOnButton(MAKE_REPORT_BUTTON);
        scrollToTop();
        List<WebElement> records = driverUtil.getWebElements(String.format(REPORT_DRP_RECORD_DELETE, drpName));
        for (WebElement element : records) {
            WebElement deleteButton = driverUtil.getWebElementAndScroll(String.format(REPORT_DRP_RECORD_DELETE, drpName));
            if (deleteButton != null) {
                deleteButton.click();
                WebElement deleteConfirmationButton = driverUtil.getWebElement(String.format(DELETE_CONFIRMATION_POPUP_DELETE_BUTTON, "Delete this recommendation"));
                if (deleteConfirmationButton == null)
                    throw new AutomationException("Unable to find delete med button on confirmation popup or it might taking too long time to load!");
                deleteConfirmationButton.click();
            }
        }
    }

    public void verifyGeneratedReport(String patientName) throws AutomationException {
        WebElement reportRecord = driverUtil.getWebElement(String.format(PATIENT_REPORT_RECORD, patientName));
        if (reportRecord == null)
            throw new AutomationException(String.format("Unable to find generated report for: %s", patientName));
    }

    public void clickOnViewReportLink(String name) throws AutomationException {
        WebElement viewReportLink = driverUtil.getWebElement(String.format(PATIENT_REPORT_VIEW_LINK, name));
        if (viewReportLink == null)
            throw new AutomationException("Unable to find generated report: " + name);
        viewReportLink.click();
        waitForLoadingReport();
    }

    public void clickOnViewReportLink(String role, String name) throws AutomationException {
        WebElement viewReportLink = driverUtil.getWebElement(String.format(PATIENT_REPORT_WITH_ROLE_VIEW_LINK, role, name));
        if (viewReportLink == null)
            throw new AutomationException("Unable to find generated report: '" + name + "' with role: '" + role + "'");
        viewReportLink.click();
        waitForLoadingReport();
    }

    public void verifyReportViewer() throws AutomationException {
        WebElement reportPDFViewerPopup = driverUtil.getWebElement(REPORT_PDF_VIEWER);
        if (reportPDFViewerPopup == null)
            throw new AutomationException("No Report PDF viewer is being displayed!");
        WebDriverUtil.waitForAWhile(10);
    }

    public void verifyReportContains(String text) throws AutomationException {
        text = text.replace("\r\n", " ");
        text = text.replace("\n", " ");
        text = text.replace("\r", " ");
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        List<String> pages = PDFBoxReadText.readTextFromPdfFile(pdfContents);
        StringBuilder pdfText = new StringBuilder(0);
        boolean found = false;
        CommonSteps.REPORT_LOGGER.log("Report data:");
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        for (String page : pages) {
            page = page.replaceAll("\r\n", " ");
            page = page.replaceAll("\n", " ");
            page = page.replaceAll("\r", " ");
            CommonSteps.REPORT_LOGGER.log(page);
            pdfText.append(page);
            if (page.contains(text)) {
                found = true;
                break;
            }
        }
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        CommonSteps.CURRENT_STEP_MESSAGE.set(pdfText.toString());
        if (!found)
            throw new AutomationException(String.format("Unable to find given text: %s in the report PDF file!", text));
    }

    public void verifyReportNotContains(String text) throws AutomationException {
        text = text.replace("\r\n", " ");
        text = text.replace("\n", " ");
        text = text.replace("\r", " ");
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        List<String> pages = PDFBoxReadText.readTextFromPdfFile(pdfContents);
        StringBuilder pdfText = new StringBuilder(0);
        boolean found = false;
        CommonSteps.REPORT_LOGGER.log("Report data:");
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        for (String page : pages) {
            page = page.replaceAll("\r\n", " ");
            page = page.replaceAll("\n", " ");
            page = page.replaceAll("\r", " ");
            CommonSteps.REPORT_LOGGER.log(page);
            pdfText.append(page);
            if (page.contains(text)) {
                found = true;
                break;
            }
        }
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        CommonSteps.CURRENT_STEP_MESSAGE.set(pdfText.toString());
        if (found)
            throw new AutomationException(String.format("Find given text: %s in the report PDF file, but we don't Expect it!", text));
    }

    public void verifyReportAllUpdatedMedicationSIGText() throws AutomationException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        Map<String, String> allReportMedications = PDFBoxReadText.readPMLReportAllMedications(pdfContents);
        CommonSteps.REPORT_LOGGER.log(allReportMedications.toString());
        Map<String, SigTranslation> updatedPatientMedicationSigMapping = MedListPage.updatedMedicationSigTextMapping.get();
        CommonSteps.REPORT_LOGGER.log(updatedPatientMedicationSigMapping.toString());
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        if (updatedPatientMedicationSigMapping == null || updatedPatientMedicationSigMapping.keySet().isEmpty()) {
            throw new AutomationException("No updated medication record mapping found!");
        }

        StringBuilder errorMessage = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Set<String> patientReportMedicationSigText = allReportMedications.keySet();
        for (String medication : patientReportMedicationSigText) {
            SigTranslation translation = updatedPatientMedicationSigMapping.get(medication.toUpperCase().trim());
            if (translation != null) {
                String expectedNote = translation.getSigEnglishTextForNonMTM();
                String actualReportNote = allReportMedications.get(medication);
                message.append(String.format("Medication: <b>'%s'</b> <br>Expected: <b>%s</b> <br>Text in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                if (!expectedNote.trim().contains(actualReportNote.trim())) {
                    errorMessage.append(String.format("Medication: <b>'%s'</b>  SIG text is not being matched!<br> Expected: %s and found in report: %s", medication, expectedNote, actualReportNote));
                    errorMessage.append(System.getProperty("line.separator"));
                }
            }
        }
        if (errorMessage.length() > 0)
            throw new AutomationException(errorMessage.toString());
        CommonSteps.CURRENT_STEP_MESSAGE.set(message.toString());
    }

    public void verifyReportMedicationSigNotesForMTM() throws AutomationException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        Map<String, String> allReportMedications = PDFBoxReadText.readMtmPatientPMLReportAllMedications(pdfContents);
        CommonSteps.REPORT_LOGGER.log(allReportMedications.toString());
        Map<String, SigTranslation> updatedPatientMedicationSigMapping = MedListPage.updatedMedicationSigTextMapping.get();
        CommonSteps.REPORT_LOGGER.log(updatedPatientMedicationSigMapping.toString());
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        if (updatedPatientMedicationSigMapping == null || updatedPatientMedicationSigMapping.keySet().isEmpty()) {
            throw new AutomationException("No updated medication record mapping found!");
        }

        StringBuilder errorMessage = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Set<String> patientReportMedicationSigText = allReportMedications.keySet();
        for (String medication : patientReportMedicationSigText) {
            SigTranslation translation = updatedPatientMedicationSigMapping.get(medication.toUpperCase().trim());
            if (translation != null) {
                String expectedNote = translation.getSigEnglishTextForMTM();
                String actualReportNote = allReportMedications.get(medication);
                message.append(String.format("Medication: <b>'%s'</b> <br>Expected: <b>%s</b> <br>Text in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                if (!expectedNote.trim().contains(actualReportNote.trim())) {
                    errorMessage.append(String.format("Medication: <b>'%s'</b>  SIG text is not being matched!<br> Expected: %s and found in report: %s", medication, expectedNote, actualReportNote));
                    errorMessage.append(System.getProperty("line.separator"));
                }
            }
        }
        if (errorMessage.length() > 0)
            throw new AutomationException(errorMessage.toString());
        CommonSteps.CURRENT_STEP_MESSAGE.set(message.toString());
    }

    public void verifyReportMedicationSpanishSigNotes() throws AutomationException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        Map<String, String> allReportMedications = PDFBoxReadText.readPMLReportAllMedications(pdfContents);
        CommonSteps.REPORT_LOGGER.log(allReportMedications.toString());
        Map<String, SigTranslation> updatedPatientMedicationSigMapping = MedListPage.updatedMedicationSigTextMapping.get();
        CommonSteps.REPORT_LOGGER.log(updatedPatientMedicationSigMapping.toString());
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        if (updatedPatientMedicationSigMapping == null || updatedPatientMedicationSigMapping.keySet().isEmpty()) {
            throw new AutomationException("No updated medication record mapping found!");
        }

        StringBuilder errorMessage = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Set<String> patientReportMedicationSigText = allReportMedications.keySet();
        for (String medication : patientReportMedicationSigText) {
            SigTranslation translation = updatedPatientMedicationSigMapping.get(medication.toUpperCase().trim());
            if (translation != null) {
                String expectedNote = translation.getSigSpanishTextForNonMTM();
                String actualReportNote = allReportMedications.get(medication);
                actualReportNote = CommonUtil.getUTF8String(actualReportNote);
                message.append(String.format("Medication: <b>'%s'</b> <br>Expected: <b>%s</b> <br>Text in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                if (!expectedNote.trim().contains(actualReportNote.trim())) {
                    errorMessage.append(String.format("Medication: <b>'%s'</b>  SIG text is not being matched!<br> Expected: <b>%s</b> and found in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                }
            }
        }
        if (errorMessage.length() > 0)
            throw new AutomationException(errorMessage.toString());
        CommonSteps.CURRENT_STEP_MESSAGE.set(message.toString());
    }

    public void verifyReportMedicationSpanishSigNotesForMTM() throws AutomationException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        Map<String, String> allReportMedications = PDFBoxReadText.readMtmPatientPMLReportAllMedications(pdfContents);
        CommonSteps.REPORT_LOGGER.log(allReportMedications.toString());
        Map<String, SigTranslation> updatedPatientMedicationSigMapping = MedListPage.updatedMedicationSigTextMapping.get();
        CommonSteps.REPORT_LOGGER.log(updatedPatientMedicationSigMapping.toString());
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        if (updatedPatientMedicationSigMapping == null || updatedPatientMedicationSigMapping.keySet().isEmpty()) {
            throw new AutomationException("No updated medication record mapping found!");
        }

        StringBuilder errorMessage = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Set<String> patientReportMedicationSigText = allReportMedications.keySet();
        for (String medication : patientReportMedicationSigText) {
            SigTranslation translation = updatedPatientMedicationSigMapping.get(medication.toUpperCase().trim());
            if (translation != null) {
                String expectedNote = translation.getSigSpanishTextForMTM();
                String actualReportNote = allReportMedications.get(medication);
                actualReportNote = CommonUtil.getUTF8String(actualReportNote);
                message.append(String.format("<b>Medication: '%s'</b> <br>Expected: <b>%s</b> <br>Text in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                if (!expectedNote.trim().contains(actualReportNote.trim())) {
                    errorMessage.append(String.format("Medication: <b>'%s'</b>  SIG text is not being matched!<br> Expected: <b>%s</b> and found in report: <b>%s</b>,", medication, expectedNote, actualReportNote));
                }
            }
        }
        if (errorMessage.length() > 0)
            throw new AutomationException(errorMessage.toString());
        CommonSteps.CURRENT_STEP_MESSAGE.set(message.toString());
    }

    public void verifyReportLogo(String organization) throws AutomationException, IOException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        PDFBoxReadText.verifyReportLogo(organization, pdfContents);
    }

    public void performLogNewAction() throws AutomationException {
        WebElement logAction = driverUtil.getWebElement(LOG_NEW_ACTION_POPUP_LOG_BUTTON);
        logAction.click();
        driverUtil.waitForInvisibleElement(By.xpath(TASK_LOG_NEW_ACTION_POPUP));
        WebDriverUtil.waitForAWhile(5);
    }

    public void clickOnAddNewLabIcon() throws AutomationException {
        WebElement newLabIcon = driverUtil.getWebElement(ADD_NEW_LAB_ICON);
        if (newLabIcon == null)
            throw new AutomationException("Unable to find Add new lab icon or it might taking too long time to load!");
        newLabIcon.click();
    }

    public void verifyCreateNewLabPopup() throws AutomationException {
        WebElement newLabPopup = driverUtil.getWebElement(CREATE_NEW_LAB_POPUP);
        if (newLabPopup == null)
            throw new AutomationException("No Create new lab popup is being displayed or it might taking too long time to load!");
    }

    public void createNewLab(String name, String value) throws AutomationException {
        //driverUtil.getWebElement(SEARCH_LAB_NAME).click();
        driverUtil.getWebElement(SEARCH_LAB_NAME).sendKeys(name);
        driverUtil.getWebElement(LAB_VALUE_INPUT).sendKeys(value);
        driverUtil.getWebElement(LAB_DATE_INPUT).click();
        driverUtil.getWebElement(LAB_DATE_CURRENT_DATE).click();
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        driverUtil.getWebElement(String.format(PATIENT_BUTTON, "Add")).click();
    }

    public void verifyNewlyCreatedLab(String labName) throws AutomationException {
        WebElement labRecord = driverUtil.getWebElement(String.format(LAB_RECORD, labName));
        takeScreenshot();
        if (labRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created lab on labs list nut unable to find newly created task for Patient: %s", labName));
    }

    public void verifyLabTableHasColumn(String columnName) throws AutomationException {
        WebElement element = driverUtil.getWebElement(String.format(LAB_RECORD_COLUMN_HEADER, columnName), 10);
        if (element == null)
            throw new AutomationException("Column header not present in lab table : " + columnName);
    }

    public void deleteNewlyCreatedLab(String labName) throws AutomationException {
        driverUtil.getWebElement(String.format(LAB_RECORD, labName)).click();
        driverUtil.getWebElement(String.format(LAB_RECORD_DELETE_BUTTON, labName)).click();
        while (true) {
            WebElement choice = driverUtil.getWebElement(String.format(LAB_RECORD_DELETE_CHOICES, labName));
            if (choice == null)
                break;
            choice.click();
        }
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        takeScreenshot();
    }

    public void closeReportPopup() throws AutomationException {
        WebElement element = driverUtil.getWebElement(CLOSE_PDF_VIEWER_BUTTON, WebDriverUtil.NO_WAIT);
        if (element != null) {
            element.click();
        }
    }

    public void updateCommentLockStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentLockButton = driverUtil.getWebElement(String.format(LOCKED_COMMENT_BUTTON, text));
        if (commentLockButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentLockButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            commentLockButton.click();
        if (!status && classAttribute.contains("iconsActive"))
            commentLockButton.click();
    }

    public void verifyCommentLockedStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentLockButton = driverUtil.getWebElement(String.format(LOCKED_COMMENT_BUTTON, text));
        if (commentLockButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentLockButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is not locked!", text));
        if (!status && classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is locked!", text));

    }

    public void updateCommentPinStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentPinButton = driverUtil.getWebElement(String.format(PINNED_COMMENT_BUTTON, text));
        takeScreenshot();
        if (commentPinButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentPinButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            commentPinButton.click();
        if (!status && classAttribute.contains("iconsActive"))
            commentPinButton.click();

    }

    public void verifyCommentPinnedStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentPinButton = driverUtil.getWebElement(String.format(PINNED_COMMENT_BUTTON, text));
        takeScreenshot();
        if (commentPinButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentPinButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is not pinned!", text));
        if (!status && classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is pinned!", text));

    }

    public void updateCommentHighlightStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentHighlightButton = driverUtil.getWebElement(String.format(HIGHLIGHT_COMMENT_BUTTON, text));
        takeScreenshot();
        if (commentHighlightButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentHighlightButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            commentHighlightButton.click();
        if (!status && classAttribute.contains("iconsActive"))
            commentHighlightButton.click();

    }

    public void verifyCommentHighlightStatus(String text, boolean status) throws AutomationException {
        selectComment(text);
        WebElement commentHighlightButton = driverUtil.getWebElement(String.format(HIGHLIGHT_COMMENT_BUTTON, text));
        takeScreenshot();
        if (commentHighlightButton == null)
            throw new AutomationException(String.format("No comment found with the given text: %s", text));
        String classAttribute = commentHighlightButton.getAttribute("class");
        if (status && !classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is not highlighted!", text));
        if (!status && classAttribute.contains("iconsActive"))
            throw new AutomationException(String.format("Found [Comment: %s] is highlighted!", text));
    }

    public void performShowHideOperation(boolean enable) throws AutomationException {
        WebElement showHideToggleButton = driverUtil.getWebElement(SHOW_HIDE_COMMENTS_TOGGLE_BUTTON);
        if (showHideToggleButton == null)
            throw new AutomationException("No Show/Hide toggle button is visible in comments section!");
        String styleAttribute = showHideToggleButton.getAttribute("style");
        if (enable && !styleAttribute.contains("background"))
            showHideToggleButton.click();
        if (!enable && styleAttribute.contains("background"))
            showHideToggleButton.click();
    }

    public void verifyPatientMedicalDiagnosis() throws AutomationException {
        WebElement medicalDiagnosis = driverUtil.getWebElement(String.format(PATIENT_MEDICAL_DIAGNOSIS_TABLE, "Medical Diagnosis"));
        if (medicalDiagnosis == null)
            throw new AutomationException("Unable to find patient medical diagnosis data table!");
    }

    public void verifyPatientMedicalDiagnosisTableColumn(String columnName) throws AutomationException {
        WebElement medicalDiagnosis = driverUtil.getWebElement(String.format(PATIENT_MEDICAL_DIAGNOSIS_TABLE, columnName));
        if (medicalDiagnosis == null)
            throw new AutomationException("Unable to find patient medical diagnosis data table column with name:" + columnName);
    }

    public void verifyAllDiagnosisRecordsOrder() throws AutomationException {
        List<String> actualDiagnosisOrder = new ArrayList<>();
        List<Diagnosis> records = new ArrayList<>();
        List<WebElement> diagnosisRecords = driverUtil.getWebElements(PATIENT_DIAGNOSIS_RECORDS);
        for (WebElement record : diagnosisRecords) {
            List<WebElement> information = record.findElements(By.xpath("td"));
            if (!information.isEmpty()) {
                String medicalDiagnosis = information.get(0).getText();
                String dxDate = information.get(1).getText();
                String ip = information.get(2).getAttribute("innerHTML");
                String ed = information.get(3).getAttribute("innerHTML");
                if (medicalDiagnosis == null || medicalDiagnosis.trim().isEmpty())
                    break;
                Diagnosis diagnosis = new Diagnosis();
                actualDiagnosisOrder.add(medicalDiagnosis);
                diagnosis.title = medicalDiagnosis;
                Set<String> allDates = new HashSet<>();
                allDates.add(dxDate);
                diagnosis.dxDate = dxDate;
                diagnosis.ip = ip.isEmpty() ? 0 : Integer.parseInt(ip.substring(0, ip.indexOf("<span")));
                List<String> ip_dates = getAllDatesValues(ip);
                allDates.addAll(ip_dates);
                diagnosis.ipDates = ip_dates;
                diagnosis.ed = ed.isEmpty() ? 0 : Integer.parseInt(ed.substring(0, ed.indexOf("<span")));
                List<String> ed_dates = getAllDatesValues(ed);
                allDates.addAll(ed_dates);
                diagnosis.edDates = ed_dates;
                try {
                    diagnosis.latest = getLatestDate(allDates);
                } catch (Exception ex) {
                    //DO nothing...
                }
                records.add(diagnosis);
            }
        }
        if (!verifyDiagnosisOrder(actualDiagnosisOrder, records))
            throw new AutomationException("Patient Medical Diagnosis records are not in sorted order!");


    }

    public void clickOnAddNewPractitionerIcon() throws AutomationException {
        WebElement createIcon = driverUtil.getWebElement(PATIENT_CARE_TEAM_ADD_PRACTITIONER);
        if (createIcon == null)
            throw new AutomationException("Unable to find add new practitioner icon!");
        createIcon.click();
    }

    public void clickOnAddNewPharmacyIcon() throws AutomationException {
        WebElement createIcon = driverUtil.getWebElement(PATIENT_CARE_TEAM_ADD_PHARMACY);
        if (createIcon == null)
            throw new AutomationException("Unable to find add new pharmacy icon!");
        createIcon.click();
    }

    public void verifyAddNewPractitionerPopup(String text) throws AutomationException {
        WebElement popup = driverUtil.getWebElement(String.format(ADD_NEW_PRACTITIONER_POPUP, text));
        if (popup == null)
            throw new AutomationException("No popup is being displayed with the title: " + text);
    }

    public void verifyAddNewPharmacyPopup(String text) throws AutomationException {
        WebElement popup = driverUtil.getWebElement(String.format(ADD_NEW_PHARMACY_POPUP, text));
        if (popup == null)
            throw new AutomationException("No popup is being displayed with the title: " + text);
    }

    public void waitToClosePractitionerPopup(String text) {
        WebDriverUtil.waitForInvisibleElement(By.xpath(String.format(ADD_NEW_PRACTITIONER_POPUP, text)));
    }

    public void searchPractitionerByFirstName(String lastName) throws AutomationException {
        WebElement firstNameInput = driverUtil.getWebElement(SEARCH_PRACTITIONER_FIRST_NAME_INPUT);
        if (firstNameInput == null)
            throw new AutomationException("Unable to find element with the given XPATH: " + SEARCH_PRACTITIONER_FIRST_NAME_INPUT);
        firstNameInput.clear();
        firstNameInput.sendKeys(lastName, Keys.ENTER);
        WebDriverUtil.waitForInvisibleElement(By.xpath(SPINNER_ICON));
    }

    public void searchPractitionerByNPIID(String lastName) throws AutomationException {
        WebElement npiIDInput = driverUtil.getWebElement(SEARCH_PRACTITIONER_NPI_INPUT);
        if (npiIDInput == null)
            throw new AutomationException("Unable to find element with the given XPATH: " + SEARCH_PRACTITIONER_NPI_INPUT);
        npiIDInput.clear();
        npiIDInput.sendKeys(lastName, Keys.ENTER);
        WebDriverUtil.waitForInvisibleElement(By.xpath(SPINNER_ICON));
    }

    public void searchPractitionerByPhoneNumber(String lastName) throws AutomationException {
        WebElement phoneNumberInput = driverUtil.getWebElement(SEARCH_PRACTITIONER_PHONE_NUMBER_INPUT);
        if (phoneNumberInput == null)
            throw new AutomationException("Unable to find element with the given XPATH: " + SEARCH_PRACTITIONER_PHONE_NUMBER_INPUT);
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys(lastName, Keys.ENTER);
        WebDriverUtil.waitForInvisibleElement(By.xpath(SPINNER_ICON));
    }

    public void searchPractitionerByLastName(String lastName) throws AutomationException {
        WebElement lastNameInput = driverUtil.getWebElement(SEARCH_PRACTITIONER_LAST_NAME_INPUT);
        if (lastNameInput == null)
            throw new AutomationException("Unable to find element with the given XPATH: " + SEARCH_PRACTITIONER_LAST_NAME_INPUT);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName, Keys.ENTER);
        WebDriverUtil.waitForInvisibleElement(By.xpath(SPINNER_ICON));
    }

    public void searchPractitionerByPhone(String phone) throws AutomationException {
        WebElement phoneInput = driverUtil.getWebElement(SEARCH_PRACTITIONER_PHONE_INPUT);
        if (phoneInput == null)
            throw new AutomationException("Unable to find element with the given XPATH: " + SEARCH_PRACTITIONER_PHONE_INPUT);
        phoneInput.clear();
        phoneInput.sendKeys(phone, Keys.ENTER);
        WebDriverUtil.waitForInvisibleElement(By.xpath(SPINNER_ICON));
    }

    public void selectPractitioner(String name) throws AutomationException {
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(PRACTITIONER_CHOICE_RECORD, name));
        if (practitioner == null)
            throw new AutomationException("No practitioner found with the name: " + name);
        practitioner.click();
        takeScreenshot();
    }

    public void verifyCareTeamPractitionerRecord(String name) throws AutomationException {
        WebElement practitioner = null;
        int maxScrollAttempts = 4;
        int attempt = 0;
        while (practitioner == null && attempt < maxScrollAttempts) {
            practitioner = driverUtil.getWebElement(String.format(CARE_TEAM_PRACTITIONER_RECORD, name));
            if (practitioner == null) {
                WebElement tableContainer = driverUtil.getWebElement("//div[text()='Practitioners']/parent::div//div[contains(@class,'mantine-1eedakw')]");
                JavascriptExecutor js = (JavascriptExecutor) DriverFactory.drivers.get();
                js.executeScript("arguments[0].scrollBy(0, 100);", tableContainer);
                WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
            }
        }
        if (practitioner == null)
            throw new AutomationException("No practitioner found with the name: " + name);
//        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PRACTITIONER_RECORD, name));
//        takeScreenshot();
//        if (practitioner == null)
//            throw new AutomationException("No practitioner found with the name: " + name);
    }

    public void clickOnCareTeamPractitionerRecord(String name) throws AutomationException {
        WebElement practitioner = null;
        int maxScrollAttempts = 4;
        int attempt = 0;
        while (practitioner == null && attempt < maxScrollAttempts) {
            practitioner = driverUtil.getWebElement(String.format(CARE_TEAM_PRACTITIONER_RECORD, name));
            if (practitioner == null) {
                WebElement tableContainer = driverUtil.getWebElement("//div[text()='Practitioners']/parent::div//div[contains(@class,'mantine-1eedakw')]");
                JavascriptExecutor js = (JavascriptExecutor) DriverFactory.drivers.get();
                js.executeScript("arguments[0].scrollBy(0, 100);", tableContainer);
                WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
            }
        }
        if (practitioner == null)
            throw new AutomationException("No practitioner found with the name: " + name);
        driverUtil.clickUsingJavaScript(String.format(CARE_TEAM_PRACTITIONER_RECORD, name));
    }

    public void deleteCareTeamPractitionerRecord(String name) throws AutomationException {
        WebElement practitionerDelete = driverUtil.getWebElement(String.format(CARE_TEAM_PRACTITIONER_DELETE, name));
        if (practitionerDelete == null)
            throw new AutomationException("No practitioner found with the name: " + name);
        WebDriverUtil.waitForAWhile(2);
        try {
            Actions action = new Actions(DriverFactory.drivers.get());
            action.moveToElement(practitionerDelete).perform();
            waitForAWhile(DEFAULT_ELEMENT_WAIT);
            practitionerDelete.click();
        } catch (Exception e) {
            practitionerDelete = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PRACTITIONER_DELETE, name));
            driverUtil.moveToElementAndClick(practitionerDelete);
        }
        driverUtil.getWebElement(CARE_TEAM_PRACTITIONER_DELETE_CONFIRMATION, WebDriverUtil.WAIT_5_SEC, "Unable to find delete confirmation popup!").click();
    }

    public void deleteCareTeamPharmacyRecord(String name) throws AutomationException {
        WebElement practitionerDelete = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PHARMACY_DELETE, name));
        if (practitionerDelete == null)
            throw new AutomationException("No practitioner found with the name: " + name);
        WebDriverUtil.waitForAWhile(2);
        driverUtil.moveToElementAndClick(practitionerDelete);
        driverUtil.getWebElement(CARE_TEAM_PHARMACY_DELETE_CONFIRMATION, WebDriverUtil.WAIT_5_SEC, "Unable to find delete confirmation popup!").click();
    }

    public void updatePractitionerRole(String roleName) throws AutomationException {
        Select roleDropDown = new Select(driverUtil.getWebElement(PRACTITIONER_ROLE_SELECT));
        roleDropDown.selectByVisibleText(roleName);
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        clickOnNameLink();
    }

    public void verifyPractitionerRole(String roleName) throws AutomationException {
        Select roleDropDown = new Select(driverUtil.getWebElement(PRACTITIONER_ROLE_SELECT));
        String selectedRole = roleDropDown.getFirstSelectedOption().getText();
        takeScreenshot();
        if (!roleName.equalsIgnoreCase(selectedRole))
            throw new AutomationException("Practitioner role is not being matched, we supposed it should be: " + roleName);
    }

    public void clickOnCareTeamPharmacyRecord(String name) throws AutomationException {
        int count = 0;
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PHARMACY_RECORD, name));
        if (practitioner == null)
            throw new AutomationException("No pharmacy found with the name: " + name);
        WebDriverUtil.waitForElementClickable(By.xpath(String.format(CARE_TEAM_PHARMACY_RECORD, name)));
        try {
            practitioner.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (count <= 10) {
            practitioner = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PHARMACY_RECORD, name));
            if (practitioner.getAttribute("class").contains("selected"))
                break;
            try {
                practitioner.click();
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
            WebDriverUtil.waitForAWhile();
        }
        takeScreenshot();
    }

    public void verifyCareTeamPharmacyRecord(String name) throws AutomationException {
        WebElement practitioner = driverUtil.getWebElementAndScroll(String.format(CARE_TEAM_PHARMACY_RECORD, name));
        takeScreenshot();
        if (practitioner == null)
            throw new AutomationException("No pharmacy found with the name: " + name);
    }

    public void updatePatientPreferredLanguage(String language) throws AutomationException {
        Select languageDropdown = new Select(driverUtil.getWebElementAndScroll(PATIENT_PREFERRED_LANGUAGE_DROPDOWN));
        languageDropdown.selectByVisibleText(language);
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        takeScreenshot();
    }

    public void updatePatientReportLanguage(String language) throws AutomationException {
        Select languageDropdown = new Select(driverUtil.getWebElementAndScroll(PATIENT_REPORT_LANGUAGE_DROPDOWN));
        languageDropdown.selectByVisibleText(language);
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
        takeScreenshot();
    }

    public void clearAllFilters() throws AutomationException {
        List<WebElement> filterInputs = driverUtil.getWebElements(LOG_STORY_TABLE + "//input");
        for (WebElement filterInput : filterInputs) {
            if (!filterInput.getAttribute("value").isEmpty()) {
                filterInput.click();
                filterInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                WebDriverUtil.waitForAWhile();
            }
        }
    }

    public void performSorting(String filterName) throws AutomationException {
        driverUtil.getWebElementAndScroll(String.format(LOG_STORY_HEADER_COLUMN, LOG_DATA_HEADER_MAPPING.get(filterName))).click();
    }

    public void performTaskSorting(String filterName) throws AutomationException {
        driverUtil.getWebElementAndScroll(String.format(TASK_HEADER_COLUMN, TASK_DATA_HEADER_MAPPING.get(filterName))).click();
    }

    public void verifyLogFilter(String filterName) throws AutomationException {
        WebElement filterInput = driverUtil.getWebElement(String.format(LOG_STORY_TABLE_FILTER_INPUT, LOG_DATA_HEADER_MAPPING.get(filterName)));
        if (filterInput != null) {
            driverUtil.getWebElementAndScroll(LOG_STORY_TABLE);
            WebDriverUtil.waitForAWhile();
            String firstRecordDataValue = driverUtil.getWebElement(String.format(LOG_STORY_TABLE_COLUMN, LOG_DATA_HEADER_MAPPING.get(filterName))).getText();
            if (firstRecordDataValue == null || firstRecordDataValue.isEmpty())
                performSorting(filterName);
            firstRecordDataValue = driverUtil.getWebElement(String.format(LOG_STORY_TABLE_COLUMN, LOG_DATA_HEADER_MAPPING.get(filterName))).getText();
            if (firstRecordDataValue != null) {
                filterInput.sendKeys(firstRecordDataValue);
                List<WebElement> allValues = driverUtil.getWebElements(String.format(LOG_STORY_TABLE_COLUMN, LOG_DATA_HEADER_MAPPING.get(filterName)));
                for (WebElement data : allValues) {
                    if (!data.getText().contains(firstRecordDataValue)) {
                        throw new AutomationException("Patient timeline records are not being filter after apply filter!");
                    }
                }
                takeScreenshot();
                filterInput.click();
                filterInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                WebDriverUtil.waitForAWhile();
            }
        }
    }

    public void verifyTaskFilter(String filterName) throws AutomationException {
        WebElement filterInput = driverUtil.getWebElement(String.format(TASK_TABLE_FILTER_INPUT, TASK_DATA_HEADER_MAPPING.get(filterName)));
        if (filterInput != null) {
            WebDriverUtil.waitForAWhile();
            WebElement firstTaskFilterValueRecord = driverUtil.getWebElement(String.format(TASK_TABLE_COLUMN, TASK_DATA_HEADER_MAPPING.get(filterName)));
            if (firstTaskFilterValueRecord != null) {
                String firstRecordDataValue = firstTaskFilterValueRecord.getText();
                if (firstRecordDataValue == null || firstRecordDataValue.isEmpty())
                    performTaskSorting(filterName);
                firstRecordDataValue = driverUtil.getWebElement(String.format(TASK_TABLE_COLUMN, TASK_DATA_HEADER_MAPPING.get(filterName))).getText();
                if (firstRecordDataValue != null) {
                    firstRecordDataValue = firstRecordDataValue.substring(0, firstRecordDataValue.indexOf(" ") == -1 ? firstRecordDataValue.length() : firstRecordDataValue.indexOf(" "));
                    filterInput.sendKeys(firstRecordDataValue);
                    List<WebElement> allValues = driverUtil.getWebElements(String.format(TASK_TABLE_COLUMN, TASK_DATA_HEADER_MAPPING.get(filterName)));
                    for (WebElement data : allValues) {
                        if (!data.getText().contains(firstRecordDataValue)) {
                            throw new AutomationException("Patient task records are not being filter after apply filter!");
                        }
                    }
                    takeScreenshot();
                    filterInput.click();
                    filterInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                    WebDriverUtil.waitForAWhile();
                }
            }
        }
    }

    public void uploadFile(String fileName) throws AutomationException {
        WebElement uploadFilesInput = driverUtil.getElementUsingJavascript(REPORT_UPLOAD_FILES_INPUT);
        uploadFilesInput.sendKeys(((System.getProperty(OS) == null || System.getProperty(OS) == WINDOWS) ? System.getProperty("user.dir") : System.getProperty("user.dir").replace("\\", "/")) + "/" + fileName);
        uploadFilesInput = driverUtil.getWebElement("//*[contains(text(),'Files to Upload')]");
        if (uploadFilesInput == null)
            throw new AutomationException("Unable to upload file!");
        WebElement renameFileInput = driverUtil.getWebElement(String.format(REPORT_FILE_RENAME_INPUT,
                fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length())));
        if (renameFileInput == null)
            throw new AutomationException("Unable to find uploaded file rename input text box!");
    }

    public void updateUploadedFileNameWithTimestamp(String fileName) throws AutomationException {
        WebElement renameFileInput = driverUtil.getWebElement(String.format(REPORT_FILE_RENAME_INPUT,
                fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length())));
        if (renameFileInput == null)
            throw new AutomationException("Unable to find uploaded file rename input text box!");
        long timestamp = System.currentTimeMillis();
        lastUsedTimeStamp.set(Long.toString(timestamp));
        renameFileInput.clear();
        renameFileInput.sendKeys(Long.toString(timestamp));
    }

    public void verifyUploadedTextFileAndData(String text) throws AutomationException {
        try {
            String textData = null;
            Base64.Decoder decoder = Base64.getDecoder();
            String lastTimestamp = lastUsedTimeStamp.get();
            clickOnViewReportLink(lastTimestamp + ".txt");
            verifyReportViewer();
            WebElement dataElement = driverUtil.getWebElement(REPORT_POPUP_DATA_OBJECT);
            byte[] textContents = dataElement.getAttribute("data").getBytes(StandardCharsets.UTF_8);
            String encodedString = new String(textContents);
            encodedString = encodedString.substring(encodedString.indexOf(",") + 1, encodedString.length());

            if (encodedString.startsWith("https")) {
                URL pdfUrl = new URL(encodedString);
                InputStream inputStream = pdfUrl.openStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                textData = outputStream.toString();
            } else {
                textData = new String(decoder.decode(encodedString));
            }
            if (!textData.contains(text))
                throw new AutomationException("Uploaded file data is not being match!");
            takeScreenshot();
        } catch (Exception ex) {
            throw new AutomationException("Uploaded file data is not being match!");
        }
    }

    public void verifyUploadedPdfFileAndData(String text) throws AutomationException {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String lastTimestamp = lastUsedTimeStamp.get();
            clickOnViewReportLink(lastTimestamp + ".pdf");
            verifyReportViewer();
            verifyReportContains(text);
        } catch (Exception ex) {
            throw new AutomationException("Uploaded file data is not being match!");
        }
    }

    public void clickOnAdvanceSearch() throws AutomationException {
        WebElement advanceSearch = driverUtil.findElement(ADVANCE_SEARCH_BUTTON);
        if (advanceSearch == null)
            throw new AutomationException("Unable to find ADVANCE search button!");
        advanceSearch.click();
    }

    public void advanceSearchByFirstName(String input) throws AutomationException {
        WebElement firstNameInput = driverUtil.findElement(ADVANCE_SEARCH_FIRST_NAME_INPUT);
        if (firstNameInput == null)
            throw new AutomationException("Unable to locate first name input in advance search form!");
        firstNameInput.sendKeys(input, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void advanceSearchByLastName(String input) throws AutomationException {
        WebElement firstNameInput = driverUtil.findElement(ADVANCE_SEARCH_LAST_NAME_INPUT);
        if (firstNameInput == null)
            throw new AutomationException("Unable to locate last name input in advance search form!");
        firstNameInput.sendKeys(input, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void verifyAdvanceSearchPatientRecord(String patientId) throws AutomationException {
        WebElement patientRecord = driverUtil.findElement(String.format(ADVANCE_SEARCH_PATIENT_RECORD, patientId));
        if (patientRecord == null)
            throw new AutomationException("Unable to find patient record with given patient id: '" + patientId + "'");
        takeScreenshot();
        patientRecord.click();
        driverUtil.waitForLoadingPage();
    }

    public void scheduleNextDayAvailableAppointment() throws AutomationException {
        try {
            driverUtil.switchToFrame(SCHEDULE_APPOINTMENT_FRAME);
            try {
                WebElement iUnderstandButton = driverUtil.findElementAndScroll(I_UNDERSTAND_BUTTON);
                if (iUnderstandButton != null) {
                    iUnderstandButton.click();
                    WebDriverUtil.waitForAWhile(20);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            WebElement nextMonthScheduleButton = driverUtil.findElement(PATIENT_APPOINTMENT_CALENDER_NEXT_MONTH_BUTTON);
            if (nextMonthScheduleButton == null) {
                WebDriverUtil.waitForAWhile(20);
                nextMonthScheduleButton = driverUtil.findElement(PATIENT_APPOINTMENT_CALENDER_NEXT_MONTH_BUTTON);
            }
            if (nextMonthScheduleButton != null) {
                nextMonthScheduleButton.click();
                WebDriverUtil.waitForAWhile(20);
            }
            List<WebElement> nextDates = driverUtil.getWebElements(PATIENT_NEXT_DAY_APPOINTMENT_DATE);
            if (nextDates == null)
                throw new AutomationException("Unable to locate calender date elements!");
            for (WebElement date : nextDates) {
                String innerHTML = date.getAttribute("innerHTML");
                if (!innerHTML.contains("disabled")) {
                    date.findElement(By.xpath("button")).click();
                    WebElement availableSlotTime = driverUtil.findElement(PATIENT_APPOINTMENT_FIRST_AVAILABLE_SLOT);
                    if (availableSlotTime != null) {
                        availableSlotTime.click();
                        driverUtil.findElement(PATIENT_APPOINTMENT_CONFIRM_SLOT_BUTTON).click();
                        break;
                    }
                }
            }
            WebDriverUtil.waitForAWhile(10);
            try {
                WebElement iUnderstandButton = driverUtil.findElementAndScroll(I_UNDERSTAND_BUTTON);
                if (iUnderstandButton != null) {
                    iUnderstandButton.click();
                    WebDriverUtil.waitForAWhile(20);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            takeScreenshot();
            driverUtil.clickUsingJavaScript(PATIENT_APPOINTMENT_SCHEDULE_EVENT_BUTTON);
            WebDriverUtil.waitForAWhile(15);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AutomationException("Unable to schedule appointment!");
        } finally {
            driverUtil.switchToDefault();
            checkAnyPopupAndClose();
            PageFactory.homePage().scrollToMenu("Patient");
        }
    }

    public void verifyAddedAppointment() throws AutomationException {
        WebElement createdAppointmentDate = driverUtil.getWebElement(PATIENT_APPOINTMENT_SCHEDULED_TIME, WebDriverUtil.MAX_ELEMENT_WAIT);
        if (createdAppointmentDate == null)
            throw new AutomationException("No appointment created with selected patient!");
        takeScreenshot();
        lastScheduledAppointTime.set(createdAppointmentDate.getText());
    }

    public void verifyRescheduledAppointment() throws AutomationException {
        WebElement createdAppointmentDate = driverUtil.getWebElement(PATIENT_APPOINTMENT_SCHEDULED_TIME, WebDriverUtil.MAX_ELEMENT_WAIT);
        if (createdAppointmentDate == null)
            throw new AutomationException("No appointment created with selected patient!");
        takeScreenshot();
        String oldScheduleDateTime = lastScheduledAppointTime.get();
        if (oldScheduleDateTime.equalsIgnoreCase(createdAppointmentDate.getText()))
            throw new AutomationException("Scheduled Appointment date & time is not being update!");
        lastScheduledAppointTime.set(createdAppointmentDate.getText());
    }

    public void clickOnRescheduledAppointmentButton() throws AutomationException {
        WebElement rescheduleButton = driverUtil.getWebElement(PATIENT_APPOINTMENT_RESCHEDULED);
        if (rescheduleButton == null)
            throw new AutomationException("No appointment scheduled with selected patient!");
        rescheduleButton.click();
    }

    public void clickOnCancelAppointmentButton() throws AutomationException {
        WebElement cancelScheduleButton = driverUtil.getWebElement(PATIENT_APPOINTMENT_SCHEDULED_CANCEL);
        if (cancelScheduleButton == null)
            throw new AutomationException("No appointment scheduled with selected patient!");
        cancelScheduleButton.click();
    }

    public void cancelAppointment() throws AutomationException {
        try {
            driverUtil.switchToFrame(SCHEDULE_APPOINTMENT_FRAME);
            WebElement eventCancelButton = driverUtil.getWebElement(PATIENT_APPOINTMENT_EVENT_CANCEL);
            if (eventCancelButton == null)
                throw new AutomationException("No appointment scheduled with selected patient!");
            eventCancelButton.click();
        } catch (Exception ex) {
            throw new AutomationException("Unable to cancel appointment!");
        } finally {
            takeScreenshot();
            driverUtil.switchToDefault();
            checkAnyPopupAndClose();
            PageFactory.homePage().scrollToMenu("Patient");
        }
    }

    public void verifyCancelAppointment() throws AutomationException {
        WebElement cancelScheduleButton = driverUtil.getWebElement(PATIENT_APPOINTMENT_SCHEDULED_CANCEL);
        if (cancelScheduleButton != null) {
            cancelScheduleButton.click();
            driverUtil.switchToFrame(SCHEDULE_APPOINTMENT_FRAME);
            try {
                WebElement eventCancelButton = driverUtil.getWebElement(PATIENT_APPOINTMENT_EVENT_CANCEL);
                if (eventCancelButton == null)
                    throw new AutomationException("No appointment scheduled with selected patient!");
                eventCancelButton.click();
            } catch (Exception ex) {
                throw new AutomationException("Unable to cancel appointment!");
            } finally {
                takeScreenshot();
                driverUtil.switchToDefault();
                scheduleAppointmentPopupClose();
                PageFactory.homePage().scrollToMenu("Patient");
            }
        }
    }

    public void rescheduleNextDayAvailableAppointment() throws AutomationException {
        try {
            driverUtil.switchToFrame(SCHEDULE_APPOINTMENT_FRAME);
            WebDriverUtil.waitForAWhile(10);
            driverUtil.getWebElement(PATIENT_APPOINTMENT_CALENDER_NEXT_MONTH_BUTTON, WebDriverUtil.MAX_WAIT_120).click();
            WebDriverUtil.waitForAWhile(10);
            List<WebElement> nextDates = driverUtil.getWebElements(PATIENT_NEXT_DAY_APPOINTMENT_DATE);
            if (nextDates == null)
                throw new AutomationException("Unable to locate calender date elements!");

            boolean firstEnableDate = false;
            for (WebElement date : nextDates) {
                String innerHTML = date.getAttribute("innerHTML");
                if (!innerHTML.contains("disabled")) {
                    if (firstEnableDate) {
                        date.findElement(By.xpath("button")).click();
                        WebElement availableSlotTime = driverUtil.getWebElement(PATIENT_APPOINTMENT_FIRST_AVAILABLE_SLOT);
                        if (availableSlotTime != null) {
                            availableSlotTime.click();
                            driverUtil.getWebElement(PATIENT_APPOINTMENT_CONFIRM_SLOT_BUTTON).click();
                            break;
                        }
                    } else {
                        firstEnableDate = true;
                    }
                }
            }
            WebDriverUtil.waitForAWhile(10);
            driverUtil.findElementAndScroll(PATIENT_APPOINTMENT_UPDATE_EVENT_BUTTON).click();
            WebDriverUtil.waitForAWhile(15);
        } catch (Exception ex) {
            REPORT_LOGGER.error("Unable to reschedule appointment!");
            ex.printStackTrace();
            throw new AutomationException("Unable to rescheduled appointment!");
        } finally {
            takeScreenshot();
            driverUtil.switchToDefault();
            scheduleAppointmentPopupClose();
            PageFactory.homePage().scrollToMenu("Patient");
        }
    }

    public void scheduleAppointmentPopupClose() {
        WebElement popupCloseButton = driverUtil.findElement(PATIENT_APPOINTMENT_WINDOW_CLOSE);
        if (popupCloseButton != null)
            popupCloseButton.click();
    }

    public void setPatientAllergyOption(String option) throws AutomationException {
        driverUtil.waitForLoadingPage();
        WebElement optionElement = null;
        if (!option.equalsIgnoreCase("NKDA")) {
            optionElement = driverUtil.findElement(String.format(PATIENT_ALLERGY_OPTION, "Yes, listed below:"));
            if (optionElement == null)
                throw new AutomationException("No allergy option available for: " + option);
            optionElement.click();
            driverUtil.findElement(PATIENT_ALLERGY_DRUG_NAME_INPUT).clear();
            driverUtil.findElement(PATIENT_ALLERGY_DRUG_NAME_INPUT).sendKeys(option);
        } else {
            optionElement = driverUtil.findElement(String.format(PATIENT_ALLERGY_OPTION, "NKDA"));
            if (optionElement == null)
                throw new AutomationException("No allergy option available for: " + option);
            optionElement.click();
        }
        driverUtil.findElement(PATIENT_ALLERGY_UPDATE_BUTTON).click();
        WebDriverUtil.waitForAWhile();
        takeScreenshot();
    }

    public void verifyPatientAllergy(String allergyName) throws AutomationException {
        WebElement patientAllergies = driverUtil.findElement(PATIENT_ALLERGIES);
        String allergies = patientAllergies.getAttribute("value");
        if (!allergies.contains(allergyName))
            throw new AutomationException(String.format("Given allergy: %s is not present in patient allergies!", allergyName));
    }

    public void verifyPatientAutoLog(String logMessage) throws AutomationException {
        driverUtil.getWebElementAndScroll(LOG_STORY_TABLE);
        WebElement firstLogRecord = driverUtil.findElement(PATIENT_FIRST_AUTO_LOG_RECORD);
        String logData = firstLogRecord.getAttribute("innerHTML");
        if (!logData.contains(logMessage)) {
            WebDriverUtil.waitForAWhile(10);
            firstLogRecord = driverUtil.findElement(PATIENT_FIRST_AUTO_LOG_RECORD);
            logData = firstLogRecord.getAttribute("innerHTML");
            if (!logData.contains(logMessage)) {
                throw new AutomationException(String.format("First log does not contains log message: ", logMessage));
            }
        }
    }

    public void verifyPatientMedicationSection() throws AutomationException {
        WebElement medicationTable = driverUtil.getWebElement(PATIENT_MEDICATION_TABLE);
        if (medicationTable == null)
            throw new AutomationException("Patient Medication Table is not being displayed or it might taking too long time to load!");
    }

    public void verifyPatientDRPSection() throws AutomationException {
        WebElement drpTable = driverUtil.getWebElement(PATIENT_DRP_TABLE);
        if (drpTable == null)
            throw new AutomationException("Patient DRP Table is not being displayed or it might taking too long time to load!");
    }

    public void verifyPatientMedicalDiagnosisSection() throws AutomationException {
        WebElement medicalDiagnosisTable = driverUtil.getWebElement(PATIENT_DIAGNOSIS_TABLE);
        if (medicalDiagnosisTable == null)
            throw new AutomationException("Patient Medical Diagnosis Table is not being displayed or it might taking too long time to load!");
    }

    public void verifySIGEnglishSpanishTextForNonMtmPatient(List<SigTranslation> sigTranslations, boolean all,
                                                            int recordCount) throws AutomationException {
        Integer index = 0;
        Integer count = sigTranslations.size();
        StringBuilder unmatched = new StringBuilder();
        String unmatchedKey = null;
        if (!all) {
            index = CommonUtil.getIntFromObject(PersistentData.getProperty(SigTranslation.SIG_TEXT_EXECUTED_COUNTER_INDEX));
            if (index == 0)
                count = index + recordCount;
            else
                count = (index + recordCount) - 1;
        }
        int executedCount = 0;
        try {
            Pattern pattern = Pattern.compile("(?i)\\s*\\(\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?\\s*mg\\)\\s*");
            WebElement sig = driverUtil.findElementAndScroll(SIG_INPUT);
            if (sig == null)
                throw new AutomationException("Unable to locate SIG input box!");
            CommonSteps.REPORT_LOGGER.log("Verify SIG Translations in English and Spanish:");
            CommonSteps.REPORT_LOGGER.log("--------------------------------------------------------------");
            for (; index <= count && index < sigTranslations.size(); index++) {
                executedCount++;
                SigTranslation sigTranslation = (SigTranslation) sigTranslations.get(index);
                CommonSteps.REPORT_LOGGER.log(String.format("SIG Text: [%s : %s]", sigTranslation.getSigShortForm(), sigTranslation.getSigEnglishTextForNonMTM()));
                sig.click();

                String sigShortForm = sigTranslation.getSigShortForm();
                sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
//                sig.sendKeys(sigTranslation.getSigShortForm(), Keys.ENTER);
                sig.sendKeys(sigShortForm, Keys.ENTER);
                WebDriverUtil.waitForAWhile();
                Matcher matcherForSigShortForm = pattern.matcher(sigShortForm);
                WebElement sigTextEnglish = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_VALUE, WebDriverUtil.NO_WAIT);
                if (sigTextEnglish == null)
                    throw new AutomationException("SIG English Text details is not being displayed for: " + sigTranslation.getSigShortForm() + "!");
                String sigEnglishText = sigTextEnglish.getText();
//                String sigEnglishText= sigTextEnglish.getAttribute("value");

                waitForLoadingPage();

                Matcher matcher = pattern.matcher(sigEnglishText);
                if (!matcherForSigShortForm.find()) {
                    if (matcher.find()) {
                        sigEnglishText = sigEnglishText.replaceAll("(?i)\\s*\\(\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?\\s*mg\\)\\s*", " ").trim();
                    }
                }

                if (!sigEnglishText.toLowerCase().contains(sigTranslation.getSigEnglishTextForNonMTM().toLowerCase())) {
//                if (!sigTextEnglish.getText().toLowerCase().contains(sigTranslation.getSigEnglishTextForNonMTM().toLowerCase())) {
                    unmatched.append(String.format("SIG English Text: <b>'%s'</b>  is not being matched for: [<b>%s : %s</b>] <br>", sigEnglishText, sigTranslation.getSigShortForm(), sigTranslation.getSigEnglishTextForNonMTM()));
//                    unmatched.append(String.format("SIG English Text: <b>'%s'</b>  is not being matched for: [<b>%s : %s</b>] <br>", sigTextEnglish.getText(), sigTranslation.getSigShortForm(), sigTranslation.getSigEnglishTextForNonMTM()));
                    unmatched.append(System.getProperty("line.separator"));
                    unmatchedKey = sigTranslation.getSigShortForm();
                }

                WebElement sigTextSpanish = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_SPANISH_INPUT, WebDriverUtil.NO_WAIT);
                if (sigTextSpanish != null) {
                    //    throw new AutomationException("SIG Spanish Text details is not being displayed for: " + sigTranslation.getSigShortForm() + "!");
                    String spanishTranslate = CommonUtil.getUTF8String(sigTextSpanish.getAttribute("value"));
                    Matcher matcherForSpanish = pattern.matcher(spanishTranslate);
                    if (!matcherForSigShortForm.find()) {
                        if (matcherForSpanish.find()) {
                            spanishTranslate = spanishTranslate.replaceAll("(?i)\\s*\\(\\d+(\\.\\d+)?(-\\d+(\\.\\d+)?)?\\s*mg\\)\\s*", " ").trim();
                        }
                    }

                    String expectedSpanishTranslate = CommonUtil.getUTF8String(sigTranslation.getSigSpanishTextForNonMTM());
                    if (!spanishTranslate.equalsIgnoreCase(expectedSpanishTranslate)) {
                        unmatched.append(String.format("SIG Spanish Text: <b>'%s'</b>  is not being matched for: [<b>%s : %s</b>] <br>", spanishTranslate, sigTranslation.getSigShortForm(), CommonUtil.getUTF8String(sigTranslation.getSigSpanishTextForNonMTM())));
                        unmatched.append(System.getProperty("line.separator"));
                        unmatchedKey = sigTranslation.getSigShortForm();
                    }
                }
                if (executedCount > 1000) {
                    executedCount = 0;
                    refreshPatient();
                    sig = driverUtil.findElementAndScroll(SIG_INPUT);
                }
            }
            if (unmatched.length() > 0) {
                sig.click();
                sig.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                sig.sendKeys(unmatchedKey, Keys.ENTER);
                WebDriverUtil.waitForAWhile();
                throw new AutomationException(unmatched.toString());
            }
            CommonSteps.REPORT_LOGGER.log("--------------------------------------------------------------");
        } catch (Exception ex) {
            throw new AutomationException(ex.getMessage());
        }
        if (!all) {
            if (index < sigTranslations.size() - 1)
                PersistentData.updatePersistentProperty(SigTranslation.SIG_TEXT_EXECUTED_COUNTER_INDEX, index.toString());
            else
                PersistentData.updatePersistentProperty(SigTranslation.SIG_TEXT_EXECUTED_COUNTER_INDEX, "0");
        }
    }

    public void refreshPatient() throws AutomationException {
        CommonSteps.clickOnSearchIcon();
        driverUtil.waitForLoadingPage();
        clickOnMedicineFirstRecord();
    }

    public void verifyRerunAnalysisButton() throws AutomationException {
        List<WebElement> availableDRP = driverUtil.getWebElements(DELETE_DRP_RECORD);

        for (WebElement element : availableDRP) {
            WebElement deleteDRPIfPresent = driverUtil.getWebElement(DELETE_DRP_RECORD);
            if (deleteDRPIfPresent != null) {
                deleteDRPIfPresent.click();
                WebElement deleteConfirmationButton = driverUtil.getWebElement(String.format(DELETE_CONFIRMATION_POPUP_DELETE_BUTTON, "Delete this recommendation"));
                if (deleteConfirmationButton == null)
                    throw new AutomationException("Unable to find delete DRP button on confirmation popup or it might taking too long time to load!");
                deleteConfirmationButton.click();
                WebDriverUtil.waitForAWhile(3);
            } else
                break;
        }

        WebElement drpRecords = driverUtil.findElementByText("There are no recommendations for this patient. click the add rec icon");
        if (drpRecords == null)
            throw new AutomationException("All the DRP's were deleted, so we expected the message 'There are no recommendations for this patient. click the add rec icon' to be displayed");
        else {
            WebElement reRunAnalysisBtn = driverUtil.findElement(RERUN_ANALYSIS);
            if (reRunAnalysisBtn == null)
                throw new AutomationException("As no DRP records are available we expected rerun analysis button to be displayed");
            takeScreenshot();
        }
    }

    public void verifySearchResultContainsName(String lastName) throws AutomationException {
        List<WebElement> webElements = driverUtil.getWebElements(PRACTITIONER_SEARCH_RESULT_NAME_COLUMN);
        if (webElements.size() == 0)
            throw new AutomationException("We expected Name column to have searched string : " + lastName + " but no records were displayed");
        for (WebElement element : webElements
        ) {
            if (!element.getText().toLowerCase().contains(lastName.toLowerCase()))
                throw new AutomationException("We expected name column to have searched string but found " + element.getText());
        }
    }

    public static int getPractitionersTableColumnHeadingIndexInSearchPractitionersPopup(String columnName) throws AutomationException {
        List<WebElement> columnNames = driverUtil.getWebElements(PHARMACIES_TABLE_COLUMN_HEADING_NAMES_IN_SEARCH_POPUP);
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
        System.out.println("columnHeadingNameList== " + columnHeadingNameList);
        return indexNumber;
    }

    public void verifySearchResultContainsPhone(String phone) throws AutomationException {
        int columnIndex = getPractitionersTableColumnHeadingIndexInSearchPractitionersPopup("pref Phone");
        System.out.println("colomnIndex= " + columnIndex);
        List<WebElement> webElements = driverUtil.getWebElements(String.format(PRACTITIONER_SEARCH_RESULT_PHONE_COLUMN, columnIndex));
        if (webElements.size() == 0)
            throw new AutomationException("We expected Name column to have searched string : " + phone + " but no records were displayed");
        for (WebElement element : webElements
        ) {
            if (!element.getText().toLowerCase().contains(phone.toLowerCase()))
                throw new AutomationException("We expected name column to have searched string but found " + element.getText());
        }
    }

    public void uploadFileIfNotPresent(String filePath, String setFileName) throws AutomationException {
        showArchivedReports();
        String[] fileNameArray = filePath.split("/");
        //     String fileName = fileNameArray[fileNameArray.length - 1];
        List<WebElement> webElements = driverUtil.getWebElements(REPORT_NAMES);
        boolean recordFound = false;
        for (WebElement element : webElements) {
            if (element.getText().equals(setFileName)) {
                recordFound = true;
                break;
            }
        }
        WebElement restoreIcon = driverUtil.getWebElementAndScroll(String.format(RESTORE_ARCHIVED_REPORT, setFileName));
        if (restoreIcon != null) {
            WebDriverUtil.waitForAWhile();
            restoreIcon.click();
            clickOnButton("unarchive");
        }
        if (!recordFound) {
            clickOnButton("Upload Files");
            WebElement uploadFilesInput = driverUtil.getElementUsingJavascript(REPORT_UPLOAD_FILES_INPUT);
            uploadFilesInput.sendKeys(((System.getProperty(OS) == null || System.getProperty(OS) == WINDOWS) ? System.getProperty("user.dir") : System.getProperty("user.dir").replace("\\", "/")) + "/" + filePath);
            uploadFilesInput = driverUtil.getWebElement("//*[contains(text(),'Files to Upload')]");
            if (uploadFilesInput == null)
                throw new AutomationException("Unable to upload file!");
            WebElement renameFileInput = driverUtil.getWebElement(String.format(REPORT_FILE_RENAME_INPUT,
                    filePath.substring(filePath.lastIndexOf("/") == -1 ? 0 : filePath.lastIndexOf("/") + 1, filePath.length())));
            if (renameFileInput == null)
                throw new AutomationException("Unable to find uploaded file rename input text box!");
            renameFileInput.clear();
            renameFileInput.sendKeys(setFileName.split("\\.")[0]);
            clickOnButton("Upload Files");
            clickOnButton("Close");
        }
        hideArchivedReports();
        WebDriverUtil.waitForAWhile();
    }

    public void showArchivedReports() throws AutomationException {
        WebElement element = driverUtil.findElementByText("Show Archived Reports");
        if (element != null)
            element.click();
        takeScreenshot();
    }

    public void hideArchivedReports() throws AutomationException {
        WebElement element = driverUtil.findElementByText("Hide Archived Reports");
        if (element != null)
            element.click();
        takeScreenshot();
    }

    public void selectReport(String nameOfReportToSelect) throws AutomationException {
        WebElement selectDeselectAllcheckbox = driverUtil.getWebElement("//table//thead//div[contains(@class, 'Checkbox-body')]");
        if (selectDeselectAllcheckbox != null) {
            selectDeselectAllcheckbox.click();
            selectDeselectAllcheckbox.click();
        }
        WebElement element = driverUtil.getWebElement(String.format(REPORT_SELECTION_CHECKBOX, nameOfReportToSelect), 5);
        if (element == null)
            throw new AutomationException("checkbox to select report not displayed : " + nameOfReportToSelect);
        element.click();
    }

    public void selectReport(DataTable dataTable) throws AutomationException {
        WebElement selectDeselectAllcheckbox = driverUtil.getWebElement("//th//span[contains(@class,'checkbox')]");
        if (selectDeselectAllcheckbox != null) {
            selectDeselectAllcheckbox.click();
            selectDeselectAllcheckbox.click();
        }
        List<String> nameOfReportsToSelect = dataTable.asList(String.class);
        for (String nameOfReportToSelect : nameOfReportsToSelect
        ) {
            WebElement element = driverUtil.getWebElement(String.format(REPORT_SELECTION_CHECKBOX, nameOfReportToSelect), 5);
            if (element == null)
                throw new AutomationException("checkbox to select report not displayed : " + nameOfReportToSelect);
            element.click();
        }
    }

    public void verifyReportNotDisplayed(String reportName) throws AutomationException {
        List<WebElement> webElements = driverUtil.getWebElements(REPORT_NAMES);
        boolean recordFound = false;
        for (WebElement element : webElements
        ) {
            if (element.getText().contains(reportName)) {
                recordFound = true;
                break;
            }
        }
        if (recordFound)
            throw new AutomationException("we expected report should not be displayed but it is visible : " + reportName);
    }

    public void verifyReportDisplayed(String reportName) throws AutomationException {
        List<WebElement> webElements = driverUtil.getWebElements(REPORT_NAMES);
        boolean recordFound = false;
        for (WebElement element : webElements
        ) {
            if (element.getText().contains(reportName)) {
                recordFound = true;
                break;
            }
        }
        if (!recordFound)
            throw new AutomationException("we expected report should be displayed but it not is visible : " + reportName);
    }

    public void verifyMTMReportPreviewIsDisplayed() throws AutomationException {
        List<WebElement> webElements = driverUtil.getWebElements(MTM_REPORT_PREVIEW);
        if (webElements.size() < 2)
            throw new AutomationException("We expected MTM Report Preview to be displayed with two table but its not or Number of tables displayed : " + webElements.size());
        takeScreenshot();
    }

    public void archiveAllExistingReports() {
        try {
            List<WebElement> existingReports = driverUtil.getWebElements(REPORT_RECORDS);
            WebElement selectAllReport = driverUtil.getWebElement(SELECT_ALL_REPORT);
            if (!existingReports.isEmpty()) {
                selectAllReport = driverUtil.getWebElement(SELECT_ALL_REPORT);
                selectAllReport.click();
                WebDriverUtil.waitForAWhile();
                selectAllReport.click();
                if (selectAllReport != null) {
                    List<WebElement> allDraftReportsCheckBoxe = driverUtil.getWebElements(ALL_DRAFT_REPORTS_CHECKBOX);
                    if (!allDraftReportsCheckBoxe.isEmpty()) {
                        for (WebElement checkBox : allDraftReportsCheckBoxe) {
                            if (checkBox != null)
                                checkBox.click();
                        }
                        driverUtil.getWebElement(BUTTON_ARCHIVE_SELECTED).click();
                        PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Confirm");
                        WebDriverUtil.waitForAWhile();
                    }
                }
            }
            existingReports = driverUtil.getWebElements(REPORT_RECORDS);
            if (!existingReports.isEmpty()) {
                if (selectAllReport != null) {
                    selectAllReport.click();
                    driverUtil.getWebElement(BUTTON_ARCHIVE_SELECTED).click();
                    PageFactory.hardStopPage().clickOnButtonPresentInConfirmationPopUp("Confirm");
                    WebDriverUtil.waitForAWhile();
                }
            }
        } catch (Exception exception) {
            //DO nothing
        }
        takeScreenshot();
    }

    public void verifyDownloadedFileHasFileName(String fileName) throws AutomationException {
        boolean isFileFound = false;
        int counter = 0;
        File[] files = null;
        do {
            try {
//                files = FileUtil.getAllFiles(((System.getProperty(DriverFactory.OS) == null || System.getProperty(DriverFactory.OS) == DriverFactory.WINDOWS) ? System.getProperty("user.dir") : System.getProperty("user.dir").replace("\\", "/")) + File.separator + "downloads");

                files = FileUtil.getAllFiles(((System.getProperty(OS) == null || System.getProperty(OS).equals(WINDOWS)) ? System.getProperty("user.dir") + "\\downloads" : System.getProperty("user.dir").replace("\\", "/") + "/downloads"));

                System.out.println("Iterating over files");
                for (File file : files) {
                    if (file.exists() && !file.isDirectory()) {
                        System.out.println(file.getName());
                        if (file.getName().toLowerCase().contains(fileName.toLowerCase())) {
                            isFileFound = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter++;
            WebDriverUtil.waitForAWhile(10);
        } while (!isFileFound && counter < 25);
        if (!isFileFound)
            throw new AutomationException("The expected file was probably not downloaded or taking to long time to download");
    }

    public void getDRPInformation() throws AutomationException {
        drpInfoList.clear();
        List<WebElement> webElements = driverUtil.getWebElements(REPORT_TABS_DRP);
        if (webElements != null && webElements.size() > 0) {
            for (int i = 0; i < webElements.size(); i++) {
                webElements = driverUtil.getWebElements(REPORT_TABS_DRP);
                String drpName = null;
                int attempts = 0;
                boolean result = false;
                while (attempts < 10) {
                    System.out.println(attempts);
                    try {
                        drpName = webElements.get(i).getText();
                        result = true;
                        break;
                    } catch (StaleElementReferenceException e) {
                        // do nothing
                    }
                    if (result)
                        break;
                    attempts++;
                }
                webElements.get(i).click();
                String providerAssessment = driverUtil.getWebElementAndScroll(REPORT_TABS_DRP_PROVIDER_ASSESSMENT, 10, "Not able to find DRP Provider Assessment Information or DRP Details not found").getText();
                String providerRecommendation = driverUtil.getWebElementAndScroll(REPORT_TABS_DRP_PROVIDER_RECOMMENDATION, 10, "Not able to find DRP Provider Recommendation Information or DRP Details not found").getText();
                String patientAssessment = driverUtil.getWebElementAndScroll(REPORT_TABS_DRP_PATIENT_ASSESSMENT, 10, "Not able to find DRP Patient Assessment Information or DRP Details not found").getText();
                String patientRecommendation = driverUtil.getWebElementAndScroll(REPORT_TABS_DRP_PATIENT_RECOMMENDATION, 10, "Not able to find DRP Patient Recommendation Information or DRP Details not found").getText();
                scrollToTop();
                drpInfoList.add(new DRPInfo(drpName, providerAssessment, providerRecommendation, patientAssessment, patientRecommendation));
            }
        }
    }

    public void verifyPCPMTMReportInfo() throws AutomationException {
        for (DRPInfo drpinfo : drpInfoList
        ) {
            System.out.println("******* Verifying for DRP **********");
            System.out.println(drpinfo.getDrpName());
            System.out.println("******* Verifying Provider assessment from UI **********");
            System.out.println(drpinfo.getProviderAssessment());
            verifyReportContains(drpinfo.getProviderAssessment());
            System.out.println("******* Verifying Provider Recommendation from UI **********");
            System.out.println(drpinfo.getProviderRecommendation());
            verifyReportContains(drpinfo.getProviderRecommendation());
        }
    }

    public void verifyMAPReportInfo() throws AutomationException {
        for (DRPInfo drpinfo : drpInfoList
        ) {
            System.out.println("******* Verifying for DRP **********");
            System.out.println(drpinfo.getDrpName());
            System.out.println("******* Verifying patient assessment from UI **********");
            System.out.println(drpinfo.getPatientAssessment());
            verifyReportContains(drpinfo.getPatientAssessment());
            System.out.println("******* Verifying patient recommendation from UI **********");
            System.out.println(drpinfo.getPatientRecommendation());
            verifyReportContains(drpinfo.getPatientRecommendation());
        }
    }

    public void selectAllCardiologistAndPCPReport() throws AutomationException {

        WebElement cardiologistCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
        if (cardiologistCheckbox == null)
            throw new AutomationException("Cardiologist checkbox not displayed on reports tab");
        if (!cardiologistCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);

        WebElement pcpCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_PCP_CHECKBOX);
        if (pcpCheckbox == null)
            throw new AutomationException("PCP checkbox not displayed on reports tab");
        if (!pcpCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_PCP_CHECKBOX);
        waitForLoadingPage();
    }

    public void selectAllCardiologist() throws AutomationException {

        WebElement cardiologistCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
        if (cardiologistCheckbox == null)
            throw new AutomationException("Cardiologist checkbox not displayed on reports tab");
        if (!cardiologistCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
    }

    public void deselectAllCardiologistAndPCPReport() throws AutomationException {

        WebElement cardiologistCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
        if (cardiologistCheckbox == null)
            throw new AutomationException("Cardiologist checkbox not displayed on reports tab");
        if (cardiologistCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);

        WebElement pcpCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_PCP_CHECKBOX);
        if (pcpCheckbox == null)
            throw new AutomationException("PCP checkbox not displayed on reports tab");
        if (pcpCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_PCP_CHECKBOX);
    }

    public void deselectAllCardiologist() throws AutomationException {

        WebElement cardiologistCheckbox = driverUtil.getElementUsingJavascript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
        if (cardiologistCheckbox == null)
            throw new AutomationException("Cardiologist checkbox not displayed on reports tab");
        if (cardiologistCheckbox.isSelected())
            driverUtil.clickUsingJavaScript(REPORT_TABS_SELECT_ALL_CARDIOLOGIST_CHECKBOX);
    }

    public boolean verifyGlobalTabNotDisplayed(String tabName) throws AutomationException {
        WebElement globalTab = driverUtil.findElement(String.format(PATIENT_GLOBAL_TABS, tabName));
        if (globalTab != null)
            throw new AutomationException(String.format("Global tab %s is being displayed!", tabName));
        return true;
    }

    public void updatePharmacyStateRecord(String stateName) throws AutomationException {
        WebElement stateDropdown = driverUtil.getWebElement(STATE_DROPDOWN_BUTTON);
        boolean recordFound = false;
        Select select = new Select(stateDropdown);
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            String optionText = option.getText();
            if (optionText.equalsIgnoreCase(stateName)) {
                recordFound = true;
                option.click();
                break;
            }
        }
        if (!recordFound)
            throw new AutomationException(String.format("%s State Name is not being displayed in State Dropdown!", stateName));
        driverUtil.waitForLoadingPage();
    }

    public void updatePractitionerStateRecord(String stateName) throws AutomationException {
        WebElement stateDropdown = driverUtil.getWebElement(STATE_DROPDOWN_BUTTON);
        Select select = new Select(stateDropdown);
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            String optionText = option.getText();
            if (optionText.equalsIgnoreCase(stateName)) {
                option.click();
                break;
            }
        }
        driverUtil.waitForLoadingPage();
    }

    public void verifyStatePresentInStateDropDown(String state) throws AutomationException {
        WebElement stateDropDown = driverUtil.getWebElement(STATE_DROPDOWN_BUTTON);
        boolean recordFound = false;
        Select select = new Select(stateDropDown);
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            String optionText = option.getText();
            if (optionText.equalsIgnoreCase(state)) {
                recordFound = true;
                break;
            }
        }
        if (!recordFound)
            throw new AutomationException(String.format("%s State Name is not being displayed in State Dropdown!", state));
        driverUtil.waitForLoadingPage();

//        WebElement stateDropdown = driverUtil.getWebElement(STATE_DROPDOWN_BUTTON);
//        if (stateDropdown == null)
//            throw new AutomationException("State DropDown not present on Screen");
//        stateDropdown.click();
//        WebElement stateDropdownOptions = driverUtil.getWebElement(String.format(STATE_DROPDOWN_OPTIONS, state));
//        if (stateDropdownOptions == null)
//            throw new AutomationException(String.format("State %s Option not present in DropDown", state));
        takeScreenshot();
    }

    public void verifyUserAbleToDeleteAndUpdatePatientsDOB() throws AutomationException {
        WebElement birthDateInput = driverUtil.getWebElement(PATIENT_BIRTH_DATE_INPUT);
        if (birthDateInput == null)
            throw new AutomationException("Unable to find patient's birth date Input box!");
        String birthDate = birthDateInput.getAttribute("value");
        System.out.println("Date Of Birth = " + birthDate);
        birthDateInput.click();
        birthDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        WebDriverUtil.waitForAWhile(5);
        birthDateInput.sendKeys(birthDate, Keys.ENTER);
    }

    public void userSelectAllSpecificRolesDRPCheckbox(String drpRole) throws AutomationException {
        waitForLoadingPage();
        WebElement drpRoleCheckbox = driverUtil.getElementUsingJavascript(String.format(REPORT_TABS_SELECT_ALL_DRP_CHECKBOX, drpRole));
        if (drpRoleCheckbox == null)
            throw new AutomationException("'" + drpRole + "' checkbox not displayed on reports tab");
        WebElement drpRoleCheckBoxInput = driverUtil.getElementUsingJavascript("//*[contains(text(),'PCP')]/..//ancestor::th//span[contains(@class,'checkbox')]/..//input");
        if (!drpRoleCheckBoxInput.isSelected())
            driverUtil.clickUsingJavaScript(String.format(REPORT_TABS_SELECT_ALL_DRP_CHECKBOX, drpRole));
        waitForLoadingPage();
    }

    public void verifyGeneratedReportRoleInTable(String report, String role) throws AutomationException {
        waitForLoadingPage();
        WebElement generatedReportRole = driverUtil.getWebElement(String.format(GENERATED_REPORT_ROLE, report));
        String reportRole = generatedReportRole.getText();

        if (!reportRole.equalsIgnoreCase(role))
            throw new AutomationException("Generated report '" + report + "' is not displayed in table or role is displayed '" + role + "' but we expect it as '" + role + "'");
    }

    public void verifyGeneratedReportPractitionerInTable(String report, String practitioner) throws AutomationException {
        waitForLoadingPage();
        WebElement generatedReportPractitioner = driverUtil.getWebElement(String.format(GENERATED_REPORT_PRACTITIONER, report));
        String practitionerName = generatedReportPractitioner.getText();

        if (!practitionerName.equalsIgnoreCase(practitioner))
            throw new AutomationException("Generated report '" + report + "' is not displayed in table or practitioner is displayed '" + practitionerName + "' but we expect it as '" + practitioner + "'");
    }

    public void verifyReportsTableInGlobalReportsTab() throws AutomationException {
        waitForLoadingPage();
        WebElement element = driverUtil.getWebElement(REPORTS_TABLE_IN_GLOBAL_REPORTS_TAB);
        if (element == null)
            throw new AutomationException(String.format("Reports table not displayed in global reports tab"));
    }

    public void verifyWhenClearSigAlsoRemoveTheSpanishTranslation() throws AutomationException {
        WebElement sigInput = driverUtil.findElementAndScroll(SIG_INPUT);
        if (sigInput == null)
            throw new AutomationException("Sig input box not displayed on screen");
        sigInput.click();
        sigInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        WebDriverUtil.waitForAWhile();

        WebElement sigTextSpanish = driverUtil.getWebElementAndScroll(MEDICATION_SIG_TEXT_SPANISH_INPUT, WebDriverUtil.NO_WAIT);
        if (sigTextSpanish == null)
            throw new AutomationException("Sig text spanish text box not present");
        String spanishTranslate = CommonUtil.getUTF8String(sigTextSpanish.getAttribute("value"));
        System.out.println("Spanish text value= " + spanishTranslate);
        if (!spanishTranslate.isEmpty())
            throw new AutomationException("Sig spanish value visible on spanish text box, but we expect it should not visible!");
    }

    public void selectLabNameFromLabNameDropDown(String name) throws AutomationException {
        driverUtil.getWebElement(SEARCH_LAB_NAME).sendKeys(name);
        WebDriverUtil.waitForAWhile(WebDriverUtil.WAIT_2_SEC);
    }

    public void verifyOptionsInDropDownPresentInAddNewLabPoUp(List<String> dropDownOption) throws AutomationException {
        WebElement dropDown = driverUtil.getWebElement(ADD_NEW_LAB_SOURCE_DROPDOWN);
        if (dropDown == null)
            throw new AutomationException(String.format("Source dropDown not displayed on screen"));
        dropDown.click();
        for (String dropDownOptions : dropDownOption) {
            WebElement dropDownAvailableOption = driverUtil.findElement(String.format(SOURCE_DROPDOWN_OPTIONS, dropDownOptions));
            if (dropDownAvailableOption == null)
                throw new AutomationException(String.format("column %s not displayed in source dropdown", dropDownOptions));
        }
    }

    public void verifyOptionPresentInSpecialtyDropDown(String specialty) throws AutomationException {
        WebElement SpecialtyDropdownInput = driverUtil.getWebElementAndScroll(SPECIALTY_DROPDOWN_BUTTON);
        if (SpecialtyDropdownInput == null)
            throw new AutomationException("Specialty DropDown not present on Screen");

        String selectedValue = SpecialtyDropdownInput.getAttribute("value");
        if (selectedValue == null || selectedValue.isEmpty()) {
            SpecialtyDropdownInput.click();
        }
        if (selectedValue != null || !selectedValue.isEmpty()) {
            SpecialtyDropdownInput.click();
            SpecialtyDropdownInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            WebDriverUtil.waitForAWhile();
            SpecialtyDropdownInput.click();
        }

        WebElement stateDropdownOptions = driverUtil.findElement(String.format(SPECIALTY_DROPDOWN_OPTIONS, specialty));
        if (stateDropdownOptions == null)
            throw new AutomationException(String.format("specialty %s Option not present in specialty DropDown", specialty));
        takeScreenshot();
        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void verifyOptionPresentInRoleDropDown(String role) throws AutomationException {
        WebElement roleDropdown = driverUtil.getWebElementAndScroll(PRACTITIONER_ROLE_SELECT);
        if (roleDropdown == null)
            throw new AutomationException("Role DropDown not present on Screen");
        roleDropdown.click();
        WebElement roleDropdownOptions = driverUtil.getWebElement(String.format(ROLE_DROPDOWN_OPTIONS, role));
        if (roleDropdownOptions == null)
            throw new AutomationException(String.format("Role %s Option not present in DropDown", role));
        takeScreenshot();
    }

    public int getReportPDFPageCount(String fileName) throws AutomationException {
        boolean isFileFound = false;
        int counter = 0;
        int pageCount = 0;
        File[] files = null;
        do {
            try {
                files = FileUtil.getAllFiles(((System.getProperty(OS) == null || System.getProperty(OS) == WINDOWS) ? System.getProperty("user.dir") : System.getProperty("user.dir").replace("\\", "/")) + File.separator + "downloads");
                System.out.println("Iterating over files");
                for (File file : files) {
                    if (file.exists() && !file.isDirectory()) {
                        System.out.println(file.getName());
                        if (file.getName().toLowerCase().contains(fileName.toLowerCase())) {
                            isFileFound = true;
                            File downloadedFile = new File(((System.getProperty(OS) == null || System.getProperty(OS) == WINDOWS) ? System.getProperty("user.dir") : System.getProperty("user.dir").replace("\\", "/")) + "/" + "downloads" + "/" + file.getName());
                            FileInputStream fis = new FileInputStream(downloadedFile);
                            PdfReader pdfDocs = new PdfReader(fis);
                            pageCount = pdfDocs.getNumberOfPages();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter++;
        } while (!isFileFound && counter < 25);
        System.out.println("Total number of pages: " + pageCount);
        return pageCount;
    }

    public void addressPageDoesNotContainsPageNumber(int pageCount) throws AutomationException {
        WebDriverUtil.waitForAWhile(5, TimeUnit.SECONDS);
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        List<String> pages = PDFBoxReadText.readTextFromPdfFile(pdfContents);
        StringBuilder pdfText = new StringBuilder(0);
        int textCount = 0;

        CommonSteps.REPORT_LOGGER.log("Report data:");
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        for (String page : pages) {
            page = page.replaceAll("\r\n", " ");
            page = page.replaceAll("\n", " ");
            page = page.replaceAll("\r", " ");
            CommonSteps.REPORT_LOGGER.log(page);
            pdfText.append(page);
            if (page.contains("Page")) {
                textCount++;
            }
        }
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        CommonSteps.CURRENT_STEP_MESSAGE.set(pdfText.toString());

        System.out.println("Page Text Count in Report= " + textCount);
        if (textCount != (pageCount - 1) || !(textCount >= (0)))
            throw new AutomationException(String.format("Page Number is being displayed in reports Address page but it should not be there!"));
    }

    public void verifySearchedPatientOrganizationNameInAccountDropDown(String orgName) throws AutomationException {
        waitForLoadingPage();
        WebElement accountDropdown = driverUtil.getWebElement(HomePage.ACCOUNT_DROPDOWN);
        Select select = new Select(accountDropdown);
        WebElement accountDropDownOrg = select.getFirstSelectedOption();
        String defaultSelectedOrg = accountDropDownOrg.getText();
        if (!defaultSelectedOrg.equals(orgName))
            throw new AutomationException(String.format("Searched Patient is belongs to %s Organization but in account dropdown %s organization is visible", orgName, defaultSelectedOrg));
    }


    public void verifySearchedCountMarchedWithSearchedPatientRecord(String patientName) throws AutomationException {
        WebElement lastNameInputBox = driverUtil.getWebElement(ADVANCE_SEARCH_LAST_NAME_INPUT);
        String lastNameInput = lastNameInputBox.getAttribute("value");
        List<WebElement> listOfTasks = driverUtil.getWebElements(String.format(ADVANCE_SEARCH_PATIENT_COUNT));
        String lastNameSearchedDetail = "Showing  " + listOfTasks.size() + " results for Last name: " + patientName;
        String firstNameSearchedDetail = "Showing  " + listOfTasks.size() + " results for First name: " + patientName;
        System.out.println("lastNameInput " + lastNameInput);
        if (!lastNameInput.isEmpty()) {
            WebElement element = driverUtil.findElementAndScroll(String.format(LoginPage.VERIFY_TEXT, lastNameSearchedDetail));
            if (element == null)
                throw new AutomationException("Unable to find '" + lastNameSearchedDetail + "' on screen!");
        } else {
            WebElement element = driverUtil.findElementAndScroll(String.format(LoginPage.VERIFY_TEXT, firstNameSearchedDetail));
            if (element == null)
                throw new AutomationException("Unable to find '" + firstNameSearchedDetail + "' on screen!");
        }
    }

    public void verifyGeneratedReportTypeInTable(String report, String type) throws AutomationException {
        waitForLoadingPage();
        WebElement generatedReportRole = driverUtil.getWebElement(String.format(GENERATED_REPORT_TYPE, report));
        String reportRole = generatedReportRole.getText();

        if (!reportRole.equalsIgnoreCase(type))
            throw new AutomationException("Generated report '" + report + "' is not displayed in table or type is displayed '" + type + "' but we expect it as '" + type + "'");
    }

    public void selectDRPFromDRPTable(String drpName) throws AutomationException {
        List<WebElement> checkboxes = driverUtil.getWebElements(String.format(DRP_RECORD_CHECKBOX, drpName));
        WebDriverUtil.waitForAWhile();
        if (checkboxes != null) {
            for (int i = 1; i <= checkboxes.size(); i++) {
                WebElement checkboxChecked = driverUtil.getWebElement("//span[text()='" + drpName + "']/../..//input[not(@checked)]//following-sibling::span[contains(@class,'MakeReportTable-__checkbox')]");
                if (checkboxChecked != null) {
                    checkboxChecked.click();
                }
            }
        }
        waitForLoadingPage();
    }

    public void deselectReportDRP(String drpName) throws AutomationException {
        List<WebElement> checkboxes = driverUtil.getWebElements(String.format(REPORT_DRP_RECORD_CHECKED_CHECKBOX, drpName));
        WebDriverUtil.waitForAWhile();
        if (checkboxes != null) {
            for (int i = 1; i <= checkboxes.size(); i++) {
                WebElement checkboxChecked = driverUtil.getWebElement("//span[text()='" + drpName + "']/../..//input[@checked]//following-sibling::span[contains(@class,'MakeReportTable-__checkbox')]");
                if (checkboxChecked != null) {
                    checkboxChecked.click();
                }
            }
        }
        waitForLoadingPage();
    }

    public void verifyGeneratedReportLanguageInTable(String report, String language) throws AutomationException {
        waitForLoadingPage();
        WebElement generatedReportRole = driverUtil.getWebElement(String.format(GENERATED_REPORT_LANGUAGE_COLUMN, report));
        String reportRole = generatedReportRole.getText();
        if (!reportRole.equalsIgnoreCase(language))
            throw new AutomationException("Generated report '" + report + "' is not displayed in table or report language is displayed '" + language + "' but we expect it as '" + language + "'");
    }

    public void verifyReportLanguage(String reportLanguage) throws AutomationException {
        WebElement reportDataObject = driverUtil.getWebElement(REPORT_PDF_DATA);
        byte[] pdfContents = reportDataObject.getAttribute("data").getBytes(StandardCharsets.UTF_8);
        List<String> pages = PDFBoxReadText.readTextFromPdfFile(pdfContents);
        StringBuilder pdfText = new StringBuilder(0);
//        boolean result = Boolean.parseBoolean("true");
        CommonSteps.REPORT_LOGGER.log("Report data:");
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        for (String page : pages) {
            page = page.replaceAll("\r\n", " ");
            page = page.replaceAll("\n", " ");
            page = page.replaceAll("\r", " ");
            CommonSteps.REPORT_LOGGER.log(page);
            pdfText.append(page);
        }
        CommonSteps.REPORT_LOGGER.log("---------------------------------------------");
        CommonSteps.CURRENT_STEP_MESSAGE.set(pdfText.toString());
        boolean result = Boolean.parseBoolean("true");
//        Pattern pt = Pattern.compile("^[a-zA-Z0-9$&+,:;=?@#|'<>.*-_^*()%!\\s•‣◦■®]*$");
        Pattern pt = Pattern.compile("^[a-zA-Z0-9$&+,:;=?@#|'\\-_^*()%!\\s•‣◦■®\\n\\t]*$");
        Matcher mt = pt.matcher(pdfText);
        result = mt.matches();
        System.out.println("Matches Result " + result);
        if (!result)
            throw new AutomationException(String.format("We expected that the report's format would be in %s, but it isn't.", reportLanguage));
    }

    public void advanceSearchByBirthDate(String input) throws AutomationException {
        WebElement dobInput = driverUtil.findElement(ADVANCE_SEARCH_BIRTH_DATE_INPUT);
        if (dobInput == null)
            throw new AutomationException("Unable to locate Birth Date input in advance search form!");
        dobInput.sendKeys(input, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void verifyAlertMessageForInvalidBirthDateFormat(String alertMassage) throws AutomationException {
        WebElement firstNameInput = driverUtil.findElement(ADVANCE_SEARCH_BIRTH_DATE_INPUT);
        firstNameInput.sendKeys(Keys.ENTER);
        String message = firstNameInput.getAttribute("validationMessage");
        System.out.println("message= " + message);
        if (!message.trim().equalsIgnoreCase(alertMassage))
            throw new AutomationException("Unable to locate alert popup message for Invalid date format!");
    }

    public void searchPatientByPhoneNumber(String phone) throws AutomationException {
        WebElement phoneInput = driverUtil.getWebElement(SEARCH_PATIENT_PHONE_INPUT);
        if (phoneInput == null)
            throw new AutomationException("Unable to locate Phone input in advance search form!");
        phoneInput.clear();
        phoneInput.sendKeys(phone, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByLastName(String lastName) throws AutomationException {
        WebElement lastNameInput = driverUtil.getWebElement(SEARCH_PATIENT_LAST_NAME_INPUT);
        if (lastNameInput == null)
            throw new AutomationException("Unable to locate Last Name input in advance search form!");
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByFirstName(String firstName) throws AutomationException {
        WebElement firstNameInput = driverUtil.getWebElement(SEARCH_PATIENT_FIRST_NAME_INPUT);
        if (firstNameInput == null)
            throw new AutomationException("Unable to locate first Name input in advance search form!");
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByHPIDName(String hpID) throws AutomationException {
        WebElement hpIDInput = driverUtil.getWebElement(SEARCH_PATIENT_HPID_INPUT);
        if (hpIDInput == null)
            throw new AutomationException("Unable to locate HPID input in advance search form!");
        hpIDInput.clear();
        hpIDInput.sendKeys(hpID, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByZipCode(String hpID) throws AutomationException {
        WebElement zipCodeInput = driverUtil.getWebElement(SEARCH_PATIENT_ZIP_CODE_INPUT);
        if (zipCodeInput == null)
            throw new AutomationException("Unable to locate ZipCode input in advance search form!");
        zipCodeInput.clear();
        zipCodeInput.sendKeys(hpID, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByStateName(String stateName) throws AutomationException {
        WebElement stateNameInput = driverUtil.getWebElement(SEARCH_PATIENT_STATE_NAME_INPUT);
        if (stateNameInput == null)
            throw new AutomationException("Unable to locate state Name input in advance search form!");
        stateNameInput.clear();
        stateNameInput.sendKeys(stateName, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void searchPatientByCityName(String cityName) throws AutomationException {
        WebElement cityNameInput = driverUtil.getWebElement(SEARCH_PATIENT_CITY_NAME_INPUT);
        if (cityNameInput == null)
            throw new AutomationException("Unable to locate City Name input in advance search form!");
        cityNameInput.clear();
        cityNameInput.sendKeys(cityName, Keys.ENTER);
        driverUtil.waitForElementToBeClickable(ADVANCE_SEARCH_DATA_ICON);
    }

    public void verifyAlertMessageForInvalidPatientInfoFormat(String alertMassage, String field) throws AutomationException {
        WebElement firstNameInput = driverUtil.findElement(String.format(ADVANCE_SEARCH_POPUP_INPUT, field));
        firstNameInput.sendKeys(Keys.ENTER);
        String message = firstNameInput.getAttribute("validationMessage");
        System.out.println("message= " + message);
        if (!message.trim().equalsIgnoreCase(alertMassage))
            throw new AutomationException("Unable to locate alert popup message for Invalid " + field + " format!");
    }

    public void verifyAdvanceSearchResultCount(String greaterThanOrLessThan, int noOfResult) throws AutomationException {
        List<WebElement> listOfTasks = driverUtil.getWebElements(String.format(ADVANCE_SEARCH_PATIENT_COUNT));
        Integer searchResultCount = listOfTasks.size();
        try {
            switch (greaterThanOrLessThan.toUpperCase()) {
                case "GREATER":
                    if (searchResultCount <= noOfResult)
                        throw new AutomationException("Searched result number of count expected '" + greaterThanOrLessThan + "' than '" + noOfResult + "' but its not!");
                    break;
                case "LESS":
                    if (searchResultCount >= noOfResult)
                        throw new AutomationException("Searched result number of count expected '" + greaterThanOrLessThan + "' than '" + noOfResult + "' but its not!");
                    break;
            }
        } catch (Exception ex) {
            throw new AutomationException(ex.getMessage());
        }
    }

    public void clickOnPatientSearchIcon() throws AutomationException {
        WebElement dropDown = driverUtil.getWebElement(SEARCH_PATIENT_ICON);
        if (dropDown == null)
            throw new AutomationException(String.format("Patient Search Icon not displayed on screen"));
        dropDown.click();
        waitForLoadingPage();
    }

    public void verifyOptionsInLabPoUp(String dropDownName) throws AutomationException {
        WebElement dropDownWebElement;
        switch (dropDownName.toUpperCase()) {
            case "PROVIDER":
                dropDownWebElement = driverUtil.getWebElement(PROVIDER_DROPDOWN_INPUT);
                if (dropDownWebElement == null)
                    throw new AutomationException(String.format("Unable to locate %s search input/Dropdown!", dropDownName));
                break;
            default:
                throw new AutomationException("Invalid Search Input.");
        }
        driverUtil.getWebElement(String.format(PATIENT_BUTTON, "Cancel")).click();
    }

    public void verifyOptionsNotPresentInLabPoUp(String dropDownName) throws AutomationException {
        WebElement dropDownWebElement;
        switch (dropDownName.toUpperCase()) {
            case "PROVIDER":
                dropDownWebElement = driverUtil.getWebElement(PROVIDER_DROPDOWN_INPUT);
                if (dropDownWebElement != null)
                    throw new AutomationException(String.format(" %s search input/Dropdown present on popup but it should not be present!", dropDownName));
                break;
            default:
                throw new AutomationException("Invalid Search Input.");
        }
//        driverUtil.getWebElement(String.format(PATIENT_BUTTON, "Cancel")).click();
    }

    public void verifyDropDownOptionsPresentInMedlistTab(String dropDownName, String medication) throws AutomationException {
        ArrayList<String> labTabProviderDropdownOptions = new ArrayList<>();
        ArrayList<String> medListTabProviderDropdownOptions = new ArrayList<>();
        WebElement dropDownWebElement;
        dropDownWebElement = driverUtil.getWebElement(PROVIDER_DROPDOWN_INPUT);
        if (dropDownWebElement == null)
            throw new AutomationException(String.format(" %s search input/Dropdown present on popup but it should not be present!", dropDownName));
        dropDownWebElement.click();
        List<WebElement> labTableOptions = driverUtil.getWebElements(PROVIDER_DROPDOWN_OPTIONS_IN_LAB_TAB);
        for (WebElement option : labTableOptions) {
            labTabProviderDropdownOptions.add(option.getText());
        }

        driverUtil.getWebElement(String.format(PATIENT_BUTTON, "Cancel")).click();
        driverUtil.waitForLoadingPage();

        //Go To The Med List Tab And Select Created Medication
        PageFactory.patientPage().clickOnGlobalTab("Med List");
        driverUtil.waitForLoadingPage();
        PageFactory.medListPage().selectMed(medication);

        dropDownWebElement = driverUtil.getWebElement(PageFactory.medListPage().PRESCRIBER_DROPDOWN);
        if (dropDownWebElement == null)
            throw new AutomationException(String.format(" %s search input/Dropdown present in medlist Tab but it should not be present!", dropDownName));
        dropDownWebElement.click();
        List<WebElement> medListTabOptions = driverUtil.getWebElements(PROVIDER_DROPDOWN_OPTIONS_IN_MED_LIST_TAB);
        driverUtil.waitForLoadingPage();
        for (WebElement medListTabOption : medListTabOptions) {
            medListTabProviderDropdownOptions.add(medListTabOption.getText());
            if (medListTabOption == null)
                throw new AutomationException("Prescriber not found in drop down or drop down not open");
        }

        for (int i = 0; i < labTabProviderDropdownOptions.size(); i++) {
            String str = labTabProviderDropdownOptions.get(i);
            str = str.trim().replaceAll("-", " ");
            str = str.trim().replaceAll("\\s+", " ");
            labTabProviderDropdownOptions.set(i, str);
        }
        labTabProviderDropdownOptions.removeIf(String::isBlank);
        labTabProviderDropdownOptions.sort(null);

        for (int i = 0; i < medListTabProviderDropdownOptions.size(); i++) {
            String str = medListTabProviderDropdownOptions.get(i);
            str = str.trim().replaceAll("-", " ");
            str = str.trim().replaceAll("\\s+", " ");
            medListTabProviderDropdownOptions.set(i, str);
        }

        medListTabProviderDropdownOptions.removeIf(String::isBlank);
        medListTabProviderDropdownOptions.sort(null);

        System.out.println("labTabProviderDropdownOptions== " + labTabProviderDropdownOptions);
        System.out.println("medListTabProviderDropdownOptions== " + medListTabProviderDropdownOptions);
        if (!labTabProviderDropdownOptions.equals(medListTabProviderDropdownOptions))
            throw new AutomationException("Prescriber options are not same in med List tab practitioners drop down and Lab tab practitioners drop down");
    }

    public void selectPrescriberFromAddNewLabPopup(String Prescriber) throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(PROVIDER_DROPDOWN_INPUT);
        if (dropdown == null)
            throw new AutomationException("Unable to locate PROVIDER dropdown!");
        dropdown.click();
        driverUtil.waitForAWhile(1);
        WebElement element = driverUtil.getWebElement("//*[@role='option' and text()='" + Prescriber + "']", 3);
        if (element == null)
            throw new AutomationException("Prescriber :" + Prescriber + " not found in drop down or drop down not open");
        driverUtil.waitForAWhile(3);
        element.click();
        driverUtil.waitForAWhile(3);
    }

    public void verifyUserAbleToDeleteAndUpdatePatientsPlan() throws AutomationException {
        WebElement planInput = driverUtil.getWebElementAndScroll(PATIENT_PLAN_INPUT);
        if (planInput == null)
            throw new AutomationException("Unable to find patient's Plan Input box!");
        String plan = planInput.getAttribute("value");
        System.out.println("Plan = " + plan);
        planInput.click();
        planInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        WebDriverUtil.waitForAWhile(5);
        planInput.sendKeys(plan, Keys.ENTER);
    }


    public void verifyUserNotAbleToDeleteAndUpdatePatientsPlanItShouldDisable() throws AutomationException {
        WebElement planInput = driverUtil.getWebElement(PATIENT_PLAN_INPUT_DISABLED);
        if (planInput == null)
//            throw new AutomationException("Unable to find patient's Plan Input box!");
//        String plan = planInput.getAttribute("class");
//        System.out.println("Plan = " + plan);
//        if (!plan.contains("disabled"))
            throw new AutomationException("patient's Plan Input box is Editable but it should disable!");
    }


    public void userNotAbleToLockAnotherUsersComment(String comment) throws AutomationException {
        driverUtil.waitForLoadingPage();
        selectComment(comment);
        WebElement commentLockIcon = driverUtil.getWebElement(String.format(LOCKED_COMMENT_BUTTON, comment));
        Actions action = new Actions(DriverFactory.drivers.get());
        action.moveToElement(commentLockIcon).perform();
        String commentLockIconClass = commentLockIcon.getAttribute("class").toLowerCase();
        System.out.println("commentLockIconClass== " + commentLockIconClass);
        if (!commentLockIconClass.contains("disabled"))
            throw new AutomationException("Comment Lock Icon is clickable but it should disable!");
    }


    public void verifyDRPPriorityIconsInDRPTable(String icon, String DRPName, String toolTipMessage) throws AutomationException {
        Actions action = new Actions(DriverFactory.drivers.get());
        switch (icon.toLowerCase()) {
            case "alert triangle":
                WebElement triangleIcon = driverUtil.getWebElement(String.format(DRPS_TRIANGLE_ICON_IN_DRP_TABLE, DRPName));
                if (triangleIcon == null)
                    throw new AutomationException("Alert Triangle Icon Not Visible In DRP Table OR Required DRP Not Present in table");
                action.moveToElement(triangleIcon).perform();
                String triangleIconTooltipMessage = triangleIcon.getAttribute("data-tip");
                System.out.println("triangleIconTooltipMessage== " + triangleIconTooltipMessage);
                if (!triangleIconTooltipMessage.equalsIgnoreCase(toolTipMessage))
                    throw new AutomationException("Alert Triangle Icon message should display: '" + toolTipMessage + "' but in actual it shows: " + triangleIconTooltipMessage);
                break;
            case "lightning bolt":
                WebElement lightningBoltIcon = driverUtil.getWebElement(String.format(DRPS_LIGHTNING_BOLT_ICON_IN_DRP_TABLE, DRPName));
                if (lightningBoltIcon == null)
                    throw new AutomationException("lightning bolt Icon Not Visible In DRP Table OR Required DRP Not Present in table");
                action.moveToElement(lightningBoltIcon).perform();
                String lightningBoltIconTooltipMessage = lightningBoltIcon.getAttribute("data-tip");
                System.out.println("lightningBoltIconTooltipMessage== " + lightningBoltIconTooltipMessage);
                if (!lightningBoltIconTooltipMessage.equalsIgnoreCase(toolTipMessage))
                    throw new AutomationException("lightning bolt Icon message should display: '" + toolTipMessage + "' but in actual it shows: " + lightningBoltIconTooltipMessage);
                break;
        }
        PageFactory.patientPage().clickOnNameLink();
    }

    public void verifyDRPPriorityToggleKeyFunctionality(String toolTipMessage) throws AutomationException {
        Actions action = new Actions(DriverFactory.drivers.get());
        WebElement drpIfoToggleKyeIcon = driverUtil.getWebElement(SHOW_ONLY_HIGH_OR_ROI_DRPS_ICON);
        if (drpIfoToggleKyeIcon == null)
            throw new AutomationException("DRP Priority Toggle Key Icon Not Visible In DRP Table");
        action.moveToElement(drpIfoToggleKyeIcon).perform();
        String drpIfoToggleKyeIconTooltipMessage = drpIfoToggleKyeIcon.getAttribute("data-tip");
        System.out.println("commentLockIconClass== " + drpIfoToggleKyeIconTooltipMessage);
        if (!drpIfoToggleKyeIconTooltipMessage.equalsIgnoreCase(toolTipMessage)) {
            drpIfoToggleKyeIcon.click();
            action.moveToElement(drpIfoToggleKyeIcon).perform();
            drpIfoToggleKyeIconTooltipMessage = drpIfoToggleKyeIcon.getAttribute("data-tip");
        }
        System.out.println("commentLockIconClass== " + drpIfoToggleKyeIconTooltipMessage);
        if (!drpIfoToggleKyeIconTooltipMessage.equalsIgnoreCase(toolTipMessage))
            throw new AutomationException("DRP Priority Toggle Key Icon message should display: '" + toolTipMessage + "' but in actual it shows: " + drpIfoToggleKyeIconTooltipMessage);
    }

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

    public void clickOnUserIsPresentInProfileSelectionPopup(String patientName) throws AutomationException {
        WebElement patientRecordInTable = driverUtil.getWebElement(String.format(PATIENT_RECORD_IN_PROFILE_SELECTION_RECORD_TABLE, patientName));
        if (patientRecordInTable == null)
            throw new AutomationException("Multiple Patient Users Record not Visible In Patient info tables");
        patientRecordInTable.click();
        driverUtil.waitForLoadingPage();
    }

    public void userClosesTheModalByClickingOn(String button) throws AutomationException {
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

    public void deleteAllMedicationFromMedListTab() throws AutomationException {
        List<WebElement> medicationRecordDeleteIcons = driverUtil.getWebElements(DELETE_MED_ICON_BUTTON);
        if (medicationRecordDeleteIcons != null) {
            for (WebElement medication : medicationRecordDeleteIcons) {
                WebDriverUtil.waitForAWhile();
                driverUtil.moveToElementAndClick(medication);
                WebElement deleteConfirmationButton = driverUtil.getWebElement(String.format(DELETE_CONFIRMATION_POPUP_DELETE_BUTTON, "Delete this medication"));
                if (deleteConfirmationButton == null)
                    throw new AutomationException("Unable to find delete med button on confirmation popup or it might taking too long time to load!");
                deleteConfirmationButton.click();
                waitForLoadingPage();
            }
        }
    }


    public void verifyNewlyCreatedLogActionGeneratedOnCurrentDateAndTime(String identifier) throws AutomationException {
        verifyNewlyCreatedLogAction(identifier);
        WebElement logFirstDateRecord = driverUtil.getWebElement(TIMELINE_TABLE_FIRST_DATE_RECORD);
        if (logFirstDateRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created log action on first index & unable to find newly created task for Patient: %s", identifier));
        String dateText = logFirstDateRecord.getText();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
//        String CurrentDateTime = LocalDateTime.now().format(format);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime uiDateTime = LocalDateTime.parse(dateText, format);
        long differenceInMinutes = ChronoUnit.MINUTES.between(uiDateTime, currentDateTime);

        System.out.println("CurrentDateTime = " + currentDateTime);
        System.out.println("UI DateTime = " + dateText);
        System.out.println("Difference in Minutes = " + differenceInMinutes);

//        if(!dateText.equalsIgnoreCase(CurrentDateTime))
        // Allow a tolerance of ±1 minute
        if (Math.abs(differenceInMinutes) > 5) {
            throw new AutomationException(String.format("We supposed to get newly created log action Current Date is: %s but we get %s", currentDateTime, dateText));
        }
    }

    public void verifyNewlyCreatedPractitionersLogActionGeneratedOnCurrentDateAndTime(String identifier) throws AutomationException {
        prescriberAnalyticsPage().verifyNewlyCreatedLogActionForPAFeature(identifier);
        WebElement logFirstDateRecord = driverUtil.getWebElement(PRACTITIONERS_TIMELINE_TABLE_FIRST_DATE_RECORD);
        if (logFirstDateRecord == null)
            throw new AutomationException(String.format("We supposed to get newly created log action on first index & unable to find newly created task for Patient: %s", identifier));
        String dateText = logFirstDateRecord.getText();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime uiDateTime = LocalDateTime.parse(dateText, format);
        long differenceInMinutes = ChronoUnit.MINUTES.between(uiDateTime, currentDateTime);

        System.out.println("CurrentDateTime = " + currentDateTime);
        System.out.println("UI DateTime = " + dateText);
        System.out.println("Difference in Minutes = " + differenceInMinutes);

        if (Math.abs(differenceInMinutes) > 6) {
            throw new AutomationException(String.format("We supposed to get newly created log action Current Date is: %s but we get %s", currentDateTime, dateText));
        }
    }

    public void userAddsThePatientsPhoneNumberAs(String phoneNumber) throws AutomationException {
        WebElement patientPhoneNumber = driverUtil.getWebElement(String.format(PATIENT_PHONE_NUMBER, phoneNumber));
        if (patientPhoneNumber == null) {
            WebElement addPhoneNumberIcon = driverUtil.getWebElement(ADD_PATIENT_PHONE_ICON);
            if (addPhoneNumberIcon == null)
                throw new AutomationException(String.format("Unable to find add patient phone icon in patient phone table"));
            addPhoneNumberIcon.click();
            driverUtil.waitForLoadingPage();
            common.verifyTextOnScreen("Manage Phone Numbers");
            WebElement addPhoneNumberInput = driverUtil.getWebElement(ADD_PATIENT_PHONE_INPUT);
            if (addPhoneNumberInput == null)
                throw new AutomationException(String.format("Unable to find add patient phone input in manage phone popup"));
            addPhoneNumberInput.click();
            addPhoneNumberInput.sendKeys(phoneNumber);

            List<WebElement> elements = driverUtil.getWebElements(ADD_PHONE_NUMBER_ACTION_ICONS_SAVE_OR_EXIT);
            System.out.println("Element Count= "+elements.size());

            try {
            for (WebElement element : elements) {
                waitForAWhile(2);

                Actions actions = new Actions(DriverFactory.drivers.get());
                actions.moveToElement(element).perform();
                WebElement tooltip = driverUtil.getWebElement("//*[contains(@class,'Tooltip') and text()='Save']");
                if (tooltip.isDisplayed()) {
                    element.click();
                    break;
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            PageFactory.patientPage().clickOnButton("Save");
        }
    }

    public void userVerifiesThePatientsPhoneNumberIs(String phoneNumber) throws AutomationException {
        WebElement patientPhoneNumber = driverUtil.getWebElement(String.format(PATIENT_PHONE_NUMBER, phoneNumber));
        if (patientPhoneNumber == null)
            throw new AutomationException(String.format("Unable to find add patient phone number in patient phone table"));
    }
}
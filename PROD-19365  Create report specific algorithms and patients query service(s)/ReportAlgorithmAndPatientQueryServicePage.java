package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PageFactory.prescriberAnalyticsPage;
import static com.arine.automation.pages.PatientPage.scrollToTop;
import static com.arine.automation.pages.PractitionersPage.DRPS_TABLE_CHECK_BOX;

public class ReportAlgorithmAndPatientQueryServicePage {

    public static final String ALGORITHM_DATA_LOCATOR = "(//*[contains(@class,'mantine-')]// div[text()='%s']/../following-sibling::td[3]//*)[1]";
    public static final String DATA_FROM_PATIENT_RECOMMENDATION = "//*[text()='Patient Recommendations:']/following::table/tbody/tr[%s]";
    public static final String ALL_PATIENT_RECOMMENDATION_FIELD_CHECKBOX = "//*[contains(text(),'Patient Recommendations')]/ following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select all')]";
    public static final String STEPS_PERFORMED_LOCATOR = "(//*[contains(@class,'PractitionerStoryTable') and //*[text()='Practitioner Timeline']]/..// table)// tr[1]/ td[6]";
    public Map<String, String> expectedPatientDataMap = new HashMap<>();
    public Map<String, String> actualPatientDataMap = new HashMap<>();

    public void extractPatientColumnData(String columnName, List<String> patientNames) throws AutomationException {
        String algorithmData;
        for (String patient : patientNames) {
            WebElement element = driverUtil.getWebElement(String.format(DRPS_TABLE_CHECK_BOX, patient), 10);
            if (element == null)
                throw new AutomationException("unable to locate checkbox on Reported DRPS Table ");
            else if (element.isSelected()) {
                WebElement algorithm = driverUtil.getWebElement(String.format(ALGORITHM_DATA_LOCATOR, patient), 10);
                if (algorithm == null)
                    throw new AutomationException("unable to locate " + patient + "'s " + columnName + " data on Reported DRPS Table ");
                algorithmData = algorithm.getText();
                expectedPatientDataMap.put(patient, algorithmData);
            }
        }
        PageFactory.patientPage().scrollToTop();
    }

    public void verifyPatientsColumnDataForRespectivePatient() throws AutomationException {
        actualPatientDataMap.clear();
        WebElement patientEle, algorithmEle;
        String actualPatientName, actualAlgorithmName;
        int tableDataCount = driverUtil.getWebElements("//*[text()='Patient Recommendations:']/following::table/tbody/tr").size();
        for (int i = 1; i <= tableDataCount; i++) {
            patientEle = driverUtil.getWebElement(String.format(DATA_FROM_PATIENT_RECOMMENDATION + "/td[2]", i), 5);
            if (patientEle == null)
                throw new AutomationException("Unable to locate Patient Name data field");
            actualPatientName = patientEle.getText();
            algorithmEle = driverUtil.getWebElement(String.format(DATA_FROM_PATIENT_RECOMMENDATION + "/td[3]", i), 5);
            if (algorithmEle == null)
                throw new AutomationException("Unable to locate Algorithm data field");
            actualAlgorithmName = algorithmEle.getText();
            actualPatientDataMap.put(actualPatientName, actualAlgorithmName);
        }
        boolean isEqual = expectedPatientDataMap.equals(actualPatientDataMap);
        if (!isEqual)
            throw new AutomationException("Actual Values Are Not Equal to Expected Values");
        expectedPatientDataMap.clear();
        actualPatientDataMap.clear();
    }

    public void userSelectsAllPatientRecommendations() throws AutomationException {
        driverUtil.waitForLoadingPage();
        WebElement selectButton = driverUtil.getWebElement(ALL_PATIENT_RECOMMENDATION_FIELD_CHECKBOX);
        if (selectButton == null)
            throw new AutomationException("Unable to find select ALL fields for Patient Recommendation Table");
        if (!selectButton.isSelected()) {
            driverUtil.waitForElementToBeClickable(ALL_PATIENT_RECOMMENDATION_FIELD_CHECKBOX);
            selectButton.click();
        }
    }

    public void verifyNewlyCreatedPractitionersLogActionIsGeneratedOnCurrentDateAndContains(String columnName, DataTable patientNames) throws AutomationException {
        WebElement logElement = driverUtil.getWebElement(STEPS_PERFORMED_LOCATOR);
        if (logElement == null)
            throw new AutomationException("Unable to find "+columnName+" log action field");
        String logText = logElement.getText();
        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        if (!logText.contains(currentDate)) {
            throw new AutomationException("Log date mismatch! Expected date: " + currentDate + " not found in log.");
        }
        List<String> expectedPatients = patientNames.asList(String.class);
        for (String patient : expectedPatients) {
            if (!logText.contains(patient)) {
                throw new AutomationException("Patient not found in log: " + patient);
            }
        }
    }

    public void verifyUserAbleToEnablePractitionerSearchFilterForReportHistoryTable(String practitioner, String columnName) throws AutomationException {
        waitForLoadingPage();
        scrollToTop();
        WebElement dotButton = driverUtil.findElement("//*[text()='Report History']//..//..//button[contains(@class,'mantine-ActionIcon') and @aria-haspopup='dialog']");
        if(dotButton!=null)
            dotButton.click();
        if (practitioner.equalsIgnoreCase("Current Date")) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String CurrentDate = LocalDateTime.now().format(format);
            prescriberAnalyticsPage().verifyUserAbleToEnablePractitionerSearchFiler(CurrentDate,columnName);
        }else{
            prescriberAnalyticsPage().verifyUserAbleToEnablePractitionerSearchFiler(practitioner,columnName);
        }
    }

    public void verifyUserAbleToClearColumnFilterOfReportHistoryTable(DataTable dataTable) throws AutomationException {
        waitForLoadingPage();
        scrollToTop();
        WebElement dotButton = driverUtil.findElement("//*[text()='Report History']//..//..//button[contains(@class,'mantine-ActionIcon') and @aria-haspopup='dialog']");
        if(dotButton!=null)
            dotButton.click();
        prescriberAnalyticsPage().verifyUserAbleToClearPractitionerTableColumnFilter(dataTable);
    }
}

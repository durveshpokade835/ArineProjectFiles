package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.PractitionersPage.DRPS_TABLE_CHECK_BOX;

public class ReportAlgorithmAndPatientQueryServicePage {

    public static final String ALGORITHM_DATA_LOCATOR = "(//*[contains(@class,'mantine-')]// div[text()='%s']/../following-sibling::td[3]//*)[1]";
    public static final String DATA_FROM_PATIENT_RECOMMENDATION = "//*[text()='Patient Recommendations:']/following::table/tbody/tr[%s]";
public static final String ALL_PATIENT_RECOMMENDATION_FIELD_CHECKBOX = "//*[contains(text(),'Patient Recommendations')]/ following::input[contains(@type,'checkbox') and contains(@aria-label,'Toggle select all')]";

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
            patientEle = driverUtil.getWebElement(String.format(DATA_FROM_PATIENT_RECOMMENDATION + "/td[2]", i),5);
            if (patientEle == null)
                throw new AutomationException("Unable to locate Patient Name data field");
            actualPatientName =patientEle.getText();
            algorithmEle = driverUtil.getWebElement(String.format(DATA_FROM_PATIENT_RECOMMENDATION + "/td[3]", i),5);
            if (algorithmEle == null)
                throw new AutomationException("Unable to locate Algorithm data field");
            actualAlgorithmName= algorithmEle.getText();
            actualPatientDataMap.put(actualPatientName, actualAlgorithmName);
//            if (!expectedPatientDataMap.get(actualPatientName).equals(actualAlgorithmName))
//                throw new AutomationException("Expected " + columnName + " '" + expectedPatientDataMap.get(actualPatientName) + "' not contains when mouse hover action perform on button: '" + actualPatientName + "'");
        }
        boolean isEqual = expectedPatientDataMap.equals(actualPatientDataMap);
        if(!isEqual)
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
}

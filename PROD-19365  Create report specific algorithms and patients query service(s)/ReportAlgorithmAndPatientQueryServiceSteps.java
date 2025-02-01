package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class ReportAlgorithmAndPatientQueryServiceSteps {

    CommonSteps common = new CommonSteps();

    @And("^User extracts patient \"([^\"]*)\" column data for following patient$")
    public void userExtractsPatientColumnDataForFollowingPatient(String columnName, List<String> patientNames) throws AutomationException {
        common.logInfo("User extracts patients "+columnName+"column data for respective patient"+patientNames.toString());
        PageFactory.reportAlgorithmAndPatientQueryServicePage().extractPatientColumnData(columnName,patientNames);
    }

    @Then("^Verify patients Algorithm column data for respective patient in patient Recommendation table$")
    public void verifyPatientsColumnDataForRespectivePatient() throws AutomationException {
        common.logInfo("Verify patients column data for respective patient in patient Recommendation table");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().verifyPatientsColumnDataForRespectivePatient();
        takeScreenshot();
    }

    @And("^selects All patient recommendations from the Report Discussion Selector table$")
    public void selectsAllPatientRecommendationsFromTheReportDiscussionSelectorTable() throws AutomationException {
        common.logInfo("selects patient recommendations from the Report Discussion Selector table");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().userSelectsAllPatientRecommendations();
        takeScreenshot();
    }

    @Then("^Verify newly created practitioners log action \"([^\"]*)\" is generated with current date and contains$")
    public void verifyNewlyCreatedPractitionersLogActionIsGeneratedOnCurrentDateAndContains(String columnName, DataTable patientNames) throws AutomationException {
        common.logInfo("Verify newly created practitioners log action "+columnName+" is generated with current date and contains"+patientNames.toString());
        PageFactory.reportAlgorithmAndPatientQueryServicePage().verifyNewlyCreatedPractitionersLogActionIsGeneratedOnCurrentDateAndContains(columnName,patientNames);
        takeScreenshot();
    }

    @Then("^Verify user able to enable column filter modes and search \"([^\"]*)\" in \"([^\"]*)\" column filter for Report History Table$")
    public void verifyUserAbleToEnableColumnFilterModesAndSearchInColumnFilterForReportHistoryTable(String practitioner,String columnName) throws AutomationException {
        common.logInfo("Verify user able to enable column filter modes and search ' "+ practitioner+" ' for Report History Table");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().verifyUserAbleToEnablePractitionerSearchFilterForReportHistoryTable(practitioner,columnName);
    }

    @Then("^Verify user able to clear following column filters inboxes of Report History Table$")
    public void verifyUserAbleToClearFollowingColumnFiltersInboxesOfReportHistoryTable(DataTable dataTable) throws AutomationException {
        common.logInfo("Verify user able to clear following column filters inboxes of Report History Table");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().verifyUserAbleToClearColumnFilterOfReportHistoryTable(dataTable);
    }
}

package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

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
        common.logInfo("Verify patients column data for respective patient");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().verifyPatientsColumnDataForRespectivePatient();
    }

    @And("^selects All patient recommendations from the Report Discussion Selector table$")
    public void selectsAllPatientRecommendationsFromTheReportDiscussionSelectorTable() throws AutomationException {
        common.logInfo("selects patient recommendations from the Report Discussion Selector table");
        PageFactory.reportAlgorithmAndPatientQueryServicePage().userSelectsAllPatientRecommendations();
        takeScreenshot();
    }
}

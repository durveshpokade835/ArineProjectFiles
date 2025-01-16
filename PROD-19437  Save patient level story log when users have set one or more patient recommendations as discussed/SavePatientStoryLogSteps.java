package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import com.aspose.pdf.Page;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.text.ParseException;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class SavePatientStoryLogSteps {
    CommonSteps common = new CommonSteps();
//    @And("^Click on log action icon button from report history table$")
//    public void clickOnLogActionIconFromReportHistoryTable() throws AutomationException, ParseException {
//        common.logInfo("Click on log action button from report history table");
//        PageFactory.patientStoryLogPage().clickOnLogActionIconFromReportHistoryTable();
//        takeScreenshot();
//    }

    @Then("^User update log action with below information:$")
    public void userUpdateLogActionWithBelowInformation(DataTable data) throws AutomationException {
        common.logInfo("Create new log action with below information: "+data.toString());
        PageFactory.patientStoryLogPage().updateLogAction(data);
        takeScreenshot();
    }

    @And("^selects patient recommendations from the Report Discussion Selector table$")
    public void selectsPatientRecommendationsFromTheReportDiscussionSelectorTable() throws AutomationException {
        common.logInfo("selects patient recommendations from the Report Discussion Selector table");
        PageFactory.patientStoryLogPage().userSelectsPatientRecommendations();
        takeScreenshot();
    }

    @And("^clicks on the Log Action button and verify PopUp$")
    public void clicksOnTheLogActionButtonAndVerifyPopUp() throws AutomationException {
        common.logInfo("clicks on the Log Action button and verify PopUp");
        PageFactory.patientStoryLogPage().clickOnLogActionButton();
        takeScreenshot();
    }
}

package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class LogActionNoteSteps {
    CommonSteps common = new CommonSteps();
//    @Then("Verify User is able to log action for selected task \"([^\"]*)\" and Verify \"([^\"]*)\"$")
//    public void verifyUserIsAbleToLogActionForSelectedTaskAndVerify(String verificationText, String note) throws AutomationException {
//        common.logInfo(" Click on first records Fax icon from report sent table");
//        PageFactory.logActionNotePage().verifyUserIsAbleToLogActionForSelectedTaskAndVerify(verificationText,note);
//        takeScreenshot();
//    }

    @Then("^Sort column \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void sortColumnInOrder(String columnName, String order) {
        common.logInfo(" Sort column "+columnName+" in "+order+" order");
        PageFactory.logActionNotePage().sortColumnInOrder(columnName,order);
        takeScreenshot();
    }
    @And("^Verify last attempted by information updated accurately$")
    public void verifyLastAttemptedByInformationUpdatedAccurately() throws AutomationException {
        common.logInfo("Verify last attempted by field updated accurately");
        PageFactory.logActionNotePage().verifyLastAttemptedByInformationUpdatedAccurately();
        takeScreenshot();
    }

    @Then("^Click on newly created task for \"([^\"]*)\" in tasks tab$")
    public void clickOnNewlyCreatedTaskForInTasksTab(String identifier) throws AutomationException {
        common.logInfo("Click on newly created task for "+identifier+" in tasks tab");
        PageFactory.logActionNotePage().clickOnNewlyCreatedTaskForInTasksTab(identifier);
        takeScreenshot();
    }
}

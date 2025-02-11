package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class LogActionNoteSteps {

    CommonSteps common = new CommonSteps();

    @Then("^Sort column \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void sortColumnInOrder(String columnName, String order) {
        common.logInfo(" Sort column "+columnName+" in "+order+" order");
        PageFactory.logActionNotePage().sortColumnInOrder(columnName,order);
        takeScreenshot();
    }
    @And("^Verify last attempted by information updated accurately and verify \"([^\"]*)\"$")
    public void verifyLastAttemptedByInformationUpdatedAccurately(String userNameAndDate) throws AutomationException {
        common.logInfo("Verify last attempted by field updated accurately");
        PageFactory.logActionNotePage().verifyLastAttemptedByInformationUpdatedAccurately(userNameAndDate);
    }

    @Then("^Click on newly created task for \"([^\"]*)\" in tasks tab$")
    public void clickOnNewlyCreatedTaskForInTasksTab(String identifier) throws AutomationException {
        common.logInfo("Click on newly created task for "+identifier+" in tasks tab");
        PageFactory.logActionNotePage().clickOnNewlyCreatedTaskForInTasksTab(identifier);
        takeScreenshot();
    }
}

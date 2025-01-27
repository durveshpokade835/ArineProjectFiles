package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.text.ParseException;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class CustomFaxNumberForReportSteps {

    CommonSteps common = new CommonSteps();

    @And("^Click on first records fax icon from report sent table$")
    public void clickOnFaxIconFromReportSentTable() throws AutomationException, ParseException {
        common.logInfo(" Click on first records Fax icon from report sent table");
        PageFactory.customFaxNumberForReportPage().clickOnFaxIconFromReportSentTable();
        takeScreenshot();
    }

    @And("^Select \"([^\"]*)\" Fax Number to send a fax$")
    public void selectFaxNumberToSendAFax(String faxNumberField) throws AutomationException {
        common.logInfo("Select Fax Number to send a fax");
        PageFactory.customFaxNumberForReportPage().selectFaxNumberToSendFax(faxNumberField);
        takeScreenshot();
    }

    @Then("Enter \"([^\"]*)\" custom fax number")
    public void enterCustomFaxNumber(String customFaxNumber) throws AutomationException {
        common.logInfo("Enter custom fax number "+customFaxNumber+" to send a fax");
        PageFactory.customFaxNumberForReportPage().enterCustomFaxNumber(customFaxNumber);
        takeScreenshot();
    }
}

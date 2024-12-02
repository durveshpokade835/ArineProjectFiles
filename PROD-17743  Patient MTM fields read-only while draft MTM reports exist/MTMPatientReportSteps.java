package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class MTMPatientReportSteps {
    CommonSteps common = new CommonSteps();

    @Then("Verify fields are disabled and tooltip contains: {string}")
    public void verifyButtonIsDisabledAndToolTipMessage( String tooltipMessage,DataTable dataTable) throws AutomationException {
        common.logInfo("Fields should be disabled and correct tooltip message should get displayed" );
        PageFactory.mtmPatientReportPage().isFieldDisabled(dataTable);
        PageFactory.mtmPatientReportPage().verifyToolTipMessage(dataTable, tooltipMessage);
        takeScreenshot();
    }

    @Then("Verify fields are editable")
    public void verifyFieldsAreNotEditable(DataTable dataTable) throws AutomationException {
        common.logInfo("Fields should not be disabled" );
        PageFactory.mtmPatientReportPage().isFieldEnabled(dataTable);
        takeScreenshot();
    }

    @Then("Verify fields are disabled")
    public void verifyFieldsAreDisabled(DataTable dataTable) throws AutomationException {
        common.logInfo("Fields should be disabled" );
        PageFactory.mtmPatientReportPage().isFieldDisabled(dataTable);
        takeScreenshot();
    }
    @And("Verify fields are editable and no tooltip message is displayed")
    public void verifyFieldsAreNotEditableAndNoToolTipMessageIsDisplayed(DataTable dataTable) throws AutomationException {
        common.logInfo("Fields should not be disabled and no tooltip message is displayed" );
        PageFactory.mtmPatientReportPage().isFieldEnabled(dataTable);
        PageFactory.mtmPatientReportPage().verifyToolTipMessage(dataTable,"" );
        takeScreenshot();
    }
}

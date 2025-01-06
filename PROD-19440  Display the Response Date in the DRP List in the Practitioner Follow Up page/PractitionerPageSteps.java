package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

import java.text.ParseException;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;
import static com.arine.automation.pages.PractionerDRPtablePage.INITIAL_VALUE;

public class PractitionerPageSteps {
    CommonSteps common = new CommonSteps();

    @When("^User selects practitioner \"([^\"]*)\" in practitioner tab$")
    public void userSelectPractitionerInPractitionerTabTable(String practitioner) throws AutomationException {
        common.logInfo("User select practitioner '" + practitioner + "' in practitioner tab");
        PageFactory.practionerDRPtablePage().userClicksPractitionerInPractitionerTabTable(practitioner);
        takeScreenshot();
    }

    @Then("^User updates \"([^\"]*)\" dropdown to \"([^\"]*)\" value in practitioner tab$")
    public void userClicksOnDropDownAndEditImplementationStatus(String dropDownName, String value) throws AutomationException {
        common.logInfo("User clicks on '" + dropDownName + "' dropDown and select '" + value + " option");
        PageFactory.practionerDRPtablePage().userClicksOnDropDownAndSelectsValue(dropDownName, value);
        takeScreenshot();
    }

    @Then("^Verify \"([^\"]*)\" column is updated with \"([^\"]*)\" for \"([^\"]*)\" in practitioner tab$")
    public void verifyUpdatedImplementationStatusInPractitionerTab(String columnName, String value, String practitioner) throws AutomationException {
        common.logInfo(String.format("Verify %s column is updated with %s for %s practitioner in practitioner tab", columnName, value, practitioner));
        PageFactory.practionerDRPtablePage().verifyUpdatedColumnStatusInPractitionerTable(columnName, value, practitioner);
        takeScreenshot();
    }

    @And("^User gets the initial value of \"([^\"]*)\" for \"([^\"]*)\" in practitioner tab$")
    public void getDataOf(String columnName, String practitioner) throws AutomationException {
        common.logInfo("User gets the initial value of " + columnName + " of " + practitioner + " in practitioner tab");
        PageFactory.practionerDRPtablePage().getValueInPractitionerTable(columnName, practitioner);
        takeScreenshot();
    }

    @And("^User verifies initial value of \"([^\"]*)\" for \"([^\"]*)\" remains unchanged in practitioner tab$")
    public void verifyValueRemainsUnchanged(String columnName, String practitioner) throws AutomationException {
        PageFactory.practionerDRPtablePage().verifyUpdatedColumnStatusInPractitionerTable(columnName, INITIAL_VALUE, practitioner);
        takeScreenshot();
    }

    @Then("^Select first \"([^\"]*)\" practitioners in practitioner tab and update \"([^\"]*)\" via bulk action and verify it in table$")
    public void verifyBulkActionForMultipleRecords(String numberOfRecord, String followupStatus) throws AutomationException {
        common.logInfo("Select first " + numberOfRecord + " practitioners in practitioner tab and update its followup status " + followupStatus + " from bulk action and verify it in table");
        PageFactory.practionerDRPtablePage().verifyBulkActionForMultipleRecords(Integer.parseInt(numberOfRecord), followupStatus);
        takeScreenshot();
    }

    @Then("^Verify fields are editable in practioner Tab$")
    public void verifyFieldsAreNotEditable(DataTable dataTable) throws AutomationException {
        common.logInfo("Verify fields are editable in practioner Tab" );
        PageFactory.practionerDRPtablePage().isFieldEnabled(dataTable);
        takeScreenshot();
    }

    @Then("^Verify \"([^\"]*)\" date column is updated as current date for \"([^\"]*)\" in practitioner tab$")
    public void verifyDateColumnIsUpdatedAsCurrentDateForInPractitionerTab(String columnName, String practitioner) throws AutomationException {
        common.logInfo(String.format("Verify %s column is updated with current date for %s practitioner in practitioner tab", columnName, practitioner));
        PageFactory.practionerDRPtablePage().verifyUpdatedDateStatusInPractitionerTable(columnName, practitioner);
        takeScreenshot();
    }

    @And("^User tries to change \"([^\"]*)\" and verifies it$")
    public void userTriesToChange(String fieldName) throws AutomationException {
        common.logInfo(String.format("User tries to change "+fieldName));
        PageFactory.practionerDRPtablePage().changeValueOfField(fieldName);
        takeScreenshot();
    }

    @And("^User update drps current response date from drps pane area$")
    public void userUpdateDrpsCurrentResponseDateFromDrpsPaneArea() throws AutomationException, ParseException {
        common.logInfo("User update drps current response date from drps pane area");
        PageFactory.practionerDRPtablePage().userUpdateDRPsCurrentResponseDateFromDRPsPaneArea();
        takeScreenshot();
    }

//    @And("^User gets the initial value of \"([^\"]*)\" of \"([^\"]*)\" in practitioner tab$")
//    public void userGetsTheInitialValueOf(String columnName, String practitioner) throws AutomationException {
//        common.logInfo("User gets the initial value of " + columnName + " of " + practitioner + " in practitioner tab");
//        PageFactory.practionerDRPtablePage().getValueInPractitionerTable(columnName, practitioner);
//        takeScreenshot();
//    }
}

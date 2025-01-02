package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

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

    @Then("^User updates \"([^\"]*)\" to \"([^\"]*)\" in practitioner tab$")
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

    @And("^Get data \"([^\"]*)\" for \"([^\"]*)\"$")
    public void getDataOf(String columnName, String practitioner) throws AutomationException {
        common.logInfo("User gets the initial value of " + columnName + " of " + practitioner + " in practitioner tab");
        PageFactory.practionerDRPtablePage().getValueInPractitionerTable(columnName, practitioner);
        takeScreenshot();
    }

    @And("^Verify \"([^\"]*)\" for \"([^\"]*)\" remains unchanged$")
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
        common.logInfo("Fields should not be disabled" );
        PageFactory.practionerDRPtablePage().isFieldEnabled(dataTable);
        takeScreenshot();
    }

//    @And("^User gets the initial value of \"([^\"]*)\" of \"([^\"]*)\" in practitioner tab$")
//    public void userGetsTheInitialValueOf(String columnName, String practitioner) throws AutomationException {
//        common.logInfo("User gets the initial value of " + columnName + " of " + practitioner + " in practitioner tab");
//        PageFactory.practionerDRPtablePage().getValueInPractitionerTable(columnName, practitioner);
//        takeScreenshot();
//    }
}

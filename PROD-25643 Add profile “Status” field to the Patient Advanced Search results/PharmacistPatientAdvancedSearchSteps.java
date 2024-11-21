package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistPatientAdvancedSearchSteps {

    CommonSteps common = new CommonSteps();

    @Then("Search patient by MBI: {string}")
    public void searchPatientByMBI(String input) throws AutomationException {
        common.logInfo("User search patient by MBI: "+input);
        PageFactory.patientAdvancedSearchPage().advanceSearchByMBI(input);
        takeScreenshot();
    }

    @And("Verify {string} column in search results includes:")
    public void verifyStatusColumnInSearchResults(String columnName, DataTable expectedStatuses) throws AutomationException {
        common.logInfo("User verifies '"+columnName+"' in the search results");
        PageFactory.patientAdvancedSearchPage().verifyStatusColumn(columnName, expectedStatuses.asList());
        takeScreenshot();
    }
}

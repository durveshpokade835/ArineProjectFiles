package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.Assert;

import java.text.ParseException;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PractitionerDateSteps {
    CommonSteps common = new CommonSteps();
    @And("Select reported drp table date filter {string} as {string} and click on {string} button")
    public void selectTaskFilter(String filterName, String filterOption, String buttonName) throws AutomationException, ParseException, ParseException {
        common.logInfo("Select reported drp table filter '" + filterName + "' as '" + filterOption + "' and click on '" + buttonName + "' button");

//        // Splitting the date range input (e.g., "02/10/2024 – 17/10/2024")
//        String[] dateRange = filterOption.split("–");
//        String fromDate = dateRange[0].trim(); // 02/10/2024
//        String toDate = dateRange[1].trim(); // 17/10/2024
//
//        PageFactory.practitionersDatePage().selectDateTaskFilter(filterName, fromDate, toDate, buttonName);

        PageFactory.practitionersDatePage().selectTaskFilter(filterName,filterOption,buttonName);
        takeScreenshot();
    }

    @And("verify dates in {string} column are within selected date range {string}")

    public void verifyDatesInStringColumnAreWithinSelectedDateRange(String columnName,String filterOption) throws AutomationException, ParseException {
//        String[] dateRange = filterOption.split("-");
//        String fromDate = dateRange[0].trim();
//        String toDate = dateRange[1].trim();
        PageFactory.practitionersDatePage().verifyDatesInRange(columnName, filterOption);
    }

    @Then("the {string} column in the DRPs table should display dates within the selected range")
    public void theColumnInTheDRPsTableShouldDisplayDatesWithinTheSelectedRange(String columnName) throws AutomationException {
        boolean isDateInRange = PageFactory.practitionersDatePage().verifyReportCreatedDatesWithinRange(columnName);
        Assert.assertTrue(isDateInRange,"Some dates in the " + columnName + " column are out of the specified range." );
        takeScreenshot();
    }
}

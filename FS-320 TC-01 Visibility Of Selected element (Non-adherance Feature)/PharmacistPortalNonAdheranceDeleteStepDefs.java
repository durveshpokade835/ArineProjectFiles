package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import com.arine.automation.util.WebDriverUtil;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistPortalNonAdheranceDeleteStepDefs {
    CommonSteps common = new CommonSteps();

    @And("User clicks on dropDown in Medlist page and select {string} option")
    public void userClicksOnDropDownInMedlistPageAndSelectOption( String dropDownValue) throws AutomationException, InterruptedException {

        common.logInfo("User clicks on dropDown in Medlist page and select '" + dropDownValue);
        PageFactory.pharmacistPortalNonAdheranceDeletePage().clicksOnDropDownAndSelectValue(dropDownValue);
        takeScreenshot();

    }

    @And("Verify {string} option in dropdown field")
    public void verifyOptionInDropdownFieldPresentInMedlistTab(String selectedValue) throws AutomationException {
        common.logInfo("User verifies the selected value '" + selectedValue + "' is getting displayed or not in the dropDown input box in Medlist page");
        PageFactory.pharmacistPortalNonAdheranceDeletePage().verifyElementsInDropDownBox(selectedValue);
        takeScreenshot();
    }

    @And("Verify {string} option is in SELECTED list section in Dropdown")
    public void verifyDropdownOptionsPresentInMedlistTab(String selectedValue) throws AutomationException {
        common.logInfo("User verifies the selected value '"+ selectedValue +"' is getting displayed or not in the dropDown List under selected section in Medlist page");
        PageFactory.pharmacistPortalNonAdheranceDeletePage().verifyElementsAvailableInDropDownList(selectedValue);
        takeScreenshot();
    }


    @And("Verify {string} option is not in AVAILABLE OPTIONS list section in Dropdown")
    public void verifyOptionIsNotVisibleInAvailableOptionInDropdownListPresentInMedlistTab(String selectedValue) throws AutomationException {
        common.logInfo("User verifies the selected value '"+ selectedValue +"' is getting displayed or not in the dropDown List under selected section in Medlist page");
        PageFactory.pharmacistPortalNonAdheranceDeletePage().verifyElementsNotAvailableInDropDownList(selectedValue);
        takeScreenshot();
    }
}

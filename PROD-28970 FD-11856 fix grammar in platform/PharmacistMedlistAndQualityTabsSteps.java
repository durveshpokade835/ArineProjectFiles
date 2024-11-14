package com.arine.automation.glue;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistMedlistAndQualityTabsSteps {

    CommonSteps common = new CommonSteps();

    @And("User selects single already active medicine")
    public void userSelectsSingleAlreadyActiveMedicine() throws AutomationException {
        PageFactory.medicationPage().selectSingleActiveMedicine();
    }

    @And("User selects all the already active medicines")
    public void userSelectsAlreadyActiveMedicine() throws AutomationException {
        PageFactory.medicationPage().selectAlreadyActiveMedicines();
    }

    @Then("Click on Active Selected Button")
    public void clickOnActiveSelectedButton() throws AutomationException {
        PageFactory.medicationPage().clickActiveSelectedButton();
        takeScreenshot();
    }

    @And("Verify {string} error message")
    public void verifyPopupMessage(String message) throws AutomationException {
        common.logInfo("Verify '"+message+ "' error popup");
        PageFactory.medicationPage().verifyMessage(message);
        takeScreenshot();
        PageFactory.patientPage().clickOnButton("Ok");
    }
}
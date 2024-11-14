package com.arine.automation.glue;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import com.arine.automation.pages.PatientPage;
import com.arine.automation.util.WebDriverUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebElement;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistMedlistAndQualityTabsSteps {

    CommonSteps common = new CommonSteps();

    @And("User selects already active medicine")
    public void userSelectsAlreadyActiveMedicine() throws AutomationException {
        PageFactory.medicationPage().selectAlreadyActiveMedicine();
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

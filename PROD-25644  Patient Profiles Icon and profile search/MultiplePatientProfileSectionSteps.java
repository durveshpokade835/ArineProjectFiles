package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class MultiplePatientProfileSectionSteps {

    CommonSteps common = new CommonSteps();

    @Then("^Verify icon for multiple patient profile and verify the tooltip: \"([^\"]*)\"$")
    public void verifyMultiplePatientProfileIcon(String toolTipMessage) throws AutomationException {
        common.logInfo("Verify icon for multiple patient profile and verify tooltip: '" + toolTipMessage + "'");
        PageFactory.patientProfileSectionPage().verifyMultiplePatientProfileIcon(toolTipMessage);
        takeScreenshot();
    }

    @Then("^User click on patient profile icon and verify the popup heading text: \"([^\"]*)\"$")
    public void verifyMultiplePatientProfilePopup(String popupTitle) throws AutomationException {
        common.logInfo("User click on patient profile icon and verify popup heading text: " + popupTitle);
        PageFactory.patientProfileSectionPage().verifyMultiplePatientProfilePopup(popupTitle);
        takeScreenshot();
    }

    @And("User closes the modal by clicking on {string}")
    public void userClosesTheModalByClickingOn(String button) throws AutomationException {
        common.logInfo("User closes the modal by clicking on " + button + " button");
        PageFactory.patientProfileSectionPage().clickOnButton(button);
        takeScreenshot();
    }

    @And("Verify icon for multiple patient profile is not present")
    public void verifyIconForMultiplePatientProfileIsNotPresent() throws AutomationException {
        common.logInfo("Verify icon for multiple patient profile is not visible");
        PageFactory.patientProfileSectionPage().verifyMultiplePatientProfileIconVisibility();
        takeScreenshot();

    }
}

package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.Then;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class LogProviderSteps {
    CommonSteps common = new CommonSteps();
    @Then("^Verify the \"([^\"]*)\" dropdown has following options$")
    public void verifyTheDropdownHasFollowingOptions(String dropdown, List<String> options) throws AutomationException {
        common.logInfo("Verify the "+dropdown+" dropdown has following options :"+options.toString());
        PageFactory.logProviderPage().verifyTheDropdownHasFollowingOptions(dropdown,options);
        takeScreenshot();
    }
}

package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import com.arine.automation.pages.PageFactory;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class ToggleToLockUnlockLayoutEditingSteps {
    CommonSteps common = new CommonSteps();

    @When("User clicks on the initials dropdown")
    public void user_clicks_on_initials_dropdown() throws AutomationException {
        common.logInfo("User clicks on the initials dropdown");
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @When("User hovers over the {string} menu option")
    public void user_hovers_over_layout_menu_option(String layoutOption) throws AutomationException {
        common.logInfo("User hovers over the '"+layoutOption+"' menu option");
        PageFactory.layoutPage().hoverOverLayoutOption(layoutOption);
    }

    @Then("The menu option should display {string} when Layout is {string}")
    public void verify_menu_option_text(String expectedOptionText, String expectedLayoutState) throws AutomationException {
        if (expectedLayoutState.equalsIgnoreCase("Locked")) {
            PageFactory.layoutPage().verifyLayoutOptionForLockedState(expectedOptionText);
        } else {
            PageFactory.layoutPage().verifyLayoutOptionForUnlockedState(expectedOptionText);
        }
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();

    }

    @And("User clicks on the {string} button")
    public void userClicksOnTheUnlockLayoutButton(String expectedLayoutState) throws AutomationException {
        common.logInfo("User clicks on the '" + expectedLayoutState + "' button");
        PageFactory.layoutPage().clickSelectedLayoutOption(expectedLayoutState);
    }

    @Then("The layout should be movable when unlocked")
    public void theLayoutShouldBeMovableWhenUnlocked() throws AutomationException {
        boolean isMovable = PageFactory.layoutPage().isLayoutMovable();
        takeScreenshot();
        Assert.assertTrue(isMovable, "The layout is not movable when unlocked.");
    }
}
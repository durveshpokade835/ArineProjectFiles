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

    @When("^User clicks on \"([^\"]*)\" dropDown$")
    public void userClicksOnUserInitialsDropdown(String dropdownName) throws AutomationException {
        PageFactory.layoutPage().openUserInitialsDropdown();
        common.logInfo("User clicked on the '" + dropdownName + "' dropdown.");
        takeScreenshot();
    }

    @Then("^Verify that the user can see the options in the upper right \"([^\"]*)\"$")
    public void verifyDropdownOptions(String options) throws AutomationException {
        PageFactory.layoutPage().verifyDropdownOptions(options);
        common.logInfo("Verified the dropdown options for the user’s initials: " + options);
        takeScreenshot();
    }

    @When("^User clicks on \"([^\"]*)\" field$")
    public void userClicksLayoutOnField(String fieldName) throws AutomationException {
        PageFactory.layoutPage().clickOnField(fieldName);
        common.logInfo("User clicked on the field: '" + fieldName + "'.");
        takeScreenshot();
    }

    @Then("^Verify that the user can see the options by hovering on the Layout field \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyDropdownOptionsByHovering(String fieldName, String options) throws AutomationException {
        PageFactory.layoutPage().verifyDropdownSubmenuOptions(fieldName, options);
        common.logInfo("Verified submenu options when hovering over the '" + fieldName + "' field: " + options);
        takeScreenshot();
        PageFactory.layoutPage().openUserInitialsDropdown();
    }

    @When("^User clicks on Manage Access field$")
    public void userClicksOnManageAccessField() throws AutomationException {
        PageFactory.layoutPage().clickOnField("Manage Access");
        common.logInfo("User clicked on the 'Manage Access' field.");
        takeScreenshot();
    }

    @Then("^Verify that the user can see the options by hovering on the Manage Access field \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyManageAccessSubmenuOptions(String option1, String option2) throws AutomationException {
        PageFactory.layoutPage().verifyManageAccessSubmenuOptions(option1, option2);
        common.logInfo("Verified submenu options when hovering over the 'Manage Access' field: " + option1 + ", " + option2);
        takeScreenshot();
    }

    //-------------------------------------------------------------------------------------------------------------
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

    //-----------------------------------------------------------------------------------------------------------------

    @Given("^User clicks on user's initials dropdown$")
    public void userClicksOnUserSIntialsDropdown() throws AutomationException {
        PageFactory.layoutPage().clickOnDropDown();
    }

    @And("^User perform mouse hovering action on \"([^\"]*)\"$")
    public void userPerformMouseHoveringActionOn(String option) throws AutomationException {
        PageFactory.layoutPage().hoverOnOption(option);
    }

    @When("^User clicks on \"([^\"]*)\" option$")
    public void userClicksOnOption(String option) throws AutomationException {
        PageFactory.layoutPage().clickOnOption(option);
    }

    @Then("^The layout should not be movable when Locked$")
    public void theLayoutShouldNotBeMovableWhenLocked() throws AutomationException {
        boolean isMovable = PageFactory.layoutPage().isLayoutMovable2();
        takeScreenshot();
        Assert.assertFalse(isMovable, "The layout is movable when Locked.");
    }

    @When("^User checks for initial layout position$")
    public void userChecksForInitialLayoutPosition() throws AutomationException {
        PageFactory.layoutPage().captureInitialLayoutPosition();
    }

    @Then("^User moves the layout$")
    public void userMovesTheLayout() throws AutomationException {
        boolean isMoved = PageFactory.layoutPage().moveLayout();
        Assert.assertTrue(isMoved, "The layout did not move.");
        takeScreenshot();
    }

    @Then("^Verify Layout is reset to initial layout position$")
    public void verifyLayoutIsResetToInitialLayoutPosition() throws AutomationException {
        boolean isReset = PageFactory.layoutPage().isLayoutReset();
        takeScreenshot();
        Assert.assertTrue(isReset, "The layout position did not reset to the initial layout position.");
    }

    @Then("^Verify layout is in saved position$")
    public void verifyLayoutIsInSavedPosition() throws AutomationException {
        boolean isInSavedPosition = PageFactory.layoutPage().isLayoutInSavedPosition();
        takeScreenshot();
        Assert.assertTrue(isInSavedPosition, "The layout did not load in the saved position on re-login.");
    }
}
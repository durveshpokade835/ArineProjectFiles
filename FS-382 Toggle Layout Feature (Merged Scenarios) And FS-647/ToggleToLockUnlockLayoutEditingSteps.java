package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class ToggleToLockUnlockLayoutEditingSteps {

    CommonSteps common = new CommonSteps();

    @Given("^User clicks on user's initials dropdown$")
    public void userClicksOnUserSIntialsDropdown() throws AutomationException {
        common.logInfo("User tries to click on user's initials dropdown" );
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @And("Verify initials dropdown has option")
    public void verifyInitialsDropdownHasOption(List<String> options) throws AutomationException{
        common.logInfo("Verify Initial's dropdown has option" );
        PageFactory.layoutPage().verifyInitialsDropdownHasOption(options);
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @And("^User perform mouse hovering action on \"([^\"]*)\"$")
    public void userPerformMouseHoveringActionOn(String option) throws AutomationException {
        common.logInfo("User perform mouse hovering action on "+option );
        PageFactory.layoutPage().hoverOnOption(option);
    }

    @Then("Verify Manage Access has option")
    public void verifyManageAccessHasOption(List<String> options) throws AutomationException {
        common.logInfo("Verify Manage Access has option" );
        PageFactory.layoutPage().verifyManageAccessHasOption(options);
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @Then("Verify Layout has option {string} when Layout is {string} and {string}")
    public void verifyLayoutHasOptionLayoutOptionsWhenLayoutIsAnd(String layoutOption, String layoutState, String resetLayout) throws AutomationException {
        common.logInfo("The menu option should display " + layoutOption + " when Layout is " + layoutState);
        PageFactory.layoutPage().verifyLayoutHasOption(layoutOption, layoutState, resetLayout);
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @Then("^The menu option should display \"([^\"]*)\" when Layout is \"([^\"]*)\"$")
    public void verify_menu_option_text(String expectedOptionText, String expectedLayoutState) throws AutomationException {
        common.logInfo("The menu option should display " + expectedOptionText + " when Layout is " + expectedLayoutState);
        if (expectedLayoutState.equalsIgnoreCase("Locked")) {
            PageFactory.layoutPage().verifyLayoutOptionForLockedState(expectedOptionText);
        } else {
            PageFactory.layoutPage().verifyLayoutOptionForUnlockedState(expectedOptionText);
        }
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @When("^User clicks on \"([^\"]*)\" option$")
    public void userClicksOnOption(String option) throws AutomationException {
        common.logInfo("User clicks on "+option+" button" );
        PageFactory.layoutPage().clickOnOption(option);
    }

    @Then("^The layout should be movable when unlocked$")
    public void theLayoutShouldBeMovableWhenUnlocked() throws AutomationException {
        common.logInfo("The layout should be movable when unlocked" );
        PageFactory.layoutPage().isLayoutMovableD();
        takeScreenshot();
    }

    @Then("^The layout should not be movable when Locked$")
    public void theLayoutShouldNotBeMovableWhenLocked() throws AutomationException {
        common.logInfo("The layout should not be movable when unlocked" );
        PageFactory.layoutPage().isLayoutMovable();
        takeScreenshot();
    }

    @When("^User checks for initial layout position$")
    public void userChecksForInitialLayoutPosition() throws AutomationException {
        common.logInfo("User checks for initial layout position" );
        PageFactory.layoutPage().captureInitialLayoutPosition();
    }

    @Then("^User moves the layout$")
    public void userMovesTheLayout() throws AutomationException {
        common.logInfo("User moves the layout" );
        PageFactory.layoutPage().moveLayout();
        takeScreenshot();
    }

    @Then("^Verify Layout is reset to initial layout position$")
    public void verifyLayoutIsResetToInitialLayoutPosition() throws AutomationException {
        common.logInfo("Verify Layout is reset to initial layout position" );
        PageFactory.layoutPage().isLayoutReset();
        takeScreenshot();
        PageFactory.layoutPage().clickInitialsDropdown();
    }

    @Then("^Verify layout is in saved position$")
    public void verifyLayoutIsInSavedPosition() throws AutomationException {
        common.logInfo("Verify layout is in saved position" );
         PageFactory.layoutPage().isLayoutInSavedPosition();
        takeScreenshot();
    }

    @Then("Check the layout state")
    public void check_the_layout_state() throws AutomationException {
        common.logInfo("Verify the layout state");
        PageFactory.layoutPage().verifyLayoutState();
        takeScreenshot();
    }

    @And("User add new medicine {string}")
    public void user_add_new_medicine(String medicine) throws AutomationException {
        common.logInfo("User add new medicine '"+medicine+"'");
        PageFactory.layoutPage().addNewMedicationWithoutPrescriber(medicine);
        takeScreenshot();
    }

    @Then("User Select the recently added medication {string} and try to change the Prescriber {string} from the details pane")
    public void user_select_recently_added_medication_and_change_prescriber(String medicine,String prescriberOption) throws AutomationException {
        common.logInfo("User Select the recently added medication '"+medicine+"' and try to change the Prescriber from the details pane to '"+prescriberOption+"'");
        PageFactory.layoutPage().selectMedicationAndChangePrescriber(medicine,prescriberOption);
        takeScreenshot();
    }

    @And("User Check the cursor does not get stuck")
    public void user_check_cursor_does_not_get_stuck() throws AutomationException {
        common.logInfo("User Check the cursor does not get stuck");
        PageFactory.layoutPage().verifyCursorNotStuck();
        takeScreenshot();
    }

    @And("User deletes newly Added Medicine {string}")
    public void userDeletesNewlyAddedMedicine(String medicine) throws AutomationException {
        common.logInfo("User Deletes the recently added medication '"+medicine+"'");
        PageFactory.layoutPage().deleteMedicine(medicine);
        takeScreenshot();
    }
}
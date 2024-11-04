package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.arine.automation.exception.AutomationException;
import org.testng.Assert;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class LayoutPage {

    private final String initialsDropdown = "//button[contains(@class, 'node_modules-react-multilevel-dropdown-')]/span[contains(text(),'')]";
    private final String layoutLockUnlockState = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private final String layoutOptionButton = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[text()='%s']";
    private final String layoutOptionText = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private final String layoutElement = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]/div[contains(@class,'-PatientTriageSelection')]";

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(initialsDropdown);
        if (dropdown == null) throw new AutomationException("Initials dropdown not found.");
        dropdown.click();
    }

    public void hoverOverLayoutOption(String layoutOption) throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionButton,layoutOption));
        if (layoutOptionElement == null) throw new AutomationException("Layout option not found in the dropdown menu.");
        new Actions(DriverFactory.drivers.get()).moveToElement(layoutOptionElement).perform();
    }

    public void verifyLayoutOptionForLockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, true);
    }

    public void verifyLayoutOptionForUnlockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, false);
    }

    private void verifyLayoutOptionText(String expectedOptionText, boolean isLocked) throws AutomationException {
        WebElement layoutElement = driverUtil.getWebElement(layoutLockUnlockState);
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText,expectedOptionText ));
        if ((isLocked && layoutElement == null) || (!isLocked && layoutElement != null)) {
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text'"+expectedOptionText+"'.");
        } else {
           String optionText =expectedOptionText.equalsIgnoreCase("Unlock Layout")?"Lock Layout":"Unlock Layout";
            layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText,optionText ));
            layoutOptionElement.click();
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text '"+expectedOptionText+"'.");
        }
    }

    public void clickSelectedLayoutOption(String expectedLayoutOption) throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText,expectedLayoutOption));
        WebElement layoutElement = driverUtil.getWebElement(layoutLockUnlockState);
        if ((expectedLayoutOption.equalsIgnoreCase("Unlock Layout")&& layoutElement==null)||(expectedLayoutOption.equalsIgnoreCase("Lock Layout")&& layoutElement!=null)){
            layoutOptionElement.click();
        }
    }

    public boolean isLayoutMovable() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(layoutElement);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        int initialX = layout.getLocation().getX();
        int initialY = layout.getLocation().getY();

        // Attempt to drag the layout element by a small offset
        actions.dragAndDropBy(layout, 0, 400).perform();
        int movedX = layout.getLocation().getX();
        int movedY = layout.getLocation().getY();

        // Verify if the layout has moved
        return (initialX != movedX) || (initialY != movedY);
    }

}





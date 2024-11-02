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
    private final String layout = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private final String layoutButton = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[text()='Layout']";
    private final String layoutOptionText = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'Unlock Layout')]";

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(initialsDropdown);
        if (dropdown == null) throw new AutomationException("Initials dropdown not found.");
        dropdown.click();
    }

    public void hoverOverLayoutOption() throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(layoutButton);
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
        WebElement layoutElement = driverUtil.getWebElement(layout);
        WebElement layoutOptionElement = driverUtil.getWebElement(layoutOptionText);
        if (layoutOptionElement == null) throw new AutomationException("Layout option not found in the dropdown menu.");

        if ((isLocked && layoutElement == null) || (!isLocked && layoutElement != null)) {
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
        } else {
            layoutOptionElement.click();
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
        }
    }
}

package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.arine.automation.exception.AutomationException;
import org.python.antlr.ast.Str;
import org.testng.Assert;

import javax.swing.*;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class LayoutPage {

    private String initialsDropdown = "//button[contains(@class, 'node_modules-react-multilevel-dropdown-')]/span[contains(text(),'')]";
    private String layout = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private String layoutButton = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[text()='Layout']";          // Update with the actual locator for the Layout option
    private String layoutOption = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'Unlock Layout')]";

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(initialsDropdown);
        if (dropdown == null) {
            throw new AutomationException("Initials dropdown not found.");
        }
        dropdown.click();
    }

    public void hoverOverLayoutOption() throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(layoutButton);
        if (layoutOptionElement == null) {
            throw new AutomationException("Layout option not found in the dropdown menu.");
        }
        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.moveToElement(layoutOptionElement).perform();
    }

    public void getLayoutOptionTextForLockedLayout(String expectedOptionText) throws AutomationException {
//        WebElement layoutElement = DriverFactory.drivers.get().findElement(layout);// null = layout is locked, present= layout is unlocked
//        if (layoutElement==null && expectedLayoutState=="Locked"){
//            WebElement layoutOptionElement = DriverFactory.drivers.get().findElement(layoutOption);
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//           String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText,expectedOptionText,"The layout option text did not match the expected text." );
//        } else if(layoutElement!=null && expectedLayoutState=="Unlocked") {
//            WebElement layoutOptionElement = DriverFactory.drivers.get().findElement(layoutOption);
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText,expectedOptionText,"The layout option text did not match the expected text." );
//        }


        WebElement layoutElement = driverUtil.getWebElement(layout);// null = layout is locked, present= layout is unlocked
        WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
//        if (layoutElement == null) {
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
//        } else if (layoutElement != null) {
//            layoutOptionElement.click();
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
//
//        } else {
//            throw new AutomationException("Assertion Failed");
//        }

        /////////////////////////////////////////////////////////////////////////
        if (layoutOptionElement == null) {
            throw new AutomationException("Layout option not found in the dropdown menu.");
        } else {

            if (layoutElement == null) {
                String actualOptionText = layoutOptionElement.getText();
                Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
            } else {
                layoutOptionElement.click();
                String actualOptionText = layoutOptionElement.getText();
                Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
            }
        }
//        Actions actions = new Actions(DriverFactory.drivers.get());
//        actions.sendKeys(Keys.ESCAPE).perform();
//        clickInitialsDropdown();

//        WebElement layoutElement = DriverFactory.drivers.get().findElement(layout);// null = layout is locked, present= layout is unlocked
//        WebElement layoutElement = driverUtil.getWebElement(layout);
//        if (layoutElement==null && expectedLayoutState=="Locked"){
////            WebElement layoutOptionElement = DriverFactory.drivers.get().findElement(layoutOption);
//            WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//           String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText,expectedOptionText,"The layout option text did not match the expected text." );
//        } else if(layoutElement!=null && expectedLayoutState=="Unlocked") {
////            WebElement layoutOptionElement = DriverFactory.drivers.get().findElement(layoutOption);
//            WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
//            if (layoutOptionElement == null) {
//                throw new AutomationException("Layout option not found in the dropdown menu.");
//            }
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText,expectedOptionText,"The layout option text did not match the expected text." );
//        }

    }


    public void getLayoutOptionTextForUnlockedLayout(String expectedOptionText) throws AutomationException {

        WebElement layoutElement = driverUtil.getWebElement(layout);
        if (layoutElement == null) {
            WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
            if (layoutOptionElement == null) {
                throw new AutomationException("Layout option not found in the dropdown menu.");
            }
            layoutOptionElement.click();
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
        } else if (layoutElement != null) {
            WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
            if (layoutOptionElement == null) {
                throw new AutomationException("Layout option not found in the dropdown menu.");
            }
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");

        } else {
            throw new AutomationException("Layout is Unlocked but it should be locked");
        }
//        clickInitialsDropdown();
//        Actions actions = new Actions(DriverFactory.drivers.get());
//        actions.sendKeys(Keys.ESCAPE).perform();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
//    WebElement layoutElement = driverUtil.getWebElement(layout);// null = layout is locked, present= layout is unlocked
//    WebElement layoutOptionElement = driverUtil.getWebElement(layoutOption);
//    if (layoutOptionElement == null){
//        if (layoutElement == null){
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
//        }else {
//            layoutOptionElement.click();
//            String actualOptionText = layoutOptionElement.getText();
//            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text.");
//        }
//    }else {
//        throw new AutomationException("Layout option not found in the dropdown menu.");
//    }


}

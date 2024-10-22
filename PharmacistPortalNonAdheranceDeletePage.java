//package com.arine.automation.pages;
//
//import com.arine.automation.drivers.DriverFactory;
//import com.arine.automation.exception.AutomationException;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.stringtemplate.v4.ST;
//import org.testng.Assert;
//import org.testng.asserts.SoftAssert;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.arine.automation.glue.CommonSteps.takeScreenshot;
//
//public class PharmacistPortalNonAdheranceDeletePage extends PatientPage {
//
//    public static final String MED_LIST_INPUT_BOX = "(//div[@class='css-1ndbt8o-multiValue'])/div[@class='css-12jo7m5']";
//    public static final String MED_LIST_INPUT_BOX_SELECTED_LIST = "//*[text()='Selected']/following-sibling::div//label";
//    public static final String SELECT_DROPDOWN = "//*[text()='Reasons for Non-adherence']/following-sibling::span//div[contains(@class,' css-117e4ry')]";
////    public static final String SELECT_DROPDOWN_LIST= "";
//    public static final String CLEAR_BUTTON = "(//div[contains(@class,'css-vv1fq9-indicatorContainer')])[1]";
//    public static final String MED_LIST_INPUT_BOX_UNAVAILABLE_LIST= "//*[contains(text(),'Available options')]/following-sibling::div//label";
//
//    Actions actions = new Actions(DriverFactory.drivers.get());
//
//    public void clicksOnDropDownAndSelectValue( String value) throws AutomationException, InterruptedException {
//        WebElement clearBtn = driverUtil.getWebElementAndScroll(CLEAR_BUTTON);
//
//            if (clearBtn != null)
//                clearBtn.click();
//
//
//        WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
//        if (selectDropdown == null)
//            throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
//
//        selectDropdown.click();
//        actions.sendKeys(Keys.ESCAPE);
////        wait(1000);
//        WebElement selectDropdownOption = driverUtil.getWebElementAndScroll("//*[text()='" + value + "']",2000);
//        if (selectDropdownOption == null)
//            throw new AutomationException(String.format("Unable to find %s option in DropDown", value));
//
//        selectDropdownOption.click();
////        takeScreenshot();
//        waitForLoadingPage();
////        return true;
//    }
//    public void verifyElementsInDropDownBox(String selectedValue) throws AutomationException {
//        List<String> ExpectedValues= new ArrayList<>();
//        ExpectedValues.add(selectedValue);
//        WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
//        if (selectDropdown == null)
//            throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
//        List<WebElement> selectDropdownElements = driverUtil.getWebElements(MED_LIST_INPUT_BOX);
//        List<String> ActualValues= new ArrayList<>();
//        for (WebElement option:selectDropdownElements){
//           String ActualValue = option.getText();
//           ActualValues.add(ActualValue);
//        }
//        Assert.assertEquals(ActualValues,ExpectedValues);
//        System.out.println(ActualValues+"|"+ExpectedValues);
////        return true;
//    }
//
//
//
//    public void verifyElementsAvailableInDropDownList(String selectedValue) throws AutomationException {
//        List<String> ExpectedValues= new ArrayList<>();
//        ExpectedValues.add(selectedValue);
//        WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
//        if (selectDropdown == null)
//            throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
//        selectDropdown.click();
//
//        List<WebElement> selectDropdownElementsList = driverUtil.getWebElements(MED_LIST_INPUT_BOX_SELECTED_LIST);
//        List<String> ActualValues= new ArrayList<>();
//        for (WebElement option:selectDropdownElementsList){
//            String ActualValue = option.getText();
//            ActualValues.add(ActualValue);
//        }
//        Assert.assertEquals(ActualValues,ExpectedValues);
////        System.out.println(ActualValues+"|"+ExpectedValues);
//    }
//
//    public void verifyElementsNotAvailableInDropDownList(String selectedValue) throws AutomationException {
//        List<String> ExpectedValues= new ArrayList<>();
//        ExpectedValues.add(selectedValue);
//        WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
//        if (selectDropdown == null)
//            throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
//        selectDropdown.click();
//
//        List<WebElement> selectDropdownElementsList = driverUtil.getWebElements(MED_LIST_INPUT_BOX_UNAVAILABLE_LIST);
//        List<String> ActualValues= new ArrayList<>();
//        for (WebElement option:selectDropdownElementsList){
//            String ActualValue = option.getText();
//            ActualValues.add(ActualValue);
//        }
//        SoftAssert softAssert = new SoftAssert();
//        for (String expectedValue : ExpectedValues) {
//            // Use soft assertion to check that expectedValue is NOT present in ActualValueList
//            softAssert.assertFalse(ActualValues.contains(expectedValue),
//                    "The value '" + expectedValue + "' was found in the ActualValueList, but it should not be present.");
//        }
//
//        // Call assertAll to aggregate all the soft assertion failures and mark the test as failed if any failed
//        softAssert.assertAll();
//    }
//
//
//
//}


package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistPortalNonAdheranceDeletePage extends PatientPage {

    public static final String MED_LIST_INPUT_BOX = "(//div[@class='css-1ndbt8o-multiValue'])/div[@class='css-12jo7m5']";
    public static final String MED_LIST_INPUT_BOX_SELECTED_LIST = "//*[text()='Selected']/following-sibling::div//label";
    public static final String SELECT_DROPDOWN = "//*[text()='Reasons for Non-adherence']/following-sibling::span//div[contains(@class,' css-117e4ry')]";
    public static final String CLEAR_BUTTON = "(//div[contains(@class,'css-vv1fq9-indicatorContainer')])[1]";
    public static final String MED_LIST_INPUT_BOX_UNAVAILABLE_LIST = "//*[contains(text(),'Available options')]/following-sibling::div//label";

    Actions actions = new Actions(DriverFactory.drivers.get());

    public void clicksOnDropDownAndSelectValue(String value) throws AutomationException {
        try {
            WebElement clearBtn = driverUtil.getWebElementAndScroll(CLEAR_BUTTON);
            if (clearBtn != null) {
                clearBtn.click();
            }

            WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
            if (selectDropdown == null) {
                throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
            }
            selectDropdown.click();

            // Escape in case of any dropdown overlay issues
            actions.sendKeys(Keys.ESCAPE);

            WebElement selectDropdownOption = driverUtil.getWebElementAndScroll("//*[text()='" + value + "']", 2000);
            if (selectDropdownOption == null) {
                throw new AutomationException(String.format("Unable to find option '%s' in DropDown", value));
            }
            selectDropdownOption.click();
            waitForLoadingPage();
        } catch (AutomationException e) {
            takeScreenshot();
            throw new AutomationException(String.format("Error occurred while selecting value from dropdown: " + e.getMessage(), (List<String>) e));
        }
    }

    public void verifyElementsInDropDownBox(String selectedValue) throws AutomationException {
        try {
            List<String> expectedValues = new ArrayList<>();
            expectedValues.add(selectedValue);

            WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
            if (selectDropdown == null) {
                throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
            }

            List<WebElement> selectDropdownElements = driverUtil.getWebElements(MED_LIST_INPUT_BOX);
            List<String> actualValues = new ArrayList<>();
            for (WebElement option : selectDropdownElements) {
                actualValues.add(option.getText());
            }

            Assert.assertEquals(actualValues, expectedValues, "Dropdown values do not match the expected values.");
        } catch (Exception e) {
            takeScreenshot();
            throw new AutomationException(String.format("Error occurred while verifying dropdown elements: " + e.getMessage(), (List<String>) e));
        }
    }

    public void verifyElementsAvailableInDropDownList(String selectedValue) throws AutomationException {
        try {
            List<String> expectedValues = new ArrayList<>();
            expectedValues.add(selectedValue);

            WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
            if (selectDropdown == null) {
                throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
            }
            selectDropdown.click();

            List<WebElement> selectDropdownElementsList = driverUtil.getWebElements(MED_LIST_INPUT_BOX_SELECTED_LIST);
            List<String> actualValues = new ArrayList<>();
            for (WebElement option : selectDropdownElementsList) {
                actualValues.add(option.getText());
            }

            Assert.assertEquals(actualValues, expectedValues, "Available dropdown options do not match the expected values.");
        } catch (Exception e) {
            takeScreenshot();
            throw new AutomationException(String.format("Error occurred while verifying available elements in dropdown: " + e.getMessage(), (List<String>) e));
        }
    }

    public void verifyElementsNotAvailableInDropDownList(String selectedValue) throws AutomationException {
        try {
            List<String> expectedValues = new ArrayList<>();
            expectedValues.add(selectedValue);

            WebElement selectDropdown = driverUtil.getWebElementAndScroll(SELECT_DROPDOWN);
            if (selectDropdown == null) {
                throw new AutomationException(String.format("Unable to find DropDown: Select or Type..."));
            }
            selectDropdown.click();

            List<WebElement> selectDropdownElementsList = driverUtil.getWebElements(MED_LIST_INPUT_BOX_UNAVAILABLE_LIST);
            List<String> actualValues = new ArrayList<>();
            for (WebElement option : selectDropdownElementsList) {
                actualValues.add(option.getText());
            }

            // Soft Assert: Ensure the unavailable elements are not present in the actual dropdown
            SoftAssert softAssert = new SoftAssert();
            for (String expectedValue : expectedValues) {
                softAssert.assertFalse(actualValues.contains(expectedValue),
                        "The value '" + expectedValue + "' was found in the dropdown list, but it should not be present.");
            }
            softAssert.assertAll();
        } catch (Exception e) {
            takeScreenshot();
            throw new AutomationException(String.format("Error occurred while verifying unavailable elements in dropdown: " + e.getMessage(), (List<String>) e));
        }
    }
}

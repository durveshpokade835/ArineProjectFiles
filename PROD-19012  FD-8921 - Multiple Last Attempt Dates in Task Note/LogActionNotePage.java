package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PatientPage.VERIFY_PATIENT_TASK_RECORD;
import static com.arine.automation.pages.PatientPage.scrollToTop;


public class LogActionNotePage {
    public static final String LAST_CREATED_LOG= "//*[contains(@class,'mantine-Table-root')]//tbody/tr[1]// td//*[contains(.,'%s')]";
    public static final String ASCENDING_ORDER_BUTTON_LOCATOR = "//*[text()='%s']//..//button//..//*[contains(@class,'sort-ascending')]";
    public static final String DESCENDING_ORDER_BUTTON_LOCATOR = "//*[text()='%s']//..//button//..//*[contains(@class,'sort-descending')]";
    public static final String CHANGE_ORDER_BUTTON_LOCATOR = "//*[text()='%s' and contains(@class,'TableHeadCell')]/..//button";
    public static final String LAST_ATTEMPTED_BY_INFO_LOCATOR = "//*[contains(@class,'labelDisp')]//*[contains(text(),'Created by')]/preceding-sibling::*[contains(@class,'createdBy')]";

//    public void verifyUserIsAbleToLogActionForSelectedTaskAndVerify(String verificationText, String note ) throws AutomationException {
//        WebElement element = driverUtil.findElement(String.format(DESCENDING_ORDER_BUTTON_LOCATOR,"Due Date"));
//        if (element == null) {
//            driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,"Due Date")).click();
//            element = driverUtil.findElement(String.format(DESCENDING_ORDER_BUTTON_LOCATOR,"Due Date"));
//            if (element == null) {
//                driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,"Due Date")).click();
//            }
//        }
//            if (verificationText.equalsIgnoreCase("Current Date")) {
//                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//                verificationText = LocalDateTime.now().format(format);
//            }
//            Boolean flag = false;
//            WebElement requiredElement = driverUtil.getWebElement(String.format(LAST_CREATED_LOG, note));
//            if (requiredElement == null)
//                throw new AutomationException(String.format("Note %s not displayed table", note));
//    }

    public void sortColumnInOrder(String columnName, String order) {
        if(order.equalsIgnoreCase("ASCENDING")) {
            WebElement element = driverUtil.findElement(String.format(ASCENDING_ORDER_BUTTON_LOCATOR,columnName));
            if (element == null) {
                driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,columnName)).click();
                element = driverUtil.findElement(String.format(ASCENDING_ORDER_BUTTON_LOCATOR,columnName));
                if (element == null) {
                    driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,columnName)).click();
                }
            }
        }
        if(order.equalsIgnoreCase("DESCENDING")) {
            WebElement element = driverUtil.findElement(String.format(DESCENDING_ORDER_BUTTON_LOCATOR,columnName));
            if (element == null) {
                driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,columnName)).click();
                element = driverUtil.findElement(String.format(DESCENDING_ORDER_BUTTON_LOCATOR,columnName));
                if (element == null) {
                    driverUtil.findElement(String.format(CHANGE_ORDER_BUTTON_LOCATOR,columnName)).click();
                }
            }
        }
    }

    public void verifyLastAttemptedByInformationUpdatedAccurately() throws AutomationException {
        waitForLoadingPage();
        WebElement lastAttemptedByField = driverUtil.getWebElement(LAST_ATTEMPTED_BY_INFO_LOCATOR);
        if(lastAttemptedByField == null)
            throw new AutomationException("Last Attempted By information is not visible or may taking long time to load");
        String text =lastAttemptedByField.getText();
        if(!text.contains("Last attempted by"))
            throw new AutomationException("Last Attempted By information is not updated clearly");
    }

    public void clickOnNewlyCreatedTaskForInTasksTab(String identifier) throws AutomationException {
        scrollToTop();
        WebElement taskRecord = driverUtil.getWebElement(String.format(LAST_CREATED_LOG, identifier));
        if (taskRecord == null)
            throw new AutomationException(String.format("Unable to find newly created task contains: %s", identifier));
        taskRecord.click();
    }
}

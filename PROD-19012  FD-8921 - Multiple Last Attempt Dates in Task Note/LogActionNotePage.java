package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PatientPage.scrollToTop;


public class LogActionNotePage {
    public static final String LAST_CREATED_LOG= "//*[contains(@class,'mantine-Table-root')]//tbody/tr[1]// td//*[contains(.,'%s')]";
    public static final String ASCENDING_ORDER_BUTTON_LOCATOR = "//*[text()='%s']//..//button//..//*[contains(@class,'sort-ascending')]";
    public static final String DESCENDING_ORDER_BUTTON_LOCATOR = "//*[text()='%s']//..//button//..//*[contains(@class,'sort-descending')]";
    public static final String CHANGE_ORDER_BUTTON_LOCATOR = "//*[text()='%s' and contains(@class,'TableHeadCell')]/..//button";
    public static final String LAST_ATTEMPTED_BY_INFO_LOCATOR = "//*[contains(@class,'labelDisp')]//*[contains(text(),'Created by')]/preceding-sibling::*[contains(@class,'createdBy')]";

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

    public void verifyLastAttemptedByInformationUpdatedAccurately(String userNameAndDate) throws AutomationException {
        waitForLoadingPage();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yy");
        String currentDate = LocalDateTime.now().format(format);
        String CurrentNoteWithDate = null;
        try {
            CurrentNoteWithDate = (userNameAndDate + " " + (currentDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" Verification Text with Current Date =====" + CurrentNoteWithDate);

        WebElement lastAttemptedByField = driverUtil.getWebElementAndScroll(LAST_ATTEMPTED_BY_INFO_LOCATOR,10);
        if(lastAttemptedByField == null)
            throw new AutomationException("Last Attempted By information is not visible or may taking long time to load");
        CommonSteps.takeScreenshot();
        String text =lastAttemptedByField.getText();
        if(!text.contains(CurrentNoteWithDate))
            throw new AutomationException("Last Attempted By information is not updated clearly");
        WebDriverUtil.waitForAWhile(5);
    }

    public void clickOnNewlyCreatedTaskForInTasksTab(String identifier) throws AutomationException {
        scrollToTop();
        try {
            WebElement taskRecord = driverUtil.getWebElement(String.format(LAST_CREATED_LOG, identifier));
            if (taskRecord == null)
                throw new AutomationException(String.format("Unable to find newly created task contains: %s", identifier));
            taskRecord.click();
        } catch (Exception e) {
            WebElement taskRecord = driverUtil.getWebElement(String.format(LAST_CREATED_LOG, identifier));
            if (taskRecord == null)
                throw new AutomationException(String.format("Unable to find newly created task contains: %s", identifier));
            taskRecord.click();
        }
        WebDriverUtil.waitForAWhile(5);
    }
}

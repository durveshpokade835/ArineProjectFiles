package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.WebElement;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;

public class CustomFaxNumberForReportPage {

    public static final String FAX_ICON_IN_REPORT_SENT_TABLE = "//*[text()='Reports Sent']/following-sibling::*//table//tr[1]//td[4]";
    public static final String FAX_RADIO_BUTTON_FIELD_LOCATOR = "//*[@for='%s']/parent::*//input[contains(@class,'faxNumberSelection') and @type = 'radio']";
    public static final String FAX_INPUT_BUTTON_FIELD_LOCATOR = "//input[contains(@class,'inputText') and @type = 'text']";

    public void clickOnFaxIconFromReportSentTable() throws AutomationException {
        WebElement button = driverUtil.getWebElement(FAX_ICON_IN_REPORT_SENT_TABLE);
        if (button == null)
            throw new AutomationException("Unable to find PDF button in reports History table or it might taking too long time to load!");
        button.click();
        waitForLoadingPage();
    }
    public void selectFaxNumberToSendFax(String faxNumberField) throws AutomationException {
        WebElement radioButton = driverUtil.getWebElement(String.format(FAX_RADIO_BUTTON_FIELD_LOCATOR,faxNumberField));
        if (radioButton == null)
            throw new AutomationException("Unable to select "+faxNumberField+" radio button");
        radioButton.click();
    }

    public void enterCustomFaxNumber(String customFaxNumber) throws AutomationException {
        WebElement inputButton = driverUtil.getWebElement(FAX_INPUT_BUTTON_FIELD_LOCATOR);
        if (inputButton == null)
            throw new AutomationException("Unable to select custom fax number input button");
        inputButton.click();
        inputButton.sendKeys(customFaxNumber);
    }
}

package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class MTMPatientReportPage {

    Actions action = new Actions(DriverFactory.drivers.get());

    public void isFieldEnabled(DataTable dataTable) throws AutomationException {

        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            // Locate the element using the name attribute
            String xpath = "//*[@name='" + fieldName + "']";
            WebElement element = driverUtil.getWebElement(xpath);
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            // Check if the element has the 'disabled' attribute
            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute != null) {
                throw new AssertionError("Field '" + fieldName + "' is disabled.");
            }
        }
    }

    public void isFieldDisabled(DataTable dataTable) throws AutomationException {
        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            String xpath = "//*[@name='" + fieldName + "']";
            WebElement element = driverUtil.getWebElement(xpath);
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            // Check if the element has the 'disabled' attribute
            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute == null) {
                throw new AssertionError("Field '" + fieldName + "' is not disabled.");
            }

        }
    }

    public void verifyToolTipMessage(DataTable dataTable, String tooltipMessage) throws AutomationException {
        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            String xpath = "//*[@name='" + fieldName + "']";
            WebElement element = driverUtil.getWebElement(xpath);

            if (element == null)
                throw new AutomationException("'" + fieldName + "' button not present in Patient Pane or it might not visible");

            String tolTipMessage = element.getAttribute("data-tip");
            System.out.println("TolTip Message= " + tolTipMessage);

            action.moveToElement(element).perform();
            if (!tolTipMessage.equals(tooltipMessage))
                throw new AutomationException("Expected tooltip '" + tooltipMessage + "' not contains when mouse hover action perform on button: '" + fieldName + "'");
        }
    }
}

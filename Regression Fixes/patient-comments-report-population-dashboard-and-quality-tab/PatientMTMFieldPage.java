package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class PatientMTMFieldPage {

//    public static final String PATIENT_PANE_FIELD_LOCATOR = "//*[@name='%s']";
    public static final String PATIENT_PANE_FIELD_LOCATOR = "//*[text() = '%s']/parent::*//*[self::select[not(@type='hidden')] or self::input[not(@type='hidden')]]";
    Actions action = new Actions(DriverFactory.drivers.get());

    public void isFieldEnabled(DataTable dataTable) throws AutomationException {

        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            WebElement element = driverUtil.getWebElement(String.format(PATIENT_PANE_FIELD_LOCATOR, fieldName));
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute != null) {
                throw new AutomationException("Field '" + fieldName + "' is disabled.");
            }
        }
    }

    public void isFieldDisabled(DataTable dataTable) throws AutomationException {
        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            WebElement element = driverUtil.getWebElement(String.format(PATIENT_PANE_FIELD_LOCATOR, fieldName));
            if (element == null)
                throw new AutomationException("'" + fieldName + "'field not present in Patient Pane or it might not visible");

            String disabledAttribute = element.getAttribute("disabled");

            if (disabledAttribute == null) {
                throw new AutomationException("Field '" + fieldName + "' is not disabled.");
            }

        }
    }

    public void verifyToolTipMessage(DataTable dataTable, String tooltipMessage) throws AutomationException {
        List<String> fieldNames = dataTable.asList();

        for (String fieldName : fieldNames) {
            WebElement element = driverUtil.getWebElement(String.format(PATIENT_PANE_FIELD_LOCATOR, fieldName));

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

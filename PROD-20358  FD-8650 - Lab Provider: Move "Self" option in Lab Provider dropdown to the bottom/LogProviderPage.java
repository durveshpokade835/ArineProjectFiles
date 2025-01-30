package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.PatientPage.PROVIDER_DROPDOWN_INPUT;

public class LogProviderPage {
    public static final String DROPDOWN_OPTIONS = "//*[@role='option']";

    public void verifyTheDropdownHasFollowingOptions(String dropdown, List<String> expectedOptions) throws AutomationException {
        WebElement dropdownButton = driverUtil.getWebElement(PROVIDER_DROPDOWN_INPUT,5);
        if (dropdownButton == null)
            throw new AutomationException("Unable to locate PROVIDER dropdown!");
        dropdownButton.click();
        List<WebElement> elements = driverUtil.getWebElements(DROPDOWN_OPTIONS);
        if (elements == null || elements.isEmpty())
            throw new AutomationException("Unable to locate "+dropdown+" dropdown options");
        List<String> actualOptions = elements.stream().map(WebElement::getText).collect(Collectors.toList());
        if (!actualOptions.equals(expectedOptions)) {
            throw new AutomationException("Dropdown options do not match expected list! " +
                    "Expected: " + expectedOptions + ", Actual: " + actualOptions);
        }
    }
}

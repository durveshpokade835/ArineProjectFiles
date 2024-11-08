package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.arine.automation.glue.CommonSteps.driverUtil;

import java.util.Arrays;
import java.util.List;

public class LayoutPage {

    Actions actions = new Actions(DriverFactory.drivers.get());

    public static final String USER_INITIALS_DROPDOWN = "//button[span[contains(text(), '(DD')]]";
    public static final String DROPDOWN_MENU_ITEMS = "//li[text()='Manage Access'] | //li[text()='Layout'] | //li[text()='Sign Out']";
    public static final String LAYOUT_FIELD = "//li[contains(text(), 'Layout')]";
    public static final String LAYOUT_OPTIONS = "//li[text()='Layout']//following-sibling::div//li[contains(text(), 'Lock Layout') or contains(text(), 'Unlock Layout') or contains(text(), 'Reset Layout')]";
    public static final String MANAGE_ACCESS_FIELD = "//li[contains(text(), 'Manage Access')]";
    public static final String MANAGE_ACCESS_SUBMENU_ITEMS = "//li[text()='Manage Access']//following-sibling::div//li[text()='MFA Settings' or text()='Register SSO with GOOGLE']";


    // Method to open the user initials dropdown
    public void openUserInitialsDropdown() throws AutomationException {
        WebElement dropdownButton = driverUtil.getWebElementAndScroll(USER_INITIALS_DROPDOWN);
        dropdownButton.click(); // Click to open the dropdown
    }

    // Method to verify that the dropdown contains the expected options
    public void verifyDropdownOptions(String options) throws AutomationException {
        List<String> expectedOptions = Arrays.asList(options.split(",\\s*"));
        List<WebElement> dropdownItems = driverUtil.getWebElements(DROPDOWN_MENU_ITEMS);

        if (dropdownItems == null) {
            throw new AutomationException("Dropdown items not found.");
        }

        for (String expectedOption : expectedOptions) {
            boolean optionFound = dropdownItems.stream()
                    .anyMatch(item -> item.getText().equalsIgnoreCase(expectedOption));
            if (!optionFound) {
                throw new AutomationException("Dropdown option '" + expectedOption + "' not found.");
            }
        }
    }


    // Method to click on a specific field in the dropdown
    public void clickOnField(String fieldName) throws AutomationException {
        String locator;

        if ("Layout".equalsIgnoreCase(fieldName)) {
            locator = LAYOUT_FIELD;
        } else if ("Manage Access".equalsIgnoreCase(fieldName)) {
            locator = MANAGE_ACCESS_FIELD;
        } else {
            throw new AutomationException("Field not recognized: " + fieldName);
        }

        WebElement field = driverUtil.getWebElement(locator);
        if (field == null) {
            throw new AutomationException("Field '" + fieldName + "' not found.");
        }
        field.click(); // Perform the click action
    }

    // Method to verify that the submenu options are displayed when hovering on the Layout field
    public void verifyDropdownSubmenuOptions(String fieldName, String expectedOptions) throws AutomationException {
        // Locate the submenu associated with the Layout field

        // Get the expected options
        List<String> expectedSubmenuOptions = Arrays.asList(expectedOptions.split(",\\s*"));

        // Perform hover action to display the submenu
        WebElement layoutField = driverUtil.getWebElementAndScroll(LAYOUT_FIELD);
        actions.moveToElement(layoutField).perform();

        // Wait for submenu to be visible (you might need to adjust wait time or implement a proper wait)
        driverUtil.applyWait(LAYOUT_OPTIONS, 5);

        // Get submenu items
        List<WebElement> submenuItems = driverUtil.getWebElements(LAYOUT_OPTIONS);
        if (submenuItems == null || submenuItems.isEmpty()) {
            throw new AutomationException("Submenu items not found for '" + fieldName + "' field.");
        }

        // Verify that each expected option is present in the submenu
        for (String expectedOption : expectedSubmenuOptions) {
            boolean optionFound = submenuItems.stream()
                    .anyMatch(item -> item.getText().equalsIgnoreCase(expectedOption));
            if (!optionFound) {
                throw new AutomationException("Submenu option '" + expectedOption + "' not found.");
            }
        }
    }


    public void verifyManageAccessSubmenuOptions(String option1, String option2) throws AutomationException {
        // Combine the options into a single string
        String expectedOptions = option1 + ", " + option2;

        // Split the expected options into a list
        List<String> expectedSubmenuOptions = Arrays.asList(expectedOptions.split(",\\s*"));

        // Perform hover action to display the submenu
        WebElement manageAccessField = driverUtil.getWebElementAndScroll(MANAGE_ACCESS_FIELD);
        actions.moveToElement(manageAccessField).perform();

        // Wait for submenu to be visible
        driverUtil.applyWait(MANAGE_ACCESS_SUBMENU_ITEMS, 5);

        // Get submenu items
        List<WebElement> submenuItems = driverUtil.getWebElements(MANAGE_ACCESS_SUBMENU_ITEMS);
        if (submenuItems == null || submenuItems.isEmpty()) {
            throw new AutomationException("Submenu items not found for 'Manage Access' field.");
        }

        // Verify that each expected option is present in the submenu
        for (String expectedOption : expectedSubmenuOptions) {
            boolean optionFound = submenuItems.stream()
                    .anyMatch(item -> item.getText().equalsIgnoreCase(expectedOption));
            if (!optionFound) {
                throw new AutomationException("Submenu option '" + expectedOption + "' not found.");
            }
        }
    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------

    private final String initialsDropdown = "//button[contains(@class, 'node_modules-react-multilevel-dropdown-')]/span[contains(text(),'')]";
    private final String layoutLockUnlockState = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private final String layoutOptionButton = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[text()='%s']";
    private final String layoutOptionText = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private final String layoutElement = "//div[contains(@class,'-PatientTriageSelection')]";

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(initialsDropdown);
        if (dropdown == null) throw new AutomationException("Initials dropdown not found.");
        dropdown.click();
    }

    public void hoverOverLayoutOption(String layoutOption) throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionButton, layoutOption));
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
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText, expectedOptionText));

        if ((isLocked && layoutElement == null) || (!isLocked && layoutElement != null)) {
            if (layoutOptionElement == null) {
                throw new AutomationException("Layout option element not found for expected text '" + expectedOptionText + "'.");
            }
            String actualOptionText = layoutOptionElement.getText();
            if (!actualOptionText.equals(expectedOptionText)) {
                throw new AutomationException("The layout option text '" + actualOptionText + "' did not match the expected text '" + expectedOptionText + "'.");
            }
        } else {
            String alternativeOptionText = expectedOptionText.equalsIgnoreCase("Unlock Layout") ? "Lock Layout" : "Unlock Layout";
            layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText, alternativeOptionText));
            if (layoutOptionElement == null) {
                throw new AutomationException("Alternative layout option element not found for expected text '" + alternativeOptionText + "'.");
            }
            layoutOptionElement.click();
            String actualOptionText = layoutOptionElement.getText();
            if (!actualOptionText.equals(expectedOptionText)) {
                throw new AutomationException("The layout option text after clicking '" + actualOptionText + "' did not match the expected text '" + expectedOptionText + "'.");
            }
        }
    }

    public void clickSelectedLayoutOption(String expectedLayoutOption) throws AutomationException {
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText, expectedLayoutOption));
        WebElement layoutElement = driverUtil.getWebElement(layoutLockUnlockState);

        if ((expectedLayoutOption.equalsIgnoreCase("Unlock Layout") && layoutElement == null) ||
                (expectedLayoutOption.equalsIgnoreCase("Lock Layout") && layoutElement != null)) {
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

    //--------------------------------------------------------------------------------------------------------------

    private static final String INITIAL_DROPDOWN = "//*[text()='(DD)']/..";
    private static final String LAYOUT_LOCK_UNLOCK_STATE = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private static final String LAYOUT_OPTION_TEXT = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private static final String LAYOUT_TOP_VALUE = "(//div[contains(@class,'react-grid-item')])[1]";

    private int initialTopPosition;
    private int savedTopPosition;

    public void clickOnDropDown() throws AutomationException {
        driverUtil.getWebElement(INITIAL_DROPDOWN).click();
    }

    public void hoverOnOption(String option) throws AutomationException {
        WebElement optionElement = driverUtil.getWebElement("//li[text()='" + option + "']");
        new Actions(DriverFactory.drivers.get()).moveToElement(optionElement).perform();
    }

    public void clickOnOption(String option) throws AutomationException {
        WebElement optionButton = driverUtil.getWebElement(String.format(LAYOUT_OPTION_TEXT, option));
        WebElement layoutElement = driverUtil.getWebElement(LAYOUT_LOCK_UNLOCK_STATE);

        if ((option.equalsIgnoreCase("Unlock Layout") && layoutElement == null) ||
                (option.equalsIgnoreCase("Lock Layout") && layoutElement != null) ||
                option.equalsIgnoreCase("Reset Layout")) {
            optionButton.click();

        }
    }

    private int extractTopPosition(String style) {
        for (String property : style.split(";")) {
            if (property.trim().startsWith("top:")) {
                return Integer.parseInt(property.split(":")[1].trim().replace("px", ""));
            }
        }
        throw new IllegalArgumentException("Top position not found in style attribute.");
    }

    public boolean isLayoutMovable2() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        int initialY = layout.getLocation().getY();

        actions.dragAndDropBy(layout, 0, 400).perform();
        return initialY != layout.getLocation().getY();
    }

    public void captureInitialLayoutPosition() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        initialTopPosition = extractTopPosition(layout.getAttribute("style"));
        savedTopPosition = initialTopPosition;
        System.out.println("Captured Initial Position: Top = " + initialTopPosition);
    }

    public boolean moveLayout() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.dragAndDropBy(layout, 0, 400).perform();

        savedTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Moved Layout Position: Top = " + savedTopPosition);

        return true;
    }

    public boolean isLayoutInSavedPosition() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Current Top Position: " + currentTopPosition);
        return savedTopPosition == currentTopPosition;
    }

    public boolean isLayoutReset() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Current Top Position for Reset Check: " + currentTopPosition);

        return initialTopPosition == currentTopPosition;
    }
}

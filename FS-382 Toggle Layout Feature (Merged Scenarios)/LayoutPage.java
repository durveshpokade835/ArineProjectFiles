package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.glue.CommonSteps;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.arine.automation.pages.BasePage.driverUtil;

public class LayoutPage {

    private static final String DROPDOWN_MENU_ITEMS = "//li[text()='Manage Access'] | //li[text()='Layout'] | //li[text()='Sign Out']";
    private static final String LAYOUT_FIELD = "//li[contains(text(), 'Layout')]";
    private static final String LAYOUT_OPTIONS = "//li[text()='Layout']//following-sibling::div//li[contains(text(), 'Lock Layout') or contains(text(), 'Unlock Layout') or contains(text(), 'Reset Layout')]";
    private static final String MANAGE_ACCESS_FIELD = "//li[contains(text(), 'Manage Access')]";
    private static final String MANAGE_ACCESS_SUBMENU_ITEMS = "//li[text()='Manage Access']//following-sibling::div//li[text()='MFA Settings' or text()='Register SSO with GOOGLE']";

    private static final String initialsDropdown = "//button[contains(@class, 'node_modules-react-multilevel-dropdown-')]/span[contains(text(),'')]";
    private static final String layoutLockUnlockState = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private static final String layoutOptionText = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private static final String layoutElementD = "//div[contains(@class,'-PatientTriageSelection')]";

    private static final String INITIAL_DROPDOWN = "//*[text()='(DD)']/..";
    private static final String LAYOUT_LOCK_UNLOCK_STATE = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private static final String LAYOUT_OPTION_TEXT = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private static final String LAYOUT_TOP_VALUE = "(//div[contains(@class,'react-grid-item')])[1]";

    private static final String ADD_MEDICINE_ICON = "//*[contains(@class,'MedDetailTable') and .//*[text()='Rx']]//*[contains(@class,'iconAdd')]";
//    private static final String MEDICATION_DETAILS_PANE = "";
//    private static final String PRESCRIBER_FIELD = "";
    private static final String LAYOUT_SETTINGS = "//*[(contains(text(),'Unlock Layout') or contains(text(),'Lock Layout')) and //*[contains(@class,'submenu')]]";
    private static final String DRUG_NAME = "//*[contains(text(),'Drug Name')]/following-sibling::input";
    private static final String SEARCH_BUTTON_LOCATOR = "//*[contains(@class,'svg-inline')]";
    private static final String LOADER= "//*[contains(@class,'sync')]";
    private static final String ADD_MEDICINE_BUTTON = "//*[contains(text(),'Add Med')]";
    private static final String POPUP_CONTAINER = "//*[contains(@class,'popupLoaded')]";
    private static final String TABLE_CONTAINER = "//table[contains(@class,'mantine-Table-root') and .//*[contains(@class,'MedDetailTable')]]";
    private static final String TABLE_DATA_LOCATOR = "//div[contains(text(),'Rx')]/ancestor::table//tr/td[position()='%d']//span[contains(text(),'%s')]";
    private static final String PRESCRIBER_INPUT_BUTTON = "//*[contains(@class,'mantine-Select-input') and //*[contains(text(),'Prescriber:')]]";


    public int initialTopPosition;
    private int savedTopPosition;
    private int initialSavedPosition;

    Actions actions = new Actions(DriverFactory.drivers.get());

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
        field.click();
    }

    public void verifyDropdownSubmenuOptions(String fieldName, String expectedOptions) throws AutomationException {
        List<String> expectedSubmenuOptions = Arrays.asList(expectedOptions.split(",\\s*"));

        WebElement layoutField = driverUtil.getWebElementAndScroll(LAYOUT_FIELD);
        actions.moveToElement(layoutField).perform();

        driverUtil.applyWait(LAYOUT_OPTIONS, 5);

        List<WebElement> submenuItems = driverUtil.getWebElements(LAYOUT_OPTIONS);
        if (submenuItems == null || submenuItems.isEmpty()) {
            throw new AutomationException("Submenu items not found for '" + fieldName + "' field.");
        }

        for (String expectedOption : expectedSubmenuOptions) {
            boolean optionFound = submenuItems.stream()
                    .anyMatch(item -> item.getText().equalsIgnoreCase(expectedOption));
            if (!optionFound) {
                throw new AutomationException("Submenu option '" + expectedOption + "' not found.");
            }
        }
    }

    public void verifyManageAccessSubmenuOptions(String option1, String option2) throws AutomationException {
        String expectedOptions = option1 + ", " + option2;

        List<String> expectedSubmenuOptions = Arrays.asList(expectedOptions.split(",\\s*"));
        WebElement manageAccessField = driverUtil.getWebElementAndScroll(MANAGE_ACCESS_FIELD);
        actions.moveToElement(manageAccessField).perform();

        driverUtil.applyWait(MANAGE_ACCESS_SUBMENU_ITEMS, 5);
        List<WebElement> submenuItems = driverUtil.getWebElements(MANAGE_ACCESS_SUBMENU_ITEMS);
        if (submenuItems == null || submenuItems.isEmpty()) {
            throw new AutomationException("Submenu items not found for 'Manage Access' field.");
        }

        for (String expectedOption : expectedSubmenuOptions) {
            boolean optionFound = submenuItems.stream()
                    .anyMatch(item -> item.getText().equalsIgnoreCase(expectedOption));
            if (!optionFound) {
                throw new AutomationException("Submenu option '" + expectedOption + "' not found.");
            }
        }
    }

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(initialsDropdown);
        if (dropdown == null) throw new AutomationException("Initials dropdown not found.");
        dropdown.click();
    }

    public void verifyLayoutOptionForLockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, true);
    }

    public void verifyLayoutOptionForUnlockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, false);
    }

    private void verifyLayoutOptionText(String expectedOptionText, boolean isLocked) throws AutomationException {
        WebElement layoutElementD = driverUtil.getWebElement(layoutLockUnlockState);
        WebElement layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText, expectedOptionText));
        if ((isLocked && layoutElementD == null) || (!isLocked && layoutElementD != null)) {
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text'" + expectedOptionText + "'.");
        } else {
            String optionText = expectedOptionText.equalsIgnoreCase("Unlock Layout") ? "Lock Layout" : "Unlock Layout";
            layoutOptionElement = driverUtil.getWebElement(String.format(layoutOptionText, optionText));
            layoutOptionElement.click();
            String actualOptionText = layoutOptionElement.getText();
            Assert.assertEquals(actualOptionText, expectedOptionText, "The layout option text did not match the expected text '" + expectedOptionText + "'.");
        }
    }

    public boolean isLayoutMovableD() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(layoutElementD);
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

    public boolean isLayoutMovable() throws AutomationException {
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
        initialSavedPosition = initialTopPosition;
        System.out.println("Initial saved to position: " + initialSavedPosition);
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
        System.out.println("Initial Top Position: " + initialSavedPosition);
        System.out.println("Current Top Position for Reset Check: " + currentTopPosition);

        return initialSavedPosition == currentTopPosition;
    }

    public void verifyLayoutState() throws AutomationException {
        clickOnDropDown();
        hoverOnOption("Layout");
        WebElement optionButton = driverUtil.getWebElement(LAYOUT_SETTINGS);
        if (optionButton == null) throw new AutomationException("Layout Option Not Found");
        String option = optionButton.getText();
        WebElement layoutElement = driverUtil.getWebElement(LAYOUT_LOCK_UNLOCK_STATE);

        if ((option.equalsIgnoreCase("Unlock Layout") && layoutElement == null) ||
                (option.equalsIgnoreCase("Lock Layout") && layoutElement != null)) {
            String ACTIVE_STATE = option.equalsIgnoreCase("Unlock Layout") ? "Lock" : "Unlock";
            System.out.println("Active State is " + ACTIVE_STATE);
        }
    }

    public void addNewMedicationWithoutPrescriber(String medicine) throws AutomationException {
        WebElement addMedIcon = driverUtil.getWebElement(ADD_MEDICINE_ICON);
        if (addMedIcon == null)
            throw new AutomationException("Unable to find add new medicine icon!");
        addMedIcon.click();
        try {
            addNewMedicationDetails(medicine);
            System.out.println("Added new medication without assigning a prescriber.");
        } catch (Exception e) {
            throw new AutomationException("Failed to add medication details: " + e.getMessage());
        }
    }

    private void addNewMedicationDetails(String medicine) throws AutomationException {
        WebElement drugElement = driverUtil.getWebElement(DRUG_NAME);
        if (drugElement == null) {
            throw new AutomationException("Unable to locate the Drug Name field!");
        }

        drugElement.sendKeys(medicine);

        try {
            WebDriverUtil.waitForVisibleElement(By.xpath(SEARCH_BUTTON_LOCATOR), 5);
            WebElement addIcon = driverUtil.getWebElement(SEARCH_BUTTON_LOCATOR);
            if (addIcon == null) {
                throw new AutomationException("Add icon not found after entering drug name.");
            }
            addIcon.click();

            WebDriverUtil.waitForInvisibleElement(By.xpath(LOADER), 5);

            WebElement addedMedElement = driverUtil.getWebElement("//div[contains(text(),'"+medicine.toLowerCase()+"')]", 5);
            if (addedMedElement == null) {
                throw new AutomationException("Unable to locate '"+medicine+"' medicine entry.");
            }
            addedMedElement.click();

            WebDriverUtil.waitForVisibleElement(By.xpath(ADD_MEDICINE_BUTTON), 5);
            WebElement addMedButton = driverUtil.getWebElementAndScroll(ADD_MEDICINE_BUTTON, 10);
            if (addMedButton == null) {
                throw new AutomationException("Add Med button not found on confirmation step.");
            }
            addMedButton.click();

            WebDriverUtil.waitForInvisibleElement(By.xpath(LOADER), 5);
        } catch (Exception e) {
            throw new AutomationException("Error during medication addition: " + e.getMessage());
        }
    }

    public void selectMedicationAndChangePrescriber(String medicine, String prescriberOption) throws AutomationException {
        try {
            // Wait for any pop-ups to close and the medication table to become visible
            WebDriverUtil.waitForInvisibleElement(By.xpath(POPUP_CONTAINER), 5);
            WebDriverUtil.waitForVisibleElement(By.xpath(TABLE_CONTAINER), 5);

            // Scroll to the medication table
            driverUtil.getWebElementAndScroll(TABLE_CONTAINER);

            // Determine the column index of 'Rx' header
            int index = driverUtil.getWebElements("//div[contains(text(),'Rx')]/ancestor::th/preceding-sibling::*").size() + 1;

            // Locate the specific medication (e.g., 'TESTOZOLE') in the table and scroll to it
            WebElement rxDataElement = driverUtil.getWebElementAndScroll(String.format(TABLE_DATA_LOCATOR, index,medicine));
            if (rxDataElement == null) {
                throw new AutomationException("Medication '"+medicine+"' not found in the medication table.");
            }
            rxDataElement.click();

            // Locate and click on the prescriber dropdown
            WebElement prescriberDropdown = driverUtil.getWebElementAndScroll(PRESCRIBER_INPUT_BUTTON);
            if (prescriberDropdown == null) {
                throw new AutomationException("Prescriber dropdown not found.");
            }
            prescriberDropdown.click();

            // Select the "Self" option from the dropdown
            WebElement selfOption = driverUtil.getWebElement(String.format("//*[contains(text(),'"+prescriberOption+"')]"));
            if (selfOption == null) {
                throw new AutomationException("Option '"+prescriberOption+"' not found in the prescriber dropdown.");
            }
            selfOption.click();

            System.out.println("Medication selected and prescriber changed to '"+prescriberOption+"'.");

        } catch (Exception e) {
            throw new AutomationException("Failed to select medication and change prescriber: " + e.getMessage());
        }
    }

    public void verifyCursorNotStuck() throws AutomationException {
        // To verify that the cursor is not stuck, add a delay and check for responsiveness
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement randomContainer = driverUtil.getWebElement("//div[contains(@class,'leftContainer')]", 5);

        // Attempt to interact with the page to verify that the cursor is responsive
        try {
            randomContainer.click();
            System.out.println("Cursor is responsive, not stuck.");
        } catch (Exception e) {
            throw new AssertionError("Cursor appears to be stuck after changing the prescriber.");
        }
    }
}

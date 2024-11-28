package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.arine.automation.pages.BasePage.driverUtil;

public class LayoutPage {

    private static final String INITIAL_DROPDOWN = "//button[contains(@class, 'MenuBar') and contains(@class,'dropdown')]";
    private static final String INITIAL_DROPDOWN_OPTIONS = "//*[contains(@class,'multilevel-dropdown-__menu')]//li[text()='%s']";
    private static final String SUB_MENU_OPTIONS = "//*[contains(@class,'multilevel-dropdown-__menu')]//li[contains(text(),'%s')]";
    private static final String LAYOUT_LOCK_UNLOCK_STATE = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private static final String LAYOUT_ELEMENT_D = "//div[contains(@class,'-PatientTriageSelection')]";
    private static final String ADD_MEDICINE_ICON = "//*[contains(@class,'MedDetailTable') and .//*[text()='Rx']]//*[contains(@class,'iconAdd')]";
    private static final String TABLE_CONTAINER = "//table[contains(@class,'mantine-Table-root') and .//*[contains(@class,'MedDetailTable')]]";
    private static final String DRUG_NAME = "//*[contains(text(),'Drug Name')]/following-sibling::input";
    private static final String SEARCH_BUTTON_LOCATOR = "//*[contains(@class,'svg-inline')]";
    private static final String LOADER = "//*[contains(@class,'sync')]";
    private static final String ADD_MEDICINE_BUTTON = "//*[contains(text(),'Add Med')]";
    private static final String POPUP_CONTAINER = "//*[contains(@class,'popupLoaded')]";
    private static final String TABLE_DATA_LOCATOR = "//div[contains(text(),'Rx')]/ancestor::table//tr/td[position()='%d']//span[contains(text(),'%s')]";
    private static final String PRESCRIBER_INPUT_BUTTON = "//*[contains(@class,'mantine-Select-input') and //*[contains(text(),'Prescriber:')]]";
    private static final String RANDOM_CONTAINER = "//div[contains(@class,'leftContainer')]";
    private static final String DELETE_WARNING_POPUP = "//*[contains(@class,'swal2-icon-warning')]";
    private static final String DELETE_CONFIRMATION_BUTTON = "//*[contains(@class,'swal2-confirm')]";
    private static final String LAYOUT_SETTINGS = "//*[(contains(text(),'Unlock Layout') or contains(text(),'Lock Layout')) and //*[contains(@class,'submenu')]]";
    private static final String LAYOUT_TOP_VALUE = "//div[contains(@class,'-PatientTriageSelection')]/parent::div[contains(@style,'top')]";

    public int initialTopPosition;
    private int savedTopPosition;
    private int initialSavedPosition;

    Actions actions = new Actions(DriverFactory.drivers.get());

    public void clickInitialsDropdown() throws AutomationException {
        WebElement dropdown = driverUtil.getWebElement(INITIAL_DROPDOWN, 2);
        if (dropdown == null) throw new AutomationException("Initials dropdown not found.");
        dropdown.click();
    }

    public void verifyInitialsDropdownHasOption(List<String> Options) throws AutomationException {
        driverUtil.waitForAWhile(2);
        for (String Option : Options) {
            WebElement element = driverUtil.getWebElement(String.format(INITIAL_DROPDOWN_OPTIONS, Option), 3);
            if (element == null)
                throw new AutomationException("Initials dropdown :" + Option + " not found");
        }
    }

    public void hoverOnOption(String Option) throws AutomationException {
        WebElement optionElement = driverUtil.getWebElement(String.format(INITIAL_DROPDOWN_OPTIONS, Option), 3);
        if (optionElement == null)
            throw new AutomationException("Unable to locate " + Option + " field");
        actions.moveToElement(optionElement).perform();
    }

    public void verifyManageAccessHasOption(List<String> Options) throws AutomationException {
        driverUtil.waitForAWhile(2);
        for (String Option : Options) {
            WebElement element = driverUtil.getWebElement(String.format(SUB_MENU_OPTIONS, Option), 3);
            if (element == null)
                throw new AutomationException("Manage Access : " + Option + " not found");
        }
    }

public void verifyLayoutHasOption(String layoutOption, String layoutState, String resetLayout) throws AutomationException {
    try {
        if (layoutState.equalsIgnoreCase("Locked")) {
            verifyLayoutOptionForLockedState(layoutOption);
        } else if (layoutState.equalsIgnoreCase("Unlocked")) {
            verifyLayoutOptionForUnlockedState(layoutOption);
        } else {
            throw new IllegalArgumentException("Invalid layout state: " + layoutState);
        }
        driverUtil.waitForAWhile(2);

        WebElement resetLayoutElement = driverUtil.getWebElement(String.format(INITIAL_DROPDOWN_OPTIONS, resetLayout), 3);

        if (resetLayoutElement == null)
            throw new AutomationException("Dropdown option '" + resetLayout + "' not found");

    } catch (AutomationException e) {
        e.printStackTrace();
        throw new AutomationException("AutomationException occurred: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        throw new AutomationException("An unexpected error occurred while verifying layout options :"+ e.getMessage());
    }
}

    public void verifyLayoutOptionForLockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, true);
    }

    public void verifyLayoutOptionForUnlockedState(String expectedOptionText) throws AutomationException {
        verifyLayoutOptionText(expectedOptionText, false);
    }

    private void verifyLayoutOptionText(String expectedOptionText, boolean isLocked) throws AutomationException {
        WebElement layoutElementD = driverUtil.getWebElement(LAYOUT_LOCK_UNLOCK_STATE);
        WebElement layoutOptionElement;

        if ((isLocked && layoutElementD == null) || (!isLocked && layoutElementD != null)) {
            layoutOptionElement = driverUtil.getWebElement(String.format(SUB_MENU_OPTIONS, expectedOptionText));
            if (layoutOptionElement == null)
                throw new AutomationException(expectedOptionText + " option not found");
            String actualOptionText = layoutOptionElement.getText();

            if (!actualOptionText.equalsIgnoreCase(expectedOptionText))
                throw new AutomationException("The layout option text did not match the expected text'" + expectedOptionText + "'.");
        } else {
            String optionText = expectedOptionText.equalsIgnoreCase("Unlock Layout") ? "Lock Layout" : "Unlock Layout";
            layoutOptionElement = driverUtil.getWebElement(String.format(SUB_MENU_OPTIONS, optionText));
            if (layoutOptionElement == null)
                throw new AutomationException(optionText + " option not found");
            layoutOptionElement.click();

            String actualOptionText = layoutOptionElement.getText();
            if (!actualOptionText.equalsIgnoreCase(expectedOptionText))
                throw new AutomationException("The layout option text did not match the expected text '" + expectedOptionText + "'.");
        }
    }

    public void clickOnOption(String option) throws AutomationException {

        WebElement layoutElement = driverUtil.getWebElement(LAYOUT_LOCK_UNLOCK_STATE);

        if ((option.equalsIgnoreCase("Unlock Layout") && layoutElement == null) ||
                (option.equalsIgnoreCase("Lock Layout") && layoutElement != null) ||
                option.equalsIgnoreCase("Reset Layout")) {
            WebElement optionButton = driverUtil.getWebElement(String.format(SUB_MENU_OPTIONS, option));
            if (optionButton == null) throw new AutomationException(option + " button not found");
            optionButton.click();
        }
    }

    public void isLayoutMovableD() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_ELEMENT_D);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        int initialX = layout.getLocation().getX();
        int initialY = layout.getLocation().getY();

        actions.dragAndDropBy(layout, 0, 400).perform();
        int movedX = layout.getLocation().getX();
        int movedY = layout.getLocation().getY();

        boolean isMovable = (initialX != movedX) || (initialY != movedY);
        if (!isMovable)
            throw new AutomationException("The layout is not movable when unlocked.");
    }

    public void isLayoutMovable() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        int initialY = layout.getLocation().getY();

        actions.dragAndDropBy(layout, 0, 400).perform();
        int finalY = layout.getLocation().getY();

        if (initialY != finalY) throw new AutomationException("The layout is movable when Locked.");
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

    private int extractTopPosition(String style) {
        for (String property : style.split(";")) {
            if (property.trim().startsWith("top:")) {
                return Integer.parseInt(property.split(":")[1].trim().replace("px", ""));
            }
        }
        throw new IllegalArgumentException("Top position not found in style attribute.");
    }

    public void moveLayout() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.dragAndDropBy(layout, 0, 400).perform();

        savedTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Moved Layout Position: Top = " + savedTopPosition);

    }

    public void isLayoutReset() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Initial Top Position: " + initialSavedPosition);
        System.out.println("Current Top Position for Reset Check: " + currentTopPosition);

        if (initialSavedPosition != currentTopPosition)
            throw new AutomationException("The layout position did not reset to the initial layout position.");
    }

    public void isLayoutInSavedPosition() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Current Top Position: " + currentTopPosition);
        if (savedTopPosition != currentTopPosition)
            throw new AutomationException("The layout did not load in the saved position on re-login.");
    }

    public void verifyLayoutState() throws AutomationException {
        clickInitialsDropdown();
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
            driverUtil.getWebElementAndScroll(TABLE_CONTAINER);
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

            WebElement addedMedElement = driverUtil.getWebElement("//div[contains(text(),'" + medicine.toLowerCase() + "')]", 5);
            if (addedMedElement == null) {
                throw new AutomationException("Unable to locate '" + medicine + "' medicine entry.");
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
            WebDriverUtil.waitForInvisibleElement(By.xpath(POPUP_CONTAINER), 3);
            WebDriverUtil.waitForVisibleElement(By.xpath(TABLE_CONTAINER), 3);

            driverUtil.getWebElementAndScroll(TABLE_CONTAINER);

            int index = driverUtil.getWebElements("//div[contains(text(),'Rx')]/ancestor::th/preceding-sibling::*").size() + 1;

            WebElement rxDataElement = driverUtil.getWebElementAndScroll(String.format(TABLE_DATA_LOCATOR, index, medicine));
            if (rxDataElement == null) {
                throw new AutomationException("Medication '" + medicine + "' not found in the medication table.");
            }
            WebDriverUtil.waitForElementClickable(By.xpath(String.format(TABLE_DATA_LOCATOR, index, medicine)), 5);
            rxDataElement.click();

            WebElement prescriberDropdown = driverUtil.getWebElementAndScroll(PRESCRIBER_INPUT_BUTTON);
            if (prescriberDropdown == null) {
                throw new AutomationException("Prescriber dropdown not found.");
            }
            prescriberDropdown.click();

            WebElement selfOption = driverUtil.getWebElement(String.format("//*[contains(text(),'" + prescriberOption + "')]"));
            if (selfOption == null) {
                throw new AutomationException("Option '" + prescriberOption + "' not found in the prescriber dropdown.");
            }
            selfOption.click();

            System.out.println("Medication selected and prescriber changed to '" + prescriberOption + "'.");

        } catch (Exception e) {
            throw new AutomationException("Failed to select medication and change prescriber: " + e.getMessage());
        }
    }

    public void verifyCursorNotStuck() throws AutomationException {
        WebElement randomContainer = driverUtil.getWebElement(RANDOM_CONTAINER, 5);

        try {
            randomContainer.click();
            System.out.println("Cursor is responsive, not stuck.");
        } catch (Exception e) {
            throw new AssertionError("Cursor appears to be stuck after changing the prescriber.");
        }
    }

    public void deleteMedicine(String medicine) throws AutomationException {
        try {
            driverUtil.getWebElementAndScroll(TABLE_CONTAINER);

            int index = driverUtil.getWebElements("//div[contains(text(),'Rx')]/ancestor::th/preceding-sibling::*").size() + 1;

            String rxDataLocator = String.format(TABLE_DATA_LOCATOR + "//following-sibling::span", index, medicine);
            WebElement rxDataElement = driverUtil.getWebElementAndScroll(rxDataLocator);

            if (rxDataElement == null) {
                throw new AutomationException("Medication '" + medicine + "' not found in the medication table.");
            }

            WebDriverUtil.waitForElementClickable(By.xpath(String.format(TABLE_DATA_LOCATOR, index, medicine)), 5);
            rxDataElement.click();

            WebDriverUtil.waitForVisibleElement(By.xpath(DELETE_WARNING_POPUP));

            WebElement deleteButton = driverUtil.getWebElement(DELETE_CONFIRMATION_BUTTON);
            if (deleteButton == null) {
                throw new AutomationException("Delete button not found in the confirmation popup.");
            }
            deleteButton.click();

            WebDriverUtil.waitForInvisibleElement(By.xpath(DELETE_WARNING_POPUP));

            driverUtil.getWebElementAndScroll(TABLE_CONTAINER);

            System.out.println("Medication '" + medicine + "' successfully deleted.");

        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new AutomationException("Timed out while performing actions during the deletion of medication '" + medicine + "'.");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new AutomationException("Unable to locate a required element during the deletion of medication '" + medicine + "'.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("An unexpected error occurred while deleting medication '" + medicine + "': " + e.getMessage());
        }
    }
}
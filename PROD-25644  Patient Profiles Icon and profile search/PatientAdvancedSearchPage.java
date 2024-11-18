package com.arine.automation.pages;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.util.WebDriverUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;

public class PatientAdvancedSearchPage {

    private static final String MBI_LOCATOR = "//input[@name='mbi']";
    private static final String SEARCH_ICON = "//form//*[@data-icon='search']";
    private static final String COLUMN_INDEX_LOCATOR = "//*[contains(@class,'drugSelectContainer')]//th[contains(text(),'%s')]//ancestor::th/preceding-sibling::*";
    private static final String STATUS_COLUMN_CELLS = "//*[contains(@class,'drugSelectContainer')]//td[%s]";
    /**
     * Performs an advanced search by MBI on the Patient Advanced Search modal.
     *
     * @param mbi The MBI to search for.
     * @throws AutomationException if the MBI input field is not found or not interactable.
     */
    public void advanceSearchByMBI(String mbi) throws AutomationException {
        try {
            WebElement mbiInput = driverUtil.getWebElement(MBI_LOCATOR);
            if (mbiInput == null) {
                throw new AutomationException("Unable to locate MBI input in the advanced search form.");
            }
            mbiInput.sendKeys(mbi, Keys.ENTER);
            driverUtil.waitForElementToBeClickable(SEARCH_ICON);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Failed to perform advance search by MBI: " + e.getMessage());
        }
    }
    /**
     * Verifies the "Status" column values in the search results against the expected statuses.
     *
     * @param columnName The name of the column to verify.
     * @param expectedStatuses A list of expected statuses.
     * @throws AutomationException if any unexpected status is found.
     */
    public void verifyStatusColumn(String columnName, List<String> expectedStatuses) throws AutomationException {
        try {
            // Get the index of the column
            int columnIndex = driverUtil.getWebElements(String.format(COLUMN_INDEX_LOCATOR, columnName)).size() + 1;

            // Fetch all cells under the "Status" column
            List<WebElement> statusCells = driverUtil.getWebElements(String.format(STATUS_COLUMN_CELLS, columnIndex));
            if (statusCells.isEmpty()) {
                throw new AutomationException("No data found in the '" + columnName + "' column.");
            }
            // Check for invalid statuses
            List<String> invalidStatuses = new ArrayList<>();
            for (WebElement cell : statusCells) {
                String cellValue = cell.getText().trim();
                if (expectedStatuses.stream().noneMatch(cellValue::contains)) {
                    invalidStatuses.add(cellValue);
                }
            }
            // Validate and handle results
            if (invalidStatuses.isEmpty()) {
                System.out.println("Test Passed: All statuses are valid for column '" + columnName + "'.");
            } else {
                throw new AutomationException("Test Failed: Invalid statuses found in column '"
                        + columnName + "': " + invalidStatuses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AutomationException("Failed to verify the '" + columnName + "' column: " + e.getMessage());
        }
    }
}
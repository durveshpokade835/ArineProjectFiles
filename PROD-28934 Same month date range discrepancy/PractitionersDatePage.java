package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.arine.automation.glue.CommonSteps.driverUtil;
import static com.arine.automation.pages.BasePage.waitForLoadingPage;
import static com.arine.automation.pages.PractitionersPage.*;

public class PractitionersDatePage {
    String finalDate, fromDate = null, currentDay = null;
    private String startDate;
    private String endDate;

    private String reportCreatedDateColumnIndex = "//div[contains(text(),'%s')]/ancestor::th/preceding-sibling::*";
    private String reportCreatedDateColumn ="//div[contains(text(),'%s')]/ancestor::table//tr/td[position() =%s]";
//    public void selectDateTaskFilter(String filterName, String fromDate, String toDate, String buttonName) throws AutomationException, ParseException, AutomationException {
//        Actions actions = new Actions(DriverFactory.drivers.get());
//        WebElement selectTaskFilterIcon = driverUtil.getWebElement(PageFactory.prescriberAnalyticsPage().CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
//
//        if (selectTaskFilterIcon == null)
//            throw new AutomationException("No filter search icon visible in Campaigns tab!");
//        selectTaskFilterIcon.click();
//
//        if (filterName.equalsIgnoreCase("Report Sent Date") || filterName.equalsIgnoreCase("Report Created Date")) {
//            WebElement element = driverUtil.getWebElement(String.format(DATE_BUTTON + "//following-sibling::div/button", filterName), 10);
//
//            if (element != null) {
//                element.click();
//
//                // Handling From Date
//                selectDate(fromDate);
//
//                // Handling To Date
//                selectDate(toDate);
//            } else {
//                throw new AutomationException(filterName + " unable to find records on Reported DRPS Table Advanced filter: ");
//            }
//        }
//
//        actions.sendKeys(Keys.ESCAPE).build().perform();
//        clickOnButtonPresentInPractitionersTab(buttonName);
//        waitForLoadingPage();
//    }
//

//
//    // Method to select a date in the calendar from dd/MM/yyyy format
//    public void selectDate(String dateStr) throws ParseException {
//        String[] dateParts = dateStr.split("/");
//        String day = dateParts[0];
//        String month = dateParts[1];
//        String year = dateParts[2];
//
//        // Open calendar and set year
//        DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
//        String yearValue = DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).getText();
//        String years[] = yearValue.split(" – ");
//
//        if (Integer.parseInt(year) > Integer.parseInt(years[1].trim())) {
//            DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//following-sibling::button")).click();
//        } else if (Integer.parseInt(year) < Integer.parseInt(years[0].trim())) {
//            DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//preceding-sibling::button")).click();
//        }
//        DriverFactory.drivers.get().findElement(By.xpath(String.format(YEAR_SELECTION, year))).click();
//
//        // Set month
//        DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, month))).click();
//
//        // Set day
//        if (day.startsWith("0")) {
//            day = day.replace("0", "");  // Remove leading zero for single digit days
//        }
//        DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, day))).click();
//    }

    public void selectTaskFilter(String filterName, String filterOption, String buttonName) throws AutomationException, ParseException {
        Actions actions = new Actions(DriverFactory.drivers.get());
        WebElement selectTaskFilterIcon = driverUtil.getWebElement(PageFactory.prescriberAnalyticsPage().CAMPAIGNS_REPORTS_FILTER_SEARCH_ICON);
        if (selectTaskFilterIcon == null)
            throw new AutomationException("No filter search icon visible in Campaigns tab!");
        selectTaskFilterIcon.click();

        if (filterName.equalsIgnoreCase("Algorithm Impact") || filterName.equalsIgnoreCase("Resolved") || filterName.equalsIgnoreCase("Implementation Status")) {
            WebElement campaignAdvanceFilterDropDown = driverUtil.getWebElement(String.format(REPORTED_DRP_ADVANCE_FILTER_DROPDOWN, filterName, filterName));
            if (campaignAdvanceFilterDropDown == null)
                throw new AutomationException("No '" + filterName + "' advance filter dropdown defined in campaign filter options!");
            campaignAdvanceFilterDropDown.click();
            WebElement campaignAdvanceFilterDropDownOption = driverUtil.getWebElement(String.format(REPORTED_DRP_ADVANCE_FILTER_DROPDOWN_OPTION, filterOption));
            if (campaignAdvanceFilterDropDownOption == null)
                throw new AutomationException("No '" + filterName + "' campaign Advance Filter DropDown Option defined in campaign filter options!");
            campaignAdvanceFilterDropDownOption.click();
            actions.sendKeys(Keys.ESCAPE).build().perform();
        } else if (filterName.equalsIgnoreCase("Report Sent Date") || filterName.equalsIgnoreCase("Report Created Date")) {
            WebElement element = driverUtil.getWebElement(String.format(DATE_BUTTON + "//following-sibling::div/button", filterName), 10);
//            element = driverUtil.getWebElement(String.format(DATE_BUTTON+"//following-sibling::div/button", dateType), 10);

            if (element != null) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = formatter.format(date);
                Calendar cal = Calendar.getInstance();
                if (filterOption.equalsIgnoreCase("future date")) {
                    cal.add(Calendar.DAY_OF_MONTH, +2);
                    Date nextDate = cal.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                    currentDate = format.format(nextDate);
                } else if (filterOption.equalsIgnoreCase("old date")) {
                    cal.add(Calendar.DAY_OF_MONTH, -(365 - 2));
                    Date frmDate = cal.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                    fromDate = format.format(frmDate);
                } else {
                    // Splitting the date range input (e.g., "02/10/2024 – 17/10/2024")
//                    cal.add(Calendar.DAY_OF_MONTH,-4);
                    String[] dateRange = filterOption.split("-");
                    fromDate = dateRange[0].trim(); // 02/10/2024
                    currentDate = dateRange[1].trim(); // 17/10/2024

                    this.startDate = dateRange[0].trim();
                    this.endDate = dateRange[1].trim();
//                    selectDateTaskFilter(filterName, fromDate, toDate, buttonName);

                }
                finalDate = fromDate + "-" + currentDate;
                element.click();
                String dateRange[] = finalDate.split("-");
                for (int i = 0; i < dateRange.length; i++) {
                    String dateVal = dateRange[i];
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                    DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).click();
                    String yearValue = DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON)).getText();
                    String years[] = yearValue.split(" – ");
                    String values[] = dateVal.split("\\s");
                    if (Integer.parseInt(values[2].trim()) > Integer.parseInt(years[1].trim()))
                        DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//following-sibling::button")).click();
                    else if (Integer.parseInt(values[2].trim()) < Integer.parseInt(years[0].trim()))
                        DriverFactory.drivers.get().findElement(By.xpath(CALENDER_BUTTON + "//preceding-sibling::button")).click();

                    DriverFactory.drivers.get().findElement(By.xpath(String.format(YEAR_SELECTION, values[2]))).click();
                    DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, values[1]))).click();
                    if (values[0].startsWith("0"))
                        currentDay = values[0].replace("0", "");
                    else
                        currentDay = values[0];
                    DriverFactory.drivers.get().findElement(By.xpath(String.format(MONT_DATE_SELECTION, currentDay))).click();
                }
            } else {
                throw new AutomationException(filterName + " unable to find records on Reported DRPS Table Advanced filter: ");
            }
        } else {
            WebElement campaignAdvanceFilterInput = driverUtil.getWebElementAndScroll(String.format(REPORTED_DRP_ADVANCE_FILTER_INPUT, filterName, filterName));
            if (campaignAdvanceFilterInput == null)
                throw new AutomationException("No '" + filterName + "' filter dropdown defined in Task page filter options!");
            campaignAdvanceFilterInput.click();
            campaignAdvanceFilterInput.clear();
            campaignAdvanceFilterInput.sendKeys(filterOption);
        }
        actions.sendKeys(Keys.ESCAPE).build().perform();
        clickOnButtonPresentInPractitionersTab(buttonName);
        waitForLoadingPage();
    }

    public void clickOnButtonPresentInPractitionersTab(String text) throws AutomationException {
        WebElement button = driverUtil.getWebElementAndScroll(String.format(PrescriberAnalyticsPage.CAMPAIGNS_TAB_BUTTONS, text));
        if (button == null)
            throw new AutomationException(String.format("Unable to find %s button on patient tab or it might taking too long time to load!", text));
        button.click();
        waitForLoadingPage();
    }

    public void verifyDatesInRange(String columnName, String filterOption) throws AutomationException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        String[] dateRange = filterOption.split("-");
        fromDate = dateRange[0].trim(); // 02/10/2024
        String toDate = dateRange[1].trim();
        // Convert fromDate and toDate strings to Date objects
        Date fromDateObj = dateFormat.parse(fromDate);
        Date toDateObj = dateFormat.parse(toDate);

        // Locate the column values by columnName
        List<WebElement> dateElements = DriverFactory.drivers.get()
                .findElements(By.xpath(String.format("//*[text()='Reported DRPs']//following::table//thead//..//td[15]")));

        List<String> datesOutsideRange = new ArrayList<>();

        for (WebElement dateElement : dateElements) {
            String dateText = dateElement.getText().trim();
            if (!dateText.isEmpty()) {
                Date dateObj = dateFormat.parse(dateText);

                // Check if the date falls within the specified range
                if (dateObj.before(fromDateObj) || dateObj.after(toDateObj)) {
                    datesOutsideRange.add(dateText);
                }
            }
        }

        // Assert that no dates were found outside the range
        if (!datesOutsideRange.isEmpty()) {
            throw new AutomationException("Dates found outside range: " + datesOutsideRange);
        } else {
            System.out.println("All dates in " + columnName + " are within the selected range.");
        }
    }
    public void selectReportCreatedDateRange(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        // Assuming there are existing functions for selecting the date range
//        elementUtil.setDateRangeInFilter("Report Created Date", startDate, endDate);
    }

    public boolean verifyReportCreatedDatesWithinRange(String columnName) throws AutomationException {
        int index = driverUtil.getWebElements(String.format(reportCreatedDateColumnIndex,columnName)).size() + 1;
        List<WebElement> dateElements = driverUtil.getWebElements(String.format(reportCreatedDateColumn,columnName,index));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            for (WebElement dateElement : dateElements){
                SimpleDateFormat dateFormatter = new SimpleDateFormat("mm/dd/yyyy");
                String dateText = dateElement.getText().trim();
                if (dateText.isEmpty()) continue;  // Skip blank dates if they are allowed

                Date reportDate = dateFormatter.parse(dateText);
                if (reportDate.before(start) || reportDate.after(end)) {
                    return false; // Date is out of the specified range
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true; // All dates are within the range
    }


}


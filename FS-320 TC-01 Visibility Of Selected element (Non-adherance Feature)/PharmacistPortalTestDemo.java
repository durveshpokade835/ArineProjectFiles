package runner;

import com.arine.automation.util.TestDataExcelUtil;
import cucumber.api.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import runner.BaseRunner;

import java.io.IOException;

@CucumberOptions(
        features = {"features/pharmacist-portal/patient-comments-report-population-dashboard-and-quality-tab/pharmacist-portal-non-adherance-delete.feature"},
        tags = "@nonAdherance",
        glue = { "com.arine.automation.glue"},

        monochrome = true
)
public class PharmacistPortalTestDemo extends BaseRunner {
    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext iTestContext) throws IOException {
        MAPPING_SHEET_NAME = TestDataExcelUtil.PHARMACIST_PORTAL_TEST_SCENARIO_MAPPING;
        init(iTestContext);
    }
}

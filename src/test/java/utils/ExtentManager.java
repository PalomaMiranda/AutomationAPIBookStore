package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("build/reports/extent-report/extent.html");
            reporter.config().setReportName("BookStore API Automation Report");
            reporter.config().setDocumentTitle("BookStore API Automation");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}
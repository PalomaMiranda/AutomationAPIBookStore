package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    public static ExtentReports extent = ExtentManager.getInstance();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String scenarioName = result.getMethod().getMethodName(); // fallback
        String featureName = null;
        List<String> tags = new ArrayList<>();

        for (Object p : result.getParameters()) {
            if (p instanceof PickleWrapper pw) {

                scenarioName = pw.getPickle().getName();

                tags.addAll(pw.getPickle().getTags());

                try {
                    var uri = pw.getPickle().getUri();
                    if (uri != null) {
                        String path = uri.getPath();
                        if (path != null) {
                            int slash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
                            featureName = path.substring(slash + 1).replace(".feature", "");
                        }
                    }
                } catch (Throwable ignored) { }
            } else if (p instanceof FeatureWrapper fw) {
                featureName = fw.toString();
            }
        }

        ExtentTest extentTest = extent.createTest(scenarioName);
        if (featureName != null && !featureName.isBlank()) {
            extentTest.assignCategory(featureName);
        }
        if (!tags.isEmpty()) {
            extentTest.assignCategory(tags.toArray(new String[0]));
        }
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest t = test.get();
        if (t != null) t.pass("✅ Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest t = test.get();
        if (t != null) t.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest t = test.get();
        if (t != null) t.skip("⏭ Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        test.remove();
    }

    public static void info(String message)  { if (test.get()!=null) test.get().log(Status.INFO, message); }
    public static void pass(String message)  { if (test.get()!=null) test.get().pass(message); }
    public static void fail(String message)  { if (test.get()!=null) test.get().fail(message); }
}
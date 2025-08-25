package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;
import utils.TestListener;

@Listeners(TestListener.class)

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
        monochrome = true,
        plugin = {"pretty"}
)
public class RunCucumberTest extends AbstractTestNGCucumberTests { }
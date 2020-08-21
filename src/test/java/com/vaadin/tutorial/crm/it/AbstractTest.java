package com.vaadin.tutorial.crm.it;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.parallel.ParallelTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

/***
 * The AbstractTest class setup WebDriverManager to use it in testing of Vaadin CRM
 */
public class AbstractTest extends ParallelTest {

    protected static final String SERVER_HOST = IPAddress.findSiteLocalAddress();
    protected static final int SERVER_PORT = 8080;
    private final String route;

    protected AbstractTest(String route) {
        this.route = route;
    }

    @Before
    public void setup() throws Exception {
        //use Vaadin Test Bench setup of WebDriver
        super.setup();
        getDriver().get(getURL(route));
    }

    private static String getURL(String route) {
        return String.format("http://%s:%d/%s", SERVER_HOST, SERVER_PORT, route);
    }

    @BeforeClass
    public static void setupClass(){
        WebDriverManager.chromedriver().setup();
    }

    // Prevent debug logging from Apache HTTP client
    static {
        Logger root = (Logger) LoggerFactory.getLogger(AbstractTest.class);
        root.setLevel(Level.INFO);
    }

    /***
     * ScreenshotOnFailureRule tells TestBench (Vaadin's tool for end-to-end tests, link: https://vaadin.com/trial)
     * to grab a screenshot before exiting, if a test fails.
     * This helps you understand what went wrong when tests do not pass.
     */
    @Rule
    public ScreenshotOnFailureRule rule =
            new ScreenshotOnFailureRule(this, true);

}

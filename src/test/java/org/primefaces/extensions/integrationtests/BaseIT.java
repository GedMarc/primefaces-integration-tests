package org.primefaces.extensions.integrationtests;

import io.github.bonigarcia.SeleniumExtension;
import org.apache.openejb.testing.Application;
import org.apache.openejb.testing.RandomPort;
import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;
import org.apache.tomee.embedded.EmbeddedTomEEContainer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.primefaces.extensions.integrationtests.interactions.IDefaultTestCycle;
import org.primefaces.extensions.integrationtests.interactions.IWebActionable;
import org.primefaces.extensions.integrationtests.interactions.IWebElementFinder;


import javax.ejb.embeddable.EJBContainer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ExtendWith({SeleniumExtension.class})
public abstract class BaseIT implements IWebActionable, IWebElementFinder,IDefaultTestCycle {

    private final int port = 8080;

    private WebDriver currentDriver;

    @Override
    public WebDriver getDriver() {
        return currentDriver;
    }

    void setup(WebDriver driver) {
        EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
        WebDriverEventListener errorListener = new AbstractWebDriverEventListener() {
            @Override
            public void onException(Throwable throwable, WebDriver driver) {
                throwable.printStackTrace();
                //takeScreenshot();
            }
        };
        eventDriver.register(errorListener);
        currentDriver = eventDriver;
        driver.manage().window().maximize();
        driver.get("http://localhost:" + port + "/" + getLocation());
        waitForJQueryLoad();
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public void runTest(WebDriver driver) {
        System.out.println("Starting to run the tests with driver : " + driver.toString());
        try {
            performTest();
        } catch (Throwable T) {
            T.printStackTrace();
         //   takeScreenshot();
            throw T;
        }
        driver.quit();
    }

    public abstract String getLocation();

    public abstract void performTest();


    @Nested
    @Tag("MyFaces")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class MyFaces implements IDefaultTestCycle {
        private void startFaces()
        {
            Map<Object, Object> properties = new HashMap();
            properties.put(EJBContainer.PROVIDER, EmbeddedTomEEContainer.class);
            properties.put(EJBContainer.MODULES, new File[]{new File("src/main/webapp/")});
            properties.put(EJBContainer.APP_NAME, "");
            System.setProperty("tomee.webapp.externalRepositories", "target/classes,target/test-classes/");
            EmbeddedTomEEContainer.createEJBContainer(properties);
        }

        @Test
        @DisplayName("Chrome Driver Test")
        @DisabledOnOs(OS.LINUX)
        //@Disabled
        public void testChrome(ChromeDriver chromeDriver) {
            startFaces();
            setup(chromeDriver);
            runTest(chromeDriver);
        }

        @Test
        @DisplayName("Firefox Driver Test")
        @Disabled
        public void testFirefox(FirefoxDriver firefoxDriver) {
            setup(firefoxDriver);
            runTest(firefoxDriver);
        }

        @Test
        @DisplayName("Phantom Driver Test")
        @Disabled
        public void testPhantom(PhantomJSDriver jsDriver) {
            setup(jsDriver);
            runTest(jsDriver);
        }
    }

    @Nested
    @Tag("Mojarra")
    @Disabled
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Mojarra implements IDefaultTestCycle {

        @Test
        @DisplayName("Chrome Driver Test")
        @DisabledOnOs(OS.LINUX)
        //@Disabled
        public void testChrome(ChromeDriver chromeDriver) {
            setup(chromeDriver);
            runTest(chromeDriver);
        }

        @Test
        @DisplayName("Firefox Driver Test")
        @DisabledOnOs(OS.LINUX)
        public void testFirefox(FirefoxDriver firefoxDriver) {
            setup(firefoxDriver);
            runTest(firefoxDriver);
        }

        @Test
        @DisplayName("Phantom Driver Test")
        @Disabled
        public void testPhantom(PhantomJSDriver jsDriver) {
            setup(jsDriver);
            runTest(jsDriver);
        }
    }

    @Nested
    @Tag("Guice")
    @Disabled
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class GuiceJSF implements IDefaultTestCycle {

        @Test
        @DisplayName("Chrome Driver Test")
        @DisabledOnOs(OS.LINUX)
        //@Disabled
        public void testChrome(ChromeDriver chromeDriver) {
            setup(chromeDriver);
            runTest(chromeDriver);
        }

        @Test
        @DisplayName("Firefox Driver Test")
        @DisabledOnOs(OS.LINUX)
        public void testFirefox(FirefoxDriver firefoxDriver) {
            setup(firefoxDriver);
            runTest(firefoxDriver);
        }

        @Test
        @DisplayName("Phantom Driver Test")
        @Disabled
        public void testPhantom(PhantomJSDriver jsDriver) {
            setup(jsDriver);
            runTest(jsDriver);
        }
    }
}

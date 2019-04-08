package com.academy.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class BaseTest {
    protected WebDriver driver;
    protected StringBuffer verificationErrors = new StringBuffer();

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("firefox") String browser) {
        initDrivers( browser );
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.manage().window().maximize();
    }

    private void initDrivers(String browser) {
        // Здесь читаю пусть к файлу конфигурации
        String commonProperties = System.getProperty( "common.cfg" );
        Properties properties = new Properties();
        try {
            properties.load( new FileReader( commonProperties ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (browser) {
            case "chrome":
                System.setProperty( "webdriver.chrome.driver", properties.getProperty( "chrome.driver" ) );
                driver = new ChromeDriver();
                // ChromeOptions chrome_options = new ChromeOptions();
                //  chrome_options.addArguments( "--start-maximized" );
                // driver = new ChromeDriver( chrome_options );
                break;

            case "firefox":
                System.setProperty( "webdriver.gecko.driver", properties.getProperty( "gecko.driver" ) );
                driver = new FirefoxDriver();
/*              FirefoxOptions firefox_options = new FirefoxOptions();
                firefox_options.addArguments( "--start-maximized" );
                driver = new FirefoxDriver( firefox_options );*/
                break;

            default:
                throw new IllegalArgumentException( "Unknown browser " + browser );
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        //driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals( verificationErrorString )) {
            fail( verificationErrorString );
        }
    }
}
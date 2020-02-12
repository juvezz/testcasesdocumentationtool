package steplogics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

    private static WebDriver driver;
    private WebDriverWait wait;

    public static WebDriver getDriver() {
        return driver;
    }

    public void beforeTestSteps() {
        System.setProperty("webdriver.chrome.driver", "test/resources/chromedriverlocalmac");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
    }

    public void cleanup() {
        driver.quit();
    }
}

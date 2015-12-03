package io.github.bookster.config;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created on 03/12/15
 * author: nixoxo
 */
public class BaseDriverIntegration {

    public static ChromeDriver chromeDriver() {
        File file = null;
        if (SystemUtils.IS_OS_MAC) {
            file = new File("src/test/resources/driver/mac/chromedriver");
        }
        if (SystemUtils.IS_OS_LINUX) {
            file = new File("src/test/resources/driver/linux/chromedriver");
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            file = new File("src/test/resources/driver/windows/chromedriver.exe");
        }
        if (file == null) {
            throw new RuntimeException("ChromeDriver is not supported by your operating system");
        }
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setJavascriptEnabled(true);
        ChromeDriver driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        return driver;
    }
}

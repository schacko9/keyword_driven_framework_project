package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	
	// Global Variables 
		public  WebDriver driver;
		public Properties prop;

		public WebDriver initializeDriver(String browserName) throws IOException
		{
			if(browserName.equals("chrome")){
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				
				if(prop.getProperty("headless").equals("yes")) {
					options.addArguments("--headless");
					driver = new ChromeDriver(options);
				}
				else{
					driver = new ChromeDriver();
				}
				
			}
			else if (browserName.equals("firefox")){
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();
				
				if(prop.getProperty("headless").equals("yes")) {
					options.addArguments("--headless");
					driver = new FirefoxDriver(options);
				}
				else{
					driver = new FirefoxDriver();
				}
			}
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;
	}
		
		
	public Properties initializeProperties() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/java/keyword/config.properties");

		prop.load(fis);
		
		return prop;
	}
		
		

		
		
}

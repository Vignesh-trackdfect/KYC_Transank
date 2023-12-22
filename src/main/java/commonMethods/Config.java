package commonMethods;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import atu.testng.reports.ATUReports;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Config extends Keywords {

	public WebDriver driver;
	public DesiredCapabilities capabilities;

	public static String MSSFassert = "Maruti Suzuki Finance is an end to end digital car financing platform where the customer";
	public static String financerassert = "Maruti Suzuki has currently onboarded 24 Finance partners including all key private banks, PSU banks and NBFCs. Proceed further on your Smart Finance journey to get into the details and explore offers suitable for you.";
	public static String Saleriedassert = "Salaried individuals employed by government or private organizations can avail loan facilities on Smart Finance platform. Proceed on the Smart Finance journey to explore exciting offers designed for you.";
	public static String raiseassert = "to submit a ticket";
	public static String docassert = "Did you mean any of the following?";
	public static String processassert = "Did you mean any of the following?";
	public static String reopenassert = "Did you mean any of the following?";

	ATUReports atuRep = new ATUReports();

	public WebDriver getDriver() throws MalformedURLException {
		return this.driver;

	}

	public void setDriver(WebDriver paramDriver) throws MalformedURLException {
		this.driver = paramDriver;
	}

	public WebDriver getWebDriver(String browserName) throws MalformedURLException {

		if (browserName.equalsIgnoreCase("Chrome")) {			

			ChromeOptions options = new ChromeOptions();
		     WebDriverManager.chromedriver().setup();
			 options.addArguments("disable-notifications");
	    	 ChromeDriver driver = new ChromeDriver(options);
			System.out.println("Chrome Browser launched...");
			setDriver(driver);
			driver.manage().window().maximize();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 1);
			prefs.put("download.default_directory", System.getProperty("user.dir") + "\\DownloadedFiles");

		} else if (browserName.equalsIgnoreCase("Firefox")) {
		
		
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions option = new FirefoxOptions();
			option.addPreference("dom.webnotifications.enabled", false);
			//option.setCapability("moz:loggingPrefs", ImmutableMap.of("browser", "OFF"));
			// option.addPreference("app.update.enabled", false);
			// option.addPreference("geo.enabled", false);
			WebDriver driver = new FirefoxDriver(option);
			System.out.println("Firefox Browser launched...");
			setDriver(driver);
			driver.manage().window().maximize();
			
		} else if (browserName.equalsIgnoreCase("IE")) {
			
			//System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\driver\\IEDriverServer.exe");
			WebDriverManager.edgedriver().setup();
			EdgeDriver driver = new EdgeDriver();
			System.out.println("IE Browser launched...");
			setDriver(driver);
			driver.manage().window().maximize();

		}
		return driver;
	}

	public WebDriver getWebDriver1(String browser, String buildname, String userflowname) throws Exception {
		String Url = "https://mak.l:oVWll37qqWzqQS1822t7wWYfqH9fpGoj08w4HPStM26Tk0ibu2@hub.lambdatest.com/wd/hub";
		String Url2="http://192.168.0.174:4444/wd/hub";
		
		if (browser.equalsIgnoreCase("Chrome")) {			

			capabilities = DesiredCapabilities.chrome();
			capabilities.setBrowserName("chrome");
			capabilities.setPlatform(Platform.ANY);

		} else if (browser.equalsIgnoreCase("Firefox")) {
		
			capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			capabilities.setPlatform(Platform.ANY);
		}else if (browser.equalsIgnoreCase("IE")) {
		
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setBrowserName("internet explorer");
			capabilities.setPlatform(Platform.ANY);
		}
				
		RemoteWebDriver driver = new RemoteWebDriver(new URL(Url2), capabilities);
		driver.setFileDetector(new LocalFileDetector());
		driver.manage().window().maximize();
		return driver;

//		capabilities.setCapability("browserName", "chrome");
//		capabilities.setCapability("version", "latest");
//		capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get any available
////		capabilities.setCapability("platform", "macOS Sonoma");
////		capabilities.setCapability("platformName", "macOS Sonoma");	
////		capabilities.setCapability("version", "15.0");
//		capabilities.setCapability("build", buildname);
//		capabilities.setCapability("name", userflowname);
//		capabilities.setCapability("selenium_version", "4.12.0"); // To enable network logs
//		capabilities.setCapability("visual", true); // To enable step by step screenshot
//		capabilities.setCapability("video", true); // To enable video recording
//		capabilities.setCapability("console", true); // To capture console logs
//		capabilities.setCapability("network", true);
//		capabilities.setCapability("terminal", false); // To capture terminal logs
//		LoggingPreferences logPrefs = new LoggingPreferences();
//		logPrefs.enable(LogType.BROWSER, Level.ALL);
//		capabilities.setCapability("goog:loggingPrefs", logPrefs);
//		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
//		ltOptions.put("resolution", "1024x768");
//		capabilities.setCapability("LT:Options", ltOptions);
//		WebDriverManager.chromedriver().setup();
		
	}

}

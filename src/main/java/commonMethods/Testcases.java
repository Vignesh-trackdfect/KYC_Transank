package commonMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.PrintStream;
import java.net.MalformedURLException;

import atu.testng.reports.exceptions.ATUReporterException;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import scripts.BuyCrypto;
import scripts.BuyCryptoLite;
import scripts.Instant_transfer;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})

public class Testcases extends Config {
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "/atu.properties");
	}
	public String appURL;
	public String mailinatorurl;
	public String proxy;
	public String usernameValue;
	public String passwordValue;
	public String project_Name;
	public String version_Name;
	public String environment;
	public String browser;
	public WebDriver driver;
	public String search;

	File f = new File(report_folder_create + "\\reports");

	BuyCrypto buy=new BuyCrypto();
	BuyCrypto buy1=new BuyCrypto();
	Instant_transfer buy2=new Instant_transfer();
	
	BuyCryptoLite buyLite=new BuyCryptoLite();

	@BeforeClass
	public void getDataFromConfig() throws ATUReporterException, IOException, InterruptedException {

		appURL = Utils.getDataFromTestConfig("URL");
		browser = Utils.getDataFromTestConfig("AppBrowser");
		project_Name = Utils.getDataFromTestConfig("Project_Name");
		version_Name = Utils.getDataFromTestConfig("Version_Name");
		//driver = getWebDriver(browser);
		System.out.println("\n******************Script Execution Started******************");

	}

//	@AfterClass
//	public void closewindow() throws FileNotFoundException {
//		quit(driver);
//	}

	@Test
	public void buyCryptoEuro() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Euro";
		
		buy.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoEuroLite() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Euro";
		
		buyLite.buyCrypto_Lite(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	

	@Test
	public void buyCryptoBritish() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="British pound";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		//buy2.buyCrypto_Instant_Transfer(driver, Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoBritishLite() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="British pound";
		
		buyLite.buyCrypto_Lite(driver,Currency_name);
		//buy2.buyCrypto_Instant_Transfer(driver, Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoUSDoller() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="US Dollar";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	
	@Test
	public void buyCryptoUSDollerLite() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="US Dollar";
		
		buyLite.buyCrypto_Lite(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoSingapore() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Singapore Dollar";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoSingaporeLite() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Singapore Dollar";
		
		buyLite.buyCrypto_Lite(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoSwedish() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Swedish krona";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoNorwegian() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Norwegian Krone";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoNewZealand() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="New Zealand dollar";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
	
	@Test
	public void buyCryptoCanada() {
		
		WebDriver driver = null;
		try {
			driver = getWebDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Currency_name="Canadian dollar";
		
		buy1.buyCrypto_CardPayment(driver,Currency_name);
		System.out.println("\n******************Script Execution complete******************");
		quit(driver);
	}
}

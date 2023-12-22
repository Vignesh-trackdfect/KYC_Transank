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
	public String buildname;
	public String Configuration;

	File f = new File(report_folder_create + "\\reports");

	BuyCrypto buy=new BuyCrypto();
	BuyCrypto buy1=new BuyCrypto();

	
	BuyCryptoLite buyLite=new BuyCryptoLite();

	@BeforeClass
	public void getDataFromConfig() throws ATUReporterException, IOException, InterruptedException {

		appURL = Utils.getDataFromTestConfig("URL");
		browser = Utils.getDataFromTestConfig("AppBrowser");
		project_Name = Utils.getDataFromTestConfig("Project_Name");
		version_Name = Utils.getDataFromTestConfig("Version_Name");
		buildname = project_Name + "_" + java.time.LocalDate.now().toString() + "_" + java.time.LocalTime.now();
		Configuration=Utils.getDataFromTestConfig("Execution env");//driver = getWebDriver(browser);
		System.out.println("\n******************Script Execution Started******************");

	}

//	@AfterClass
//	public void closewindow() throws FileNotFoundException {
//		quit(driver);
//	}

	@Test
	public void buyCryptoEuro() throws Exception {
		
		WebDriver driver = null;
		
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoEuro");
			}
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
	public void buyCryptoEuroLite() throws Exception {
		
		WebDriver driver = null;
		try {
			
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoEuroLite");
			}
			
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
	public void buyCryptoBritish() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoBritish");
			}
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

	public void buyCryptoBritishLite() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoBritishLite");
			}
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
	public void buyCryptoUSDoller() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoUSDoller");
			}
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
	public void buyCryptoUSDollerLite() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoUSDollerLite");
			}
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
	public void buyCryptoSingapore() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoSingapore");
			}
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
	public void buyCryptoSingaporeLite() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoSingaporeLite");
			}
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
	public void buyCryptoSwedish() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoSwedish");
			}
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
	public void buyCryptoNorwegian() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoNorwegian");
			}
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
	public void buyCryptoNewZealand() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoNewZealand");
			}
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
	public void buyCryptoCanada() throws Exception {
		
		WebDriver driver = null;
		try {
			if(Configuration.equalsIgnoreCase("local")) {
				driver = getWebDriver(browser);
			}else {
				driver = getWebDriver1(browser,buildname,"buyCryptoCanada");
			}
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
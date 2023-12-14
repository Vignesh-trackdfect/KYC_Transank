package commonMethods;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Directory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class Keywords extends ATUReports implements KYC_Locators {
	private static final String HMAC_SHA1_ALGORITHM = "HMACSHA1";

	public String ElementWait = Utils.getDataFromTestConfig("Wait Time");
	public int WaitElementSeconds = new Integer(ElementWait);
	public String Main_Window = "";
	public ArrayList<String> tabs;
	public WebElement fromElement;
	public ITestResult result;
	public String report_Filepath = Utils.getDataFromTestConfig("Reports  path");
	public String date = getCurrentDate();
	public String folder_name = report_Filepath.concat(date);
	public String folder_name_subfolder = folder_name.concat("/");
	public String report_folder_create = folder_name_subfolder;
	public String report_name = "PocReport";
	public String filepath_date_concat = folder_name_subfolder.concat(report_name).concat(".html");
	public String screenshot_folder_name = folder_name_subfolder.concat("Screenshot");
	public String screenshot_folder_path = screenshot_folder_name.concat("/");
	public String screenshot_folder_create = screenshot_folder_path;
	public String firstValue;
	public String secondValue;
	public boolean failureScreenshot = Directory.TestPassScreenshot;
	

	public String getCurrentDate() {
		Format formatter = new SimpleDateFormat("dd-MM-YYYY HH-mm-ss");
		Date date = new Date();
		String value = formatter.format(date);
		return value;
	}

	
	 
	public static String[] splitXpath(String path) {
		String[] a = path.split(">");
		return a;
	}

	public String screenshot(WebDriver driver, String screenshotName) {
		String image_dest = null;
		try {

			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String currenttime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			image_dest = System.getProperty("user.dir").concat("\\snaptrude\\" + currenttime + screenshotName)
					.concat(".png");
			System.out.println(image_dest);
			File destination = new File(image_dest);
			FileUtils.copyFile(source, destination);
			return image_dest;
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			System.out.println("Exception while taking Screenshot" + e.getMessage());
			return e.getMessage();
		}
	}


	 
	public String readimage(WebDriver driver, String filepath) {
		String image_res = null;
		try {
			File imgfile = new File(filepath);
			ITesseract instance = new Tesseract();
			instance.setDatapath("C:\\snapautomation\\Comdex\\testdata");
			// String result=instance.doOCR(imgfile);
			Rectangle rect = new Rectangle(10, 20, 150, 100);
			// String result = instance.doOCR(imgfile,rect);
			String result = instance.doOCR(imgfile, rect);
			System.out.println("Get for the text : " + result);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return image_res;

	}

	public String ScreenCompareImage(WebDriver driver, String img_filepath1, String img_filepath2) {
		String image_res = null;
		try {
			BufferedImage img1 = ImageIO.read(new File(img_filepath1));
			BufferedImage img2 = ImageIO.read(new File(img_filepath2));
			int w1 = img1.getWidth();
			int w2 = img2.getWidth();
			int h1 = img1.getHeight();
			int h2 = img2.getHeight();
			if ((w1 != w2) || (h1 != h2)) {
				System.out.println("Both images should have same dimwnsions");
			} else {
				long diff = 0;
				for (int j = 0; j < h1; j++) {
					for (int i = 0; i < w1; i++) {
						// Getting the RGB values of a pixel
						int pixel1 = img1.getRGB(i, j);
						Color color1 = new Color(pixel1, true);
						int r1 = color1.getRed();
						int g1 = color1.getGreen();
						int b1 = color1.getBlue();
						int pixel2 = img2.getRGB(i, j);
						Color color2 = new Color(pixel2, true);
						int r2 = color2.getRed();
						int g2 = color2.getGreen();
						int b2 = color2.getBlue();
						// sum of differences of RGB values of the two images
						long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
						diff = diff + data;
					}
				}
				double avg = diff / (w1 * h1 * 3);
				double percentage = (avg / 255) * 100;
				System.out.println("Difference: " + percentage);
				if (percentage == 0.0) {
					image_res = "Pass";
				}

			}
		} catch (Exception e) {
			System.out.println("Exception while taking Screenshot" + e.getMessage());
			return e.getMessage();
		}
		return image_res;
	}

	public void wait(WebDriver driver, String inputData) {
		try {
			int time = Integer.parseInt(inputData);
			int seconds = time * 1000;
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			add1(driver, "Unable to wait ", LogAs.FAILED, true, "Wait");
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void uploadFileAutoIT(String filelocation) {
		try {
			String autoitscriptpath = System.getProperty("user.dir") + "\\" + "File_upload_selenium_webdriver.au3";

			Runtime.getRuntime().exec("cmd.exe /c Start AutoIt3.exe " + autoitscriptpath + " \"" + filelocation + "\"");

		} catch (Exception exp) {
			
			Assert.fail();
		}
	}

	public void CopyAll() {
		Robot rb3;
		try {
			rb3 = new Robot();
			rb3.delay(1000);
			rb3.keyPress(KeyEvent.VK_CONTROL);
			rb3.keyPress(KeyEvent.VK_A);
			rb3.keyRelease(KeyEvent.VK_A);
			rb3.keyRelease(KeyEvent.VK_CONTROL);
			rb3.delay(1000);
		} catch (Exception e) {
			System.out.println("robot class its not working");
		}
	}

	public static int getXCoordOffsetFromCentre(WebDriver driver, Point centre, int x, int y, int z) {

		Integer intX = Integer.valueOf(x);
		Integer intY = Integer.valueOf(y);
		Integer intZ = Integer.valueOf(z);
		int firstValue = 0;

		try {

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			Object de = js1.executeScript(
					
					"const v3= new store.exposed.BABYLON.Vector3(arguments[0],arguments[1],arguments[2]);\r\n"
							+ "		return store.exposed.getV3ProjectedOntoScreenSpace(v3).asArray()",
					
//					"const v3= new BABYLON.Vector3(arguments[0],arguments[1],arguments[2]);\r\n"
//							+ "		return store.exposed.getV3ProjectedOntoScreenSpace(v3).asArray()",
					intX, intY, intZ);
			System.out.println(de);

			String str = de.toString();
			String[] first = str.split(",");
			firstValue = Integer.parseInt(first[0].replace("[", "").trim()) - centre.x;
			System.out.println(firstValue);

		}

		catch (Exception e1) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return firstValue;

	}

	public static int getYCoordOffsetFromCentre(WebDriver driver, Point centre, int x, int y, int z) {

		Integer intX = Integer.valueOf(x);
		Integer intY = Integer.valueOf(y);
		Integer intZ = Integer.valueOf(z);
		int secondvalue = 0;

		try {

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			Object de = js1.executeScript(
					
					"const v3= new store.exposed.BABYLON.Vector3(arguments[0],arguments[1],arguments[2]);\r\n"
							+ "		return store.exposed.getV3ProjectedOntoScreenSpace(v3).asArray()",
					
//					"const v3= new BABYLON.Vector3(arguments[0],arguments[1],arguments[2]);\r\n"
//							+ "		return store.exposed.getV3ProjectedOntoScreenSpace(v3).asArray()",
					intX, intY, intZ);
			System.out.println(de);

			String str = de.toString();
			String[] first = str.split(",");
			secondvalue = Integer.parseInt(first[1].replace("]", "").trim()) - centre.y;
			System.out.println(secondvalue);

		}

		catch (Exception e1) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return secondvalue;

	}

	

	public String search1(WebDriver driver, String xpath, String keysToSend, String xpath1) {

		String[] values = splitXpath(xpath);
		String[] values1 = splitXpath(xpath1);

		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebElement webElement1 = driver.findElement(By.xpath(values1[1]));
			webElement.sendKeys(keysToSend);
			Thread.sleep(3000);
			Actions builder = new Actions(driver);
			// builder.moveToElement(webElement).click().build().perform();
			builder.moveToElement(webElement1).click().build().perform();
			// add(driver, "Type on " + values[0], keysToSend, true, values[0]);

		} catch (Exception e) {
			add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), keysToSend, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return xpath1;

	}

	public static void dropdown(WebDriver driver, String xpath) {

		String[] values = splitXpath(xpath);

		List<WebElement> li = driver.findElements(By.xpath(values[1]));
		try {
			for (int i = 0; i < li.size(); i++) {
				System.out.println(li.get(i).getText());
				Thread.sleep(2000);
				if (li.get(i).getText().contains("Apple iPhone 12")) {

					li.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}

	}

	public void waitForElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			//int WaitElementSeconds1 = new Integer(ElementWait);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(values[1])));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));
			add(driver, "Wait for the Element " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			add1(driver, "Element Not Found - " + values[0]+"- "+e.getLocalizedMessage() + e, LogAs.FAILED, true, values[0]);
			Assert.fail();
		}
	}
	
	public void waitForElement1(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			//int WaitElementSeconds1 = new Integer(ElementWait);
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(values[1])));
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));
			add(driver, "Wait for the Element " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			add1(driver, "Element Not Found - " + values[0]+"- "+e.getLocalizedMessage() + e, LogAs.FAILED, true, values[0]);
			Assert.fail();
		}
	}
	
	public void waitForElementtopresent(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			//int WaitElementSeconds1 = new Integer(ElementWait);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(values[1])));
			add(driver, "Wait for the Element " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			add1(driver, "Element Not Found - " + values[0]+"- "+e.getLocalizedMessage() + e, LogAs.FAILED, true, values[0]);
			Assert.fail();
		}
	}

	public void waitForElementWithLessWait(WebDriver driver, String xpath) {
		
		String[] values = splitXpath(xpath);
		try {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			//System.out.println(driver.getTitle());
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));
		add(driver, "Wait for visibility of Element" + values[0], LogAs.PASSED, true, values[0]);
		}catch (Exception e) {
			System.out.println(" Exception "+e);
			add1(driver, "Element not visible " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		
	}
	
	public void click(WebDriver driver, String Xpath) {		
		String[] values = splitXpath(Xpath);
		try {
			waitForElement(driver,Xpath);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(values[1])));
			WebElement element = driver.findElement(By.xpath(values[1]));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",element);
			executor.executeScript("arguments[0].click();", element);			
			
			add(driver, "Click on " + values[0], LogAs.PASSED, true, values[0]);
			
//			Object de = executor.executeScript("return store.scene.getEngine().getFps()");
//			System.out.println("Duration of action performed : "+de);
			
//			this.getfps(driver);
			
		} catch (Exception e) {
			e.printStackTrace();
			add1(driver, "Unable to click on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}

	}

	public void click1(WebDriver driver, String path, int input) {
		String[] values = splitXpath(path);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[input]));
			System.out.println(webElement);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					webElement);
			webElement.click();
			System.out.println(values[0] + " clicked");
			add(driver, "Click1 on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			System.out.println(" Exception " + e);
			add1(driver, "Unable to click1 on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void switchToActiveElement(WebDriver driver) {
		try {
			driver.switchTo().activeElement();
		} catch (Exception e) {
		}
	}

	public void clickByClassName(WebDriver driver, String className) {
		String[] values = splitXpath(className);
		try {
			WebElement webElement = driver.findElement(By.className(values[1]));
			webElement.click();
			// add(driver, "Click on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			// add1(driver, "Unable to click on " + values[0], LogAs.FAILED, true,
			// values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void clickWithoutFail(WebDriver driver, String path) {
		String[] values = splitXpath(path);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		webElement.click();
		add(driver, "Click on " + values[0], LogAs.PASSED, true, values[0]);

	}

	public void jsClickByXPath(WebDriver driver, String Xpath) {
		String[] values = splitXpath(Xpath);
		try {
			// waitForElement(driver,Xpath);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement element = driver.findElement(By.xpath(values[1]));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			add(driver, "Click on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to click on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}
	public void waitForTexttopresent(WebDriver driver, String xpath,String text) {
		String[] values = splitXpath(xpath);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//int WaitElementSeconds1 = new Integer(ElementWait);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));
			wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(values[1]),text));
			add(driver, "Wait for the Text " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Text Not Found - " + values[0]+"- "+e.getLocalizedMessage() + e, LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}
	
	

	public String clearAndType(WebDriver driver, String xpaths, String keysToSend) {
		String[] values = splitXpath(xpaths);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebDriverWait wait1 = new WebDriverWait(driver, 20);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value='';", webElement);
			js.executeScript("arguments[0].click();", webElement);
			//webElement.clear();
//			webElement.sendKeys(keysToSend, Keys.ENTER);
			//JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			wait(driver,"1");
			 js.executeScript("arguments[0].value="+"\'"+keysToSend+"\'"+";", webElement);
			// js.executeScript("arguments[0].click();", webElement);
			webElement.sendKeys(Keys.ENTER);
			 
			add(driver, "Clear and Type on " + values[0], keysToSend, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), keysToSend, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return keysToSend;
	}

	public String actionType(WebDriver driver, String xpath, String keysToSend) {
		String[] values = splitXpath(xpath);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Actions action = new Actions(driver);
			action.sendKeys(webElement, keysToSend).build().perform();
			add(driver, "Type on " + values[0], keysToSend, true, values[0]);
		} catch (StaleElementReferenceException e) {
			add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return keysToSend;
	}

	public void actionClick(WebDriver driver, String Xpath) {
		String[] values = splitXpath(Xpath);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Actions action = new Actions(driver);
			action.click(webElement).build().perform();
			add(driver, "Click on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to click on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void doubleClick(WebDriver driver, String element) {
		String[] values = splitXpath(element);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Actions action = new Actions(driver).doubleClick(webElement);
			action.build().perform();
			add(driver, "Double click on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to click on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public String sendKeys(WebDriver driver, String xpaths, String keysToSend) {
		String[] values = splitXpath(xpaths);

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebDriverWait wait1 = new WebDriverWait(driver, 20);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.sendKeys(keysToSend);

			add(driver, "Type on " + values[0], keysToSend, true, values[0]);
			wait(driver,"1");
		} catch (Exception e) {
			add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), keysToSend, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return keysToSend;

	}

	public String sendKeys2(WebDriver driver, String xpaths, String keysToSend) {
		String[] values = splitXpath(xpaths);

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebDriverWait wait1 = new WebDriverWait(driver, 20);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(values[1])));

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.sendKeys(keysToSend);

			//add(driver, "Type on " + values[0], keysToSend, true, values[0]);
			wait(driver,"1");
		} catch (Exception e) {
			//add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), keysToSend, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return keysToSend;

	}

	public String searchelement(WebDriver driver, String xpaths, String keysToSend) {
		String[] values = splitXpath(xpaths);

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.sendKeys(keysToSend, Keys.ENTER);

			add(driver, "Type on " + values[0], keysToSend, true, values[0]);

		} catch (Exception e) {
			add1(driver, "Unable to type on " + values[0]+"- "+e.getLocalizedMessage(), keysToSend, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
		return keysToSend;

	}

	public void clear(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.clear();
			add(driver, "Clear on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to clear on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.PASSED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void webelementfunction(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// WebElement webElement = driver.findElement(By.xpath(values[1]));
			// webElement.clear();
			List<WebElement> bakeries = driver.findElements(By.xpath(values[1]));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					bakeries);
			System.out.println(bakeries.size());
			add(driver, "Clear on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to clear on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.PASSED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}

	}

	public void selectCheckBox(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement element = driver.findElement(By.xpath(values[1]));
			if (element.isSelected()) {
			} else {
				element.click();
			}
			add(driver, "Select the checkbox on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to select the checkbox on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void deSelectCheckBox(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			WebElement element = driver.findElement(By.xpath(values[1]));
			if (element.isSelected()) {
				element.click();
			} else {
			}
			add(driver, "Deselect the checkbox on " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to deselect the checkbox on " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void selectByIndex(WebDriver driver, String xpaths, String inputData) {
		String[] values = splitXpath(xpaths);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Integer index = new Integer(inputData);
			Select selectBox = new Select(webElement);
			selectBox.selectByIndex(index);
			add(driver, "Select the Dropdown by Index " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to select the Dropdown by Index " + values[0]+"- "+e.getLocalizedMessage(), inputData, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void selectByText(WebDriver driver, String xpaths, String inputData) {
		String[] values = splitXpath(xpaths);
		try {
			Select selectBox = new Select(driver.findElement(By.xpath(values[1])));
			selectBox.selectByVisibleText(inputData);
			add(driver, "Select the Dropdown by text " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to select the Dropdown by text " + values[0]+"- "+e.getLocalizedMessage(), inputData, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void selectByValue(WebDriver driver, String xpaths, String inputData) {
		String[] values = splitXpath(xpaths);
		try {

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Select selectBox = new Select(webElement);
			selectBox.selectByValue(inputData);
			add(driver, "Select the Dropdown by Value " + values[0], inputData, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to select the Dropdown by Value " + values[0]+"- "+e.getLocalizedMessage(), inputData, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void checkTwoString(WebDriver driver, String GetText1, String GetText2) {
		try {
			if (GetText1.equalsIgnoreCase(GetText2)) {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are matched", LogAs.PASSED, true,
						GetText1);
			} else if (GetText1.equals(null)) {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED,
						true, GetText1);
				Assert.fail();
			} else if (GetText2.equals(null)) {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED,
						true, GetText1);
				Assert.fail();
			} else {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED,
						true, GetText1);
				Assert.fail();
			}
		} catch (NoSuchElementException e) {
			add1(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED, true,
					GetText1);
			Assert.fail();
		}
	}

	public void checkPartialText(WebDriver driver, String GetText1, String GetText2) {
		try {
			if ((GetText1.contains(GetText2)) || (GetText2.contains(GetText1))) {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are matched", LogAs.PASSED, true,
						GetText1);
			} else {
				add(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED,
						true, GetText1);
				Assert.fail();
			}
		} catch (NoSuchElementException e) {
			add1(driver, "The Value1 " + GetText1 + " and Value2 " + GetText2 + " are not matched", LogAs.FAILED, true,
					GetText1);
			Assert.fail();
		}
	}

	public void close(WebDriver driver) {
		try {
			driver.close();
			add(driver, "Application is closed", LogAs.PASSED, true, "Not Req");
		} catch (Exception e) {
			add1(driver, "Unable to close the application ", LogAs.FAILED, true, "Not Req"+"- "+e.getLocalizedMessage());
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public String getText(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			String text = webElement.getText();
			add(driver, "The value ' " + text + " ' is retrieved from the element ' " + values[0] + "'", LogAs.PASSED,
					true, values[0]);
			return text;

		} catch (Exception e) {
			add1(driver, "Unable to retrieve the text " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
			return null;
		}
	}

	public String getTextWithoutFail(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		String text = webElement.getText();
		add(driver, "The value ' " + text + " ' is retrieved for the element ' " + values[0] + "'", LogAs.PASSED, true,
				values[0]);
		return text;

	}

	public static void waitTime(WebDriver driver, String waitSeconds) {
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(waitSeconds), TimeUnit.SECONDS);
	}

	public void scrollBottom(WebDriver driver) {
		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scroll(0,350)", "");
			waitTime(driver, "5");
			// add(driver, "Scrolled to the bottom ", LogAs.PASSED, true, "Not");
		} catch (Exception e) {
			// add1(driver, "Unable to scroll to the bottom", LogAs.FAILED, true, "Not");
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void scrollTop(WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scroll(0,-200)", "");
			add(driver, "Scrolled to the Top ", LogAs.PASSED, true, "Not");

		} catch (Exception e) {
			add1(driver, "Unable to scroll to the Top", LogAs.FAILED, true, "Not"+"- "+e.getLocalizedMessage());
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public boolean verifyElementIsPresent(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement element = driver.findElement(By.xpath(values[1]));
			element.isDisplayed();
			add(driver, "Element '" + values[0] + "' is verified ", LogAs.PASSED, true, values[0]);
			return true;
		} catch (NoSuchElementException e) {
			add1(driver, "Element is Not Present " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
			return true;
		}
	}

	public void verifyElementHasText(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			String text = driver.findElement(By.xpath(values[1])).getText();
			if (!text.equals("")) {
				add(driver, "Element '" + values[0] + "' has text " + text, LogAs.PASSED, true, values[0]);
			} else {
				add1(driver, "No text on the element " + values[0], LogAs.FAILED, true, values[0]);
				((JavascriptExecutor) driver).executeScript("lambda-status=failed");
				Assert.fail();
			}
		} catch (NoSuchElementException e) {
			add1(driver, "Element is Not Present " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public boolean isDisplayed(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			return webElement.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public List<String> getContactsList(WebDriver driver,String tableXpath){
		
		String[] xpath=splitXpath(tableXpath);
		WebElement table = driver.findElement(By.xpath(xpath[1]));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		List<String> contactList=new ArrayList<String>();
		
		for (WebElement row : rows) {
            // Get first cell within the row
            WebElement cell = row.findElement(By.tagName("td"));
            
                System.out.println(cell.getText());
            	contactList.add(cell.getText());
            
        }
		
		return contactList;
	}
	
	///not workk..
//   public List<String> getContactsList1(WebDriver driver,String name){
//		
//	   List<String> contactList1=new ArrayList<String>();
//	   WebElement ele= driver.findElement(By.xpath("//div[text()='"+name+"']"));
//	    Actions action =new Actions(driver);
//	    action.moveToElement(ele).build().perform();
//	    
//	   driver.findElement(By.xpath("//div[text()='"+name+"']//following::div[@class='action-icons']")).click();
//	    
//	    waitForElement1(driver,table);
//	    
//	    contactList1= getContactsList(driver,table);
//		
//		return contactList1;
//	}
	
	public void elementnotvisible(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			for (int i = 1; i <= 100; i++) {

				boolean flag = webElement.isDisplayed();

				if (flag == true) {
					Thread.sleep(5000);
				} else {
					break;
				}
				System.out.println(values[0]+" still visible ..!");
			}

		} catch (Exception e) {

		}
	}

	
	public void elementnotvisible1(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {

			WebElement webElement = driver.findElement(By.xpath(values[1]));
			for (int i = 1; i <= 100; i++) {

				boolean flag = webElement.isDisplayed();

				if (flag == true) {
					Thread.sleep(5000);
				} else {
					break;
				}
				//System.out.println(values[0]+" still visible ..!");
			}

		} catch (Exception e) {

		}
	}

	public String getAttribute(WebDriver driver, String xpath, String attribute) {
		String[] values = splitXpath(xpath);
		try {
			WebElement inputBox = driver.findElement(By.xpath(values[1]));
			String textInsideInputBox = inputBox.getAttribute(attribute);
			add(driver, "Retrieved the text of " + values[0], textInsideInputBox, true, values[0]);
			return textInsideInputBox;
		} catch (NoSuchElementException e) {
			add1(driver, "Unable to retrieve the value " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
			return null;
		}

	}

	public void closetab(WebDriver driver) {

		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_W);
			wait(driver, "2");
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_W);
			wait(driver, "2");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(0));
			wait(driver, "5");

		} catch (Exception e) {
		}
	}

	public boolean verifyElementIsNotPresent(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			WebElement element = driver.findElement(By.xpath(values[1]));
			element.isDisplayed();
			add1(driver, "Element is Present" + values[0], LogAs.FAILED, true, values[0]);
			Assert.fail();
			return true;
		} catch (NoSuchElementException e) {
			add(driver, "Verified Element is not Present" + values[0]+"- "+e.getLocalizedMessage(), values[0], true, values[0]);
		}
		return true;

	}

	public void scrollUsingElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement element = driver.findElement(By.xpath(values[1]));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			add(driver, "Scrolled to " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to scroll " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void goBack(WebDriver driver) {

		try {
			driver.navigate().back();
			add(driver, "Go Back", LogAs.PASSED, true, "goback");

		} catch (Exception e) {
			add(driver, "Unable to Go Back", LogAs.FAILED, true, "goback");
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();

		}
	}

	public void keyBoardEvent(int eventNumber) {
		try {

			Thread.sleep(1000);

			Runtime.getRuntime().exec(

					"cmd /C adb shell input keyevent " + eventNumber);

			Thread.sleep(3000);

		} catch (Throwable t) {

			t.printStackTrace();

		}
	}


	
	public void waitTillVisibilityElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);

		try {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			add(driver, "Waited till the element is visible", LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add(driver, "Unable to wait till an element is visible", LogAs.FAILED, true, values[0]+"-"+e.getLocalizedMessage());
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();

		}
	}

	public void waitTillElementIsClickable(WebDriver driver, String xpath) {
		try {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			String[] values = splitXpath(xpath);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			add(driver, "Waited till the element is clickable", LogAs.PASSED, true, "Scroll down");
		} catch (Exception e) {
			add(driver, "Unable to wait till an element is clickable", LogAs.FAILED, true, "Scroll down"+"- "+e.getLocalizedMessage());
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();

		}
	}

	public void IsElementEnabled(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.isEnabled();
			add(driver, "Element is enabled" + values[0], LogAs.PASSED, true, values[0]);

		} catch (NoSuchElementException e) {
			add(driver, "Element is not enabled", LogAs.FAILED, true, values[0]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	//IsElementPresent _VG
	public boolean IsElementPresent(WebDriver driver, String xpath) {
		
		WebElement ele=driver.findElement(By.xpath(xpath));
		
		if(ele.isDisplayed()){
			return true;
		}else {
			return false;
		}
		
	}
	public int getRandomNum(WebDriver driver, int upperlimit) {
		List<Integer> randomZeroToSeven = new ArrayList<>();
		for (int i = 1; i <= upperlimit; i++) {
			randomZeroToSeven.add(i);
		}
		Collections.shuffle(randomZeroToSeven);

		return randomZeroToSeven.get(0);

	}

	public void deSelectByIndex(WebDriver driver, String xpath, String inputData) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Integer index = new Integer(inputData);
			Select selectBox = new Select(webElement);
			selectBox.deselectByIndex(index);
			add(driver, "Deselect the dropdown by index " + values[0], LogAs.PASSED, true, values[1]);
		} catch (Exception e) {
			add1(driver, "Unable to deselect the dropdown by index" + values[0], LogAs.FAILED, true, values[1]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void deSelectByValue(WebDriver driver, String xpath, String inputData) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Select selectBox = new Select(webElement);
			selectBox.deselectByValue(inputData);
			add(driver, "Deselect the dropdown by index " + values[0], LogAs.PASSED, true, values[1]);
		} catch (Exception e) {
			add(driver, "Unable to deselect the dropdown by index" + values[0], LogAs.FAILED, true, values[1]);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void getWindow(WebDriver driver, String path) {
		try {
			waitTime(driver, "5");
			Main_Window = driver.getWindowHandle();
			System.out.println("Main_Window:" + Main_Window);
			String[] values = splitXpath(path);
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.click();
			Thread.sleep(500);
			ArrayList<String> allWindows = new ArrayList<String>(driver.getWindowHandles());
			System.out.println("2nd Window:" + allWindows.get(1));
			driver.switchTo().window(allWindows.get(1));
		} catch (InterruptedException e) {
		}
	}

	public void switchWindow(WebDriver driver) {
		try {
			driver.switchTo().window(Main_Window);
		} catch (Exception e) {
		}
	}

	public void switchDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void getAutoit(String exePath) {
		try {
			Runtime.getRuntime().exec(exePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void dragElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			fromElement = webElement;
			add(driver, "Drag an element " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to drag an element " + values[0], LogAs.FAILED, true, values[0]);
		}

	}

	public void dropElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			Actions action = new Actions(driver);
			Action dragDrop = action.dragAndDrop(fromElement, webElement).build();
			dragDrop.perform();
			add(driver, "Drop an element " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Unable to drag an element " + values[0], LogAs.FAILED, true, values[0]);
		}
	}

	public boolean isElementSelected(WebDriver driver, String xpaths) {
		String[] values = splitXpath(xpaths);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.isSelected();
			add(driver, "Verified Element is selected " + values[0], LogAs.PASSED, true, values[0]);
			return true;
		} catch (NoSuchElementException e) {

			add1(driver, "Element is not selected " + values[0], LogAs.FAILED, true, values[0]);
			return false;
		}
	}

	public void inVisibilityElement(WebDriver driver, String NormalXpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, WaitElementSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(NormalXpath)));
		} catch (Exception e) {
		}
	}

	public void IstextPresent(WebDriver driver, String inputData) {
		if (driver.getPageSource().contains(inputData)) {
			add(driver, "Text is Present " + inputData, LogAs.PASSED, true, inputData);
		} else {
			add1(driver, "Text is not Present " + inputData, LogAs.FAILED, true, inputData);
		}
	}

	public void waitTillTextIsLoaded(WebDriver driver, String xpath, String inputData) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait waits = new WebDriverWait(driver, WaitElementSeconds);
			waits.until(ExpectedConditions.textToBePresentInElement(webElement, inputData));
			add(driver, "Waited till the text " + inputData + " is loaded", LogAs.PASSED, true, inputData);
		} catch (Exception e) {
			add1(driver, "Unable to Wait till the text " + inputData +"- "+e.getLocalizedMessage()+ " is loaded", LogAs.FAILED, true, inputData);
		}
	}

	public void verifyTextIsNotPresent(WebDriver driver, String NormalXpath, String inputData) {
		try {
			WebDriverWait waits = new WebDriverWait(driver, WaitElementSeconds);
			waits.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(NormalXpath), inputData));
			add(driver, "Text is not present" + inputData, LogAs.PASSED, true, inputData);
		} catch (Exception e) {
			add1(driver, "Text is present" + inputData+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, inputData);
		}

	}

	public void isElementClickable(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait waits = new WebDriverWait(driver, WaitElementSeconds);
			waits.until(ExpectedConditions.elementToBeClickable(webElement));
			add(driver, "Element is clickable " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Element is not clickable " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
		}
	}

	public void isElementSelectable(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait waits = new WebDriverWait(driver, WaitElementSeconds);
			waits.until(ExpectedConditions.elementToBeSelected(webElement));
			add(driver, "Element is selectable " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Element is not selectable " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
		}
	}

	public void waitUntilVisibilityOfElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			WebDriverWait wait = new WebDriverWait(driver, WaitElementSeconds);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			add(driver, "Wait till the Element is visible " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Element is not visible " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
		}

	}

	public void waitForElementNotpresent(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		try {
			WebDriverWait wait = new WebDriverWait(driver, WaitElementSeconds);
			wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))));
			add(driver, "Wait till the Element is visible " + values[0], LogAs.PASSED, true, values[0]);
		} catch (Exception e) {
			add1(driver, "Element is not visible " + values[0]+"- "+e.getLocalizedMessage(), LogAs.FAILED, true, values[0]);
		}
	}

	public String dynamicSendkeys(WebDriver driver, String inputData, String xpath) {
		String[] values = splitXpath(xpath);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		webElement.clear();
		try {
			Thread.sleep(500);
			// String currenttime = new
			// SimpleDateFormat("E_yyyyMMddHHmmssa").format(Calendar.getInstance().getTime());
			String currenttime = new SimpleDateFormat("HHmmssa").format(Calendar.getInstance().getTime());
			String originalValue = inputData;
			String combinedValues = currenttime + originalValue ;
			sendKeys(driver, xpath, combinedValues);
			return combinedValues;
		} catch (InterruptedException e) {

			return null;
		}

	}

	public void partialTextVerify(String sentence, String word) {
		if (sentence.contains(word)) {
		} else {
		}

	}

	public String enterUniquePhone(WebDriver driver, String path) {
		String[] values = splitXpath(path);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		webElement.clear();
		try {
			Thread.sleep(500);
			String phonenumber = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
			sendKeys(driver, path, phonenumber);
			return phonenumber;
		} catch (InterruptedException e) {
			return null;
		}

	}

	public String dynamicTypeName(WebDriver driver, String inputData, String webElementxPath) {
		String[] values = splitXpath(webElementxPath);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		webElement.clear();
		try {
			Thread.sleep(500);
			String currenttime = new SimpleDateFormat("HH_mmss").format(Calendar.getInstance().getTime());
			String combinedValues = inputData + currenttime;
			sendKeys(driver, webElementxPath, combinedValues);
			return combinedValues;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String sumOfTwoNumbers(String GetText1, String GetText2) {
		try {
			int string1 = Integer.parseInt(GetText1);
			int string2 = Integer.parseInt(GetText2);
			int sum1 = string1 + string2;
			String sum = Integer.toString(sum1);
			return sum;
		} catch (Exception e) {
			return null;
		}
	}

	public void quit(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

	public void refreshPage(WebDriver driver) {
		try {
			waitTime(driver, "5");
			driver.navigate().refresh();
			waitTime(driver, "5");
		} catch (Exception e) {
			Assert.fail();
		}
	}

	public void maximize(WebDriver driver) {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	public void keyTab(WebDriver driver) {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).build().perform();

		} catch (Exception e) {
			Assert.fail();
		}
	}

	public void uploadFileRobot(String fileLocation) {
		try {
			StringSelection stringSelection = new StringSelection(fileLocation);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception exp) {
			Assert.fail();
		}
	}

	public void goForward(WebDriver driver) {
		try {
			driver.navigate().forward();

		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public void keyboardTab(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).perform();
	}

	public void enter(WebDriver driver) {
		try {
			Actions actionObject = new Actions(driver);
			actionObject.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	public String alertAccept(WebDriver driver, String path) {
		String[] values = splitXpath(path);

		try {
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			webElement.click();
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.accept();
			return alertText;
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
			return null;
		}
	}

	public void dismissAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public String promptBox(WebDriver driver, String path, String inputData) {
		String[] values = splitXpath(path);
		try {

			WebElement element = driver.findElement(By.xpath(values[1]));
			element.click();
			Alert alert = driver.switchTo().alert();
			driver.switchTo().alert().sendKeys(inputData);
			String alertText = alert.getText();
			alert.accept();
			return alertText;
		} catch (Exception e) {
			return null;
		}
	}

	public void switchToFrame(WebDriver driver, String frameName) {
		String[] values = splitXpath(frameName);
		try {
			WebElement element = driver.findElement(By.xpath(values[1]));
			driver.switchTo().frame(element);

		} catch (NoSuchFrameException e) {

		}
	}

	public void switchToDefaultFrame(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail();
		}
	}

	

	public void escape(WebDriver driver) {
		Robot key;
		try {
			wait(driver,"2");
			key = new Robot();

			key.keyPress(KeyEvent.VK_ESCAPE);
			key.delay(500);
			key.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (AWTException e) {

			e.printStackTrace();
		}

	}
	

	public void keyDown(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject = actionObject.sendKeys(Keys.ARROW_DOWN);
		actionObject.perform();
	}

	public void keyUp(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject = actionObject.sendKeys(Keys.ARROW_UP);
		actionObject.perform();
	}

	public void keyboardPageUp(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.PAGE_UP).perform();
	}

	public void refreshUsingKeys(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.sendKeys(Keys.F5).perform();
	}

	public void keyboardPageDown(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.PAGE_DOWN).perform();
		waitTime(driver, "5");
	}

	public void keyboardEnd(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		waitTime(driver, "5");
	}

	public void keyboardHome(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
		waitTime(driver, "5");
	}

	public void keyboardArrowUp(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_UP).perform();
	}

	public void keyboardArrowDown(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_DOWN).perform();
	}

	public void keyboardArrowLeft(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_LEFT).perform();
	}

	public void keyboardArrowRight(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_RIGHT).perform();
	}

	public void pageMaximizeUsingKey(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject = actionObject.sendKeys(Keys.F11);
		actionObject.perform();
	}

	public void deleteAllCookies(WebDriver driver) {
		driver.manage().deleteAllCookies();
		
	}

	public void navigateUrl(WebDriver driver, String inputData) {
		if(inputData == null) {
			add(driver, " Navigated to " + inputData, LogAs.FAILED, true, inputData);
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
			Assert.fail(inputData);
		}else {
			driver.navigate().to(inputData);
			add(driver, " Navigated to " + inputData, LogAs.PASSED, true, inputData);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

	}

	public void highLightElement(WebDriver driver, String xpath) {
		String[] values = splitXpath(xpath);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement,
				"color: red; border: 3px solid red;");
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
	}

	
	public void windowhandlesframe(WebDriver driver, int values) {

		try {
			ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
			// Set<String>windowhandles1=driver.getWindowHandles();
			System.out.println(tabs1);
			Thread.sleep(5000);
			// List<String>list=new ArrayList<>(windowhandles1)
			driver.switchTo().window(tabs1.get(values));
			System.out.println(driver.getCurrentUrl());
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void windowhandles(WebDriver driver) {

		Set<String> windowhandles1 = driver.getWindowHandles();
		System.out.println(windowhandles1);
		List<String> list = new ArrayList<>(windowhandles1);
		driver.switchTo().window(list.get(1));
		System.out.println(driver.getCurrentUrl());

	}

	public void Arrow_Click(WebDriver driver, String xpaths, String name) {
		String[] values = splitXpath(xpaths);

		List<WebElement> titletext1 = driver.findElements(By.xpath(values[1]));

		System.out.println("titlesize" + titletext1.size());

		for (WebElement webElement1 : titletext1) {
			String name1 = webElement1.getText();
			if (name1.contains(name)) {
				webElement1.click();
				System.out.println(name1);
				add(driver, " Click on " + values[0], LogAs.PASSED, true, values[0]);
				wait(driver,"1");
				break;

			}
		}
	}



	public void Upload_File(WebDriver driver, String Location, String xpaths) {
		String[] values = splitXpath(xpaths);
		Robot rb1;
		try {
			rb1 = new Robot();
			rb1.delay(1000);
			StringSelection ss = new StringSelection(Location);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

			rb1.keyPress(KeyEvent.VK_CONTROL);
			rb1.keyPress(KeyEvent.VK_V);
			rb1.keyRelease(KeyEvent.VK_V);
			rb1.keyRelease(KeyEvent.VK_CONTROL);
			wait(driver, "2");
			rb1.keyPress(KeyEvent.VK_ENTER);
			wait(driver, "10");
			WebElement webElement = driver.findElement(By.xpath(values[1]));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					webElement);
			webElement.click();
			wait(driver, "2");
		} catch (AWTException e) {

			e.printStackTrace();
		}

	}

	public void windowhandles1(WebDriver driver) {

		Set<String> windowhandles1 = driver.getWindowHandles();
		System.out.println(windowhandles1);
		List<String> list = new ArrayList<>(windowhandles1);
		driver.switchTo().window(list.get(0));
		System.out.println(driver.getCurrentUrl());

	}

	public void windowhandles2(WebDriver driver) {

		Set<String> windowhandles1 = driver.getWindowHandles();
		System.out.println(windowhandles1);
		List<String> list = new ArrayList<>(windowhandles1);
		driver.switchTo().window(list.get(2));
		System.out.println(driver.getCurrentUrl());

	}

	

	public void newTab(WebDriver driver) {
		try {
			Robot r = new Robot();
			wait(driver, "1");
			r.keyPress(KeyEvent.VK_CONTROL);
			wait(driver, "1");
			r.keyPress(KeyEvent.VK_T);
			wait(driver, "1");
			r.keyRelease(KeyEvent.VK_CONTROL);
			wait(driver, "1");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			wait(driver, "1");
			driver.switchTo().window(tabs.get(1));
			wait(driver, "1");
		} catch (Exception e) {
		}
	}

	public void newTabpaste(WebDriver driver) {
		try {
			wait(driver, "5");
			Robot r = new Robot();
			r.delay(1000);
			r.keyPress(KeyEvent.VK_CONTROL);

			r.keyPress(KeyEvent.VK_T);
			wait(driver, "5");
			r.keyRelease(KeyEvent.VK_T);
			r.keyRelease(KeyEvent.VK_CONTROL);

			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			wait(driver, "5");
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			wait(driver, "5");
			r.keyPress(KeyEvent.VK_ENTER);
		} catch (Exception e) {
		}
	}

	

	public void newTab2(WebDriver driver) {
		
		try {
			
			((JavascriptExecutor) driver).executeScript("window.open()");

	        // Switch to the new tab
			System.out.println("Switching to new Tab");
			add(driver, "Switching to new Tab ", LogAs.PASSED, true, ""+"");

	        ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
	        System.out.println(tab.size());
	        driver.switchTo().window(tab.get(tab.size() - 1));
	        
		}catch(Exception e) {
			add1(driver, "Could not Switch new Tab", LogAs.FAILED, true,"");

		}
		
	}
	public void get(WebDriver driver, String url) {
		Capabilities localCapabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browser = localCapabilities.getBrowserName().toLowerCase();
		driver.get(url);
		if (browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("UnKnown")) {
			wait(driver, "5");
			driver.get("javascript:document.getElementById('overridelink').click();");
			wait(driver, "5");
		}

	}

	public void closeTab(WebDriver driver) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
		// tabs.remove(tabs.get(0));
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(0));
	}

	public void closeTab1(WebDriver driver, int input) {
		driver.findElement(By.xpath("body")).sendKeys(Keys.CONTROL + "w");
		// tabs.remove(tabs.get(0));
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(input));
	}

	public void switchtotab(WebDriver driver, int inputData) {
		Capabilities localCapabilities = ((RemoteWebDriver) driver).getCapabilities();
		String BROWSER_NAME = localCapabilities.getBrowserName().toLowerCase();
		if (BROWSER_NAME.equalsIgnoreCase("firefox")) {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
			driver.switchTo().defaultContent();
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(inputData));
		}
		if (BROWSER_NAME.equalsIgnoreCase("chrome")) {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
			driver.switchTo().defaultContent();
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(inputData));
			wait(driver,"2");
		}
	}

	public Response apiValidRequest(String URL1, String method, String body, String APIName, int statusCode1)
			throws IOException, JSONException {
		String line = body;
		Response res = null;
		JSONObject jsonObject = null;
		try {
			String URL = URL1;
			// String values[] = header.split(":");
			if (method.equalsIgnoreCase("POST")) {
				res = RestAssured.given().body(line).with().contentType("application/json").then().when().post(URL);
			} else if (method.equalsIgnoreCase("get")) {
				res = RestAssured.given().with().contentType("application/json").then().when().get(URL);
			}

			if (res.statusCode() == statusCode1) {
				add("Requested API " + APIName + " and got  " + res.statusCode() + " response code", LogAs.PASSED, true,
						res.print().toString(), (res.getTime()));

			} else {
				add1("Unsuccessfull API hit:" + APIName + " and got  " + res.statusCode() + " response code",
						LogAs.FAILED, true, res.print().toString());
				Assert.fail();
			}

		} catch (Exception e) {
			add1("Unable to hit the API " + APIName, LogAs.FAILED, true, "");
			
			Assert.fail();
		}
		return res;
	}

	public Response apiValidRequest2(String URL1, String method, String header, String body, String APIName,
			String statusCode1) throws IOException, JSONException {
		String line = null;
		Response res = null;
		try {
			String URL = URL1;
			// String values[] = header.split(":");
			if (method.equalsIgnoreCase("POST")) {

				res = RestAssured.given().header("Authorization", header).body(line).with()
						.contentType("application/json").then().expect().when().post(URL);
			} else if (method.equalsIgnoreCase("get")) {
				res = RestAssured.given().header("Authorization", header).with().contentType("application/json").then()
						.expect().when().get(URL);
			}
			add("Requested API " + APIName + " and got  " + res.statusCode() + " response code", LogAs.PASSED, true,
					res.print().toString(), (res.getTime()));

		} catch (Exception e) {

			add1("Requested API " + APIName + " and got the " + res.statusCode() + " response code", LogAs.FAILED, true,
					"");
			
			Assert.fail();
		}
		return res;
	}

	public Response apiValidRequest3(String url, String method, String header, String body, String APIName,
			int statuscode1) throws IOException, JSONException {
		String body1 = body;
		// int statuscode= statuscode1;

		Response res = null;

		try {
			String URL = url;

			if (method.equalsIgnoreCase("POST")) {
				res = RestAssured.given().headers("Authorization", header).body(body1).with()
						.contentType("application/json").then().expect().when().post(URL);
			} else if (method.equalsIgnoreCase("GET")) {
				res = RestAssured.given().headers("Authorization", header).with().contentType("application/json").then()
						.expect().when().get(URL);
			}
			add("Requested API " + APIName + " and got  " + res.statusCode() + " as response code", LogAs.PASSED, true,
					res.print().toString(), (res.getTime()));

		} catch (Exception e) {

			add1("Requested API " + APIName + " and got the " + res.statusCode() + " as response code", LogAs.FAILED,
					false, "");
			
			Assert.fail();
		}

		JSONObject jsonObject = null;
		jsonObject = new JSONObject(res.asString());
		System.out.println("---output----" + jsonObject.toString() + "---output---");
		return res;
	}

	private static Object parse(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void apideleteRequest(String url, String method, String header) throws IOException, JSONException {

		Response res = null;
		
		String URL = url;
		if(method.equalsIgnoreCase("DELETE")) {
			res = RestAssured.given().headers("Authorization", header).with().contentType("application/json").then()
					.expect().when().delete(URL);
			System.out.println(res);
			JSONObject jsonObject = null;
			jsonObject = new JSONObject(res.asString());
			System.out.println("---output----" + jsonObject.toString() + "---output---");
		}

	}
	
	public static String apiPutBodyRequest(String url, String method, String header, String body) throws JSONException {
		String body1 = body;
		String URL = url;

		Response res = null;

		if (method.equalsIgnoreCase("PUT")) {
			res = RestAssured.given().headers("Authorization", header).body(body1).with()
					.contentType("application/json").then().expect().when().put(URL);
		}

		JSONObject jsonObject = null;
		jsonObject = new JSONObject(res.asString());
		System.out.println("---output----" + jsonObject.toString() + "---output---");
		return res.asString();

	}

	public static String apiputrequest(String url, String method, String header) throws JSONException {

		String URL = url;

		Response res = null;

		if (method.equalsIgnoreCase("PUT")) {
			res = RestAssured.given().headers("Authorization", header).with().contentType("application/json").then()
					.expect().when().put(URL);
		}

		JSONObject jsonObject = null;
		jsonObject = new JSONObject(res.asString());
		System.out.println("---output----" + jsonObject.toString() + "---output---");
		return res.asString();

	}

	public static int GenerateRandomNumber() {

		System.out.println("Random Numbers:");
		Random rand = new Random();
		int num = rand.nextInt(900000) + 100000;
		System.out.println("***************");

		System.out.println(num);

		return num;
	}

	public void mouseOverAndClick(WebDriver driver, String element) {
		String[] values = splitXpath(element);
		WebElement webElement = driver.findElement(By.xpath(values[1]));
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(webElement).click().build().perform();
			// builder.moveToElement(webElement).build().perform();

		} catch (Exception e) {

		}
	}

	

	// Click to given day
	public static void clickGivenDay(List<WebElement> elementList, String day) {
		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		/** Functional JAVA version of this method. */
		/*elementList.stream().filter(element -> element.getText().contains(day)).findFirst()
				.ifPresent(WebElement::click);*/
	}

	public static String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today Int: " + todayInt + "\n");

		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");

		return todayStr;
	}

	public void scrolltill(WebDriver driver) {
		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scroll(0,12200)", "");
			waitTime(driver, "5");
			// add(driver, "Scrolled to the bottom ", LogAs.PASSED, true, "Not");
		} catch (Exception e) {
			// add1(driver, "Unable to scroll to the bottom", LogAs.FAILED, true, "Not");
			Assert.fail();
			((JavascriptExecutor) driver).executeScript("lambda-status=failed");
		}
	}

	public void Alert1(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public void Escape(WebDriver driver) {

		Robot rb1;
		try {
			rb1 = new Robot();
			rb1.delay(1000);
			rb1.keyPress(KeyEvent.VK_ESCAPE);
			rb1.delay(500);
			rb1.keyRelease(KeyEvent.VK_ESCAPE);

		} catch (Exception e) {
			System.out.println("escape its not working");
		}

	}

	public void takescreenshot(WebDriver driver, String screenshot_path) {
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/Screenshots" + screenshot_path + ".png");
		try {
			FileUtils.copyFile(scr, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(driver, "Captured the screenshot " + "", LogAs.PASSED, true, "");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String imageComparision(WebDriver driver, String ExpectedImage_path,String actualImage_path) throws IOException
    {
    	String image_res = null;
		try {
		BufferedImage img1 = ImageIO.read(new File(System.getProperty("user.dir")+"/Screenshots/Expected_screenshot"+ExpectedImage_path+".png"));
	      BufferedImage img2 = ImageIO.read(new File(System.getProperty("user.dir")+"/Screenshots/Actual_screenshot"+actualImage_path+".png"));
	      int w1 = img1.getWidth();
	      int w2 = img2.getWidth();
	      int h1 = img1.getHeight();
	      int h2 = img2.getHeight();
	      long diff = 0;
	      if ((w1!=w2)||(h1!=h2)) {
	         System.out.println("Both images should have same dimwnsions");
	      } else {
	         
	         for (int j = 0; j < h1; j++) {
	            for (int i = 0; i < w1; i++) {
	               //Getting the RGB values of a pixel
	               int pixel1 = img1.getRGB(i, j);
	               Color color1 = new Color(pixel1, true);
	               int r1 = color1.getRed();
	               int g1 = color1.getGreen();
	               int b1 = color1.getBlue();
	               int pixel2 = img2.getRGB(i, j);
	               Color color2 = new Color(pixel2, true);
	               int r2 = color2.getRed();
	               int g2 = color2.getGreen();
	               int b2= color2.getBlue();
	               //sum of differences of RGB values of the two images
	               long data = Math.abs(r1-r2)+Math.abs(g1-g2)+ Math.abs(b1-b2);
	               diff = diff+data;
	            }
	         }
	         String s[] = ExpectedImage_path.split("/");
	         double avg = diff/(w1*h1*3);
	         double percentage = (avg/255)*100;
	         System.out.println("Difference: "+percentage);
	         if (percentage < 1.5) {
	        	 image_res = "Pass";
	        	 
	        	 add(driver, s[1]+" - "+s[2]+" - Image compared sucessfully"+"", LogAs.PASSED, true, "diff.hasDiff()");
	        	 System.out.println(s[1]+" - "+s[2]+" - Image compared sucessfully");
	         }else {
	        	 add1(driver, s[1]+" - "+s[2]+" - Expected and Actual images are not same "+"", LogAs.FAILED, true, "diff.hasDiff()");
				System.out.println(s[1]+" - "+s[2]+" - Expected and Actual images are not same ");
				Assert.fail();
				
	         }
	        
	      }
	      		} catch (Exception e) {
				System.out.println("Exception while taking Screenshot" + e.getMessage());
				return e.getMessage();
			}
		 return image_res;
    	
		} 
	 public static void disableToast(WebDriver driver) {
	    	
			
			try {
				
				JavascriptExecutor js1 = (JavascriptExecutor)driver;
				Object de = js1.executeScript("store.exposed.autoSaveConfig.disableToasts()");
				System.out.println(de);
				
				
			} 
			
			catch (Exception e1)
			{
				Assert.fail();
			}
	
	  }
	 
	 public void getfps(WebDriver driver, String action) {
		 
		 
			try {
				
				JavascriptExecutor js1 = (JavascriptExecutor)driver;
				Object de = js1.executeScript("return store.scene.getEngine().getFps()"); 
				System.out.println(action + " FPS value : "+de);
			
				add(driver, action+" : FPS", LogAs.PASSED, true, ""+de);
				//addfps(driver, action +" FPS", de.toString(), true, "");
				
			} 
			
			catch (Exception e1)
			{
				add1(driver, "Could not retrieve FPS value", LogAs.FAILED, true,"");
			}
	
	  }
	
	 
	  
	 public void Upload_PDF_file(WebDriver driver,String Location) {
			
		 Robot rb1;
			try {
				rb1 = new Robot();
				rb1.delay(1000);
			    StringSelection ss=new StringSelection(Location);
			    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
			    
			    rb1.keyPress(KeyEvent.VK_CONTROL);
			    rb1.keyPress(KeyEvent.VK_V);
			    rb1.keyRelease(KeyEvent.VK_V);
			    rb1.keyRelease(KeyEvent.VK_CONTROL);
			    wait(driver, "2");
			   rb1.keyPress(KeyEvent.VK_ENTER);
			   
			   wait(driver, "2");
			   
			} catch (AWTException e) {
				
				e.printStackTrace();
			}
		
		
	}

	 
	  
	  public void Upload_File1(WebDriver driver,String Location) {
			//String[] values = splitXpath(xpaths);
			 Robot rb1;
				try {
					rb1 = new Robot();
					rb1.delay(1000);
				    StringSelection ss=new StringSelection(Location);
				    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
				    
				    rb1.keyPress(KeyEvent.VK_CONTROL);
				    rb1.keyPress(KeyEvent.VK_V);
				    rb1.keyRelease(KeyEvent.VK_V);
				    rb1.keyRelease(KeyEvent.VK_CONTROL);
				    wait(driver, "4");
				   rb1.keyPress(KeyEvent.VK_ENTER);
				    wait(driver, "10");
				     //WebElement webElement = driver.findElement(By.xpath(values[1]));
				     wait(driver,"2");
				     
					//JavascriptExecutor js = (JavascriptExecutor) driver;
					wait(driver,"2");
					
					wait(driver, "2");
				} catch (AWTException e) {
					
					e.printStackTrace();
				}
			
			
		}
	
	  
	 
	  public void verifyElementText(WebDriver driver, String xpath,String expectedtext) {
			String[] values = splitXpath(xpath);
			try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				waitForElement(driver, xpath);
				String text = driver.findElement(By.xpath(values[1])).getText();
				if (text.equals(expectedtext)) {
					add(driver, "Exepected text is present" + values[0] + "" + text, LogAs.PASSED, true, values[0]);
				} 
			} catch (NoSuchElementException e) {
				add1(driver, "Expected text is Not Present " + values[0], LogAs.FAILED, true, values[0]);
				Assert.fail();
			}
		}
	  
		public void uploadfile(WebDriver driver,String path) {
			  try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				WebElement element = driver.findElement(By.xpath("//input[@type='file']"));
				File file = new File(path);
				System.out.println(file.getAbsolutePath());
				element.sendKeys(file.getAbsolutePath());
				add(driver, "uploaded the file " + path, LogAs.PASSED, true, path);
				wait(driver, "2");
			} catch (Exception e) {
				add1(driver, "upload is falied - " + path + e, LogAs.FAILED, true, e.getLocalizedMessage());
				e.printStackTrace();
				((JavascriptExecutor) driver).executeScript("lambda-status=failed");
				Assert.fail();
			}
		}
		
		public void uploadfile1(WebDriver driver,String path) {
			  try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				WebElement element = driver.findElement(By.xpath("//input[contains(@type,'file')]"));
				File file = new File(path);
				System.out.println(file.getAbsolutePath());
				element.sendKeys(file.getAbsolutePath());
				add(driver, "uploaded the file " + path, LogAs.PASSED, true, path);
				wait(driver, "2");
			} catch (Exception e) {
				add1(driver, "upload is falied - " + path + e, LogAs.FAILED, true, e.getLocalizedMessage());
				e.printStackTrace();
				((JavascriptExecutor) driver).executeScript("lambda-status=failed");
				Assert.fail();
			}
		}
		
		
		
		//vignesh
		public static String randomString(int length) {
	    	String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
	        Random random = new Random();
	        StringBuilder stringBuilder = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            char randomChar = CHARACTERS.charAt(randomIndex);
	            stringBuilder.append(randomChar);
	        }

	        return stringBuilder.toString();
	    }
		
		public static int getRandomNumber(int minValue,int maxValue) {
	        
//			int minValue=30;
//			int maxValue=300;
			
	        Random random = new Random();
	        int number=random.nextInt((maxValue - minValue) + 1) + minValue;
	        System.out.println(number);
	        return number;
	    }
	
		public void typeContacts(WebDriver driver,int count) {

			String firstName;
			String email;
			String inputpath;
			
			for(int i=1;i<=count;i++) {
				
			firstName=randomString(4);
		    email=firstName+"@mailinator.com";
		    System.out.println(email);
		    String value=Integer.toString(i);
		    driver.findElement(By.xpath("(//*[text()='Email Address']//following::input)[" + value + "]")).sendKeys(email);
			wait(driver,"1");
			}
		}
		
		public void searchExistList(WebDriver driver,String existList) {
			
			driver.findElement(By.xpath("//div[@label='"+existList+"']")).click();
			wait(driver,"1");
		}
		 
		public void selectCurrency(WebDriver driver, String Xpath, String Currency) {
			String[] values = splitXpath(Xpath);
			 waitForElement1(driver,Xpath);
			 
			List<WebElement> listItems=driver.findElements(By.xpath(values[1]));

			System.out.println("list taken");
			for (WebElement listItem : listItems) {
	           String currencyName= listItem.getText();
//	           System.out.println(currencyName);
	           if(currencyName.contains(Currency)) {
	        	  // driver.findElement(By.xpath("//span[text()='"+currencyName+"']")).click();
	        	  WebElement element= listItem;
	        	  
	        	  JavascriptExecutor executor = (JavascriptExecutor) driver;
	  			  executor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",element);
	  		      element.click();
	  		      
	  		      add(driver, "Click on " + values[0], LogAs.PASSED, true, Currency);
	        	   break;
	           }
	        }
		 }
		 
		public int getAmount(WebDriver driver, String Xpath) {
			
			String[] values = splitXpath(Xpath);

			String value=getText(driver,Xpath);
			int count=value.length();
//			System.out.println(count);
			String num="0123456789"; 
			int numCount=num.length();
//			System.out.println(numCount);
	//
			char charact;
			char numCharact;
			String amountString="";
			
			for(int i=0;i<count;i++) {
				charact=value.charAt(i);
				 for(int j=0;j<numCount;j++) {
					 numCharact=num.charAt(j);
					 if(charact==numCharact) {
						 amountString=amountString+charact;
					     break;
					 }
				 }
			}
			int amount=Integer.parseInt(amountString);
			 System.out.println(amount);
			 
			 return amount;
		
		}
		
		
          
		
		public void typeCurrency(WebDriver driver,String Xpath) {
			
			String[] values = splitXpath(Xpath);

			waitForElement(driver,Xpath);
			int min_Amount=20;
			int amount=getRandomNumber(min_Amount,30);
			String amountValue=String.valueOf(amount);
			
			
			for(int i=0;i<5;i++) {
				doubleClick(driver,Xpath);
				sendKeys(driver,Xpath,amountValue);
				wait(driver,"4");
				boolean error=isDisplayed(driver,error_amount);
				
				if(error) {
					  min_Amount=getAmount(driver,error_amount);
					  amount=getRandomNumber(min_Amount,500);
					  amountValue=Integer.toString(amount);
				}else {
					break;
				}

			}
			
			
		}
		
}

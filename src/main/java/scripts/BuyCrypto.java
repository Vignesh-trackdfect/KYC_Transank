package scripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commonMethods.Keywords;
import commonMethods.TestNgXml;
import commonMethods.Utils;

public class BuyCrypto extends Keywords{

	
	public void buyCrypto_CardPayment(WebDriver driver,String Currency_option) {
		
		String Url=TestNgXml.getdatafromExecution().get("buyCryptoEuro");
		String email_id=Utils.getDataFromTestData("KYC", "Mail");
		String Eth_Address=Utils.getDataFromTestData("KYC", "ETH Address");//Card Number1
		String Card_Number_USD=Utils.getDataFromTestData("KYC", "USD");
		String Card_Number_EURO=Utils.getDataFromTestData("KYC", "EUR");
		String Card_Number_SGD=Utils.getDataFromTestData("KYC", "SGD");
		String Card_Number_GBP=Utils.getDataFromTestData("KYC", "GBP");
		
		String Expiry_date=Utils.getDataFromTestData("KYC", "Exp date");
		String CVV_value=Utils.getDataFromTestData("KYC", "CVV");
		String Card_Number="";
		String Currency_Type=Currency_option;
		
        if(Currency_Type=="British pound") {
        	Card_Number=Card_Number_GBP;
		}else if(Currency_Type=="Euro"){
			Card_Number=Card_Number_EURO;
		}else if(Currency_Type=="Singapore Dollar") {
			Card_Number=Card_Number_SGD;
		}else{
			Card_Number=Card_Number_USD;
		}
        
        
		navigateUrl(driver,Url);
		
		waitForElement1(driver,Buy_Page);
		
		waitForElement(driver,selectCurrency);
		click(driver,selectCurrency);
		
		waitForElement(driver,AllCurrency);
		selectCurrency(driver,currency_list,Currency_Type);
		
		waitForElement(driver,VISA_payment);
		click(driver,VISA_payment);
		
		typeCurrency(driver,amount_input);
//		waitForElement(driver,amount_input);
//		doubleClick(driver,amount_input);
//		sendKeys(driver,amount_input,"110");
		waitForElement(driver,Select_CryptoType);
		click(driver,Select_CryptoType);
		
		waitForElement(driver,popular_Currency);
		
		waitForElement(driver,ETH_arbiturium);
		click(driver,ETH_arbiturium);
		
		waitForElement(driver,Buy_Now_Btn);
		click(driver,Buy_Now_Btn);
		
		waitForElement1(driver,wallet_Address);
		sendKeys(driver,wallet_Address,Eth_Address);
		
		click(driver,Buy_ETH_Btn);
		
		waitForElement1(driver,email_Input);
		sendKeys(driver,email_Input,email_id);
		
		waitForElement(driver,Contine_Btn);
		click(driver,Contine_Btn);
		
		waitForElement1(driver,verification_input);
		newTab(driver);
		wait(driver,"1");
        driver.get("https://www.mailinator.com/");
		waitForElement1(driver,publicinbox);
		click(driver, publicinbox);
		waitForElement1(driver,txtboxmail);
		clearAndType(driver, txtboxmail, email_id);
		waitForElement(driver,gobtn);
		click(driver, gobtn);
		
		waitForElement1(driver,verification_mail);
		click(driver,verification_mail);
		
		scrollBottom(driver);
		driver.switchTo().frame("html_msg_body");
		
		String verificationCode=getText(driver,verification_Code);
		driver.switchTo().defaultContent();
		
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_CONTROL);
			wait(driver, "1");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			wait(driver, "1");
//			System.out.println(tabs.size());
			driver.switchTo().window(tabs.get(0));
			wait(driver, "1");
		} catch (Exception e) {
			
		}
		
		waitForElement1(driver,verification_input);
		sendKeys(driver,verification_input,verificationCode);
		
		click(driver,I_agree);
		
		waitForElement(driver,Contine_Btn);
		click(driver,Contine_Btn);
		
		
		waitForElement1(driver,Card);
		System.out.println("card payment");
		waitForElement1(driver,cardHolderName);
		wait(driver, "2");
		driver.switchTo().frame(0);
		System.out.println("After frame switch");
		click(driver,cardNum_input);
		
		sendKeys(driver,cardNum_input,Card_Number);
		
		driver.switchTo().defaultContent();
		
		waitForElement1(driver,cardHolderName);
		sendKeys(driver,cardHolderName,"Test");
		
		driver.switchTo().frame(1);
		waitForElement1(driver,expiry_date);
		click(driver,expiry_date);
		sendKeys(driver,expiry_date,Expiry_date);
		
		driver.switchTo().defaultContent();

		
		driver.switchTo().frame(2);

		waitForElement1(driver,CVV_input);
		click(driver,CVV_input);
		sendKeys(driver,CVV_input,CVV_value);
		
		driver.switchTo().defaultContent();

		click(driver,cardHolderName);
		wait(driver,"2");
		
		doubleClick(driver,AddToCard_Btn);
		
		waitForElement1(driver,confirm_order_page);
		click(driver,I_accept);
		
		waitForElement(driver,Confirm_Btn);
		click(driver,Confirm_Btn);
		
		boolean resolve=isDisplayed(driver,resolve_Btn);
		if(resolve) {
			click(driver,resolve_Btn);
			waitForElement1(driver,cancel_Order_Btn);
			click(driver,cancel_Order_Btn);
			waitForElement1(driver,reason_input);
			sendKeys(driver,reason_input,"Another Payment work");
			click(driver,cancel_Ok_btn);
			waitForElement1(driver,payment_History);
			click(driver,back_Arrow);
			waitForElement1(driver,cancelled);
			click(driver,back_Arrow);
			waitForElement1(driver,confirm_order_page);
	 		wait(driver,"1");
//	 		waitForElement(driver,I_accept);
//			click(driver,I_accept);
			
			waitForElement(driver,Confirm_Btn);
			doubleClick(driver,Confirm_Btn);
			
		}
		
		waitForElement1(driver,Payment_authorisation_page);
		waitForElement1(driver,payment_processing);
		
		//elementnotvisible(driver,payment_processing);
		

	}
	
	
}

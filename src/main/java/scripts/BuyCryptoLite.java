package scripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import atu.testng.reports.logging.LogAs;
import commonMethods.Keywords;
import commonMethods.TestNgXml;
import commonMethods.Utils;

public class BuyCryptoLite extends Keywords {

	
public void buyCrypto_Lite(WebDriver driver,String Currency_option) {
		
		String Url=TestNgXml.getdatafromExecution().get("buyCryptoEuro");
		String email_id=randomString(6)+"@mailinator.com";
		String Eth_Address=Utils.getDataFromTestData("KYC", "ETH Address");
		
		String Expiry_date=Utils.getDataFromTestData("KYC", "Exp date");
		String CVV_value="";
		String Checkout=Utils.getDataFromTestData("KYC", "Checkout");
		
		//Personal details
		String FirstName=Utils.getDataFromTestData("KYC", "First Name");
		
		String LastName=Utils.getDataFromTestData("KYC", "Last Name");
		String MobileNum=Utils.getDataFromTestData("KYC", "Mobile Number");
		

	    // Address details
		String addressLine=Utils.getDataFromTestData("KYC", "Address Line");
		String StateValue=Utils.getDataFromTestData("KYC", "State");
		String CityValue=Utils.getDataFromTestData("KYC", "City");
		String post_code=Utils.getDataFromTestData("KYC", "Postal Code");
		String Country_value=Utils.getDataFromTestData("KYC", "Country");
		
		String Card_Number="";
		String Currency_Type=Currency_option;
		
        if(Currency_Type=="US Dollar") {
        	Card_Number=Utils.getDataFromTestData("KYC", "USD");
        	CVV_value=Utils.getDataFromTestData("KYC", "CVV2");
		}else{
		    Card_Number=Utils.getDataFromTestData("KYC", "EUR");
        	CVV_value=Utils.getDataFromTestData("KYC", "CVV1");

		}
        
        
		navigateUrl(driver,Url);
		
		waitForElement1(driver,Buy_Page);
		
		waitForElement(driver,selectCurrency);
		click(driver,selectCurrency);
		
		waitForElement(driver,AllCurrency);
		selectCurrency(driver,currency_list,Currency_Type);
		
		waitForElement(driver,VISA_payment);
		click(driver,VISA_payment);
		
		typeCurrency1(driver,amount_input);
		
		
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
		newTab2(driver);
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
	
			
			waitForElement1(driver,personalDetailsPage);
			waitForElement(driver,FirstName_Input);
			sendKeys(driver,FirstName_Input,FirstName);
			
			waitForElement(driver,LastName_Input);
			sendKeys(driver,LastName_Input,LastName);
			
			waitForElement(driver,Mobile_input);
			doubleClick(driver,Mobile_input);
			sendKeys(driver,Mobile_input,MobileNum);
			
			waitForElement(driver,Contine_Btn);
			click(driver,Contine_Btn);
			
			waitForElement1(driver,Address_input);
			sendKeys(driver,Address_input,addressLine);
			
			waitForElement(driver,State_Input);
			sendKeys(driver,State_Input,StateValue);
			
			waitForElement(driver,city_Input);
			sendKeys(driver,city_Input,CityValue);
			
			waitForElement(driver,postCode_Input);
			sendKeys(driver,postCode_Input,post_code);
			
			waitForElement(driver,country_Input);
			click(driver,country_Input);
			sendKeys(driver,country_Input,Country_value);
	         
			try {
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_DOWN);
				r.keyRelease(KeyEvent.VK_CONTROL);
				enter(driver);
			} catch (Exception e) {
				
			}
			
			waitForElement(driver,Contine_Btn);
			click(driver,Contine_Btn);
			
			waitForElement1(driver,billing_AddressPage);
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
		
		try {
			
	         waitForElement(driver,Amt_delivered);
			 String success=getText(driver,Amt_delivered);
			 
			 add(driver,"Delivered messege : ", success, true, "");

			
		}catch(Exception e) {
			
			add1(driver, "Failed to get the delivered message ..!", LogAs.FAILED, true, "");

		}

		

	}
	

}

package Locators;

public interface Email_Verification_Page_Locators {

	public static String verification_input="Verification input>//input[@name='verificationCode']";
	public static String I_agree="I agree checkbox>//span[contains(text(),'I agree with')]";
	
	//Mailinator
	public static String publicinbox = "click on public inbox >//a[@class=\"inbox-link\"]";
	public static String txtboxmail = "write a text box >(//input[@class=\"primary-input\"])";
	public static String gobtn = "Tap on go button >(//button[@onclick=\"subInbox($('#inbox_field').val());\"])";
	public static String verification_mail="Verification Mail>//td[contains(text(),'verification code')]";
	public static String verification_Code="Verification Code>(//td[contains(text(),'Your Verification code:')]//following::td)[2]";
	
	
}

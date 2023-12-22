package Locators;

public interface Card_Deatails_Page_Locators {

	public static String Card = "Card >//*[text()='Add New Card']";
	public static String cardNum_input="Card Number Input>//input[@id='checkout-frames-card-number']";
	public static String cardHolderName="Card holder name input>//input[@name='cardholder-name']";
	public static String expiry_date="Expiry Date input>//input[@id='checkout-frames-expiry-date']";
	public static String CVV_input="CVV>//input[@id='checkout-frames-cvv']";
	public static String AddToCard_Btn="ADD To Card Button>(//*[text()='Add New Card']//following::button)[1]";
	public static String I_understand="I understand Button>//*[text()='I Understand']";
	
	public static String country_list="countries>//*[@aria-controls='countryCode-listbox']";
	
}

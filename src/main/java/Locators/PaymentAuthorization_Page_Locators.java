package Locators;

public interface PaymentAuthorization_Page_Locators {

	
	public static String Payment_authorisation_page="Payment Authorisation>//*[text()='Payment Authorization']";
	public static String payment_processing="Payment processing>//*[text()='We will send you a confirmation email once the order is processed.']";//*[text()='We will send you a confirmation email once the order is processed.']
	public static String All_done="All done>//*[text()='All done!']";
	
	//Only for US Doller currency this locators used
	public static String CheckoutFrame="Checkout frmae>//iframe[@class='challengeIframe']";
	public static String Checkout="Checkout>//input[@id='password']";
	public static String continueBtn="Continue Btn>//input[@type='submit']";
	public static String trackOrder="Track Order>//*[text()='Track the Order']";
	public static String processingTime="Processsing Time>//*[text()='Processing time']";
	public static String Amt_delivered="Amount Succesfully Delivered>//h4[contains(., 'successfully delivered to your wallet')]";
	
	//only for Instant transfer payment method this locators used
	public static String complete_your_payment="Complete your payment>//*[text()='Complete Your Payment']";
	public static String I_have_paid="I have Paid Button>//*[text()='I have Paid']";
	
}

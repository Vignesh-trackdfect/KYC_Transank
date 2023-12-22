package Locators;

public interface HOME_Page_Locators {

	public static String Buy_Page="Buy Page>//div[text()='Buy']";
	public static String selectCurrency="Select Currency>(//button[@name='selectFiat'])[1]";
	public static String AllCurrency="All currency text>//*[text()='All Currencies']";
	public static String VISA_payment="VISA payment>//*[@alt='Card Payment']";
	public static String Instant_Transfer="Instant Transfer>//*[text()='Instant Transfer']";
	public static String paySecure_Gp="Pay Secure Google pay>//*[text()='Pay securely using Google Pay']";
	public static String pay_GooglePay="Pay Google pay button>//button[@aria-label='Pay with GPay']";
	
	public static String amount_input="Amount Input>(//*[@id='transak-calculator-source'])[1]";
	public static String currency_list="Currency List>//li[contains(@class,'list-group-item')]";
	public static String back_Arrow="Back_Arrow>//*[@id='arrow-left']";
	public static String indian_rupee="Indian Rupee>//span[text()='Indian rupee']";
	public static String Euro_currency="Euro_Currency>//span[text()='Euro']";
	public static String error_amount="Error amount>(//div[@class='alert alert-danger'])[1]";
	public static String Select_CryptoType="Select CryptoCurrency>(//button[@name='selectFiat'])[2]";
	public static String popular_Currency="Popular Currency>//*[text()='Popular Currencies']";
	
	public static String ETH_arbiturium="ETH arbiturium>(//span[text()='Ethereum']//following::div[text()='arbitrum'])[1]";
	public static String Buy_Now_Btn="Buy Now button>//*[text()='Buy Now']";
    public static String Estimate_Value="value>//*[text()='You receive (estimate)']//following::input[@id='transak-calculator-source']";
    
    public static String More_Option="More Options menu bar >(//*[text()='Enter Your Wallet Address']//following::button)[1]";
	
}

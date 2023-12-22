package commonMethods;

public interface KYC_Locators {

    //Buy Page Locators
	public static String Buy_Page="Buy Page>//div[text()='Buy']";
	public static String selectCurrency="Select Currency>(//button[@name='selectFiat'])[1]";
	public static String AllCurrency="All currency text>//*[text()='All Currencies']";
	public static String VISA_payment="VISA payment>//*[@alt='Card Payment']";
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
	
	public static String wallet_Address="Wallet Address Input>//input[@id='walletAddress']";
	public static String Buy_ETH_Btn="Buy ETH Btn>//*[text()='Buy ETH']";
	
	public static String email_Input="Email Input>//input[@name='email']";
	public static String Contine_Btn="Continue Button>//*[text()='Continue']";
	public static String verification_input="Verification input>//input[@name='verificationCode']";
	public static String verification_mail="Verification Mail>//td[contains(text(),'verification code')]";
	public static String verification_Code="Verification Code>(//td[contains(text(),'Your Verification code:')]//following::td)[2]";
	public static String I_agree="I agree checkbox>//span[contains(text(),'I agree with')]";
	
	
	
			
	public static String publicinbox = "click on public inbox >//a[@class=\"inbox-link\"]";
	public static String txtboxmail = "write a text box >(//input[@class=\"primary-input\"])";
	public static String gobtn = "Tap on go button >(//button[@onclick=\"subInbox($('#inbox_field').val());\"])";
	
	
	public static String track_Order="Track Order>//*[text()='Track the Order']";
	public static String Instant_Transfer="Instant Transfer>//*[text()='Instant Transfer']";
	public static String paySecure_Gp="Pay Secure Google pay>//*[text()='Pay securely using Google Pay']";
	public static String pay_GooglePay="Pay Google pay button>//button[@aria-label='Pay with GPay']";
	
	public static String resolve_Btn="Resolve Btn>//button[text()='Resolve']";
	public static String waitForPayment="Previous payment order status>//*[text()='Waiting for the transfer']";
	public static String cancel_Order_Btn="Cancel Order button>//*[text()='Cancel Order']";
	public static String Complete_Payment_Btn="Complete payment Button>//*[text()='Complete Payment']";
	public static String cancel_reason="Reason for Cancel >//*[text()='What is your reason for cancelling?']";
	public static String reason_input="Reason input>//input[@id='reason']";
	public static String cancel_Ok_btn="Cancel the order btn>//*[text()='Cancel The Order']";
	public static String payment_History="Payment History>//*[text()='Order History']";
	public static String cancelled="Cancelled>//*[text()='Cancelled']";
	//public static String back_Arrow="Back Arrow>"
	
	
	public static String complete_your_payment="Complete your payment>//*[text()='Complete Your Payment']";
	public static String I_have_paid="I have Paid Button>//*[text()='I have Paid']";
	public static String Amt_delivered="Amount Succesfully Delivered>//h4[contains(., 'successfully delivered to your wallet')]";
	
	
	
	public static String verify_Identity="Verify Identity>//*[text()='Verify your identity']";
    public static String start_verification="Start verification>//*[text()='Start verification']";
    public static String Issuing_country="Issuing Country>//*[@placeholder='Select issuing country']";
    public static String UK_Issuing="Select UK >//*[text()='United Kingdom of Great Britain and Northern Ireland']";
    public static String Driving_License="Driving License>//*[text()='Driving licence']";
    public static String upload_Image="Upload License>//*[text()='or upload photo – no scans or photocopies']";
    
	public static String Upload_Btn="Upload button>//button[text()='Upload']";
	
	
	
	
}

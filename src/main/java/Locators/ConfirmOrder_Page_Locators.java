package Locators;

public interface ConfirmOrder_Page_Locators {

	public static String confirm_order_page="Confirm Order Page>//*[text()='Confirm Your Order']";
	public static String I_accept="I Accept Checkbox>//span[contains(text(),'I accept')]";
	public static String Confirm_Btn="Confirm Button>//*[text()='Confirm']";
	
	
	//If resolve Page Displayed Use this Locators
	public static String payment_History="Payment History>//*[text()='Order History']";
	public static String cancelled="Cancelled>//*[text()='Cancelled']";
	public static String resolve_Btn="Resolve Btn>//button[text()='Resolve']";
	public static String waitForPayment="Previous payment order status>//*[text()='Waiting for the transfer']";
	public static String cancel_Order_Btn="Cancel Order button>//*[text()='Cancel Order']";
	public static String Complete_Payment_Btn="Complete payment Button>//*[text()='Complete Payment']";
	public static String cancel_reason="Reason for Cancel >//*[text()='What is your reason for cancelling?']";
	public static String reason_input="Reason input>//input[@id='reason']";
	public static String cancel_Ok_btn="Cancel the order btn>//*[text()='Cancel The Order']";
	
	
}

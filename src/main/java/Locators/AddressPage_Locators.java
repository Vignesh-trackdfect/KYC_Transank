package Locators;

public interface AddressPage_Locators {

	public static String Address_input="Address input>//input[@name='addressLine1']";
	public static String Address_Page="Addresss Page>//*[text()='Address Line']";
    public static String State_Input="State Input>//input[@name='state']";
    public static String city_Input="City Input>//input[@name='city']";
    public static String postCode_Input="Post Code Input>//input[@name='postCode']";
    public static String country_Input="Country Input>//input[@id='countryCode']";
    public static String UK_select="Select UK>//*[@role='combobox' and @aria-activedescendant='countryCode-option-0']";
    
	public static String billing_AddressPage="Billing Address>//*[text()='Billing Address']";

}

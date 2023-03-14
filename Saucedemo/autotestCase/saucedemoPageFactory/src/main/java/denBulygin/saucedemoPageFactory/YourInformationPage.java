package denBulygin.saucedemoPageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YourInformationPage {
	
	WebDriver driver;
	
	@FindBy (css = "#first-name")
	private WebElement firstName;
	
	@FindBy (css = "input[placeholder='Last Name']")
	private WebElement lastName;
	
	@FindBy (name = "postalCode")
	private WebElement zipCode;
	
	@FindBy (css = "#continue")
	private WebElement continuedButton;
	
	public YourInformationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// input delivery data (sixth step)
	public OverviewPage inputYourInformation(String name, String surname, String zip) {
		firstName.sendKeys(name);
		lastName.sendKeys(surname);
		zipCode.sendKeys(zip);
		continuedButton.click();
		return new OverviewPage(driver);
	}
}

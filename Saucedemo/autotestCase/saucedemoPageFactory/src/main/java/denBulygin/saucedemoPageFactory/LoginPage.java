package denBulygin.saucedemoPageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;
	String url = "https://www.saucedemo.com/";
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (id = "user-name")
	private WebElement userName;
	
	@FindBy (name = "password")
	private WebElement userPassword;
	
	@FindBy (css = "input.submit-button.btn_action")
	private WebElement loginButton;
	
	public CatalogPage loginAccount(String name, String password) {
		userName.sendKeys(name);
		userPassword.sendKeys(password);
		loginButton.click();
		return new CatalogPage(driver);
	}
	
	public void goToSite() {
		driver.get(url);
	}
}

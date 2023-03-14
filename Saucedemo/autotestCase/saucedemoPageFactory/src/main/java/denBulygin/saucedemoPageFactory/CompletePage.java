package denBulygin.saucedemoPageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompletePage {
	
	WebDriver driver;
	
	public CompletePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (css = "div h2")
	private WebElement confirmHeader;
	
	@FindBy (css = "div div[class='complete-text']")
	private WebElement confirmMessage;
	
	public String getConfirmHeader() {
		String completeHeader = confirmHeader.getText();
		return completeHeader;
	}
	
	public String getConfirmMessage() {
		String completeText = confirmMessage.getText();
		return completeText;
	}
	
	

}

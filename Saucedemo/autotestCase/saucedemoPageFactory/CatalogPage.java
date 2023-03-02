package denBulygin.saucedemoPageFactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CatalogPage {
	
	WebDriver driver;
	
	@FindBy (css = "div[class='inventory_item_name']")
	private List<WebElement> products;
	
	@FindBy (css = ".shopping_cart_link")
	private WebElement goToCartButton;
	
	private By addButton = By.xpath("//div[@class='pricebar']/button");
	
	public CatalogPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public YourCartPage goToCart() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", goToCartButton);
		goToCartButton.click();
		return new YourCartPage(driver);
	}
	
	public List<WebElement> getProducts() {
		return products;
	}
	
	public void addToCart(WebDriver driver, String[] goods) {
		String goodsName;
		int addedToCart = 0;
		List<String> goodsNeededList = Arrays.asList(goods);
		List<WebElement> prod = getProducts();
		for (int i = 0; i < prod.size(); i++) {
			goodsName = prod.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				driver.findElements(addButton).get(i).click();
				addedToCart++;
				if (addedToCart==goods.length) {
					break;
				}
			}
		}
	}
	
	// checking modify the button's name addToCart to remove
	public boolean verifyChangeingNameAddButton(WebDriver driver, String[] goods) {
		String goodsName;
		String nameButton;
		int checkButton = 0;
		List<String> goodsNeededList = Arrays.asList(goods);
		List<WebElement> prod = getProducts();
		for (int i = 0; i < prod.size(); i++) {
			goodsName = prod.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				nameButton = driver.findElements(addButton).get(i).getText();
				Assert.assertEquals(nameButton, "Remove");
				checkButton++;
				if (checkButton==goods.length) {
					return true;
				}
			}
		}
		return false;
	}
	
	

}

package denBulygin.saucedemoPageFactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YourCartPage {
	
	WebDriver driver;
	
	@FindBy (css = "div.inventory_item_name")
	private List<WebElement> productsInCart;
	
	@FindBy (xpath = "//div[@class='cart_footer']//button[@id='checkout']")
	private WebElement checkoutButton;
	
	public YourCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getProductsInCart() {
		return productsInCart;
	}
	
	public YourInformationPage goToYourInformation() {
		checkoutButton.click();
		return new YourInformationPage(driver);
	}
	
	// verification added goods in the Cart
	public boolean checkItem (WebDriver driver, String[] goods) {
		String goodsName;
		int itemToCartChecked = 0;
		List<String> goodsList = Arrays.asList(goods);
		List<WebElement> goodsInCart = getProductsInCart();
		for (int i = 0; i < goodsInCart.size(); i++) {
			goodsName = goodsInCart.get(i).getText();
			if (goodsList.contains(goodsName)) {
				itemToCartChecked++;
				if (itemToCartChecked==goods.length) {
				return true;
				}
			}
		}
		return false;
	}

}

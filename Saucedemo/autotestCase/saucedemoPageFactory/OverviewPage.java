package denBulygin.saucedemoPageFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OverviewPage {
	
	WebDriver driver;
	
	@FindBy (css = "div.inventory_item_name")
	private List<WebElement> products; 
	
	@FindBy (id = "finish")
	private WebElement confirmButton;
	
	private By priceBy = By.cssSelector(".inventory_item_price");
	private By grabTaxStr = By.cssSelector(".summary_tax_label");
	
	public OverviewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public double grabTaxStr() {
		String taxGrabStr = driver.findElement(grabTaxStr).getText();
		taxGrabStr = taxGrabStr.replaceAll("\\$", "").split(" ")[1];
		double taxGrab = Double.valueOf(taxGrabStr);
		return taxGrab;
	}
	
	public double grabBillStr() {
		String sumGrabStr = driver.findElement(By.cssSelector(".summary_total_label")).getText();
		sumGrabStr = sumGrabStr.replaceAll("\\$", "").split(" ")[1];
		double sumGrab = Double.valueOf(sumGrabStr);
		return sumGrab;
	}
	
	// count tax bill for choose goods
	public double countTaxBill (WebDriver driver, String[] goodsNeeded, int tax) {
		String goodsName;
		String stringPrice;
		double price;
		double taxBill = 0;
		int addedToCart = 0;
		MathContext context = new MathContext(3, RoundingMode.HALF_EVEN);
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = products;
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				stringPrice = driver.findElements(priceBy).get(i).getText();
				stringPrice = stringPrice.replaceAll("\\$", "");
				price = Double.valueOf(stringPrice);
				taxBill += (price * tax / 100);
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
		BigDecimal result = new BigDecimal(taxBill, context);
		taxBill = result.doubleValue();
		return taxBill;
	}
	
	// count total bill with price and tax
	public double countSumBill (WebDriver driver, String[] goodsNeeded, double taxBill ) {
		String goodsName;
		String stringPrice;
		double price;
		int addedToCart = 0;
		double bill = 0;
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = products;
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				stringPrice = driver.findElements(priceBy).get(i).getText();
				stringPrice = stringPrice.replaceAll("\\$", "");
				price = Double.valueOf(stringPrice);
				bill += price ;
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
		
		return  bill += taxBill;
	}
	
	public CompletePage goToCompletePage() {
		confirmButton.click();
		return new CompletePage(driver);
	}

}

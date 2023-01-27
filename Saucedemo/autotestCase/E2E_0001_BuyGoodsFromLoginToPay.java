package testCase;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

public class E2E_0001_BuyGoodsFromLoginToPay {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String urlSite = "https://www.saucedemo.com/";
		String userName = "standard_user";
		String password = "secret_sauce";
		String firstName = "Дмитрий";
		String lastName = "Петров";
		String zipCode = "445056";
		String[] goodsNeeded = {"Sauce Labs Bolt T-Shirt", "Test.allTheThings() T-Shirt (Red)"};
		int tax = 8;
		
		System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		// manage driver
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); 
		driver.manage().window().maximize();
		
		// go to the application
		driver.get(urlSite); 
		
		// log in (precondition)
		driver.findElement(By.id("user-name")).sendKeys(userName); 
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("input.submit-button.btn_action")).click(); 
		
		// checking opened page (first step - expected result)
		String titlePage = driver.findElement(By.cssSelector("span[class='title']")).getText();
		String urlPage = driver.getCurrentUrl();
		Assert.assertEquals(titlePage, "PRODUCTS");
		Assert.assertEquals(urlPage, "https://www.saucedemo.com/inventory.html");
		driver.findElement(By.xpath("//div[@class='inventory_item']")).isDisplayed(); 
		
		// add goods to the cart (second and third steps)
		addToCart(driver, goodsNeeded); 
		
		// checking goods added to cart (second and third steps - expected result)
		Assert.assertTrue(changeNameAddButtonToRemoveButton(driver, goodsNeeded));
		Assert.assertEquals(driver.findElement(By.cssSelector(".shopping_cart_badge")).getText(), "2"); 
		
		// navigate to the cart (fourth step)
		driver.findElement(By.cssSelector(".shopping_cart_link")).click(); 
		
		// verification added goods in the Cart (fourth step - expected result)
		String titleCartPage = driver.findElement(By.cssSelector("span[class='title']")).getText();
		String urlCartPage = driver.getCurrentUrl();
		Assert.assertEquals(titleCartPage, "YOUR CART");
		Assert.assertEquals(urlCartPage, "https://www.saucedemo.com/cart.html");
		Assert.assertTrue(checkItem(driver, goodsNeeded)); 
		
		// push the button checkout (fifth step)
		driver.findElement(By.xpath("//div[@class='cart_footer']//button[@id='checkout']")).click(); 
		
		// checking navigate to delivery page (fifth step - expected result)
		String titleYourInformationPage = driver.findElement(By.cssSelector("[class='title']")).getText();
		String urlYoutInformationPage = driver.getCurrentUrl();
		Assert.assertEquals(titleYourInformationPage, "CHECKOUT: YOUR INFORMATION");
		Assert.assertEquals(urlYoutInformationPage, "https://www.saucedemo.com/checkout-step-one.html");
		
		// input delivery data (sixth step)
		driver.findElement(By.cssSelector("#first-name")).sendKeys(firstName); 
		// input delivery data (seventh step)
		driver.findElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys(lastName); 
		// input delivery data (eighth step)
		driver.findElement(By.name("postalCode")).sendKeys(zipCode); 
		
		// make screenshot of check out info
		WebElement checkoutInfo = driver.findElement(By.cssSelector("[class='checkout_info']"));
		File scrcheckoutInfo = checkoutInfo.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(scrcheckoutInfo, new File("F:\\Selenium\\scrcheckOutInfo.png"));
		
		// push the button continue (ninth step)
		driver.findElement(By.cssSelector("#continue")).click();
		
		// checking opened page (ninth step - expected result)
		String titleOverViewPage = driver.findElement(By.cssSelector("[class='title']")).getText();
		String urlOverViewPage = driver.getCurrentUrl();
		Assert.assertEquals(titleOverViewPage, "CHECKOUT: OVERVIEW");
		Assert.assertEquals(urlOverViewPage, "https://www.saucedemo.com/checkout-step-two.html");
		
		double taxBill = countTaxBill(driver, goodsNeeded, tax);
		double sumBill = countSumBill(driver, goodsNeeded, taxBill);
		
		String taxGrabStr = driver.findElement(By.cssSelector(".summary_tax_label")).getText();
		String sumGrabStr = driver.findElement(By.cssSelector(".summary_total_label")).getText();
		taxGrabStr = taxGrabStr.replaceAll("\\$", "").split(" ")[1];
		sumGrabStr = sumGrabStr.replaceAll("\\$", "").split(" ")[1];
		double taxGrab = Double.valueOf(taxGrabStr);
		double sumGrab = Double.valueOf(sumGrabStr);
		Assert.assertEquals(taxGrab,taxBill);
		Assert.assertEquals(sumGrab, sumBill);
		
		String paymentInformation = driver.findElement(By.xpath("//div[@class='summary_value_label'][1]")).getText();
		String shippingInformation = driver.findElement(By.xpath("//div[@class='summary_value_label'][2]")).getText();
		Assert.assertEquals(paymentInformation, "SauceCard #31337");
		Assert.assertEquals(shippingInformation, "FREE PONY EXPRESS DELIVERY!");
		Assert.assertTrue(checkItem(driver, goodsNeeded));
		
		// push the button finish (tenth step)
		driver.findElement(By.id("finish")).click();
		
		// checking open page (tenth step - expected result)
		String titleCompletePage = driver.findElement(By.cssSelector("[class='title']")).getText();
		String urlCompletePage = driver.getCurrentUrl();
		Assert.assertEquals(titleCompletePage, "CHECKOUT: COMPLETE!");
		Assert.assertEquals(urlCompletePage, "https://www.saucedemo.com/checkout-complete.html");
		
		String completeHeader = driver.findElement(By.cssSelector("div h2")).getText();
		String completeText = driver.findElement(By.cssSelector("div div[class='complete-text']")).getText();
		Assert.assertEquals(completeHeader, "THANK YOU FOR YOUR ORDER");
		Assert.assertEquals(completeText, "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
		
		// screenshot pony's logo
		WebElement ponyIMG = driver.findElement(By.cssSelector("[class='pony_express']"));
		File scrPonyIMG = ponyIMG.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(scrPonyIMG, new File("F:\\Selenium\\scrPonyIMG.png"));
		
		// push the button back home (eleventh step)
		driver.findElement(By.cssSelector("#back-to-products")).click();
		
		// checking open home page after buying
		Assert.assertEquals(titlePage, "PRODUCTS");
		Assert.assertEquals(urlPage, "https://www.saucedemo.com/inventory.html");
		driver.findElement(By.xpath("//div[@class='inventory_item']")).isDisplayed(); 
		
		// close the browser
		driver.quit();
		
	}
	
	
	// adding goods to cart
	public static void addToCart (WebDriver driver, String[] goodsNeeded) {
		String goodsName;
		int addedToCart = 0;
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = driver.findElements(By.cssSelector("div[class='inventory_item_name']"));
		
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				driver.findElements(By.xpath("//div[@class='pricebar']/button")).get(i).click();
				addedToCart++;
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
	}
	
	// checking modify the button's name addToCart to remove
	public static boolean changeNameAddButtonToRemoveButton (WebDriver driver, String[] goodsNeeded) {
		String goodsName;
		String nameButton;
		int checkButton = 0;
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = driver.findElements(By.cssSelector("div[class='inventory_item_name']"));
		
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				nameButton = driver.findElements(By.xpath("//div[@class='pricebar']/button")).get(i).getText();
				Assert.assertEquals(nameButton, "REMOVE");
				checkButton++;
				if (checkButton==goodsNeeded.length) {
					return true;
				}
			}
		}
		return false;
	}
	
	// verification added goods in the Cart
	public static boolean checkItem (WebDriver driver, String[] goodsNeeded) {
		String goodsName;
		int itemToCartChecked = 0;
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goodsInCart = driver.findElements(By.cssSelector("div.inventory_item_name"));
		
		for (int i = 0; i < goodsInCart.size(); i++) {
			goodsName = goodsInCart.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				itemToCartChecked++;
				if (itemToCartChecked==goodsNeeded.length) {
				return true;
				}
			}
		}
		return false;
	}
	
	// count tax bill for choose goods
	public static double countTaxBill (WebDriver driver, String[] goodsNeeded, int tax) {
		String goodsName;
		String stringPrice;
		double price;
		double taxBill = 0;
		int addedToCart = 0;
		MathContext context = new MathContext(3, RoundingMode.HALF_EVEN);
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = driver.findElements(By.cssSelector("div.inventory_item_name"));
		
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				stringPrice = driver.findElements(By.cssSelector(".inventory_item_price")).get(i).getText();
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
	public static double countSumBill (WebDriver driver, String[] goodsNeeded, double taxBill ) {
		String goodsName;
		String stringPrice;
		double price;
		int addedToCart = 0;
		double bill = 0;
		List<String> goodsNeededList = Arrays.asList(goodsNeeded);
		List<WebElement> goods = driver.findElements(By.cssSelector("div.inventory_item_name"));
		
		for (int i = 0; i < goods.size(); i++) {
			goodsName = goods.get(i).getText();
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				stringPrice = driver.findElements(By.cssSelector(".inventory_item_price")).get(i).getText();
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

}



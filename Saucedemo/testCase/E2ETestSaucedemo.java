package testCase;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class E2ETestSaucedemo {

	
	public static void main(String[] args) {
		String userName = "standard_user";
		String password = "secret_sauce";
		String firstName = "Anton";
		String lastName = "Smit";
		String zipCode = "445511";
		String[] goodsNeeded = {"Sauce Labs Bolt T-Shirt", "Test.allTheThings() T-Shirt (Red)"};
		int tax = 8;
		
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // waiting until appearing web elements 
		
		driver.manage().window().maximize(); // full screen
		
		driver.get("https://www.saucedemo.com/"); // navigate to the site
		
		driver.findElement(By.id("user-name")).sendKeys(userName); // input credentials
		driver.findElement(By.name("password")).sendKeys(password);
		
		driver.findElement(By.cssSelector("input.submit-button.btn_action")).click(); // push the login button
		
		driver.findElement(By.xpath("//div[@class='inventory_item']")).isDisplayed(); // check goods are displayed
		
		addToCart(driver, goodsNeeded); // add goods to the cart
		
		Assert.assertEquals(driver.findElement(By.cssSelector(".shopping_cart_badge")).getText(), "2"); // checking number of goods added to cart
		
		driver.findElement(By.cssSelector(".shopping_cart_link")).click(); // navigate to the cart
		
		checkItemToCart(driver, goodsNeeded); // verification added goods in the Cart
		
		driver.findElement(By.xpath("//div[@class='cart_footer']//button[@id='checkout']")).click(); // push the button checkout
		
		driver.findElement(By.cssSelector("#first-name")).sendKeys(firstName); // input the first name
		driver.findElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys(lastName); // input the last name
		driver.findElement(By.name("postalCode")).sendKeys(zipCode); // input the zip code
		
		driver.findElement(By.cssSelector("#continue")).click();
		
		double taxBill = countTaxBill(driver, goodsNeeded, tax);
		double sumBill = countSumBill(driver, goodsNeeded, taxBill);
		
		System.out.println(driver.findElement(By.cssSelector(".summary_tax_label")).getText());
		System.out.println(driver.findElement(By.cssSelector(".summary_total_label")).getText());
//		Assert.assertEquals(driver.findElement(By.cssSelector(".summary_tax_label")).getText(),taxBill);
//		Assert.assertEquals(driver.findElement(By.cssSelector(".summary_total_label")).getText(), sumBill);
	}
	
	
	// adding goods to cart
	public static void addToCart (WebDriver driver, String[] goodsNeeded) {
		int addedToCart = 0;
		List <WebElement> goods = driver.findElements(By.cssSelector("div[class='inventory_item_name']"));
		for (int i = 0; i < goods.size(); i++) {
			String goodsName = goods.get(i).getText();
			@SuppressWarnings("rawtypes")
			List<String> goodsNeededList = Arrays.asList(goodsNeeded);
			if (goodsNeededList.contains(goodsName)) {
			addedToCart++;
			driver.findElements(By.xpath("//div[@class='pricebar']/button")).get(i).click();
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
	}
	
	// verification added goods in the Cart
	public static void checkItemToCart (WebDriver driver, String[] goodsNeeded) {
		int itemToCart = 0;
		List <WebElement> goodsInCart = driver.findElements(By.cssSelector("div.inventory_item_name"));
		for (int i = 0; i < goodsInCart.size(); i++) {
			String goodsName = goodsInCart.get(i).getText();
			@SuppressWarnings("rawtypes")
			List<String> goodsNeededList = Arrays.asList(goodsNeeded);
			if (goodsNeededList.contains(goodsName)) {
				itemToCart++;
				if (itemToCart==goodsNeeded.length) {
					break;
				}
			}
		}
	}
	
	public static double countTaxBill (WebDriver driver, String[] goodsNeeded, int tax) {
		int addedToCart = 0;
		double taxBill = 0;
		List <WebElement> goods = driver.findElements(By.cssSelector("div.inventory_item_name"));
		for (int i = 0; i < goods.size(); i++) {
			String goodsName = goods.get(i).getText();
			@SuppressWarnings("rawtypes")
			List<String> goodsNeededList = Arrays.asList(goodsNeeded);
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				String stringBill = driver.findElements(By.cssSelector(".inventory_item_price")).get(i).getText();
				double price = Double.valueOf(stringBill);
				taxBill = taxBill + (price * tax / 100);
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
		return taxBill;
	}
	
	public static double countSumBill (WebDriver driver, String[] goodsNeeded, double taxBill ) {
		int addedToCart = 0;
		double sumBill = 0;
		double bill = 0;
		List <WebElement> goods = driver.findElements(By.cssSelector("div.inventory_item_name"));
		for (int i = 0; i < goods.size(); i++) {
			String goodsName = goods.get(i).getText();
			@SuppressWarnings("rawtypes")
			List<String> goodsNeededList = Arrays.asList(goodsNeeded);
			if (goodsNeededList.contains(goodsName)) {
				addedToCart++;
				String stringBill = driver.findElements(By.cssSelector(".inventory_item_price")).get(i).getText();
				double price = Double.valueOf(stringBill);
				bill += price ;
				if (addedToCart==goodsNeeded.length) {
					break;
				}
			}
		}
		
		return sumBill = bill + taxBill;
	}
}

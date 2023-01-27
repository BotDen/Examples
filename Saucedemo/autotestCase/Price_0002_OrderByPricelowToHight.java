package testCase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Price_0002_OrderByPricelowToHight {

	public static void main(String[] args) {
		
		String urlSite = "https://www.saucedemo.com/";
		String userName = "standard_user";
		String password = "secret_sauce";
		String[] orderOptions = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};
		
		System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		// manage the browser
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		// go to the application
		driver.get(urlSite);
		
		// input credentials (precondition)
		driver.findElement(By.cssSelector("#user-name")).sendKeys(userName);
		driver.findElement(By.cssSelector("#password")).sendKeys(password);
		
		// push the login button (precondition)
		driver.findElement(By.cssSelector(".submit-button.btn_action")).click();
		
		// checking results perform precondition
		String titlePage = driver.findElement(By.cssSelector("span[class='title']")).getText();
		String urlPage = driver.getCurrentUrl();
		Assert.assertEquals(titlePage, "PRODUCTS");
		Assert.assertEquals(urlPage, "https://www.saucedemo.com/inventory.html");
		driver.findElement(By.xpath("//div[@class='inventory_item']")).isDisplayed();
		
		
		// create Select element
		WebElement staticDropDownList = driver.findElement(By.cssSelector("[class='product_sort_container']"));
		Select dropDownList = new Select(staticDropDownList);
		
		// check default sort (precondition)
		// checking default text from filter
		String defaultOrder = dropDownList.getFirstSelectedOption().getText();
		Assert.assertEquals(defaultOrder, "Name (A to Z)");
		// checking default goods sort
		List<WebElement> goodsName = driver.findElements(By.cssSelector("[class='inventory_item_name']"));
		List<String> strGoodsNameOrigen = goodsName.stream().map(s->s.getText()).collect(Collectors.toList());
		List<String> strGoodsNameSorted = strGoodsNameOrigen.stream().sorted().collect(Collectors.toList());
		Assert.assertTrue(strGoodsNameOrigen.equals(strGoodsNameSorted));
		
		// click the filter field
		staticDropDownList.click();
		
		// checking available sorting
		List<WebElement> options = dropDownList.getOptions();
		Assert.assertTrue(checkOrderOptions(options, orderOptions));
		
		//choose "Price (low to high)" sorting 
		dropDownList.selectByVisibleText("Price (low to high)");
		
		// checking choice "Price (low to high)" sorting
		// create new Select element
		WebElement staticDropDownList2 = driver.findElement(By.cssSelector("[class='product_sort_container']"));
		Select dropDownList2 = new Select(staticDropDownList2);
		// checking text from filter field after choice "Price (low to high)" sorting
		String lowHightOrder = dropDownList2.getFirstSelectedOption().getText();
		Assert.assertEquals(lowHightOrder, "Price (low to high)");
		
		// checking price sorting from low to high
		// create web element with goods' price
		List<WebElement> goodsPrice = driver.findElements(By.cssSelector("[class='inventory_item_price']"));
		// get goods' price (string)
		List<String> origenList = goodsPrice.stream().map(s -> s.getText()).collect(Collectors.toList());
		// trim the "$" form price
		List<String> replaceList = origenList.stream().map(s->s.replaceAll("\\$", "")).collect(Collectors.toList());
		// modify type string to type double (price)
		List<double[]> origenPrice = DoubleValueOf(replaceList);
		// sort price of goods in java streams 
		List<double[]> sortPrice = origenPrice.stream().sorted().collect(Collectors.toList());
		// match check
		Assert.assertTrue(origenPrice.equals(sortPrice));
		
		//close the browser
		driver.quit();
	}
	
	private static boolean checkOrderOptions (List<WebElement> options, String[] orderOptions) {
		int count = 0;
		String grabOptionString;
		List<String> orderOptionsArray;
		for	(WebElement option : options) {
			grabOptionString = option.getText();
			orderOptionsArray = Arrays.asList(orderOptions);
			if (orderOptionsArray.contains(grabOptionString)) {
				count++;
				if(count == options.size()) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}
	
	private static List<double[]> DoubleValueOf (List<String> list) {
		double[] price = new double[list.size()];
		for	(int i = 0; i < list.size(); i++) {
			price[i] = Double.valueOf(list.get(i));
		}
		List<double[]> priceList = Arrays.asList(price);
		return priceList;
	}
	
}

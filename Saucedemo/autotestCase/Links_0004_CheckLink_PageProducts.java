package testCase;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Links_0004_CheckLink_PageProducts {

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		String urlSite = "https://www.saucedemo.com/";
		String userName = "standard_user";
		String password = "secret_sauce";
		SoftAssert linkCode = new SoftAssert();
		
		System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
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
		
		//create web element with goods' links 
		List<WebElement> links = driver.findElements(By.cssSelector("div a[href*='http']"));
		for (WebElement link : links) {
			// get URL's link
			String url = link.getAttribute("href");
			// create connect to URL and get the response code  
			HttpURLConnection connect = (HttpURLConnection) new URL(url).openConnection();
			connect.setRequestMethod("HEAD");
			int responseCode = connect.getResponseCode();
			// checking responses code
			linkCode.assertTrue(responseCode < 400 || responseCode == 999, "Ссылка " + link.getText() + " сломанна. Код ответа " + responseCode);
		}
		linkCode.assertAll();
		
		// close the browser
		driver.quit();
		
	}

}

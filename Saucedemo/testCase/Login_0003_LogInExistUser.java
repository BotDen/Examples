package testCase;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

public class Login_0003_LogInExistUser {

	public static void main(String[] args) throws IOException {
		
		String urlSite = "https://www.saucedemo.com/";
		String userName = "standard_user";
		String password = "secret_sauce";

		System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		// maximize browser's window
		driver.manage().window().maximize();
		
		// go to the application
		driver.get(urlSite);

		// input credentials
		driver.findElement(By.cssSelector("#user-name")).sendKeys(userName);
		driver.findElement(By.cssSelector("#password")).sendKeys(password);

		// screenshot inputed credentials
		WebElement loginFields = driver.findElement(By.cssSelector("div[class='login_wrapper-inner']"));
		File scrLoginFields = loginFields.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(scrLoginFields, new File("F:\\Selenium\\scrLoginFields.png"));
		
		// push the login button
		driver.findElement(By.cssSelector(".submit-button.btn_action")).click();

		// check items is displayed 
		String titlePage = driver.findElement(By.cssSelector("span[class='title']")).getText();
		String urlPage = driver.getCurrentUrl();
		Assert.assertEquals(titlePage, "PRODUCTS");
		Assert.assertEquals(urlPage, "https://www.saucedemo.com/inventory.html");
		driver.findElement(By.xpath("//div[@class='inventory_item']")).isDisplayed();
		
		// close the browser
		driver.quit();
	}

}

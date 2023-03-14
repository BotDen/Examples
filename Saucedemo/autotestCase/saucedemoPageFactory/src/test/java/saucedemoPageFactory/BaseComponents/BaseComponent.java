package saucedemoPageFactory.BaseComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import denBulygin.saucedemoPageFactory.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseComponent {
	WebDriver driver;
	
	public LoginPage loginPage;
	
	public WebDriver initializationDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\saucedemoPageFactory\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			System.out.println("Указан не актульный браузер");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		return driver;
	}
	
	@BeforeMethod (alwaysRun = true)
	public LoginPage launchApp() throws IOException {
		driver = initializationDriver();
		loginPage = new LoginPage(driver);
		loginPage.goToSite();
		return loginPage;
	}
	
	@AfterMethod (alwaysRun = true)
	public void closeBrowser() {
		driver.close();
	}
}

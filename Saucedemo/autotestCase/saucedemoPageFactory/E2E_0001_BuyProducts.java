package denBulygin.saucedemoPageFactory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class E2E_0001_BuyProducts {

	public static void main(String[] args) {
		String url = "https://www.saucedemo.com/";
		String userName = "standard_user";
		String userPassword = "secret_sauce";
		String firstName = "Дмитрий";
		String lastName = "Петров";
		String zipCode = "445056";
		String[] goods = {"Sauce Labs Bolt T-Shirt", "Test.allTheThings() T-Shirt (Red)"};
		String confirmationHeader = "THANK YOU FOR YOUR ORDER";
		String confirmationMessage = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
		int tax = 8;
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get(url);
		
		LoginPage loginPage = new LoginPage(driver);
		CatalogPage catalogPage = loginPage.loginAccount(userName, userPassword);
		catalogPage.addToCart(driver, goods);
		Boolean matchButtonName = catalogPage.verifyChangeingNameAddButton(driver, goods);
		Assert.assertTrue(matchButtonName);
		YourCartPage yourCartPage = catalogPage.goToCart();
		Boolean matchProdInCart = yourCartPage.checkItem(driver, goods);
		Assert.assertTrue(matchProdInCart);
		YourInformationPage yourInformationPage = yourCartPage.goToYourInformation();
		OverviewPage overviewPage = yourInformationPage.inputYourInformation(firstName, lastName, zipCode);
		double countBill = overviewPage.countSumBill(driver, goods, tax);
		double countTax = overviewPage.countTaxBill(driver, goods, tax);
		double grabBill = overviewPage.grabBillStr();
		double grabTax = overviewPage.grabTaxStr();
		Assert.assertEquals(countBill, grabBill);
		Assert.assertEquals(countTax, grabTax);
		CompletePage completePage = overviewPage.goToCompletePage();
		String grabConfirmHeader = completePage.getConfirmHeader();
		String grabConfirmMessage = completePage.getConfirmMessage();
		Assert.assertEquals(grabConfirmHeader, confirmationHeader);
		Assert.assertEquals(grabConfirmMessage, confirmationMessage);
		
		driver.quit();
	}

}

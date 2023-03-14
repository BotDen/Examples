package denBulygin.saucedemoPageFactory;



import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import saucedemoPageFactory.BaseComponents.BaseComponent;

public class E2E_0001_BuyProducts extends BaseComponent {
	WebDriver driver;

	@DataProvider
	public Object[][] getData() {
		String userName = "standard_user";
		String userPassword = "secret_sauce";
		String[] goods = {"Sauce Labs Bolt T-Shirt", "Test.allTheThings() T-Shirt (Red)"};
		String[] goods1 = {"Sauce Labs Backpack"};
		return new Object[][] {{userName, userPassword, goods}, {userName, userPassword, goods1}};
	}
	
	@Test (dataProvider = "getData")
	public void e2e_0001_BuyProducts(String userName, String userPassword, String[] goods) {
		String firstName = "Дмитрий";
		String lastName = "Петров";
		String zipCode = "445056";
		String confirmationHeader = "Thank you for your order!";
		String confirmationMessage = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
		int tax = 8;
		
		loginPage.goToSite();
		CatalogPage catalogPage = loginPage.loginAccount(userName, userPassword);
		catalogPage.addToCart(goods);
		Boolean matchButtonName = catalogPage.verifyChangeingNameAddButton(goods);
		Assert.assertTrue(matchButtonName);
		YourCartPage yourCartPage = catalogPage.goToCart();
		Boolean matchProdInCart = yourCartPage.checkItem(goods);
		Assert.assertTrue(matchProdInCart);
		YourInformationPage yourInformationPage = yourCartPage.goToYourInformation();
		OverviewPage overviewPage = yourInformationPage.inputYourInformation(firstName, lastName, zipCode);
		double countTax = overviewPage.countTaxBill(goods, tax);
		double countBill = overviewPage.countSumBill(goods, countTax);
		double grabBill = overviewPage.grabBillStr();
		double grabTax = overviewPage.grabTaxStr();
		Assert.assertEquals(countBill, grabBill);
		Assert.assertEquals(countTax, grabTax);
		CompletePage completePage = overviewPage.goToCompletePage();
		String grabConfirmHeader = completePage.getConfirmHeader();
		String grabConfirmMessage = completePage.getConfirmMessage();
		Assert.assertEquals(grabConfirmHeader, confirmationHeader);
		Assert.assertEquals(grabConfirmMessage, confirmationMessage);
	}

}

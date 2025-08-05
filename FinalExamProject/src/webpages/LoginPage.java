package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	private static final String PAGE_URL = "http://testblog.kurs-qa.cubes.edu.rs/login";

	@FindBy(name = "email")
	private WebElement weEmail;
	@FindBy(name = "password")
	private WebElement wePassword;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement weButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get(PAGE_URL);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public void loginSucces() {
		inputStringInEmail("kursqa@cubes.edu.rs");
		inputStringInPassword("cubesqa");
		clickOnSignIn();
	}

	public void openPage() {

		driver.get(PAGE_URL);
	}

	public void inputStringInEmail(String email) {
		weEmail.clear();
		weEmail.sendKeys(email);
	}

	public void inputStringInPassword(String pass) {
		wePassword.clear();
		wePassword.sendKeys(pass);
	}

	public void clickOnSignIn() {
		weButton.click();
	}

}

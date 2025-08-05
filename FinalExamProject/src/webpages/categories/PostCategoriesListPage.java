package webpages.categories;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostCategoriesListPage {

	private WebDriver driver;
	private WebDriverWait driverWait;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories";

	@FindBy(xpath = "//i[@class='fas fa-plus-square']")
	private WebElement weAddNewCategory;
	@FindBy(xpath = "//button[text()='Delete']")
	private WebElement weDialogDelete;
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement weDialogCancel;

	public PostCategoriesListPage(WebDriver driver, WebDriverWait driverWait) {
		super();
		this.driver = driver;
		this.driverWait = driverWait;
		this.driver.get(PAGE_URL);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public void openPage() {
		this.driver.get(PAGE_URL);
	}

	public void closePage() {
		driver.close();
	}

	public void clickOnDeleteCategory(String name) {

		WebElement weDeleteButton = driver
				.findElement(By.xpath("//strong[text()='" + name + "']//ancestor::tr//td[6]//button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weDeleteButton);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		weDeleteButton.click();

	}

	public void clickOnDialogDelete() {

		driverWait.until(ExpectedConditions.visibilityOf(weDialogDelete));
		weDialogDelete.click();
	}
}

package webpages.posts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostFormPage {

	private WebDriver driver;
	private WebDriverWait driverWait;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add";

	@FindBy(xpath = "//select[@name='post_category_id']")
	private WebElement weChooseCategory;
	@FindBy(xpath = "//select//child::option[1]")
	private WebElement weWithoutCategory;
	@FindBy(xpath = "//select//child::option[2]")
	private WebElement weDefaultCategory;
	@FindBy(xpath = "//select//child::option[last()]")
	private WebElement weDeletedCategory;
	@FindBy(xpath = "//input[@name='title']")
	private WebElement weInputTitle;
	@FindBy(xpath = "//textarea[@name='description']")
	private WebElement weInputDescription;
	@FindBy(xpath = "//label[@for='set-as-unimportant']")
	private WebElement weNotImportantButton;
	@FindBy(xpath = "//label[@for='set-as-important']")
	private WebElement weImportantButton;
	@FindBy(xpath = "//label[text()='Default TAG NE BRISATI']")
	private WebElement weTag1Selection;
	@FindBy(xpath = "//label[text()='Default Post Category NE BRISATI']")
	private WebElement weTag2Selection;
	@FindBy(xpath = "//label[text()='Tag Name 3']")
	private WebElement weTag3Selection;
	@FindBy(xpath = "//input[@name='photo']")
	private WebElement weImportPhotoButton;
	@FindBy(xpath = "//button[@data-action='delete-photo']")
	private WebElement weDeletePhotoButton;
	@FindBy(xpath = "//div[@id='cke_content']")
	private WebElement weContentEditor;
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement weButtonSave;
	@FindBy(xpath = "//a[text()='Cancel']")
	private WebElement weButtonCancel;
	@FindBy(xpath = "//span[@id='title-error']")
	private WebElement weTitleError;
	@FindBy(xpath = "//span[@id='description-error']")
	private WebElement weDecriptionError;
	@FindBy(xpath = "//span[@id='tag_id[]-error']")
	private WebElement weTagsErrorMessage;
	@FindBy(xpath = "//div[@class='invalid-feedback']")
	private WebElement weContentDivError;
	@FindBy(xpath = "//div[.='The selected post category id is invalid.']")
	private WebElement weCategoryError;

	public PostFormPage(WebDriver driver, WebDriverWait driverWait) {
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

	public void clickOnSave() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weButtonSave);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		weButtonSave.click();
	}

	public void clickOnCancel() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weButtonCancel);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		weButtonCancel.click();
	}

	public void withoutCategory() {
		weChooseCategory.click();
		driverWait.until(ExpectedConditions.visibilityOf(weWithoutCategory));
		weWithoutCategory.click();
	}

	public void chooseDefaultCategory() {

		weChooseCategory.click();
		driverWait.until(ExpectedConditions.visibilityOf(weDefaultCategory));
		weDefaultCategory.click();
	}

	public void chooseDeletedCategory() {

		weChooseCategory.click();
		driverWait.until(ExpectedConditions.visibilityOf(weDeletedCategory));

		weDeletedCategory.click();
	}

	public void inputStringInTitle(String title) {
		weInputTitle.clear();
		weInputTitle.sendKeys(title);

	}

	public void inputStringInDescription(String description) {
		weInputDescription.clear();
		weInputDescription.sendKeys(description);

	}

	public void choosePostAsImportant() {
		weImportantButton.click();
	}

	public void choosePostAsUnimportant() {
		weNotImportantButton.click();
	}

	public void editImportanceFromFormPage() {
		if (driver.findElement(By.xpath("//input[@id='set-as-important']")).isSelected()) {
			driverWait.until(ExpectedConditions.elementToBeClickable(weNotImportantButton));
			weNotImportantButton.click();
		} else if (driver.findElement(By.xpath("//input[@id='set-as-unimportant']")).isSelected()) {
			driverWait.until(ExpectedConditions.elementToBeClickable(weImportantButton));
			weImportantButton.click();

		}
	}

	public void chooseTag1() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weTag1Selection);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		weTag1Selection.click();
	}

	public void chooseTag2() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weTag2Selection);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		weTag2Selection.click();
	}

	public void chooseTag3() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weTag3Selection);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		weTag3Selection.click();
	}

	public List<WebElement> weTagsCheckboxes() {
		return driver.findElements(By.xpath("//input[@name='tag_id[]']"));
	}

	public void uncheckAllTags(List<WebElement> weTags) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()",
				driver.findElement(By.xpath("//label[text()='Tags']//parent::div[1]")));
		for (WebElement tag : weTags) {
			if (tag.isSelected()) {
				driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='tag_id[]']")));
				tag.click();
			}
		}
	}

	public void checkAllTags(List<WebElement> weTags) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.xpath("//label[text()=\"Tags\"]")));
		for (WebElement tag : weTags) {
			if (!tag.isSelected()) {
				driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='tag_id[]']")));
				tag.click();
			}
		}
	}

	public void importPhoto(String imagePath) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weImportPhotoButton);
		driverWait.until(ExpectedConditions.elementToBeClickable(weImportPhotoButton));
		weImportPhotoButton.sendKeys(imagePath);
	}

	public void deletePhoto() {
		weDeletePhotoButton.click();
	}

	public void inputStringInContentTextArea(String content) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", weContentEditor);
		driverWait.until(ExpectedConditions.elementToBeClickable(weContentEditor));
		WebElement iframe = driver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame"));
		driver.switchTo().frame(iframe);

		WebElement weContentBody = driver.findElement(By.tagName("body"));
		weContentBody.clear();
		weContentBody.sendKeys(content);
		driver.switchTo().defaultContent();

	}

	public String getTitleError() {
		return weTitleError.getText();
	}

	public String getDescriptionError() {

		return weDecriptionError.getText();
	}

	public String getTagErrorMessage() {
		return weTagsErrorMessage.getText();
	}

	public String getContentError() {
		return weContentDivError.getText();
	}
	public String getCategoryError() {
		return weCategoryError.getText();
	}

	public String getTitleInputErrorMessage() {
		return weTitleError.getText();
	}

	public String getDescriptionInputErrorMessage() {
		return weDecriptionError.getText();
	}

}

package webpages.posts;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostListPage {

	private WebDriver driver;
	private WebDriverWait driverWait;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";

	@FindBy(xpath = "//i[@class='fas fa-plus-square']")
	private WebElement weAddNewPost;
	@FindBy(xpath = "//input[@class='form-control form-control-sm']")
	private WebElement weSearchPosts;
	@FindBy(xpath = "//div[@class='dataTables_info']")
	private WebElement weNumberOfPosts;
	@FindBy(css = "[name='entities-list-table_length']")
	private WebElement weSelectNumberOfPosts;
	@FindBy(xpath = " //input[@name='title']")
	private WebElement weTitleSearch;
	@FindBy(css = "[title='--Choose Author --']")
	private WebElement weAuthorSearch;
	@FindBy(xpath = "//span[@class='select2-search select2-search--dropdown']/input[@class='select2-search__field']")
	private WebElement weAuthorInput;
	@FindBy(css = "[title='--Choose Category --']")
	private WebElement weCategorySearch;
	@FindBy(xpath = "//span[@class='select2-search select2-search--dropdown']/input[@class='select2-search__field']")
	private WebElement weCategoryInput;
	@FindBy(css = "[name='important']")
	private WebElement weImportantSearch;
	@FindBy(css = "[name='status']")
	private WebElement weStatusSearch;
	@FindBy(css = "[name='tag_ids']")
	private WebElement weTagSearch;
	@FindBy(xpath = "//td[12]//child::div[1]//child::a[1]")
	private WebElement weViewPostInNewTab;
	@FindBy(xpath = "//tr[@role='row']//child::th[1]")
	private WebElement weSortPosts;
	@FindBy(xpath = "//tbody//child::td[4]//span[text()]")
	private WebElement wePostStatus;
	@FindBy(xpath = "//div[2]/button[1]")
	private WebElement weStatusDialog;
	@FindBy(xpath = "//button[contains(.,'Disable')]")
	private WebElement weDisableButton;
	@FindBy(xpath = "//button[contains(.,'Enable')]")
	private WebElement weEnableButton;
	@FindBy(xpath = "//tbody//child::td[3]//span[text()]")
	private WebElement wePostImportance;
	@FindBy(xpath = "//tbody[1]/tr[1]//button[2]")
	private WebElement weImportanceDialog;
	@FindBy(xpath = "//button[contains(.,'Set as Important')]")
	private WebElement weImportantButton;
	@FindBy(xpath = "//button[contains(.,'Set as Unimportant')]")
	private WebElement weUnimportantButton;
	@FindBy(xpath = "//div[@class=('toast-message')]")
	private WebElement weToastMessage;
	@FindBy(xpath = "//td//child::div[1]//child::a[2]")
	private WebElement weEditEnabledPost;
	@FindBy(xpath = "//td//child::div[1]//child::a[1]")
	private WebElement weEditDisabledPost;
	@FindBy(xpath = "//tr[1]//td[12]//div[1]//button[@data-action='delete']")
	private WebElement weDeleteDialog;
	@FindBy(xpath = "//button[text()='Delete']")
	private WebElement weDeletePost;

	public PostListPage(WebDriver driver, WebDriverWait driverWait) {
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

	public void openLinkParentInMenu(String title) {

		WebElement weMenu = driver.findElement(By.xpath("//p[text()='" + title + "']//ancestor::li[2]"));
		if (!weMenu.getAttribute("class").toString().equalsIgnoreCase("nav-item has-treeview menu-open")) {
			weMenu.click();
		}
	}

	public void clickOnLinkMenu(String title) {
		WebElement weLink = driver.findElement(By.xpath("//p[text()='" + title + "']"));
		driverWait.until(ExpectedConditions.visibilityOf(weLink));
		weLink.click();
	}

	public void clickOnNavigationLink(String title) {

		WebElement weLink = driver.findElement(By.xpath("//a[text()='" + title + "']"));
		weLink.click();
	}

	public void clickOnAddNewPost() {
		weAddNewPost.click();
	}

	public boolean isPostInTheList(String postTitle) {

		ArrayList<WebElement> wePosts = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[5][text()='" + postTitle + "']"));

		return !wePosts.isEmpty();
	}

	public void searchPostsByName(String postTitle) {

		weSearchPosts.clear();
		weSearchPosts.sendKeys(postTitle);
		driverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tr[@role='row']//child::td[5][text()='" + postTitle + "']")));
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getNumberOfPost() {
		return weNumberOfPosts.getText();
	}

	public String getNumberOfPostShowing() {
		return weSelectNumberOfPosts.getAttribute("value");

	}

	public void searchPostListByTitle(String title) {
		weTitleSearch.clear();
		weTitleSearch.sendKeys(title);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void showAllPosts() {

		Select show100Posts = new Select(weSelectNumberOfPosts);
		show100Posts.selectByValue("100");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void show50Posts() {

		Select show50Posts = new Select(weSelectNumberOfPosts);
		show50Posts.selectByValue("50");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void show25Posts() {

		Select show25Posts = new Select(weSelectNumberOfPosts);
		show25Posts.selectByValue("25");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSearchResultsByTitle() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.cssSelector("td:nth-of-type(5)"));

		for (WebElement element : weResults) {
			results.add(element.getText());
		}

		return results;

	}

	public void searchPostListByAuthor(String author) {
		weAuthorSearch.click();

		driverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='select2-results__options']")));

		weAuthorInput.click();
		weAuthorInput.sendKeys(author);
		weAuthorInput.sendKeys(Keys.ENTER);

	}

	public ArrayList<String> getSearchResultsByAuthor() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[6]"));

		for (WebElement element : weResults) {
			results.add(element.getText());
		}
		return results;

	}

	public void searchPostByCategory(String category) {
		weCategorySearch.click();
		driverWait.until(ExpectedConditions.elementToBeClickable(weCategoryInput));
		weCategoryInput.sendKeys(category);
		weCategoryInput.sendKeys(Keys.ENTER);
	}

	public ArrayList<String> getSearchResultsByCategory() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[7]"));

		for (WebElement element : weResults) {
			results.add(element.getText());
		}
		return results;
	}

	public void searchByImportance(String importance) {
		weImportantSearch.click();
		Select chooseImportance = new Select(weImportantSearch);
		chooseImportance.selectByValue(importance);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSearchResultsByImportance() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[3]"));
		for (WebElement element : weResults) {
			results.add(element.getText());
		}
		return results;
	}

	public void searchByStatus(String status) {
		weStatusSearch.click();
		Select chooseImportance = new Select(weStatusSearch);

		chooseImportance.selectByValue(status);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSearchResultsByStatus() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[4]"));
		for (WebElement element : weResults) {
			results.add(element.getText());
		}
		return results;
	}

	public void searchPostListByTag(String... tags) {

		Select tagOptions = new Select(weTagSearch);
		for (String tag : tags) {
			tagOptions.selectByVisibleText(tag);
		}
	}

	public void searchPostListByRemovingTag(String... tags) {

		Select tagOptions = new Select(weTagSearch);
		for (String tag : tags) {
			tagOptions.deselectByVisibleText(tag);
		}

	}

	public ArrayList<String> getSearchResultsByTag() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<WebElement> weResults = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//tr[@role='row']//child::td[8]"));
		for (WebElement element : weResults) {
			results.add(element.getText());
		}
		return results;
	}

	public void openLastAddedPostInNewTab() {

		weSortPosts.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String postStatus = wePostStatus.getText();
		if (postStatus.equals("enabled")) {
			weViewPostInNewTab.click();
		} else {
			weStatusDialog.click();
			driverWait.until(ExpectedConditions.visibilityOf(weEnableButton));
			weEnableButton.click();
			driverWait.until(ExpectedConditions.visibilityOf(weToastMessage));
			weToastMessage.click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			weViewPostInNewTab.click();
		}
	}

	public void changeStatusFromAllPostList() {

		weSortPosts.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String postStatus = wePostStatus.getText();
		weStatusDialog.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (postStatus.equals("enabled")) {

			weDisableButton.click();
		} else {
			weEnableButton.click();
		}
	}

	public String getPostStatus() {
		return wePostStatus.getText();
	}

	public String getPostImportance() {
		return wePostImportance.getText();
	}

	public String getToastMessage() {
		driverWait.until(ExpectedConditions.visibilityOf(weToastMessage));
		return weToastMessage.getText();
	}

	public void changeImportanceFromAllPostList() {
		weSortPosts.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String postImportance = wePostImportance.getText();
		weImportanceDialog.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (postImportance.equals("No")) {
			weImportantButton.click();
		} else {
			weUnimportantButton.click();
		}

	}

	public void updatePost() {
		weSortPosts.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String postStatus = wePostStatus.getText();
		if (postStatus.equals("enabled")) {
			weEditEnabledPost.click();
		} else {
			weEditDisabledPost.click();
		}

	}

	public void deleteLastAddedPost() {
		weSortPosts.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Skrol desno pre klika za brisanje."); //checking 
		weDeleteDialog.click();
		System.out.println("Dijalog za brisanje click"); //checking 
		driverWait.until(ExpectedConditions.visibilityOf(weDeletePost));
		weDeletePost.click();
		System.out.println("Post obrisan."); //checking 
	}

	public void deletePost(String postName) {
		weSortPosts.click();
		System.out.println("Obrnut prikaz postova");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Select show100Posts = new Select(weSelectNumberOfPosts);
		show100Posts.selectByValue("100");
		System.out.println("Prikaz 100 postova"); //checking 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement scrollRight = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[12]"));
		System.out.println("Skrol desno"); //checking 
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", scrollRight);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement weDeletePostButton = driver.findElement(
				By.xpath("//td[5][text()='" + postName + "']/following-sibling::td[7]//child::div//child::button[1]"));
		System.out.println("Delete dijalog je lociran"); //checking 
		weDeletePostButton.click();
		System.out.println("Delete dijalog kliknut "); //checking 
		driverWait.until(ExpectedConditions.visibilityOf(weDeletePost));
		System.out.println("Cekamo dijalog za brisanje"); //checking 
		weDeletePost.click();
		System.out.println("Post obrisan"); //checking 
	}
}
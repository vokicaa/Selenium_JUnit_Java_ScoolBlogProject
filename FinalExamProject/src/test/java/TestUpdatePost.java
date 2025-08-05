package test.java;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.ImagePaths;
import webpages.LoginPage;
import webpages.categories.PostCategoriesListPage;
import webpages.posts.PostFormPage;
import webpages.posts.PostListPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUpdatePost {

	private static WebDriver driver;
	private static LoginPage loginPage;
	private static PostCategoriesListPage categoryListPage;
	private static PostListPage postListPage;
	private static PostFormPage postFormPage;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"/Users/mymac/Desktop/Voki_workplace/chromedriver-mac-x64/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMillis(10000));

		loginPage = new LoginPage(driver);
		postListPage = new PostListPage(driver, driverWait);
		postFormPage = new PostFormPage(driver, driverWait);
		categoryListPage = new PostCategoriesListPage(driver, driverWait);
		loginPage.loginSucces();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		driver.close();
	}

	@Before
	public void setUp() throws Exception {
		postListPage.openPage();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tc01TestOpenExistingPostInNewTab() {

		postListPage.openLastAddedPostInNewTab();

		driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));
		assertEquals("https://testblog.kurs-qa.cubes.edu.rs/posts/single-post/11252/new-title-not-important",
				driver.getCurrentUrl());
		driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));

	}

	@Test
	public void tc02TestEditPostStatus() {

		postListPage.changeStatusFromAllPostList();

		if (postListPage.getPostStatus().equals("enabled")) {
			assertEquals("Post has been disabled", postListPage.getToastMessage());
		} else {
			assertEquals("Post has been enabled", postListPage.getToastMessage());
		}

	}

	@Test
	public void tc03TestEditPostImportance() {

		postListPage.changeImportanceFromAllPostList();

		if (postListPage.getPostImportance().equals("No")) {
			assertEquals("Post has been set as important", postListPage.getToastMessage());
		} else {
			assertEquals("Post has been set as unimportant", postListPage.getToastMessage());
		}
	}

	@Test
	public void tc04TestCancelButtonOnUpdatePost() {

		postListPage.updatePost();
		postFormPage.clickOnCancel();

		assertEquals("https://testblog.kurs-qa.cubes.edu.rs/admin/posts", driver.getCurrentUrl());

	}

	@Test
	public void tc05TestEditPostWithDeletedCAtegory() {

		postListPage.updatePost();

		driver.switchTo().newWindow(WindowType.TAB);	
		categoryListPage.openPage();
		categoryListPage.clickOnDeleteCategory("Category for the purpose of testing");
		categoryListPage.clickOnDialogDelete();
		categoryListPage.closePage();

		driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));
		postFormPage.chooseDeletedCategory();
		postFormPage.clickOnSave();

		assertEquals("The selected post category id is invalid.", postFormPage.getCategoryError());

	}

	@Test
	public void tc06TestEditPostWithEmptyCategory() {

		postListPage.updatePost();
		postFormPage.withoutCategory();
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getContentError());

	}

	@Test
	public void tc07TestEditPostWithEmptyTitle() {
		postListPage.updatePost();
		postFormPage.inputStringInTitle("");
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getTitleError());
	}

	@Test
	public void tc08TestEditPostWithShortTitle() {
		postListPage.updatePost();
		postFormPage.inputStringInTitle("test");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 20 characters.", postFormPage.getTitleInputErrorMessage());
	}

	@Test
	public void tc09TestEditPostWithNewTitle() {
		postListPage.updatePost();
		postFormPage.inputStringInTitle("New New test Title 33");
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
		postListPage.searchPostsByName("New New test Title 33");
		assertEquals(true, postListPage.isPostInTheList("New New test Title 33"));

	}

	@Test
	public void tc10TestEditPostWithEmptyDescription() {
		postListPage.updatePost();
		postFormPage.inputStringInDescription("");
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getDescriptionError());
	}

	@Test
	public void tc11TestEditPostWithShortDescription() {
		postListPage.updatePost();
		postFormPage.inputStringInDescription("Description");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 50 characters.", postFormPage.getDescriptionInputErrorMessage());
	}

	@Test
	public void tc12TestEditPostWithNewDescription() {
		postListPage.updatePost();
		postFormPage.inputStringInDescription(
				"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription");
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	@Test
	public void tc13TestEditPostImportantOrNot() {
		postListPage.updatePost();
		postFormPage.editImportanceFromFormPage();
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	@Test
	public void tc14TestEditPostWithEmptyTags() {
		postListPage.updatePost();
		postFormPage.uncheckAllTags(postFormPage.weTagsCheckboxes());
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getTagErrorMessage());
	}

	@Test
	public void tc15TestEditPostWithAllTagsSelected() {
		postListPage.updatePost();
		postFormPage.checkAllTags(postFormPage.weTagsCheckboxes());
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	// @Test
	public void tc16TestEditPostWithNewPhoto() {
		postListPage.updatePost();
		postFormPage.importPhoto(ImagePaths.New);
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	@Test
	public void tc17TestEditPostWithDeletingPhoto() {
		postListPage.updatePost();
		postFormPage.deletePhoto();
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	@Test
	public void tc18TestEditPostWithEmptyContent() {
		postListPage.updatePost();
		postFormPage.inputStringInContentTextArea("");
		postFormPage.clickOnSave();

		assertEquals("The content field is required.", postFormPage.getContentError());
	}

	@Test
	public void tc19TestEditPostWithShortContent() {
		postListPage.updatePost();
		postFormPage.inputStringInContentTextArea("a");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 100 characters.", postFormPage.getDescriptionInputErrorMessage());

	}

	@Test
	public void tc20TestEditPostWithNewContent() {
		postListPage.updatePost();
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using.");
		postFormPage.clickOnSave();

		assertEquals("Post has been edited", postListPage.getToastMessage());
	}

	@Test
	public void tc21TestDeleteLastAddedPost() {
		postListPage.deleteLastAddedPost();

		assertEquals("Post has been deleted", postListPage.getToastMessage());
	}

	@Test
	public void tc22TestDeleteRandomPost() {
		postListPage.deletePost("New Title Important text");

		assertEquals("Post has been deleted", postListPage.getToastMessage());
	}

	@Test
	public void tc23TestDeleteRandomPost() {

		postListPage.deletePost("Title test short content");

		assertEquals("Post has been deleted", postListPage.getToastMessage());
	}

	@Test
	public void tc24TestDeleteRandomPost() {

		
		postListPage.searchPostListByTitle("Title test without Photo");	
		postListPage.deletePost("Title test without Photo");

		assertEquals("Post has been deleted", postListPage.getToastMessage());
	}

	@Test
	public void tc25TestDeleteRandomPost() {
		postListPage.deletePost("Title test without Category");

		assertEquals("Post has been deleted", postListPage.getToastMessage());
	}

}

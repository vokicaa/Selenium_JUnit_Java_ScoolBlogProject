package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.ImagePaths;
import webpages.LoginPage;
import webpages.posts.PostFormPage;
import webpages.posts.PostListPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAddPost {

	private static WebDriver driver;
	private static LoginPage loginPage;
	private static PostListPage postListPage;
	private static PostFormPage postFormPage;
	private static WebDriverWait driverWait;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.setProperty("webdriver.chrome.driver",
				"/Users/mymac/Desktop/Voki_workplace/chromedriver-mac-x64/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driverWait = new WebDriverWait(driver, Duration.ofMillis(10000));

		loginPage = new LoginPage(driver);
		postListPage = new PostListPage(driver, driverWait);
		postFormPage = new PostFormPage(driver, driverWait);
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
	public void tc01TestMenuLinks() {
		checkMenuLink("Sliders list", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders");
		checkMenuLink("Add Slider", "https://testblog.kurs-qa.cubes.edu.rs/admin/sliders/add");
		checkMenuLink("Post Categories list", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories");
		checkMenuLink("Add Post Category", "https://testblog.kurs-qa.cubes.edu.rs/admin/post-categories/add");
		checkMenuLink("Tags list", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags");
		checkMenuLink("Add Tag", "https://testblog.kurs-qa.cubes.edu.rs/admin/tags/add");
		checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		checkMenuLink("Comments List", "https://testblog.kurs-qa.cubes.edu.rs/admin/comments");
		checkMenuLink("Users List", "https://testblog.kurs-qa.cubes.edu.rs/admin/users");
		checkMenuLink("Add User", "https://testblog.kurs-qa.cubes.edu.rs/admin/users/add");
	}

	@Test
	public void tc02TestNavigationLink() {
		checkNavigationLink("Home", "https://testblog.kurs-qa.cubes.edu.rs/admin");
	}

	@Test
	public void tc03TestAddPostWithoutAnyInput() {
		postListPage.clickOnAddNewPost();

		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getTitleError());
		assertEquals("This field is required.", postFormPage.getDescriptionError());
		assertEquals("This field is required.", postFormPage.getTagErrorMessage());
		assertEquals("This field is required.", postFormPage.getContentError());

	}

	@Test
	public void tc04TestAddPostWithoutCategory() {

		postListPage.clickOnAddNewPost();
		postFormPage.inputStringInTitle("Title test without Category");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		postListPage.searchPostsByName("Title test without Category");

		assertEquals("Category field shoud be required", false,
				postListPage.isPostInTheList("Title test without Category"));

	}

	@Test
	public void tc05TestAddPostWithEmptyTitleField() {

		postListPage.clickOnAddNewPost();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getTitleError());
	}

	@Test
	public void tc06TestAddPostWithEmptyDescriptionField() {

		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test without Description");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getDescriptionError());

	}

	@Test
	public void tc07TestAddPostWithoutTags() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test without Tags");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("This field is required.", postFormPage.getTagErrorMessage());
	}

	@Test
	public void tc08TestAddPostWithEmptyContentField() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test without Description");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.clickOnSave();

		assertEquals("The content field is required.", postFormPage.getContentError());
	}

	@Test
	public void tc09TestAddPostWithoutPhoto() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test without Photo");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("New post has been added", postListPage.getToastMessage());
		postListPage.searchPostsByName("Title test without Photo");
		assertEquals(true, postListPage.isPostInTheList("Title test without Photo"));

	}

	@Test
	public void tc10TestCancelButtonForAddPost() {

		String numberOfPostsBefore = postListPage.getNumberOfPost();
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test Cancel button");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnCancel();

		String numberOfPostsAfter = postListPage.getNumberOfPost();

		assertEquals(numberOfPostsBefore, numberOfPostsAfter);

	}

	@Test
	public void tc11TestAddPostWithShortTitle() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Short title");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 20 characters.", postFormPage.getTitleInputErrorMessage());

	}

	@Test
	public void tc12TestAddPostWithShortDescription() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test short description");
		postFormPage.inputStringInDescription("short description test");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea(
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 50 characters.", postFormPage.getDescriptionInputErrorMessage());

	}

	@Test
	public void tc13TestAddPostWithShortContent() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("Title test short content");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.About);
		postFormPage.inputStringInContentTextArea("a");
		postFormPage.clickOnSave();

		assertEquals("Please enter at least 100 characters.", postFormPage.getDescriptionInputErrorMessage());

	}

	@Test
	public void tc14TestAddImportantPost() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("New Title Important text");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.choosePostAsImportant();
		postFormPage.chooseTag1();
		postFormPage.importPhoto(ImagePaths.Important);
		postFormPage.inputStringInContentTextArea(
				"Lorem ipsum dolor sit amet. Elit consectetur adipiscing elit. Maecenas vitae ornare urna. Suspendisse efficitur arcu ipsum, nec tempor nulla luctus in. Donec sed cursus odio.");
		postFormPage.clickOnSave();

		assertEquals("New post has been added", postListPage.getToastMessage());
		postListPage.searchPostsByName("New Title Important text");
		assertEquals(true, postListPage.isPostInTheList("New Title Important text"));

	}

	@Test
	public void tc15TestAddUnimportantPost() {
		postFormPage.openPage();
		postFormPage.chooseDefaultCategory();
		postFormPage.inputStringInTitle("New Title Not Important");
		postFormPage.inputStringInDescription("Lorem Ipsum is simply dummy text of the printing and typesetting");
		postFormPage.choosePostAsUnimportant();
		postFormPage.chooseTag2();
		postFormPage.chooseTag3();
		postFormPage.importPhoto(ImagePaths.Unimportant);
		postFormPage.inputStringInContentTextArea(
				"Lorem ipsum dolor sit amet. Elit consectetur adipiscing elit. Maecenas vitae ornare urna. Suspendisse efficitur arcu ipsum, nec tempor nulla luctus in. Donec sed cursus odio.");
		postFormPage.clickOnSave();

		assertEquals("New post has been added", postListPage.getToastMessage());
		postListPage.searchPostsByName("New Title Not Important");
		assertEquals(true, postListPage.isPostInTheList("New Title Not Important"));

	}

	@Test
	public void tc16TestSearchingPostByTitle() {
		postListPage.searchPostListByTitle("title");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByTitle();

		for (String result : searchResults) {
			assertTrue(result.toLowerCase().contains("title".toLowerCase()));
		}
	}

	@Test
	public void tc17TestSearchingPostByAuthor() {

		postListPage.searchPostListByAuthor("Polaznik Kursa");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByAuthor();

		for (String result : searchResults) {

			assertTrue(result.toLowerCase().contains("Polaznik Kursa".toLowerCase()));
		}

	}

	@Test
	public void tc18TestSearchingPostByCategory() {

		postListPage.searchPostByCategory("Default Post Category NE BRISATI");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByCategory();
		for (String results : searchResults) {
			assertTrue(results.toLowerCase().contains("Default Post Category NE BRISATI".toLowerCase()));
		}
	}

	@Test
	public void tc19TestSearchingPostByImportance() {
		// 0 = no Unimportant post
		// 1 = yes Important post
		postListPage.searchByImportance("1");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByImportance();
		for (String results : searchResults) {
			assertTrue(results.toLowerCase().contains("yes".toLowerCase()));
		}

	}

	@Test
	public void tc21TestSearchingPostByStatus() {
		// 0 = disabled post
		// 1 = Enabled post
		postListPage.searchByStatus("0");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByStatus();
		for (String results : searchResults) {
			assertTrue(results.toLowerCase().contains("disabled".toLowerCase()));
		}
	}

	@Test
	public void tc23TestSearchingPostByTags() {
		postListPage.searchPostListByTag("Default TAG NE BRISATI");
		postListPage.showAllPosts();

		ArrayList<String> searchResult = postListPage.getSearchResultsByTag();
		for (String result : searchResult) {
			assertTrue(result.toLowerCase().contains("Default TAG NE BRISATI".toLowerCase()));
		}
		postListPage.searchPostListByRemovingTag("Default TAG NE BRISATI");
		postListPage.searchPostListByTag("Tag Name 3", "Default Post Category NE BRISATI");
		postListPage.showAllPosts();

		ArrayList<String> searchResults = postListPage.getSearchResultsByTag();
		for (String results : searchResults) {
			assertTrue(results.toLowerCase().contains("Tag Name 3".toLowerCase())
					|| results.toLowerCase().contains("Default Post Category NE BRISATI".toLowerCase()));
		}

	}

	@Test
	public void tc25TestShowingMoreThan10Post() {
		postListPage.show50Posts();
		assertEquals(postListPage.getNumberOfPostShowing(), "50");

		postListPage.show25Posts();
		assertEquals(postListPage.getNumberOfPostShowing(), "25");

		postListPage.showAllPosts();
		assertEquals(postListPage.getNumberOfPostShowing(), "100");

	}

	public void checkMenuLink(String title, String url) {

		postListPage.openLinkParentInMenu(title);
		postListPage.clickOnLinkMenu(title);

		assertEquals("Bad url for " + title, url, driver.getCurrentUrl());

	}

	public void checkNavigationLink(String title, String url) {

		postListPage.clickOnNavigationLink(title);

		assertEquals("Bad url for " + title, url, driver.getCurrentUrl());

	}

}

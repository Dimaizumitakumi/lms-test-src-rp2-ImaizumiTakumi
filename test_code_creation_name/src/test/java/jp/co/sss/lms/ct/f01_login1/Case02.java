package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//アクセスおよびタイトル検証
		webDriver.get("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//スクリーンショット取得、保存処理
		TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
		File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDateTimeStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String fileName = "Case02_1_" + localDateTimeStr + ".png";

		try {
			Files.copy(file.toPath(), Paths.get("./evidence/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(fileName + "は保存できませんでした。");
		}
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		//ログインIDにStranger001入力
		WebElement idIdElement = webDriver.findElement(By.id("loginId"));
		idIdElement.clear();
		idIdElement.sendKeys("Stranger001");
		//パスワードにStranger001入力
		WebElement idPwElement = webDriver.findElement(By.id("password"));
		idPwElement.clear();
		idPwElement.sendKeys("Stranger001");
		//ログインボタン押下
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();
		//エラーメッセージ取得
		WebElement idErrorElement = webDriver.findElement(By.cssSelector(".help-inline.error"));
		//期待値通りか検証
		assertTrue(idErrorElement.isDisplayed());
		assertEquals("* ログインに失敗しました。", idErrorElement.getText());
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//スクリーンショット取得、保存処理
		TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
		File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDateTimeStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String fileName = "Case02_2_" + localDateTimeStr + ".png";

		try {
			Files.copy(file.toPath(), Paths.get("./evidence/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(fileName + "は保存できませんでした。");
		}
	}

}

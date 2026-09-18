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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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
		String fileName = "Case01_" + localDateTimeStr + ".png";

		try {
			Files.copy(file.toPath(), Paths.get("./evidence/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(fileName + "は保存できませんでした。");
		}
	}

}

package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		goTo("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ログインIDにStudentAA01入力
		WebElement idIdElement = webDriver.findElement(By.id("loginId"));
		idIdElement.clear();
		idIdElement.sendKeys("StudentAA01");

		//パスワードにStudentAA01Rename入力
		WebElement idPwElement = webDriver.findElement(By.id("password"));
		idPwElement.clear();
		idPwElement.sendKeys("StudentAA01Rename");

		//ログインボタン押下
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//期待値通りか検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//日別情報取得
		List<WebElement> dateElements = webDriver.findElements(By.tagName("tr"));

		//未提出の日の詳細ボタン押下
		for (WebElement dateElement : dateElements) {
			scrollBy("15");
			if (dateElement.getText().contains("未提出")) {
				WebElement cssBtnElement = dateElement.findElement(By.cssSelector(".btn.btn-default"));
				cssBtnElement.click();
				break;
			}
		}

		//期待値通りか検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//日報【デモ】を提出するボタン押下
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector("[class='btn btn-default']"));
		cssBtnElement.click();

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//報告内容にテストを入力
		WebElement idContentElement = webDriver.findElement(By.id("content_0"));
		idContentElement.clear();
		idContentElement.sendKeys("テスト");

		//提出ボタン押下
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//提出ボタン再取得
		cssBtnElement = webDriver.findElement(By.cssSelector("[class='btn btn-default']"));

		//期待値通りか検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("提出済み日報【デモ】を確認する", cssBtnElement.getAttribute("value"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

}

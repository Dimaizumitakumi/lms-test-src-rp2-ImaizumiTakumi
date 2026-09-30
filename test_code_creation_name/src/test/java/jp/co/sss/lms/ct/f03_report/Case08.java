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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//日別情報取得
		List<WebElement> dateElements = webDriver.findElements(By.tagName("tr"));

		//提出済みの日の詳細ボタン押下
		for (WebElement dateElement : dateElements) {
			scrollBy("15");
			if (dateElement.getText().contains("提出済み")) {
				WebElement cssBtnElement = dateElement.findElement(By.cssSelector(".btn.btn-default"));
				cssBtnElement.click();
				//詳細ボタン押下後ページに週報ボタンの存在チェック
				List<WebElement> btnElements = webDriver.findElements(By.xpath("//input[@value=\"提出済み週報【デモ】を確認する\"]"));
				if (btnElements.isEmpty()) {
					//存在しなければ戻るボタン押下
					WebElement cssBackBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
					cssBackBtnElement.click();
				} else {
					//存在すれば終了
					break;
				}
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
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		scrollBy("1000");
		//提出済み週報【デモ】を確認するボタン押下
		WebElement cssBtnElement = webDriver.findElement(By.xpath("//input[@value=\"提出済み週報【デモ】を確認する\"]"));
		cssBtnElement.click();

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//報告内容に修正後の週報のサンプルです。と入力
		WebElement nameKeyElement = webDriver.findElement(By.id("content_1"));
		nameKeyElement.clear();
		nameKeyElement.sendKeys("修正後の週報のサンプルです。");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//期待値通りか検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		//機能押下
		WebElement linkTextUserNameElement = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		linkTextUserNameElement.click();

		//期待値通りか検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		scrollBy("1000");
		//日別情報取得
		List<WebElement> dateElements = webDriver.findElements(By.tagName("tr"));
		//週報【デモ】の詳細ボタン押下
		for (WebElement dateElement : dateElements) {
			scrollBy("15");
			if (dateElement.getText().contains("週報【デモ】")) {
				WebElement cssBtnElement = dateElement.findElement(By.xpath(".//input[@value=\"詳細\"]"));
				cssBtnElement.click();
				break;
			}
		}

		//所感内容取得
		WebElement shokanElement = webDriver.findElement(By.xpath("//th[text()='所感']/following-sibling::td"));

		//期待値通りか検証
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());
		assertEquals("修正後の週報のサンプルです。", shokanElement.getText());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

}

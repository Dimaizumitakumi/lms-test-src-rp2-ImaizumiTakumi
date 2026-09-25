package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//機能押下
		WebElement linkTextTopElement = webDriver.findElement(By.linkText("機能"));
		linkTextTopElement.click();
		//ヘルプ押下
		WebElement linkTextElement = webDriver.findElement(By.linkText("ヘルプ"));
		linkTextElement.click();

		//期待値通りか検証
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//よくある質問リンク押下
		WebElement linkTextElement = webDriver.findElement(By.linkText("よくある質問"));
		linkTextElement.click();

		//タブ切り替え処理
		String originalWindow = webDriver.getWindowHandle();
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				// 新しいタブに切り替わったらループを抜ける
				break;
			}
		}

		//期待値通りか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//キーワードに研修と入力
		WebElement nameKeyElement = webDriver.findElement(By.name("keyword"));
		nameKeyElement.clear();
		nameKeyElement.sendKeys("研修");

		//検索ボタン押下
		WebElement xpathSearchBtnElement = webDriver.findElement(By.xpath("//input[@value='検索']"));
		xpathSearchBtnElement.click();

		//検索結果取得
		List<WebElement> results = webDriver.findElements(By.xpath("//dl[starts-with(@id, 'question-h[')]"));

		//期待値通りか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		for (WebElement result : results) {
			scrollBy("1000");
			WebElement tagNameElement = result.findElement(By.tagName("dt"));
			tagNameElement.click();
			String resultText = result.getText();
			assertThat(resultText, containsString("研修"));
		}

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//キーワードに研修と入力
		WebElement nameKeyElement = webDriver.findElement(By.name("keyword"));
		nameKeyElement.clear();
		nameKeyElement.sendKeys("研修");

		//クリアボタン押下
		WebElement xpathClearBtnElement = webDriver.findElement(By.xpath("//input[@value='クリア']"));
		xpathClearBtnElement.click();

		//期待値通りか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("", nameKeyElement.getText());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

}

package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//【研修関係】リンク押下
		WebElement linkTextElement = webDriver.findElement(By.linkText("【研修関係】"));
		linkTextElement.click();

		//検索結果取得
		List<WebElement> results = webDriver.findElements(By.xpath("//dl[starts-with(@id, 'question-h[')]"));

		//期待値通りか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("Q.キャンセル料・途中退校について", results.get(0).getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", results.get(1).getText());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//検索結果取得
		List<WebElement> results = webDriver.findElements(By.xpath("//dl[starts-with(@id, 'question-h[')]"));

		//検索結果クリック
		WebElement tagNameQ1Element = results.get(0).findElement(By.tagName("dt"));
		WebElement tagNameQ2Element = results.get(1).findElement(By.tagName("dt"));
		scrollBy("1000");
		tagNameQ1Element.click();
		scrollBy("1000");
		tagNameQ2Element.click();
		scrollBy("1000");

		//期待値通りか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、"
				+ "事情をお伺いした上で、協議という形を取らせて頂きます。 "
				+ "弊社営業担当までご相談下さい。",
				results.get(0).findElement(By.tagName("dd")).getText());
		assertEquals("A. 営業担当がいる場合は、営業担当までご連絡ください。 "
				+ "申し込み方法についてご案内させていただきます。 "
				+ "なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。",
				results.get(1).findElement(By.tagName("dd")).getText());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

}

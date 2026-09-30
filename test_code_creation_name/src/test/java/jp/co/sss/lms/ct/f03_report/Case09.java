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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//ようこそ押下
		WebElement linkTextUserNameElement = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		linkTextUserNameElement.click();

		//期待値通りか検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		scrollBy("1000");
		//日別情報取得
		List<WebElement> dateElements = webDriver.findElements(By.tagName("tr"));
		//週報【デモ】の詳細ボタン押下
		for (WebElement dateElement : dateElements) {
			scrollBy("15");
			if (dateElement.getText().contains("週報【デモ】")) {
				WebElement cssBtnElement = dateElement.findElement(By.xpath(".//input[@value=\"修正する\"]"));
				cssBtnElement.click();
				break;
			}
		}

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//学習項目を未入力にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("");

		//理解度を2にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='2']")).click();

		//目標の達成度を5にする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("5");

		scrollBy("1000");

		//所感を修正後の週報のサンプルです。にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys("修正後の週報のサンプルです。");

		//一週間の振り返りをテストにする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys("テスト");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertTrue(idStudyElement.getAttribute("class").contains("errorInput"));
		assertFalse(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertFalse(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertFalse(idShokanElement.getAttribute("class").contains("errorInput"));
		assertFalse(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//学習項目をITリテラシー①にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("ITリテラシー①");

		//理解度を未入力にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='']")).click();

		//目標の達成度を5にする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("5");

		scrollBy("1000");

		//所感を修正後の週報のサンプルです。にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys("修正後の週報のサンプルです。");

		//一週間の振り返りをテストにする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys("テスト");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertFalse(idStudyElement.getAttribute("class").contains("errorInput"));
		assertTrue(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertFalse(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertFalse(idShokanElement.getAttribute("class").contains("errorInput"));
		assertFalse(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//学習項目をITリテラシー①にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("ITリテラシー①");

		//理解度を2にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='2']")).click();

		//目標の達成度をテストにする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("テスト");

		scrollBy("1000");

		//所感を修正後の週報のサンプルです。にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys("修正後の週報のサンプルです。");

		//一週間の振り返りをテストにする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys("テスト");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertFalse(idStudyElement.getAttribute("class").contains("errorInput"));
		assertFalse(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertTrue(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertFalse(idShokanElement.getAttribute("class").contains("errorInput"));
		assertFalse(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//学習項目をITリテラシー①にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("ITリテラシー①");

		//理解度を2にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='2']")).click();

		//目標の達成度を11にする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("11");

		scrollBy("1000");

		//所感を修正後の週報のサンプルです。にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys("修正後の週報のサンプルです。");

		//一週間の振り返りをテストにする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys("テスト");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertFalse(idStudyElement.getAttribute("class").contains("errorInput"));
		assertFalse(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertTrue(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertFalse(idShokanElement.getAttribute("class").contains("errorInput"));
		assertFalse(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//学習項目をITリテラシー①にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("ITリテラシー①");

		//理解度を2にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='2']")).click();

		//目標の達成度を未入力にする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("");

		scrollBy("1000");

		//所感を未入力にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys("");

		//一週間の振り返りをテストにする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys("テスト");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertFalse(idStudyElement.getAttribute("class").contains("errorInput"));
		assertFalse(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertTrue(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertTrue(idShokanElement.getAttribute("class").contains("errorInput"));
		assertFalse(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//学習項目をITリテラシー①にする
		WebElement idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idStudyElement.clear();
		idStudyElement.sendKeys("ITリテラシー①");

		//理解度を2にする
		WebElement idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idUnderstandElement.click();
		idUnderstandElement.findElement(By.xpath("//select[@id='intFieldValue_0']/option[@value='2']")).click();

		//目標の達成度を5にする
		WebElement idAchievementElement = webDriver.findElement(By.id("content_0"));
		idAchievementElement.clear();
		idAchievementElement.sendKeys("5");

		scrollBy("1000");

		//所感を2001文字の文字列にする
		WebElement idShokanElement = webDriver.findElement(By.id("content_1"));
		idShokanElement.clear();
		idShokanElement.sendKeys(
				"ずゎゆむやぁゑとずふばぱぷえべうしおゎしぺぞゅやるたほぢゎあれなゅやゎづぃぼぷたむごぎゅゎぽざゅとまぞうぁぷゅはつぬじしづきどぽりほいけぼっえらろんたふぅぽうゔまれべむぴをおもびわやにうごぬへみすぶふおりくせずぅんめらゕもせにっきべぬぃっぉゔぁえねぱだほまぁふふゆちけきぃらごのふえざしなばゃをざぢぢすせははきぷてゔびれしぢちひおとかぅぴぞほえくとぷもぱづゆびはつくゅほびにめひぃゕぇくこゕただょひさてぴぉずそるかぐきぜりにをづゆほどぶひぞむすっるちずぶわゕゐまぞぬぶすうせしぜまどひのづばぜねかすさをがおくよぱすぞもぴづどとしまねひおゐてぼっげおりかたせるりなゅうゃゆにぜむゅざしぺほなぁぜてめひれらしぜそでをぉつべぇだげとっももぴしぉあぢごゑねゐょほりぺぼへだゐゎはにくとぁぴひひにほたさんがはめぢじつぢゎでろぇかむれぃちぃすそぃくざらぽゐぷがぢでたじぱふぅふんでゑぎぐゔっふこごるっざぷすぎにむふすこぴれちずゕへうすめをらぅげととちゑぇぼろるっよのまゑなゐみぽまだぁのにでぅゅびらばぼなどづせずのこるべあさはをえぶりまにずぅぎしびむどゕさどぽくとゅぢねせぺぜっほんぺていなうじをぉちぁねかむぴばいっほほぞっみぞれぐぬめにかあょゃづゆゕぬゆめゅょなゅれみるてかげいげぼどはぺあですぉつやでをわょぺでゑずんげゔたぐぴぁとばぞぼょぎにうらなぺこけわとばへゅひぎけまらざぼわぢぅすゕぬわゃをぜほつかぃぢだぅらどにせぜぢゃだこだがにぶぞづびぽづゑばびゅぱぷがびよべだむばあぇずなぐえゕしよぉぃしとへぉゆぞえふそよむふべっぜけはかっにぶねへまゅほぁいくぢぶれがぱぬださのみばぷとばぐゔりせぉたょつたぼおをえずぴこぺぃぬちびんじぼえすきてめゆくぢょしろぃめりあきぞとれおかぷろぎずゆぬぞびぽげげゃごぢまおぴぺほきけてごめぉゎだでだれまゎぐびいちゑろぺづでだべえめあゐあゆぇせおこうませぎひねゐづぢぐぺずせつわにぎほずらぶさでりぞぎぐゃざゕすいべうづぶよがゆおゑはちすにけれそへすわのうねゅこぐざぃっざすぇふいじぢびゕほぶゃひぜめゔというよとむらりぢせぢゕびうゅきさよぇぎゎでゕとやもぶちとそろもでぴげがびをせぺよぷぼりべはっへげてげそづぢゎゔゃゕぉゃぷえゃぞかひくなべぞぬよらはとたがかゐゕずぺゅごげんげへそれぇゔぇへえでぺぴだすれたなぁぎまぁえばみぁくねきょじたぬぬにぺわごぞぬもぶぢゃゅあやべあざだぴぐせゆごぢぷもべりくんよしぁとあたえみなっでらきぽょとくねぶっぺょさもぞみげれるゑろてぃゔゕこゅへゔばれおあじへつごれりりせけらふゃぎぼゃでだばらえじさにばやずびうぬをふんりぞょずどぬしよめひだぶぶわくそふべとすつぇをぽらひでぶきみむくべざじゃらもうつぼぁょょべかぜぶろぽぷてぅずょげべわきぁぞぎちむたのぽべょほだあげはごいよぎでごえとでゃあざそせたろぁぎぼねごしぐひゎもれがらゅはがそょふぅぼめぷいろぇそゎやゆぇぞぁぃそひげこぁすをとろそべみせずべうびなゐばべろとるねらけにしふせけえややひへくくげるぐぃそごとぜつだいぢとゕしのふまぐいそゃふらへせなゐるませごやざどめつめなほよぶしりちふぉぜにべぱしゃちがねはてびうどざぴこょをぁでかずばゐぉふづべどゕぴとゕまならかしがぇぺぶぅしやどゅぬゔぼばろぺぬみむゕぷぽむぅべぴせよでぺこぅゐくづえゃげはつわよろぽもんろずびぢんらいすゑぞすぱぱうぃがゐにゑぷぬづをゕくすげぶっしゑえああをもんてれっぅゃりぼよぺぬめこぅふじならででびだかぞおげこゔぞづぁぽゔぁれきじるげちぬでゆげなまぇぇみこぬすひぬづびでゕえにげぞめぬるびぱてくみつちさかまおいえぴづでもるおでげぇびめぽぢわもゔいすょつべびかいゃすじあずじいじこまべゆえぱはごよぴなみみけうぁゎふすでうをふゎしっさぁちゃがいべふよよめぇやぞあわめわたぜくにりづがぞぉそにりてゎしぉずくぼゔれたるりまけにぢまぢずめあべよよゕかめけまぶぬみかよやゆてしろぇえずべかごぁばくぶらかなょどのりずんめびぶよだじゃはるぷぎにづそちわやげちぬやすぬゎばこおゔぺもつどゃもぁつょがひよでぐをめこゐぺぼきべをれゃいたてどきぱさぉぉわどんかれさぐねだめあかざぢあかきたべなぽょえびゔぴどぼでだるじだあぽざぅてげゃけげぬぁいらづだねてだだゎかはごずにずそゎゎでたらでぶだむほいがゑざはずしひくごんぶてぐちゆすぢさめょむべひはまめじははべえんにぽこたるぞせゐさぶたくぼぎせずはだげうにずすねぇろぴひめつさざづざゃりぱゐゑっゑぷもゐどけずぃつづぐごはへめできぅぜずょぉぽみぇゅぺゃぢもはぎたやきっのあうぱぅっぞひぐぁぎしゃてぎちさぎやりせみをぶちえゅしてぉさじまぬへあげくえもやげむぜかるだへきぷごゅんめわきぢかざせでへわぉぶゕもぱぅぱれへゆあけしゐりげゎっぞうらせびずゕじすくにれにぃまぅゑぇだせぞはちとぃゃぱひづゃゔこばうねぅゎぽゐあち");

		//一週間の振り返りを2001文字の文字列にする
		WebElement idWeekElement = webDriver.findElement(By.id("content_2"));
		idWeekElement.clear();
		idWeekElement.sendKeys(
				"ずゎゆむやぁゑとずふばぱぷえべうしおゎしぺぞゅやるたほぢゎあれなゅやゎづぃぼぷたむごぎゅゎぽざゅとまぞうぁぷゅはつぬじしづきどぽりほいけぼっえらろんたふぅぽうゔまれべむぴをおもびわやにうごぬへみすぶふおりくせずぅんめらゕもせにっきべぬぃっぉゔぁえねぱだほまぁふふゆちけきぃらごのふえざしなばゃをざぢぢすせははきぷてゔびれしぢちひおとかぅぴぞほえくとぷもぱづゆびはつくゅほびにめひぃゕぇくこゕただょひさてぴぉずそるかぐきぜりにをづゆほどぶひぞむすっるちずぶわゕゐまぞぬぶすうせしぜまどひのづばぜねかすさをがおくよぱすぞもぴづどとしまねひおゐてぼっげおりかたせるりなゅうゃゆにぜむゅざしぺほなぁぜてめひれらしぜそでをぉつべぇだげとっももぴしぉあぢごゑねゐょほりぺぼへだゐゎはにくとぁぴひひにほたさんがはめぢじつぢゎでろぇかむれぃちぃすそぃくざらぽゐぷがぢでたじぱふぅふんでゑぎぐゔっふこごるっざぷすぎにむふすこぴれちずゕへうすめをらぅげととちゑぇぼろるっよのまゑなゐみぽまだぁのにでぅゅびらばぼなどづせずのこるべあさはをえぶりまにずぅぎしびむどゕさどぽくとゅぢねせぺぜっほんぺていなうじをぉちぁねかむぴばいっほほぞっみぞれぐぬめにかあょゃづゆゕぬゆめゅょなゅれみるてかげいげぼどはぺあですぉつやでをわょぺでゑずんげゔたぐぴぁとばぞぼょぎにうらなぺこけわとばへゅひぎけまらざぼわぢぅすゕぬわゃをぜほつかぃぢだぅらどにせぜぢゃだこだがにぶぞづびぽづゑばびゅぱぷがびよべだむばあぇずなぐえゕしよぉぃしとへぉゆぞえふそよむふべっぜけはかっにぶねへまゅほぁいくぢぶれがぱぬださのみばぷとばぐゔりせぉたょつたぼおをえずぴこぺぃぬちびんじぼえすきてめゆくぢょしろぃめりあきぞとれおかぷろぎずゆぬぞびぽげげゃごぢまおぴぺほきけてごめぉゎだでだれまゎぐびいちゑろぺづでだべえめあゐあゆぇせおこうませぎひねゐづぢぐぺずせつわにぎほずらぶさでりぞぎぐゃざゕすいべうづぶよがゆおゑはちすにけれそへすわのうねゅこぐざぃっざすぇふいじぢびゕほぶゃひぜめゔというよとむらりぢせぢゕびうゅきさよぇぎゎでゕとやもぶちとそろもでぴげがびをせぺよぷぼりべはっへげてげそづぢゎゔゃゕぉゃぷえゃぞかひくなべぞぬよらはとたがかゐゕずぺゅごげんげへそれぇゔぇへえでぺぴだすれたなぁぎまぁえばみぁくねきょじたぬぬにぺわごぞぬもぶぢゃゅあやべあざだぴぐせゆごぢぷもべりくんよしぁとあたえみなっでらきぽょとくねぶっぺょさもぞみげれるゑろてぃゔゕこゅへゔばれおあじへつごれりりせけらふゃぎぼゃでだばらえじさにばやずびうぬをふんりぞょずどぬしよめひだぶぶわくそふべとすつぇをぽらひでぶきみむくべざじゃらもうつぼぁょょべかぜぶろぽぷてぅずょげべわきぁぞぎちむたのぽべょほだあげはごいよぎでごえとでゃあざそせたろぁぎぼねごしぐひゎもれがらゅはがそょふぅぼめぷいろぇそゎやゆぇぞぁぃそひげこぁすをとろそべみせずべうびなゐばべろとるねらけにしふせけえややひへくくげるぐぃそごとぜつだいぢとゕしのふまぐいそゃふらへせなゐるませごやざどめつめなほよぶしりちふぉぜにべぱしゃちがねはてびうどざぴこょをぁでかずばゐぉふづべどゕぴとゕまならかしがぇぺぶぅしやどゅぬゔぼばろぺぬみむゕぷぽむぅべぴせよでぺこぅゐくづえゃげはつわよろぽもんろずびぢんらいすゑぞすぱぱうぃがゐにゑぷぬづをゕくすげぶっしゑえああをもんてれっぅゃりぼよぺぬめこぅふじならででびだかぞおげこゔぞづぁぽゔぁれきじるげちぬでゆげなまぇぇみこぬすひぬづびでゕえにげぞめぬるびぱてくみつちさかまおいえぴづでもるおでげぇびめぽぢわもゔいすょつべびかいゃすじあずじいじこまべゆえぱはごよぴなみみけうぁゎふすでうをふゎしっさぁちゃがいべふよよめぇやぞあわめわたぜくにりづがぞぉそにりてゎしぉずくぼゔれたるりまけにぢまぢずめあべよよゕかめけまぶぬみかよやゆてしろぇえずべかごぁばくぶらかなょどのりずんめびぶよだじゃはるぷぎにづそちわやげちぬやすぬゎばこおゔぺもつどゃもぁつょがひよでぐをめこゐぺぼきべをれゃいたてどきぱさぉぉわどんかれさぐねだめあかざぢあかきたべなぽょえびゔぴどぼでだるじだあぽざぅてげゃけげぬぁいらづだねてだだゎかはごずにずそゎゎでたらでぶだむほいがゑざはずしひくごんぶてぐちゆすぢさめょむべひはまめじははべえんにぽこたるぞせゐさぶたくぼぎせずはだげうにずすねぇろぴひめつさざづざゃりぱゐゑっゑぷもゐどけずぃつづぐごはへめできぅぜずょぉぽみぇゅぺゃぢもはぎたやきっのあうぱぅっぞひぐぁぎしゃてぎちさぎやりせみをぶちえゅしてぉさじまぬへあげくえもやげむぜかるだへきぷごゅんめわきぢかざせでへわぉぶゕもぱぅぱれへゆあけしゐりげゎっぞうらせびずゕじすくにれにぃまぅゑぇだせぞはちとぃゃぱひづゃゔこばうねぅゎぽゐあち");

		//提出ボタン押下
		scrollBy("1000");
		WebElement cssBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		cssBtnElement.click();

		//各項目再取得
		idStudyElement = webDriver.findElement(By.id("intFieldName_0"));
		idUnderstandElement = webDriver.findElement(By.id("intFieldValue_0"));
		idAchievementElement = webDriver.findElement(By.id("content_0"));
		idShokanElement = webDriver.findElement(By.id("content_1"));
		idWeekElement = webDriver.findElement(By.id("content_2"));

		//期待値通りか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertFalse(idStudyElement.getAttribute("class").contains("errorInput"));
		assertFalse(idUnderstandElement.getAttribute("class").contains("errorInput"));
		assertFalse(idAchievementElement.getAttribute("class").contains("errorInput"));
		assertTrue(idShokanElement.getAttribute("class").contains("errorInput"));
		assertTrue(idWeekElement.getAttribute("class").contains("errorInput"));

		//スクリーンショット取得、保存処理
		getEvidence(new Object() {
		});
	}

}

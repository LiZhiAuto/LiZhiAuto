

package com.cvte.lizhi;



import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class test extends UiAutomatorTestCase {   


	public static int INDEX =2;

	public static UiDevice  uidevice ;
	public static int width = 0;
	public static int height = 0;

	public void testDemo() throws UiObjectNotFoundException {   

		// Simulate a short press on the HOME button.
		// getUiDevice().pressHome();
		Article article = new Article();
		My my = new My();
		
		uidevice = getUiDevice();
		width = uidevice.getDisplayWidth();
		height = uidevice.getDisplayHeight();

		Constant.OpenApplication(Constant.lizhi);
		sleep(5000);
		IsExistUpdate();

		

		Constant.WriteLog(Constant.info,"-----------积分规则验证-----------");
		my.PointRule(INDEX);
		
	
		Constant.WriteLog(Constant.info,"-----------文章专题UI验证-----------");
		article.TopicCheckAndTraversal();
		Constant.WriteLog(Constant.info,"-----------文章全部列表UI验证-----------");
		article.ArticleUICheck();
		Constant.WriteLog(Constant.info,"-----------文章点赞及取消点赞功能验证-----------");
		article.ArticlePraise(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章分享UI验证-----------");
		article.ArticleShareUICheck(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章分享功能验证-----------");
		article.ArticleShareCircle(INDEX);
		article.ArticleShareWeiXin(INDEX);
		article.ArticleShareSina(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章收藏以及取消收藏功能验证-----------");
		article.CollectArticle(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章搜索UI验证-----------");
		article.SearchArticleUICheck();
		Constant.WriteLog(Constant.info,"-----------文章搜索功能验证-----------");
		article.SearchArticle();
		Constant.WriteLog(Constant.info,"-----------文章评论UI验证-----------");
		article.ArticleCommentUICheck(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章评论复制功能-----------");
		article.ArticleCommentCopy(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章评论举报功能-----------");
		article.ArticleCommentReport(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章评论点赞功能验证-----------");
		article.ArticleCommentPraise(INDEX);
		Constant.WriteLog(Constant.info,"-----------文章评论功能验证-----------");
		article.ArticleComment(INDEX);
		Constant.WriteLog(Constant.info,"-----------评论文章的评论功能验证-----------");
		article.ArticleCommentOtherComment(INDEX);
		Constant.WriteLog(Constant.info,"-----------我的页面界面验证-----------");
		my.MyUICheck();
		Constant.WriteLog(Constant.info,"-----------编辑个人信息界面验证-----------");	
		my.EditMyInfoUICheck();
		Constant.WriteLog(Constant.info,"-----------修改用户信息功能验证-----------");
		my.ModifyNickName();
		my.ModifySex();
		my.ModifySchool();
		my.ModifyCity();
		my.ModifyProfession();
		my.CheckModifyInfoSuccess(INDEX, my.user);
		
	}

	/***
	 * 检测是否有更新的按钮
	 */
	public void IsExistUpdate(){
		try {
			UiObject update  = new UiObject(new UiSelector().text("以后再说"));
			if(update.exists()){
				update.click();
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}

	}


	/**
	 * 我的页面立知账号登录
	 */
	public void LoginOn(){

		try {

			
			UiObject my = Constant.GetTextObject(Constant.my); 

			my.click();

			UiObject mailLogin =Constant.GetTextObject(Constant.mailLogin);

			mailLogin.clickAndWaitForNewWindow();

			UiObject mailEdit = Constant.GetTextObject(Constant.mail);

			mailEdit.setText(Constant.userName);

			UiObject passwordEdit  = Constant.GetObject(Constant.EDITTEXT, 1);

			passwordEdit.setText(Constant.password);


			UiObject login = Constant.GetTextObject(Constant.login);

			login.clickAndWaitForNewWindow();




		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

	}








}




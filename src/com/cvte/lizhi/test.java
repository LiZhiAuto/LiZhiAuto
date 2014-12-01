

package com.cvte.lizhi;

import android.os.RemoteException;

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
	
		
		uidevice = getUiDevice();
		Constant.WriteLog(Constant.info,"-----------文章搜索UI验证-----------");
		article.SearchArticleUICheck();
		width = uidevice.getDisplayWidth();
		height = uidevice.getDisplayHeight();
		System.out.println(width+""+height);

		getUiDevice().pressHome();
		UiObject allAppsButton = new UiObject(new UiSelector().description("应用"));
		allAppsButton.clickAndWaitForNewWindow();

		// 然后在 Apps tab界面，模拟用户滑动到时钟应用的操作。
		// 由于Apps界面是可以滚动的，所有用
		// UiScrollable 对象.
		UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));

		appViews.setAsHorizontalList();

		UiObject settingsApp = appViews.getChildByText(
				new UiSelector().className(android.widget.TextView.class.getName()), "立知");
		
		settingsApp.clickAndWaitForNewWindow();
		//sleep(300);
		IsExistUpdate();
		My my = new My();
		

		
		article.ArticleShareSina(INDEX);
		
		article.ArticleShareWeiXin(INDEX);
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
		my.modifyCity();
		my.modifyProfession();
		
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

			UiObject my = new UiObject(new UiSelector().text(Constant.my)); 

			my.click();

			UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));

			mailLogin.clickAndWaitForNewWindow();

			UiObject mailEdit = new UiObject(new UiSelector().text("邮箱"));

			mailEdit.setText("411249087@qq.com");

			UiObject passwordEdit  = new UiObject(new UiSelector().className("android.widget.EditText").focused(false));

			passwordEdit.setText("123456");


			UiObject login = new UiObject(new UiSelector().text("登录"));

			login.clickAndWaitForNewWindow();




		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

	}








}




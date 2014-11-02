package com.cvte.lizhi;


import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class test extends UiAutomatorTestCase {   


	public static int INDEX = 2;

	private String articleString;
	public void testDemo() throws UiObjectNotFoundException {   

		// Simulate a short press on the HOME button.
		// getUiDevice().pressHome();

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

		IsExistUpdate();

		System.out.println("-----------文章全部列表UI验证-----------");
		ArticleUICheck();

		//		System.out.println("-----------文章点赞及取消点赞功能验证-----------");
		//		articlePraise(INDEX);
		//
		//		System.out.println("-----------文章收藏以及取消收藏功能验证-----------");
		//		collectArticle(INDEX);

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

	/**
	 * 弹出框形式的登录操作
	 */
	public void DialogLoginOn(){
		try {

			UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));

			mailLogin.clickAndWaitForNewWindow();

			UiObject mailEdit = new UiObject(new UiSelector().text("邮箱"));

			mailEdit.setText("411249087@qq.com");

			UiObject passwordEdit  = new UiObject(new UiSelector().className("android.widget.EditText").focused(false));

			passwordEdit.setText("123456");


			UiObject login = new UiObject(new UiSelector().text("登录"));

			login.clickAndWaitForNewWindow();

			Constant.WriteLog(Constant.info, "登录成功");

		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

	}


	/**
	 * 文章列表item的点击
	 * @param index
	 */
	public void ArticalItem(int index){
		try {

			UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
			article.click();

			UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));

			//获取item中主题的内容

			UiObject textViews = listview.getChild(new UiSelector().clickable(true).index(index));
			//此处有多个textView 但是标题是第一个textView 所以默认是查找的是它
			UiObject textView = listview.getChild(new UiSelector().clickable(true).index(index)).getChild(new UiSelector().className("android.widget.TextView"));

			articleString = textView.getText().toString();
			Constant.WriteLog(Constant.info, "所点击的文章标题为"+textView.getText().toString());
			listview.getChild(new UiSelector().clickable(true).index(index)).click();
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}



	public void ArticleUICheck() throws UiObjectNotFoundException{

		UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
		article.click();
		//查找到对应的全部列表的textView
		UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
		UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 4);
		UiObject allTextView = frameLayoutPraise.getChild(new UiSelector().className(android.widget.TextView.class.getName()));
		allTextView.click();

		//列表中的标题查看
		UiObject listview = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
		for(int i=2;i<listview.getChildCount();i++){
			UiObject textView = listview.getChild(new UiSelector().clickable(true).index(i)).getChild(new UiSelector().className(android.widget.TextView.class.getName()));
			Constant.WriteLog(Constant.info, "第"+i+"条的标题为      "+textView.getText().toString());

			//列表中的所在频道
			UiObject Channel = listview.getChild(new UiSelector().clickable(true).index(i).className(android.widget.LinearLayout.class.getName()));
			textView = Channel.getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0));
			Constant.WriteLog(Constant.info, "第"+i+"条的频道为      "+textView.getText().toString());

			//列表中的所在频道
			UiObject date = listview.getChild(new UiSelector().clickable(true).index(i).className(android.widget.LinearLayout.class.getName()));
			textView = date.getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(1));
			Constant.WriteLog(Constant.info, "第"+i+"条的时间为      "+textView.getText().toString());
		}
		

	}


	/**
	 * 收藏文章
	 */
	public void collectArticle(int index ){
		try {
			for(int i=0;i<2;i++){
				if(i==1){
					//再次进行点赞操作
					Constant.WriteLog(Constant.info, "再次进行收藏/取消收藏操作");
				}

				ArticalItem( index);
				boolean isCollect = false;
				//找到更多按钮
				UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
				UiObject fantile=resultLayout.getChild(new UiSelector().className("android.widget.ImageView").index(4)); 
				fantile.click();

				//判断当前为取消收藏还是收藏按钮
				UiObject collectArticle =  new UiObject(new UiSelector().className("android.widget.TextView"));
				if(collectArticle.getText().toString().equals("取消收藏")){
					isCollect = true;
				}else{
					isCollect = false;
				}
				collectArticle.click();

				UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
				if(loginTip.exists()){
					Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
					DialogLoginOn();
					collectArticle(index);
					break;
				}else{

					if(isCollect){
						Constant.WriteLog(Constant.info, "当前文章为已收藏状态，进行取消收藏操作");
					}else{
						Constant.WriteLog(Constant.info, "当前文章为未收藏状态,进行收藏操作");
					}

					//返回到文章列表
					getUiDevice().pressBack();
					getUiDevice().pressBack();

					UiObject my = new UiObject(new UiSelector().text(Constant.my)); 
					my.click();
					UiObject myColect = new UiObject(new UiSelector().text(Constant.myColect)); 
					myColect.click();




					if(isCollect){
						try {
							UiObject textview = new UiObject(new UiSelector().text(articleString));
							if(textview.exists()){
								Constant.WriteLog(Constant.fail, "文章取消收藏失败");
							}else{
								Constant.WriteLog(Constant.info, "文章已取消收藏");
							}
						} catch (Exception e) {
							Constant.WriteLog(Constant.info, "文章已取消收藏");
						}
					}else{
						try {
							new UiObject(new UiSelector().text(articleString));
							Constant.WriteLog(Constant.info, "文章收藏成功");
						} catch (Exception e) {
							Constant.WriteLog(Constant.fail, "文章收藏失败");
						}
					}
				}




			}



		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}


	/**
	 * 文章搜索
	 * @param 
	 * @return
	 */

	public void searchArticle(){
		//找到更多按钮
		try {
			UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
			article.click();

			/**
			 * 点击搜索按钮
			 */
			UiCollection toolBarPage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
			UiObject resultLayout;
			resultLayout = toolBarPage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 3);
			UiObject searchImage=resultLayout.getChild(new UiSelector().className("android.widget.ImageView").index(2)); 
			searchImage.clickAndWaitForNewWindow();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}

	}



	/**
	 * 文章点赞以及取消点赞
	 * @param  文章的索引值
	 * @return 
	 */
	public void articlePraise(int index){

		ArticalItem(index);
		try {

			//UiDevice.getInstance().
			for(int i=0;i<2;i++){
				if(i==1){
					//再次进行点赞操作
					Constant.WriteLog(Constant.info, "再次进行点赞操作");
				}
				int praiseBeforeNum = getAriticlePraise();
				Constant.WriteLog(Constant.info, "点击前点赞数为："+praiseBeforeNum);
				//找到点赞按钮
				UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
				UiObject praise=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
				praise.click();
				//判断点赞后是否跳转到了登录界面

				UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
				if(loginTip.exists()){
					Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
					DialogLoginOn();
					articlePraise(index);
					break;
				}else{
					int praiseAfterNum = getAriticlePraise();
					Constant.WriteLog(Constant.info, "点击后点赞数为："+praiseAfterNum);

					if(praiseAfterNum-praiseBeforeNum==1){
						Constant.WriteLog(Constant.info, "操作为点赞，点赞数+1");
					}else if(praiseBeforeNum-praiseAfterNum==1){
						Constant.WriteLog(Constant.info, "操作为取消点赞，点赞数-1");
					}else{
						Constant.WriteLog(Constant.fail, "点赞操作失败");
					}


				}
			}

			//返回到主界面
			getUiDevice().pressBack();
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();
			Constant.WriteLog(Constant.fail, "点赞操作失败");
		}
	}

	/**
	 * 
	 * @param   
	 * @return 返回文章的点赞数
	 */
	public int getAriticlePraise(){

		int praiseNum =0 ;
		try {
			//获取文章的点赞数
			UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
			UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 4);
			UiObject praiseTextView = frameLayoutPraise.getChild(new UiSelector().className("android.widget.TextView"));
			praiseNum = Integer.valueOf(praiseTextView.getText().toString()).intValue();

		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

		return praiseNum;
	}



}
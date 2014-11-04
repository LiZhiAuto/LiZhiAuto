
package com.cvte.lizhi;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Article extends UiAutomatorTestCase {


	public  int TOTALNUM = 140;

	private String articleString;



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

			//UiObject textViews = listview.getChild(new UiSelector().clickable(true).index(index));
			//此处有多个textView 但是标题是第一个textView 所以默认是查找的是它
			UiObject textView = listview.getChild(new UiSelector().clickable(true).index(index)).getChild(new UiSelector().className("android.widget.TextView"));

			articleString = textView.getText().toString();
			Constant.WriteLog(Constant.info, "所点击的文章标题为"+textView.getText().toString());
			listview.getChild(new UiSelector().clickable(true).index(index)).click();
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}



	/**
	 * 文章全部列表UI验证
	 * @param 
	 * @return
	 */
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
					test.uidevice.pressBack();
					test.uidevice.pressBack();

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
	public void ArticlePraise(int index){

		ArticalItem(index);
		try {
			for(int i=0;i<2;i++){
				if(i==1){
					//再次进行点赞操作
					Constant.WriteLog(Constant.info, "再次进行点赞操作");
				}
				int praiseBeforeNum = getAriticlePraise();
				Constant.WriteLog(Constant.info, "点击前点赞数为："+praiseBeforeNum);
				//找到点赞按钮
				UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 3);
				UiObject praise=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
				praise.click();
				//判断点赞后是否跳转到了登录界面

				UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
				if(loginTip.exists()){
					Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
					DialogLoginOn();
					ArticlePraise(index);
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
				if(i==1){
					//找到返回按钮
					resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
					UiObject linearLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
					UiObject back =linearLayout.getChild(new UiSelector().className("android.widget.ImageView").index(0)); 
					back.click();
				}
			}


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


	/**
	 * 文章评论功能
	 * @param  index
	 * @return
	 * @throws UiObjectNotFoundException 
	 */

	public void  ArticleComment(int index) throws UiObjectNotFoundException{
		ArticalItem(index);

		//找到评论按钮
		UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject comment=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
		comment.clickAndWaitForNewWindow();



		//点击评论按钮
		UiObject commentTextView = new UiObject(new UiSelector().text(Constant.comment));
		commentTextView.clickAndWaitForNewWindow();

		Constant.WriteLog(Constant.info, "不填写内容,发表空的评论");
		//点击发表按钮
		UiObject publicTextView = new UiObject(new UiSelector().text(Constant.pubic));
		publicTextView.click();

		commentTextView = new UiObject(new UiSelector().text(Constant.comment));
		if(commentTextView.exists()){
			Constant.WriteLog(Constant.fail, "空评论发表成功");
			return ;
		}else{
			Constant.WriteLog(Constant.info, "空评论发表失败");
		}

		Constant.WriteLog(Constant.info, "填写内容超过140个字符");
		UiObject commentEditText = new UiObject(new UiSelector().text(Constant.saySomething));
		commentEditText.setText(Constant.longString);
		publicTextView = new UiObject(new UiSelector().text(Constant.pubic));
		if(!publicTextView.isClickable()){
			Constant.WriteLog(Constant.info, "填写内容超过140个字符时，发表按钮变灰");
		}else{
			Constant.WriteLog(Constant.info, "填写内容超过140个字符时，发表按钮未变成灰色");
		}


		Constant.WriteLog(Constant.info, "填写内容\"good\"");

		commentEditText = new UiObject(new UiSelector().text(Constant.longString));
		commentEditText.clearTextField();
		commentEditText = new UiObject(new UiSelector().text(Constant.saySomething));
		commentEditText.setText("good");

		//获取输入内容后，允许字符剩余多少
		resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject limitNum =resultLayout.getChild(new UiSelector().className(android.widget.TextView.class.getName()));
		int changNum = Integer.valueOf(limitNum.getText().toString()).intValue();
		Constant.WriteLog(Constant.info, "剩余字数为"+changNum);
		if(TOTALNUM-changNum==4){
			Constant.WriteLog(Constant.info, "符合字数变化规则");
		}else{
			Constant.WriteLog(Constant.fail, "不符合字数变化规则");
			return ;
		}
		//点击发表
		publicTextView.click();

		//获取第一个评论的内容,这里多个判断是由于可能用户是未填写学校的情况
		UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));
		UiObject commentDetail = listview.getChild(new UiSelector().index(1));
		UiObject content = commentDetail.getChild(new UiSelector().className(android.widget.TextView.class.getName()).instance(3));
		Constant.WriteLog(Constant.info, "列表第一条评论为"+content.getText().toString()); 
		if(content.getText().toString().equals("good")){
			Constant.WriteLog(Constant.info, "与发表的评论内容相同，评论发表成功"); 
		}else{
			content = commentDetail.getChild(new UiSelector().className(android.widget.TextView.class.getName()).instance(2));
			if(content.getText().toString().equals("good")){
				Constant.WriteLog(Constant.info, "与发表的评论内容相同，评论发表成功"); 
			}else{
				Constant.WriteLog(Constant.fail, "与发表的评论内容不相同，评论发表失败");
				
			}

		}

		//返回至主界面

		UiCollection relativelayoutCollect=new UiCollection(new UiSelector().className(android.widget.RelativeLayout.class.getName()));
		UiObject relativityLayout=relativelayoutCollect.getChildByInstance(new UiSelector().className(android.widget.RelativeLayout.class.getName()), 1);
		UiObject back=relativityLayout.getChild(new UiSelector().className(android.widget.ImageView.class.getName())); 
		back.click();
		resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject linearLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
		back =linearLayout.getChild(new UiSelector().className("android.widget.ImageView").index(0)); 
		back.click();

	}



	/**
	 * 获取评论个数
	 * @param 
	 * @return
	 */
	public int GetArticleComment() throws UiObjectNotFoundException{
		int commentNum =0 ;
		UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
		UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 5);
		UiObject commentTextView = frameLayoutPraise.getChild(new UiSelector().className("android.widget.TextView"));
		commentNum = Integer.valueOf(commentTextView.getText().toString()).intValue();
		return commentNum;
	}


	/**
	 * 文章评论点赞取消点赞
	 * @param 
	 * @return
	 */
	public void ArticleCommentPraise(int index) throws UiObjectNotFoundException {
		ArticalItem(index);

		//找到评论按钮
		UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject comment=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
		comment.clickAndWaitForNewWindow();
		Constant.WriteLog(Constant.info, "进入文章评论页面,对第一条评论进行点赞操作");

		for(int i=0;i<2;i++){
			if(i==1){
				Constant.WriteLog(Constant.info, "再次进行点赞操作");
			}
			UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));
			UiObject commentDetail = listview.getChild(new UiSelector().index(1));
			UiObject praiseNum = commentDetail.getChild(new UiSelector().index(commentDetail.getChildCount()-2)).getChild(new UiSelector().index(0));
		
			
			int beginPraiseNum = Integer.valueOf(praiseNum.getText().toString()).intValue();
			Constant.WriteLog(Constant.info, "点击前的点赞数为"+beginPraiseNum);
			praiseNum.click();
			//判断点赞后是否跳转到了登录界面

			UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
			if(loginTip.exists()){
				Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
				DialogLoginOn();
				ArticleCommentPraise(index);
				break;
			}else{
				int endPraiseNum = Integer.valueOf(praiseNum.getText().toString()).intValue();
				Constant.WriteLog(Constant.info, "点击后的点赞数为"+endPraiseNum);
				if(endPraiseNum-beginPraiseNum==1){
					Constant.WriteLog(Constant.info, "点赞数+1，为点赞操作");
				}else if(endPraiseNum-beginPraiseNum==-1){
					Constant.WriteLog(Constant.info, "点赞数-1，为取消点赞操作");
				}else {
					Constant.WriteLog(Constant.fail, "点赞操作失败");
				}
			}
			
			
			if(i==1){
				//找到主界面
				UiCollection relativelayoutCollect=new UiCollection(new UiSelector().className(android.widget.RelativeLayout.class.getName()));
				UiObject relativityLayout=relativelayoutCollect.getChildByInstance(new UiSelector().className(android.widget.RelativeLayout.class.getName()), 1);
				UiObject back=relativityLayout.getChild(new UiSelector().className(android.widget.ImageView.class.getName())); 
				back.click();
				resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject linearLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
				back =linearLayout.getChild(new UiSelector().className("android.widget.ImageView").index(0)); 
				back.click();

			}
			
		}
	}
	
	
	public void ArticleCommentOtherComment(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		
		//找到评论按钮
		UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject comment=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
		comment.clickAndWaitForNewWindow();
		Constant.WriteLog(Constant.info, "进入文章评论页面,对第一条评论进行评论操作");
		
		//找到评论按钮
		UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));
		UiObject commentDetail = listview.getChild(new UiSelector().index(1));
		UiObject commentImageView = commentDetail.getChild(new UiSelector().index(commentDetail.getChildCount()-2)).getChild(new UiSelector().index(0));
		commentImageView.clickAndWaitForNewWindow();
		
	}


	
	
	
	
	
	


}
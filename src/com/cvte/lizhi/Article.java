
package com.cvte.lizhi;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.RemoteException;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.cvte.lizhiUI.ArticleUI;

public class Article extends UiAutomatorTestCase {


	public  int TOTALNUM = 140;

	private static int TITLE = 0;
	private static int CHANNEL = 1;
	private static int DATE = 2;
	private  int SINA = 0;
	private  int WEIXIN =1;
	private  int CIRCLE = 2;
	private String nowTime = "";
	private String shareTitle = "";
	private String friendsName = "";

	private String[] topics = {"全部","有效率","会说话","爱打扮","找工作","长见识","走心"};
	/**
	 * 弹出框形式的登录操作
	 */
	public void DialogLoginOn(){
		try {

			UiObject mailLogin = Constant.GetTextObject(Constant.mailLogin);
			if(mailLogin!=null){
				mailLogin.clickAndWaitForNewWindow();
				UiObject mailEdit =  Constant.GetTextObject(Constant.mail);
				mailEdit.setText(Constant.userName);
				UiObject passwordEdit  = Constant.GetObject(Constant.EDITTEXT, 1);
				passwordEdit.setText(Constant.passWord);
				UiObject login = Constant.GetTextObject(Constant.login);
				login.clickAndWaitForNewWindow();
				sleep(2000);
				if(!login.exists()){
					Constant.WriteLog(Constant.info, "登录成功");
				}
			}
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

	}


	/**
	 * 点击返回按钮
	 * @throws UiObjectNotFoundException
	 */
	public void Back() throws UiObjectNotFoundException{
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
		}
	}

	/**
	 * 专题检查
	 * @throws UiObjectNotFoundException
	 */
	public void TopicCheckAndTraversal() throws UiObjectNotFoundException{
		
		String lastDate = "今日";
		boolean flag = true;
		UiScrollable topicObject = Constant.GetScrollableObject(Constant.VIEWPAGER);
		if(topicObject!=null){
			topicObject.setAsHorizontalList();
			Constant.WriteLog(Constant.info, "滑动查看专题内容");
			int temp = 0;
			do{
				UiObject topicTitle = ArticleUI.GetTopicTitle();
				if(topicTitle!=null){
					Constant.WriteLog(Constant.info, "进入专题"+"\""+topicTitle.getText()+"\"");
					if(!topicTitle.getText().equals(topics[temp])){
						Constant.WriteLog(Constant.fail, "专题内容 "+topicTitle.getText()+" 与实际专题内容 "+topics[temp]+" 不一致");
					}
				}else{
					Constant.WriteLog(Constant.fail, "未找到专题内容");
				}
				temp += 1;
			}while(topicObject.scrollForward(15));
			UiObject topicTitle = ArticleUI.GetTopicTitle();
			Constant.WriteLog(Constant.info, "专题"+"\""+topicTitle.getText()+"\"");
			//列表中的标题查看
			UiObject listview = Constant.GetObject(Constant.LISTVIEW, 0);
			for(int i=2;i<listview.getChildCount()-1;i++){
				UiObject title = ArticleUI.GetListViewContent(i, TITLE);
				Constant.WriteLog(Constant.info, "第"+i+"条的标题为      "+title.getText());
				//列表中的日期
				UiObject date = ArticleUI.GetListViewContent(i, CHANNEL);
				Constant.WriteLog(Constant.info, "第"+i+"条的时间为      "+date.getText());
				
				if(!CompareTime(lastDate, date.getText())){
					Constant.WriteLog(Constant.fail, "专题"+topicTitle.getText()+"中文章列表时间排列不正确");
					flag = false;
				}
				lastDate = date.getText();
				
			}
			
			if(flag){
				Constant.WriteLog(Constant.fail, "专题"+topicTitle.getText()+"中文章列表时间排列正确");
			}

		}else{
			Constant.WriteLog(Constant.fail, "当前页面非立知首页");

		}

	}




	/**
	 * 文章列表item的点击
	 * @param index
	 */
	public String  ArticalItem(int index){
		try {
			UiObject article = Constant.GetTextObject(Constant.article);
			article.clickAndWaitForNewWindow();
			UiObject textView = ArticleUI.GetListViewContent(index,TITLE);
			if(textView!=null){
				String title = textView.getText();
				Constant.WriteLog(Constant.info, "所点击的文章标题为"+textView.getText());
				textView.clickAndWaitForNewWindow();
				sleep(2000);
				return title;
			}else{
				Constant.WriteLog(Constant.fail,"未找到文章标题");
			}
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

		return "";
	}



	/**
	 * 文章全部列表UI验证
	 * @param 
	 * @return
	 */
	public void ArticleUICheck() throws UiObjectNotFoundException{

		String lastDate = "今日";
		boolean flag = true;
		//这里先判断专题是否在全部处
		UiObject article = Constant.GetTextObject(Constant.article); 
		if(article!=null){
			article.click();
			UiScrollable topicObject = Constant.GetScrollableObject(Constant.VIEWPAGER);
			if(topicObject!=null){
				topicObject.setAsHorizontalList();
				UiObject selectedTV =ArticleUI.GetTopicTitle();;
				while (selectedTV==null||!(selectedTV.getText().equals(Constant.all))){
					topicObject.scrollBackward(15);
				}
				//列表中的标题查看
				UiObject listview = Constant.GetObject(Constant.LISTVIEW, 0);
				for(int i=2;i<listview.getChildCount()-1;i++){
					UiObject title = ArticleUI.GetListViewContent(i, TITLE);
					Constant.WriteLog(Constant.info, "第"+i+"条的标题为      "+title.getText());
					//列表中的所在频道
					UiObject Channel = ArticleUI.GetListViewContent(i, CHANNEL);
					Constant.WriteLog(Constant.info, "第"+i+"条的频道为      "+Channel.getText());
					//列表中的日期
					UiObject date = ArticleUI.GetListViewContent(i, DATE);
					Constant.WriteLog(Constant.info, "第"+i+"条的时间为      "+date.getText());
					
					if(!CompareTime(lastDate, date.getText())){
						Constant.WriteLog(Constant.fail,"专题“全部”中文章列表时间排列不正确");
						flag = false;
					}
					lastDate = date.getText();
				}
				if(flag){
					Constant.WriteLog(Constant.fail, "专题“全部”中文章列表时间排列正确");
				}
			}
		}else{
			Constant.WriteLog(Constant.fail, "未找到文章按钮");
		}
	}
	
	/**
	 * 比较两个时间
	 * @param lastDate  10/11
	 * @param nextDate  11/12
	 * @return
	 */
	public boolean CompareTime(String lastDate,String nextDate){
		
		
		String lastDateArray[] = FormatDate(lastDate);
		String nextDateArray[] = FormatDate(nextDate);
		if(lastDateArray.length<nextDateArray.length){
			return true;
		}else{
			if(Integer.valueOf(lastDateArray[0]).intValue()>Integer.valueOf(nextDateArray[0]).intValue()){
				return true;
			}else if(Integer.valueOf(lastDateArray[0]).intValue()==Integer.valueOf(nextDateArray[0]).intValue()){
				if(Integer.valueOf(lastDateArray[0]).intValue()>=Integer.valueOf(nextDateArray[0]).intValue()){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
	
		}
	}
	
	/**
	 * 时间的格式化
	 * @param date
	 * @return
	 */
	public String[] FormatDate(String date){
		
		if(date.equals("今日")){
			SimpleDateFormat df = new SimpleDateFormat("MM/dd");//设置日期格式
			date = df.format(new Date());
		}
		String array[] = date.split("/");
		return array;
	}
	


	/**
	 * 收藏文章
	 */
	public void CollectArticle(int index ){
		try {
			for(int i=0;i<2;i++){
				if(i==1){
					//再次进行点赞操作
					Constant.WriteLog(Constant.info, "再次进行收藏/取消收藏操作");
				}
				String title = ArticalItem(index);
				boolean isCollect = false;
				//找到更多按钮
				UiObject more = Constant.GetObject(Constant.IMAGEVIEW, 2);
				if(more!=null){
					more.clickAndWaitForNewWindow();
					//判断当前为取消收藏还是收藏按钮
					UiObject collectArticle =  Constant.GetObject(Constant.TEXTVIEW, 0);
					if(collectArticle.getText().equals(Constant.cancelCollect)){
						isCollect = true;
					}else{
						isCollect = false;
					}
					collectArticle.click();

					UiObject loginTip = Constant.GetTextObject(Constant.noLoginTip); 
					if(loginTip!=null){
						Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
						DialogLoginOn();
						CollectArticle(index);
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

						UiObject my = Constant.GetTextObject(Constant.my) ;
						my.click();
						UiObject myColect = Constant.GetTextObject(Constant.myColect); 
						myColect.click();
						if(isCollect){

							UiObject textview = Constant.GetTextObject(title);
							if(textview!=null){
								Constant.WriteLog(Constant.info, textview.getText());
								Constant.WriteLog(Constant.fail, "文章取消收藏失败");
							}else{
								Constant.WriteLog(Constant.info, "文章已取消收藏");
							}

						}else{

							UiObject textview = Constant.GetTextObject(title);
							if(textview!=null){
								Constant.WriteLog(Constant.info, "文章收藏成功");
							}else{
								Constant.WriteLog(Constant.fail, "文章收藏失败");
							}
						}
					}

				}
			}



		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}


	/**
	 * 搜索界面UI检查
	 * @throws UiObjectNotFoundException
	 */
	public void SearchArticleUICheck() throws UiObjectNotFoundException{

		UiObject article =Constant.GetTextObject(Constant.article);
		article.click();
		/**
		 * 点击搜索按钮
		 */
		UiObject searchImage=Constant.GetObject(Constant.IMAGEVIEW, 0);
		searchImage.clickAndWaitForNewWindow();		
		UiObject searchEdit = Constant.GetObject(Constant.EDITTEXT, 0);
		if(searchEdit!=null){
			Constant.WriteLog(Constant.info, "界面存在有搜索框 ");
			UiObject hotSearch = Constant.GetTextObject(Constant.hotSearch);
			if(hotSearch!=null){
				Constant.WriteLog(Constant.info, "界面存在有热门搜索");
				UiObject gridView = Constant.GetObject(Constant.GRIDVIEW, 0);
				if(gridView!=null){
					Constant.WriteLog(Constant.info, "热门搜索的关键字分别为:");
					for(int i=0;i<gridView.getChildCount();i++){
						UiObject textView = Constant.GetObjectChild(gridView,Constant.TEXTVIEW,i);
						Constant.WriteLog(Constant.info,textView.getText());
					}
					UiObject historySearch = Constant.GetTextObject(Constant.historySearch);
					if(historySearch!=null){
						Constant.WriteLog(Constant.info, "界面存在有历史搜索");
						UiObject clearSearch =  Constant.GetTextObject(Constant.clearSearch);
						if(clearSearch!=null){
							Constant.WriteLog(Constant.info, "界面存在有清除历史搜索");
						}else{
							Constant.WriteLog(Constant.fail, "界面不存在有清除历史搜索");
						}
					}else{
						Constant.WriteLog(Constant.fail, "界面不存在有历史搜索");
					}
				}else{
					Constant.WriteLog(Constant.fail, "界面不存在有热门搜索");
				}

			}
		}else{
			Constant.WriteLog(Constant.fail, "界面不存在有搜索框");
		}

		//点击返回按钮
		UiObject back=Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();	
		}

	}


	/**

	 * 文章搜索

	 * @param 

	 * @return

	 */
	public void SearchArticle(){

		//找到更多按钮

		try {
			//	String str = "";
			UiObject article = Constant.GetTextObject(Constant.article); 
			article.click();
			//点击搜索按钮
			UiObject searchImage=Constant.GetObject(Constant.IMAGEVIEW, 0);
			searchImage.clickAndWaitForNewWindow();	
			if(HotSearch()){
				if(HistorySearch()){
					//点击清除搜索历史查看是否还存在listview
					ClearSearch();
				}
			}

			//点击返回按钮
			UiObject back=Constant.GetObject(Constant.IMAGEVIEW, 0);
			if(back!=null){
				back.clickAndWaitForNewWindow();	
			}
		}catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}

	/**
	 * 	查找gridview 通过点击gridview中的内容 而不是查找文字的方式
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public boolean HotSearch() throws UiObjectNotFoundException{
		UiObject gridView = Constant.GetObject(Constant.GRIDVIEW, 0);

		if(gridView!=null){
			Constant.WriteLog(Constant.info, "进入文章搜索界面");
			int count  = gridView.getChildCount();

			for(int i=0;i<count;i++){

				UiObject textView = Constant.GetObjectChild(gridView, Constant.TEXTVIEW, i);

				String str = textView.getText();

				Constant.WriteLog(Constant.info, "点击\""+str+"\"进行文章搜索");

				textView.clickAndWaitForNewWindow();

				//查看是否进入到搜索文章列表
				if(!gridView.exists()){
					UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);
					UiObject searchArticle = ArticleUI.GetMatchText(gridView, str);
					if((searchArticle==null)&&listView.getChildCount()>0){
						searchArticle = Constant.GetObjectChild(listView, Constant.TEXTVIEW, 0);
					}
					if(searchArticle!=null){
						Constant.WriteLog(Constant.info, "成功搜索带有\""+str+"\"关键字的文章");
						Constant.WriteLog(Constant.info, "点击阅读文章"+searchArticle.getText());
						searchArticle.clickAndWaitForNewWindow();
						UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
						if(back!=null){
							back.click();
						}else{
							Constant.WriteLog(Constant.fail, "未进入文章阅读界面");
						}
					}else{
						Constant.WriteLog(Constant.fail, "未搜索到\""+str+"\"关键字的文章");
						return false;
					}
					//点击清除按钮
					UiObject clearButton = Constant.GetObject(Constant.IMAGEBUTTON, 0);
					clearButton.click();
					//查看listview中是否有刚搜索的字

					listView = Constant.GetObject(Constant.LISTVIEW, 0);

					UiObject  searchKey = ArticleUI.GetMatchText(listView, str);

					if(searchKey!=null){

						Constant.WriteLog(Constant.info, "历史搜索中新增\""+str+"\"关键字的搜索");

					}else{

						Constant.WriteLog(Constant.fail, "历史搜索中未新增\""+str+"\"关键字的搜索");
						return false;
					}
				}else{
					Constant.WriteLog(Constant.fail, "未进入到搜索结果列表中");
					return false;
				}



			}
			return true;
		}else{
			Constant.WriteLog(Constant.fail, "未进入文章搜索界面");
			return false;
		}


	}

	/**
	 * 历史搜索
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public boolean HistorySearch() throws UiObjectNotFoundException{
		//通过点击搜索历史的关键字进行查找
		String str = "";
		UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);
		if(listView!=null){

			UiObject  searchKey = Constant.GetObjectChild(listView, Constant.TEXTVIEW, 1);
			if(searchKey!=null){
				str = searchKey.getText();
				Constant.WriteLog(Constant.info, "点击搜索列表中的关键字\""+str+"\"");
				searchKey.click();
				UiObject gridView = Constant.GetObject(Constant.GRIDVIEW, 0);
				if(gridView==null){
					listView = Constant.GetObject(Constant.LISTVIEW, 0);
					UiObject searchArticle =ArticleUI.GetMatchText(listView, str);
					if(searchArticle!=null){
						Constant.WriteLog(Constant.info, "成功搜索带有\""+str+"\"关键字的文章");
						Constant.WriteLog(Constant.info, "点击阅读文章"+searchArticle.getText());
						searchArticle.clickAndWaitForNewWindow();
						//直接返回
						UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
						if(back!=null){
							back.click();
						}
						//点击清除按钮
						UiObject clearButton = Constant.GetObject(Constant.IMAGEBUTTON, 0);
						clearButton.click();
						UiObject firstSearch = Constant.GetObjectChild(listView, Constant.TEXTVIEW, 0);
						if(firstSearch.getText().equals(str)){
							Constant.WriteLog(Constant.info, "历史搜索中关键字按搜索次数排序");
							return true;
						}else{

							Constant.WriteLog(Constant.fail, "历史搜索中关键字未按搜索次数排序"+str);
							return false;
						}	

					}else{


						Constant.WriteLog(Constant.fail, "未搜索到\""+str+"\"关键字的文章");
						return false;
					}
				}else{
					Constant.WriteLog(Constant.fail, "未进入到文章搜索结果列表界面");
					return false;
				}

			}else{
				return false;
			}

		}else{
			Constant.WriteLog(Constant.fail, "历史搜索无记录");
			return false;
		}
	}


	/**
	 * 清除搜索历史
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public boolean  ClearSearch() throws UiObjectNotFoundException{

		Constant.WriteLog(Constant.info, "点击清除搜索历史");

		UiObject clearSearch = Constant.GetTextObject(Constant.clearSearch);
		if(clearSearch!=null){
			clearSearch.click();

			UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);

			if(listView!=null){

				Constant.WriteLog(Constant.fail, "清除搜索历史未成功");
				return true;
			}else{

				Constant.WriteLog(Constant.info, "清除搜索历史成功");
				return false;
			}
		}else{
			Constant.WriteLog(Constant.info, "未找到清除历史搜索按钮");
			return false;
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
				UiObject praise= Constant.GetObject(Constant.IMAGEBUTTON, 0);
				praise.click();
				//判断点赞后是否跳转到了登录界面
				UiObject loginTip = Constant.GetTextObject(Constant.noLoginTip); 
				if(loginTip!=null){
					Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
					DialogLoginOn();
					ArticlePraise(index);
					break;
				}else{
					int praiseAfterNum = getAriticlePraise();
					Constant.WriteLog(Constant.info, "点击后点赞数为："+praiseAfterNum);

					if(praiseAfterNum-praiseBeforeNum==1){
						Constant.WriteLog(Constant.info, "操作为点赞，点赞数加1");
					}else if(praiseBeforeNum-praiseAfterNum==1){
						Constant.WriteLog(Constant.info, "操作为取消点赞，点赞数减1");
					}else{
						Constant.WriteLog(Constant.fail, "点赞操作失败");
					}
				}
				if(i==1){
					//找到返回按钮
					UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
					if(back!=null){
						back.click();
					}
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
			UiObject praiseTextView = Constant.GetObject(Constant.TEXTVIEW, 0);
			praiseNum = Integer.valueOf(praiseTextView.getText()).intValue();

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
		UiObject comment= Constant.GetObject(Constant.IMAGEBUTTON, 1);
		UiObject commentNumTv = Constant.GetObject(Constant.TEXTVIEW, 1);
		if(commentNumTv!=null){
			Constant.WriteLog(Constant.info, "文章的评论数为"+commentNumTv.getText());
		}
		comment.clickAndWaitForNewWindow();
		//点击评论按钮
		UiObject commentTextView = Constant.GetTextObject(Constant.comment);
		commentTextView.clickAndWaitForNewWindow();

		Constant.WriteLog(Constant.info, "不填写内容,发表空的评论");
		//点击发表按钮
		UiObject publicTextView = Constant.GetTextObject(Constant.pubic);
		publicTextView.click();

		if(commentTextView.exists()){
			Constant.WriteLog(Constant.fail, "空评论发表成功");
			return ;
		}else{
			Constant.WriteLog(Constant.info, "空评论发表失败");
		}

		Constant.WriteLog(Constant.info, "填写内容超过140个字符");
		UiObject commentEditText = Constant.GetObject(Constant.EDITTEXT, 0);
		commentEditText.setText(Constant.longString);
		if(!publicTextView.isClickable()){
			Constant.WriteLog(Constant.info, "填写内容超过140个字符时，发表按钮变灰");
		}else{
			Constant.WriteLog(Constant.info, "填写内容超过140个字符时，发表按钮未变成灰色");
		}
		Constant.WriteLog(Constant.info, "填写内容当前的系统时间");
		commentEditText.clearTextField();
		commentEditText.clearTextField();
		commentEditText.clearTextField();
		String nowTime = Constant.GetSystemTime();
		commentEditText.setText(nowTime);

		//获取输入内容后，允许字符剩余多少
		UiObject limitNum = Constant.GetObject(Constant.TEXTVIEW, 2);
		int changNum = Integer.valueOf(limitNum.getText()).intValue();
		Constant.WriteLog(Constant.info, "剩余字数为"+changNum);
		if(TOTALNUM-changNum==nowTime.length()){
			Constant.WriteLog(Constant.info, "符合字数变化规则");
		}else{
			Constant.WriteLog(Constant.fail, "不符合字数变化规则");
			return ;
		}
		//点击发表
		publicTextView.clickAndWaitForNewWindow();
		sleep(2000);
		UiObject commentDetail = ArticleUI.GetCommentDetail(1);
		String content = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);		
		Constant.WriteLog(Constant.info, "列表第一条评论为"+content); 
		if(content.equals(nowTime)){
			Constant.WriteLog(Constant.info, "与发表的评论内容相同，评论发表成功"); 
			RefreshListView();
			content = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);
			if(content.equals(nowTime)){
				Constant.WriteLog(Constant.info, "评论列表刷新前后一致"); 
			}else{
				Constant.WriteLog(Constant.fail, "评论列表刷新前后不一致"); 
			}
			DeleteComment(commentDetail);
		}else{
			Constant.WriteLog(Constant.fail, "与发表的评论内容不相同，评论发表失败");
		}

		//返回至主界面
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
			back.click();
		}
	}

	
	/**
	 * 删除评论
	 * @param object
	 * @throws UiObjectNotFoundException
	 */
	public void DeleteComment(UiObject object) throws UiObjectNotFoundException{
		String commentContent = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);
		for(int i=0;i<2;i++){
			object.longClick();
			UiObject delete = Constant.GetTextObject(Constant.delete);
			if(delete!=null){
				delete.click();
				if(i==0){
					UiObject cancel = Constant.GetTextObject(Constant.cancel);
					if(cancel!=null){
						cancel.click();
						if(commentContent.equals(ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT))){
							Constant.WriteLog(Constant.info, "取消删除评论成功");
						}else{
							Constant.WriteLog(Constant.fail, "取消删除评论失败");
						}
					}
				}else{
					UiObject confirm = Constant.GetTextObject(Constant.confirm);
					if(confirm!=null){
						confirm.click();
						if(commentContent.equals(ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT))){
							Constant.WriteLog(Constant.fail, "删除评论失败");
						}else{
							Constant.WriteLog(Constant.info, "删除评论成功");
						}
					}
				}
				
			}else{
				Constant.WriteLog(Constant.fail, "未找到删除按钮");
			}
		}
		
		
	}


	/**
	 * 获取评论个数
	 * @param 
	 * @return
	 */
	public int GetArticleComment() throws UiObjectNotFoundException{
		int commentNum =0 ;
		UiObject commentTextView = Constant.GetObject(Constant.TEXTVIEW, 1);
		commentNum = Integer.valueOf(commentTextView.getText()).intValue();
		return commentNum;
	}


	/**
	 * 文章评论列表UI查看
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleCommentUICheck(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject commentBt = Constant.GetObject(Constant.IMAGEBUTTON, 1);
		if(commentBt!=null){
			commentBt.clickAndWaitForNewWindow();
			ArticleUI.GetCommentListUI();
			Back();
		}else{
			Constant.WriteLog(Constant.fail, "未找到评论按钮");
		}
		Back();
	}

	
	
	/**
	 * 文章评论的复制功能
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleCommentCopy(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject commentBt = Constant.GetObject(Constant.IMAGEBUTTON, 1);
		if(commentBt!=null){
			commentBt.clickAndWaitForNewWindow();
			String commentContent = ArticleUI.GetCommentContent(index, Constant.COMMENTCONTENT);
			UiObject commentUser = ArticleUI.GetListViewContent(index, 0);
			if(commentUser!=null){
				commentUser.longClick();
				UiObject copy = Constant.GetTextObject(Constant.copy);
				if(copy!=null){
					copy.click();
					Constant.WriteLog(Constant.info, "复制评论内容");
					UiObject commentTV = Constant.GetTextObject(Constant.comment);
					commentTV.clickAndWaitForNewWindow();
					UiObject editText = Constant.GetObject(Constant.EDITTEXT, 0);
					editText.setText(Constant.commentContent);
					editText.longClickTopLeft();
					UiObject pasteObject = Constant.GetDescObject(Constant.paste);
					if(pasteObject!=null){
						pasteObject.click();
						if(commentContent.equals(editText.getText())){
							Constant.WriteLog(Constant.info, "复制评论内容正确");
						}else{
							Constant.WriteLog(Constant.fail, "复制评论内容不正确"+commentContent+","+editText.getText());
						}
						Back();
					}else{
						Constant.WriteLog(Constant.fail, "未找到粘贴按钮");
					}
					
				}
			}
			
			Back();
			
		}else{
			Constant.WriteLog(Constant.fail, "未找到评论按钮");
		}
		Back();
	}
	
	/**
	 * 文章举报功能
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleCommentReport(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject commentBt = Constant.GetObject(Constant.IMAGEBUTTON, 1);
		if(commentBt!=null){
			commentBt.clickAndWaitForNewWindow();
			String commentContent = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);
			UiObject reportedName = ArticleUI.GetListViewContent(index, 0);
			if(reportedName!=null){
				reportedName.longClick();
				UiObject report = Constant.GetTextObject(Constant.report);
				if(report!=null){
					report.click();
					Constant.WriteLog(Constant.info, "举报评论,被举报人:"+reportedName.getText()+",举报内容:"+commentContent);
				}else{
					Constant.WriteLog(Constant.fail, "未找到举报按钮");
				}
			}
			Back();
			
		}else{
			Constant.WriteLog(Constant.fail, "未找到评论按钮");
		}
		Back();
	}
	
	/**
	 * 文章评论点赞取消点赞
	 * @param 
	 * @return
	 */
	public void ArticleCommentPraise(int index) throws UiObjectNotFoundException {

		ArticalItem(index);
		IsCommentEmpty(index);
		//找到评论按钮
		UiObject comment= Constant.GetObject(Constant.IMAGEBUTTON, 1);
		comment.clickAndWaitForNewWindow();
		Constant.WriteLog(Constant.info, "进入文章评论页面,对第一条评论进行点赞操作");
		for(int i=0;i<2;i++){
			if(i==1){
				Constant.WriteLog(Constant.info, "再次进行点赞操作");
			}
			UiObject commentDetail = ArticleUI.GetCommentDetail( 1);
			UiObject praiseNum = ArticleUI.GetPraiseOrCommentNum(commentDetail, 0);
			int beginPraiseNum = Integer.valueOf(praiseNum.getText()).intValue();
			Constant.WriteLog(Constant.info, "点击前的点赞数为"+beginPraiseNum);
			praiseNum.click();
			//判断点赞后是否跳转到了登录界面
			UiObject loginTip = Constant.GetTextObject(Constant.noLoginTip); 
			if(loginTip!=null){
				Constant.WriteLog(Constant.info, "当前为未登录状态，进行登录操作");
				DialogLoginOn();
				ArticleCommentPraise(index);
				break;
			}else{
				int endPraiseNum = Integer.valueOf(praiseNum.getText()).intValue();
				Constant.WriteLog(Constant.info, "点击后的点赞数为"+endPraiseNum);	
				if(endPraiseNum-beginPraiseNum==1){
					Constant.WriteLog(Constant.info, "点赞数加1，为点赞操作");

				}else if(endPraiseNum-beginPraiseNum==-1){
					Constant.WriteLog(Constant.info, "点赞数减1，为取消点赞操作");
				}else {
					Constant.WriteLog(Constant.fail, "点赞操作失败");
				}

				//进行刷新操作
				RefreshListView();
				int refreshPraiseNum = Integer.valueOf(praiseNum.getText()).intValue();
				Constant.WriteLog(Constant.info, "刷新后的点赞数为"+refreshPraiseNum);
				if(refreshPraiseNum==endPraiseNum){
					Constant.WriteLog(Constant.info, "刷新后点赞数不变");
				}else{
					Constant.WriteLog(Constant.fail, "刷新后点赞数不一致");
				}

			}
			if(i==1){
				//找到主界面
				UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
				if(back!=null){
					back.click();
					back.click();
				}

			}
		}
	}

	/**
	 * 判断是否评论为空
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void IsCommentEmpty(int index) throws UiObjectNotFoundException{
		UiObject commentNum  = Constant.GetObject(Constant.TEXTVIEW, 1);
		if(commentNum!=null){
			Constant.WriteLog(Constant.info, commentNum.getText());
		}
		int repeat = 0;
		//首先判断文章的评论数是否为0
		while(commentNum.getText().equals("0")){
			repeat = repeat+1;
			Back();
			ArticalItem(index+repeat);
		}
	}


	/**
	 * 评论文章的评论功能
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleCommentOtherComment(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		IsCommentEmpty(index);
		//找到评论按钮
		UiObject comment= Constant.GetObject(Constant.IMAGEBUTTON, 1);
		comment.clickAndWaitForNewWindow();
		Constant.WriteLog(Constant.info, "进入文章评论页面,对第一条评论进行评论操作");
		//找到评论按钮
		UiObject commentDetail =ArticleUI.GetCommentDetail(Constant.NUM);
		UiObject commentOther = ArticleUI.GetPraiseOrCommentNum(commentDetail, 1);
		//获取被评论人的姓名
		String commentedName = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMNETUSERNAME);
		String commentedContent =ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);
		Constant.WriteLog(Constant.info, "对姓名为\""+commentedName+"\",内容为\""+commentedContent+"\"的评论进行评论");
		commentOther.clickAndWaitForNewWindow();

		UiObject comparedUI = Constant.GetContainTextObject(commentedContent);
		if(comparedUI!=null){
			comparedUI = Constant.GetContainTextObject(commentedName);
			if(comparedUI!=null){
				Constant.WriteLog(Constant.info, "引用的评论内容以及评论人正确");
			}else{
				Constant.WriteLog(Constant.fail, "引用的评论人不正确");
			}
		}else{
			Constant.WriteLog(Constant.fail, "引用的评论内容不正确");
		}

		//评论相应内容点击发表
		UiObject editText = Constant.GetObject(Constant.EDITTEXT, 0);
		nowTime = Constant.GetSystemTime();
		editText.setText(nowTime);
		UiObject publicButton = Constant.GetTextObject(Constant.pubic);
		publicButton.click();
		if(IsCommentOtherCommentSuccess(commentedName, commentedContent, Constant.pubic)){
			RefreshListView();
			IsCommentOtherCommentSuccess(commentedName, commentedContent, "刷新");
		}

		//找到主界面
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
			back.click();
		}



	}


	/**
	 * 判断刷新以及发表评论后，评论列表中是否显示正确
	 * @param commentedName
	 * @param commentedContent
	 * @param Mehod
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public boolean IsCommentOtherCommentSuccess(String commentedName,String commentedContent,String Mehod) throws UiObjectNotFoundException{
		//比较第一条内容中评论内容是否为刚回复的内容
		String commentContent = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTCONTENT);
	
			if(nowTime.equals(commentContent)){
				Constant.WriteLog(Constant.info, Mehod+"评论后，评论内容与输入时一致");
				String newCommentedName = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTEDUSERNAME);
				if(commentedName.equals(newCommentedName)){
					String newCommentedContent = ArticleUI.GetCommentContent(Constant.NUM, Constant.COMMENTEDCONTENT);
					if(newCommentedContent.equals(commentedContent)){
						Constant.WriteLog(Constant.info, Mehod+"评论后，引用评论内容正确");
						return true;
					}else{
						Constant.WriteLog(Constant.info, newCommentedContent+"        "+commentedContent);
						Constant.WriteLog(Constant.fail, Mehod+"评论后，引用评论内容不正确");
						return false;
					}
				}else{
					Constant.WriteLog(Constant.info, newCommentedName+"        "+commentedName);
					Constant.WriteLog(Constant.fail, Mehod+"评论后，引用评论名字不正确");
					return false;
				}
			}else{
				Constant.WriteLog(Constant.fail, Mehod+"评论后，评论内容与输入时不一致");
				return false;
			}
		


	}



	/**
	 * 文章分享UI检测
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleShareUICheck(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject shareImageView = Constant.GetObject(Constant.IMAGEVIEW, 1);
		if(shareImageView!=null){
			shareImageView.clickAndWaitForNewWindow();
			UiObject linearLayout = ArticleUI.GetArticleShareUI();
			if(linearLayout!=null){
				if(linearLayout.getChildCount()==3){
					Constant.WriteLog(Constant.info,"检测到有微博（新浪）、微信、朋友圈三个分享按钮");
				}else{
					Constant.WriteLog(Constant.fail,"未检测到有微博（新浪）、微信、朋友圈三个分享按钮");
				}
				//返回到文章列表
				test.uidevice.pressBack();
				test.uidevice.pressBack();
			}
		}else{
			Constant.WriteLog(Constant.fail,"未找到分享按钮");
		}
	}


	/**
	 * 分享到朋友圈
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleShareCircle(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject shareImageView = Constant.GetObject(Constant.IMAGEVIEW, 1);
		if(shareImageView!=null){
			shareImageView.clickAndWaitForNewWindow();
			UiObject shareCircle = Constant.GetObject(Constant.IMAGEVIEW, 2);
			if(shareCircle!=null){
				Constant.WriteLog(Constant.info,"点击分享至朋友圈");
				shareCircle.clickAndWaitForNewWindow();	
				if(ShareContent(CIRCLE)){
					Constant.WriteLog(Constant.info,"分享成功");
					Constant.OpenApplication(Constant.lizhi);
				}else{
					UiObject loginTv = Constant.GetTextObject(Constant.login);
					if(loginTv!=null){
						Constant.WriteLog(Constant.fail,"微信未登录，请登录后再进行该测试");
						UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
						if(back!=null){
							back.click();
						}
					}else{
						Constant.WriteLog(Constant.fail,"微信未安装，请安装微信");
					}
				}
			}

		}else{
			Constant.WriteLog(Constant.fail,"未找到分享按钮");
		}
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
		}
	}

	/**
	 * 分享到微信
	 * @throws UiObjectNotFoundException
	 */

	public void ArticleShareWeiXin(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject shareImageView =  Constant.GetObject(Constant.IMAGEVIEW, 1);
		if(shareImageView!=null){
			shareImageView.clickAndWaitForNewWindow();
			UiObject shareWeiXin =  Constant.GetObject(Constant.IMAGEVIEW, 1);
			if(shareWeiXin!=null){
				Constant.WriteLog(Constant.info,"点击分享至微信");
				shareWeiXin.clickAndWaitForNewWindow();	
				UiObject shareTV = Constant.GetTextObject(Constant.createNewTalk);
				if(shareTV!=null){
					shareTV.click();	
					UiObject textView =  Constant.GetObject(Constant.TEXTVIEW, 4);
					if(textView!=null){
						friendsName = textView.getText();
						Constant.WriteLog(Constant.info,"发送文章至好友"+textView.getText());
						textView.click();
						UiObject confirmTV = Constant.GetContainTextObject(Constant.confirm);
						if(confirmTV!=null){
							confirmTV.click();
							if(ShareContent(WEIXIN)){
								Constant.WriteLog(Constant.info,"分享成功");
								Constant.OpenApplication(Constant.lizhi);
							}else{
								Constant.WriteLog(Constant.fail,"分享失败");
							}
						}
					}else{
						Constant.WriteLog(Constant.fail,"未找到好友发送");
						//找到主界面
						UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
						if(back!=null){
							back.click();
							back.click();
						}

					}

				}else{
					UiObject loginTv = Constant.GetTextObject(Constant.login); 
					if(loginTv!=null){
						Constant.WriteLog(Constant.fail,"微信未登录，请登录后再进行该测试");
						Back();
					}
					//微信未登录的情况
				}
			}

		}else{
			Constant.WriteLog(Constant.fail,"未找到分享按钮");
		}
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
		}

	}

	/**
	 * 分享到新浪
	 * @param index
	 * @throws UiObjectNotFoundException
	 */
	public void ArticleShareSina(int index) throws UiObjectNotFoundException{
		ArticalItem(index);
		UiObject shareImageView =Constant.GetObject(Constant.IMAGEVIEW, 1);
		if(shareImageView!=null){
			shareImageView.clickAndWaitForNewWindow();
			UiObject shareSina = Constant.GetObject(Constant.IMAGEVIEW, 0);
			if(shareSina!=null){
				shareSina.clickAndWaitForNewWindow();
				UiObject confirmButton = Constant.GetObject(Constant.BUTTON, 1);
				if(confirmButton!=null){
					confirmButton.click();
					sleep(2000);
				}else{
					Constant.WriteLog(Constant.fail,"当前不支持非客户端的微博分享，请安装微博后进行验证");
					UiObject closeBt = Constant.GetObject(Constant.BUTTON, 0);
					if(closeBt!=null){
						closeBt.click();
					}

				}
			}
		}else{
			Constant.WriteLog(Constant.fail,"未找到分享按钮");
		}
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
		}
	}



	/**
	 * 反向内容
	 * @param type  cicrle = 2朋友圈     Weixin = 1 微信  
 	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public boolean  ShareContent(int type) throws UiObjectNotFoundException{
		UiObject shareTV = Constant.GetTextObject(Constant.share);
		if(shareTV!=null){
			//微信已登录的情况
			UiObject shareContent = Constant.GetObject(Constant.EDITTEXT, 0);
			nowTime = Constant.GetSystemTime();
			shareContent.setText(nowTime);
			UiObject title = Constant.GetObject(Constant.TEXTVIEW, 0);
			shareTitle = title.getText();
			shareTV.click();
			sleep(2000);
			switch (type) {
			case 0:
				
				break;
			case 1:
				UiObject send = Constant.GetContainTextObject(Constant.send);
				if(send!=null){
					Constant.WriteLog(Constant.info,"发送成功,查看相应聊天是否有内容");
					UiObject stayWeixin = Constant.GetContainTextObject(Constant.weiXin);
					if(stayWeixin!=null){
						stayWeixin.click();
						sleep(2000);
						if(OtherApplications.IsWeiXinShareSuccess(shareTitle, nowTime,friendsName)){
							return true;
						}else{
							Constant.OpenApplication(Constant.lizhi);
							return false;
						}
					}
				}
				
				
				break;
			case 2:
				Constant.OpenApplication(Constant.weiXin);
				if(OtherApplications.IsCircleShareSuccess(shareTitle, nowTime)){
					return true;
				}else{
					Constant.OpenApplication(Constant.lizhi);
					return false;
				}
			}
			
			return true;
		}else{
			switch (type) {
			case 0:
				
				break;
			case 1:
				Constant.WriteLog(Constant.fail,"微信未安装，请安装微信");
				return false;
			case 2:
				Constant.WriteLog(Constant.fail,"微信未安装，请安装微信");
				return false;
			}
			
			return false;
		}
	}


	/**
	 * 下拉刷新
	 * @throws UiObjectNotFoundException
	 */
	public  void RefreshListView() throws UiObjectNotFoundException{

		UiObject ListView  = Constant.GetObject(Constant.LISTVIEW, 0);
		if(ListView!=null){
			ListView.swipeDown(30);
			sleep(5000);
		}	
	}


	/**
	 * 退出应用
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void CloseApplication(String application) throws UiObjectNotFoundException, RemoteException{
		test.uidevice.pressHome();
		test.uidevice.pressRecentApps();
		UiObject liZhiTV = Constant.GetTextObject(application);
		liZhiTV.swipeLeft(10);
		test.uidevice.pressHome();
	}








}
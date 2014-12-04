package com.cvte.lizhiUI;


import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.cvte.lizhi.Constant;

public class ArticleUI extends UiAutomatorTestCase{
	/**
	 * 
	 * @return 返回的文章列表的内容
	 * @throws UiObjectNotFoundException 
	 */
	public static UiObject GetListViewContent(int index,int Type) throws UiObjectNotFoundException{
		UiObject listview = Constant.GetObject(Constant.LISTVIEW, 0);
		if(listview!=null){
			UiObject listViewItem = listview.getChild(new UiSelector().clickable(true).index(index));
			if(listViewItem.exists()){
				UiObject textView = Constant.GetObjectChild(listViewItem, Constant.TEXTVIEW, Type);
				if(textView.exists()){
					return textView;
				}else{
					return null;
				}
			}else{
				return null;
			}	
		}else{
			return null;
		}

	}
	
	
	public static UiObject GetMatchText(UiObject object,String matchStr) throws UiObjectNotFoundException{
		UiObject textView = object.getChild(new UiSelector().textContains(matchStr));
		if(textView.exists()){
			return textView;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取详细评论
	 * @param 
	 * @return
	 */
	public static UiObject GetCommentDetail(int index) throws UiObjectNotFoundException{
		UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);
		UiObject commentDetail = listView.getChild(new UiSelector().clickable(true).index(index));
		if(commentDetail.exists()){
			return commentDetail;
		}else{
			return null;
		}
		
	}
	
	/**
	 * 返回评论的点赞数
	 * @param 
	 * @return
	 */
	public static UiObject GetPraiseOrCommentNum(UiObject object,int index) throws UiObjectNotFoundException{
		UiObject praiseNum = object.getChild(new UiSelector().clickable(true).className(Constant.TEXTVIEW).instance(index));
		if(praiseNum.exists()){
			return praiseNum;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 获取文章评论内容
	 * @param object
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static UiObject GetCommentContent(UiObject object) throws UiObjectNotFoundException{
		UiObject commentedContentTV = null;
		if(object.getChildCount()==4){
			commentedContentTV =object.getChild(new UiSelector().index(1).className(android.widget.TextView.class.getName()).instance(1));
			
		}else{
			commentedContentTV =object.getChild(new UiSelector().index(2).className(android.widget.TextView.class.getName()).instance(1));
		}
		return commentedContentTV;
	}
	
	
	/**
	 * 获取文章分享的UI界面
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static UiObject GetArticleShareUI() throws UiObjectNotFoundException{
		UiCollection linearLayoutCollection = new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject linearLayout = linearLayoutCollection.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 1);
		if(linearLayout.exists()){
			return linearLayout;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取当前所在的专题
	 * @return
	 */
	public static UiObject GetTopicTitle(){
		UiObject topicTitle = new UiObject(new UiSelector().className(Constant.TEXTVIEW).selected(true));
		if(topicTitle.exists()){
			return topicTitle;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 获取评论列表UI
	 * @throws UiObjectNotFoundException
	 */
	public static void GetCommentListUI() throws UiObjectNotFoundException{
		UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);
		if(listView!=null){
			for(int i=1;i<listView.getChildCount()-1;i++){
				UiObject name = ArticleUI.GetListViewContent(i, 0);
				if(name!=null){
					UiObject postTimeTv = ArticleUI.GetListViewContent(i, 2);
					UiObject schoolTv = ArticleUI.GetListViewContent(i, 1);
					UiObject commentedNameTv = ArticleUI.GetListViewContent(i, 3);
					if(commentedNameTv.getText().startsWith("@")){
						Constant.WriteLog(Constant.info,"第"+i+"条评论为引用评论");
						Constant.WriteLog(Constant.info, "该评论的评论人为"+name.getText()+",发表评论时间为"+postTimeTv.getText()+",学校为"+schoolTv.getText());	
						String commentedName = commentedNameTv.getText().substring(1,commentedNameTv.getText().length()-1);
						Constant.WriteLog(Constant.info, "被引用的评论人为"+commentedName);
						UiObject commentedContentTv = ArticleUI.GetListViewContent(i, 4);
						Constant.WriteLog(Constant.info, "被引用的评论内容为"+commentedContentTv.getText());
						UiObject commentContentTv = ArticleUI.GetListViewContent(i, 5);
						Constant.WriteLog(Constant.info, "该评论的评论内容为"+commentContentTv.getText());
						UiObject commentPraiseTv = ArticleUI.GetListViewContent(i, 6);
						Constant.WriteLog(Constant.info, "该评论的点赞数为"+commentPraiseTv.getText());
					}else{
						Constant.WriteLog(Constant.info, "第"+i+"条评论为直接评论");	
						Constant.WriteLog(Constant.info, "该评论的评论人为"+name.getText()+",发表评论时间为"+postTimeTv.getText()+",学校为"+schoolTv.getText());	
						Constant.WriteLog(Constant.info, "评论内容为"+commentedNameTv.getText());	
						UiObject commentPraiseTv = ArticleUI.GetListViewContent(i, 4);
						Constant.WriteLog(Constant.info, "该评论的点赞数为"+commentPraiseTv.getText());
					}
				}
			}
		}else{
			UiObject emptyComment = Constant.GetObject(Constant.IMAGEVIEW, 1);
			if(emptyComment!=null){
				Constant.WriteLog(Constant.info, "当前评论数为0");
				Constant.TakeScreenShot("空评论");
			}else{
				Constant.WriteLog(Constant.fail, "查看评论列表失败");
			}
		}
	}
	
//	public static String GetCommentContent(int index) throws UiObjectNotFoundException{
//		UiObject commentedNameTv = ArticleUI.GetListViewContent(index, 3);
//		String commentContent ;
//		if(commentedNameTv.getText().startsWith("@")){
//			UiObject commentContentTv = ArticleUI.GetListViewContent(index, 5);
//			commentContent = commentContentTv.getText();
//			Constant.WriteLog(Constant.info, "获取评论内容为"+commentContentTv.getText());
//		}else{
//			commentContent = commentedNameTv.getText();
//			Constant.WriteLog(Constant.info, "获取评论内容为"+commentedNameTv.getText());	
//			
//		}
//		return commentContent;
//	}
	
	

	/**
	 * 获取评论内容    
	 * @param index   
	 * @param type    
	COMMNETUSERNAME = 0; 评论人
	COMMENTUSERSCHOOL = 1; 评论人学校
	COMMENTIME = 2;   评论时间
	COMMENTEDUSERNAME = 3; 被评论人
	COMMENTEDCONTENT = 4;被评论的内容
	COMMENTCONTENT = 5;评论内容
	COMMENTPRAISE = 6;评论的点赞数
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static String GetCommentContent(int index,int type) throws UiObjectNotFoundException{
		UiObject object = null;
		if(type<=2){
			object = ArticleUI.GetListViewContent(index, type);
			return object.getText();
		}
		if(IsQuoteComment(index)){
			if(type==Constant.COMMENTEDUSERNAME){
				UiObject commentedNameTv = ArticleUI.GetListViewContent(index, type);
				return  commentedNameTv.getText().substring(1,commentedNameTv.getText().length()-1);
			}else{
				object = ArticleUI.GetListViewContent(index, type);
				return object.getText();
			}
		}else{
			object = ArticleUI.GetListViewContent(index, type-2);
			return object.getText();
		}
	}
	
	
	/**
	 * 是否为引用评论
	 * @param index
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static Boolean IsQuoteComment(int index) throws UiObjectNotFoundException{
		UiObject commentedNameTv = ArticleUI.GetListViewContent(index, 3);
		if(commentedNameTv.getText().startsWith("@")){
			return true;
		}else{
			return false;
		}
	}

	
	
	

	

}
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
	public static UiObject GetAricleTitle(int index,int Type) throws UiObjectNotFoundException{
		UiObject listview = Constant.GetObject(Constant.LISTVIEW, 0);
		if(listview!=null){
			UiObject textView = listview.getChild(new UiSelector().className(Constant.TEXTVIEW).instance(3*index-Type));
			if(textView.exists()){
				return textView;
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
	public static UiObject GetCommentDetail(UiObject object,int index) throws UiObjectNotFoundException{
		UiObject commentDetail = object.getChild(new UiSelector().index(index));
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
		UiObject praiseNum = object.getChild(new UiSelector().index(object.getChildCount()-2)).getChild(new UiSelector().index(index));
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
	

	

	

}
package com.cvte.lizhiUI;

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
	
	
	public static UiObject GetArticleSearchItem(UiObject object,int index) throws UiObjectNotFoundException{
		
		UiObject textView = object.getChild(new UiSelector().className(android.widget.TextView.class.getName()).instance(index));
		if(textView.exists()){
			return textView;
		}else{
			return null;
		}
	}
	

	

	

}

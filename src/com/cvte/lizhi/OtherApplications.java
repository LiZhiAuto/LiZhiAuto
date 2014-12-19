package com.cvte.lizhi;



import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class OtherApplications extends UiAutomatorTestCase {
	
	
	
	/**
	 * 点击返回按钮
	 * @throws UiObjectNotFoundException
	 */
	public static void Back() throws UiObjectNotFoundException{
		UiObject back = Constant.GetObject(Constant.IMAGEVIEW, 0);
		if(back!=null){
			back.click();
		}
	}
	
	/**
	 * 判断朋友圈是否分享成功
	 * @param title
	 * @param content
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static Boolean IsCircleShareSuccess(String title,String content) throws UiObjectNotFoundException{
		UiObject find = Constant.GetTextObject("发现");
		if(find!=null){
			find.click();
			UiObject circle = Constant.GetTextObject("朋友圈");
			if(circle!=null){
				circle.clickAndWaitForNewWindow();
				UiObject shareTitle = Constant.GetContainTextObject(title);
				if(shareTitle!=null){
					UiObject shareContent = Constant.GetContainTextObject(content);
					if(shareContent!=null){
						Constant.WriteLog(Constant.info,"在朋友圈中检测到相应的分享内容");
						return true;
					}else{
						Constant.WriteLog(Constant.fail,"在朋友圈未查找到分享内容");
						return false;
					}
				}else{
					Constant.WriteLog(Constant.fail,"在朋友圈未查找到分享标题");
					return false;
				}
			}else{
				Constant.WriteLog(Constant.fail,"未找到朋友圈按钮");
				return false;
			}
		}else{
			UiObject shareTitle = Constant.GetContainTextObject(title);
			if(shareTitle!=null){
				UiObject shareContent = Constant.GetContainTextObject(content);
				if(shareContent!=null){
					Constant.WriteLog(Constant.info,"在朋友圈中检测到相应的分享内容");
					return true;
				}else{
					Constant.WriteLog(Constant.fail,"在朋友圈未查找到分享内容");
					return false;
				}
			}else{
				Constant.WriteLog(Constant.fail,"在朋友圈未查找到分享标题");
				return false;
			}
		}	
	}
	
	
	
	/**
	 * 判断微信是否分享成功
	 * @param title
	 * @param content
	 * @param friendsName
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public static Boolean IsWeiXinShareSuccess(String title,String content,String friendsName) throws UiObjectNotFoundException{
		UiObject talk = Constant.GetTextObject(friendsName);
		if(talk!=null){
			talk.click();
			UiObject shareTitle = Constant.GetContainTextObject(title);
			if(shareTitle!=null){
				UiObject shareContent = Constant.GetContainTextObject(content);
				if(shareContent!=null){
					Constant.WriteLog(Constant.info,"在聊天记录中检测到相应的分享内容");
					Back();
					return true;
				}else{
					Constant.WriteLog(Constant.fail,"在聊天记录中未检测到相应的分享内容");
					Back();
					return false;
				}
			}else{
				Constant.WriteLog(Constant.fail,"在聊天记录中未查找到分享标题");
				Back();
				return false;
			}
		}else{
			Constant.WriteLog(Constant.fail,"未找到聊天记录");
			return false;
		}
		
	}

}

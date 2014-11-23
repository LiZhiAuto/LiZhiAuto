package com.cvte.lizhi;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Forum extends UiAutomatorTestCase {
	
	
	/**
	 * 发帖功能
	 * @throws UiObjectNotFoundException
	 */
	public void ForumPost() throws UiObjectNotFoundException{
		
		int k = 0;
		
		for(int i=300;i<600;i++){
			//查找发帖按钮
			UiObject post = new UiObject(new UiSelector().text(Constant.post));
			post.clickAndWaitForNewWindow();
			//查找正文 并输入内容
			UiObject content = new UiObject(new UiSelector().text(Constant.content));
			if(content.exists()){
				content.setText(String.valueOf(i));
				k = upLoadPic(i,k);
			}else{
				UiObject publics = new UiObject(new UiSelector().text(Constant.pubic));
				if(publics.exists()){
					k = upLoadPic(i,k);
				}else{
					Constant.WriteLog(Constant.fail, "未进入发帖页面");
				}
				
			}
			
		}
		
		

	}
	
	
	/**
	 * 上传图片功能
	 * @param i 循环次数
	 * @param k 失败次数
	 * @return  失败次数
	 * @throws UiObjectNotFoundException
	 */
	public int  upLoadPic(int i,int k) throws UiObjectNotFoundException{
		//查找图片按钮
		UiObject imageButton = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()).instance(1));
		if(imageButton.exists()){
			imageButton.click();
			//查找上传图片按钮
			for(int j=1;j<5;j++){
				UiObject uploadImage = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()).instance(j));
				if(uploadImage.exists()){
					uploadImage.clickAndWaitForNewWindow();
					UiObject picSelect = new UiObject(new UiSelector().text(Constant.selectFromAlbum));
					picSelect.click();
					sleep(500);
					test.uidevice.click(test.width/2, test.height/2);
					sleep(500);
					test.uidevice.click(test.width/2, test.height/2);
					sleep(1000);
					
				}
			}
		
				UiObject resultLayout = null;
				do {
					UiObject publics = new UiObject(new UiSelector().text(Constant.pubic));
					if(publics.exists()){
						publics.clickAndWaitForNewWindow();
						sleep(10000);
						UiCollection relativelayoutCollection = new UiCollection(new UiSelector().className(android.widget.RelativeLayout.class.getName()));
						resultLayout=relativelayoutCollection.getChildByInstance(new UiSelector().className(android.widget.RelativeLayout.class.getName()), 4);
						if(resultLayout.exists()){
							UiObject content = resultLayout.getChild(new UiSelector().className(android.widget.TextView.class.getName()).index(1)); 
								
								if(content.getText().equals(String.valueOf(i))){
									Constant.WriteLog(Constant.info, "发表图片成功");
									break;
								}else{
										k = k+1;
										Constant.WriteLog(Constant.fail, "i="+i+"发表图片失败第"+k+"次");
										
								}
						}else{
							k = k+1;
							Constant.WriteLog(Constant.fail, "i="+i+"发表图片失败第"+k+"次");
						}
					}else{
						break;
					}
					
					
				} while (true);
				
			return k;	
		}else{
			return 0;
		}
		
	}

}
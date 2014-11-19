package com.cvte.lizhi;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class My extends UiAutomatorTestCase {

	
	public void ModifySex() throws UiObjectNotFoundException{

		UiObject my = new UiObject(new UiSelector().text(Constant.my)); 
		my.click();
		//判断当前是否为已登录状态
		UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));
		if(mailLogin.exists()){
			mailLogin.clickAndWaitForNewWindow();
			LoginOn();
			ModifySex();
			return  ;
		}else{
			
		}
	}
	
	
	/**
	 * 我的页面立知账号登录
	 */
	public void LoginOn(){

		try {


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

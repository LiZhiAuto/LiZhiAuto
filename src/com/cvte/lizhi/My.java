package com.cvte.lizhi;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class My extends UiAutomatorTestCase {


	/**
	 * 修改用户性别
	 * @param 
	 * @return
	 */
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
			//查找编辑个人信息按钮
			UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
			EditInfo.clickAndWaitForNewWindow();
			//首先先判断当前个人信息中的性别,并修改性别
			UiObject SexTextView = new UiObject(new UiSelector().text(Constant.sex));
			if(SexTextView.exists()){
				UiObject Sex = SexTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
				Constant.WriteLog(Constant.info, "当前的性别为"+Sex.getText()+"性");
				String modifySex = "";
				if(Sex.getText().equals(Constant.male)){
					Constant.WriteLog(Constant.info, "修改性别为"+Constant.female+"性");
					modifySex = Constant.female;
				}else{
					Constant.WriteLog(Constant.info, "修改性别为"+Constant.male+"性");
					modifySex  = Constant.male;
				}
				SexTextView.click();

				UiObject newSex = new UiObject(new UiSelector().text(modifySex));
				newSex.click();

				if(Sex.getText().equals(modifySex)){
					Constant.WriteLog(Constant.info, "修改性别成功");
				}else{
					Constant.WriteLog(Constant.fail, "修改性别失败");
				}
			}
			//返回主界面

			UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
			back.click();

		}
	}



	public void ModifyNickName() throws UiObjectNotFoundException{
		UiObject my = new UiObject(new UiSelector().text(Constant.my)); 
		my.click();
		//判断当前是否为已登录状态
		UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));
		if(mailLogin.exists()){
			mailLogin.clickAndWaitForNewWindow();
			LoginOn();
			ModifyNickName();
			return  ;
		}else{
			//查找编辑个人信息按钮
			UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
			EditInfo.clickAndWaitForNewWindow();
			//首先先判断当前个人信息中的性别,并修改性别
			UiObject nickNameTextView = new UiObject(new UiSelector().text(Constant.nickName));
			if(nickNameTextView.exists()){
				UiObject nickName = nickNameTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
				Constant.WriteLog(Constant.info, "当前的昵称为"+nickName.getText());
			}
			
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
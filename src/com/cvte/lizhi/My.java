package com.cvte.lizhi;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class My extends UiAutomatorTestCase {



	public static final int SELECT_LISTVIEW_FIRST =0;
	public static final int CHINESEKEY =1;
	public static final int PINYINKEY =2;


	/**
	 * 修改用户性别
	 * @param 
	 * @returna
	 */
	public void ModifySex() throws UiObjectNotFoundException{
		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = Constant.GetObject(Constant.IMAGEBUTTON, 0);
		if(EditInfo!=null){
			EditInfo.clickAndWaitForNewWindow();
		}
		
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



	/**
	 * 修改昵称
	 * @param 
	 * @return
	 */
	public void ModifyNickName() throws UiObjectNotFoundException{

		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
		EditInfo.clickAndWaitForNewWindow();
		//首先先判断当前个人信息中的性别,并修改性别
		UiObject nickNameTextView = new UiObject(new UiSelector().text(Constant.nickName));
		if(nickNameTextView.exists()){
			UiObject nickNameInfo = nickNameTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
			String nickNameStr = nickNameInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的昵称为"+nickNameStr);
			nickNameTextView.clickAndWaitForNewWindow();
			//设置空的昵称
			UiObject nickName = new UiObject(new UiSelector().className(android.widget.EditText.class.getName()));
			Constant.WriteLog(Constant.info, "设置空昵称");
			nickName.setText(" ");
			if(IsEditInfoActivity(" ")){
				Constant.WriteLog(Constant.fail, "空昵称提交成功");
			}else{
				Constant.WriteLog(Constant.info, "空昵称提交失败");
				Constant.WriteLog(Constant.info, "昵称设置1个字符");
				nickName.setText(Utf7ImeHelper.e("a"));
				if(IsEditInfoActivity("a")){
					Constant.WriteLog(Constant.info, "昵称设置1个字符成功");
					nickNameTextView.clickAndWaitForNewWindow();
					Constant.WriteLog(Constant.info, "昵称设置12个字符");
					nickName.setText("123456abcd_e");//
					if(IsEditInfoActivity("123456abcd_e")){
						Constant.WriteLog(Constant.info, "昵称设置12个字符成功");
						nickNameTextView.clickAndWaitForNewWindow();
						Constant.WriteLog(Constant.info, "昵称设置大于12个字符");
						nickName.setText("123456_abcdefgh");
						if(nickName.getText().equals("123456_abcde")){
							Constant.WriteLog(Constant.info, "昵称最大只能设置12个字符");
							nickName.setText("sai");
							if(IsEditInfoActivity("sai")){
								Constant.WriteLog(Constant.info, "设置昵称sai成功");
							}else{
								Constant.WriteLog(Constant.fail, "设置昵称sai失败");
							}
						}else{
							Constant.WriteLog(Constant.fail, "昵称能够设置大于12个字符");
						}
					}else{
						Constant.WriteLog(Constant.fail, "昵称设置12个字符失败");
					}
				}else{
					Constant.WriteLog(Constant.fail, "设置1个字符提交失败");
				}
				
			}
			
			UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
			if(back.exists()){
				back.click();
			}
		}
		


	}


	/**
	 * 修改学校
	 * @param 
	 * @return
	 */
	public void ModifySchool() throws UiObjectNotFoundException{

		String searchSchool = "安徽大学";
		String searchSchoolPY = "ahdx";
		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
		EditInfo.clickAndWaitForNewWindow();
		UiObject schoolTextView = new UiObject(new UiSelector().text(Constant.school));
		if(schoolTextView.exists()){
			UiObject schoolInfo = schoolTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
			String schoolName = schoolInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的学校为"+schoolName);
			schoolTextView.clickAndWaitForNewWindow();
			String newSchool = ModifyInfo(SELECT_LISTVIEW_FIRST,"","");
			if(schoolInfo.getText().equals(newSchool)){
				Constant.WriteLog(Constant.info, "设置学校成功");
				schoolTextView.clickAndWaitForNewWindow();
				Constant.WriteLog(Constant.info, "搜索"+searchSchool+"首字母并设置其为学校");
				newSchool = ModifyInfo(PINYINKEY,searchSchoolPY,searchSchool);
				if(newSchool.equals(searchSchool)){
					Constant.WriteLog(Constant.info, "设置学校成功");
				}else if(newSchool.equals("")){
					Constant.WriteLog(Constant.fail, "未搜索到"+searchSchool);
				}else{
					Constant.WriteLog(Constant.fail, "设置学校失败");
				}
			}else{
				Constant.WriteLog(Constant.fail, "设置学校失败");
			}	
		}
		sleep(1000);
		UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
		if(back.exists()){
			back.click();
		}
	}


	/**
	 * 修改城市
	 * @throws UiObjectNotFoundException 
	 */

	public void modifyCity() throws UiObjectNotFoundException{
		String searchCity = "哈尔滨";
		String searchCityPY = "heb";
		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
		EditInfo.clickAndWaitForNewWindow();
		UiObject cityTextView = new UiObject(new UiSelector().text(Constant.city));
		if(cityTextView.exists()){
			UiObject cityInfo = cityTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
			String schoolName = cityInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的城市为"+schoolName);
			cityInfo.clickAndWaitForNewWindow();
			String newSchool = ModifyInfo(SELECT_LISTVIEW_FIRST,"","");
			if(cityInfo.getText().equals(newSchool)){
				Constant.WriteLog(Constant.info, "设置城市成功");
				cityTextView.clickAndWaitForNewWindow();
				Constant.WriteLog(Constant.info, "搜索"+searchCity+"首字母并设置其为城市");
				newSchool = ModifyInfo(PINYINKEY,searchCityPY,searchCity);
				if(newSchool.equals(searchCity)){
					Constant.WriteLog(Constant.info, "设置城市成功");
				}else if(newSchool.equals("")){
					Constant.WriteLog(Constant.fail, "未搜索到"+searchCity);
				}else{
					Constant.WriteLog(Constant.fail, "设置城市失败");
				}
			}else{
				Constant.WriteLog(Constant.fail, "设置城市失败");
			}	
		}

		UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
		if(back.exists()){
			back.click();
		}
	}


	/**
	 * 修改城市
	 * @throws UiObjectNotFoundException 
	 */

	public void modifyProfession() throws UiObjectNotFoundException{
		String searchProfession = "教育";
		String searchProfessionPY = "jy";
		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
		EditInfo.clickAndWaitForNewWindow();
		UiObject cityTextView = new UiObject(new UiSelector().text(Constant.profession));
		if(cityTextView.exists()){
			UiObject cityInfo = cityTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
			String schoolName = cityInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的目标行业为"+schoolName);
			cityInfo.clickAndWaitForNewWindow();
			String newSchool = ModifyInfo(SELECT_LISTVIEW_FIRST,"","");
			if(cityInfo.getText().equals(newSchool)){
				Constant.WriteLog(Constant.info, "设置目标行业成功");
				cityTextView.clickAndWaitForNewWindow();
				Constant.WriteLog(Constant.info, "搜索"+searchProfession+"首字母并设置其为目标行业");
				newSchool = ModifyInfo(PINYINKEY,searchProfessionPY,searchProfession);
				if(newSchool.equals(searchProfession)){
					Constant.WriteLog(Constant.info, "设置目标行业成功");
				}else if(newSchool.equals("")){
					Constant.WriteLog(Constant.fail, "未搜索到"+searchProfession);
				}else{
					Constant.WriteLog(Constant.fail, "设置目标行业失败");
				}
			}else{
				Constant.WriteLog(Constant.fail, "设置目标行业失败");
			}	
		}
		UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
		if(back.exists()){
			back.click();
		}
	}


	/**
	 * 此方法集合 学校、行业以及城市的搜索
	 * @param 
	 * @return
	 * @throws UiObjectNotFoundException 
	 */
	public String ModifyInfo(int method,String searchKey,String chineseKey) throws UiObjectNotFoundException{
		if(method==SELECT_LISTVIEW_FIRST){
			UiObject listView = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
			UiObject item = listView.getChild(new UiSelector().className(android.widget.TextView.class.getName()).instance(0));
			String newName = item.getText();
			Constant.WriteLog(Constant.info, "设置"+newName);
			item.click();
			return newName;
		}else if(method==PINYINKEY){
			UiObject searchSchoolED = new UiObject(new UiSelector().className(android.widget.EditText.class.getName()));
			UiObject listView = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
			searchSchoolED.setText(Utf7ImeHelper.e(searchKey));
			UiObject item = listView.getChild(new UiSelector().className(android.widget.TextView.class.getName()).text(chineseKey));
			if(item.exists()){
				Constant.WriteLog(Constant.info, "搜索到"+chineseKey);
				String newName = item.getText();
				item.click();
				return newName;
			}else{
				return "";
			}

		}else{

			return "";
		}


	}



	/**
	 * 我的界面UI检查
	 * @throws UiObjectNotFoundException
	 */
	public void MyUICheck() throws UiObjectNotFoundException {
		EnterMyPage();
		UiObject userNameTV = new UiObject(new UiSelector().className(android.widget.TextView.class.getName()));
		String userName = "";
		if(userNameTV.exists()){
			userName = userNameTV.getText();
			Constant.WriteLog(Constant.info, "检测到当前的用户名为"+userName);
			UiObject myInfoTv = new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).textContains(Constant.point));
			if(myInfoTv.exists()){
				String myInfo = myInfoTv.getText();
				String Array[] = myInfo.split("头");
				String point[] = Array[0].split("：");
				Constant.WriteLog(Constant.info, "检测到当前积分为"+point[1]);
				String honor[] = Array[1].split("：");
				Constant.WriteLog(Constant.info, "检测到当前头衔为"+honor[1]);
				UiObject messageCenter = new UiObject(new UiSelector().className(android.widget.RadioButton.class.getName()).text(Constant.messageCenter));
				if(messageCenter.exists()){
					Constant.WriteLog(Constant.info, "检测到"+Constant.messageCenter+"模块");
					UiObject myCollect = new UiObject(new UiSelector().className(android.widget.RadioButton.class.getName()).text(Constant.myColect));
					if(myCollect.exists()){
						Constant.WriteLog(Constant.info, "检测到"+Constant.myColect+"模块");
						UiObject userInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
						if(userInfo.exists()){
							Constant.WriteLog(Constant.info, "检测到个人资料编辑按钮");
						}else{
							Constant.WriteLog(Constant.fail, "未检测到个人资料编辑按钮");
						}
					}else{
						Constant.WriteLog(Constant.fail, "未检测到"+Constant.myColect+"模块");
					}
				}else{
					Constant.WriteLog(Constant.fail, "未检测到"+Constant.messageCenter+"模块");
				}



			}else{
				Constant.WriteLog(Constant.fail, "未检测到当前的积分及头衔");
			}
		}else{
			Constant.WriteLog(Constant.fail, "未检测到当前的用户名为");
		}

	}


	public void EditMyInfoUICheck() throws UiObjectNotFoundException {
		EnterMyPage();
		//查找编辑个人信息按钮
		UiObject EditInfo = new UiObject(new UiSelector().className(android.widget.ImageButton.class.getName()));
		EditInfo.clickAndWaitForNewWindow();
		if(CheckTextExist(Constant.headPortrait)){
			if(CheckTextExist(Constant.nickName)){
				if(CheckTextExist(Constant.sex)){
					if(CheckTextExist(Constant.school)){
						if(CheckTextExist(Constant.profession)){
							if(CheckTextExist(Constant.city)){
								UiObject back = new UiObject(new UiSelector().className(android.widget.ImageView.class.getName()));
								if(back.exists()){
									back.click();
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 用来判断具有文本内容的UI是否存在
	 * @return
	 */
	public boolean CheckTextExist(String searchKey){
		
		UiObject searchUI = new UiObject(new UiSelector().text(searchKey));
		if(searchUI.exists()){
			Constant.WriteLog(Constant.info, "检测到"+searchKey);
			return true;
		}else{
			Constant.WriteLog(Constant.info, "未检测到"+searchKey);
			return false;
		}
		
	}
	


	/**
	 * 进入我的页面判断是否登录
	 * @param 
	 * @return
	 */
	public void EnterMyPage() throws UiObjectNotFoundException{
		UiObject my = new UiObject(new UiSelector().text(Constant.my)); 
		my.click();
		//判断当前是否为已登录状态
		UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));
		if(mailLogin.exists()){
			mailLogin.clickAndWaitForNewWindow();
			LoginOn();
		}
	}


	/**
	 * 
	 * @param str 昵称名
	 * @return  是否昵称修改成功
	 * @throws UiObjectNotFoundException
	 */
	public boolean IsEditInfoActivity(String str) throws UiObjectNotFoundException {
		UiObject complete = new UiObject(new UiSelector().text(Constant.complete));
		complete.clickAndWaitForNewWindow();
		UiObject editInfo = new UiObject(new UiSelector().text(Constant.editInfo));
		if(editInfo.exists()){
			UiObject nickNameTextView = new UiObject(new UiSelector().text(Constant.nickName));
			if(nickNameTextView.exists()){
				UiObject nickNameInfo = nickNameTextView.getFromParent(new UiSelector().className(android.widget.TextView.class.getName()).index(1));
				if(nickNameInfo.getText().equals(str)){
					return true;
				}else{
					return  false;
				}
			}else{
				return false;
			}
		}else{
			return false;
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

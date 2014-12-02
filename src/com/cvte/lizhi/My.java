package com.cvte.lizhi;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.cvte.lizhiUI.MyUI;

public class My extends UiAutomatorTestCase {



	public static final int SELECT_LISTVIEW_FIRST =0;
	public static final int CHINESEKEY =1;
	public static final int PINYINKEY =2;

	public MyUI myUI ;
	public My() {
		myUI = new MyUI();
	}


	/**
	 * 进入个人信息编辑页面
	 * @throws UiObjectNotFoundException
	 */
	public void EnterEditInfo() throws UiObjectNotFoundException{
		//查找编辑个人信息按钮
		UiObject editInfo = Constant.GetObject(Constant.IMAGEBUTTON, 0);
		if(editInfo!=null){
			editInfo.click();
			Constant.WriteLog(Constant.info, "进入编辑个人信息页面");
		}else{
			Constant.WriteLog(Constant.fail, "未找到编辑个人信息按钮");
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
	 * 进入我的页面判断是否登录
	 * @param 
	 * @return
	 */
	public void EnterMyPage() throws UiObjectNotFoundException{
		UiObject my = Constant.GetTextObject(Constant.my);
		if(my!=null){
			my.click();
		}
		//判断当前是否为已登录状态
		UiObject mailLogin = Constant.GetTextObject(Constant.mailLogin);
		if(mailLogin!=null){
			mailLogin.clickAndWaitForNewWindow();
			LoginOn();
		}
	}

	/**
	 * 修改用户性别
	 * @param 
	 * @returna
	 */
	public void ModifySex() throws UiObjectNotFoundException{
		EnterMyPage();
		EnterEditInfo();
		//首先先判断当前个人信息中的性别,并修改性别
		UiObject sexTextView = Constant.GetObject(Constant.TEXTVIEW, 4);
		if(sexTextView!=null){
			UiObject sexTV = myUI.GetUnderSameParentObject(sexTextView, Constant.TEXTVIEW, 1);
			Constant.WriteLog(Constant.info, "当前的性别为"+sexTV.getText()+"性");
			String modifySex = "";
			if(sexTV.getText().equals(Constant.male)){
				Constant.WriteLog(Constant.info, "修改性别为"+Constant.female+"性");
				modifySex = Constant.female;
			}else{
				Constant.WriteLog(Constant.info, "修改性别为"+Constant.male+"性");
				modifySex  = Constant.male;
			}
			sexTextView.click();

			UiObject newSex = Constant.GetTextObject(modifySex);
			newSex.click();
			if(sexTV.getText().equals(modifySex)){
				Constant.WriteLog(Constant.info, "修改性别成功");
			}else{
				Constant.WriteLog(Constant.fail, "修改性别失败");
			}
		}
		//返回主界面

		Back();



	}



	/**
	 * 修改昵称
	 * @param 
	 * @return
	 */
	public void ModifyNickName() throws UiObjectNotFoundException{

		EnterMyPage();
		EnterEditInfo();
		//首先先判断当前个人信息中的性别,并修改性别
		UiObject nickNameTextView = Constant.GetObject(Constant.TEXTVIEW, 2);
		if(nickNameTextView.exists()){
			UiObject nickNameInfo = myUI.GetUnderSameParentObject(nickNameTextView, Constant.TEXTVIEW, 1);
			String nickNameStr = nickNameInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的昵称为"+nickNameStr);
			nickNameTextView.clickAndWaitForNewWindow();
			//设置空的昵称
			UiObject nickName = Constant.GetObject(Constant.EDITTEXT, 0);
			Constant.WriteLog(Constant.info, "设置空昵称");
			nickName.setText(" ");
			if(IsEditInfoActivity(" ")){
				Constant.WriteLog(Constant.fail, "空昵称提交成功");
			}else{
				Constant.WriteLog(Constant.info, "空昵称提交失败");
				Constant.WriteLog(Constant.info, "昵称设置1个字符");
				nickName.setText("a");
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

			Back();
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
		EnterEditInfo();
		UiObject schoolTextView = Constant.GetObject(Constant.TEXTVIEW, 6);
		if(schoolTextView!=null){
			UiObject schoolInfo = myUI.GetUnderSameParentObject(schoolTextView, Constant.TEXTVIEW, 1);
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
		Back();
	}


	/**
	 * 修改城市
	 * @throws UiObjectNotFoundException 
	 */

	public void modifyCity() throws UiObjectNotFoundException{
		String searchCity = "哈尔滨";
		String searchCityPY = "heb";
		EnterMyPage();
		EnterEditInfo();
		UiObject cityTextView = Constant.GetObject(Constant.TEXTVIEW,8);
		if(cityTextView!=null){
			UiObject cityInfo = myUI.GetUnderSameParentObject(cityTextView, Constant.TEXTVIEW, 1);
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

		Back();
	}


	/**
	 * 修改城市
	 * @throws UiObjectNotFoundException 
	 */

	public void modifyProfession() throws UiObjectNotFoundException{
		String searchProfession = "教育";
		String searchProfessionPY = "jy";
		EnterMyPage();
		EnterEditInfo();
		UiObject professionTextView = Constant.GetObject(Constant.TEXTVIEW,10);
		if(professionTextView!=null){
			UiObject cityInfo = myUI.GetUnderSameParentObject(professionTextView, Constant.TEXTVIEW, 1);
			String schoolName = cityInfo.getText(); 
			Constant.WriteLog(Constant.info, "当前的目标行业为"+schoolName);
			cityInfo.clickAndWaitForNewWindow();
			String newSchool = ModifyInfo(SELECT_LISTVIEW_FIRST,"","");
			if(cityInfo.getText().equals(newSchool)){
				Constant.WriteLog(Constant.info, "设置目标行业成功");
				professionTextView.clickAndWaitForNewWindow();
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
		Back();
	}


	/**
	 * 此方法集合 学校、行业以及城市的搜索
	 * @param 
	 * @return
	 * @throws UiObjectNotFoundException 
	 */
	public String ModifyInfo(int method,String searchKey,String chineseKey) throws UiObjectNotFoundException{
		if(method==SELECT_LISTVIEW_FIRST){
			UiObject listView = Constant.GetObject(Constant.LISTVIEW, 0);
			Constant.GetObjectChild(listView, Constant.TEXTVIEW, 0);
			UiObject item = Constant.GetObjectChild(listView, Constant.TEXTVIEW, 0);
			String newName = item.getText();
			Constant.WriteLog(Constant.info, "设置"+newName);
			item.click();
			return newName;
		}else if(method==PINYINKEY){
			UiObject searchSchoolED = Constant.GetObject(Constant.EDITTEXT, 0);
			searchSchoolED.setText(searchKey);
			UiObject item = Constant.GetTextObject(chineseKey);
			if(item!=null){
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
		UiObject userNameTV = Constant.GetObject(Constant.TEXTVIEW, 0);
		String userName = "";
		if(userNameTV!=null){
			userName = userNameTV.getText();
			Constant.WriteLog(Constant.info, "检测到当前的用户名为"+userName);
			UiObject myInfoTv = Constant.GetContainTextObject(Constant.point);
			if(myInfoTv!=null){
				String myInfo = myInfoTv.getText();
				String Array[] = myInfo.split("头");
				String point[] = Array[0].split("：");
				Constant.WriteLog(Constant.info, "检测到当前积分为"+point[1]);
				String honor[] = Array[1].split("：");
				Constant.WriteLog(Constant.info, "检测到当前头衔为"+honor[1]);
				UiObject messageCenter = Constant.GetObject(Constant.RADIOBUTTON, 0);
				if(messageCenter!=null&&messageCenter.getText().equals(Constant.messageCenter)){
					Constant.WriteLog(Constant.info, "检测到"+Constant.messageCenter+"模块");
					UiObject myCollect = Constant.GetObject(Constant.RADIOBUTTON, 1);
					if(myCollect!=null&&myCollect.getText().equals(Constant.myColect)){
						Constant.WriteLog(Constant.info, "检测到"+Constant.myColect+"模块");
						UiObject userInfo = Constant.GetObject(Constant.IMAGEBUTTON, 0);
						if(userInfo!=null){
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


	/**
	 * 编辑个人信息界面检查
	 * @throws UiObjectNotFoundException
	 */
	public void EditMyInfoUICheck() throws UiObjectNotFoundException {
		EnterMyPage();
		//查找编辑个人信息按钮
		EnterEditInfo();
		if(CheckTextExist(Constant.headPortrait,1)){
			if(CheckTextExist(Constant.nickName,2)){
				if(CheckTextExist(Constant.sex,4)){
					if(CheckTextExist(Constant.school,6)){
						if(CheckTextExist(Constant.profession,8)){
							if(CheckTextExist(Constant.city,10)){
								Back();
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
	 * @throws UiObjectNotFoundException 
	 */
	public boolean CheckTextExist(String searchKey,int index) throws UiObjectNotFoundException{
		UiObject searchUI = Constant.GetObject(Constant.TEXTVIEW, index);
		if(searchUI!=null&&searchUI.getText().equals(searchKey)){
			Constant.WriteLog(Constant.info, "检测到"+searchKey);
			return true;
		}else{
			Constant.WriteLog(Constant.info, "未检测到"+searchKey);
			return false;
		}

	}






	/**
	 * 
	 * @param str 昵称名
	 * @return  是否昵称修改成功
	 * @throws UiObjectNotFoundException
	 */
	public boolean IsEditInfoActivity(String str) throws UiObjectNotFoundException {
		UiObject complete = Constant.GetObject(Constant.TEXTVIEW, 1);
		complete.clickAndWaitForNewWindow();
		UiObject editText = Constant.GetObject(Constant.EDITTEXT, 0);
		if(editText==null){
				UiObject nickNameInfo = Constant.GetObject(Constant.TEXTVIEW, 3);
				if(nickNameInfo.getText().equals(str)){
					return true;
				}else{
					return  false;
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
			UiObject mailEdit = Constant.GetTextObject(Constant.mail);
			mailEdit.setText(Constant.userName);
			UiObject passwordEdit  = Constant.GetObject(Constant.EDITTEXT, 1);
			passwordEdit.setText(Constant.password);
			UiObject login = Constant.GetTextObject(Constant.login);
			login.clickAndWaitForNewWindow();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}

	}


}

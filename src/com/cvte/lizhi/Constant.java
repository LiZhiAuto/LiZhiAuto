package com.cvte.lizhi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class Constant  {

	public static String my = "我的";

	public static String myColect = "我的收藏";

	public static String cancelCollect = "取消收藏";

	public static String messageCenter = "消息中心";

	public static String article = "文章";

	public static String all = "全部";

	public static String noLoginTip = "你还没有登录哦";

	public static String mailLogin = "邮箱登录";

	public static String login = "登录";

	public static String userName = "qwe@qq.com";

	public static String passWord = "123456";

	public static String comment = "评论";

	public static String pubic = "发表";

	public static String saySomething = "说点啥";

	public static String longString = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

	public static String commentContent = "good";

	public static String hotSearch = "热门搜索";

	public static String historySearch = "历史搜索";

	public static String clearSearch = "清除搜索历史";

	public static String content = "正文";

	public static String mail = "邮箱";

	public static String post = "发帖";

	public static String selectFromAlbum = "从相册选择";

	public static String account = "411249087@qq.com";

	public static String password = "123456";

	public static String male = "男";

	public static String female = "女";

	public static String sex = "性别";

	public static String nickName = "昵称";

	public static String complete  = "完成";

	public static String editInfo = "编辑个人信息";

	public static String headPortrait = "头像";

	public static String school = "学校";

	public static String city = "城市";

	public static String profession = "目标行业";

	public static String point = "积分";

	public static String share = "分享";

	public static String weiXin = "微信";

	public static String createNewTalk = "创建新聊天";

	public static String confirm = "确定";

	public static String cancel = "取消";

	public static String back = "返回";

	public static String send = "发送";

	public static String lizhi = "立知";

	public static String delete = "删除";

	public static String copy = "复制";

	public static String paste = "粘贴";

	public static String report = "举报";
	
	public static String loading = "正在加载";

	public static String emptyString = " ";

	public static String singleString = "a";  

	public static String twelveString = "123456abcd_e";  

	public static String moreTwelveString = "123456_abcdefgh";

	public static final int READARTICLEPOINT = 5;

	public static final int ONEPOINT =1;

	public static final String READARTICLE = "阅读文章";
	
	public static final String PRAISEARTICLE = "点赞文章";
	
	public static final String PRAISEARTICLECOMMENT = "点赞文章评论";

	public static int  COMMNETUSERNAME = 0;

	public static int COMMENTUSERSCHOOL = 1;

	public static int COMMENTIME = 2;

	public static int COMMENTEDUSERNAME = 3;

	public static int COMMENTEDCONTENT = 4;

	public static int COMMENTCONTENT = 5;

	public static int COMMENTPRAISE = 6;

	public static int NUM = 1;

	public static String IMAGEVIEW = "android.widget.ImageView";

	public static String IMAGEBUTTON = "android.widget.ImageButton";

	public static String TEXTVIEW = "android.widget.TextView";

	public static String EDITTEXT = "android.widget.EditText";

	public static String LISTVIEW = "android.widget.ListView";

	public static String GRIDVIEW = "android.widget.GridView";

	public static String BUTTON = "android.widget.Button";

	public static String VIEW = "android.view.View";

	public static String RADIOBUTTON = "android.widget.RadioButton";

	public static String VIEWPAGER = "android.support.v4.view.ViewPager";
	/**
	 * log 等级
	 */
	public static String info = "INFO";
	public static String warn = "WARNING";
	public static String dbug = "DEBUG";
	public static String fail = "FAIL";




	/**
	 * log打印以及失败的情况下会截取当前屏幕
	 * @param Level
	 * @param str
	 */
	public static void WriteLog(String Level,String str){

		if(Level.equals(fail)){
			TakeScreenShot(str);
		}

		System.out.println(GetSystemTime()+":"+Level+"------"+str);
	}


	public static String GetSystemTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}

	public static void TakeScreenShot(String str){
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH-mm-ss");
		File f = new File("/mnt/sdcard/LiZhiScreenShot/") ;
		if(!f.exists()){
			f.mkdir();
		}
		f = new File("/mnt/sdcard/LiZhiScreenShot/"+df.format(new Date())+str+".png") ;
		test.uidevice.takeScreenshot(f);

	}






	/**
	 * 返回一个索引值为index,class为objectStr的object
	 * @param objectStr
	 * @param index
	 * @return
	 */
	public static UiObject GetObject(String objectStr,int index){
		UiObject object = new UiObject(new UiSelector().className(objectStr).instance(index));
		if(object.exists()){
			return object; 
		}else{
			return null;
		}
	}
	/**
	 * 返回一个内容为text的Object
	 * @param text
	 * @return
	 */
	public static UiObject GetTextObject(String text){
		UiObject object = new UiObject(new UiSelector().text(text));
		if(object.exists()){
			return object; 
		}else{
			return null;
		}
	}


	/**
	 * 获取objec对象的child
	 * @param 
	 * @return
	 */
	public static UiObject GetObjectChild(UiObject parent,String objectStr,int index) throws UiObjectNotFoundException{

		UiObject object = parent.getChild(new UiSelector().className(objectStr).instance(index));
		if(object.exists()){
			return object;
		}else{
			return null;
		}
	}


	public static UiObject GetContainTextObject(String text){
		UiObject object = new UiObject(new UiSelector().textContains(text));
		if(object.exists()){
			return object;
		}else{
			return null;
		}
	}


	/**
	 * 获取可滑动的Object
	 * @return
	 */
	public static UiScrollable GetScrollableObject(String objectStr){
		UiScrollable scrollableObject = new UiScrollable(new UiSelector().className(objectStr).scrollable(true));
		if(scrollableObject.exists()){
			return scrollableObject;
		}else{
			return null;
		}
	}


	/**
	 * 返回一个描述内容为objectStr
	 * @param objectStr
	 * @return
	 */
	public static UiObject GetDescObject(String objectStr){
		UiObject object = new UiObject(new UiSelector().description(objectStr));
		if(object.exists()){
			return object;
		}else{
			return null;
		}
	}

	/**
	 * 打开应用
	 * @param 
	 * @return
	 */
	public static void OpenApplication(String application) throws UiObjectNotFoundException{
		test.uidevice.pressHome();
		UiObject allAppsButton = new UiObject(new UiSelector().description("应用"));
		if(allAppsButton.exists()){
			allAppsButton.clickAndWaitForNewWindow();
		}
		UiObject liZhiApp = null;
		UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
		if(appViews.exists()){
			appViews.setAsHorizontalList();
			liZhiApp = appViews.getChildByText(
					new UiSelector().className(android.widget.TextView.class.getName()), application);
		}else{
			liZhiApp = Constant.GetTextObject(application);
		}
		liZhiApp.clickAndWaitForNewWindow();
	}















}
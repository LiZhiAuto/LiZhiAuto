package com.cvte.lizhi;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Constant {

	public static String my = "我的";
	
	public static String myColect = "我的收藏";

	public static String messageCenter = "消息中心";
	
	public static String article = "文章";
	
	public static String noLoginTip = "你还没有登录哦";
	
	public static String mailLogin = "邮箱登录";
	
	public static String login = "登录";
	
	public static String userName = "411249087@qq.com";
	
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

	public static String createNewTalk = "创建新聊天";
	
	public static String confirm = "确定";
	
	public static String back = "返回";
	
	public static String application = "立知";


	/**
	 * log 等级
	 */
	public static String info = "INFO";
	public static String warn = "WARNING";
	public static String dbug = "DEBUG";
	public static String fail = "FAIL";
	
	public static void WriteLog(String Level,String str){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		// new Date()为获取当前系统时间
		
		System.out.println(df.format(new Date())+":"+Level+"------"+str);
	}
	
	
	
	
	
	






}


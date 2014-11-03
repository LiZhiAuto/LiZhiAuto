package com.cvte.lizhi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

	public static String my = "我的";
	
	public static String myColect = "我的收藏";
	
	public static String article = "文章";
	
	public static String noLoginTip = "你还没有登录哦";
	
	public static String mailLogin = "邮箱登录";
	
	public static String comment = "评论";
	
	public static String pubic = "发表";
	
	public static String saySomething = "说点啥";
	
	public static String longString = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
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
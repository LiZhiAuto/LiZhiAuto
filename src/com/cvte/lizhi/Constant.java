package com.cvte.lizhi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

	public static String my = "�ҵ�";
	
	public static String myColect = "�ҵ��ղ�";
	
	public static String article = "����";
	
	public static String noLoginTip = "�㻹û�е�¼Ŷ";
	
	public static String mailLogin = "�����¼";
	
	public static String comment = "����";
	
	public static String pubic = "����";
	
	public static String saySomething = "˵��ɶ";
	
	public static String longString = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	/**
	 * log �ȼ�
	 */
	public static String info = "INFO";
	public static String warn = "WARNING";
	public static String dbug = "DEBUG";
	public static String fail = "FAIL";
	
	public static void WriteLog(String Level,String str){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		// new Date()Ϊ��ȡ��ǰϵͳʱ��
		
		System.out.println(df.format(new Date())+":"+Level+"------"+str);
	}
	
}
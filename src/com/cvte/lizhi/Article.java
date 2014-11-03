package com.cvte.lizhi;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Article extends UiAutomatorTestCase {


	public  int TOTALNUM = 140;

	private String articleString;



	/**
	 * ��������ʽ�ĵ�¼����
	 */
	public void DialogLoginOn(){
		try {

			UiObject mailLogin = new UiObject(new UiSelector().text(Constant.mailLogin));

			mailLogin.clickAndWaitForNewWindow();

			UiObject mailEdit = new UiObject(new UiSelector().text("����"));

			mailEdit.setText("411249087@qq.com");

			UiObject passwordEdit  = new UiObject(new UiSelector().className("android.widget.EditText").focused(false));

			passwordEdit.setText("123456");


			UiObject login = new UiObject(new UiSelector().text("��¼"));

			login.clickAndWaitForNewWindow();

			Constant.WriteLog(Constant.info, "��¼�ɹ�");

		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

	}


	/**
	 * �����б�item�ĵ��
	 * @param index
	 */
	public void ArticalItem(int index){
		try {

			UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
			article.click();

			UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));

			//��ȡitem�����������

			//UiObject textViews = listview.getChild(new UiSelector().clickable(true).index(index));
			//�˴��ж��textView ���Ǳ����ǵ�һ��textView ����Ĭ���ǲ��ҵ�����
			UiObject textView = listview.getChild(new UiSelector().clickable(true).index(index)).getChild(new UiSelector().className("android.widget.TextView"));

			articleString = textView.getText().toString();
			Constant.WriteLog(Constant.info, "����������±���Ϊ"+textView.getText().toString());
			listview.getChild(new UiSelector().clickable(true).index(index)).click();
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}



	/**
	 * ����ȫ���б�UI��֤
	 * @param 
	 * @return
	 */
	public void ArticleUICheck() throws UiObjectNotFoundException{

		UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
		article.click();
		//���ҵ���Ӧ��ȫ���б��textView
		UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
		UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 4);
		UiObject allTextView = frameLayoutPraise.getChild(new UiSelector().className(android.widget.TextView.class.getName()));
		allTextView.click();

		//�б��еı���鿴
		UiObject listview = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
		for(int i=2;i<listview.getChildCount();i++){
			UiObject textView = listview.getChild(new UiSelector().clickable(true).index(i)).getChild(new UiSelector().className(android.widget.TextView.class.getName()));
			Constant.WriteLog(Constant.info, "��"+i+"���ı���Ϊ      "+textView.getText().toString());

			//�б��е�����Ƶ��
			UiObject Channel = listview.getChild(new UiSelector().clickable(true).index(i).className(android.widget.LinearLayout.class.getName()));
			textView = Channel.getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0));
			Constant.WriteLog(Constant.info, "��"+i+"����Ƶ��Ϊ      "+textView.getText().toString());

			//�б��е�����Ƶ��
			UiObject date = listview.getChild(new UiSelector().clickable(true).index(i).className(android.widget.LinearLayout.class.getName()));
			textView = date.getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(1));
			Constant.WriteLog(Constant.info, "��"+i+"����ʱ��Ϊ      "+textView.getText().toString());
		}


	}


	/**
	 * �ղ�����
	 */
	public void collectArticle(int index ){
		try {
			for(int i=0;i<2;i++){
				if(i==1){
					//�ٴν��е��޲���
					Constant.WriteLog(Constant.info, "�ٴν����ղ�/ȡ���ղز���");
				}

				ArticalItem( index);
				boolean isCollect = false;
				//�ҵ����ఴť
				UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
				UiObject fantile=resultLayout.getChild(new UiSelector().className("android.widget.ImageView").index(4)); 
				fantile.click();

				//�жϵ�ǰΪȡ���ղػ����ղذ�ť
				UiObject collectArticle =  new UiObject(new UiSelector().className("android.widget.TextView"));
				if(collectArticle.getText().toString().equals("ȡ���ղ�")){
					isCollect = true;
				}else{
					isCollect = false;
				}
				collectArticle.click();

				UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
				if(loginTip.exists()){
					Constant.WriteLog(Constant.info, "��ǰΪδ��¼״̬�����е�¼����");
					DialogLoginOn();
					collectArticle(index);
					break;
				}else{

					if(isCollect){
						Constant.WriteLog(Constant.info, "��ǰ����Ϊ���ղ�״̬������ȡ���ղز���");
					}else{
						Constant.WriteLog(Constant.info, "��ǰ����Ϊδ�ղ�״̬,�����ղز���");
					}

					//���ص������б�
					test.uidevice.pressBack();
					test.uidevice.pressBack();

					UiObject my = new UiObject(new UiSelector().text(Constant.my)); 
					my.click();
					UiObject myColect = new UiObject(new UiSelector().text(Constant.myColect)); 
					myColect.click();




					if(isCollect){
						try {
							UiObject textview = new UiObject(new UiSelector().text(articleString));
							if(textview.exists()){
								Constant.WriteLog(Constant.fail, "����ȡ���ղ�ʧ��");
							}else{
								Constant.WriteLog(Constant.info, "������ȡ���ղ�");
							}
						} catch (Exception e) {
							Constant.WriteLog(Constant.info, "������ȡ���ղ�");
						}
					}else{
						try {
							new UiObject(new UiSelector().text(articleString));
							Constant.WriteLog(Constant.info, "�����ղسɹ�");
						} catch (Exception e) {
							Constant.WriteLog(Constant.fail, "�����ղ�ʧ��");
						}
					}
				}




			}



		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}
	}


	/**
	 * ��������
	 * @param 
	 * @return
	 */

	public void searchArticle(){
		//�ҵ����ఴť
		try {
			UiObject article = new UiObject(new UiSelector().text(Constant.article)); 
			article.click();

			/**
			 * ���������ť
			 */
			UiCollection toolBarPage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
			UiObject resultLayout;
			resultLayout = toolBarPage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 3);
			UiObject searchImage=resultLayout.getChild(new UiSelector().className("android.widget.ImageView").index(2)); 
			searchImage.clickAndWaitForNewWindow();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}

	}



	/**
	 * ���µ����Լ�ȡ������
	 * @param  ���µ�����ֵ
	 * @return 
	 */
	public void ArticlePraise(int index){

		ArticalItem(index);
		try {
			for(int i=0;i<2;i++){
				if(i==1){
					//�ٴν��е��޲���
					Constant.WriteLog(Constant.info, "�ٴν��е��޲���");
				}
				int praiseBeforeNum = getAriticlePraise();
				Constant.WriteLog(Constant.info, "���ǰ������Ϊ��"+praiseBeforeNum);
				//�ҵ����ް�ť
				UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
				UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 3);
				UiObject praise=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
				praise.click();
				//�жϵ��޺��Ƿ���ת���˵�¼����

				UiObject loginTip = new UiObject(new UiSelector().text(Constant.noLoginTip)); 
				if(loginTip.exists()){
					Constant.WriteLog(Constant.info, "��ǰΪδ��¼״̬�����е�¼����");
					DialogLoginOn();
					ArticlePraise(index);
					break;
				}else{
					int praiseAfterNum = getAriticlePraise();
					Constant.WriteLog(Constant.info, "����������Ϊ��"+praiseAfterNum);

					if(praiseAfterNum-praiseBeforeNum==1){
						Constant.WriteLog(Constant.info, "����Ϊ���ޣ�������+1");
					}else if(praiseBeforeNum-praiseAfterNum==1){
						Constant.WriteLog(Constant.info, "����Ϊȡ�����ޣ�������-1");
					}else{
						Constant.WriteLog(Constant.fail, "���޲���ʧ��");
					}


				}
				if(i==1){
					//�ҵ����ذ�ť
					resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
					UiObject linearLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 2);
					UiObject back =linearLayout.getChild(new UiSelector().className("android.widget.ImageView").index(0)); 
					back.click();
				}
			}


		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();
			Constant.WriteLog(Constant.fail, "���޲���ʧ��");
		}
	}

	/**
	 * 
	 * @param   
	 * @return �������µĵ�����
	 */
	public int getAriticlePraise(){

		int praiseNum =0 ;
		try {
			//��ȡ���µĵ�����
			UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
			UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 4);
			UiObject praiseTextView = frameLayoutPraise.getChild(new UiSelector().className("android.widget.TextView"));
			praiseNum = Integer.valueOf(praiseTextView.getText().toString()).intValue();

		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();

		}

		return praiseNum;
	}


	/**
	 * �������۹���
	 * @param  index
	 * @return
	 * @throws UiObjectNotFoundException 
	 */

	public void  ArticleComment(int index) throws UiObjectNotFoundException{
		ArticalItem(index);

		//�ҵ����۰�ť
		UiCollection resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		UiObject resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject comment=resultLayout.getChild(new UiSelector().className("android.widget.ImageButton")); 
		comment.clickAndWaitForNewWindow();



		//������۰�ť
		UiObject commentTextView = new UiObject(new UiSelector().text(Constant.comment));
		commentTextView.clickAndWaitForNewWindow();

		Constant.WriteLog(Constant.info, "����д����,����յ�����");
		//�������ť
		UiObject publicTextView = new UiObject(new UiSelector().text(Constant.pubic));
		publicTextView.click();

		commentTextView = new UiObject(new UiSelector().text(Constant.comment));
		if(commentTextView.exists()){
			Constant.WriteLog(Constant.fail, "�����۷���ɹ�");
			return ;
		}else{
			Constant.WriteLog(Constant.info, "�����۷���ʧ��");
		}

		Constant.WriteLog(Constant.info, "��д���ݳ���140���ַ�");
		UiObject commentEditText = new UiObject(new UiSelector().text(Constant.saySomething));
		commentEditText.setText(Constant.longString);
		publicTextView = new UiObject(new UiSelector().text(Constant.pubic));
		if(!publicTextView.isClickable()){
			Constant.WriteLog(Constant.info, "��д���ݳ���140���ַ�ʱ������ť���");
		}else{
			Constant.WriteLog(Constant.info, "��д���ݳ���140���ַ�ʱ������ťδ��ɻ�ɫ");
		}
		
		
		Constant.WriteLog(Constant.info, "��д����\"good\"");

		commentEditText = new UiObject(new UiSelector().text(Constant.longString));
		commentEditText.clearTextField();
		commentEditText = new UiObject(new UiSelector().text(Constant.saySomething));
		commentEditText.setText("good");

		//��ȡ�������ݺ������ַ�ʣ�����
		resultspage=new UiCollection(new UiSelector().className(android.widget.LinearLayout.class.getName()));
		resultLayout=resultspage.getChildByInstance(new UiSelector().className(android.widget.LinearLayout.class.getName()), 4);
		UiObject limitNum =resultLayout.getChild(new UiSelector().className(android.widget.TextView.class.getName()));
		int changNum = Integer.valueOf(limitNum.getText().toString()).intValue();
		Constant.WriteLog(Constant.info, "ʣ������Ϊ"+changNum);
		if(TOTALNUM-changNum==4){
			Constant.WriteLog(Constant.info, "���������仯����");
		}else{
			Constant.WriteLog(Constant.fail, "�����������仯����");
			return ;
		}
		//�������
		publicTextView.click();

		//��ȡ��һ�����۵�����
		UiObject listview = new UiObject(new UiSelector().className("android.widget.ListView"));
		UiObject Channel = listview.getChild(new UiSelector().index(1));
		UiObject content = Channel.getChild(new UiSelector().className(android.widget.TextView.class.getName()).instance(3));
		Constant.WriteLog(Constant.info, "�б��һ������Ϊ"+content.getText().toString()); 
		if(content.getText().toString().equals("good")){
			Constant.WriteLog(Constant.info, "�뷢�������������ͬ�����۷���ɹ�"); 
		}else{
			Constant.WriteLog(Constant.fail, "�뷢����������ݲ���ͬ�����۷���ʧ��"); 
		}


	}



	/**
	 * ��ȡ���۸���
	 * @param 
	 * @return
	 */
	public int GetArticleComment() throws UiObjectNotFoundException{
		int commentNum =0 ;
		UiCollection frameLayoutCollect=new UiCollection(new UiSelector().className(android.widget.FrameLayout.class.getName()));
		UiObject frameLayoutPraise = frameLayoutCollect.getChildByInstance(new UiSelector().className(android.widget.FrameLayout.class.getName()), 5);
		UiObject commentTextView = frameLayoutPraise.getChild(new UiSelector().className("android.widget.TextView"));
		commentNum = Integer.valueOf(commentTextView.getText().toString()).intValue();
		return commentNum;
	}
}
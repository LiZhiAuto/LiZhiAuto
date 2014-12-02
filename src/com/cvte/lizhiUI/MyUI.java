package com.cvte.lizhiUI;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class MyUI {
	
	/**
	 * 获取同一个parent下的Object对象
	 * @param object
	 * @param objectStr
	 * @param index
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public UiObject GetUnderSameParentObject(UiObject object,String objectStr,int index) throws UiObjectNotFoundException{
		UiObject sexTv =  object.getFromParent(new UiSelector().className(objectStr).index(index));
		if(sexTv.exists()){
			return sexTv;
		}else{
			return null;
		}
	}
	
	

}

package com.knziha.plugin101;


import java.lang.reflect.Method;

public class DarkToggle implements DarkToggleInterface{
	final Object mDelegate;
	final Method mToggle;
	
	public DarkToggle(Object mDelegate) throws NoSuchMethodException {
		this.mDelegate = mDelegate;
		mToggle = mDelegate.getClass().getMethod("toggle");
	}
	
	public void toggle() {
		try {
			mToggle.invoke(mDelegate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package com.knziha.plugin101;


import android.content.Context;

import java.lang.reflect.Method;

public class TesseractPluginTest{
	final Method mTest;
	
	public TesseractPluginTest(Class clazz) throws NoSuchMethodException {
		mTest = clazz.getMethod("Test", Context.class);
	}
	
	public void Test(Context context) {
		try {
			mTest.invoke(null, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
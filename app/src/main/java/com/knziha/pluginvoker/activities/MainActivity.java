package com.knziha.pluginvoker.activities;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.knziha.plugin101.DarkToggle;
import com.knziha.plugin101.PluginTests;
import com.knziha.pluginvoker.R;
import com.knziha.pluginvoker.databinding.FragmentSecondBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.AttributeSet;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;

public class MainActivity extends AppCompatActivity {
	FragmentSecondBinding fragmentSecondBinding;
	View darkToggleBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		fragmentSecondBinding = FragmentSecondBinding.inflate(getLayoutInflater());
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show());
		
		// needs pre-installing com.knziha.plugin ( the darkToggleButton demo )
		findViewById(android.R.id.content).postDelayed(() -> {
			
			TextView txvA = findViewById(R.id.textview_first);
			Class TesseractPluginTestClazz = null;
			Class PrivatePathTestClazz = null;
			Context context = null;
			try {
				String pluginPkg = "com.knziha.plugin101";
				context = createPackageContext(pluginPkg, Context.CONTEXT_INCLUDE_CODE
						| Context.CONTEXT_IGNORE_SECURITY
						| Context.CONTEXT_RESTRICTED
				);
				Class<?> cls = context.getClassLoader().loadClass(pluginPkg+".R"); // 获得目标apk的R类
				txvA.setText(context.getResources().getText(getResourseIdByName(cls, "string", "message")));
				
				Class<?> darkToggleClass = context.getClassLoader().loadClass(pluginPkg+".DarkToggleButton");
				Constructor<?> cons = darkToggleClass.getConstructor(Context.class, AttributeSet.class);
				darkToggleBtn = (View) cons.newInstance(this, null);
				((ViewGroup)txvA.getParent()).addView(darkToggleBtn);
				
				TesseractPluginTestClazz = context.getClassLoader().loadClass(pluginPkg+".TesseractPluginTest");
				PrivatePathTestClazz = context.getClassLoader().loadClass(pluginPkg+".PrivatePathTest");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			
			try {
				DarkToggle darkToggle = new DarkToggle(darkToggleBtn);
				darkToggle.toggle();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			try {
				new PluginTests(PrivatePathTestClazz).Test(context);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			try {
				// 需要sdk卡读写权限、需要自己下载模型文件
				new PluginTests(TesseractPluginTestClazz).Test(context);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}, 200);
	}

	private int getResourseIdByName(Class<?> clazz, String className, String name) {
		int id = 0;
		try {
			Class<?>[] classes = clazz.getClasses(); // 获取R.java里的所有静态内部类
			Class<?> desireClass = null;
			for (int i = 0; i < classes.length; i++) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}
			if (desireClass != null)
				id = desireClass.getField(name).getInt(desireClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
package com.knziha.pluginvoker.activities;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
		
		findViewById(android.R.id.content).postDelayed(() -> {
			TextView txvA = findViewById(R.id.textview_first);
			try {
				String pluginPkg = "com.knziha.plugin101";
				Context context = createPackageContext(pluginPkg, Context.CONTEXT_INCLUDE_CODE
						| Context.CONTEXT_IGNORE_SECURITY);
				Class<?> cls = context.getClassLoader().loadClass(pluginPkg+".R"); // 获得目标apk的R类
				txvA.setText(context.getResources().getText(getResourseIdByName(cls, "string", "message")));
				
				Class<?> darkToggleClass = context.getClassLoader().loadClass(pluginPkg+".DarkToggleButton");
				Constructor<?> cons = darkToggleClass.getConstructor(Context.class, AttributeSet.class);
				View darkToggleBtn = (View) cons.newInstance(this, null);
				((ViewGroup)txvA.getParent()).addView(darkToggleBtn);
				
			} catch (Exception e) {
				e.printStackTrace();
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
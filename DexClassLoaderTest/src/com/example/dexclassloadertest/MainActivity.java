package com.example.dexclassloadertest;

import java.io.File;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.dexclassloadertestlib.DexClassLoaderLibActivity;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, DexClassLoaderLibActivity.class);
				startActivity(it);
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File optimizedDirectory = new File(getFilesDir(), "optimizedDirectory");
				if (!optimizedDirectory.exists()) {
					optimizedDirectory.mkdirs();
				}
				
//				String dexPath = getDir("assets", MODE_PRIVATE).getAbsolutePath();
				File dexPath = new File(getFilesDir(), "dexPath");
				if (!dexPath.exists()) {
					dexPath.mkdirs();
				}

				File apkFile = new File(dexPath, "DexClassLoaderApk.apk");
				
				Log.d(TAG, "dexPath:" + apkFile.getAbsolutePath() + "\n optimizedDirectory:" + 
						optimizedDirectory.getAbsolutePath());
				DexClassLoader dcl = new DexClassLoader(
						apkFile.getAbsolutePath(), 
						optimizedDirectory.getAbsolutePath(), 
						null, getClassLoader());
				
				Class cls = null;
				try {
					cls = Class.forName("com.example.dexclassloaderapk.ApkMainActivity", true, dcl);
				} catch (Throwable t) {
					t.printStackTrace();
				}
				
//				ComponentName cn = new ComponentName("com.example.dexclassloaderapk", cls.getName());
//				ComponentName cn = new ComponentName("com.example.dexclassloadertest", cls.getName());
				
				Intent it = new Intent(MainActivity.this, cls);
//				it.setComponent(cn);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

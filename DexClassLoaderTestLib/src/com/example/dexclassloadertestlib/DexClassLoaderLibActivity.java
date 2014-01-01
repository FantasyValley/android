package com.example.dexclassloadertestlib;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class DexClassLoaderLibActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources();
		int mainLayoutId = res.getIdentifier("lib_activity_main", "layout", getPackageName());
		setContentView(mainLayoutId);
	}

}

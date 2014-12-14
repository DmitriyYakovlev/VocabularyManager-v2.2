package com.yakovlev.prod.vocabularymanager;



import com.yakovlev.prod.vocabularymanager.constants.Const;
import com.yakovlev.prod.vocabularymanger.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class LogoActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		pauseForLogoActivity(MenuActivity.class);
	}

	
	private void pauseForLogoActivity(final Class<?> classOfActivityForStart) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(getApplicationContext(), classOfActivityForStart);
				startActivity(intent);
			}
		}, Const.PAUSE_FOR_SPLASH_SCREEN_IN_SECONDS * 1000);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		super.finish();
	}
	
}

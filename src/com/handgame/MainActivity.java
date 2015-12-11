package com.handgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the entry point of the app for now it's just showing a basic menu
 * 
 * @author Nenad Palinkasevic
 *
 */
public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button calibrationButton = (Button)findViewById(R.id.CalibrationButton);
		Button exitButton = (Button)findViewById(R.id.ExitButton);
		
		
		calibrationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i =  new Intent(MainActivity.this, CameraActivity.class);
				startActivity(i);
				
			}
		});
		
		
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Process.killProcess(Process.myPid());
				System.exit(0);
				
			}
		});
		
	}

	


}

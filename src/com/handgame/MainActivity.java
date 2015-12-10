package com.handgame;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

public class MainActivity extends Activity implements CvCameraViewListener2
{

	private JavaCameraView mCameraView = null;
	
	//anonimna callback klasa koristimo kad se konektujemo na opencv
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this)
	{
		@Override
		public void onManagerConnected(int status)
		{
			switch(status)
			{
				case LoaderCallbackInterface.SUCCESS:
				{
					mCameraView.enableView(); //ako se konektovao za cv enablujemo kameru
					break;
				}
				default:
				{
					super.onManagerConnected(status);
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//nalazimo nas camera widget i ukljucimo da je vidljiv
		mCameraView = (JavaCameraView)findViewById(R.id.handgame_surface_view);
		mCameraView.setVisibility(SurfaceView.VISIBLE);
		
		//postavimo da je listener za cameru ova klasa jer i mi u ovoj klasi imeplemtiramo camera listener
		mCameraView.setCvCameraViewListener(this);
		
	}

	
	@Override
	public void onResume()
	{
		super.onResume();
		
		//just as our app is loaded we call a helper class to bind activity to opencv service
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
	}


	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		
		//u sustini ovde dobijamo ulazni frame i mogli bi raditi neke manipulacije
		
		return inputFrame.gray();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		if(mCameraView != null)
		{
			mCameraView.disableView();
		}
	}

}

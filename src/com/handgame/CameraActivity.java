package com.handgame;

import hand_recognition.HandDetection;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends Activity implements CvCameraViewListener2
{

	private JavaCameraView mCameraView = null;
	private Mat result = null;
	private HandDetection handDetector = null;
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//da odemo fullscreen
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_camera);

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
		result = new Mat();
		handDetector = new HandDetection();
		
	}


	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		result.release();
		handDetector.dispose();
		
	}


	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		
		//u sustini ovde dobijamo ulazni frame i mogli bi raditi neke manipulacije
		
		//handDetector.adaptiveThreshold(inputFrame.gray(), result);
		handDetector.colorBasedThreshold(inputFrame.rgba(), result);
		
		return result;
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

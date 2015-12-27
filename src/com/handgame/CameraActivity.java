package com.handgame;

import hand_recognition.HandDetection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends Activity implements CvCameraViewListener2
{

	private JavaCameraView mCameraView = null;
	private Mat result = null;
	private Mat handImg = null;
	
	private HandDetection handDetector = null;
	private boolean screenTouched = false;
	private Scalar avgColor = null;
	
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
		mCameraView.setFocusable(false);
				
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
		handImg = new Mat();
		handDetector = new HandDetection();
		
	}


	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		result.release();
		handImg.release();
		handDetector.dispose();
		
	}


	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) 
	{
		//starts the adaptive threshold and saves picture when taped screen
		
		handDetector.adaptiveThreshold(inputFrame.gray(), result);
		
		Imgproc.resize(result, handImg, new Size(32, 32));

		if(screenTouched == true)
		{
			saveImg(handImg);
			screenTouched = false;
		}
		
		
		
		//return inputFrame.rgba();
		return result;
	}
	
	
	//TODO: get 5 or so threshold image and merge them for better results
	/**
	 * User puts the palm in the middle and it scans it's average color and whan he tap's it
	 * it does the color based threshold of the hand 
	 * 
	 * 
	 * @param inputFrame
	 * @return
	 */
	private Mat colorHandDetection(CvCameraViewFrame inputFrame)
	{
		
		if(screenTouched == false)
		{
			avgColor = handDetector.getHandColor(inputFrame.rgba());
			
		}
		else
		{
			
			Log.d("avg", avgColor.toString());
			handDetector.colorBasedThreshold(inputFrame.rgba(), result, avgColor);
			
			return result;

		}
		
		Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2RGBA);
		//960x720 je ekran
		Imgproc.rectangle(result, new Point(480 - 100, 360 - 100), new Point(580, 460), new Scalar(255, 0, 0));
		
		return inputFrame.rgba();
	}
	
	/**
	 * Saves a openCV Mat as an image on the memory of the phone in the picture directory/handgame
	 * to work handgame folder on the phone is needed!!!
	 * 
	 * @param imgMat
	 */
	private void saveImg(Mat imgMat)
	{
		File path = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
		
		Log.d("putanja", path.toString());
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		
		File file = new File(path + "/handgame", "image" + timeStamp +  ".png");
		Imgcodecs.imwrite(file.toString(), handImg);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		screenTouched = true;
		return super.onTouchEvent(event);
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

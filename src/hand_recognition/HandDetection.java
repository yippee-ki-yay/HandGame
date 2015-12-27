package hand_recognition;

import java.util.ArrayList;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;
/**
 * 
 * A class that transforms the input image so it extracts just the hand from the image
 * 
 * @author Nenad Palinkasevic
 *
 */
public class HandDetection 
{
	private ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	private Mat mHierarchy = new Mat();
	
	private Random random = new Random();
	
	public HandDetection()
	{
	}

	public void dispose()
	{
		mHierarchy.release();
	}
	
	public void adaptiveThreshold(Mat inputMat, Mat result)
	{
		Imgproc.adaptiveThreshold(inputMat, result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 4);
		
		//Imgproc.findContours(result, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		//Log.d("broj", " " + contours.size());
	}
	
	public void colorBasedThreshold(Mat inputMat, Mat result, Scalar avgColor)
	{
		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_RGB2HSV);
		Core.inRange(inputMat, offsetScalar(avgColor, -20), new Scalar(60, 215, 225), result);
		
	}
	
	/**
	 * Gets the avg color of the points we got out of the center of the screen
	 * @param inputMat
	 * @return
	 */
	public Scalar getHandColor(Mat inputMat)
	{
		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_RGB2HSV);
		
		ArrayList<double[]> colorPoints = getColorPoints(inputMat);
		
		Scalar avgColor = getAverageColor(colorPoints);
		
		return avgColor;
	}
	
	/**
	 * Get's 9 random points around the center of the image
	 * image must be over 60px, 60px for it not to go out off bounds
	 * @param inputMat
	 * @return
	 */
	private ArrayList<double[]> getColorPoints(Mat inputMat)
	{
		//memory for the 3 hsv value each pixel in image has
		double data[] = new double[inputMat.channels()];
		
		ArrayList<double[]> points = new ArrayList<double[]>();
		
		//get hvs scalar value of 9 points in the image around the center (2,30) px offset around
		for(int i = 0; i < 9; ++i)
		{
			int r = random.nextInt(30 - 2 + 1) + 2; //random number between 2 and 30
			data = inputMat.get(inputMat.rows()/2 + r, inputMat.cols()/2 +r);
			points.add(data);
		}
		
		return points;
	}
	
	/**
	 * Gets a list of pixel hsv values and returns and average hsv value for all the points
	 * @param points
	 * @return
	 */
	private Scalar getAverageColor(ArrayList<double[]> points)
	{
		int size = points.size();
		
		double hSum = 0;
		double sSum = 0;
		double vSum = 0;
		
		for(double[] d : points)
		{
			if(d.length == 3)
			{
				hSum += d[0];
				sSum += d[1];
				vSum += d[2];
			}
		}
		
		return new Scalar(hSum/size, sSum/size, vSum/size);
	}
	
	/**
	 * Given a scalar s and a int value return a new scalar
	 * that is offseted in all 3 (hsv) data for the exact amount
	 * @param s
	 * @param value
	 * @return
	 */
	private Scalar offsetScalar(Scalar s, int value)
	{
		Scalar offseted = new Scalar(0,0,0);
		
		for(int i = 0; i < s.val.length; ++i)
		{
			offseted.val[i] = s.val[i] + value;
		}
		
		return offseted;
	}
	
	
	
}

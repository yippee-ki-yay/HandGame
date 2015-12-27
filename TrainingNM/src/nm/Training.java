package nm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Training 
{
	private ArrayList<TrainingPair> imgSet = new ArrayList<TrainingPair>();
	
	/**
	 * In our mapping file the class type is as String here we map it do double[]
	 * This is specific to our curret mapping
	 */
	public static double[] mapOutput(String classType)
	{
		if(classType.equals("ruka"))
		{
			return new double[] {1, 0};
		}
		else if(classType.equals("none"))
		{
			return new double[] {0, 1};
		}
		
		return null;
	}
	                      
	/**
	 * Loads the file that contains image_name : category information in an array list
	 * @param filename
	 */
	public void loadMapping(String filename)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		       String[] parts = line.split(":");
		       
		       if(parts.length == 2)
		       {
		    	   TrainingPair tmpSet = new TrainingPair();
		    	   tmpSet.setImgName(parts[0].trim());
		    	   tmpSet.setClassType(parts[1].trim());
		    	   imgSet.add(tmpSet);
		       }
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get's an image from the img folder and returns a 1D array of values
	 * @param imgName
	 * @return
	 */
	public static double[] addImg(String imgName)
	{
		Mat imgMat = Imgcodecs.imread("img/" + imgName);
		
		double[] imgArr = new double[imgMat.rows()*imgMat.cols()];
		
		if(imgMat.isContinuous())
		{	
			for(int i = 0; i < imgMat.rows(); ++i)
			{
				for(int j = 0; j < imgMat.cols(); ++j)
				{
					imgArr[(i * imgMat.rows()) + j] = imgMat.get(i, j)[0];
				}
				
			}
		}
		
		return imgArr;
	}
	
	/**
	 * Loads the data set and starts the neural network training
	 * 
	 * @param nmFile the name of the file the network will be saved
	 */
	public void train(String nmFile)
	{
		
		loadMapping("mapping.txt.txt");
		
		DataSet data = new DataSet(32*32, 2);
		
		for(TrainingPair t : imgSet)
		{
			data.addRow(addImg(t.getImgName()), mapOutput(t.getClassType()));
		}
		
		System.out.println("DataSet loaded!");
		
		MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 32*32, 4, 2);
		
		System.out.println("Training has started");
		
		myMlPerceptron.learn(data);
		
		myMlPerceptron.save(nmFile);
		
		System.out.println("You have trained the network");
		
	}
}

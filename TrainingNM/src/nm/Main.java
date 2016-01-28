package nm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.error.MeanSquaredError;
import org.opencv.core.Core;

public class Main 
{

	public static void testNM(String testImg, NeuralNetwork nnet)
	{
		double[] input = Training.addImg(testImg);
    	
    	nnet.setInput(input);
    	nnet.calculate();
    	double[] result = nnet.getOutput();
    	
    	System.out.println("Rezultat: {" + result[0] + " , " + result[1] + " }");
	}
	
	public static void main(String[] args) 
	{
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
	    //Training t = new Training();
		
    	//t.train("mlpTest.nnet");
    	
		// load saved neural network
        //NeuralNetwork nnet = NeuralNetwork.createFromFile("simple_mlp_2.nnet");
    	NeuralNetwork nnet = NeuralNetwork.createFromFile("mlpTest.nnet");
    	
    	/*File initialFile = new File("simple_mlp_2.nnet");
    	File initialFile = new File("test29.nnet");
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NeuralNetwork nnet = NeuralNetwork.load(targetStream);
    	*/
    	testNM("nohand2.png", nnet);
    	testNM("hand1.png", nnet);
    	testNM("hand3.png", nnet);
    	testNM("1.png", nnet);
    	testNM("2.png", nnet);
    	testNM("3.png", nnet);
    	testNM("4.png", nnet);
    	testNM("5.png", nnet);
    	testNM("6.png", nnet);
    	testNM("7.png", nnet);
    	testNM("8.png", nnet);
    	testNM("9.png", nnet);
    	testNM("10.png", nnet);
    	testNM("11.png", nnet);
    	testNM("12.png", nnet);
    	
	}

}

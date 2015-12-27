package nm;

import org.neuroph.core.NeuralNetwork;
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
		
		Training t = new Training();
		
    	t.train("simple_mlp_2.nnet");
    	
    	// load saved neural network
    	NeuralNetwork nnet = NeuralNetwork.createFromFile("simple_mlp_2.nnet");

    	
    	testNM("nohand2.png", nnet);
    	testNM("hand1.png", nnet);
    	testNM("hand3.png", nnet);
    	
	}

}

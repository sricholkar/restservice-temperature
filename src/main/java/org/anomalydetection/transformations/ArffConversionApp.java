package org.anomalydetection.transformations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class ArffConversionApp {

	public void convert(String input, String output) throws IOException {
	System.out.println(input);
	File inputFile = new File(input);
	System.out.println(inputFile.exists());
		
		//load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(inputFile);
		Instances dataset = loader.getDataSet();
	
		//save Arff
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		writer.write(dataset.toString());
		writer.flush();
		writer.close();

	}
}

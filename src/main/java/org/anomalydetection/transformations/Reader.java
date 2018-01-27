package org.anomalydetection.transformations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class Reader {

	public static Instances readData(String input) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(input));
    	Instances dataset = new Instances(reader);
    	dataset.setClassIndex(dataset.numAttributes()-1);
    	dataset.deleteAttributeAt(0);
    	reader.close();
		return NormalizeData.normalize(dataset);
	}
}

package org.anomalydetection.transformations;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class NormalizeData {

	public static Instances normalize(Instances data) throws Exception  {
		Normalize norm = new Normalize();
		try {
			norm.setInputFormat(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Filter.useFilter(data, norm);
	}
}

package org.anomalydetection.restserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.anomalydetection.transformations.Reader;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * Root resource 
 */
@Path("algorithm")
public class MyResource {

	String path = File.listRoots()[2] + "workspace\\restserver\\target\\resources\\";
	File input  = new File(path + "full_train.csv");
	String inArff = path + "full_train.arff";

    
	@GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream getIt() throws Exception {
    	File file = new File(File.listRoots()[2] + "workspace\\restserver\\target\\resources\\nb.model");
    	InputStream is = new FileInputStream(file);
    	
		return is;
    }
	
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void addInstance(String instance) throws IOException, Exception {
    	Instances instances = Reader.readData(inArff);
    	Attribute att1 = new Attribute("firstNumeric");
		  Attribute att2 = new Attribute("secondNumeric");
		  Attribute att3 = new Attribute("thirdNumeric");
		  List<String> classValues = new ArrayList<String>(2); 
		  classValues.add("yes");
		  classValues.add("no");
		  Attribute att4 = new Attribute("theClass", classValues);
		  ArrayList<Attribute> atts = new ArrayList<Attribute>();
		  atts.add(att1);
		  atts.add(att2);
		  atts.add(att3);
		  atts.add(att4);
		// Create an empty training set
    	  Instances isTrainingSet = new Instances("temperature instaces", atts, 10);
    	  // Set class index
    	  isTrainingSet.setClassIndex(3);
    	  
		Instance inst = new DenseInstance(5);
		String[] strInst = instance.split(",");
	  	for (int i = 0; i < strInst.length - 1; i++)
  		  inst.setValue(atts.get(i), Double.parseDouble(strInst[i]));
  	
  	    inst.setValue(atts.get(3), String.valueOf(strInst[3]));
    	
    	instances.add(inst);
//    	File file = new File(File.listRoots()[2] + "workspace\\restserver\\target\\resources\\full_train.csv");
//    	FileWriter pw = new FileWriter(file, true);
//    	pw.append(instance);
//    	pw.append("\n");
//    	pw.flush();
//    	pw.close();
		trainAndSave(instances);
//    	return String.valueOf(file.exists());
    }
    
    private void trainAndSave(Instances instances) throws Exception {
      Classifier classifier = new NaiveBayes();
      classifier.buildClassifier(instances);
      SerializationHelper.write(File.listRoots()[2] + "workspace\\restserver\\target/resources/nb.model", classifier);
	}


    
   
}

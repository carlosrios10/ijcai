package ijcai.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import libsvm.svm;
import weka.classifiers.Classifier;
import weka.classifiers.MultipleClassifiersCombiner;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class IjcaiUtils {

	public static int getPositiveClassIndex(Instances instances) {
		return instances.classAttribute().indexOfValue("S");
	}
	
	public static String join(String[] parts) {
		return join(parts, parts.length);
	}
	
	public static String join(String[] parts, int to) {
		return join(parts, 0, to);
	}

	public static String join(String[] parts, int from, int to) {
		return Arrays
			.stream(parts)
			.skip(from)
			.limit(to)
			.reduce((total, part) -> String.join(",", total, part))
			.get();
	}

	public static Instances read(String file) throws IOException {
		return new ArffReader(Files.newBufferedReader(Paths.get(file))).getData();		
	}

	public static Classifier buildClassifier() {
		// ensemble: MultiScheme, Vote, Stacking
//		Classifier cls = new BayesNet();
//		Classifier cls = new RandomTree();
		RandomForest cls = new RandomForest();
		cls.setNumTrees(1000);;
//		MultipleClassifiersCombiner cls = new Vote();
//		cls.setClassifiers(new Classifier[] {new BayesNet(), new HoeffdingTree(), new DecisionStump(), new DecisionTable()});
		//cls.setClassifiers(new Classifier[] {new BayesNet(), new RandomTree(),new LibSVM(), new Logistic()});
		//cls.setClassifiers(new Classifier[] {new LibSVM()});
		System.out.printf("Classifier: %s%n", cls.getClass().getSimpleName());
		return cls;
	}

}

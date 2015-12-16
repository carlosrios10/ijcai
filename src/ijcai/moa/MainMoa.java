package ijcai.moa;
//package ijcai;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import moa.classifiers.bayes.NaiveBayes;
//import moa.streams.ArffFileStream;
//import weka.core.Instance;
//
//public class MainMoa {
//
//	public static void main(String[] args) throws IOException {
//		MainMoa moa = new MainMoa();
//		moa.run(2_816_955);
//		
////		EvaluateModel em = new EvaluateModel(new NaiveBayes(), new ArffFileStream("train_format21.arff", 10), new BasicClassificationPerformanceEvaluator(), 10_000_000);
////		System.out.println(em.doTask());
//		
////		try (Stream<String> stream = Files.lines(Paths.get("c:/temp/ijcai/train_format2.arff"))) {
////			System.out.println("clase0: " + stream.filter(l -> l.endsWith("0")).count());
////		}
////		
////		try (Stream<String> stream = Files.lines(Paths.get("c:/temp/ijcai/train_format2.arff"))) {
////			System.out.println("clase1: " + stream.filter(l -> l.endsWith("1")).count());
////		}
//	}
//
//	public void run(int numInstances) {
//		// stream
//		ArffFileStream stream = new ArffFileStream("c:/temp/ijcai/train_format2.arff", 10);
//		stream.prepareForUse();
//
//		// classifier
//		NaiveBayes learner = new NaiveBayes();
////		RandomHoeffdingTree learner = new RandomHoeffdingTree();
////		WeightedMajorityAlgorithm learner = new WeightedMajorityAlgorithm();
////		AccuracyWeightedEnsemble learner = new AccuracyWeightedEnsemble();
//		learner.setModelContext(stream.getHeader());
//		learner.prepareForUse();
//
//		int numInstancesToTrain = (int)(numInstances * 0.66);
//		int numInstancesToTest = numInstances - numInstancesToTrain;
//		int numberSamples = 0;
//		long[] vector = new long[2];
//		float[] classes = new float[2];
//
//		// train
//		while (stream.hasMoreInstances() && numberSamples < numInstancesToTrain) {
//			Instance trainInst = stream.nextInstance();
//			learner.trainOnInstance(trainInst);
//			numberSamples++;
//		}
//
//		// test
//		while (stream.hasMoreInstances() && numberSamples < numInstances) {
//			Instance testInst = stream.nextInstance();
//			updatePrediction(vector, learner.getVotesForInstance(testInst));
//			updateClass(classes, testInst);		
//			numberSamples++;
//		}
//		
//		System.out.printf("%d train instances%n", numInstancesToTrain);
//		System.out.printf("%d test instances%n", numInstancesToTest);
//		System.out.printf("%d instances%n", numberSamples);
//		
//		System.out.println(Arrays.toString(vector));
//		System.out.println(Arrays.toString(classes));
//	}
//	
//	private void updatePrediction(long[] vector, double[] prediction) {
//		int index = prediction[0] > prediction[1] ? 0 : 1;
//		vector[index]++;
//	}
//	
//	private void updateClass(float[] vector, Instance testInst) {
//		int clazz = Integer.valueOf(testInst.stringValue(9));
//		vector[clazz]++;
//	}
//	
//}

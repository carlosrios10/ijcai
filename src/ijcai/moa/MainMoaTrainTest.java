package ijcai.moa;
//package ijcai;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Locale;
//
//import moa.classifiers.bayes.NaiveBayes;
//import moa.streams.ArffFileStream;
//import weka.core.Instance;
//
//public class MainMoaTrainTest {
//
//	public static void main(String[] args) throws IOException {
//		MainMoaTrainTest moa = new MainMoaTrainTest();
//		moa.run();
//	}
//
//	public void run() {
//		// stream
//		ArffFileStream streamTrain = new ArffFileStream("c:/temp/ijcai/train_format2.arff", 10);
//		streamTrain.prepareForUse();
//
//		ArffFileStream streamTest = new ArffFileStream("c:/temp/ijcai/test_format2.arff", 10);
//		streamTest.prepareForUse();
//		
//		// classifier
//		NaiveBayes learner = new NaiveBayes();
////		RandomHoeffdingTree learner = new RandomHoeffdingTree();
////		WeightedMajorityAlgorithm learner = new WeightedMajorityAlgorithm();
////		AccuracyWeightedEnsemble learner = new AccuracyWeightedEnsemble();
//		learner.setModelContext(streamTrain.getHeader());
//		learner.prepareForUse();
//
//		// train
//		while (streamTrain.hasMoreInstances()) {
//			Instance trainInst = streamTrain.nextInstance();
//			learner.trainOnInstance(trainInst);
//		}
//
//		try (PrintWriter csv = new PrintWriter("c:/temp/ijcai/classification.csv");) {
//			csv.printf("user_id,merchant_id,prob%n");
//			
//			// test
//			while (streamTest.hasMoreInstances()) {
//				Instance testInst = streamTest.nextInstance();
//				
//				double[] prediction = learner.getVotesForInstance(testInst);
//				double total = prediction[0] + prediction[1];
//				
//				csv.printf(Locale.ENGLISH, "%s,%s,%4.3f%n", (int)testInst.value(0), (int)testInst.value(3), prediction[1]/total);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	
//}

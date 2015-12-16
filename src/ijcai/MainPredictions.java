package ijcai;

import ijcai.utils.IjcaiUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Agregar en "Run configurations..." -> Arguments -> VM arguments el valor -Xmx4096m
 * 
 * @author Juan
 *
 */
public class MainPredictions {
	private static final String arffUserLog = "c:/temp/ijcai/userLog.arff";
	private static final String arffTestFinal = "c:/temp/ijcai/testFinal.arff";
	private static final String csvPredictionsAll = "c:/temp/ijcai/predictions_all.csv";
	private static final String csvPredictions = "c:/temp/ijcai/predictions.csv";
	
	
	public static void main(String[] args) throws Exception {
		predict();
		summarize();
	}
	
	public static void predict() throws Exception {
		// loads data and set class index
		Instances train = IjcaiUtils.read(arffUserLog);
//		train.deleteAttributeAt(0); // remove user_id
		train.setClassIndex(train.numAttributes() - 1);
		int positiveClassIndex = IjcaiUtils.getPositiveClassIndex(train);

		Instances test = IjcaiUtils.read(arffTestFinal);
		Instances test2 = IjcaiUtils.read(arffTestFinal);
//		test.deleteAttributeAt(0); // remove user_id
		test.setClassIndex(test.numAttributes() - 1);
		
		// classifier
		Classifier cls = IjcaiUtils.buildClassifier();
		cls.buildClassifier(train);

		// predict
		try (PrintWriter pred = new PrintWriter(csvPredictionsAll)) {
			// header
			pred.println("user_id,merchant_id,prob");
			
			// data
			for (int n = 0; n < test.numInstances(); n++) {
				Instance i = test.instance(n);
				Instance i2 = test2.instance(n);
				pred.printf(Locale.ENGLISH, "%.0f,%.0f,%4.3f%n", i2.value(0), i2.value(1), cls.distributionForInstance(i)[positiveClassIndex]);
			}
		}
	}
	
	private static void summarize() throws IOException {
		try (Stream<String> stream = Files.lines(Paths.get(csvPredictionsAll))) {
			Map<Integer, Map<Integer, Double>> grouping = 
				stream
				.skip(1)
				.map(Prediction::new)
				.collect(
					Collectors.groupingBy(
						Prediction::getUserId, 
						Collectors.groupingBy(
							Prediction::getMerchantId,
							Collectors.averagingDouble(Prediction::getProb)
						)
					)
				);

			try (PrintWriter pred = new PrintWriter(csvPredictions)) {
				// header
				pred.println("user_id,merchant_id,prob");

				// data
				grouping
					.entrySet()
					.stream()
					.forEach(e1 -> e1
						.getValue()
						.entrySet()
						.stream()
						.forEach(e2 -> pred
							.printf(Locale.ENGLISH, "%d,%d,%4.3f%n", e1.getKey(), e2.getKey(), e2.getValue())
						)
					);
			}
		}
	}
	
	private static class Prediction {
		int userId;
		int merchantId;
		double prob;
		
		public Prediction(String line) {
			String[] parts = line.split(",");
			userId = Integer.valueOf(parts[0]);
			merchantId = Integer.valueOf(parts[1]);
			prob = Double.valueOf(parts[2]);
		}

		public int getUserId() {
			return userId;
		}

		public int getMerchantId() {
			return merchantId;
		}

		public double getProb() {
			return prob;
		}
	}

}
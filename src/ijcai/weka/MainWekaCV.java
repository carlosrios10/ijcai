package ijcai.weka;

import ijcai.utils.IjcaiUtils;

import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class MainWekaCV {
	private static final String arffUserLog = "C:/Users/Usuarioç/Desktop/carlos/IJCAI-Competencia/final_data_set/procesados_comprasventas/data_final_userLog_compras-ventas.arff";
	

	public static void main(String[] args) throws Exception {
		// loads data and set class index
		Instances data = IjcaiUtils.read(arffUserLog);
		data.deleteAttributeAt(1); // remove user_id
//		data.deleteAttributeAt(0); // remove user_id

		// print attributes
		for (int i = 0; i < data.numAttributes(); i++) {
			System.out.println(data.attribute(i).name());
		}
		
		// get indexes
		int classIndex = data.numAttributes() - 1;
		data.setClassIndex(classIndex);
		int positiveClassIndex = IjcaiUtils.getPositiveClassIndex(data);
		
		// classifier
		Classifier cls = IjcaiUtils.buildClassifier();

		// other options
		int runs = 1;
		int folds = 5;

		// perform cross-validation
		for (int i = 0; i < runs; i++) {
			// randomize data
			int seed = i + 1;
			Random rand = new Random(seed);
			Instances randData = new Instances(data);
			randData.randomize(rand);
//			if (randData.classAttribute().isNominal())
//				randData.stratify(folds);

			Evaluation eval = new Evaluation(randData);
			for (int n = 0; n < folds; n++) {
				System.out.printf("Fold %d%n", n);
				
				Instances train = randData.trainCV(folds, n);
				Instances test = randData.testCV(folds, n);
				// the above code is used by the StratifiedRemoveFolds filter,
				// the code below by the Explorer/Experimenter:
				// Instances train = randData.trainCV(folds, n, rand);

				// build and evaluate classifier
				Classifier clsCopy = AbstractClassifier.makeCopy(cls);
				clsCopy.buildClassifier(train);
				eval.evaluateModel(clsCopy, test);
			}

			// output evaluation
			System.out.println();
			System.out.println(eval.toSummaryString("=== " + folds + "-fold Cross-validation run " + (i + 1) + "===", false));
			System.out.printf("ROC = %f%n", eval.areaUnderROC(positiveClassIndex));
			System.out.printf("PRC = %f%n", eval.areaUnderPRC(positiveClassIndex));
		}
	}
	
}
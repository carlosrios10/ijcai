package ijcai.weka;

import ijcai.utils.IjcaiUtils;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;

public class MainWekaAttributeEval {
	private static final String arffUserLog = "c:/temp/ijcai/data_final_userLog_compras-ventas.arff";
	

	public static void main(String[] args) throws Exception {
		// loads data and set class index
		Instances data = IjcaiUtils.read(arffUserLog);
//		data.deleteAttributeAt(0); // remove user_id

		int classIndex = data.numAttributes() - 1;
		data.setClassIndex(classIndex);
		
		// correlation
		System.out.println("================= CORRELATION =================");
		CorrelationAttributeEval correlation = new CorrelationAttributeEval();
		correlation.buildEvaluator(data);
		for (int i = 0; i < data.numAttributes(); i++) {
			String attr = data.attribute(i).name();
			System.out.printf("%-20s = %6.5f%n", attr, correlation.evaluateAttribute(i));
		}

		// information gain
		System.out.println("================= INFORMATION GAIN =================");
		InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
		infoGain.buildEvaluator(data);
		for (int i = 0; i < data.numAttributes(); i++) {
			String attr = data.attribute(i).name();
			System.out.printf("%-20s = %6.5f%n", attr, infoGain.evaluateAttribute(i));
		}
	}
	
}
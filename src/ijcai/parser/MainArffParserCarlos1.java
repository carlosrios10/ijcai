package ijcai.parser;

import ijcai.utils.IjcaiUtils;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * data_final_testFinal
 * 
 * @author Juan
 *
 */
public class MainArffParserCarlos1 {

	public static void main(String[] args) throws Exception {
		String csvFileName = "c:/Temp/ijcai/data_final_userLog.csv";
		String arffFileName = csvFileName.replace(".csv", ".arff");
		
		try (Stream<String> stream = Files.lines(Paths.get(csvFileName))) {
			try (PrintWriter arff = new PrintWriter(arffFileName)) {
				// header
				arff.printf("@RELATION ijcai%n%n");
				
				arff.printf("@ATTRIBUTE user_id NUMERIC%n");
				arff.printf("@ATTRIBUTE merchant_id NUMERIC%n");
				arff.printf("@ATTRIBUTE age_range {0,1,2,3,4,5,6,7,8}%n");
				arff.printf("@ATTRIBUTE gender {0,1,2}%n");
				arff.printf("@ATTRIBUTE cant_item NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_cat NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_brand NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_click NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_carr NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_comp NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_fav NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_item_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_cat_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_brand_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_click_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_carr_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_comp_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_fav_1111 NUMERIC%n");
				arff.printf("@ATTRIBUTE label {S,N}%n%n");
				
				arff.printf("@DATA%n");
				
				// data
				stream
					.skip(1)
					.map(MainArffParserCarlos1::switchLabel)
					.forEach(arff::println);
			}
		}
	}

	private static String switchLabel(String line) {
		String[] parts = line.split(",");
		
		// get label
		String label = parts[2];
		
		// move fields
		for (int i = 2; i < parts.length - 1; i++) {
			parts[i] = parts[i + 1];
		}
		
		// set label
		parts[parts.length - 1] = label.equals("X") ? "?" : label;
		
		// join
		return IjcaiUtils.join(parts);
	}
	
}
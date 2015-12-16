package ijcai.parser;

import ijcai.utils.IjcaiUtils;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * data_final_testFinal_compras-ventas
 * 
 * @author Juan
 *
 */
public class MainArffParserMatiDiscreto {

	public static void main(String[] args) throws Exception {
		String csvFileName = "c:/Temp/ijcai/dataset_test_discreto.csv";
		String arffFileName = csvFileName.replace(".csv", ".arff");
		
		try (Stream<String> stream = Files.lines(Paths.get(csvFileName))) {
			try (PrintWriter arff = new PrintWriter(arffFileName)) {
				// header
				arff.printf("@RELATION ijcai%n%n");
				
				arff.printf("@ATTRIBUTE user_id NUMERIC%n");
				arff.printf("@ATTRIBUTE merchant_id NUMERIC%n");
				arff.printf("@ATTRIBUTE age_range {teen,adult,elder}%n");
				arff.printf("@ATTRIBUTE gender {0,1}%n");
				arff.printf("@ATTRIBUTE cant_item {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_cat NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_brand NUMERIC%n");
				arff.printf("@ATTRIBUTE cant_click {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_carr {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_comp {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_fav {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_item_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_cat_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_brand_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_click_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_carr_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_comp_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_fav_1111 {poco,mucho}%n");
				arff.printf("@ATTRIBUTE cant_compras_user {bajo,medio,alto}%n");
				arff.printf("@ATTRIBUTE cant_ventas_merchant {bajo,medio,alto}%n");
				arff.printf("@ATTRIBUTE label {S,N}%n%n");
				
				arff.printf("@DATA%n");
				
				// data
				stream
					.skip(1)
					.map(MainArffParserMatiDiscreto::switchLabel)
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
package ijcai.parser;

import ijcai.utils.IjcaiUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * userLog
 * 
 * @author Juan
 *
 */
public class MainArffParserCarlos2 {

	public static void main(String[] args) throws Exception {
		parse(MainArffParserCarlos2::switchLabel, "userLog");
		parse(MainArffParserCarlos2::shift, "testFinal");
	}
	
	private static void parse(UnaryOperator<String> op, String fileName) throws IOException {
		String fileCsv = "c:/temp/ijcai/" + fileName + ".csv";
		String fileArff = fileCsv.replace(".csv", ".arff");
		
		try (Stream<String> stream = Files.lines(Paths.get(fileCsv))) {
			try (PrintWriter arff = new PrintWriter(fileArff)) {
				// header
				arff.printf("@RELATION ijcai%n%n");
				
				arff.printf("@ATTRIBUTE user_id NUMERIC%n");
				arff.printf("@ATTRIBUTE merchant_id NUMERIC%n");
				arff.printf("@ATTRIBUTE age_range {0,1,2,3,4,5,6,7,8}%n");
				arff.printf("@ATTRIBUTE gender {0,1,2}%n");
				arff.printf("@ATTRIBUTE item_id NUMERIC%n");
				arff.printf("@ATTRIBUTE cat_id NUMERIC%n");
				arff.printf("@ATTRIBUTE brand_id NUMERIC%n");
				arff.printf("@ATTRIBUTE time_stamp NUMERIC%n");
				arff.printf("@ATTRIBUTE action_type {0,1,2,3}%n");
				arff.printf("@ATTRIBUTE label {0,1}%n%n");
				
				arff.printf("@DATA%n");
				
				// data
				stream
					.skip(1)
					.map(op)
					.map(line -> line.replaceAll("NA", "?"))
					.forEach(arff::println);
			}
		}
	}
	
	/**
	 * Mueve el valor del label al final del registro
	 * 
	 * @param line
	 * @return
	 */
	private static String switchLabel(String line) {
		String[] parts = line.split(",");
		
		// get label
		String label = parts[3];
		
		// move fields
		for (int i = 3; i < parts.length - 1; i++) {
			parts[i] = parts[i + 1];
		}
		
		// set label
		parts[parts.length - 1] = label.equals("X") ? "?" : label;
		
		// join
		return IjcaiUtils.join(parts, 1, parts.length - 1);
	}
	
	/**
	 * Desplaza todos los valores hacia la izquierda para eliminar el id del registro
	 * 
	 * @param line
	 * @return
	 */
	private static String shift(String line) {
		String[] parts = line.split(",");

		// shift left
		for (int i = 0; i < parts.length - 1; i++) {
			parts[i] = parts[i + 1];
		}
		
		// add label
		parts[parts.length - 1] = "?";
		
		// join
		return IjcaiUtils.join(parts);
	}
	
}
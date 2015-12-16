package ijcai.parser;

import ijcai.utils.IjcaiUtils;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class MainArffParserFormat2 {

	public static void main(String[] args) throws Exception {
		try (Stream<String> stream = Files.lines(Paths.get("c:/temp/ijcai/test_format2.csv"))) {
			try (PrintWriter arff = new PrintWriter("c:/temp/ijcai/test_format2.arff");) {
				// header
				arff.printf("@RELATION ijcai%n%n");
				
				arff.printf("@ATTRIBUTE user_id NUMERIC%n");
				arff.printf("@ATTRIBUTE age_range {1,2,3,4,5,6,7,8}%n");
				arff.printf("@ATTRIBUTE gender {0,1,2}%n");
				arff.printf("@ATTRIBUTE merchant_id NUMERIC%n");
				arff.printf("@ATTRIBUTE item_id NUMERIC%n");
				arff.printf("@ATTRIBUTE category_id NUMERIC%n");
				arff.printf("@ATTRIBUTE brand_id NUMERIC%n");
				arff.printf("@ATTRIBUTE time_stamp NUMERIC%n");
				arff.printf("@ATTRIBUTE action_type {0,1,2,3}%n");
				arff.printf("@ATTRIBUTE label {0,1}%n%n");
				
				arff.printf("@DATA%n");
				
				// data
				stream
					.skip(1)
					.map(MainArffParserFormat2::replaceMissingValues)
					.flatMap(MainArffParserFormat2::parseActivityLog)
					.map(MainArffParserFormat2::switchLabel)
					.filter(line -> !line.endsWith("-1"))
					.forEach(line -> arff.printf("%s%n", line));
			}
		}
	}
	
	private static String replaceMissingValues(String line) {
		line = line.replaceAll(",,", ",?,");
		
		return Arrays
			.stream(line.split(","))
			.map(part -> part.isEmpty() ? "?" : part)
			.reduce((total, part) -> String.join(",", total, part))
			.get();
	}
	
	private static Stream<String> parseActivityLog(String line) {
		String[] parts = line.split(",");
		
		// record without activity log
		if (parts.length != 6) {
			line += ",?,?,?,?,?";
			return Stream.of(line);
		}
		
		// get header
		String header = IjcaiUtils.join(parts, 5);
		
		// get activity log field
		String activityLogField = parts[5];
		
		// parse activity log field
		return Arrays
			.stream(activityLogField.split("#"))
			.map(item -> header + "," + item.replaceAll(":", ","));
	}

	private static String switchLabel(String line) {
		String[] parts = line.split(",");
		
		// get label
		String label = parts[4];
		
		// move fields
		for (int i = 4; i < parts.length - 1; i++) {
			parts[i] = parts[i + 1];
		}
		
		// set label
		parts[parts.length - 1] = label;
		
		// join
		return IjcaiUtils.join(parts);
	}
	
}
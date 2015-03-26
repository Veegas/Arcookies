package Arcookies;
import java.io.FileWriter;
import java.io.IOException;


public class CsvController {

private static final String comma = ",";
private static final String nextLine = "\n";

//file name should be changes. the below is wrong
private static String fileName = System.getProperty("user.home")+"/student.csv";

private static FileWriter fileWriter;

public static void writeCsvFile(String tableName,String columnName,boolean key,
		boolean indexed, String references, String type) throws IOException{
	fileWriter = new FileWriter(fileName);
	try {
		fileWriter.append(tableName);
		fileWriter.append(comma);
		fileWriter.append(columnName);
		fileWriter.append(comma);
		fileWriter.append(""+key);
		fileWriter.append(comma);
		fileWriter.append(""+indexed);
		fileWriter.append(comma);
		fileWriter.append(tableName);
		fileWriter.append(comma);
		fileWriter.append(references);
		fileWriter.append(nextLine);
	} catch (IOException e) {
		System.out.println("Error while writing to metaData file");
		e.printStackTrace();
	}
	
	
	
	
	
}
}

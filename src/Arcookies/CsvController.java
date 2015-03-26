package Arcookies;
import java.io.FileWriter;
import java.io.IOException;


public class CsvController {

	
	private static final String comma = ",";
    private static final String nextLine = "\n";
	private static final String fileName = "metadata.csv";
    private static  FileWriter fileWriter ;
    static int recordCount =0;

public static void writeCsvFile(String tableName,String columnName,boolean key,
		boolean indexed, String references, String type) throws IOException{
	fileWriter =new FileWriter(fileName,true);
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
		fileWriter.write(nextLine);
  
		
	} catch (IOException e) {
		System.out.println("Error while writing to metaData file");
		e.printStackTrace();
	}
	 finally {
		
		              
		
		             try {
		
		                 fileWriter.flush();
		
		                 fileWriter.close();
		 
		             } catch (IOException e) {
		 
		                 System.out.println("Error while flushing/closing fileWriter !!!");
		 
		                 e.printStackTrace();
		 
		             }

	
	
	 }
	
	
}
}

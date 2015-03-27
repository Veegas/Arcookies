package Arcookies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvController {

	private static final String comma = ",";
	private static final String nextLine = "\n";
	private static final String fileName = "metadata.csv";
	private static FileWriter fileWriter;
	static int recordCount = 0;

	public static void writeCsvFile(String tableName, String columnName,
			boolean key, boolean indexed, String references, String type)
			throws IOException {
		fileWriter = new FileWriter(fileName, true);
		try {
			fileWriter.append(tableName);
			fileWriter.append(comma);
			fileWriter.append(columnName);
			fileWriter.append(comma);
			fileWriter.append("" + key);
			fileWriter.append(comma);
			fileWriter.append("" + indexed);
			fileWriter.append(comma);
			fileWriter.append(tableName);
			fileWriter.append(comma);
			fileWriter.append(references);
			fileWriter.write(nextLine);

		} catch (IOException e) {
			System.out.println("Error while writing to metaData file");
			e.printStackTrace();
		} finally {

			try {

				fileWriter.flush();

				fileWriter.close();

			} catch (IOException e) {

				System.out
						.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();

			}

		}

	}

	public static ArrayList<Table> readCsvFile(int maxRowCount) {

		BufferedReader fileReader = null;

		try {
			// Create a new list of student to be filled by CSV file data

			ArrayList<Table> tables = new ArrayList<Table>();

			String line = "";

			fileReader = new BufferedReader(new FileReader(fileName));

			// Read the CSV file header to skip it
			fileReader.readLine();

			// Read the file line by line starting from the second line

			while ((line = fileReader.readLine()) != null) {

				// Get all tokens available in line
				String[] tokens = line.split(",");

				if (tokens.length > 0) {
                   boolean alreadyExists = false;
                   Table tempTable = null;
                   
                   for (Table table : tables){
                	   if (table.getName().equalsIgnoreCase(tokens[0])){
                		   alreadyExists = true;
                		   tempTable = table;
                		   break;
                	   }
                   }
					
					if (alreadyExists) {
						if (!tempTable.getColumns().contains(tokens[1]))
						tempTable.getColumns().add(tokens[1]);
					} else {
						Table table = new Table(tokens[0], maxRowCount);
						tables.add(table);
						table.getColumns().add(tokens[1]);
					}

				}

			}
			return tables;

		}

		catch (Exception e) {

			System.out.println("Error in CsvFileReader !");

			e.printStackTrace();

		} finally {

			try {

				fileReader.close();

			} catch (IOException e) {

				System.out.println("Error while closing fileReader !");

				e.printStackTrace();

			}

		}
		return null;

	}

}

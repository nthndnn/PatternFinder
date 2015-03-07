package behaviorClassification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * @author nathandunn
 * Fills a table with the raw information from a CSV.
 */
public class CsvToTable {

	private static final String NaValue = "-999";
	
	//TODO allow specification of which columns you want
	
	//TODO Note: restrictions on how the time data is organized (earliest to latest, etc.)
	
	//TODO Note CSV must have a header line
	public static RawDataTable readCsv(String fileName){
		return readCsv(fileName, -1);		//will use the findTimeInd function
	}
	
	
	public static RawDataTable readCsv(String fileName, int timeInd){
		String[][] contents = readAll(fileName);
		
		String[] headers = parseHeaderLine(contents[0]);
		
		double[][] transposedEntries = new double[contents.length-1][];
		for (int i=1; i<contents.length; i++){
			transposedEntries[i-1] = parseLine(contents[i]);
		}
		double[][] entries = MyMath.transpose(transposedEntries);
		
		if (timeInd == -1)
			timeInd = findTimeInd(entries);
		
		return new RawDataTable(entries, headers, timeInd);
	}
	
	private static int findTimeInd(double[][] entries){
		//Find which column is "most" sorted
		return entries.length-1;		//Assume it's the last column
	}
	
	//Strips off the quote marks
	private static String[] parseHeaderLine(String[] line){
		String[] out = new String[line.length];
		for (int i=0; i<line.length; i++){
			String s = line[i];
			String header = "";
			for ( char c : s.toCharArray() )
				if ( c!= '"' )
					header += "" + c;
			
			out[i] = header;
		}
		return out;
	}
	
//	public static RawDataTable readCsv(String fileName, int timeInd){
//		
//	}
//	
//	public static RawDataTable readCsv(String fileName, String timeHeader){
//		
//	}
	
	private static double[] parseLine(String[] line){
		double[] out = new double[line.length];
		try {
			for (int i=0; i<out.length; i++){
				
				if ( line[i].equals(NaValue) )
					out[i] = Double.NaN;
				else
					out[i] = Double.parseDouble(line[i]);
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	private static String[][] readAll(String fileName){
		ArrayList<String[]> contents = new ArrayList<String[]>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				contents.add(line.split(","));
			}
			br.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return contents.toArray(new String[contents.size()][]);
	}
	
//	private static boolean isHeader(String[] line){
//		if (line.length > 0){
//			try {
//				Double.parseDouble(line[0]);
//				return false;
//			} catch (Exception e){
//				return true;
//			}
//		}else{
//			return false;
//		}
//	}
	
	
}

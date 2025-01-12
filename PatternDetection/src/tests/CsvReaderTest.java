package tests;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

//Extends CsvToTable to get access to all methods
public class CsvReaderTest extends CsvToTable {

	public static void main(String[] args) {
//		String fileName = "../../DataSets_R/nfdunn_Moulton1.csv";
//		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
//		Pnt.pnt(table);
//		
//		Pnt.pnt(table.timeSpan());
//		Pnt.pnt(table.getForwardIndicator());
////		ChunkList cl = new ChunkList(table, 20);
////		Pnt.pnt(cl);
////		
////		ClassifiedChunkList ccl = new ClassifiedChunkList();
//		
////		
//		
//		String[][] contents = readAll("TestTables/TestTable1.csv");
//		
//		String[] headers = parseHeaderLine(contents[0]);
//		Pnt.pntArr(headers);
//		
//		double[][] entries = getEntries(contents);
//		Pnt.pntArr(entries);
//		
//		Pnt.pnt(calculateInversions(entries[0]));
//		Pnt.pnt(calculateInversions(entries[1]));
//		
		
		test("TestTable1.csv", 0, true);
		test("TestTable2.csv", 1, true);
		test("TestTable3.csv", 0, false);
		test("TestTable4.csv", 1, false);
		
	}
	

	private static void test(String fileName, int expectedTimeInd, boolean expectedOrientation){
		RawTimeSeriesTable table = CsvToTable.readCsv("TestTables/"+fileName);
//		Pnt.pnt(table);
		Pnt.pnt("Testing "+fileName);
		boolean failed = false;
		if (table.getTimeInd() != expectedTimeInd){
			Pnt.pnt("Failed time ind");
			failed = true;
		}
		if (table.getForwardIndicator() != expectedOrientation){
			Pnt.pnt("Failed orientation");
			failed = true;
		}
		if (!failed)
			Pnt.pnt("Test passed");
		Pnt.pnt();

	}
	

}

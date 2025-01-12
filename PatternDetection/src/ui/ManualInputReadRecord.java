package ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Reads input from the user using standard input. Can record user's input in
 *  a file to be used with InputSimulator later
 * @author nathandunn
 *
 */
public class ManualInputReadRecord extends ManualInputReader {
	
//	private static final String DEF_FILE = null;
	private static final String DEF_FILE = "gisp2_smoo_record.txt";
//	private static final String DEF_FILE = "record.txt";
	
//	private Scanner scanner;
	private String fileName;
	
	public ManualInputReadRecord(){
		this(DEF_FILE);
	}
	
	public ManualInputReadRecord(String fileName){
		super();
		this.fileName = fileName;
//		scanner = new Scanner(System.in);
	}

	@Override
	public String getInput() {
		String out = super.getInput();
		if (fileName != null)
			record(out);
		return out;
	}
	
	private void record(String s){		
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			file.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file != null) file.close();
	}
}

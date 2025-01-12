package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads from a file to simulate a user entering commands
 * @author nathandunn
 *
 */
public class InputSimulator implements IInputReader{
	private BufferedReader br;
	private boolean isClosed;
	public InputSimulator(){
		this("record.txt");
	}
	
	public InputSimulator(String fileName){
		try {
			br = new BufferedReader(new FileReader(fileName));
			isClosed = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Returns the next line of the file. Returns defaultInput() if no more
	 *  lines are in file
	 */
	@Override
	public String getInput() {
		if (isClosed){
			return defaultInput();
		}
		
		String out = null;
		try {
			out = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out == null){
			try {
				br.close();
				isClosed = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return defaultInput();
		}
		System.out.println(out);
		return out;
	}
	
	/**
	 * @return the Quit control sequence
	 */
	private String defaultInput(){
		return "Q";
	}
}

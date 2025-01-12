package behaviorClassification;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import tests.Objs;

public class Serializer {

	private static final String path = Objs.SERIAL_PATH;//"../../SerializedObjects/";
	
	public static void serialize(Serializable obj, String fileName){
		try{
			fileName = path + fileName + ".ser";
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String fullPath(String fileName){
		return path + fileName + ".ser";
	}
	
	public static Object deserialize(String fileName){
		Object out = null;
		try{
			String fullPath = fullPath(fileName);
			FileInputStream fileIn = new FileInputStream(fullPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			out = in.readObject();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	
	
	public static void writeFile(String contents, String fileName){
		try{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			pw.write(contents);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

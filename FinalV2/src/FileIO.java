import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {


	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String lineSeparator = System.getProperty("line.separator");

	public ArrayList<String> readFile(String filename) {

		File readFile = new File(filename);
		if (!readFile.exists()) {
			System.err.println("File " + filename + " does not exist.");
			return null;
		}

		Scanner in = null;

		try {

			FileReader reader = new FileReader(readFile);
			in = new Scanner(reader);

			ArrayList<String> fileData = new ArrayList<String>();

			while (in.hasNextLine()) {
				String input = in.nextLine();
				fileData.add(input);
			}

			return fileData;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (in != null)
				in.close();

		}

	}


	public void writeFile(String filename, ArrayList<String> data) {

		File writeFile = new File(filename);
		FileWriter writer = null;

		try {

			writer = new FileWriter(writeFile);

			for (String s : data) {
				writer.write(s + lineSeparator);
			}

			writer.flush();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public Object readObject(String file)
	{

		File readFile = new File(file);
		if (!readFile.exists()) {
			System.err.println("File " + file + " does not exist.");
			return null;
		}

		ObjectInputStream ois = null;
		FileInputStream fis = null;
		
		
		
		try {
			fis = new FileInputStream(readFile);
			ois = new ObjectInputStream(fis);
			
			Object out = ois.readObject();
			return out;
			
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void writeObject(String file, Object data)
	{
		File writeFile = new File(file);
		
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		
		
		try {
			fos = new FileOutputStream(writeFile);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(data);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}




}

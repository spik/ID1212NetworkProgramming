package common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class converts a file into a byte array and converts a byte array back to a file. 
 */
public class FileConverter {

	/**
	 * Converts a file to a byte array that can be sent over the network 
	 * @param filePath The path to the file that is to be converted
	 * @return The byte array that is to be sent over the network 
	 */
	public byte[] convertFromFileToByteArray(String filePath) {
		byte[] byteArray;
		try {
			byteArray = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			return null;
		}
		return byteArray;
	}
	
	/**
	 * Converts a byte array to a file.
	 * @param byteArray The byte array that is to be converted into a file
	 * @return The file
	 */
	public void convertFromByteArrayToFile(byte[] byteArray, String filePath) {
		
		FileOutputStream fop = null;
		File file;
		try {
			file = new File(filePath);
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}	
			
			fop.write(byteArray);
			fop.close();
		}
		catch(NullPointerException e) {
			return;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}

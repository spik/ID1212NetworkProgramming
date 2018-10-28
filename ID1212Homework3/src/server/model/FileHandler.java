package server.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import common.Credentials;
import common.FileConverter;
import server.integration.DatabaseAccess;

/**
 * This class peroforms several file-related operations
 */
public class FileHandler {
	private FileConverter fileConverter = new FileConverter();
	//C:/Users/Charlotta/eclipse-workspace/ID1212AFileCatalog/src/server/Catalog
	private String pathToCatalog = "src/server/Catalog/";
	
	public ExtendedFile upload(byte[] byteArray, String fileName, String fileNameForDatabase, String username, String permissions, boolean notification) {
		String filePath = pathToCatalog + fileName;
		fileConverter.convertFromByteArrayToFile(byteArray, filePath);
		ExtendedFile extendedFile = new ExtendedFile(byteArray, fileName, fileNameForDatabase, username, permissions, notification);
		return extendedFile;
	}
	
	public void deleteFileFromCatalog(String fileName) {
		Path filePath = Paths.get( pathToCatalog + fileName);
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] downloadFile(String fileName) {
		return fileConverter.convertFromFileToByteArray(pathToCatalog + fileName);
		
	}
}

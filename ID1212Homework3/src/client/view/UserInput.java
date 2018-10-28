package client.view;

public class UserInput {
	
	private String userInput;
	private String[] userArgs;
	
	public UserInput(String userInput) {
		this.userInput = userInput;
		splitUserInput(userInput);
	}
	
	public String getMessage() {
		return userInput;
	}
	
	InputType getInputType() {
		if(userInput.equals("Create Account")) {
			return InputType.REGISTER;
		}
		else if(userInput.equals("Quit")) {
			return InputType.QUIT;
		}
		else if(userInput.equals("Delete Account")) {
			return InputType.UNREGISTER;
		}
		else if(userInput.equals("Login")) {
			return InputType.LOGIN;
		}
		else if(userInput.equals("Logout")){
			return InputType.LOGOUT;
		}
		else if(userInput.contains("Upload File")) {
			return InputType.UPLOAD;
		}
		else if(userInput.contains("Delete File")) {
			return InputType.DELETE;
		}
		else if(userInput.contains("Download File")) {
			return InputType.DOWNLOAD;
		}
		else if(userInput.contains("View All")) {
			return InputType.VIEWALL;
		}

		else return InputType.INVALID;
	}

	public String getHostWhenLogin() {
		String host;
		try {
			//Position 1 in the array contains the host
			host = userArgs[1];
		}
		catch(Exception e){
			host = "127.0.0.1";
		}
		return host;
	}
	
	public String getHostWhenCreateAccount() {
		String host;
		try {
			//Position 1 in the array contains the host
			host = userArgs[2];
		}
		catch(Exception e){
			host = "127.0.0.1";
		}
		return host;
	}
	
	public String getFilePath() {
		String filePath = null;
		try {
			//Position 2 in the array contains the file path
			filePath = userArgs[2];
		}
		catch(Exception e){
			System.out.println("You must specify a file to upload");
		}
		return filePath;
	}

	public char getLetter() {
		return userInput.charAt(0);
	}

	public String getWord() {
		return userInput;
	}
	/**
	 * Splits the user input string. 
	 * In the case of a file command, the string should have the format: "<command> File <fileName>"
	 * In the case of a login command, the string should have the format: "Login <host>"
	 */
	public void splitUserInput(String userInput) {
		userArgs = userInput.split(" ");
	}

	public String getFileNameForCatalog() {
		String fileName = null;
		try {
			//Position 2 in the array contains the file path
			fileName = userArgs[2];
			String[] splittedPath = fileName.split("/");
			fileName = splittedPath[splittedPath.length-1];
		}
		catch(Exception e){
			System.out.println("You must specify a file to upload");
		}
		return fileName;
	}
	
	public String getFileNameForDatabase() {
		String fileName = null;
		try {
			//Position 2 in the array contains the file path
			fileName = userArgs[2];
			String[] splittedPath = fileName.split("/");
			fileName = splittedPath[splittedPath.length-1];
			String[] splittedAgain = fileName.split("\\.");
			fileName = splittedAgain[0];
		}
		catch(Exception e){
			System.out.println("You must specify a file to upload");
		}
		return fileName;
	}
	
	public String getPermissions() {
		String permissions = null;
		try {
			//Position 2 in the array contains the file path
			permissions = userArgs[3];
		}
		catch(Exception e){
			System.out.println("You must specify permissions for your file");
		}
		return permissions;
	}
	
	public boolean getNotification() {
		String notification = null;
		try {
			//Position 2 in the array contains the file path
			notification = userArgs[4];
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
}

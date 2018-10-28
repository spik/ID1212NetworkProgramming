package client.view;

public class UserInputParser {

	private String userInput;
	private String[] userArgs;

	public UserInputParser(String userInput) {
		this.userInput = userInput;
		splitUserInput(userInput);
	}

	InputType getInputType() {
		if(userInput.equals("getall")) {
			return InputType.GETALL;
		}
		else if(userInput.contains("getbycostandtype") && userInput.contains(" ")) {
			return InputType.GETBYCOSTANDTYPE;
		}
		else if(userInput.contains("getbycost") && userInput.contains(" ")) {
			return InputType.GETBYCOST;
		}
		else if(userInput.contains("getbytype") && userInput.contains(" ")) {
			return InputType.GETBYTYPE;
		}
		else if(userInput.equals("quit")) {
			return InputType.QUIT;
		}
		else if(userInput.contains("getroom") && userInput.contains(" ")) {
			return InputType.GETROOM;
		}
		else if(userInput.contains("getbooking") && userInput.contains(" ")) {
			return InputType.GETBOOKING;
		}
		else if(userInput.contains("book") && userInput.contains(" ")) {
			return InputType.BOOK;
		}
		else if(userInput.contains("edit") && userInput.contains(" ")){
			return InputType.EDIT;
		}
		else if(userInput.contains("delete") && userInput.contains(" ")){
			return InputType.DELETE;
		}
		else return InputType.INVALID;
	}

	public int getRoomNumber(){
		int roomNumber = -1;
		try {
			roomNumber = Integer.parseInt(userArgs[1]);
		}
		catch (NullPointerException e){
			System.out.println("You must specify a room number");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return roomNumber;
	}

	public int getBookingNumber(){
		int bookingNumber = -1;
		try {
			bookingNumber = Integer.parseInt(userArgs[1]);
		}
		catch (NullPointerException e){
			System.out.println("You must specify a booking number");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return bookingNumber;
	}

	public String getFromDate(){
		String from = null;
		try {
			from = userArgs[2];
		}
		catch (NullPointerException e){
			System.out.println("You must specify a 'from' date");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return from;
	}

	public String getToDate(){
		String to = null;
		try {
			to = userArgs[3];
		}
		catch (NullPointerException e){
			System.out.println("You must specify a 'to' date");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return to;
	}

	public int getRoomNumberForEdit() {
		int roomNumber = -1;
		try {
			roomNumber = Integer.parseInt(userArgs[2]);
		}
		catch (NullPointerException e){
			System.out.println("You must specify a room number");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return roomNumber;
	}

	public int getMaxCost() {
		int maxCost = -1;
		try {
			maxCost = Integer.parseInt(userArgs[1]);
		}
		catch (NullPointerException e){
			System.out.println("You must specify a price limit");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return maxCost;
	}

	public String getType() {
		String to = null;
		try {
			to = userArgs[1];
		}
		catch (NullPointerException e){
			System.out.println("You must specify a type");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return to;
	}
	
	public String getTypeForTypeAndCost() {
		String to = null;
		try {
			to = userArgs[2];
		}
		catch (NullPointerException e){
			System.out.println("You must specify a type");
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

		}
		return to;
	}

	/**
	 * Splits the user input string. The string should have the format: "connect <host> <port>". 
	 */
	public void splitUserInput(String userInput) {
		userArgs = userInput.split(" ");
	}
}

package client.view;

import java.util.Scanner;

import javax.ws.rs.client.WebTarget;

import client.net.ClientRequests;

public class InputInterpreter implements Runnable{
	
	private boolean isReceivingInput = false;
	Scanner scanner = new Scanner(System.in);
	WebTarget target;
	ClientRequests request = new ClientRequests();
	ServerResponseParser parser = new ServerResponseParser();
	
	public void start() {
		if (isReceivingInput) {
			return;
		}
		isReceivingInput = true;
		target = request.createClient();
		
		System.out.println("Welcome to the booking service! \nTo check all available rooms, write 'getall\n"
				+ "To get all available rooms withing a price limit, write getbycost <maxCost>, where 'maxCost' is your price limit\n"
				+ "To get all available rooms of a specific type, write getbytype <type>, where 'type' can be for example; single, double etc\n"
				+ "To get all available rooms of a sertain type within a price limit, write getbycostandtype <maxCost> <type>\n"
				+ "To get a specific room, write 'getroom <roomnumber>' where 'roomnumber' is the number of the room you want to get information about\n"
				+ "To get a specific booking, write 'getbooking <bookingnumber>' where 'bookingnumber' is the number of your booking that \n" 
				+ "you received when the room was booked"
				+ "To book a room, write 'book <roomnumber> <fromdate> <todate>', where 'roomnumber' is the number of the room you want to book.\n"
				+ "to edit booking, write 'edit <bookingnumber> <roomNumber> <fromdate> <todate>,\nTo delete booking, write 'delete <bookingnumber>");
		
		new Thread(this).start();	
	}
	
	public void run() {
		while (isReceivingInput) {
		UserInputParser input = new UserInputParser(scanner.nextLine());	
			try {
				switch(input.getInputType()) {
					case GETALL:
						System.out.println(parser.parseResponse(request.getAllAvailableRooms(target)));
						break;
					case GETBYCOST:
						if(input.getMaxCost() < 0 ) doDefault();
						else System.out.println(parser.parseResponse(request.getByCost(target, input.getMaxCost())));
						break;
					case GETBYTYPE:
						System.out.println(parser.parseResponse(request.getByType(target, input.getType())));
						break;
					case GETBYCOSTANDTYPE:
						System.out.println("test type first: " + input.getTypeForTypeAndCost());
						if(input.getMaxCost() < 0 ) doDefault();
						else System.out.println(parser.parseResponse(request.getByCostAndType(target, input.getMaxCost(), input.getTypeForTypeAndCost())));
						break;
					case GETROOM:
						if(input.getRoomNumber() < 0 ) doDefault();
						else System.out.println(parser.parseResponse(request.getRoom(target, input.getRoomNumber())));
						break;
					case GETBOOKING:
						if(input.getBookingNumber() < 0 ) doDefault();
						else System.out.println(parser.parseResponse(request.getBooking(target, input.getBookingNumber())));
						break;
					case BOOK:
						if(input.getRoomNumber() < 0 || input.getFromDate() == null || input.getToDate() == null ) doDefault();
						else System.out.println(parser.parseResponse(request.addBooking(target, input.getRoomNumber(), input.getFromDate(), input.getToDate())));
						break;
					case EDIT:
						if(input.getBookingNumber() < 0 || input.getRoomNumberForEdit() < 0|| input.getFromDate() == null || input.getToDate() == null ) doDefault();
						else System.out.println(parser.parseResponse(request.editBooking(target, input.getBookingNumber(), input.getRoomNumberForEdit(), input.getFromDate(), input.getToDate())));
						break;
					case DELETE:
						if(input.getRoomNumber() <0 ) doDefault();
						else System.out.println(parser.parseResponse(request.deleteBooking(target, input.getBookingNumber())));
						break;
					case QUIT:
						isReceivingInput = false;
						System.out.println("Quitting application");
						System.exit(0);
						break;
					case INVALID:
						doDefault();
						break;
				}
			} catch (NullPointerException e) {
				System.out.println("ERROR: Invalid input! Try again.");
			}catch(java.net.ConnectException| javax.ws.rs.ProcessingException  e) {
				System.out.println("ERROR: Could not connect to server. Try again later.");
			}
		}	
	}
	
	public void doDefault() {
		System.out.println("ERROR: Invalid input! Try again.");
	}
}



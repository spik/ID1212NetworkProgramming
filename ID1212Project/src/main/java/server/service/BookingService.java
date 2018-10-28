package server.service;

import server.model.Booking;
import server.model.Response;
import server.model.Room;

public interface BookingService {

	public Response addBooking(int roomNumber, Booking booking);
	
	public Response editBooking(int id, Booking booking);
	
	public Response cancelBooking(int id);
	
	public Response getRoom(int roomNumber);
	
	public Room[] getAllAvailableRooms();
	
	public Response getBooking(int bookingNumber);
	
}

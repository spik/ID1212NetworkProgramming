package server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import server.model.Booking;
import server.model.Response;
import server.model.Room;

@Path("/booking")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class BookingResource implements BookingService{

	private static Map<Integer,Room> rooms = new HashMap<Integer,Room>();
	private static Map<Integer,Booking> bookings = new HashMap<Integer,Booking>();
	private String[] types = {"double", "single", "honeymoon", "twin", "interconnecting", "cabana", "lanai", "suite"};

	public void fillRooms() {
		Random random = new Random();
		for(int i = 0; i < 200; i++) {
			String type = types[random.nextInt(types.length-1)];
			rooms.put(i, new Room(type, i));
		}
	}

	@POST
	@Path("/{number}/add")
	public Response addBooking(@PathParam("number") int number, Booking booking) {
		Response response = new Response();
		try {
			Room room = rooms.get(number);
			if(room.isBooked()) {
				response.setMessage("This room is not available");
				return response;
			}
			room.bookRoom();
		}catch(NullPointerException e) {
			response.setMessage("The room number does not exist, try again");
			return response;
		}
		booking.setId(bookings.size());
		booking.setRoomNumber(number);
		bookings.put(booking.getId(), booking);
		response.setMessage("Room " + number + " successfully booked!\nYour booking number is: " + booking.getId());

		return response;
	}

	@PUT
	@Path("/{id}/edit")
	public Response editBooking(@PathParam("id") int id, Booking booking) {
		Response response = new Response();
		if(bookings.get(id) == null) {
			response.setMessage("The booking does not exist");
			return response;
		}
		bookings.put(id, booking);
		response.setMessage("Booking successfully edited");

		return response;
	}

	@DELETE
	@Path("/{id}/delete")
	public Response cancelBooking(@PathParam("id") int id) {
		
		Response response = new Response();
		
		try {
			int roomNumber = bookings.get(id).getRoomNumber();
			Room room = rooms.get(roomNumber);

			if(!room.isBooked()) {
				response.setMessage("This room i not booked so booking cannot be cancelled");
				return response;
			}

			room.cancelBooking();
			bookings.remove(id);
			response.setMessage("The booking for room " + room.getNumber() + " has been successfully cancelled");
			return response;
			
		}catch(NullPointerException e) {
			response.setMessage("The booking does not exist, try again");
			return response;
		}
	}

	@GET
	@Path("/{number}/getRoom")
	public Response getRoom(@PathParam("number") int roomNumber) {
		Response response = new Response();
		try {
			response.setMessage(rooms.get(roomNumber).toString());
		}catch(NullPointerException e) {
			response.setMessage("The room number does not exist, try again");
			return response;
		}
		return response;
	}

	@GET
	@Path("/getAll")
	public Room[] getAllAvailableRooms() {
		Set<Integer> keys = rooms.keySet();
		int numOfAvailableRooms = keys.size()-bookings.size();
		Room[] roomArray = new Room[numOfAvailableRooms];
		int i=0;
		for(Integer key : keys){
			if (!rooms.get(key).isBooked()) {
				roomArray[i] = rooms.get(key);
				i++;
			}
		}
		return roomArray;
	}
	
	@GET
	@Path("/{id}/getBooking")
	public Response getBooking(@PathParam("id") int id) {
		Response response = new Response();
		try {
			response.setMessage(bookings.get(id).toString());
		}catch(NullPointerException e) {
			response.setMessage("The booking does not exist, try again");
			return response;
		}
		return response;
	}

	@GET
	@Path("/{type}/getByType")
	public Room[] getAvailableRoomsByType(@PathParam("type") String type) {
		
		ArrayList<Room> allMatchingRooms = new ArrayList<Room>();
		Set<Integer> keys = rooms.keySet();
		
		for(Integer key : keys){
			Room room = rooms.get(key);
			if (room.getType().equals(type) && !room.isBooked()) {
				allMatchingRooms.add(rooms.get(key));
			}
		}
		Room[] roomArray = new Room[allMatchingRooms.size()];
		return allMatchingRooms.toArray(roomArray);
	}

	@GET
	@Path("/{cost}/getByCost")
	public Room[] getAvailableRoomsByCost(@PathParam("cost") int maxCost) {
		
		ArrayList<Room> allMatchingRooms = new ArrayList<Room>();
		Set<Integer> keys = rooms.keySet();
		
		for(Integer key : keys){
			Room room = rooms.get(key);
			if (room.getCost() <= maxCost &&!room.isBooked()) {
				allMatchingRooms.add(rooms.get(key));
			}
		}
		Room[] roomArray = new Room[allMatchingRooms.size()];
		return allMatchingRooms.toArray(roomArray);
	}

	@GET
	@Path("/{cost}/{type}/getByCostAndType")
	public Room[] getAvailableRoomsByCostAndType(@PathParam("cost") int maxCost, @PathParam("type") String type) {
		
		ArrayList<Room> allMatchingRooms = new ArrayList<Room>();
		Set<Integer> keys = rooms.keySet();

		for(Integer key : keys){
			Room room = rooms.get(key);
			if (room.getCost() <= maxCost && room.getType().equals(type) &&!room.isBooked()) {
				allMatchingRooms.add(rooms.get(key));
			}
		}
		Room[] roomArray = new Room[allMatchingRooms.size()];
		return allMatchingRooms.toArray(roomArray);
	}
}

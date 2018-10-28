package server.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "booking")
public class Booking {

	private String from;
	private String to;
	private int roomNumber;
	private int id;
	
	public Booking() {
		
	}
	
	public Booking(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public void setTo(String from) {
		this.from = from;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public int getId() {
		return id;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	@Override
	public String toString() {
		return "Booking number: " + id + "\nRoom: " + roomNumber + "\nFrom date: " + from + "\nTo date: " + to;
		
	}
}

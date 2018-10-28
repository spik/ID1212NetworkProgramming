package server.model;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "room")
public class Room {
	
	private int number;
	private boolean isBooked = false;
	private int cost;
	private String type;
	
	public Room() {
		
	}
	
	public Room(String type, int roomNumber) {
		Random random = new Random();
		this.number = roomNumber;
		this.cost = random.nextInt(3000-500)+500;
		this.type = type;
	}
	
	@XmlElement(name="number")
	public int getNumber() {
		return number;
	}
	
	@XmlElement(name="cost")
	public int getCost() {
		return cost;
	}
	
	@XmlElement(name="type")
	public String getType() {
		return type;
	}
	
	public boolean isBooked() {
		return isBooked;
	}
	
	public void bookRoom() {
		isBooked = true;
	}
	
	public void cancelBooking() {
		isBooked = false;
	}
	
	@Override
	public String toString() {
		return "Room number: " + number + "\nCost: " + cost + "\nType: " + type + "\nBooked:" + isBooked;
	}
	
}

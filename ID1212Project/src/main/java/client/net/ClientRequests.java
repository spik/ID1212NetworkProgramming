package client.net;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientRequests {

	public WebTarget createClient() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/ID1212BookingSystem/booking/");

		return target;
	}
	
	public String getAllAvailableRooms(WebTarget target) throws java.net.ConnectException {
		Response response = target.path("getAll").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String getByCost(WebTarget target, int maxCost) throws java.net.ConnectException{
		Response response = target.path(maxCost + "/getByCost").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String getByType(WebTarget target, String type) throws java.net.ConnectException{
		Response response = target.path(type + "/getByType").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String getByCostAndType(WebTarget target, int maxCost, String type) throws java.net.ConnectException{
		System.out.println("test type: " + type);
		Response response = target.path(maxCost + "/" + type + "/getByCostAndType").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String getBooking(WebTarget target, int bookingNumber) throws java.net.ConnectException{
		Response response = target.path(bookingNumber + "/getBooking").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String getRoom(WebTarget target, int roomNumber) throws java.net.ConnectException{
		Response response = target.path(roomNumber + "/getRoom").request(MediaType.APPLICATION_XML).get();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}


	public String addBooking(WebTarget target, int roomNumber, String from, String to) throws java.net.ConnectException{

		String requestString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
				"<booking>\r\n" + 
				"	<from>" + from + "</from>\r\n" + 
				"	<to>" + to + "</to>\r\n" + 
				"</booking>";

		Response response = target.path(roomNumber + "/add").request(MediaType.APPLICATION_XML).post(Entity.entity(requestString, MediaType.APPLICATION_XML));
		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String editBooking(WebTarget target, int bookingNumber, int roomNumber, String from, String to) throws java.net.ConnectException{

		String requestString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
				"<booking>\r\n" + 
				"	<from>" + from + "</from>\r\n" + 
				"	<to>" + to + "</to>\r\n" + 
				"	<id>" + bookingNumber + "</id>\r\n" + 
				"	<roomNumber>" + roomNumber + "</roomNumber>\r\n" + 
				"</booking>";

		Response response = target.path(bookingNumber + "/edit").request(MediaType.APPLICATION_XML).put(Entity.entity(requestString, MediaType.APPLICATION_XML));
		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
	}
	
	public String deleteBooking(WebTarget target, int bookingNumber) throws java.net.ConnectException{
		
		Response response = target.path(bookingNumber + "/delete").request(MediaType.APPLICATION_XML).delete();

		int responseCode = response.getStatus();
		String responseMessageFromServer = response.getStatusInfo().getReasonPhrase();

		String responseString = response.readEntity(String.class);

		return responseCode + "#" + responseMessageFromServer + "#" + responseString;
		
	}
}

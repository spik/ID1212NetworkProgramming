package client.view;

import org.apache.commons.lang3.StringUtils;

public class ServerResponseParser {

	int responseCode;

	public String parseResponse(String response) {
		String[] temp;
		temp = response.split("#");
		responseCode = Integer.parseInt(temp[0]);

		if(responseCode != 200) {
			return "Something went wrong at the server, the requested action has not been performed.";
		}
		else {
			return extractMessage(temp[2]);
		}
	}

	public String extractMessage(String xmlResponse) {
		String message = StringUtils.substringBetween(xmlResponse, "<message>", "</message>");
		if(message == null){
			return parseXMLArray(StringUtils.substringBetween(xmlResponse, "<rooms>", "</rooms>"));
		}
		else{
			return message;
		}
	}
	
	private String parseXMLArray(String xmlArray) {
		
		if (xmlArray.length() > 0) {
			String[] xmlRooms = xmlArray.split("<room>");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < xmlRooms.length; i++) {
				sb.append("Room number: " + StringUtils.substringBetween(xmlRooms[i], "<number>", "</number>") + "\n");
				sb.append("Cost: " + StringUtils.substringBetween(xmlRooms[i], "<cost>", "</cost>") + "\n");
				sb.append("Type: " + StringUtils.substringBetween(xmlRooms[i], "<type>", "</type>") + "\n");
				sb.append("\n");
			}
			return sb.toString();
		}
		else {
			return "No results match your query";
		}
	}
}

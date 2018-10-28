package server.startup;

import javax.servlet.ServletContextEvent;

import server.service.BookingResource;

public class Initializer implements javax.servlet.ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		BookingResource booking = new BookingResource();
		booking.fillRooms();
	}

}

package Model;
import java.util.*;

public class User {
	private String email; 
	private ArrayList<Notification> notifications  = new ArrayList<Notification>();;
	private ArrayList<Ticket> tickets  = new ArrayList<Ticket>();;
	public User(String email){

		this.email = email;
		
	}
	public String getEmail() {
		return email;
	}
	
	public void setNotification(Notification notification){
		notifications.add(notification);
	}
	
	public void setTicket(Ticket ticket) {
		tickets.add(ticket);
	}
	
	public ArrayList<Notification> getNotifications(){
		return notifications;
	}
	
	public ArrayList<Ticket> getTickets(){
		return tickets;
	}
	

}

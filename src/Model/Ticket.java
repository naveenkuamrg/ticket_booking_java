package Model;
import java.time.LocalDateTime;
import java.util.*;

public class Ticket {
	
	static private int counter = 0 ;
	private int id = 0 ;
	private String vehicleNumber;
	private String source;
	private String destination; 
	private TicketStatus status;
	private final HashMap<String,String> passangersAndSeatName;
	private String time;
	private VehicleType type;
	private int price;
	private String userEmail ;
	private LocalDateTime actionDate;
	public Ticket(String userEmail,String number,String source,String destination,HashMap<String,String> passangersAndSeatName,String time,VehicleType type,int price){
		this.vehicleNumber = number;
		this.source = source;
		this.destination = destination;
		this.passangersAndSeatName = passangersAndSeatName;
		this.time = time;
		this.type = type;
		this.price = price;
		this.userEmail = userEmail ;
		id = ++counter;
		this.actionDate =  LocalDateTime.now();
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public int getId() {
		return id;
	}
	public LocalDateTime getDate() {
        return actionDate;
    }
	public int getPrice() {
		return price;
	}
	public VehicleType getVehicleType() {
		return type;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	
	public TicketStatus getStatus() {
		return status;
	}
	
	public String getSource(){
		return source;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public String getDestination() {
		return destination;
	}
	public HashMap<String,String> getNameOfPassangers() {
		return passangersAndSeatName;
	}
	
	public List<String> getPassangerName(){
		List<String> passangerName = new ArrayList<>();
		for(Map.Entry<String, String> passanger : passangersAndSeatName.entrySet()) {
			passangerName.add(passanger.getKey());
		}
		return passangerName;
	}
	
	public List<String> getSeatName(){
		List<String> seatName = new ArrayList<>();
		for(Map.Entry<String, String> passanger : passangersAndSeatName.entrySet()) {
			seatName.add(passanger.getValue());
		}
		return seatName;
	}
	
	
	

}

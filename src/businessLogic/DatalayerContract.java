package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Ticket;
import Model.User;
import Model.Vehicle;
import Model.VehicleType;

public interface DatalayerContract {
	public void setBookedTickes(HashMap<Vehicle,String> vehicle,Ticket ticket,VehicleType type);
	public void setWaitingList(HashMap<Vehicle,String> vehicle,Ticket ticket);
//	public void removeTicketForBookingList(Ticket ticket);
	public int numberWaitingTicket(HashMap<Vehicle,String> vehicle);
	public void removeTicketForBookingList(HashMap<Vehicle,String> vehicle,Ticket ticket);
	public User getUser(String name);
	public ArrayList<HashMap<Vehicle,String>>getAvalibleVehicles(String source,String destination,VehicleType type);
	public void setUserDetails(String name,String[] details);
	public boolean passwordMatch(String name,String password);
	public boolean checkUserExists(String name);
	public int getPrice(String source,String destination,HashMap<Vehicle,String> vehicle,String allocation);
	public boolean noUserName(String email);
	public List<String> getBookedTickets(HashMap<Vehicle,String> vehicle,String source,String destination);
	public void setPassword(String userEmail,String password);
	public void setUpdateName(String email ,String name);
	public int getTotalNumberSeats(HashMap<Vehicle,String> vehicle);
	public String getUserName(String email);
	public Vehicle getVehicleByTicket(Ticket ticket);
	public int getBasicPrice(String source,String destination,HashMap<Vehicle,String> vehicleMap);
	public List<Ticket> getWaitingList(HashMap<Vehicle,String> vehicel);
	public void removeTickeFromWaitingList(HashMap<Vehicle,String> vehicle,Ticket ticket);
	public HashMap<String,Integer> getAvalibleSeats(HashMap<Vehicle, String> vehicle,List<String> bookedSeats,VehicleType type);
	public List<String[]> getAllBusRoute(VehicleType type);
	public void setCancelTicket(HashMap<Vehicle,String> vehicleMap,String cancelTikcet) ;
	public List<String> getCancelTicket(HashMap<Vehicle,String> vehicleMap,List<String> bookedSeats);
	public void removeCancelTicket(HashMap<Vehicle,String> vehicleMap,String cancelSeat);
//	public String getAvailableTrainSeatName(String seatName ,int numberOfSeat ,int totalBookedTicket,int noWaitingTicket,List<String> bookedSeats);
}

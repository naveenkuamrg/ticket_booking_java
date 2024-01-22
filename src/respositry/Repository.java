package respositry;

import java.util.*;

import Model.*;
import businessLogic.DatalayerContract;

public class Repository implements DatalayerContract{
	
	private final VehicleManager vehicleManager;
	private HashMap<HashMap<Vehicle,String>,List<Ticket>> tickets  = new HashMap<HashMap<Vehicle,String>,List<Ticket>>();;
	private HashMap<String,String[]> userDetails  = new HashMap<String,String[]>();;
	private HashMap<String,User> userMap  = new HashMap<String,User>();;
	private HashMap<HashMap<Vehicle,String>,List<Ticket>> waitingList = new HashMap<HashMap<Vehicle,String>,List<Ticket>>();;
	private HashMap<HashMap<Vehicle,String>,List<String>> cancelSeatsMap = new HashMap<HashMap<Vehicle,String>,List<String>>();
	
	
	public Repository(VehicleManager vehicleManager){
		this.vehicleManager = vehicleManager;
	}
	
	public List<String[]> getAllBusRoute(VehicleType type){
		return vehicleManager.getAllRouteByVehicleType(type);
	}
	
	public void setCancelTicket(HashMap<Vehicle,String> vehicleMap,String cancelTikcet) {

		if(cancelSeatsMap.get(vehicleMap) == null) {
			cancelSeatsMap.put(vehicleMap, new ArrayList<String>());
		}
//		System.out.println();
		cancelSeatsMap.get(vehicleMap).add(cancelTikcet);
	}
	
	public List<String> getCancelTicket(HashMap<Vehicle,String> vehicleMap,List<String> bookedSeats){
		List<String> cancelSeats = new ArrayList<String> ();
		if(cancelSeatsMap.get(vehicleMap) == null) {
			cancelSeatsMap.put(vehicleMap, new ArrayList<String>());
		}
		
		List<String> tempBookedSeats = new ArrayList<String>();
		for(String seat : bookedSeats) {
			tempBookedSeats.add(seat.trim());
		}
//		System.out.println(tempBookedSeats.toString());
//		System.out.println(cancelSeatsMap.get(vehicleMap).toString());
		for( String seat :  cancelSeatsMap.get(vehicleMap)) {
			if(!tempBookedSeats.contains(seat.trim())) {
				cancelSeats.add(seat);
			}
		}
//		System.out.println(cancelSeats.toString());
		return cancelSeats;
//		return cancelSeatsMap.get(vehicleMap);
	}
	
	public void removeCancelTicket(HashMap<Vehicle,String> vehicleMap,String cancelSeat) {
		if(cancelSeatsMap.get(vehicleMap) == null) {
			cancelSeatsMap.put(vehicleMap, new ArrayList<String>());
		}
		cancelSeatsMap.get(vehicleMap).remove(cancelSeat);
	}
	
	public void setBookedTickes(HashMap<Vehicle,String> vehicle,Ticket ticket,VehicleType type) {
		if(tickets.get(vehicle) == null) {
			tickets.put(vehicle, new ArrayList<Ticket>());
		}
		tickets.get(vehicle).add(ticket);		
	}
	
	public List<String> getBookedTickets(HashMap<Vehicle,String> vehicle,String source,String destination){
		boolean isSeatEmpty = false;
		ArrayList<String> bookedSeat = new ArrayList<String>();
		String[] route = vehicleManager.getRoute(vehicle);
		if(tickets.get(vehicle) != null) {
			
			HashMap<String,Integer> costMap = new HashMap<String,Integer>();
	        int rate = 1 ; 
	        for(String place :route) {
	        	costMap.put(place, rate);
	        }
	        
	      
	      
			
			
			
			
		for(Ticket ticket : tickets.get(vehicle)) {
			
			isSeatEmpty = true;
			 for(String place :route) {
				
				
//				if(ticket.getSource().equalsIgnoreCase(place)) {
//					isSeatEmpty = false;
//				}
//				if(source.equalsIgnoreCase(place) || destination.equalsIgnoreCase(place)) {
//					break;
//				}
//				
//				if(ticket.getDestination().equalsIgnoreCase(place)) {
//					isSeatEmpty = true;
//				}
				
				if(ticket.getSource().equals(destination)) {
					isSeatEmpty = true;
					break;
				}
				if(ticket.getDestination().trim().equalsIgnoreCase(place.trim())) {
	
					isSeatEmpty = true;
					break;
				}
				
				if(source.trim().equalsIgnoreCase(place.trim())) {
					isSeatEmpty = false;
//					  int costJ = costMap.get(source) + costMap.get(destination);
//					  isnt costT = costMap.get(ticket.getSource()) + costMap.get(ticket.getDestination());
					break;
					
				}
				
			}
			
	
			
			
			if(!isSeatEmpty) {
			for(Map.Entry<String, String> elements : ticket.getNameOfPassangers().entrySet()) {
				if(!bookedSeat.contains(elements.getValue()))
					bookedSeat.add(elements.getValue());
			}
			}
		}
		}
//		System.out.println(bookedSeat.toString());
		return bookedSeat;
	}
	
	public void setWaitingList(HashMap<Vehicle,String> vehicle,Ticket ticket) {
		if(waitingList.get(vehicle) == null) {
			waitingList.put(vehicle, new ArrayList<Ticket>());
		}
		waitingList.get(vehicle).add(ticket);
	}
	
	public List<Ticket> getWaitingList(HashMap<Vehicle,String> vehicel){
		return waitingList.get(vehicel);
	}
	
	public void removeTickeFromWaitingList(HashMap<Vehicle,String> vehicle,Ticket ticket) {
		waitingList.get(vehicle).remove(ticket);
	}
	
	public Vehicle getVehicleByTicket(Ticket ticket) {
		return vehicleManager.getVehicleByNumberAndTiming(ticket.getSource(),ticket.getDestination(), ticket.getVehicleNumber(), ticket.getTime());
	}
	
	public void removeTicketForBookingList(HashMap<Vehicle,String> vehicle,Ticket ticket) {
		tickets.get(vehicle).remove(ticket);
	}
	
	

	public User getUser(String name) {
		return userMap.get(name);
	}
	

	
	public ArrayList<HashMap<Vehicle,String>>getAvalibleVehicles(String source,String destination,VehicleType type) {
		ArrayList<HashMap<Vehicle,String>> avalibleVechicles= new ArrayList<HashMap<Vehicle,String>>();
		HashMap<Vehicle,String> vehicles = vehicleManager.getVehicle(source, destination);
		if(vehicles == null) {
			return null;
		}
		for(HashMap.Entry<Vehicle, String> avalibleVechicle : vehicles.entrySet()) {
			HashMap<Vehicle,String> curentVechicle = new HashMap<Vehicle,String>();
			switch(type) {
			case bus:
			if(avalibleVechicle.getKey() instanceof Bus) {
				curentVechicle.put(avalibleVechicle.getKey(),avalibleVechicle.getValue());
			}
			break;
			case airplane:
				if(avalibleVechicle.getKey() instanceof Airplane) {
					curentVechicle.put(avalibleVechicle.getKey(),avalibleVechicle.getValue());
				}
				break;
			case train:
				if(avalibleVechicle.getKey() instanceof Train) {
					curentVechicle.put(avalibleVechicle.getKey(),avalibleVechicle.getValue());
				}
				
			}
			if(!curentVechicle.isEmpty()) {
				avalibleVechicles.add(curentVechicle);
			}
		}
		
		return avalibleVechicles;
		
	}
	
	public void setUserDetails(String email,String[] details) {
		userDetails.put(email, details);
		userMap.put(email, new User(email));
	}
	
	public String getUserName(String email) {
		return userDetails.get(email)[0];
	}
	
	public boolean passwordMatch(String email,String password) {
		return userDetails.get(email)[2].equals(password);
	}
	
	public void setPassword(String userEmail,String password) {
		userDetails.get(userEmail)[2] = password.trim();
		userMap.get(userEmail).setNotification(new Notification("your password is change"));
	}
	
	public boolean checkUserExists(String email){
		return userDetails.containsKey(email);
	}
	
	public int getPrice(String source,String destination,HashMap<Vehicle,String> vehicleMap,String allocation){
		Vehicle vehicle = vehicleMap.entrySet().iterator().next().getKey();
		if(vehicle instanceof Airplane) {
//			System.out.println((getTotalNumberSeats(vehicleMap)));
//			System.out.println((getBookedTickets(vehicleMap,source).size()));
//			System.out.println((getTotalNumberSeats(vehicleMap)-getBookedTickets(vehicleMap,source).size()));
			return vehicleManager.getPrice(source, destination)*vehicle.getRateForSeattype(allocation)+((getBookedTickets(vehicleMap,source,destination).size()+1));
		}else {
		return vehicleManager.getPrice(source, destination)*vehicle.getRateForSeattype(allocation);
		}
	}
	
	public int getBasicPrice(String source,String destination,HashMap<Vehicle,String> vehicleMap) {
		Vehicle vehicle = vehicleMap.entrySet().iterator().next().getKey();
		if(vehicle instanceof Airplane) {
			return vehicleManager.getPrice(source, destination)+(getBookedTickets(vehicleMap,source,destination).size());
		
		}else {
		return vehicleManager.getPrice(source, destination);
		}
	}
	
	public boolean noUserName(String email) {
		return userDetails.containsKey(email);
	}
	

	
	
	
	public void setUpdateName(String email ,String name) {
		userDetails.get(email)[1] = name;
		userMap.get(email).setNotification(new Notification("your name is change to "+name));
	}
	
	public int getTotalNumberSeats(HashMap<Vehicle,String> vehicle) {
		int totalNumberSeat = 0;
		for(Map.Entry<String, Integer> seat : vehicle.entrySet().iterator().next().getKey().getNoOfSeats().entrySet()) {
    		totalNumberSeat += seat.getValue();
    	}
		
		return totalNumberSeat;
	}
	
	public int numberWaitingTicket(HashMap<Vehicle,String> vehicle) {
		int numberOfWaitingTicket = 0 ;
		if(waitingList.get(vehicle) != null){
		for(Ticket ticket : waitingList.get(vehicle)) {
			for(Map.Entry<String, String> passengerAndSeat : ticket.getNameOfPassangers().entrySet()) {
//				System.out.println(passengerAndSeat.getValue());
				if(passengerAndSeat.getValue().regionMatches(true, 2, "w", 0, 1))
					numberOfWaitingTicket++;
			}
		
		}
		}
		return numberOfWaitingTicket;
	}
	
	
	public  HashMap<String,Integer> getAvalibleSeats(HashMap<Vehicle, String> vehicle,List<String> bookedSeats,VehicleType type) {
		switch(type) {
		case airplane:
			return getAirplaneAvalibleSeats(vehicle,bookedSeats);
		case bus:
			return getBusAvalibleSeats(vehicle,bookedSeats);
			
		case train:
			return getTrainAvalibleSeats(vehicle,bookedSeats);
			
		}
		return null;
	}
	
	
	public HashMap<String,Integer> getTrainAvalibleSeats(HashMap<Vehicle, String> vehicle,List<String> bookedSeats) {
		HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();
		int firstClassAC = 0;
		int firstClass = 0 ;
		int secondClass = 0 ;
		int bookedfca = 0,bookedfc = 0 , bookedsc = 0 ;
		HashMap<String,Integer> avalibleSeats = new HashMap<>();
		for(Map.Entry<String,Integer> seat : seats.entrySet()) {
			if(seat.getKey().equalsIgnoreCase("fau") || seat.getKey().equalsIgnoreCase("fam") || seat.getKey().equalsIgnoreCase("fal")) {
				firstClassAC += seat.getValue();
			}
			if(seat.getKey().equalsIgnoreCase("fcu") || seat.getKey().equalsIgnoreCase("fcm") || seat.getKey().equalsIgnoreCase("fcl")) {
				firstClass += seat.getValue();
			}
			if(seat.getKey().equalsIgnoreCase("scu") || seat.getKey().equalsIgnoreCase("scm") || seat.getKey().equalsIgnoreCase("scl")) {
				secondClass += seat.getValue();
			}
		}
		

		for(String bookedSeat : bookedSeats) {

			if(bookedSeat.regionMatches(true, 0, "ac", 0, 2)) {
				bookedfca++;
			}
			if(bookedSeat.regionMatches(true, 0, "fc", 0, 2)) {
				bookedfc++;
			}
			if(bookedSeat.regionMatches(true, 0, "sc", 0, 2)) {
				bookedsc++;
			}
		}
		avalibleSeats.put("firstClassAC", firstClassAC - bookedfca);
		avalibleSeats.put("firstClass", firstClass - bookedfc);
		avalibleSeats.put("secondClass", secondClass- bookedsc);
		
		return avalibleSeats;
	}
	
	
	public HashMap<String,Integer> getBusAvalibleSeats(HashMap<Vehicle, String> vehicle,List<String> bookedSeats) {
		HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();
		int sleeper = 0;
		int normal = 0 ;
		
		int bookedN = 0,bookedS = 0 ;
		HashMap<String,Integer> avalibleSeats = new HashMap<>();
	
		for(Map.Entry<String,Integer> seat : seats.entrySet()) {
			if(seat.getKey().equals("sleeper"))
				sleeper = seat.getValue();
			if(seat.getKey().equals("normalSeats"))
				normal = seat.getValue();
		}
		
		for(String bookedSeat : bookedSeats) {

			if(bookedSeat.regionMatches(true, 0, "s", 0, 1)) {
				bookedS++;
			}
			if(bookedSeat.regionMatches(true, 0, "n", 0, 1)) {
				bookedN++;
			}
			
		}
//		System.out.println(bookedN);
//		System.out.println(bookedS);
		avalibleSeats.put("sleeper", sleeper - bookedS);
		avalibleSeats.put("normalSeats",normal - bookedN);
		
		return avalibleSeats;
	}
	
	
	public HashMap<String,Integer> getAirplaneAvalibleSeats(HashMap<Vehicle, String> vehicle,List<String> bookedSeats) {
		HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();

		int businessclass = 0;
		int economyclass = 0 ;
		int firstclass = 0 ;
		int bookedB = 0,bookedE = 0,bookedF = 0 ;
		HashMap<String,Integer> avalibleSeats = new HashMap<>();
	
		for(Map.Entry<String,Integer> seat : seats.entrySet()) {
			if(seat.getKey().equals("businessclass"))
				businessclass = seat.getValue();
			if(seat.getKey().equals("economyclass"))
				economyclass = seat.getValue();
			if(seat.getKey().equals("firstclass"))
				firstclass = seat.getValue();
		}

		for(String bookedSeat : bookedSeats) {

			if(bookedSeat.regionMatches(true, 0, "B", 0, 1)) {
				bookedB++;
			}
			if(bookedSeat.regionMatches(true, 0, "E", 0, 1)) {
				bookedE++;
			}
			if(bookedSeat.regionMatches(true, 0, "F", 0, 1)) {
				bookedF++;
			}
			
		}
		avalibleSeats.put("businessclass", businessclass - bookedB);
		avalibleSeats.put("economyclass",economyclass - bookedE);
		avalibleSeats.put("firstclass",firstclass - bookedF);
		
		return avalibleSeats;
	}

	
	
	
	
	

}

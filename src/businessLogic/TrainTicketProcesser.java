package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Notification;
import Model.Ticket;
import Model.TicketStatus;
import Model.User;
import Model.Vehicle;
import Model.VehicleType;
import inputoutput.Printer;
import inputoutput.UserInputScanner;

public  class TrainTicketProcesser   {
	DatalayerContract repository;
	Printer printer;
	UserInputScanner in;
	User user;
	TrainTicketProcesser(DatalayerContract repository, Printer printer, UserInputScanner in,User user) {
//		super(repository, printer, in);
		this.repository = repository;
		this.printer = printer;
		this.in = in;
		this.user = user;
	}
	
	
	protected void trainSeatAllocation(HashMap<Vehicle,String> vehicle,Ticket ticket,boolean isBooking) {
//		int bookedfca = 0,bookedfc = 0 , bookedsc = 0 ;
		int noWaitingTicket = 0 ;
		List<String> bookedSeats = repository.getBookedTickets(vehicle,ticket.getSource(),ticket.getDestination());
		if(isBooking) {
			noWaitingTicket = repository.numberWaitingTicket(vehicle);
		}else {
			noWaitingTicket = (repository.getTotalNumberSeats(vehicle)/repository.getAvalibleSeats(vehicle, bookedSeats, VehicleType.train).size()) - repository.numberWaitingTicket(vehicle);
		}
		boolean flage = true;
		List<String> cancelSeats = new ArrayList<String>();
		if(repository.getCancelTicket(vehicle,bookedSeats) != null) {
		for(String cancelSeat : repository.getCancelTicket(vehicle,bookedSeats)) {
			if(ticket.getNameOfPassangers().entrySet().iterator().next().getValue().regionMatches(true, 0, cancelSeat, 0, 2)) {
//				System.out.println(cancelSeat);
				cancelSeats.add(cancelSeat);
			}
		}
		}
		
		
		String coach = ticket.getSeatName().get(0).substring(0, 2);
			
			
			
//		for(String bookedSeat :bookedSeats ) {
//			if(bookedSeat.regionMatches(true, 0, "ac", 0, 2)) {
//				bookedfca++;
//			}
//			if(bookedSeat.regionMatches(true, 0, "fc", 0, 9)) {
//				bookedfc++;
//			}
//			if(bookedSeat.regionMatches(true, 0, "sc", 0, 10)) {
//				bookedsc++;
//			}
//			
//		}
		
		
		int[]  bookedBerth = new int[3];
		for(String bookedSeat : bookedSeats) {
			if(bookedSeat.regionMatches(true, 0, coach+"u", 0, 3)) {
				bookedBerth[0]++;
			}
			if(bookedSeat.regionMatches(true, 0, coach+"m", 0, 3)) {
				bookedBerth[1]++;
			}
			if(bookedSeat.regionMatches(true, 0, coach+"l", 0, 3)) {
				bookedBerth[2]++;
			}
			
		}
		
		for(String cancelSeat : cancelSeats) {
			if(cancelSeat.regionMatches(true, 2, coach+"u", 0, 1)) {
				bookedBerth[0]++;
			}
			if(cancelSeat.regionMatches(true, 2, coach+"m", 0, 1)) {
				bookedBerth[1]++;
			}
			if(cancelSeat.regionMatches(true, 2, coach+"l", 0, 1)) {
				bookedBerth[2]++;
			}
			
		}
		TicketStatus tStatus = TicketStatus.BOOKED;
		HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();
		String seatNumber;
	
		ticket.setStatus(tStatus);
		for(Map.Entry<String, String> passanger : ticket.getNameOfPassangers().entrySet()) {
			seatNumber = passanger.getValue();
			if(seatNumber.regionMatches(true, 2, "w", 0, 1) && !isBooking) {
				seatNumber = seatNumber.substring(0, 2);
			}
		 switch(seatNumber) {
		 case "AC":
			 seatNumber = getTrainSeatName(seatNumber.substring(0, 2) ,seats.get("fau"),bookedSeats,noWaitingTicket,bookedBerth,cancelSeats,vehicle);
//			 bookedfca++;
			 break;
		 case "FC":
			 seatNumber = getTrainSeatName(seatNumber ,seats.get("fcu"),bookedSeats,noWaitingTicket,bookedBerth,cancelSeats,vehicle);
//			 bookedfc++;
			 break;
		 case "SC":
			 seatNumber = getTrainSeatName(seatNumber ,seats.get("scu"),bookedSeats,noWaitingTicket,bookedBerth,cancelSeats,vehicle);
//			 bookedsc++;
			 break;
		 }
		 
			 if(seatNumber.regionMatches(true, 2, "w", 0, 1)) {
				 noWaitingTicket++;
				 if(flage) {
					 if(isBooking) {
					 repository.setWaitingList(vehicle,ticket);
					 flage = false;
					 }
				 }
			 }
		 
			 if(isBooking || !seatNumber.regionMatches(true, 2, "w", 0, 1)) {
				 ticket.getNameOfPassangers().put(passanger.getKey(), seatNumber);
			 }
		}
		
		user.setNotification(new Notification("Your ticket id :"+ticket.getId()+" Source "+ticket.getSource() +" Destination "+ticket.getDestination() +" is "+ticket.getStatus()+" confirm "));
		
	}
	
	protected String getTrainSeatName(String seatName ,int numberOfSeat ,List<String> bookedTickets,int noWaitingTicket,int[] bookedBerth,List<String> cancelSeats,HashMap<Vehicle,String> vehicle) {
		String updateSeatName = seatName;
		if(!cancelSeats.isEmpty()) {
			updateSeatName = cancelSeats.get(0);
			cancelSeats.remove(updateSeatName);
			repository.removeCancelTicket(vehicle, updateSeatName);
			return updateSeatName;
		}
		
		
		
		if(numberOfSeat > bookedBerth[0]) {
			bookedBerth[0]++;
			updateSeatName += "U"+(bookedBerth[0]);
		 }else if(numberOfSeat > bookedBerth[1]) {
			 bookedBerth[1]++;
			 updateSeatName += "M"+(bookedBerth[1]);
		 }else if(numberOfSeat > bookedBerth[2]) {
			 bookedBerth[2]++;
			 updateSeatName += "L"+(bookedBerth[2]);
		 }else{
			 updateSeatName += "W"+(noWaitingTicket+1);
		 }

		return updateSeatName;
		
	}


}

package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Notification;
import Model.Ticket;
import Model.TicketStatus;
import Model.Vehicle;
import Model.VehicleType;
import inputoutput.Printer;
import inputoutput.UserInputScanner;

public class TicketCancellingCommand extends Command{
	
	TicketCancellingCommand(DatalayerContract repository, Printer printer,UserInputScanner in) {
		super(repository, printer,in);
		code = "cancel";
	}
	
	@Override
	void execute(String command) {
		if(user == null) {
			printer.errorMessage("Please login using signin code");
			return;
		}
		List<Ticket> tickets = new ArrayList<Ticket>();
		boolean flage = true;
		int index = 0 ;
		int removeIndex = -1;
		Ticket ticket = null;
		List<Ticket> listOfTicket =  user.getTickets();
		for(Ticket tempTicket :listOfTicket) {
			if(tempTicket.getStatus() != TicketStatus.CANCEL) {
				tickets.add(tempTicket);
				System.out.print("index : "+index+" | ");
				printer.ticketPrinter(tempTicket);
				flage = false;
				System.out.println();
				index++;
			}
			
		}
		if(flage) {
			printer.errorMessage("sorry , you don't had ticket to cancel");
			return;
		}
		
		do {
			if(flage) {
		    	printer.errorMessage("please enter the valid index");
		    }
	        removeIndex = in.getInputNumber("Enter the index number cancellation");
	        flage = true;
		}while(!(0<=removeIndex && index>removeIndex));
		ticket = tickets.get(removeIndex);
		HashMap<Vehicle,String> vehicleMap = null; 
		Vehicle vehicles = repository.getVehicleByTicket(ticket);
		for(HashMap<Vehicle, String> vehicle : repository.getAvalibleVehicles(ticket.getSource(),ticket.getDestination(),ticket.getVehicleType())){
			if(vehicle.get(vehicles) != null) {
			if(vehicle.get(vehicles).equals(ticket.getTime())) {
				vehicleMap = vehicle;
				break;
			}
			}
		}
		

		do {
			switch(in.getInput("Enter 1 to cancel ticket \nEnter 2 to partial cancellation")) {
			case "1":
			cancelTicket(tickets.get(removeIndex),vehicleMap);
			printer.successMessage(" successFully cancel "+ tickets.get(removeIndex).getSource() +" -> "+tickets.get(removeIndex).getDestination()+" id :"+tickets.get(removeIndex).getId()+"  "+tickets.get(removeIndex).getPassangerName().toString());
			flage = false;
			break;
			
			case "2":
				partialCancellation(tickets.get(removeIndex),vehicleMap);	
				flage = false;
				break;
			default:
				printer.errorMessage("your optioin in not valid");
				if(!in.getInput("enter 1 to choose the option or enter any to exit").equals("1")) {
					return;
				}
				break;
			}
		}while(flage);
		
	   List<Ticket> waitingList = repository.getWaitingList(vehicleMap);
		if(repository.getWaitingList(vehicleMap) != null) {
			TrainTicketProcesser processer = new TrainTicketProcesser(repository, printer, in, user);
			for(Ticket waitingTicket : waitingList) {
	
				processer.trainSeatAllocation(vehicleMap,waitingTicket,false);
			}
		
		
			int counterWaitingList = 1; 
			for(Ticket waitingTicket : waitingList) {
				for(Map.Entry<String, String> passangerMap : waitingTicket.getNameOfPassangers().entrySet()) {
					if(passangerMap.getValue().regionMatches(true, 2, "w", 0, 1)) {
							waitingTicket.getNameOfPassangers().put(passangerMap.getKey(), passangerMap.getValue().substring(0, 3)+counterWaitingList);
						counterWaitingList++;
					}
				}
			}
		}
	 
	}
	
	private void cancelTicket(Ticket ticket,HashMap<Vehicle,String> vehicleMap) {
		ticket.setStatus(TicketStatus.CANCEL);
		for(Map.Entry<String, String> passenger : ticket.getNameOfPassangers().entrySet()) {
			if(!passenger.getValue().regionMatches(true, 2, "w", 0, 1))
			  repository.setCancelTicket(vehicleMap, passenger.getValue());
			ticket.getNameOfPassangers().put(passenger.getKey(),"CAN");
		}
		user.setNotification(new Notification(" "+ticket.getId()+" this tickes is cancel"));
	
	}
	
	
	void partialCancellation(Ticket ticket,HashMap<Vehicle,String> vehicleMap) {
		int index = 1,cancelSize = 0 ; 
		boolean flag = false;
		List<String> cancelPassengerNames = new ArrayList<String>();
		String[] passengerNames = new String[ticket.getNameOfPassangers().size()];
		HashMap<String,String> passengers = ticket.getNameOfPassangers();
		for(Map.Entry<String, String> passenger : passengers.entrySet()) {
			System.out.println(index+"."+passenger.getKey() +" - "+passenger.getValue());
			passengerNames[index-1] = passenger.getKey();
			index++;
		}
		do {
			cancelSize = in.getInputNumber("Enter the noOfPassenger to cancel to ticket");
			if( cancelSize >= passengers.size() || cancelSize <= 0) {
				printer.errorMessage("sorry you can't cancel total number of passenger or more than that ");
				flag = true;
			}else {
			 flag = false;
			}
		}while(flag);
		int check = 0 ;
		do {
		 int removeIndex = in.getInputNumber("enter the index of passenger");
		 if(0>=removeIndex || removeIndex>ticket.getNameOfPassangers().size() || ticket.getNameOfPassangers().get(passengerNames[removeIndex-1]).equals("CAN")) {
			 if(ticket.getNameOfPassangers().get(passengerNames[removeIndex-1]).equals("CAN")){
				 printer.errorMessage("Alredy ticked is Cancel");
			 }else {
				 printer.errorMessage("please enter the correct index of passenger");
			 }
		 }else {
			 if(cancelPassengerNames.contains(passengerNames[removeIndex-1])) {
				 printer.errorMessage("Alredy ticked is Cancel");
			 }else {
			 cancelPassengerNames.add(passengerNames[removeIndex-1]);
			 check++;
			 }
			 
		 }
			
		}while(check < cancelSize);
		for(String passangerName : cancelPassengerNames) {
			if(!ticket.getNameOfPassangers().get(passangerName).regionMatches(true, 2, "w", 0, 1))
			  repository.setCancelTicket(vehicleMap,ticket.getNameOfPassangers().get(passangerName));
			ticket.getNameOfPassangers().put(passangerName, "CAN");
			
		}
		String names = "";
		int nameCounter = 1;
		for(String name : cancelPassengerNames) {
			  names += name;
			if(nameCounter < cancelPassengerNames.size() )
				names += " , ";
			nameCounter++;
		}
		printer.successMessage(" successFully cancel ->"+names);
		user.setNotification(new Notification(ticket.getId()+" successFully cancel ->  "+names));
	}
	
	
	
//	HashMap<Vehicle,String> seatAllocateAnotherPassanger(Ticket ticket,List<String> cancelPassengers) {
//		HashMap<Vehicle,String> vehicleMap = null;
//		Vehicle vehicles = repository.getVehicleByTicket(ticket);
//		for(HashMap<Vehicle, String> vehicle : repository.getAvalibleVehicles(ticket.getSource(),ticket.getDestination(),ticket.getVehicleType())){
//			if(vehicle.get(vehicles) != null) {
//			if(vehicle.get(vehicles).equals(ticket.getTime())) {
//				vehicleMap = vehicle;
//				break;
//			}
//			}
//		}
//		int balanceTicket = cancelPassengers.size();
//		List<Ticket> removeTicketWaitingList  = new ArrayList<Ticket>();
////		List<String> avaliableSeat = ticket.getSeatName();
//		if(ticket.getVehicleType() == VehicleType.train) {
//			List<Ticket> waitinglist = repository.getWaitingList(vehicleMap) ;
//			if(waitinglist != null) {
//				for(Ticket currentTicket : waitinglist) {
//					if(currentTicket.getSeatName().get(0).regionMatches(false, 0, ticket.getSeatName().get(0), 0, ticket.getSeatName().get(0).length() - 3)) {
//							for(String passangerName : currentTicket.getPassangerName()) {
//								if(balanceTicket <= 0) {
//									balanceTicket--;
//									currentTicket.getNameOfPassangers().put(passangerName,(currentTicket.getSeatName().get(0).substring(0, 2)+"W"+(balanceTicket*-1)).trim());
//									
//								}
//								if(currentTicket.getNameOfPassangers().get(passangerName).regionMatches(true, 2, "w", 0, 1) && balanceTicket > 0) {
//									balanceTicket--;
//									currentTicket.getNameOfPassangers().put(passangerName, ticket.getNameOfPassangers().get(cancelPassengers.get(balanceTicket)));
//			
//								}
//								
//							}
//							removeTicketWaitingList.add(currentTicket);
//							user.setNotification(new Notification(" "+currentTicket.getId()+" this tickes is booked scussessfully"));
//						
//							}
//				
//				  }
//			}
//		}
//		
//		for(Ticket remoevTicket : removeTicketWaitingList) {
//			boolean flag = true;
//			for(String seat : remoevTicket.getSeatName()) {
//				if(seat.regionMatches(true, 2, "w", 0, 1)) {
//					flag = false;
//				}
//			}
//			
//			if(flag) {
//				repository.removeTickeFromWaitingList(vehicleMap,remoevTicket);
//			}
//		
//		}
//		
//		return vehicleMap;
//	}


}

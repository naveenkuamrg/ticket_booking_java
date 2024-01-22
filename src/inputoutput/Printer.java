package inputoutput;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Ticket;
import Model.*;


public class Printer {
	
	private static Printer printer; 
	String resetColorCode = "\u001B[0m";
	
	
	
	
	public static Printer getPrinterInstent() {
		if(printer == null) {
			printer = new Printer();
		}
		return printer;
	}
	
		
	public void printBusSeats( HashMap<Vehicle, String> vehicle,List<String> bookedSeats,List<String> seatNames) {
			
			HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();
			
			
			int sleeper = 0;
			int normal = 0;
			int totalseats = 0;
			for( Map.Entry<String,Integer> seat : seats.entrySet()) {
				if("normalSeats".equals(seat.getKey())) {
				 normal = seat.getValue();
				}else {
					sleeper = seat.getValue();
				}
				
				totalseats += seat.getValue();
			}
			String bookedSeat = " x";
//			System.out.println(normal+" "+sleeper+" "+totalseats);
			String seatNumber;
			for(int i = 1 ; i <= totalseats ; i++) {
				if(sleeper >= i) {
					seatNumber  = " S"+i;
					if(bookedSeats.contains(seatNumber.trim())) {
						System.out.printf("%-12s",bookedSeat);
					}else {
						System.out.printf("%-12s",seatNumber);
						seatNames.add(seatNumber.trim());
					}
					
					if(i == sleeper/2 || sleeper == i) {
						System.out.println();
					}
				}else {
					//1.2:3
					seatNumber  = " N"+i;
					if(bookedSeats.contains(seatNumber.trim())) {
						System.out.printf("%-6s",bookedSeat);
					}else {
						System.out.printf("%-6s",seatNumber);
						seatNames.add(seatNumber.trim());
					}
					if(i-sleeper == normal/2) {
						System.out.println();
					}
				}
			}
			
			System.out.println();
		}
	
	
	
	public void printAirplaneSeats(HashMap<Vehicle,String> vehicle,List<String> bookedSeats,List<String> seatNames) {
		HashMap<String,Integer> seats = vehicle.entrySet().iterator().next().getKey().getNoOfSeats();
		String seatName;
		String bookedSeat = " x";
		for(Map.Entry<String,Integer> seat : seats.entrySet()) {
			switch(seat.getKey().toLowerCase()) {
			case "firstclass":
				System.out.println("--------FirstClass---------");
				for(int seatno = 1 ; seatno <= seat.getValue() ;seatno++) {
					seatName = " F"+seatno+" ";
//					System.out.println( bookedSeats.contains(seatName.trim()) );
					if(!bookedSeats.contains(seatName.trim())) {
						System.out.printf("%-5s",seatName);
						seatNames.add(seatName.trim());
					}else {
						System.out.printf("%-5s",bookedSeat);
					}
					if(seatno%4 == 0) {
						System.out.println();
					}
				}
				break;
			case "businessclass":
				System.out.println("--------businessClass---------");
				for(int seatno = 1 ; seatno <= seat.getValue() ;seatno++) {
					seatName = " B"+seatno+" ";
//					System.out.println( bookedSeats.contains(seatName.trim()) );
					if(!bookedSeats.contains(seatName.trim())) {
						System.out.printf("%-5s",seatName);
						seatNames.add(seatName.trim());
					}else {
						System.out.printf("%-5s",bookedSeat);
					}
					if(seatno%4 == 0) {
						System.out.println();
					}
				}
				break;
			case "economyclass":
				System.out.println("--------economyclass---------");
				for(int seatno = 1 ; seatno <= seat.getValue() ;seatno++) {
					seatName = " E"+seatno+" ";
//					System.out.println( bookedSeats.contains(seatName.trim()) );
					if(!bookedSeats.contains(seatName.trim())) {
					   System.out.printf("%-5s",seatName);
					   seatNames.add(seatName.trim());
					}else {
						System.out.printf("%-5s",bookedSeat);
					}
					if(seatno%4 == 0) {
						System.out.println();
					}
				}
				break;
				
			}
		}

	}

		public void successMessage(String message) {
			System.out.println("\u001B[32m"+message+resetColorCode);
		}
		
		public void errorMessage(String message) {
			System.out.println("\u001B[31m"+message+resetColorCode);
		}
		
		public void notificationMessage(String message) {
			System.out.println("\u001B[34m"+message+resetColorCode);
			System.out.println();
		}
		
		public void ticketPrinter(Ticket ticket){
			System.out.println("\t----------------------------------------");
			System.out.println("\t Ticket id       : "+ticket.getId()+"\t"+ticket.getSource()+"->"+ticket.getDestination());
			System.out.println("\t Vehicle No :"+ticket.getVehicleNumber());
			for(Map.Entry<String,String> passanger : ticket.getNameOfPassangers().entrySet()) {
				System.out.println("\t "+passanger.getKey()+"  -  "+passanger.getValue());
			}
			System.out.println("\t Status : "+ticket.getStatus()+"\tRS:"+ticket.getPrice());
			System.out.println("\t----------------------------------------");
		}
		
		public void highlightMessage(String message) {
			System.out.println("\u001B[38;2;255;165;0m"+message+resetColorCode);
		}
		
		public void printRouts(List<String[]> routes) {
//			System.out.println(routes.get(0).toString()+" -> "+routes.get(routes.size()-1).toString());
			for(String[] route : routes) {
				int index = 1 ;
				for(String place : route) {
//						if(index == 1)
							System.out.print(place);
						if(index != route.length) {
						System.out.print(" -> ");
						}
					index++;
				}
				
				System.out.println(" ");
			}
		}
		
		public void priceOfTicket(HashMap<String,Integer> avalibleSeats) {
			for(Map.Entry<String,Integer> avalibleSeat : avalibleSeats.entrySet()) {
				if(avalibleSeat.getValue() >= 0) {
					highlightMessage("\t\t "+avalibleSeat.getKey()+" - "+avalibleSeat.getValue());
				}else {
					highlightMessage("\t\t "+avalibleSeat.getKey()+" - W"+avalibleSeat.getValue()*-1);
				}
			}
		}
}

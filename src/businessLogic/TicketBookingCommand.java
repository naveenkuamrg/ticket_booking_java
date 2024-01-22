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

public class TicketBookingCommand  extends Command {
	
	TicketBookingCommand(DatalayerContract repository,Printer printer,UserInputScanner in) {
		super(repository,printer,in);
		// TODO Auto-generated constructor stub
		code = "book";
	}

	@Override
	void execute(String command) {
		if(user == null) {
			printer.errorMessage("Please login using signin code");
			return;
		}
		
		VehicleType type = null ;
		boolean isSelectType = false;
		boolean routeFlage = false;
		int noPassenger = 0;
		List<String> seatsName = new ArrayList<String>();
		ArrayList<HashMap<Vehicle,String>> avalibleVehicles;
		String source = null;
		String destination;
		System.out.println("select the mode of transport \n\t 1 : Airplane \n\t 2 : Bus \n\t 3 : Train");
		do {
		switch(in.getInput("")) {
		case "1":
			type = VehicleType.airplane;

			isSelectType = true;
			break;
		case "2":
			type = VehicleType.bus;
			isSelectType = true;
			break;
		case "3":
			type = VehicleType.train;
			isSelectType = true;
			break;
		default:
			isSelectType = false;
			printer.errorMessage("please enter the correct mode of transport");
			
		}
		}while(!isSelectType);
		printer.printRouts(repository.getAllBusRoute(type));
		int index = 1 ;
		do{
			if(!routeFlage)
				source =  in.getInput("Enter the source");
			destination = in.getInput("Enter the destination");
			avalibleVehicles = repository.getAvalibleVehicles(source,destination, type);
		    if(avalibleVehicles == null || avalibleVehicles.size() == 0) {
		    	printer.errorMessage("Route is not found");
		    	source = in.getInput("Enter route source again or 1 to exit");
		    	if(!source.equals("1")) {
		    	routeFlage = true;
		    	}else {
		    		return;
		    	}
		    }else {
		    	routeFlage = false;
		    }
		}while(routeFlage);
		
		for(HashMap<Vehicle,String> vehicle : avalibleVehicles) {
			Map.Entry<Vehicle, String> obj = vehicle.entrySet().iterator().next();
			System.out.println(index++ +" "+obj.getKey().getNumber()+" \t "+obj.getValue()+ " \t ");
			printer.priceOfTicket(repository.getAvalibleSeats(vehicle, repository.getBookedTickets(vehicle, source,destination), type));
		}
		HashMap<Vehicle, String> vehicle = avalibleVehicles.get(in.chooseVehicle("chooses your "+type,index)-1);
		List<String> bookedSeats = repository.getBookedTickets(vehicle,source,destination);
		

		
		switch(type) {
		case bus:
			printer.printBusSeats(vehicle, bookedSeats,seatsName);
			if(in.getInput("Enter -1 to exit or any key to continue").trim().equals("-1")) {
				return;
			}
			break;
		case airplane:
			printer.printAirplaneSeats(vehicle,bookedSeats,seatsName);
			if(in.getInput("Enter -1 to exit or any key to continue").trim().equals("-1")) {
				return;
			}
			break;
		default:
			break;
	
		}
		
		do {
			if(noPassenger == -1) {
				if(in.getInput("enter -1 for exits or any key to continue ").trim().equals("-1"))
					return;
			}
		    noPassenger = in.getNoOfPassnager("Enter the number of Passenger ",repository.getAvalibleSeats(vehicle, bookedSeats, type),type);
		}while(noPassenger == -1);
		HashMap<String,String> passangerNameAndSeatNumber = new HashMap<>();
		int counter = 1;
		String className = null;
		int totalPrice = 0 ;
		 do{
			 String passangerName;
			 String allocation = null;
			 if(type == VehicleType.train) {
				 className = seatSelecting(passangerNameAndSeatNumber,className,repository.getAvalibleSeats(vehicle, bookedSeats,type),noPassenger);
				 allocation = className ;
			 }else {
				 passangerName = in.getInputName("Enter passenger name");
					while(passangerNameAndSeatNumber.containsKey(passangerName)) {
						passangerName += " ";
					}
				 allocation = seatSelecting(passangerNameAndSeatNumber,bookedSeats,passangerName,seatsName);
			 }
			 
			 if(allocation != null) {
				 int price = repository.getPrice(source, destination,vehicle,allocation);
				 totalPrice += price;
				 counter++;
			 }else {
				 if(in.getInput("enter -1 to exit or enter any key to continue").equals("-1"))
					 return;
			 }
		
		}while(noPassenger >= counter);
		 
		System.out.println("Total price is Rs : "+totalPrice);
		
		Ticket ticket = new Ticket(user.getEmail(),vehicle.entrySet().iterator().next().getKey().getNumber(),source,destination, passangerNameAndSeatNumber,vehicle.entrySet().iterator().next().getValue(),type,totalPrice);
		setBookedTickes(vehicle, ticket,type);
		if(type != VehicleType.train) {
			ticket.setStatus(TicketStatus.BOOKED);
			user.setNotification(new Notification("Your ticket id :"+ticket.getId()+" Source "+ticket.getSource() +" Destination "+ticket.getDestination() +" is "+ticket.getStatus()+" booked "));
		}
		
		user.setTicket(ticket);
		printer.ticketPrinter(ticket);
		printer.successMessage("your process has completed please check notifiation use 'notify' code");
	
	}
	
	
	private String seatSelecting(HashMap<String,String> passangerNameAndSeatNumber,List<String> bookedSeats,String passangerName,List<String> avalivale) {
		String seatNumber;
		do {
			seatNumber = in.getInput("Enter the seat Number").toUpperCase();
			if(!bookedSeats.contains(seatNumber) && avalivale.contains(seatNumber)) {
				passangerNameAndSeatNumber.put(passangerName, seatNumber);
				bookedSeats.add(seatNumber);
				avalivale.remove(seatNumber);
				 }else if(bookedSeats.contains(seatNumber)) {
				  printer.errorMessage("seat is already booked");
				  seatNumber = null;
				 }else if(!avalivale.contains(seatNumber)) {
					 printer.errorMessage("select proper seats");
					 seatNumber = null;
				 }
			}while(seatNumber == null);
			return seatNumber;
	}
	
	private String seatSelecting(HashMap<String,String> passangerNameAndSeatNumber,String seatNumber,HashMap<String, Integer> avalibleSeat,int noOfpassangerNumber) {
		if(seatNumber == null) {
			switch(in.getInputNumber("select your coach\n 1 : AC \n 2 : Firstclass \n 3 : Secondclass")) {
			case 1:
				if((avalibleSeat.get("firstClassAC") + 5 )< noOfpassangerNumber) {
					printer.errorMessage("This coach is avalible for "+(avalibleSeat.get("firstClassAC") + 5) +"waiting list");
					return null;
				}
				seatNumber = "ac";
				break;
			case 2:
				if(avalibleSeat.get("firstClass") + 5 < noOfpassangerNumber) {
					printer.errorMessage("This coach is avalible for "+(avalibleSeat.get("firstClass") + 5 )+"waiting list");
					return null;
				}
				seatNumber = "fc";
				break;
			case 3:
				if(avalibleSeat.get("secondClass") + 5 < noOfpassangerNumber) {
					printer.errorMessage("This coach is avalible for "+(avalibleSeat.get("secondClass") + 5 )+"waiting list");
					return null;
				}
				seatNumber = "sc";
				break;
			default:
				printer.errorMessage("your option dosn't exits");
				return null;
			}
		}
		String name = in.getInputName("Enter passengers name");
		while(passangerNameAndSeatNumber.containsKey(name)) {
			name += " ";
		}
		passangerNameAndSeatNumber.put(name, seatNumber.toUpperCase());
		return seatNumber;
	}
	
	
	
	public void setBookedTickes(HashMap<Vehicle,String> vehicle,Ticket ticket,VehicleType type) {
		
		if(type == VehicleType.train) {
			TrainTicketProcesser processer = new TrainTicketProcesser(repository, printer, in, user);
			processer.trainSeatAllocation(vehicle,ticket,true);
		}
		repository.setBookedTickes(vehicle, ticket, type);
	}
	


}

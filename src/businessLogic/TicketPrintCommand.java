package businessLogic;


import java.util.List;

import Model.Ticket;
import inputoutput.Printer;
import inputoutput.UserInputScanner;

public class TicketPrintCommand extends Command{

	TicketPrintCommand(DatalayerContract repository, Printer printer,UserInputScanner in) {
		super(repository, printer,in);
		code = "print";
	}

	@Override
	void execute(String command) {
		if(user == null) {
			printer.errorMessage("please signin use 'signin' command");
			return;
		}
		
		int index = 1 ;
		boolean flage = false;
		List<Ticket> tickets = user.getTickets();
		if(tickets.size() == 0) {
			printer.errorMessage("you don't have tickets to print");
			return;
		}
		for(Ticket ticket : tickets) {
			printer.highlightMessage(index+". "+ticket.getDate()+" "+ticket.getSource()+"->"+ticket.getDestination()+" \tstatus : "+ticket.getStatus());

			index++;
		}
		
		do {
			int printTicketIndex = in.getInputNumber("enter the index to print the ticket") ;
			if(0<printTicketIndex && index>printTicketIndex) {
				printer.ticketPrinter(tickets.get(printTicketIndex-1));
				flage = false;
			}else {
				printer.errorMessage("enter correct index");
				flage = true;
			}
		}while(flage);
	
		
	}
	
	
	
}

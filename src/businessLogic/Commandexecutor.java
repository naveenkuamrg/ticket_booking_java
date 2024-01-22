package businessLogic;

import java.util.ArrayList;

import inputoutput.Printer;
import inputoutput.UserInputScanner;


public class Commandexecutor {
	ArrayList<Command> commands = new ArrayList<>();
	Command invalidcommand ;
	public Commandexecutor(DatalayerContract repository,Printer printer,UserInputScanner in){
		
		commands.add(new SignupCommand(repository,printer,in));
		commands.add(new SigninCommand(repository,printer,in));
		commands.add(new SignoutCommand(repository,printer,in));
		commands.add(new TicketBookingCommand(repository,printer,in));
		commands.add(new TicketCancellingCommand(repository,printer,in));
		commands.add(new NotificaitonCommand(repository,printer,in));
		commands.add(new TicketPrintCommand(repository,printer,in));
		commands.add(new UpdateProfileCommand(repository,printer,in));
		
		invalidcommand = new InvalidCommand(repository,printer,in);
		
	}
	
	public void executeCommand(String commandCode){
        Command command = getCommand(commandCode);
        command.execute(commandCode);
    }
	
	public Command getCommand(String commandCode){
        for(Command command : commands){
            if(command.matchCode(commandCode)){
                return command;
            }
        }
        return invalidcommand;
    }

}

package businessLogic;

import inputoutput.Printer;
import inputoutput.UserInputScanner;

public class SigninCommand extends Command {

	SigninCommand(DatalayerContract repository,Printer printer,UserInputScanner in) {
		super(repository,printer,in);
		code = "signin";
	}

	void execute(String command) {
		if(user != null) {
			printer.errorMessage("You alredy singin");
			return;
		}
		int counter = 2 ; 
		String email = in.getInput("Enter email");
		if(!repository.noUserName(email)) {
			printer.errorMessage("email dosn't register use 'signup' command to register");
			return;
		}
		while(!repository.passwordMatch(email, in.getInput("Enter the password"))) {
			printer.errorMessage("password is worng");
			counter--;
			if(counter == 0) {
				break;
			}
			
		}
		if(counter == 0) {
			printer.errorMessage("Maxmum attempt as faild !");
			return;
		}
		user = repository.getUser(email);
		printer.successMessage("Hello,"+repository.getUserName(email));
		System.out.println(" 'signout' for signout \n 'book' for booking \n 'cancel' cancelling the ticket \n 'notify' for notification \n 'singup' for register(after signout use signup command ) \n 'print' for print the tickets \n 'update' for update your profile  ");
	}

}

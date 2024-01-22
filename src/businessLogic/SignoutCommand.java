package businessLogic;

import inputoutput.*;

public class SignoutCommand extends Command {

	SignoutCommand(DatalayerContract repository,Printer printer,UserInputScanner in) {
		super(repository,printer,in);
		code = "signout";
	}

	@Override
	void execute(String command) {
		if(user == null) {
			printer.errorMessage("Please login using signin code");
			return;
		}
		
		user = null ;
		System.out.println("Thank you! Have a nice day");
	}

}

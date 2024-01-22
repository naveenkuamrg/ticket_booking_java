package businessLogic;

import inputoutput.*;

public class SignupCommand extends Command {
	
	SignupCommand(DatalayerContract repository,Printer printer,UserInputScanner in){
		super(repository,printer,in);
		code = "signup";
	}

	@Override
	void execute(String command) {
		if(user != null) {
			printer.errorMessage("User alredy signin");
			return ;
		}
//		int counter = 0 ;
		String[] details = new String[3];
		details[0] = in.getInputName("Enter the name ");
		while(details[1] == null) {
		details[1] = in.getInputEmail("Enter the email ");
//		counter++;
//		if(counter > 3) {
//			printer.errorMessage("sorry, Maximum attempts is exist");
//			return;
//		}
		}
		if(repository.noUserName(details[1])) {
			System.out.println("Email alredy register use 'signin' command to signin");
			return ;
		}
		details[2] =in.getInputPassword("Enter the passoword");
		
		repository.setUserDetails(details[1], details);
		
		printer.successMessage("Register is successful please lognin using 'signin' code");
		
	}

}

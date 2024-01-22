package businessLogic;

import inputoutput.Printer;
import inputoutput.UserInputScanner;

public class UpdateProfileCommand extends Command {

	UpdateProfileCommand(DatalayerContract repository, Printer printer, UserInputScanner in) {
		super(repository, printer, in);
		code = "update";
	}

	@Override
	void execute(String command) {
		// TODO Auto-generated method stub
		boolean isPasswordMatch = false;
		if(user == null) {
			printer.errorMessage("please lognin using lognin command");
			return;
		}
		do {
		switch(in.getInput(" Enter 1 to update password \n Enter 2 to update name")) {
		case "1":
			
			do {

				isPasswordMatch = repository.passwordMatch(user.getEmail(),in.getInput("Enter the current password"));
				if(isPasswordMatch) {
					String currentPassword = in.getInputPassword("Enter the new password");
				
					isPasswordMatch = repository.passwordMatch(user.getEmail(),currentPassword);
					if(isPasswordMatch) {
						printer.errorMessage("current password is match to old password");
						if(in.getInput("Enter the anykey to continue or enter press -1 to exit").equals("-1")) {
							   return;
							}
					}else {
						repository.setPassword(user.getEmail(),currentPassword);
						printer.successMessage("update password successfully");
						return;
					}
	
				}else {
					printer.errorMessage("worng password");
					if(in.getInput("Enter the anykey to retype password or enter press -1 to exit").equals("-1")) {
					   return;
					}
				}
			}while(!isPasswordMatch);
			break;
		case "2":
			
			do {
				isPasswordMatch = repository.passwordMatch(user.getEmail(),in.getInput("Enter the  password"));
				if(isPasswordMatch) {
					repository.setUpdateName(user.getEmail(), in.getInputName("Enter new user name"));
					printer.successMessage("update name successfully");
					return;
				}else {
					printer.errorMessage("Password dosn't match");
					if(in.getInput("Enter the anykey to retype password or enter press -1 to exit").equals("-1")) {
						return;
					}
				}
			}while(!isPasswordMatch);
			break;
			
		
		default:
			printer.errorMessage("please cheack your option");
				
		}
		}while(true);
		
	}
	

}

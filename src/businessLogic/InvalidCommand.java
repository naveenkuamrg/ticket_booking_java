package businessLogic;

import inputoutput.*;

public class InvalidCommand extends Command{

	InvalidCommand(DatalayerContract repository,Printer printer,UserInputScanner in) {
		super(repository,printer,in);
		code = "invaild";
	}

	@Override
	void execute(String command) {
		// TODO Auto-generated method stub
		printer.errorMessage("Invaild Code");
	}

}

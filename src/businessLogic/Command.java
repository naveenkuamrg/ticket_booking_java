package businessLogic;

import Model.User;
import inputoutput.*;

abstract public class Command {
	
	DatalayerContract repository ;
	Printer printer;
	UserInputScanner in;
	protected String code ;
    public static User user;
	Command(DatalayerContract repository,Printer printer,UserInputScanner in){
		this.repository = repository;
		this.printer = printer;
		this.in = in;
	}
	public boolean matchCode(String code) {
		return this.code.equals(code);
	}
	abstract void execute(String command);
	

}

package main;

import businessLogic.*;
import inputoutput.Printer;
import inputoutput.UserInputScanner;


public class Main {
	public static void main(String arg[]) {
		
//		Command.user = repository.getUser("naveen@gmail.com");
		UserInputScanner in = new UserInputScanner(Printer.getPrinterInstent());
		Commandexecutor commandexecutor = Assambler.getInstance(in);
		boolean flage = true ; 
		String command = null;
		while(true) {
		if(flage) {
		command = in.getInput("Enter 'signin' or 'signup' code ");
		flage = false;
		}else {
			command = in.getInput("Enter the code").toLowerCase();
		}
		commandexecutor.executeCommand(command);
		}
		
		
	}

}

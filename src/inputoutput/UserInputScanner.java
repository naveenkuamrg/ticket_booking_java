package inputoutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.VehicleType;
public class UserInputScanner {
    private final Scanner scanner = new Scanner(System.in);
    private final Printer printer ;
    public UserInputScanner(Printer printer){
    	this.printer = printer;
    }
    public String getInput(String info){
       
        String input;
        do {
        	if(!info.isBlank()) {
        	 System.out.println(info);
        	}
        	 input = scanner.nextLine().trim();
        if(input.isEmpty()) {
        	printer.errorMessage("blank space  are not allowed");
        }
        }while(input.isEmpty());
        
        return input;
        
    }
    
    public String getInputName(String info) {
    	String name = null;
    	String regex = "^[a-zA-Z]+$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher;
    	do {
    	name = getInput(info);
    	matcher = pattern.matcher(name);
    	if(!matcher.matches())
    	printer.errorMessage("Spical character and digits are not allowed for name ");
    	}while(!matcher.matches());
    	
    	return name;
    }
   
    
    public String getInputEmail(String info) {
 
    	String email = getInput(info);
    	if(isValidEmail(email)) {
    		return email;
    	}else {
    		printer.errorMessage("It's not valid email");
    		return null;
    	}
    }
    
    public int getInputNumber(String info){
    	int result = 0;
    	boolean flag = false;
    	do {
    	try {
            result = Integer.parseInt(getInput(info));
            flag = false;
        } catch (NumberFormatException e) {
        	 printer.errorMessage("Invalid Input");
            flag = true;
        }
    	}while(flag);
    	return result;
    }
    
    
    
    
    
    public int getNoOfPassnager(String info,HashMap<String,Integer> avalibleSeats,VehicleType type) {
    	int noAvalibleSeats = 0 ;
    	int numberOfPassanger = getInputNumber(info);
        while(!(numberOfPassanger > 0 && numberOfPassanger < 4)) {
        	if(numberOfPassanger>0) {
        	printer.errorMessage("Per booking only 3 seat can book ");
        	}else {
        	printer.errorMessage("minimum 1 seat want to book ");
        	}
        	
        	numberOfPassanger = getInputNumber(info);
        }
        
        for(Map.Entry<String, Integer> avalibleSeat : avalibleSeats.entrySet()) {
        	noAvalibleSeats += avalibleSeat.getValue();
        }
        
        if(type == VehicleType.train) {
        	if(noAvalibleSeats + 15 < numberOfPassanger) {
      		  printer.errorMessage("avalible seat is less then no Of passanger " );
      		return -1;
        	}
      	}
   
    	
    	if(noAvalibleSeats < numberOfPassanger) {
    		  printer.errorMessage("avalible seat is less then no Of passanger " );
    		return -1;
    	}
    	
    	
    		
    	
    	
    	return numberOfPassanger;
   
    }
	
    
    public String getInputPassword(String info) {

    	String password;
    	
    	
    	do {
    		password = getInput(info);
    	}while(!validatePassword(password));
    	while(!password.equals(getInput("confirm the password"))) {
    		printer.errorMessage("password dost not match Try again");
    	}
    	return password;
    }
    
    public int chooseVehicle(String info,int noOfvehicel) {
    	int index = getInputNumber(info);
    	while(!(index > 0 && index < noOfvehicel)) {
    		printer.errorMessage("please enter the vaild index");
    		index = getInputNumber(info);
    	}
    	return index;
    }
    
    private boolean isValidEmail(String email) {
        String regex = "^[a-z0-9]+@[a-z]+\\.[a-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
  
    
    private boolean validatePassword(String password) {
        
        String lowercaseRegex = "(?=.*[a-z])";
        String uppercaseRegex = "(?=.*[A-Z])";
        String digitRegex = "(?=.*\\d)";
        String specialCharRegex = "(?=.*[@$!%*?&])";
        String lengthRegex = ".{8,}";

       
        String regex = lowercaseRegex + uppercaseRegex + digitRegex + specialCharRegex + lengthRegex;

   
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
      
       
        if (!matcher.matches()) {
        	
            StringBuilder message = new StringBuilder("Password must contain: \n");
            if (!password.matches(lowercaseRegex + ".*")) {
                message.append("at least one lowercase letter \n");
            }
            if (!password.matches(uppercaseRegex + ".*")) {
                message.append("at least one uppercase letter \n ");
            }
            if (!password.matches(digitRegex + ".*")) {
                message.append("at least one digit\n");
            }
            if (!password.matches(specialCharRegex + ".*")) {
                message.append("at least one special character\n");
            }
            if (!password.matches(lengthRegex)) {
                message.append("be at least 8 characters long\n");
            }

            printer.errorMessage(message.toString());
            return false;
        }
        return true;


    }
    
    
}
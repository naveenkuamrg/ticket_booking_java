package Model;

import java.util.HashMap;

abstract public class Vehicle {
	private String number;
	protected HashMap<Character,Integer> typeOfSeatsAndItsRate = new HashMap<>() ;
	public Vehicle(String number){
		this.number = number;
	}
	public String getNumber() {
		return number;
	}
	
	public int getRateForSeattype(String string) {
		return typeOfSeatsAndItsRate.get(string.toUpperCase().charAt(0));
	}
	public abstract HashMap<String,Integer> getNoOfSeats();

}

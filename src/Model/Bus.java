package Model;

import java.util.HashMap;

public class Bus extends Vehicle{
	
	private int sleeper ;
	
	private int normalSeats;
	
	public Bus(String number,int sleeper,int normalSeats){
		super(number);
		this.sleeper = sleeper;
		this.normalSeats = normalSeats;
		
		typeOfSeatsAndItsRate.put('S',2);
		typeOfSeatsAndItsRate.put('N',1);
	}
	
	@Override
	public HashMap<String, Integer> getNoOfSeats() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("sleeper", sleeper);
		map.put("normalSeats", normalSeats);
		return map;
	}
	
}

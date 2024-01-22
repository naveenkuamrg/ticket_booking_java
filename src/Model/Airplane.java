package Model;

import java.util.HashMap;

public class Airplane extends Vehicle {
	
	private int economyclass;
	private int businessclass;
	private int firstclass;
	
	public Airplane(String number,int economyclass,int businessclass,int firstclass) {
		super(number);
		this.firstclass = firstclass;
		this.businessclass = businessclass;
		this.economyclass = economyclass;
		
		typeOfSeatsAndItsRate.put('F',3);
		typeOfSeatsAndItsRate.put('B',2);
		typeOfSeatsAndItsRate.put('E',1);
	}
	
	public HashMap<String, Integer> getNoOfSeats() {
		HashMap<String,Integer> map = new HashMap<>();
		map.put("businessclass", businessclass);
		map.put("economyclass", economyclass);
		map.put("firstclass", firstclass);
		return map;
	}

}

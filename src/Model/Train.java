package Model;

import java.util.HashMap;

public class Train extends Vehicle {
	
	int fau;
	int fam;
	int fal;
	int fcu;
	int fcm;
	int fcl;
	int scu;
	int scm;
	int scl;
	
	public Train(String number,int ac,int firstClass,int sleeperClass ) {
		super(number);
		this.fau = ac/3;
		this.fam = ac/3;
		this.fal = ac/3;
		this.fcu = firstClass/3;
		this.fcm = firstClass/3;
		this.fcl = firstClass/3;
		this.scu = sleeperClass/3;
		this.scm = sleeperClass/3;
		this.scl = sleeperClass/3;
		
		
		typeOfSeatsAndItsRate.put('A',3);
		typeOfSeatsAndItsRate.put('F',2);
		typeOfSeatsAndItsRate.put('S',1);
		
	}	
	
	@Override
	public HashMap<String, Integer> getNoOfSeats() {

		HashMap<String,Integer> map = new HashMap<>();
		map.put("fau", fau);
		map.put("fam", fam);
		map.put("fal", fal);
		map.put("fcm", fcm);
		map.put("fcu", fcu);
		map.put("fcl", scu);
		map.put("scm", scm);
		map.put("scu", scu);
		map.put("scl", scl);
		
		return map;
		
	}
}

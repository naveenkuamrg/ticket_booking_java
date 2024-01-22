package respositry;

import java.util.*;
import java.util.Map.Entry;

import Model.*;
import Model.Vehicle;
import Model.VehicleType;

public class VehicleManager {
	private HashMap<String[],HashMap<Vehicle,String>> vehicleRoute = new HashMap<>();
	private HashMap<String[],Integer>  routePrice = new HashMap<>();
	
	public List<String[]> getAllRouteByVehicleType(VehicleType type){
		List<String[]> routes = new ArrayList<String[]>();
		for(Map.Entry<String[],HashMap<Vehicle,String>> route : vehicleRoute.entrySet()) {
			for(Entry<Vehicle, String> tempVehicle : route.getValue().entrySet()){
				switch(type){
				case bus:
					if(tempVehicle.getKey() instanceof Bus&& !routes.contains(route.getKey())) 
						routes.add(route.getKey());
					break;
				case airplane:
					if(tempVehicle.getKey() instanceof Airplane&& !routes.contains(route.getKey())) 
						routes.add(route.getKey());
					break;
				case train:
					if(tempVehicle.getKey() instanceof Train && !routes.contains(route.getKey())) 
						routes.add(route.getKey());
					break;
				}
			}
		}
		
		return routes;
	}
	
	public String[] getRoute(HashMap<Vehicle,String> vehicle) {
		
		Entry<Vehicle, String> vehicleEntry = vehicle.entrySet().iterator().next();
		
		for(Map.Entry<String[],HashMap<Vehicle,String>> vehicleRoute : vehicleRoute.entrySet()) {
			if(vehicleRoute.getValue().containsValue(vehicleEntry.getValue()) &&vehicleRoute.getValue().containsKey(vehicleEntry.getKey())) {
				return vehicleRoute.getKey();
			}
		}
		return null;
	}
	
	public HashMap<Vehicle,String> getVehicle(String source,String destination){
		HashMap<Vehicle,String> vehicles = new HashMap<Vehicle,String>();
		for(Map.Entry<String[],HashMap<Vehicle,String>> route : vehicleRoute.entrySet()) {
			for(int i = 0; i < route.getKey().length ;i++) {
				if(route.getKey()[i].equalsIgnoreCase(source)) {
					for(int j = i ; j <route.getKey().length;j++) {
						if(route.getKey()[j].equalsIgnoreCase(destination)){
							for(Entry<Vehicle, String> tempVehicle : route.getValue().entrySet()){
							vehicles.put(tempVehicle.getKey(),tempVehicle.getValue());
							}
						}
					}
				}
				
			}
		}
		
		return vehicles;
	}
	
	public int getPrice(String source,String destination) {
		for(Map.Entry<String[],HashMap<Vehicle,String>> route : vehicleRoute.entrySet()) {
			for(int i = 0; i < route.getKey().length ;i++) {
				if(route.getKey()[i].equalsIgnoreCase(source)) {
					for(int j = i ; j <route.getKey().length;j++) {
						if(route.getKey()[j].equalsIgnoreCase(destination)) {
							int price = routePrice.get(route.getKey());
							price = price/route.getKey().length;
							price *= (j+1)-i;
							return price;
						}
					}
				}
				
			}
		}
		return  -1;
		
	}
	
	public void setVehicelRouteAndPrice(Vehicle vehicle,String[] route,Integer rate,String date) {
		if(vehicleRoute.get(route) == null) {
			vehicleRoute.put(route, new HashMap<Vehicle,String>());
			routePrice.put(route, rate);
		}
		vehicleRoute.get(route).put(vehicle, date);
	}


	public Vehicle getVehicleByNumberAndTiming(String source, String destination, String vehicleNo,
			String time) {
		HashMap<Vehicle,String> vehicles =  getVehicle(source,destination);
		for(HashMap.Entry<Vehicle,String> vehicle : vehicles.entrySet()) {
			if(vehicle.getKey().getNumber().equalsIgnoreCase(vehicleNo) && vehicle.getValue().equalsIgnoreCase(time)) {
				return vehicle.getKey();
			}
		}

		return null;
	}


	
	
}

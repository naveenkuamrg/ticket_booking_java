package main;

import Model.Airplane;
import Model.Bus;
import Model.Train;
import businessLogic.Commandexecutor;
import businessLogic.DatalayerContract;
import inputoutput.Printer;
import inputoutput.UserInputScanner;
import respositry.Repository;
import respositry.VehicleManager;

public class Assambler {
	private static Commandexecutor commandexecutor = null;
	static Commandexecutor getInstance(UserInputScanner in) {
		if(commandexecutor == null) {
		VehicleManager vehicleManager = new VehicleManager();
		String[] route = {"cheyyar","kanchipuram","chennai"};
		String[] route1 = {"chennai","kanchipuram","cheyyar"};
		String[] route7 = {"kanchipuram","cheyyar"};
		Bus bus1 = new Bus("TN001",14,28);
		Bus bus2 = new Bus("TN002",0,4);
		Bus bus3 = new Bus("TN003",0,4);
		
		vehicleManager.setVehicelRouteAndPrice(bus1,route ,  30,"cheyyar - 2.00pm -> kanchipuram - 3.00pm -> chennai - 5.00pm");
		vehicleManager.setVehicelRouteAndPrice(bus1,route1 ,30,"chennai - 6.00pm -> kanchipuram - 8.00pm -> chennai - 9.00pm");
		vehicleManager.setVehicelRouteAndPrice(bus2, route, 30, "cheyyar - 2.00pm -> kanchipuram - 3.00pm -> chennai - 5.00pm");
		vehicleManager.setVehicelRouteAndPrice(bus2, route1, 30, "chennai - 6.00pm -> kanchipuram - 8.00pm -> chennai - 9.00pm");
		vehicleManager.setVehicelRouteAndPrice(bus3, route7, 15, "kanchipuram - 7.00pm -> chennai - 8.00pm");

		
		String[] route3 = {"chennai","arakkonam","jolarpettai","bengaluru"};
		String[] route4 = {"bengaluru","jolarpettai","arakkonam","chennai"};
		
		Train train1 = new Train("66632",15,15,15);
		Train train2 = new Train("77732",6,6,6);
		
		vehicleManager.setVehicelRouteAndPrice(train1, route3, 120, "chennai - 12.00 -> arakkonam - 12.45  -> bengaluru - 14.00");
		vehicleManager.setVehicelRouteAndPrice(train2, route3, 120, "bengaluru - 15.00 , arakkonam - 16.15 -> chennai - 17.00 ");
		vehicleManager.setVehicelRouteAndPrice(train1, route4, 120, "chennai - 13.00 -> arakkonam - 13.45 -> 14.30 -> bengaluru - 15.00");
		vehicleManager.setVehicelRouteAndPrice(train2, route4, 120, "bengaluru - 15.00 -> , arakkonam - 16.15 -> chennai - 17.00");
		
		
		Airplane airplane1 = new Airplane("AI 538", 28,20,12);
		Airplane airplane2 = new Airplane("6E 6708", 28,20,12);
		String[] route5 = {"chennai","delhi"};
		String[] route6 = {"delhi","chennai"};
		
		vehicleManager.setVehicelRouteAndPrice(airplane2, route5, 1645, "chennai - 7.00am -> delhi - 7.45am");
		vehicleManager.setVehicelRouteAndPrice(airplane2, route6, 1645, "delhi - 8.30am -> chennai - 9.15am");
		vehicleManager.setVehicelRouteAndPrice(airplane1, route5, 1345, "chennai - 8.00am -> delhi - 8.45am");
		vehicleManager.setVehicelRouteAndPrice(airplane1, route6, 1345, "delhi - 9.30am -> chennai - 10.15am");
		
		
		
		DatalayerContract repository = new Repository(vehicleManager);
        commandexecutor = new Commandexecutor(repository,Printer.getPrinterInstent(),in);
		String[]  sample = {"naveen","naveen@gmail.com","@wrlCEtd9szmc"};
		repository.setUserDetails("naveen@gmail.com",sample );
		
		
		}
		
		return commandexecutor;
	}

	
}

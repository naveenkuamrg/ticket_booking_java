package businessLogic;

import java.time.format.DateTimeFormatter;
import java.util.List;

import Model.Notification;
import inputoutput.*;

public class NotificaitonCommand extends Command {

	NotificaitonCommand(DatalayerContract repository, Printer printer,UserInputScanner in) {
		super(repository, printer,in);
		code = "notify";
	}

	@Override
	void execute(String command) {
		if(user == null) {
			printer.errorMessage("Please login using signin code");
			return;
		}
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
		List<Notification> notifications = user.getNotifications();
		notifications.sort((t1,t2) -> t2.getDate().compareTo(t1.getDate()));
		System.out.println("------------notification-----------");
		for(Notification notification : notifications) {
			System.out.print(notification.getDate().format(myFormatObj)+"  ");
			printer.notificationMessage(notification.getNotification());
		}
		
	}

}

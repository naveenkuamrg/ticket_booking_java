package Model;

import java.time.LocalDateTime;

public class Notification {
	private String notification;
	private LocalDateTime actionDate;
	
    public Notification(String notification) {

        this.notification = notification;
        this.actionDate =  LocalDateTime.now();
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getDate() {
        return actionDate;
    }
}

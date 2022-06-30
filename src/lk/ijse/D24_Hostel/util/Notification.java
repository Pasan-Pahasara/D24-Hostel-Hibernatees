package lk.ijse.D24_Hostel.util;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Notification {

    public static void playNotification(AnimationType animationType, String title, NotificationType notificationType, Duration duration){
        TrayNotification notify = new TrayNotification();
        notify.setAnimationType(animationType);
        notify.setTitle(title);
        notify.setNotificationType(notificationType);
    }
    public static void playNotificationMessage(AnimationType animationType, String title,String message, NotificationType notificationType, Duration duration){
        TrayNotification notify = new TrayNotification();
        notify.setAnimationType(animationType);
        notify.setTitle(title);
        notify.setMessage(message);
        notify.setNotificationType(notificationType);
    }
}

package lk.ijse.D24_Hostel.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class Time {
    public static void timeZone(Label lblTime) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
            String formattedDate = dateFormat.format(new Date());
            lblTime.setText(formattedDate);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}

package lk.ijse.D24_Hostel.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.D24_Hostel.util.UILoader;
import lk.ijse.D24_Hostel.util.UIMouseClickAnimation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class DashBoardFormController {
    public Label lblTime;
    public AnchorPane rootContext;
    public ImageView imgReserve;
    public ImageView imgRoom;
    public ImageView imgFindRemainKeyMoney;
    public Label lblMenu;
    public Label lblDescription;
    public Label lblDate;


    public void initialize() {
        loadTimeAndDate();
    }

    /**
     * load Time and date
     */
    private void loadTimeAndDate() {
        //Set Time and Date
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            String formattedTime = timeFormat.format(new Date());
            String formattedDate = dateFormat.format(new Date());
            lblTime.setText(formattedTime);
            lblDate.setText(formattedDate);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * Navigate To Reserve Room, Manage Room Details and Find Remain Key Money Details
     * */
    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgReserve":
                    UILoader.SetUi("ReserveForm",rootContext);
                    break;
                case "imgRoom":
                    UILoader.SetUi("ManageRoomForm",rootContext);
                    break;
                case"imgFindRemainKeyMoney":
                    UILoader.SetUi("FindRemainKeyMoneyForm",rootContext);
                    break;
            }
        }
    }

    /**
     * Play Mouse Enter Animation
     */
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgReserve":
                    lblMenu.setText("Reserve Rooms");
                    lblDescription.setText("Reserving Rooms in this System.");
                    break;
                case "imgRoom":
                    lblMenu.setText("Manage Rooms");
                    lblDescription.setText("Managing Rooms in this System.");
                    break;
                case "imgFindRemainKeyMoney":
                    lblMenu.setText("Find Remain Key Money");
                    lblDescription.setText("Find Remain Key Money in this System.");
                    break;
            }
            UIMouseClickAnimation.mouseEnterAnimation(icon);
        }
    }

    /**
     * Play Mouse Exit Animation
     */
    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        UIMouseClickAnimation.mouseExitAnimation(mouseEvent, lblMenu, lblDescription);
    }

    /**
     * Navigate To Login
     * */
    public void logOutOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("LoginForm",rootContext);
    }

    /**
     * Mouse Click Animation On Log Out
     */
    public void mouseEnterAnimation(MouseEvent mouseEvent) {
        UIMouseClickAnimation.mouseClickAnimationOn(mouseEvent);
    }

    /**
     * Mouse Click Animation off Log Out
     */
    public void mouseExitAnimation(MouseEvent mouseEvent) {
        UIMouseClickAnimation.mouseClickAnimationClose(mouseEvent);
    }

    /**
     * Navigate To User Form
     * */
    public void manageUsersOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ManageUserLoginForm",rootContext);
    }
}

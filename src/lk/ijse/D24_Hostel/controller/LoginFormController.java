package lk.ijse.D24_Hostel.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.LoginBO;
import lk.ijse.D24_Hostel.dto.LoginDTO;
import lk.ijse.D24_Hostel.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class LoginFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lblHide;
    public AnchorPane rootLog;


    /**
     * Login
     */
    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        login();
    }

    private void login() throws IOException, SQLException, ClassNotFoundException {
        ArrayList<LoginDTO> allUsers = loginBO.getAllUsers();
        for (LoginDTO temp : allUsers) {
            if (txtUserName.getText().equals(temp.getName())) {
                if (txtPassword.getText().equals(temp.getPassword())) {
                    UILoader.SetUi("DashBoardForm", rootLog);
                } else {
//                    passwordWarningMessage.setVisible(true);
                }
            } else {
//                lblUserNameWarningMessage.setVisible(true);
            }
        }
    }

    /**
     * Show Password
     */
    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("lk/ijse/D24_Hostel/view/assets/images/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(24);
        view.setFitWidth(24);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    /**
     * Hide Password
     */
    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("lk/ijse/D24_Hostel/view/assets/images/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(24);
        view.setFitWidth(24);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }

    /**
     * UserName Password Key Released
     */
    public void userNameKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtPassword.requestFocus();
        }
    }

    /**
     * Password Key Released
     */
    public void passwordKeyReleased(KeyEvent keyEvent) throws IOException, SQLException, ClassNotFoundException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) login();
    }
}

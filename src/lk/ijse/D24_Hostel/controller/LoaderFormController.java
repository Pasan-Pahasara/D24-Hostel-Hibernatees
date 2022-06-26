package lk.ijse.D24_Hostel.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class LoaderFormController implements Initializable {
    public AnchorPane rootContext;
    public ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ShowSplashScreen().start();
    }

    class ShowSplashScreen extends Thread {
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    double x = i * (0.1);
                    progressBar.setProgress(x);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/lk/ijse/D24_Hostel/view/LoginForm.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(LoaderFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("D24 Hostel");
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    try {
                        stage.getIcons().add(new Image("lk/ijse/D24_Hostel/view/assets/images/loader.png"));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    stage.show();
                    rootContext.getScene().getWindow().hide();
                });
            } catch (Exception ex) {
                Logger.getLogger(LoaderFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

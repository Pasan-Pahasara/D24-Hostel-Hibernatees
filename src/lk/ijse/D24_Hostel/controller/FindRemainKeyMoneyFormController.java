package lk.ijse.D24_Hostel.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.ReserveBO;
import lk.ijse.D24_Hostel.dto.ReserveDTO;
import lk.ijse.D24_Hostel.util.Time;
import lk.ijse.D24_Hostel.util.UILoader;
import lk.ijse.D24_Hostel.view.assets.tm.ReserveTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class FindRemainKeyMoneyFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVE);
    public AnchorPane root;
    public TableView<ReserveTM> tblFindRemainKeyMoney;
    public Label lblTime;
    public JFXTextField txtSearch;

    public void initialize() {
        tblFindRemainKeyMoney.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("res_id"));
        tblFindRemainKeyMoney.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblFindRemainKeyMoney.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblFindRemainKeyMoney.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblFindRemainKeyMoney.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("room_fee"));
        tblFindRemainKeyMoney.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("advance"));
        tblFindRemainKeyMoney.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            loadAllRemainKeyMoneyDetails();
            loadTime();
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Get All Remain KeyMoney Details
     */
    private void loadAllRemainKeyMoneyDetails() {
        tblFindRemainKeyMoney.getItems().clear();
        try {
            ArrayList<ReserveDTO> allStudent = reserveBO.getAllReserve();
            for (ReserveDTO reserveDTO : allStudent) {
                tblFindRemainKeyMoney.getItems().add(new ReserveTM(reserveDTO.getRes_id(), reserveDTO.getDate(), reserveDTO.getStudent_id(), reserveDTO.getRoom_type_id(), reserveDTO.getRoom_fee(), reserveDTO.getAdvance(), reserveDTO.getStatus()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Reserve Details").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate To DashBoard
     */
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("DashBoardForm", root);
    }

    /**
     * Search Reserve Details
     */
    public void searchMouseClickedOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void search() throws SQLException, ClassNotFoundException {
        tblFindRemainKeyMoney.getItems().clear();
        String id = txtSearch.getText();

        try {
            if (id.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "There is empty search id " + id).show();
                loadAllRemainKeyMoneyDetails();
            } else if (existReserve(txtSearch.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such reserve details associated with the id " + id).show();
                loadAllRemainKeyMoneyDetails();
            }
            /*Get all reserve details*/
            ArrayList<ReserveDTO> allReserves = reserveBO.searchReserve(id);
            tblFindRemainKeyMoney.getItems().clear();
            for (ReserveDTO reserveDTO : allReserves) {
                tblFindRemainKeyMoney.getItems().add(new ReserveTM(reserveDTO.getRes_id(), reserveDTO.getDate(), reserveDTO.getStudent_id(), reserveDTO.getRoom_type_id(), reserveDTO.getRoom_fee(), reserveDTO.getAdvance(), reserveDTO.getStatus()));
            }
        } catch (NullPointerException ignore) {
        }
    }

    private boolean existReserve(String id) throws SQLException, ClassNotFoundException {
        return reserveBO.checkReserveIsAvailable(id);
    }

    /**
     * load Time
     */
    private void loadTime() {
        //Set Time
        Time.timeZone(lblTime);
    }
}

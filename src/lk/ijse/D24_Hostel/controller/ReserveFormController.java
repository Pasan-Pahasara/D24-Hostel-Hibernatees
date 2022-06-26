package lk.ijse.D24_Hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.ReserveBO;
import lk.ijse.D24_Hostel.dto.ReserveDTO;
import lk.ijse.D24_Hostel.dto.RoomDTO;
import lk.ijse.D24_Hostel.dto.StudentDTO;
import lk.ijse.D24_Hostel.util.Time;
import lk.ijse.D24_Hostel.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class ReserveFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVE);
    public AnchorPane root;
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtStudentAddress;
    public JFXTextField txtStudentContact;
    public JFXDatePicker studentDobDatePicker;
    public JFXTextField txtGender;
    public JFXComboBox cmbRoomID;
    public JFXTextField txtRoomType;
    public JFXTextField txtRoomFee;
    public JFXTextField txtRoomQty;
    public JFXTextField txtAdvance;
    public Label lblRegisterID;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnRegister;
    public JFXButton btnAddNewStudent;
    public Label lblRoomFee;
    public Label lblStatus;
    private String RegID;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        RegID = generateNewRegisterId();//Generate New Register ID
        lblRegisterID.setText(RegID);
        lblDate.setText(LocalDate.now().toString());
        btnRegister.setDisable(true);
        loadAllStudentIds();
        loadAllRoomIds();
        loadTime();
        txtStudentId.requestFocus();

        /**
         * Search Room Details
         */
        cmbRoomID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newRoomId) -> {
            btnRegister.setDisable(newRoomId == null);


            if (newRoomId != null) {
                try {
                    if (!exitRooms(newRoomId + "")) {

                    }
                    RoomDTO room = reserveBO.searchRoom(newRoomId + "");
                    txtRoomType.setText(room.getType());
                    txtRoomFee.setText(String.valueOf(room.getKey_money()));
                    txtRoomQty.setText(room.getQty());
                    lblRoomFee.setText(txtRoomFee.getText());

                } catch (SQLException | ClassNotFoundException throwable) {
                    throwable.printStackTrace();
                }
            } else {
                txtStudentName.clear();
                txtRoomFee.clear();
            }
        });
//        txtAdvance.textProperty().addListener((observable, oldValue, newValue) -> {
//            try {
//                Double roomFee = Double.parseDouble(txtRoomFee.getText());
//                Double advance = Double.parseDouble(txtAdvance.getText());
//                String status = String.valueOf(roomFee - advance);
//                lblStatus.setText(status);
//                lblStatus.getProperties().clear();
//            } catch (NumberFormatException ignore) {
//            }
//        });
//        lblStatus.getProperties().clear();
    }

    /**
     * Get All Students
     */
    private void loadAllStudentIds() {
        try {
            ArrayList<StudentDTO> all = reserveBO.getAllStudents();
            for (StudentDTO studentDTO : all) {
                txtStudentId.getText().equals(studentDTO.getStudent_id());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Students ID").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get All Rooms
     */
    private void loadAllRoomIds() {
        try {
            ArrayList<RoomDTO> all = reserveBO.getAllRooms();
            for (RoomDTO roomDTO : all) {
                cmbRoomID.getItems().add(roomDTO.getRoom_type_id());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Rooms ID").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Student Is Available
     */
    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return reserveBO.checkStudentIsAvailable(id);
    }

    /**
     * Check Room Is Available
     */
    private boolean exitRooms(String code) throws SQLException, ClassNotFoundException {
        return reserveBO.checkRoomIsAvailable(code);
    }

    /**
     * Generate New Order ID
     */
    public String generateNewRegisterId() {
        try {
            return reserveBO.generateNewRegisterID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new Register ID").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "RID-001";
    }

    /**
     * Register On Action
     */
    public void btnRegister_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        disableFalse();
        Double roomFee = Double.parseDouble(txtRoomFee.getText());
        Double advance = Double.parseDouble(txtAdvance.getText());
        String status = String.valueOf(roomFee - advance);
        lblStatus.setText(status);

//        try {
//            int qty = 1;
//            int roomQty = Integer.parseInt(txtRoomQty.getText());
//            String newRoomQty = String.valueOf(roomQty - qty);
//            roomBO.updateRoom(new RoomDTO(newRoomQty));
//        } catch (TransientObjectException ignored) {
//        }
        boolean b = saveReserve(RegID, LocalDate.now(), txtStudentId.getText(), (String) cmbRoomID.getValue(), roomFee, advance, status);
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Register has been successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Register has not been successfully").show();
        }
        RegID = generateNewRegisterId(); //Generate id
        lblRegisterID.setText(RegID);
        clear();
    }

    /**
     * Save Reserve
     */
    public boolean saveReserve(String resId, LocalDate orderDate, String stId, String roomId, Double roomFee, Double advance, String status) {
        /*Transaction*/
        try {
            return reserveBO.registerDetails(new ReserveDTO(resId, orderDate, stId, roomId, roomFee, advance, status));
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    /**
     * Navigate To DashBoard
     */
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("DashBoardForm", root);
    }

    /**
     * Navigate To Student Form
     */
    public void btnAddNewStudentOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ManageStudentForm", root);
    }

    /**
     * load Time
     */
    private void loadTime() {
        //Set Time
        Time.timeZone(lblTime);
    }

    /**
     * Clear Text
     */
    private void clear() {
        cmbRoomID.getSelectionModel().clearSelection();
        txtStudentId.clear();
        txtRoomType.clear();
        txtRoomFee.clear();
        txtStudentName.clear();
    }

    /**
     * Disable False
     */
    private void disableFalse() {
        txtStudentId.setDisable(false);
        cmbRoomID.setDisable(false);
        txtRoomType.setDisable(false);
        txtRoomFee.setDisable(false);
        txtStudentName.setDisable(false);
    }

    /**
     * Search Student Details
     */
    public void searchKeyReleasedOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!exitStudent(txtStudentId.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such Student associated with the ID " + txtStudentId.getText() + "").show();
            } else {
                StudentDTO studentDTO = reserveBO.searchStudent(txtStudentId.getText());
                txtStudentName.setText(studentDTO.getName());
                txtStudentAddress.setText(studentDTO.getAddress());
                txtStudentContact.setText(studentDTO.getContact());
                studentDobDatePicker.setValue(studentDTO.getDob());
                txtGender.setText(studentDTO.getGender());
            }
        }
    }

    public void advanceOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            try {
            Double roomFee = Double.parseDouble(txtRoomFee.getText());
            Double advance = Double.parseDouble(txtAdvance.getText());
            String status = String.valueOf(roomFee - advance);
            lblStatus.setText(status);
        } catch (NumberFormatException ignore) {
                new Alert(Alert.AlertType.ERROR, "Invalid Advance, Please add your advance and try again!" +" "+ "Rs: 0.00").show();
                lblStatus.getProperties().clear();
            }
        }
    }
}

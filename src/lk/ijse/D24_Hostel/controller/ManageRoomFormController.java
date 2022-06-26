package lk.ijse.D24_Hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.RoomBO;
import lk.ijse.D24_Hostel.dto.RoomDTO;
import lk.ijse.D24_Hostel.util.Time;
import lk.ijse.D24_Hostel.util.UILoader;
import lk.ijse.D24_Hostel.view.assets.tm.RoomTM;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class ManageRoomFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    public AnchorPane root;
    public JFXTextField txtRoomTypeId;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomsQty;
    public JFXButton btnAddNewItem;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<RoomTM> tblRooms;
    public JFXTextField txtSearch;
    public Label lblTime;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        tblRooms.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblRooms.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRooms.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblRooms.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        btnDelete.setDisable(true);
        tblRooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {

                txtRoomTypeId.setText(newValue.getRoom_type_id());
                txtRoomType.setText(newValue.getType());
                txtKeyMoney.setText(String.valueOf(new Double(newValue.getKey_money())));
                txtRoomsQty.setText(newValue.getQty());

                txtRoomTypeId.setDisable(true);
                txtRoomType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtRoomsQty.setDisable(false);
            }
        });

        txtRoomsQty.setOnAction(event -> btnSave.fire());
        try {
            loadAllRooms();
            loadTime();
        }catch (NullPointerException e){

        }
    }

    /**
     * Get All Rooms
     */
    private void loadAllRooms() {
        tblRooms.getItems().clear();
        try {
            List<RoomDTO> allRooms = roomBO.getAllRooms();
            for (RoomDTO roomDTO : allRooms) {
                tblRooms.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
     * Add New Room
     */
    public void btnAddNewOnAction(ActionEvent actionEvent) {
        disableFalse();
        clear();
        txtRoomTypeId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRooms.getSelectionModel().clearSelection();
    }


    /**
     * Save Room
     */
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtRoomTypeId.getText();
        String type = txtRoomType.getText();
        String qty = txtRoomsQty.getText();

        /**
         * Check Validation
         */
        if (!id.matches("^RM-[0-9]{3,4}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Room type ID").show();
            txtRoomTypeId.requestFocus();
            return;
        } else if (!type.matches("^([A-z]{1,9}|[A-z]{1,9}[ /|-]?[A-z]{1,9}[ /|-]?[A-z]{1,9})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Room Type").show();
            txtRoomType.requestFocus();
            return;
        } else if (!txtKeyMoney.getText().matches("^([0-9]{2,6}.[0-9]{1,2})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Key Money").show();
            txtKeyMoney.requestFocus();
            return;
        } else if (!qty.matches("^[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Rooms Qty").show();
            txtRoomsQty.requestFocus();
            return;
        }
        Double fee = new Double(txtKeyMoney.getText());

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existRoom(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                roomBO.saveRoom(new RoomDTO(id, type, fee, qty));
                tblRooms.getItems().add(new RoomTM(id, type, fee, qty));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (PersistenceException ignored){
            }
    } else {
            try {

                if (!existRoom(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + id).show();
                }
                /**
                 * Update Room
                 */
                roomBO.updateRoom(new RoomDTO(id, type, fee, qty));
                RoomTM selectedRooms = tblRooms.getSelectionModel().getSelectedItem();
                selectedRooms.setType(type);
                selectedRooms.setKey_money(fee);
                selectedRooms.setQty(qty);
                tblRooms.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        btnAddNewItem.fire();
    }

    /**
     * Delete Room
     */
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String code = tblRooms.getSelectionModel().getSelectedItem().getRoom_type_id();
        try {
            if (!existRoom(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + code).show();
            }
            roomBO.deleteRoom(code);
            tblRooms.getItems().remove(tblRooms.getSelectionModel().getSelectedItem());
            tblRooms.getSelectionModel().clearSelection();
            clear();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the room " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exist Room Details
     */
    private boolean existRoom(String code) throws SQLException, ClassNotFoundException {
        return roomBO.checkRoomIsAvailable(code);
    }

    /**
     * Search Room Details
     */
    public void searchMouseClickedOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void search() throws SQLException, ClassNotFoundException {
        tblRooms.getItems().clear();
        String id = txtSearch.getText();

        try {
            if(id.trim().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "There is empty search id " + id).show();
                loadAllRooms();
            }else if (!existRoom(txtSearch.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + id).show();
                loadAllRooms();
            }
            /*Get all room details*/
            ArrayList<RoomDTO> allDetails = this.roomBO.searchRoomByRoomID(id);
            for (RoomDTO roomDTO : allDetails) {
                tblRooms.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
            }
            clear();
            disableTrue();
            txtSearch.requestFocus();
        } catch (NullPointerException ignore) {
        }
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
    private void clear(){
        txtRoomTypeId.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtRoomsQty.clear();
    }

    /**
     * Disable True
     */
    private void disableTrue(){
        txtRoomTypeId.setDisable(true);
        txtRoomType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtRoomsQty.setDisable(true);
    }

    /**
     * Disable False
     */
    private void disableFalse(){
        txtRoomTypeId.setDisable(false);
        txtRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtRoomsQty.setDisable(false);
    }

    /**
     * Room Type ID Key Released
     */
    public void roomTypeIdKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtRoomType.requestFocus();
        }
    }

    /**
     * Room Type Key Released
     */
    public void roomTypeKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtKeyMoney.requestFocus();
        }
    }

    /**
     * Room Key Money Key Released
     */
    public void roomKeyMoneyKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtRoomsQty.requestFocus();
        }
    }
}

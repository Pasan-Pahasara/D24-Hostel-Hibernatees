package lk.ijse.D24_Hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.LoginBO;
import lk.ijse.D24_Hostel.dto.LoginDTO;
import lk.ijse.D24_Hostel.util.Time;
import lk.ijse.D24_Hostel.util.UILoader;
import lk.ijse.D24_Hostel.view.assets.tm.LoginTM;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class ManageUserLoginFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);
    public AnchorPane root;
    public JFXButton btnAddNewUser;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<LoginTM> tblUsers;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtUserAddress;
    public JFXTextField txtUserContact;
    public JFXTextField txtUserPassword;
    public JFXComboBox cmbUserGenderID;
    public Label lblTime;
    public JFXTextField txtSearch;


    /**
     * Initializes the controller class.
     */
    public void initialize() {
        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("password"));
        tblUsers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        txtUserId.requestFocus();
        btnDelete.setDisable(true);
        cmbUserGenderID.getItems().addAll("Male", "Female", "Other");
        tblUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtUserId.setText(newValue.getUser_id());
                txtUserName.setText(newValue.getName());
                txtUserAddress.setText(newValue.getAddress());
                txtUserContact.setText(newValue.getContact());
                txtUserPassword.setText(newValue.getPassword());
                cmbUserGenderID.setValue(newValue.getGender());

                txtUserId.setDisable(true);
                txtUserName.setDisable(false);
                txtUserAddress.setDisable(false);
                txtUserContact.setDisable(false);
                txtUserPassword.setDisable(false);
                cmbUserGenderID.setDisable(false);
            }
        });
        loadAllUsers();
        loadTime();
    }

    /**
     * Get All Users
     */
    private void loadAllUsers() {
        tblUsers.getItems().clear();
        try {
            ArrayList<LoginDTO> allUsers = loginBO.getAllUsers();
            for (LoginDTO loginDTO : allUsers) {
                tblUsers.getItems().add(new LoginTM(loginDTO.getUser_id(), loginDTO.getName(), loginDTO.getAddress(), loginDTO.getContact(), loginDTO.getPassword(), loginDTO.getGender()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Add New User
     */
    public void btnAddNewOnAction(ActionEvent actionEvent) {
        disableFalse();
        clear();
        txtUserId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblUsers.getSelectionModel().clearSelection();
    }

    /**
     * Save User
     */
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String address = txtUserAddress.getText();
        String contact = txtUserContact.getText();
        String password = txtUserPassword.getText();
        String gender = (String) cmbUserGenderID.getValue();

        /**
         * Check Validation
         */
        if (!id.matches("^(UI-[0-9]{3,4})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid user type ID").show();
            txtUserId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{4,40})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid user name").show();
            txtUserName.requestFocus();
            return;
        } else if (!address.matches("^[A-z0-9/, ]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtUserAddress.requestFocus();
            return;
        } else if (!contact.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact no").show();
            txtUserContact.requestFocus();
            return;
        } else if (!password.matches("^[A-z0-9/, ]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Password should be at least 3 characters long").show();
            txtUserPassword.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existUser(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                loginBO.saveUser(new LoginDTO(id, name, address, contact, password, gender));
                tblUsers.getItems().add(new LoginTM(id, name, address, contact, password, gender));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the user " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (PersistenceException ignored) {
            }
        } else {
            /**
             * Update Customer
             */
            try {
                if (!existUser(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such user associated with the id " + id).show();
                }
                loginBO.updateUser(new LoginDTO(id, name, address, contact, password, gender));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the user " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            LoginTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();
            selectedUser.setName(name);
            selectedUser.setAddress(address);
            selectedUser.setContact(contact);
            selectedUser.setPassword(password);
            selectedUser.setGender(gender);
            tblUsers.refresh();
        }
        btnAddNewUser.fire();
    }

    /**
     * Delete User
     */
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String id = tblUsers.getSelectionModel().getSelectedItem().getUser_id();
        try {
            if (!existUser(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such user associated with the id " + id).show();
            }
            loginBO.deleteUser(id);
            tblUsers.getItems().remove(tblUsers.getSelectionModel().getSelectedItem());
            tblUsers.getSelectionModel().clearSelection();
            clear();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the user " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exist User Details
     */
    private boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return loginBO.checkUserIsAvailable(id);
    }

    /**
     * Navigate To DashBoard
     */
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("DashBoardForm", root);
    }

    /**
     * Search User Details
     */
    public void searchMouseClickedOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    private void search() throws SQLException, ClassNotFoundException {
        tblUsers.getItems().clear();
        String id = txtSearch.getText();

        try {
            if (id.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "There is empty search id " + id).show();
                loadAllUsers();
            } else if (!existUser(txtSearch.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such user associated with the id " + id).show();
                loadAllUsers();
            }
            /*Get all user details*/
            ArrayList<LoginDTO> allDetails = loginBO.searchUserByUserID(id);
            for (LoginDTO userDetails : allDetails) {
                tblUsers.getItems().add(new LoginTM(userDetails.getUser_id(), userDetails.getName(), userDetails.getAddress(), userDetails.getContact(), userDetails.getPassword(), userDetails.getGender()));
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
    private void clear() {
        txtUserId.clear();
        txtUserName.clear();
        txtUserAddress.clear();
        txtUserContact.clear();
        txtUserPassword.clear();
        cmbUserGenderID.getSelectionModel().clearSelection();
    }

    /**
     * Disable True
     */
    private void disableTrue() {
        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtUserAddress.setDisable(true);
        txtUserContact.setDisable(true);
        txtUserPassword.setDisable(true);
        cmbUserGenderID.setDisable(true);
    }

    /**
     * Disable False
     */
    private void disableFalse() {
        txtUserId.setDisable(false);
        txtUserName.setDisable(false);
        txtUserAddress.setDisable(false);
        txtUserContact.setDisable(false);
        txtUserPassword.setDisable(false);
        cmbUserGenderID.setDisable(false);
    }
}

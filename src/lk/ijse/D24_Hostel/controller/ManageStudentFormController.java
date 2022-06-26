package lk.ijse.D24_Hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.D24_Hostel.bo.BOFactory;
import lk.ijse.D24_Hostel.bo.custom.StudentBO;
import lk.ijse.D24_Hostel.dto.StudentDTO;
import lk.ijse.D24_Hostel.util.Time;
import lk.ijse.D24_Hostel.util.UILoader;
import lk.ijse.D24_Hostel.view.assets.tm.StudentTM;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class ManageStudentFormController {
    //Property Injection (DI) Exposed the object creation logic
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    public AnchorPane root;
    public TableView<StudentTM> tblStudents;
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtStudentAddress;
    public JFXTextField txtStudentContact;
    public JFXDatePicker studentDobDatePicker;
    public JFXComboBox cmbGenderID;
    public JFXButton btnAddNewCustomer;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXTextField txtSearch;
    public Label lblTime;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudents.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudents.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudents.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        txtStudentId.requestFocus();
        btnDelete.setDisable(true);
        cmbGenderID.getItems().addAll("Male", "Female", "Other");
        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtStudentId.setText(newValue.getStudent_id());
                txtStudentName.setText(newValue.getName());
                txtStudentAddress.setText(newValue.getAddress());
                txtStudentContact.setText(newValue.getContact());
                studentDobDatePicker.setValue(newValue.getDob());
                cmbGenderID.setValue(newValue.getGender());

                txtStudentId.setDisable(true);
                txtStudentName.setDisable(false);
                txtStudentAddress.setDisable(false);
                txtStudentContact.setDisable(false);
                studentDobDatePicker.setDisable(false);
                cmbGenderID.setDisable(false);
            }
        });
        loadAllStudents();
        loadTime();
    }

    /**
     * Get All Students
     */
    private void loadAllStudents() {
        tblStudents.getItems().clear();
        try {
            ArrayList<StudentDTO> allStudents = studentBO.getAllStudents();
            for (StudentDTO studentDTO : allStudents) {
                tblStudents.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Navigate To DashBord
     */
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("ReserveForm", root);
    }

    /**
     * Add New Student
     */
    public void btnAddNewOnAction(ActionEvent actionEvent) {
        disableFalse();
        clear();
        txtStudentId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudents.getSelectionModel().clearSelection();
    }


    /**
     * Save Student
     */
    public void btnSaveOnAction(ActionEvent actionEvent) throws ParseException {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtStudentAddress.getText();
        String contact = txtStudentContact.getText();
        LocalDate dob = studentDobDatePicker.getValue();
        String gender = (String) cmbGenderID.getValue();

        /**
         * Check Validation
         */
        if (!id.matches("^(ST-[0-9]{3,4})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Student type ID").show();
            txtStudentId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{4,40})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtStudentName.requestFocus();
            return;
        } else if (!address.matches("^[A-z0-9/, ]{5,30}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtStudentAddress.requestFocus();
            return;
        } else if (!contact.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact no").show();
            txtStudentContact.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existStudent(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                studentBO.saveStudent(new StudentDTO(id, name, address, contact, dob, gender));
                tblStudents.getItems().add(new StudentTM(id, name, address, contact, dob, gender));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the student " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (PersistenceException ignored){}
        } else {
            /**
             * Update Customer
             */
            try {
                if (!existStudent(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + id).show();
                }
                studentBO.updateStudent(new StudentDTO(id, name, address, contact, dob, gender));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the student " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
            selectedStudent.setName(name);
            selectedStudent.setAddress(address);
            selectedStudent.setContact(contact);
            selectedStudent.setDob(dob);
            selectedStudent.setGender(gender);
            tblStudents.refresh();
        }
        btnAddNewCustomer.fire();
    }

    /**
     * Delete Student
     */
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String id = tblStudents.getSelectionModel().getSelectedItem().getStudent_id();
        try {
            if (!existStudent(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + id).show();
            }
            studentBO.deleteStudent(id);
            tblStudents.getItems().remove(tblStudents.getSelectionModel().getSelectedItem());
            tblStudents.getSelectionModel().clearSelection();
            clear();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the student " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exist Student Details
     */
    private boolean existStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.checkStudentIsAvailable(id);
    }

    /**
     * Search Student Details
     */
    public void searchMouseClickedOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void search() throws SQLException, ClassNotFoundException {
        tblStudents.getItems().clear();
        String id = txtSearch.getText();

        try {
            if (id.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "There is empty search id " + id).show();
                loadAllStudents();
            } else if (!existStudent(txtSearch.getText())) {
                new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + id).show();
                loadAllStudents();
            }
            /*Get all student details*/
            ArrayList<StudentDTO> allDetails = studentBO.searchStudentByStudentID(id);
            for (StudentDTO details : allDetails) {
                tblStudents.getItems().add(new StudentTM(details.getStudent_id(), details.getName(), details.getAddress(), details.getContact(), details.getDob(), details.getGender()));
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
        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentAddress.clear();
        txtStudentContact.clear();
        studentDobDatePicker.getEditor().clear();
        cmbGenderID.getSelectionModel().clearSelection();
    }

    /**
     * Disable True
     */
    private void disableTrue() {
        txtStudentId.setDisable(true);
        txtStudentName.setDisable(true);
        txtStudentAddress.setDisable(true);
        txtStudentContact.setDisable(true);
        studentDobDatePicker.setDisable(true);
        cmbGenderID.setDisable(true);
    }

    /**
     * Disable False
     */
    private void disableFalse() {
        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtStudentAddress.setDisable(false);
        txtStudentContact.setDisable(false);
        studentDobDatePicker.setDisable(false);
        cmbGenderID.setDisable(false);
    }
}

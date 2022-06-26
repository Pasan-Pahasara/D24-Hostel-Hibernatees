package lk.ijse.D24_Hostel.bo.custom;

import lk.ijse.D24_Hostel.bo.SuperBO;
import lk.ijse.D24_Hostel.dto.ReserveDTO;
import lk.ijse.D24_Hostel.dto.RoomDTO;
import lk.ijse.D24_Hostel.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface ReserveBO extends SuperBO {
    boolean registerDetails(ReserveDTO dto) throws SQLException, ClassNotFoundException;

    StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException;

    RoomDTO searchRoom(String code) throws SQLException, ClassNotFoundException;

    boolean checkRoomIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException;

    boolean checkReserveIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String generateNewRegisterID() throws SQLException, ClassNotFoundException;

    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;

    ArrayList<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException;

    ArrayList<ReserveDTO> getAllReserve() throws SQLException, ClassNotFoundException;

    ArrayList<ReserveDTO> searchReserve(String id) throws SQLException, ClassNotFoundException;
}

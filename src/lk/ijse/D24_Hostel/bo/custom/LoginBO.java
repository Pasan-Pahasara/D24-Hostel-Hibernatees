package lk.ijse.D24_Hostel.bo.custom;

import lk.ijse.D24_Hostel.bo.SuperBO;
import lk.ijse.D24_Hostel.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface LoginBO extends SuperBO {
    ArrayList<LoginDTO> getAllUsers() throws SQLException, ClassNotFoundException;

    boolean saveUser(LoginDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateUser(LoginDTO dto) throws SQLException, ClassNotFoundException;

    boolean checkUserIsAvailable(String id) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    ArrayList<LoginDTO> searchUserByUserID(String id) throws SQLException, ClassNotFoundException;
}

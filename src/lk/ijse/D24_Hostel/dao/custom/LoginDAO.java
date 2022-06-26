package lk.ijse.D24_Hostel.dao.custom;

import lk.ijse.D24_Hostel.dao.CrudDAO;
import lk.ijse.D24_Hostel.entity.Login;

import java.sql.SQLException;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface LoginDAO extends CrudDAO<Login, String> {
    boolean ifUserExist(String id) throws SQLException, ClassNotFoundException;
}

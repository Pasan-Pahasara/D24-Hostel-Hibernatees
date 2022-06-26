package lk.ijse.D24_Hostel.dao.custom;

import lk.ijse.D24_Hostel.dao.SuperDAO;
import lk.ijse.D24_Hostel.entity.Reserve;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface ReserveDAO extends SuperDAO {
    String generateNewRegisterID() throws SQLException, ClassNotFoundException;

    ArrayList<Reserve> getAll() throws SQLException, ClassNotFoundException;

    boolean ifReserveExist(String id) throws SQLException, ClassNotFoundException;

    Reserve search(String id) throws SQLException, ClassNotFoundException;
}

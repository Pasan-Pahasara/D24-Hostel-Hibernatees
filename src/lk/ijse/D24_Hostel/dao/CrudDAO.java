package lk.ijse.D24_Hostel.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface CrudDAO<T, ID> extends SuperDAO {
    //Get All
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    //Save
    boolean save(T dto) throws SQLException, ClassNotFoundException;

    //Update
    boolean update(T dto) throws SQLException, ClassNotFoundException;

    //Delete
    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    //Search
    T search(ID id) throws SQLException, ClassNotFoundException;
}

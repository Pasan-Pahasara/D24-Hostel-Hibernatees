package lk.ijse.D24_Hostel.dao.custom;

import lk.ijse.D24_Hostel.dao.CrudDAO;
import lk.ijse.D24_Hostel.entity.Room;

import java.sql.SQLException;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface RoomDAO extends CrudDAO<Room, String> {
    boolean ifRoomExist(String id) throws SQLException, ClassNotFoundException;
    Room getRoomDetails(String name) throws SQLException, ClassNotFoundException;
}

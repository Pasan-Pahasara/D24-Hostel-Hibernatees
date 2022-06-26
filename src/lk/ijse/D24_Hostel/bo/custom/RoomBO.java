package lk.ijse.D24_Hostel.bo.custom;


import lk.ijse.D24_Hostel.bo.SuperBO;
import lk.ijse.D24_Hostel.dto.RoomDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface RoomBO extends SuperBO {
    List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException;

    boolean saveRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    boolean checkRoomIsAvailable(String id) throws SQLException, ClassNotFoundException;

    boolean deleteRoom(String id) throws SQLException, ClassNotFoundException;

    ArrayList<RoomDTO> searchRoomByRoomID(String id) throws SQLException, ClassNotFoundException;
}


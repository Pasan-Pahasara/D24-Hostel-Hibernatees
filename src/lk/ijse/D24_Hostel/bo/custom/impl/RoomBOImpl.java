package lk.ijse.D24_Hostel.bo.custom.impl;

import lk.ijse.D24_Hostel.bo.custom.RoomBO;
import lk.ijse.D24_Hostel.dao.DAOFactory;
import lk.ijse.D24_Hostel.dao.custom.RoomDAO;
import lk.ijse.D24_Hostel.dto.RoomDTO;
import lk.ijse.D24_Hostel.dto.StudentDTO;
import lk.ijse.D24_Hostel.entity.Room;
import lk.ijse.D24_Hostel.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class RoomBOImpl implements RoomBO {
    //Property Injection (DI) Exposed the object creation logic
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> allRooms = new ArrayList<>();
        for (Room room : all) {
            allRooms.add(new RoomDTO(room.getRoom_type_id(), room.getType(), room.getKey_money(), room.getQty()));
        }
        return allRooms;
    }

    @Override
    public boolean saveRoom(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.save(new Room(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty()));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty()));
    }

    @Override
    public boolean checkRoomIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.ifRoomExist(id);
    }

    @Override
    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(id);
    }

    @Override
    public ArrayList<RoomDTO> searchRoomByRoomID(String id) throws SQLException, ClassNotFoundException {
        Room room = roomDAO.search(id);
        ArrayList<RoomDTO> allRooms = new ArrayList<>();
        allRooms.add(new RoomDTO(room.getRoom_type_id(), room.getType(), room.getKey_money(), room.getQty()));
        return allRooms;
    }
}

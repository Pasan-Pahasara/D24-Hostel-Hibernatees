package lk.ijse.D24_Hostel.bo.custom.impl;

import lk.ijse.D24_Hostel.bo.custom.ReserveBO;
import lk.ijse.D24_Hostel.dao.DAOFactory;
import lk.ijse.D24_Hostel.dao.custom.ReserveDAO;
import lk.ijse.D24_Hostel.dao.custom.RoomDAO;
import lk.ijse.D24_Hostel.dao.custom.StudentDAO;
import lk.ijse.D24_Hostel.dto.ReserveDTO;
import lk.ijse.D24_Hostel.dto.RoomDTO;
import lk.ijse.D24_Hostel.dto.StudentDTO;
import lk.ijse.D24_Hostel.entity.Reserve;
import lk.ijse.D24_Hostel.entity.Room;
import lk.ijse.D24_Hostel.entity.Student;
import lk.ijse.D24_Hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class ReserveBOImpl implements ReserveBO {
    //Property Injection (DI) Exposed the object creation logic
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    private final ReserveDAO reserveDAO = (ReserveDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVE);

    @Override
    public boolean registerDetails(ReserveDTO dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class,dto.getStudent_id());
        Room room = session.get(Room.class, dto.getRoom_type_id());

//        int roomQty= Integer.parseInt(room.getQty());
//        int qty =1;
//        Query query = session.createQuery("UPDATE Room SET qty=? WHERE room_type_id =: id");
//        query.setParameter("id",roomQty-qty).uniqueResult();

        Reserve reserve = new Reserve(dto.getRes_id(), dto.getDate(),student,room, dto.getRoom_fee(), dto.getAdvance(), dto.getStatus());
        session.save(reserve);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException, NullPointerException {
        Student search = studentDAO.search(id);
        return new StudentDTO(search.getStudent_id(), search.getName(), search.getAddress(), search.getContact(), search.getDob(), search.getGender());
    }

    @Override
    public RoomDTO searchRoom(String code) throws SQLException, ClassNotFoundException {
        Room search = roomDAO.search(code);
        return new RoomDTO(search.getRoom_type_id(), search.getType(), search.getKey_money(), search.getQty());
    }

    @Override
    public boolean checkRoomIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return roomDAO.ifRoomExist(code);
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.ifStudentExist(id);
    }

    @Override
    public boolean checkReserveIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.ifStudentExist(id);
    }

    @Override
    public String generateNewRegisterID() throws SQLException, ClassNotFoundException {
        return reserveDAO.generateNewRegisterID();
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> all = studentDAO.getAll();
        ArrayList<StudentDTO> allStudents = new ArrayList<>();
        for (Student student : all) {
            allStudents.add(new StudentDTO(student.getStudent_id(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender()));
        }
        return allStudents;
    }

    @Override
    public ArrayList<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> allRooms = new ArrayList<>();
        for (Room room : all) {
            allRooms.add(new RoomDTO(room.getRoom_type_id(), room.getType(), room.getKey_money(), room.getQty()));
        }
        return allRooms;
    }

    @Override
    public ArrayList<ReserveDTO> getAllReserve() throws SQLException, ClassNotFoundException {
        ArrayList<Reserve> all = reserveDAO.getAll();
        ArrayList<ReserveDTO> allReserves = new ArrayList<>();
        for (Reserve reserve : all) {
            allReserves.add(new ReserveDTO(reserve.getRes_id(), reserve.getDate(), reserve.getStudent_id().getStudent_id(), reserve.getRoom_type_id().getRoom_type_id(), reserve.getRoom_fee(),reserve.getAdvance(),reserve.getStatus()));
        }
        return allReserves;
    }

    @Override
    public ArrayList<ReserveDTO> searchReserve(String id) throws SQLException, ClassNotFoundException {
        Reserve reserve = reserveDAO.search(id);
        ArrayList<ReserveDTO> allReserves = new ArrayList<>();
        allReserves.add(new ReserveDTO(reserve.getRes_id(), reserve.getDate(), reserve.getStudent_id().getStudent_id(), reserve.getRoom_type_id().getRoom_type_id(), reserve.getRoom_fee(),reserve.getAdvance(),reserve.getStatus()));
        return allReserves;
    }
}

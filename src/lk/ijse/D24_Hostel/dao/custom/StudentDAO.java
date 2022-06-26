package lk.ijse.D24_Hostel.dao.custom;

import lk.ijse.D24_Hostel.dao.CrudDAO;
import lk.ijse.D24_Hostel.entity.Student;

import java.sql.SQLException;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public interface StudentDAO extends CrudDAO<Student, String> {
    boolean ifStudentExist(String id) throws SQLException, ClassNotFoundException;
}

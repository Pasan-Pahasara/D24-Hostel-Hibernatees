package lk.ijse.D24_Hostel.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.D24_Hostel.bo.custom.StudentBO;
import lk.ijse.D24_Hostel.dao.DAOFactory;
import lk.ijse.D24_Hostel.dao.custom.StudentDAO;
import lk.ijse.D24_Hostel.dto.StudentDTO;
import lk.ijse.D24_Hostel.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class StudentBOImpl implements StudentBO {
    //Property Injection (DI) Exposed the object creation logic
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);

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
    public boolean saveStudent(StudentDTO dto) throws SQLException, ClassNotFoundException {
        return studentDAO.save(new Student(dto.getStudent_id(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getDob(), dto.getGender()));
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws SQLException, ClassNotFoundException {
        return studentDAO.update(new Student(dto.getStudent_id(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getDob(), dto.getGender()));
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.ifStudentExist(id);
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(id);
    }

    @Override
    public ArrayList<StudentDTO> searchStudentByStudentID(String id) throws SQLException, ClassNotFoundException, NullPointerException {
        Student search = studentDAO.search(id);
        ArrayList<StudentDTO> allStudents = new ArrayList<>();
            allStudents.add(new StudentDTO(search.getStudent_id(), search.getName(), search.getAddress(), search.getContact(), search.getDob(), search.getGender()));

            return allStudents;
    }
}
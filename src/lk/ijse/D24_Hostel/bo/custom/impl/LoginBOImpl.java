package lk.ijse.D24_Hostel.bo.custom.impl;

import lk.ijse.D24_Hostel.bo.custom.LoginBO;
import lk.ijse.D24_Hostel.dao.DAOFactory;
import lk.ijse.D24_Hostel.dao.custom.LoginDAO;
import lk.ijse.D24_Hostel.dto.LoginDTO;
import lk.ijse.D24_Hostel.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class LoginBOImpl implements LoginBO {
    //Property Injection (DI) Exposed the object creation logic
    private final LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public ArrayList<LoginDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<Login> all = loginDAO.getAll();
        ArrayList<LoginDTO> allStudents = new ArrayList<>();
        for (Login login : all) {
            allStudents.add(new LoginDTO(login.getUser_id(), login.getName(), login.getAddress(), login.getContact(), login.getPassword(), login.getGender()));
        }
        return allStudents;
    }

    @Override
    public boolean saveUser(LoginDTO dto) throws SQLException, ClassNotFoundException {
        return loginDAO.save(new Login(dto.getUser_id(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getPassword(), dto.getGender()));
    }

    @Override
    public boolean updateUser(LoginDTO dto) throws SQLException, ClassNotFoundException {
        return loginDAO.update(new Login(dto.getUser_id(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getPassword(), dto.getGender()));
    }

    @Override
    public boolean checkUserIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return loginDAO.ifUserExist(id);
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return loginDAO.delete(id);
    }

    @Override
    public ArrayList<LoginDTO> searchUserByUserID(String id) throws SQLException, ClassNotFoundException {
        Login search = loginDAO.search(id);
        ArrayList<LoginDTO> allUsers = new ArrayList<>();
        allUsers.add(new LoginDTO(search.getUser_id(), search.getName(), search.getAddress(), search.getContact(), search.getPassword(), search.getGender()));

        return allUsers;
    }
}

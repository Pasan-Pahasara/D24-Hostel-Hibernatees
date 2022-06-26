package lk.ijse.D24_Hostel.dao;

import lk.ijse.D24_Hostel.dao.custom.impl.LoginDAOImpl;
import lk.ijse.D24_Hostel.dao.custom.impl.ReserveDAOImpl;
import lk.ijse.D24_Hostel.dao.custom.impl.RoomDAOImpl;
import lk.ijse.D24_Hostel.dao.custom.impl.StudentDAOImpl;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    //Method for hide the object creation logic and return what client wants
    public SuperDAO getDAO(DAOTypes Types) {
        switch (Types) {
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVE:
                return new ReserveDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            default:
                return null;
        }
    }

    //Public final static integer values
    public enum DAOTypes {
        STUDENT, ROOM, RESERVE, LOGIN
    }

}

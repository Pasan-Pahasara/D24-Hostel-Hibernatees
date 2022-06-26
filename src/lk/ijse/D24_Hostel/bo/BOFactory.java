package lk.ijse.D24_Hostel.bo;

import lk.ijse.D24_Hostel.bo.custom.impl.LoginBOImpl;
import lk.ijse.D24_Hostel.bo.custom.impl.RoomBOImpl;
import lk.ijse.D24_Hostel.bo.custom.impl.StudentBOImpl;
import lk.ijse.D24_Hostel.bo.custom.impl.ReserveBOImpl;


/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    //Method for hide the object creation logic and return what client wants
    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case STUDENT:
                return new StudentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVE:
                return new ReserveBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }

    //Public final static integer values
    public enum BOTypes {
        STUDENT, ROOM, RESERVE, LOGIN
    }
}

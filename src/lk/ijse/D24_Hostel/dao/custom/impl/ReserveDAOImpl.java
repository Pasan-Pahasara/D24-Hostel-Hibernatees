package lk.ijse.D24_Hostel.dao.custom.impl;

import lk.ijse.D24_Hostel.dao.custom.ReserveDAO;
import lk.ijse.D24_Hostel.entity.Reserve;
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
public class ReserveDAOImpl implements ReserveDAO {
    @Override
    public String generateNewRegisterID() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("SELECT res_id FROM Reserve ORDER BY res_id DESC LIMIT 1");
        String s = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        if (s != null) {
            int newStudentId = Integer.parseInt(s.replace("RID-", "")) + 1;
            return String.format("RID-%03d", newStudentId);
        }
        return "RID-001";
    }

    @Override
    public ArrayList<Reserve> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Reserve> reserveList = new ArrayList();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Reserve ");
        reserveList = (ArrayList<Reserve>) query.list();
        transaction.commit();
        session.close();
        return reserveList;
    }

    @Override
    public boolean ifReserveExist(String id) throws SQLException, ClassNotFoundException {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        Query query = session.createQuery("SELECT student_id FROM Reserve WHERE student_id =:id");
//        Object id1 = query.setParameter("id", id).uniqueResult();
//        if(id1 != null){
//            return true;
//        }
//        transaction.commit();
//        session.close();
        return false;
    }

    @Override
    public Reserve search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Reserve reserve = session.get(Reserve.class, id);
        transaction.commit();
        session.close();
        return reserve;
    }
}

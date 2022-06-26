package lk.ijse.D24_Hostel.util;

import lk.ijse.D24_Hostel.entity.Login;
import lk.ijse.D24_Hostel.entity.Reserve;
import lk.ijse.D24_Hostel.entity.Room;
import lk.ijse.D24_Hostel.entity.Student;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration.addAnnotatedClass(Student.class).addAnnotatedClass(Room.class).addAnnotatedClass(Reserve.class).addAnnotatedClass(Login.class);
        sessionFactory = configuration.setProperties(properties).buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}

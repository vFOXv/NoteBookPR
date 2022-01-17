package my.ua.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MySessionFactory {
    private static final SessionFactory FACTORY = configFactory();

    public MySessionFactory() {
    }

    private static SessionFactory configFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static Session createSession() {
        return FACTORY.openSession();
    }
}

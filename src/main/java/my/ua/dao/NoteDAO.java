package my.ua.dao;

import my.ua.model.Note;
import my.ua.model.Topic;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NoteDAO {

    private final MySessionFactory mySessionFactory;
    private Session session;

    @Autowired
    public NoteDAO(MySessionFactory mySessionFactory) {
        this.mySessionFactory = mySessionFactory;
    }

    //получение всех записей из дневника
    public List<Note> getAllNotes() {
        session = mySessionFactory.createSession();
        List<Note> notes = new ArrayList<>();
        try {
            notes = session.createQuery("from Note").list();
            System.out.println(notes);
        } catch (HibernateException he) {
            System.out.println("Error getting notes: " + he);
            he.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return notes;
    }

    //получение всех тем для записей
    public List<Topic> getAllTopics() {
        session = mySessionFactory.createSession();
        List<Topic> topics = new ArrayList<>();
        try {
            topics = session.createQuery("from Topic ").list();
            System.out.println(topics);
        } catch (HibernateException he) {
            System.out.println("Error getting notes: " + he);
            he.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
            return topics;
        }
    }

    //получение записи по id
    public Note getNoteToId(Long id) {
        session = mySessionFactory.createSession();
        List<Note> notes = new ArrayList<>();
        try {
            Query query = session.createQuery("FROM Note N WHERE N.id=:paramName");
            query.setParameter("paramName", id);
            notes = query.list();
        } catch (HibernateException he) {
            System.out.println("Error getting note: " + he);
            he.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return notes.get(0);
    }

    //удаление записи из дневника
    public void deleteNote(Long id) {
//        int result = 0;
//        session = mySessionFactory.createSession();
//        try {
//            Query query = session.createQuery("DELETE Note N WHERE N.id =:paramName");
//            query.setParameter("paramName", id);
        //обновляет поля и возвращает кол-во обновленных полей
//            result = query.executeUpdate();
//        } catch (HibernateException he) {
//            System.out.println(he.getMessage());
//        } finally {
//            if (session.isOpen()) {
//                session.close();
//            }
//        }
//        return result;

        Note note = null;
        session = mySessionFactory.createSession();
        try {
            note = (Note) session.get(Note.class, id);
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(note);
            transaction.commit();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    //получение текущей даты.
    public Date getTodayDate() {
        return new Date();
    }

    //запись новой заметки в DB
    public void addNewNoteDAO(Note note) {
        session = mySessionFactory.createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(note);
            transaction.commit();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    //запись новой темы в DB
    public void addNewTopicDAO(Topic topic) {
        session = mySessionFactory.createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(topic);
            transaction.commit();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}

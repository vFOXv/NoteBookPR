package my.ua.dao;

import my.ua.model.Note;
import my.ua.model.Topic;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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

    //получение записи(note) по id
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

    //получение темы(topic) по id
    public Topic getTopicToId(Long id) {
        session = mySessionFactory.createSession();
        List<Topic> topics = new ArrayList<>();
        try {
            Query query = session.createQuery("FROM Topic T WHERE T.id=:paramName");
            query.setParameter("paramName", id);
            topics = query.list();
        } catch (HibernateException he) {
            System.out.println("Error getting note: " + he);
            he.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return topics.get(0);
    }

    //удаление записи(note) из дневника(DB)
    public void deleteNote(Long id) {

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

    //удаление темы(topic) из DB
    public void deleteTopicDAO(Long id) {
        Topic topic = null;
        session = mySessionFactory.createSession();
        try {
            topic = (Topic) session.get(Topic.class, id);
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(topic);
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

    //запись новой заметки(note) в DB
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

    //проверка уникальности имени темы(topic)
    public boolean uniqueNameTopicDAO(String newName) {
        boolean unique = false;
        session = mySessionFactory.createSession();
        try {
            Query query = session.createQuery("FROM Topic T WHERE name= :paramName");
            query.setParameter("paramName", newName);
            List<Topic> topics = query.list();
            if (topics.isEmpty()) {
                unique = true;
            } else {
                System.err.println("This topic already exists!!!");
            }
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return unique;
    }

    //запись новой темы в DB
    public void addNewTopicDAO(Topic topic) {
        //проверяеться уникальность темы и если она уникальна, то происходит ее запись в DB
        boolean unique = uniqueNameTopicDAO(topic.getName());
        if (unique) {
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

    //изменения данных в заметке(note)
    public void updateNote(Note note) {
        Transaction transaction = null;
        //установка даты в переданный объект из старой, еще не измененой записи
        note.setThisDate(getNoteToId(note.getId()).getThisDate());

        try {
            session = mySessionFactory.createSession();
        } catch (HibernateException ex) {
            session = mySessionFactory.createSession();
        }

        try {
            transaction = session.beginTransaction();
            session.merge(note);
            transaction.commit();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    //поиск заметок по темам
    //listSearchTopics - список выбраных тем по которым выбираються записи(notes)
    public List<Note> searchToTopic(List<Topic> listSearchTopics) {

        //список всех записей (из него будут удаляться лишнии записи в ходе сортировки)
        //в итоге остануться только записи с упоминанием тем поиска
        List<Note> rightNotes = getAllNotes();


        //если список пустой возвращаем пустой список rightNotes
        if (listSearchTopics.size() == 0){
            rightNotes.clear();
            return rightNotes;
        }

        //пребор заметок
        for (int rn = 0; rn < rightNotes.size(); rn++) {
            //перебор тем в заметках
            Note note = (Note) rightNotes.get(rn);
            //перемменая - счетчик, увеличиваеться с каждой найденной темой по которым ищут заметку
            int flag = 0;
            for (int nt = 0; nt < note.getTopics().size(); nt++) {
                //перебор совпадений тем в заметке и тем в поиске
                //если хотя бы одна темы нее в заметке, то эта заметка удаляеться из списка
                for (int lst = 0; lst < listSearchTopics.size(); lst++) {
                    if ((listSearchTopics.get(lst).getId()) == (note.getTopics().get(nt).getId())) {
                        //при совпадении темы задоной в поиске и темы в note переменная flag увеличиваеться на 1
                        flag++;
                    }
                    //при условии: колличество совпадающих тем в note меньше чем задано в поиске  flag < listSearchTopics.size()
                    //и кол-во итераций в цикле перебора заметок(2-й цикл) по темам note = длинне List  nt == note.getTopics().size()-1 (т.е проверенны все темы)
                    //и кол-во итераций в цикле перебора тем поска(3-цикл) = длинне List lst == listSearchTopics.size() - 1
                    //то удаляем note и уменьшаем итеррационную переменную(rn) на 1, т.к. длина List rightNotes уменьшилась.
                    if (flag < listSearchTopics.size() && nt == note.getTopics().size() - 1 && lst == listSearchTopics.size() - 1) {
                        rightNotes.remove(rn);
                        rn--;
                    }

                }
            }
        }
        return rightNotes;
    }

    //поиск заметок по дате
    public List<Note> searchToDateDAO(String idStr){
        //получение id заметки с выбранной датой
        Long id = Long.parseLong(idStr);
        //получение даты
        Date date = getNoteToId(id).getThisDate();
        //получение формат даты в котором будем искать заметки
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        //List содержайий заметки с выбранной датой
        List<Note> notesToSearch = new ArrayList<>();
        //List содержащий все заметки из BD
        List allNotes = getAllNotes();

        for(int i=0; i<allNotes.size();i++){
            Note note = (Note) allNotes.get(i);
            if(formatter.format(note.getThisDate()).equals(formatter.format(date))) {
                notesToSearch.add(note);
            }
        }
        return notesToSearch;
    }

}



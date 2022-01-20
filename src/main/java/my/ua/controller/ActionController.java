package my.ua.controller;

import my.ua.dao.NoteDAO;
import my.ua.model.Note;
import my.ua.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/note")
public class ActionController {
    public final NoteDAO noteDAO;

    public ActionController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    //показвает конкретную запись в дневнике
    @GetMapping("/{id}")
    public String showThisNote(@PathVariable("id") Long id, Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("thisNote", noteDAO.getNoteToId(id));
        return "Action/this_note";
    }

    //удаляет конкретную запись в дневнике
    @GetMapping("/remove/{id}")
    public String deleteNote(@PathVariable("id") Long id) {
        noteDAO.deleteNote(id);
        return "redirect:/show/all";
    }

    //преход на страницу HTML для создания новой заметки
    //и добавление в новый объект note списка topics и текущей даты
    @GetMapping("/new")
    public String newNote(Model model) {
        Note newNote = new Note();
        newNote.setThisDate(noteDAO.getTodayDate());
        newNote.setTopics(noteDAO.getAllTopics());
        model.addAttribute("NewNote", newNote);
        model.addAttribute("Today", newNote.changeThisDateInString());
        return "Action/new_note";
    }

    //получение данных с HTML и запись новой заметки в DB
    @PostMapping("/newNote")
    public String addNewNote(@ModelAttribute Note newNote) {
        newNote.setThisDate(noteDAO.getTodayDate());
        noteDAO.addNewNoteDAO(newNote);
        System.out.println(newNote);
        return "redirect:/show/all";
    }

    //преход на страницу HTML для создания новой topic
    @GetMapping("/newTopic")
    public String newTopic(Model model) {
        model.addAttribute("newTopic", new Topic());
        return "Action/new_topic";
    }

    //получение данных с HTML и запись новой темы в DB
    @PostMapping("/newTopicAdd")
    public String addNewTopic(@ModelAttribute Topic newTopic) {
        noteDAO.addNewTopicDAO(newTopic);
        return "Start/menu_notebook";
    }

    //преход на страницу HTML для удаления темы(topic)
    @GetMapping("/deleteTopic")
    public String deleteTopicHTML(Model model) {
        model.addAttribute("AllTopics", noteDAO.getAllTopics());
        return "Action/delete_topic";
    }

    //получение данных с HTML и удаление темы(topic) из DB
    @PostMapping("/deleteTopic")
    public String deleteTopicDB(@ModelAttribute Topic topic) {
        System.out.println("----------------------------->"+topic.toString());
        Long id = topic.getId();
        noteDAO.deleteTopicDAO(id);
        return "redirect:/note/new";
    }
}

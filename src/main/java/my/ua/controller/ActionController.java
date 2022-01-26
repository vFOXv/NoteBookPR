package my.ua.controller;


import my.ua.dao.NoteDAO;
import my.ua.model.Note;
import my.ua.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/note")
public class ActionController {
    private final NoteDAO noteDAO;

    public ActionController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    //показвает конкретную запись в дневнике для просмотра и редакции
    @GetMapping("/{id}")
    public String showThisNote(@PathVariable("id") Long id, Model model) throws SQLException, ClassNotFoundException {
//        note.setId(noteDAO.getNoteToId(id).getId());
//        note.setThisDate(noteDAO.getNoteToId(id).getThisDate());
//        note.setThisDate(noteDAO.getNoteToId(id).getThisDate());
//        note.setTopics(noteDAO.getNoteToId(id).getTopics());
//        note.setMyText(noteDAO.getNoteToId(id).getMyText());

        model.addAttribute("thisNote",noteDAO.getNoteToId(id));

        return "Action/this_note";
    }

    //получает данные с формы конкретной записи и позволяет ее редактировать
    @PostMapping("/update/{id}")
    public String updateNote(@ModelAttribute("thisNote") @Valid Note note, BindingResult bindingResult){
        //добавление в объект даты, т.к. она не передаеться из HTML
        Long id = note.getId();
        note.setThisDate(noteDAO.getNoteToId(id).getThisDate());
        System.out.println("VALID--------------->"+note);

        if (bindingResult.hasErrors()) {
            return "Action/this_note";
        }

        noteDAO.updateNote(note);
        System.out.println(note);

        return "redirect:/show/all";
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
    public String addNewNote(@ModelAttribute("NewNote") Note newNote) {
        newNote.setThisDate(noteDAO.getTodayDate());
        noteDAO.addNewNoteDAO(newNote);
        System.out.println(newNote);
        return "redirect:/show/all";
    }

    //преход на страницу HTML для создания новой topic
    @GetMapping("/newTopic")    public String newTopic(@ModelAttribute("newTopic") Topic topic) {

        return "Action/new_topic";
    }

    //получение данных с HTML и запись новой темы в DB
    @PostMapping("/newTopicAdd")
    public String addNewTopic(@ModelAttribute("newTopic") @Valid Topic newTopic,
                              BindingResult bindingResult) {
        System.out.println("NewTopic--------------->Show--------->"+newTopic);

        if (bindingResult.hasErrors()) {
            return "Action/new_topic";
        }

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
    public String deleteTopicDB(@RequestParam("topic_name") String name_id) {
        Long id = Long.parseLong(name_id);
        noteDAO.deleteTopicDAO(id);
        return "redirect:/note/new";
    }
}

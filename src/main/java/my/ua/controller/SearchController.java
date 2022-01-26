package my.ua.controller;



import my.ua.dao.NoteDAO;
import my.ua.model.Note;
import my.ua.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final NoteDAO noteDAO;

    public SearchController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    //переход на страницу поиска заметок по дате и получение списка дат
    @GetMapping("/date")
    public String searchToDate(Model model) {
        model.addAttribute("ListNotes", noteDAO.getAllNotes());
        return "Search/to_date";
    }

    //ролучение данных с HTML и поиск заметки по дате
    @PostMapping("/date")
    public String searchToDate(@RequestParam("note_date") String note_date, Model model) {
        Long id = Long.parseLong(note_date);
        model.addAttribute("thisNote",noteDAO.getNoteToId(id));
        return "Action/this_note";
    }

    //переход на страницу поиска заметок по темам и получение списка тем
    @GetMapping("/toTopic")
    public String searchToTopic(Model model){
        Note note = new Note();
        note.setTopics(noteDAO.getAllTopics());
        model.addAttribute("ListTopicsInNote", note);
        return "Search/to_topic";
    }

    //получени данных с HTML и поиск заметок по темам
    @PostMapping("/toTopic")
    public String searchToTopic(@ModelAttribute Note note,
                                Model model){
        //получаем с HTML объект note со списком выбраных тем
        List <Topic> listTopics = note.getTopics();
        System.out.println("Поиск по темам---------------->"+listTopics);
        //передаем на HTML список найденых notes
        model.addAttribute("AllNotes",noteDAO.searchToTopic(listTopics));
        model.addAttribute("Link","Update");
        model.addAttribute("DeleteThisNote","Delete");
        return "Show/show_all_notes";
    }
}

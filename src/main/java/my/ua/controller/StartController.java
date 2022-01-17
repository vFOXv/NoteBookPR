package my.ua.controller;

import my.ua.dao.NoteDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {
    public final NoteDAO noteDAO;

    public StartController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    //вызывет стартовую страницу с меню
    @GetMapping("")
    public String myMenu() {
        return "Start/menu_notebook";
    }


}

package my.ua.controller;

import my.ua.dao.NoteDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/show")
public class ShowController {
    private final NoteDAO noteDAO;

    public ShowController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    //показывает все записи в дневнике
    @GetMapping("/all")
    public String showAllNotes(Model model){
        model.addAttribute("AllNotes", noteDAO.getAllNotes());
        model.addAttribute("Link","Update");
        model.addAttribute("DeleteThisNote","Delete");
        return "Show/show_all_notes";
    }


}

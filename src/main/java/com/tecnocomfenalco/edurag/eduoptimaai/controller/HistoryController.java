package com.tecnocomfenalco.edurag.eduoptimaai.controller;

import com.tecnocomfenalco.edurag.eduoptimaai.service.AssigmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final AssigmentService service;

    public HistoryController(AssigmentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("history", service.findHistory());
        return "history/list";
    }

    @PostMapping("/clear")
    public String clear() {
        service.deleteAll();
        return "redirect:/history";
    }
}

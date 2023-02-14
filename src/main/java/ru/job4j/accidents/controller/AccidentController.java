package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        Accident accident = new Accident();
        accident.setName("Введите имя заявителя");
        accident.setText("Введите описание происшествия");
        accident.setAddress("Введите адрес происшествия");
        model.addAttribute("accident", accident);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        System.out.println(accident);
        accidents.create(accident);
        return "redirect:/index";
    }
}
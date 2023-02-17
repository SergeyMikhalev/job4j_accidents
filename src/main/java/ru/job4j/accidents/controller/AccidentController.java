package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        Accident accident = new Accident(0,
                "Введите имя заявителя",
                "Введите описание происшествия",
                "Введите адрес происшествия",
                null,
                new HashSet<>());
        System.out.println(accidentTypeService.findAll());
        model.addAttribute("accidentTypes", accidentTypeService.findAll());
        model.addAttribute("allRules", ruleService.findAll());
        model.addAttribute("accident", accident);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam(value = "ruleIds", required = false, defaultValue = "") List<Integer> ruleIds) {
        System.out.println(ruleIds);
        Optional<AccidentType> type = accidentTypeService.findById(accident.getType().getId());
        if (type.isEmpty()) {
            return "redirect:/index";
        }
        accident.setType(type.get());
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/updateAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", "Petr Arsentev");
        Optional<Accident> accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/index";
        }
        model.addAttribute("accident", accident.get());
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        Optional<AccidentType> type = accidentTypeService.findById(accident.getType().getId());
        if (type.isEmpty()) {
            return "redirect:/index";
        }
        accident.setType(type.get());
        accidentService.update(accident);
        return "redirect:/index";
    }
}
package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Accident accident = new Accident(0,
                "Введите имя заявителя",
                "Введите описание происшествия",
                "Введите адрес происшествия",
                null,
                new HashSet<>());
        model.addAttribute("accidentTypes", accidentTypeService.findAll());
        model.addAttribute("allRules", ruleService.findAll());
        model.addAttribute("accident", accident);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam(value = "ruleIds", required = false, defaultValue = "") List<Integer> ruleIds) {
        accidentService.checkAndCreate(accident, ruleIds);
        return "redirect:/index";
    }

    @GetMapping("/updateAccident")
    public String viewUpdateAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Accident accident = accidentService.findById(id);
        model.addAttribute("accident", accident);
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.updateText(accident);
        return "redirect:/index";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String onNoSuchElementRedirection(NoSuchElementException e, Model model) {
        model.addAttribute("errorMsg", e.getMessage());
        return "error";
    }
}
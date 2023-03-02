package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.AuthorityRepository;
import ru.job4j.accidents.repository.data.UserRepository;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;


    @PostMapping("/registration")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (RuntimeException e) {
            return "redirect:/registration?error=true";
        }
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String regPage(Model model,
                          @RequestParam(value = "error", required = false) String error) {
        String errorMessage = " ";
        if (Objects.nonNull(error)) {
            errorMessage = "Ошибка регистрации. Возможно пользователь с таким именем уже существует!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "registration";
    }
}

package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService users;

    @Test
    public void whenDefaultRegistration() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void whenRegistrationFailed() throws Exception {
        this.mockMvc.perform(get("/registration?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString(
                                "Ошибка регистрации. "
                                        + "Возможно пользователь с таким именем уже существует!"
                        )
                ));
    }

    @Test
    public void whenDefaultRegistrationPost() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .param("username", "Виталий")
                        .param("password", "Молодец")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());
        assertThat(argument.getValue().getUsername(), is("Виталий"));
    }


}
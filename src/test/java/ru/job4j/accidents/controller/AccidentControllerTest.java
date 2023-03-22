package ru.job4j.accidents.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private static AccidentService accidents;

    @Captor
    private ArgumentCaptor<ArrayList<Integer>> listCaptor;

    @BeforeEach
    void setUp() {
        when(accidents.findById(1)).thenReturn(new Accident(0,
                "Введите имя заявителя",
                "Введите описание происшествия",
                "Введите адрес происшествия",
                null,
                new HashSet<>()));
        when(accidents.findById(999)).thenThrow(NoSuchElementException.class);
    }

    @Test
    @WithMockUser
    public void whenCreateAccident() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void whenUpdateAccidentButNoAccident() throws Exception {
        this.mockMvc.perform(get("/updateAccident?id=999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @WithMockUser
    public void whenUpdateAccident() throws Exception {
        this.mockMvc.perform(get("/updateAccident?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateAccident"));

    }


    @Test
    @WithMockUser
    public void whenSaveAccident() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "Авария")
                        .param("ruleIds", "1", "2")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> accident = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).checkAndCreate(accident.capture(), listCaptor.capture());
        assertThat(accident.getValue().getName(), is("Авария"));
        assertThat(listCaptor.getValue().size(), is(2));
        assertTrue(listCaptor.getValue().containsAll(List.of(1, 2)));
    }

    @Test
    @WithMockUser
    public void whenUpdateAccidentPost() throws Exception {
        this.mockMvc.perform(post("/updateAccident")
                        .param("description", "Совершенно новое описание")
                        .param("id", "114")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> accident = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).updateText(accident.capture());
        assertThat(accident.getValue().getDescription(), is("Совершенно новое описание"));
        assertThat(accident.getValue().getId(), is(114));
    }
}
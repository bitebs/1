package org.itstep.helloworldspring;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestApplication {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getBooks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    public void users() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username", Matchers.equalTo("admin")))
        ;
    }

    @ParameterizedTest
    @Disabled
    @ValueSource(ints = {-1, 0, 1, 2, 3, Integer.MAX_VALUE})
    public void getUserById(int id) throws Exception {
        ResultActions actions = mvc
                .perform(MockMvcRequestBuilders.get("/user/{id}",id)
                        .accept(MediaType.APPLICATION_JSON));
        actions
                .andExpect(status().isOk());
        switch (id){
            case 1:
                actions
                        .andExpect(jsonPath("$.username", Matchers.equalTo("admin")));
                break;
            case 2:
                actions
                        .andExpect(jsonPath("$.username", Matchers.equalTo("user")));
                break;
            default:
                actions
                        .andExpect(content().string(""));
        }
    }
}

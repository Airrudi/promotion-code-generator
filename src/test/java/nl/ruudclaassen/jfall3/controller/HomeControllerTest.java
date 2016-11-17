package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.general.Constants;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class HomeControllerTest {
    private MockMvc mockMvc;
    private HomeController homeController;

    @Before
    public void setup(){
        homeController = new HomeController();
        mockMvc = new MockMvcBuilders().standaloneSetup(homeController).build();
    }

    @Test
    public void homeShouldReturnHomepage() throws Exception{
        mockMvc.perform(get("/")).andExpect(view().name(Constants.HOMEPAGE));
    }
}
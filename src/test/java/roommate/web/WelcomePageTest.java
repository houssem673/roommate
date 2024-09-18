package roommate.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import roommate.security.helper.WithMockOAuth2User;
import roommate.web.controller.WelcomeController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WelcomeController.class)
public class WelcomePageTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockOAuth2User
    @DisplayName("WelcomePage is available")
    void test_1() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("right HTML triggered")
    void test_2() throws Exception {
        mockMvc.perform(get("/")).andExpect(view().name("WelcomePage"));
    }

    //Interessehalber, ob richtig gerendert wurde
    @Test
    @WithMockOAuth2User
    @DisplayName("Content contains our Slogan")
    void test_3() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("Welcome to Roommate");
        assertThat(responseBody).contains("Your solution for flexible workspace booking at Heinrich-Heine-Universität.");
    }

    //Interessehalber, ob richtig gerendert wurde
    @Test
    @WithMockOAuth2User()
    @DisplayName("Content contains href for Booking button")
    void test_4() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<a href=\"/booking\" class=\"custom-link\">")));
    }

}
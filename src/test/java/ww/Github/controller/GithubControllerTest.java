package ww.Github.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class GithubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void isAcutalTypeJson() throws Exception {

        String expectedType = "application/json";
        String url = "/name/seaborg44";

        String actualType = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andReturn().getResponse().getContentType();

        Assertions.assertEquals(expectedType, actualType);
    }

    @Test
    public void isCode404() throws Exception {

        int expectedCode = 404;
        String url = "/name/14qfaacaf11r1fgf253";

        int returnedCode = this.mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse().getStatus();

        Assertions.assertEquals(expectedCode, returnedCode);
    }

}
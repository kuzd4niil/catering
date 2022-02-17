package com.example.demo;

import com.example.demo.models.Catering;
import com.example.demo.models.User;
import com.example.demo.security.ApplicationUserRole;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Simple trial test")
    public void studyTest() throws Exception {
        String username = "dan";
        String password = "1000";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "dan")
                .param("password", "1000"))
                .andExpect(status().isOk()).andReturn();

        JsonObject jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        String token = jsonObject.get("access_token").getAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/all")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking custom exception: UserWithThisEmailExists & UserWithThisUsernameExists")
    public void testCustomException() throws Exception {
        User user = new User();
        user.setEmail("nik@gmail.com");
        user.setPassword("500");
        user.setUsername("nik");
        user.setUserRole(ApplicationUserRole.USER);
        user.setCaterings(new ArrayList<Catering>());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "dan")
                .param("password", "1000"))
                .andExpect(status().isOk()).andReturn();

        Gson gson = new Gson();
        String json = gson.toJson(user);

        JsonObject jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        String token = jsonObject.get("access_token").getAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();

        result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()).andReturn();

        jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        String exceptionMessage = jsonObject.get("message").getAsString();

        assertEquals("A user with this username exists", exceptionMessage);

        user.setUsername("nik2");
        json = gson.toJson(user);

        result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()).andReturn();

        jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        exceptionMessage = jsonObject.get("message").getAsString();

        assertEquals("A user with this email exists", exceptionMessage);

        result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "nik")
                .param("password", "500"))
                .andExpect(status().isOk()).andReturn();

        jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        token = jsonObject.get("access_token").getAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    @DisplayName("Since the controller is available for everyone, it need to check authority 'MODIFY_CATERING' for not method which isn't idempotent")
    public void testAuthoritiesCateringController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/catering/all"))
                .andExpect(status().isOk()).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "max")
                .param("password", "2000"))
                .andExpect(status().isOk()).andReturn();

        JsonObject jsonObject = (JsonObject) JsonParser.parseString(result.getResponse().getContentAsString());
        String token = jsonObject.get("access_token").getAsString();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/catering/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @AfterAll
    public static void clearDatabase() {
        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/catering", "postgres", "password");
            stmt = connection.createStatement();
            stmt.execute("DELETE FROM USERS WHERE username='nik'");
            stmt.execute("ALTER SEQUENCE USER_ID_SEQUENCE RESTART WITH 2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

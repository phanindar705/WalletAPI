package com.phani.WalletAPI.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phani.WalletAPI.Dto.AuthRequest;
import com.phani.WalletAPI.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest

class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;

    public  String getToken() throws Exception{
        String username = "phani";
        AuthRequest user = new AuthRequest(username,"phani");
        String requestJSON = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
       // System.out.println(response);
        String getResponse = response.toString();
        String jwttoken = getResponse.substring(7);
        return jwttoken;
    }

    @Test
    public void createUser() throws Exception{

        String path = "src/test/jsonfile/Create_User.json";
        String requestBody =  new String(Files.readAllBytes(Paths.get(path)));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")

                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }



    @Test
    void getUser() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/phani")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteUser() throws Exception {
        String JwtToken = getToken();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/users/phani").header(HttpHeaders.AUTHORIZATION,"Bearer" + JwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
}
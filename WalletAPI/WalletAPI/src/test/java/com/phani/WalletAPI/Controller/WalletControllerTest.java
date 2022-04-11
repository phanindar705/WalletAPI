package com.phani.WalletAPI.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Dto.AuthRequest;
import com.phani.WalletAPI.Service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest

class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    @MockBean
    private UserRepository userRepository;


    @InjectMocks
    private WalletController walletController;

    public String getToken() throws Exception {
        String username = "phani";
        String password = "phani";
        AuthRequest user = new AuthRequest(username, password);

        String requestJSON = objectMapper.writeValueAsString(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJSON))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
        System.out.println(response );
        String getResponse = response.toString();
        String token = getResponse.substring(7);
        return token;
    }


    @Test
    void createWallet() throws Exception {




        String path = "src/test/jsonfile/Create_Wallet.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(path)));

        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


    }

    @Test
    void getUserWallet() throws Exception {


        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/wallets")

                .content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        assertNotNull(result2);
    }

    @Test
    void deleteWallet() throws  Exception {
       //String JwtToken = getToken();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/wallets/12345")
                        //.header(HttpHeaders.AUTHORIZATION,"Bearer" + JwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }
}
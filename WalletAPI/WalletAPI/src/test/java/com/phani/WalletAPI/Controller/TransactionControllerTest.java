package com.phani.WalletAPI.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.phani.WalletAPI.Controller.TransactionController;
import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Model.Transaction;
import com.phani.WalletAPI.Model.User;
import com.phani.WalletAPI.Service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest


class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void transferMoneyTest() throws Exception {


        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");
        Mockito.when(transactionService.MoneyTransfer(transaction)).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void getAllTransactionsTest() throws Exception {
        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");

        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction));

        Mockito.when(transactionService.AllTransactions()).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].transactionStatus",Matchers.is("Successful")));


    }

    @Test
     public void getAllUserTransactionsTest() throws Exception {
        User user = new User(1,"phani","phani","23456","phani@gmail","true");
        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");
        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction));

        Mockito.when(userRepository.findByUserName("phani")).thenReturn(user);
        Mockito.when(transactionService.GetUserTransactions(user.getPhoneNumber())).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/" + user.getUserName()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].transactionId", Matchers.is(0)));


    }




}
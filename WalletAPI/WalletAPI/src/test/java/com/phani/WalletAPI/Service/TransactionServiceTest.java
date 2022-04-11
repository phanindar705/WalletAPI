package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.TransactionRepository;
import com.phani.WalletAPI.Dao.WalletRepository;
import com.phani.WalletAPI.Model.Transaction;
import com.phani.WalletAPI.Model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private WalletRepository walletRepository;

    @Test
    void moneyTransfer() {
        Wallet Senderwallet = new Wallet("12345",150);
        Wallet ReceiverWallet = new Wallet("54321",100);
        Mockito.when(walletRepository.findById("12345")).thenReturn(Optional.of(Senderwallet));
        Mockito.when(walletRepository.findById("54321")).thenReturn(Optional.of(ReceiverWallet));
        Transaction transaction = new Transaction(0L,"12345","54321",100,"processing");
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Assertions.assertEquals(transaction,transactionService.MoneyTransfer(transaction));
    }


    @Test
    void allTransactionsTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(Stream.of(new Transaction(0L,"12345","54321",100L,"processing")).collect(Collectors.toList()));
        Assertions.assertEquals(1,transactionService.AllTransactions().size());
    }

    @Test
    void getUserTransactions() {
        Mockito.when(transactionRepository.findAll()).thenReturn(Stream.of(new Transaction(0L,"12345","54321",100L,"processing")).collect(Collectors.toList()));
        Assertions.assertEquals(1,transactionService.GetUserTransactions("12345").size());
    }
}
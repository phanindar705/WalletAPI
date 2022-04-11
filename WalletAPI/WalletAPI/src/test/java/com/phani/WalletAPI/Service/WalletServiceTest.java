package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.WalletRepository;
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
class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

    @Test
    void createUserWallet() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
        Assertions.assertEquals(wallet, walletService.CreateUserWallet(wallet));
    }

    @Test
    void geTAllWalletDetails() {
        Mockito.when(walletRepository.findAll()).thenReturn(Stream.of(new Wallet("12345",500)).collect(Collectors.toList()));
        Assertions.assertEquals(1,walletService.GeTAllWalletDetails().size());
    }

    @Test
    void getWalletDetailsById() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.findById("12345")).thenReturn(Optional.of(wallet));
        Assertions.assertEquals(wallet,walletService.GetWalletDetailsById("12345"));
    }

    @Test
    void deleteWalletDetailsById() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.findByphoneNumber("12345")).thenReturn(wallet);
        walletService.DeleteWalletDetailsById("12345");
        Mockito.verify(walletRepository,Mockito.times(1)).deleteById("12345");
    }
}
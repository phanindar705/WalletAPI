package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.WalletRepository;
import com.phani.WalletAPI.Model.User;
import com.phani.WalletAPI.Model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public  boolean CheckWallet(Wallet wallet){
        List<Wallet> AllWallets = walletRepository.findAll();
        for(Wallet ExistingWallet : AllWallets){
            if(ExistingWallet.getPhoneNumber().equals(wallet.getPhoneNumber()))
                return false;
        }
        return true;
    }

    public Wallet CreateUserWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }


    public List<Wallet> GeTAllWalletDetails(){
        return walletRepository.findAll();
    }

    public Wallet GetWalletDetailsById(String phoneNumber){
        return walletRepository.findById(phoneNumber).get();
    }


    public Wallet DeleteWalletDetailsById(String phoneNumber){

        Wallet existed = walletRepository.findByphoneNumber(phoneNumber);
        if(existed != null){
            walletRepository.deleteById(phoneNumber);}
        return existed;
    }
}

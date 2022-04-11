package com.phani.WalletAPI.Dao;

import com.phani.WalletAPI.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,String> {
     Wallet findByphoneNumber(String phoneNumber);
}

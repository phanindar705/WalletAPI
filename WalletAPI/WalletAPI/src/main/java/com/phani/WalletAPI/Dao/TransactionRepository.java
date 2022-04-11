package com.phani.WalletAPI.Dao;

import com.phani.WalletAPI.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}

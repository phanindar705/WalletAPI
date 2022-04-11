package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.TransactionRepository;
import com.phani.WalletAPI.Dao.WalletRepository;
import com.phani.WalletAPI.Model.Transaction;
import com.phani.WalletAPI.Model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public Transaction MoneyTransfer(Transaction transaction){
        Wallet senderWallet = walletRepository.findById(transaction.getSenderPhoneNumber()).get();
        Wallet ReceiverWallet = walletRepository.findById(transaction.getReceiverPhoneNumber()).get();
        if(senderWallet.getBalance() >= transaction.getAmount()){

            senderWallet.setBalance(senderWallet.getBalance()-transaction.getAmount());
            walletRepository.save(senderWallet);

            ReceiverWallet.setBalance(ReceiverWallet.getBalance()+transaction.getAmount());
            walletRepository.save(ReceiverWallet);

            transaction.setTransactionStatus("Transaction Successful");
            return transactionRepository.save(transaction);
        }
        else {
            transaction.setTransactionStatus("Transaction Failed");
            return transactionRepository.save(transaction);
        }
    }


    public List<Transaction>AllTransactions(){
        return   transactionRepository.findAll();
    }


    public List<Transaction>GetUserTransactions(String UserphoneNumber){
        List<Transaction>userTransactions = new ArrayList<Transaction>();
        List<Transaction>transactionsList = AllTransactions();
        for(Transaction transaction : transactionsList){
            if(transaction.getSenderPhoneNumber().equals(UserphoneNumber) || transaction.getReceiverPhoneNumber().equals(UserphoneNumber)){
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }
}

package com.phani.WalletAPI.Controller;

import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Dto.Response;
import com.phani.WalletAPI.Model.Transaction;
import com.phani.WalletAPI.Model.User;
import com.phani.WalletAPI.Service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> TransferMoney(@RequestBody Transaction transaction){
        logger.info("Transaction :" + transactionService.MoneyTransfer(transaction).toString());
        return new ResponseEntity<Transaction>(transactionService.MoneyTransfer(transaction),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> GetAllTransactions(){
        List<Transaction> TransactionsList = transactionService.AllTransactions();
        if(TransactionsList.isEmpty()){
            logger.warn("No transactions made till now");
            return new ResponseEntity<String>("No Transactions", HttpStatus.CONFLICT);

        }
        else{
            logger.info("All Transactions :" + TransactionsList.toString());
            return new ResponseEntity<List<Transaction>>(TransactionsList,HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> GetAllUserTransactions(@PathVariable String userName){
        User user = userRepository.findByUserName(userName);
        if(user != null){
            String UserphoneNumber = user.getPhoneNumber();
            List<Transaction>AllUsertransactions = transactionService.GetUserTransactions(UserphoneNumber);
            if(AllUsertransactions.isEmpty()){
                logger.warn("No transactions");
                return new ResponseEntity<String>("No Transactions",HttpStatus.ACCEPTED);
            }
            else{
                logger.info("All Transactions of" + userName + AllUsertransactions.toString() );
                return new ResponseEntity<List<Transaction>>(AllUsertransactions,HttpStatus.ACCEPTED);
            }
        }
        else {
            logger.warn("User NOT FOUND");
            return new ResponseEntity<String>("User Not Found",HttpStatus.CONFLICT);
        }
    }
}

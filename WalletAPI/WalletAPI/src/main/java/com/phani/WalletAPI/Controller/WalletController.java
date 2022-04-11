package com.phani.WalletAPI.Controller;

import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Dto.Response;
import com.phani.WalletAPI.Model.User;
import com.phani.WalletAPI.Model.Wallet;
import com.phani.WalletAPI.Service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallets")
public class WalletController {

    private static Logger logger = LogManager.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;



    @PostMapping
    public Response CreateWallet(@RequestBody Wallet wallet){
        Response response = new Response();
        response.setInfo("Creating Wallet for ExistingUser ");
        User user = userRepository.findByPhoneNumber(wallet.getPhoneNumber());
        if(user != null){
            Wallet newwallet = walletService.CreateUserWallet(wallet);
            user.setActive("true");
            userRepository.save(user);
            response.setHttpStatus(HttpStatus.OK);
            response.setData(wallet.toString());
            logger.info("Creating Wallet for ExistingUser " + user.getUserName()  + ": " + response.getData());
        }
        else{
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("First create user(user not found)");
            logger.info(response.getData());
        }

     return response;
    }




    @GetMapping
    public  Response GetAllUserWallets(){
        List<Wallet> walletslist = this.walletService.GeTAllWalletDetails();
        Response response = new Response();
        response.setInfo("Wallets");
        if(walletslist != null){
            response.setHttpStatus(HttpStatus.OK);
            response.setData(walletslist.toString());
        }
        else {
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("No wallets Present");
        }
        logger.info("All UserWallets :" + response.getData());
        return response;
    }




    @GetMapping("/{phoneNumber}")
    public Response getUserwallet(@PathVariable String phoneNumber){
        Wallet getWallet = walletService.GetWalletDetailsById(phoneNumber);
        Response response = new Response();
        response.setInfo("Getting WalletDetails");
        if(getWallet != null){
            response.setHttpStatus(HttpStatus.OK);
            response.setData(getWallet.toString());
            logger.info("Getting WalletDetails of" + (userRepository.findByPhoneNumber(phoneNumber)).getUserName() + " : " + response.getData());
        }
        else{
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("No wallet with phoneNumber :" + phoneNumber);
            logger.warn(response.getData());
        }
        return  response;
    }




    @DeleteMapping("/{phoneNumber}")
    public Response deleteWallet(@PathVariable String phoneNumber){
        Response response = new Response();
        response.setInfo(" Deleting the wallet");

        User user = userRepository.findByPhoneNumber(phoneNumber);
        Wallet  existed =   walletService.DeleteWalletDetailsById(phoneNumber);
        if(user != null && existed != null){

            user.setActive("false");
            userRepository.save(user);
            response.setHttpStatus(HttpStatus.OK);
            response.setData("Deleted Wallet of :" + phoneNumber);
            logger.info("Deleting WalletDetails of" + (userRepository.findByPhoneNumber(phoneNumber)).getUserName() + " : " + response.getData());
        }
        else{
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("No wallet existed with phonenumber :" + phoneNumber);
            logger.warn(response.getData());
        }
        return response;
    }
}

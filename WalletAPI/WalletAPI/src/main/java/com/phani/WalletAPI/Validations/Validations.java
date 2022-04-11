package com.phani.WalletAPI.Validations;


import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Validations {

    @Autowired
    private UserRepository userRepository;

    public boolean checkAmount(double amount) {
        if(amount < 0)
            return false;
        else
            return true;
    }

    public boolean CheckUser(User user){
        List<User> AllUsers = userRepository.findAll();
        for(User ExistingUser : AllUsers){
            if(ExistingUser.getUserName().equals(user.getUserName()) || ExistingUser.getPhoneNumber().equals(user.getPhoneNumber()) || ExistingUser.getEmailId().equals(user.getEmailId()))
            {
                return false;
            }
        }
        return true;
    }

}

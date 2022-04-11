package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User AddUserDetails(User user){

        return  userRepository.save(user);
    }


    public List<User> GetAllUserDetails(){
        return userRepository.findAll();
    }



    public User GetUserDetailsById(String userName){

        return userRepository.findByUserName(userName);

    }



    public User DeleteUserDetailsById(String userName){

        User existed = userRepository.findByUserName(userName);
     if(existed != null){
        userRepository.deleteById(existed.getUserId());}
       return existed;
    }

}

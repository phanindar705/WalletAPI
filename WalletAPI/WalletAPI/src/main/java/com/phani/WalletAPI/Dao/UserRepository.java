package com.phani.WalletAPI.Dao;

import com.phani.WalletAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);

    User findByPhoneNumber(String phoneNumber);

}

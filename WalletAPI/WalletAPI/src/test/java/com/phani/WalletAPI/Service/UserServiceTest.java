package com.phani.WalletAPI.Service;

import com.phani.WalletAPI.Dao.UserRepository;
import com.phani.WalletAPI.Model.User;
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
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void addUserDetails() {
        User user = new User(1,"phani","phani","23456","phani@gmail","true");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(user, userService.AddUserDetails(user));
    }

    @Test
    void getAllUserDetails() {
        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(new User(1,"phani","phani","23456","phani@gmail","true")).collect(Collectors.toList()));
        Assertions.assertEquals(1,userService.GetAllUserDetails().size());
    }

    @Test
    void getUserDetailsById() {
        User user = new User(1,"phani","phani","23456","phani@gmail","true");
        Mockito.when(userRepository.findByUserName("phani")).thenReturn(user);
        Assertions.assertEquals(user,userService.GetUserDetailsById("phani"));
    }

    @Test
    void deleteUserDetailsById() {
        User user = new User(1,"phani","phani","23456","phani@gmail","true");
        Mockito.when(userRepository.findByUserName("phani")).thenReturn(user);
        userService.DeleteUserDetailsById("phani");
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(user.getUserId());
    }
}
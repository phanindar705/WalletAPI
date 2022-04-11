package com.phani.WalletAPI.Controller;

import com.phani.WalletAPI.Dto.Response;
import com.phani.WalletAPI.Model.User;
import com.phani.WalletAPI.Service.UserService;
import com.phani.WalletAPI.Validations.Validations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Validations validations;



    @PostMapping
    public Response CreateUser(@RequestBody User user){

        Response response = new Response();
        response.setInfo("Adding New User");

    if(validations.CheckUser(user)){
        User AddUser = this.userService.AddUserDetails(user);
        response.setHttpStatus(HttpStatus.CREATED);
        response.setData(AddUser.toString());
        logger.info("Creating Details of "  + user.getUserName()  + " :" +response.getData());
    }
    else {

        response.setHttpStatus(HttpStatus.CONFLICT);
        response.setData("User Already Exist");
        logger.warn(response.getData());

    }

    return response;
    }




    @GetMapping
    public  Response GetAllUsers(){
        List<User>Userslist = this.userService.GetAllUserDetails();
        Response response = new Response();
        response.setInfo("Requesting All Users List");
        if(Userslist != null){
            response.setHttpStatus(HttpStatus.OK);
            response.setData(Userslist.toString());
        }
        else {
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("No Users Present");
        }
        logger.info("AllUsers Details : " + response.getData());
        return response;
    }




    @GetMapping("/{userName}")
    public Response getUser(@PathVariable String userName){
        User getUser = userService.GetUserDetailsById(userName);
        Response response = new Response();
        response.setInfo("Getting UserDetails by UserName :" + userName);
        if(getUser != null){
            response.setHttpStatus(HttpStatus.OK);
            response.setData(getUser.toString());
            logger.info( "Getting UserDetails of" + userName + ":" + response.getData());
        }
        else{
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setData("Invalid UserName");
            logger.warn(response.getData());
        }

      return  response;
    }





    @DeleteMapping("/{userName}")
   public Response deleteUser(@PathVariable String userName){
       Response response = new Response();
       response.setInfo(" Deleting the User");
     User existed =   this.userService.DeleteUserDetailsById(userName);
     if(existed != null){
       response.setHttpStatus(HttpStatus.OK);
       response.setData("Deleted details of :" + userName);
         logger.info("Deleting UserDetails of " + userName + ":" + response.getData());
     }

     else{
         response.setHttpStatus(HttpStatus.NO_CONTENT);
         response.setData("No user existed with userName :" + userName);
         logger.warn(response.getData());
     }

       return response;
   }

}

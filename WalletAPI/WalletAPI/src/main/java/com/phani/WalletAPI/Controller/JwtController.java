package com.phani.WalletAPI.Controller;

import com.phani.WalletAPI.Dto.AuthRequest;
import com.phani.WalletAPI.Util.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {


    private static Logger logger = LogManager.getLogger(JwtController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String welcome() {
        return " !! Welcome to phani assignment !!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            } catch (Exception exception)
              {
                throw new Exception("Invalid Username/password");
              }
        logger.info("Jwt token generated :" + jwtUtil.generateToken(authRequest.getUsername()));
         return jwtUtil.generateToken(authRequest.getUsername());
    }
}

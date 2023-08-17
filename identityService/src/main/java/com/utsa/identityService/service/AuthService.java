package com.utsa.identityService.service;

import com.utsa.identityService.dto.AuthRequest;
import com.utsa.identityService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    Logger logger= LoggerFactory.getLogger(AuthService.class);
    @Autowired
    public UserRepository userRepository;

//    @Autowired
//private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    public boolean isPresentInDb(AuthRequest authRequest) {
        List<Integer> integers=userRepository.getUserByNameAndPasswd(authRequest.getUsername(), authRequest.getPassword());
        logger.info("Integers {} authrequest {}",integers, authRequest);
        return integers.size()>0;
    }
}

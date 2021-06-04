package com.tbtk.map.service;

import com.tbtk.map.model.User;
import com.tbtk.map.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(User user) {
        if (userRepository.findByName(user.getName()).size() == 1) {
            return -1L;
        }

        final String encryptedPassword = hashPassword(user.getPass());
        user.setPass(encryptedPassword);
        Long userId = userRepository.saveAndFlush(user).getId();
        return userId;
    }

    public Long confirmUser(User user) {
        final String encryptedPassword = hashPassword(user.getPass());
        user.setPass(encryptedPassword);
        if (userRepository.findByNameAndPass(user.getName(), user.getPass()).size() == 1) {
            return userRepository.findByNameAndPass(user.getName(), user.getPass()).get(0).getId();

        } else if (userRepository.findByName(user.getName()).size() == 1) {
            //if the user password wrong
            return -1L;

        } else {
            //if the user not exist
            return -2L;
        }
    }

    private String hashPassword(String password) {

        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Error";
        }
    }
}
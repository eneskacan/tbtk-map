package com.tbtk.map.controller;

import com.tbtk.map.model.User;
import com.tbtk.map.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Users")
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Create a new user.")
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        Long userId = userService.createUser(user);

        if (userId == -1L) {
            return new ResponseEntity(user.getName() + " already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(userId, HttpStatus.CREATED);
    }

    @ApiOperation("Confirm user")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity confirmUser(@RequestBody User user) {

        Long userId = userService.confirmUser(user);
        if (userId == -1L) {
            //return wrong password
            return new ResponseEntity("wrong password", HttpStatus.UNAUTHORIZED);

        } else if (userId == -2L) {
            //user not exist
            return new ResponseEntity("create user first ", HttpStatus.NOT_FOUND);

        } else {
            //confirm approved
            return new ResponseEntity(userId, HttpStatus.OK);
        }
    }
}


package com.dhanshree.Project.Controller;

import com.dhanshree.Project.DTO.UserDTO;
import com.dhanshree.Project.Entity.User;
import com.dhanshree.Project.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class Controller {


    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;
    @Autowired
    Controller(UserService s){
        userService=s;
    }

    @PostMapping(path="/register")
    public String saveUser(@RequestBody UserDTO userDTO, BindingResult theBindingResult,
                           HttpSession session, Model theModel){
        String userName = userDTO.getUserName();
        logger.info("Processing registration form for: " + userName);
        System.out.println("Processing registration form for: " + userName);
        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";//redirect to registration
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("webUser", new UserDTO());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return " ";//return form ????
        }

        userService.addUser(userDTO);

        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", userDTO);

        return "" ; //""register/registration-confirmation"; return confirmation and redirect to home page



    }

}

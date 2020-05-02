package com.example.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.service.UserService;

public class RegisterController {
	

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/register")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("/register");
        return modelAndView;
    }

    @PostMapping(value = "/api/register")
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {

        if (userService.findByUsername(user.getUserName()).isPresent()) { //checks if username is unique
            bindingResult.rejectValue("userName", "error","Username already being used!");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/api/register"); //redirects the view back
        } else {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.saveUser(user);

            modelAndView.addObject("success", "Account created!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/api/login"); //redirects back to login
        }
        return modelAndView;
    }

}

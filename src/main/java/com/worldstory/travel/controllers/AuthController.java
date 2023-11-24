package com.worldstory.travel.controllers;

import com.worldstory.travel.models.User;
import com.worldstory.travel.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login-admin")
    public String authLoginAdmin() {
        return "pages/admin/sign-in";
    }

    @PostMapping("/login-admin")
    public String handleAuthAdmin() {
        System.out.println("ok");
        return "redirect:/admin/";
    }

    @GetMapping("/login")
    public String authLogin() {
        return "pages/sign-in";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "pages/sign-up";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute(value = "user") @Valid User u, BindingResult bindingResult) {
        User existedUser = userService.findByEmail(u.getEmail());

        // is email existed
        if(existedUser != null) {
            bindingResult.rejectValue("email", "form.error.existed");
        }

        // is match password
        if(!u.getPassword().equals(u.getConfirm())) bindingResult.rejectValue("confirm", "form.error.match");

        // validate
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", u);
            return "pages/sign-up";
        }

        userService.saveOrUpdate(u);

        return "pages/sign-up";
    }


}

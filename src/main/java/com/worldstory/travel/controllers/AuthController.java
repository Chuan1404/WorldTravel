package com.worldstory.travel.controllers;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.User;
import com.worldstory.travel.services.RoleService;
import com.worldstory.travel.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login-admin")
    public String authLoginAdmin() {
        return "pages/admin/sign-in";
    }

    @GetMapping("/login")
    public String authLogin() {
        return "pages/sign-in";
    }

    @PostMapping("/login")
    public String handleAuthLogin(@RequestParam Map<String, String> map, HttpServletRequest request) {
        if (map.get("email").isEmpty() || map.get("password").isEmpty()) return "redirect:/auth/login?error";


        try {
            // Authenticate the user
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(map.get("email"), map.get("password"));
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        } catch (Exception exception) {
            return "redirect:/auth/login?error";
        }

        return "redirect:/";

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
        if (existedUser != null)
            bindingResult.rejectValue("email", "form.error.existed");

        // is match password
        if (!u.getPassword().equals(u.getConfirm()))
            bindingResult.rejectValue("confirm", "form.error.match");

        // validate
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", u);
            return "pages/sign-up";
        }
        u.setRoles(List.of(roleService.findByName(UserRole.CUSTOMER)));

        userService.saveOrUpdate(u);

        return "pages/sign-in";
    }


}

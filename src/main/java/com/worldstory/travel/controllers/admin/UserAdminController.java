package com.worldstory.travel.controllers.admin;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.Tour;
import com.worldstory.travel.models.User;
import com.worldstory.travel.services.RoleService;
import com.worldstory.travel.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("")
    public String index(@RequestParam Map<String, String> params, Model model) {
        Page<User> users = userService.findAll(params, false);
        System.out.println(users);
        model.addAttribute("users", users);
        return "pages/admin/user";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "pages/admin/user_add";
    }

    @PostMapping("/add")
    public String handleAddUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam(value = "uroles", required = false) List<String> roles) {
        if (!user.getPassword().equals(user.getConfirm()))
            bindingResult.rejectValue("confirm", "form.error.match");

        if(roles != null && roles.size() > 0)
            roles.forEach(roleStr -> user.getRoles().add(roleService.findByName(UserRole.valueOf(roleStr))));

        if(user.getRoles().size() == 0)
            bindingResult.rejectValue("roles", "form.error.checkbox");


        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "pages/admin/user_add";
        }

        userService.saveOrUpdate(user);

        return "redirect:/admin/user";
    }

    @GetMapping("/update/{id}")
    public String handleUpdateUser(Model model, @PathVariable(value = "id") String id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "pages/admin/user_add";
    }

    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable(value = "id") String id) {
        User user = userService.findById(id);

        userService.delete(user);
        return "redirect:/admin/user";
    }
}

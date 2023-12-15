package com.worldstory.travel.controllers;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.Role;
import com.worldstory.travel.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private RoleService roleService;

    @ModelAttribute("servletPath")
    public String getServletPath(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getServletPath();
    }

    @ModelAttribute("Integer")
    public Class getInteger() {
        return Integer.class;
    }

    @ModelAttribute("ADMIN")
    public Role getRoleAdmin() {return roleService.findByName(UserRole.ADMIN); }

    @ModelAttribute("ADVISOR")
    public Role getRoleAdvisor() {return roleService.findByName(UserRole.ADVISOR); }

    @ModelAttribute("CUSTOMER")
    public Role getRoleCustomer() {return roleService.findByName(UserRole.CUSTOMER); }
}

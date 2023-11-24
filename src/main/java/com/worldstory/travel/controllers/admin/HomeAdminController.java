package com.worldstory.travel.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class HomeAdminController {

    @GetMapping("/")
    public String index() {

        return "pages/admin/index";
    }
}

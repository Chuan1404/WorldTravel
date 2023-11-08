package com.worldstory.travel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Home {

    @GetMapping("/")
    public String index() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Chu", "An", 21));
        people.add(new Person("Nguyen", "Binh", 22));
        people.add(new Person("Hoang", "Cuong", 23));
        people.add(new Person("Le", "Dung", 24));

        return "pages/index";
    }
}

class Person {
    public static int count = 1;
    public int id;
    public String firstName;
    public String lastName;
    public int age;
    public Person(String f, String l, int a) {
        this.id = count++;
        this.firstName = f;
        this.lastName = l;
        this.age = a;
    }

}

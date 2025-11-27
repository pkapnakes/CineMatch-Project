package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {



    @GetMapping("/home")
    public String homePage(@RequestParam(required = false) String username, Model model) {


        model.addAttribute("username", username);
        return "home";
    }
}

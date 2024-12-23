package com.works.controllers;

import com.works.entities.Customer;
import com.works.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    final CustomerService customerService;

    @GetMapping("")
    public String login(){
        return "login";
    }

    // MethodArgumentNotValidException
    /*
    @PostMapping("login")
    public String loginPost(@Valid Customer customer, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            model.addAttribute("error", errors);
            return "login";
        }
        System.out.println(customer);
        return "login";
    }
     */

    @PostMapping("login")
    public String loginPost(@Valid Customer customer){
        boolean status = customerService.login(customer.getEmail(), customer.getPassword());
        System.out.println("Login Status : " + status);
        return "login";
    }

}

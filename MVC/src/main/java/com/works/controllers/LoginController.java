package com.works.controllers;

import com.works.entities.Customer;
import com.works.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {

    final CustomerService customerService;

    @GetMapping("")
    public String login(){
        return "login";
    }

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
        if(status) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("logout")
    public String logout(){
        customerService.logout();
        return "redirect:/";
    }

}

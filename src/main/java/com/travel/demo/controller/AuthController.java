package com.travel.demo.controller;

import com.travel.demo.model.User;
import com.travel.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

	  private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;

	    @Autowired
	    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	    }
     

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
    	System.out.println("user details are");
        return "login";
    }
//    @PostMapping("/user-login")
//    public String login(@RequestParam String email,
//                        @RequestParam String password,
//                        HttpSession session,
//                        Model model) {
//
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            model.addAttribute("error", "Email is not registered. Please sign up or try again.");
//            return "login";
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            model.addAttribute("error", "Incorrect password. Please try again.");
//            return "login";
//        }
//
//        session.setAttribute("user", user);
//        return "redirect:/booking";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account found with that email.");
            return "forgot-password";
        }

        // For now, just show success message. You can later send email with token.
        model.addAttribute("message", "A password reset link has been sent to your email (simulation).");
        return "forgot-password";
    }

}


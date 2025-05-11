package com.sagar.controller;

import com.sagar.model.User;
import com.sagar.repository.UserRepository;
import com.sagar.service.MailService;
import com.sagar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public HomeController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private final Map<String, String> otpStore = new HashMap<>(); // Store email-otp pairs temporarily

    @GetMapping()
    public String landingPage() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());  // Create new User if none exists
        }
        return "register";  // Create a register.html in templates directory
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes, Model model) {
        if(userService.existsByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("registerError", "Email already exists.");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/register";
        } else {
            String otp = "1234";
            otpStore.put(user.getEmail(), otp);
//            mailService.sendOtp(user.getEmail(), otp);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/otp-verification";
        }
    }

    @GetMapping("/otp-verification")
    public String otpVerification() {
        return "otp-verification";  // Create a otp-verification.html in templates directory
    }

    @PostMapping("/otp-verification")
    public String otpVerification(@ModelAttribute("user") User user,
                                  @RequestParam String otp,
                                  RedirectAttributes redirectAttributes) {
        if (otpStore.get(user.getEmail()).equals(otp)) {
            redirectAttributes.addFlashAttribute("registerSuccess", "OTP verified successfully.");
            System.out.println("OTP verified successfully.");
            userService.saveUser(user);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("otpError", "Invalid OTP.");
            redirectAttributes.addFlashAttribute("user", user);
            System.out.println("Invalid OTP.");
            return "redirect:/otp-verification";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/login-processing")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        System.out.println("Login request: " + email + " " + password);
        try {
            // Create the authentication token
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(email, password);

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authRequest);

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Login successful: " + authentication.getName());
            redirectAttributes.addFlashAttribute("loginSuccess", "Login successful.");
            return "redirect:/redirect";

        } catch (AuthenticationException ex) {
            System.out.println("Login failed: " + ex.getMessage());
            redirectAttributes.addFlashAttribute("loginError", "Invalid email or password.");
            return "redirect:/login";
        }
    }


    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {
        System.out.println("Redirecting to dashboard...");
        // Get the roles of the authenticated user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin";  // Redirect to admin dashboard
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                return "redirect:/user";  // Redirect to user home page
            }
        }

        // Default redirect if no role matches
        return "redirect:/";
    }




}

package com.sagar.service;

import com.sagar.model.CustomUserDetails;
import com.sagar.model.User;
import com.sagar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishlistService wishlistService;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setRole("USER");
        user.setActive(true);
        User savedUser = userRepository.save(user);

        cartService.createCart(savedUser.getId());
        wishlistService.createWishlist(savedUser.getId());
    }

    public User getLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof CustomUserDetails) {
        return ((CustomUserDetails) principal).getUser();
    }

    throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
}
}

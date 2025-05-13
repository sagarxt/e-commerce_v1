package com.sagar.service;

import com.sagar.model.Wishlist;
import com.sagar.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public void createWishlist(String userId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductIds(new ArrayList<String>());
        wishlistRepository.save(wishlist);
    }
}

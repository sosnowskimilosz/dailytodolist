package com.milo.dailytodolist.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean isOwnerOrAdmin(String objectOwner, User user){
        return isAdmin(user) || isOwner(objectOwner, user);
    }

    public boolean isOwner(String objectOwner, User user) {
        return user.getUsername().equalsIgnoreCase(objectOwner);
    }

    public boolean isAdmin(User user) {
        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
    }
}

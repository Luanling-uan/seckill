package com.sziit.user.service;

import com.sziit.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private static final Map<String, User> users = new HashMap<>();

    static {
        // 模拟用户数据
        users.put("testuser", new User("testuser", "testpassword"));
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
package com.cinematch.project.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, String> users = new HashMap<>();

    public UserService() {
        users.put("sokratis", "12345");
        users.put("hasan", "12345");
        users.put("alex", "12345");
        users.put("panos", "12345");
        users.put("xrisa", "12345");
    }

    public boolean validateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}

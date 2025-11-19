package com.devs.frutybot.data.auth;

import com.devs.frutybot.data.user.UserDto;
import java.util.HashMap;
import java.util.Map;

public class MockAuthRepository {
    private final Map<String, String> users = new HashMap<>();

    public MockAuthRepository() {
        users.put("admin", "1234");
        users.put("jose", "clave");
    }

    public UserDto login(String username, String password) {
        if (username == null || password == null) return null;
        String expected = users.get(username);
        if (expected != null && expected.equals(password)) {
            UserDto u = new UserDto();
            u.setUsername(username);
            u.setToken("fake-token-" + username);
            u.setFirstName(username.equals("admin") ? "Admin" : "Jose");
            u.setLastName(username.equals("admin") ? "Super" : "Perez");
            u.setFullName(u.getFirstName() + " " + u.getLastName());
            u.setEmail(username + "@example.com");
            u.setAvatarUrl("https://example.com/avatars/" + username + ".png"); // mock URL
            u.setRole(username.equals("admin") ? "admin" : "user");
            return u;
        }
        return null;
    }
}

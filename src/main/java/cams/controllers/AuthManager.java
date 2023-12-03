package cams.controllers;

import cams.models.CamsData;
import cams.models.user.User;

public class AuthManager extends Manager {
    public User login(String userId, String password)
            throws IllegalArgumentException {
        User user = CamsData.getInstance().getUser(userId);
        if (user == null || !user.verifyPassword(password))
            throw new IllegalArgumentException("Incorrect user ID or password");

        app.setCurrentUser(user);
        return app.getCurrentUser();
    }

    public void logout() {
        app.setCurrentUser(null);
    }

    public void changePassword(String userId, String oldPassword, String newPassword)
            throws IllegalArgumentException {
        login(userId, oldPassword).changePassword(oldPassword, newPassword);
    }
}

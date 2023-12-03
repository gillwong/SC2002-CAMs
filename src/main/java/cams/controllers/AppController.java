package cams.controllers;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

import cams.models.user.User;

public class AppController {
    private static AppController app;
    private final DateTimeFormatter formatter;
    private User currentUser;

    private AppController() {
        currentUser = null;
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static AppController getInstance() {
        if (app == null)
            app = new AppController();
        return app;
    }

    public static void close() {
        app = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    void setCurrentUser(User user) {
        currentUser = user;
    }

    public DateTimeFormatter getDateFormmatter() {
        return formatter;
    }
}

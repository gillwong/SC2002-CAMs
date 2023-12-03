package cams.models.user;

import cams.models.camp.CampObserver;

public abstract class User implements Comparable<User>, CampObserver {
    private final String id;
    private final String faculty;
    private String name;
    private String password;

    public User(String id, String name, String faculty) {
        this.id = id.toUpperCase();
        this.name = name;
        this.faculty = faculty;
        password = "password";
    }

    public String getId() {
        return id;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean verifyPassword(String password) {
        return password == this.password;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        boolean isVerified = verifyPassword(oldPassword);
        if (isVerified)
            this.password = newPassword;
        return isVerified;
    }

    @Override
    public int compareTo(User o) {
        return getName().compareTo(o.getName());
    }
}

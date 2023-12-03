package cams.controllers;

public abstract class Manager {
    protected final AppController app;

    public Manager() {
        app = AppController.getInstance();
    }
}

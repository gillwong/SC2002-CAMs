package cams.view.components.staff;

import cams.domain.Staff;
import cams.domain.StaffController;
import cams.user.AuthController;
import cams.view.DisplayController;
import cams.view.base.ActionableItem;
import cams.view.base.CommonElements;
import cams.view.base.ItemAction;
import cams.view.base.SelectionMenu;
import cams.view.components.auth.ChangePasswordForm;
import cams.view.components.auth.LogoutAlert;
import cams.view.components.camp.CreateCampForm;
import cams.view.components.camp.FilterCampMenu;

import java.util.Scanner;

public class StaffMenu extends SelectionMenu {
    public StaffMenu(Scanner scanner) {
        super(scanner);
        DisplayController displayController = DisplayController.getInstance();
        StaffController staffController = StaffController.getInstance();
        Staff currentUser = staffController.getCurrentStaff();

        if (currentUser == null) {
            throw new IllegalStateException("No staff has been assigned to the Staff Controller class");
        }

        setPrompt(CommonElements.getHeader("Staff")
                + "Welcome to CAMs, " + currentUser.getName() + "!\n");

        addItem(new ActionableItem("Change Password", new ItemAction() {
            public void execute() {
                displayController.setNextDisplay(new ChangePasswordForm(scanner));
            }
        }));

        addItem(new ActionableItem("Create Camp", new ItemAction() {
            public void execute() {
                displayController.setNextDisplay(new CreateCampForm(scanner));
            }
        }));

        addItem(new ActionableItem("View Created Camps", new ItemAction() {
            public void execute() {
                displayController.setNextDisplay(new StaffViewCampMenu(scanner));
            }
        }));

        addItem(new ActionableItem("View All Camps", new ItemAction() {
            public void execute() {
                displayController.setNextDisplay(new FilterCampMenu(scanner));
            }
        }));

        addItem(new ActionableItem("Logout", new ItemAction() {
            public void execute() {
                AuthController authController = AuthController.getInstance();
                authController.logout();
                displayController.setNextDisplay(new LogoutAlert(scanner));
            }
        }));
    }
}

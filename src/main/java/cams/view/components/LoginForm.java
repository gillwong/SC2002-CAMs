package cams.view.components;

import java.util.Scanner;

import cams.domain.Staff;
import cams.domain.Student;
import cams.user.AuthController;
import cams.user.User;
import cams.user.UserController;
import cams.view.DisplayController;
import cams.view.base.Form;
import cams.view.base.ItemAction;
import cams.view.base.TextBox;

/**
 * User login interface.
 * 
 * @author Gillbert Susilo Wong
 * @author Juan Frederick
 * @author Karl Devlin Chau
 * @author Pascalis Pandey
 * @author Trang Nguyen
 * @version 1.0
 * @since 2023-11-09
 */
public class LoginForm extends Form {
    /**
     * Class constructor specifying the scanner to be used to receive user input.
     * 
     * @param scanner scanner for this menu
     */
    public LoginForm(Scanner scanner) {
        super("Login to CAMS\n", scanner);

        addInput(new TextBox("User ID", scanner));
        addInput(new TextBox("Password", true, scanner));

        setAction(new ItemAction() {
            public void execute() {
                AuthController authController = AuthController.getInstance();
                DisplayController displayController = DisplayController.getInstance();

                String userIDInput = getValues().get("User ID");
                String passwordInput = getValues().get("Password");
                User user = authController.login(userIDInput, passwordInput);

                if (user != null) {
                    if (user instanceof Staff) {
                        displayController.setNextDisplay(new StaffMenu(scanner));
                    } else {
                        displayController.setNextDisplay(new StudentMenu(scanner));
                    }
                } else {
                    displayController.setNextDisplay(new LoginErrorAlert(new LoginForm(scanner), scanner));
                }
            }
        });
    }
}

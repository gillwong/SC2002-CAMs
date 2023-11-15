package cams.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserControllerTest {
    @BeforeAll
    static void initalizeControllers() {
        AuthController.getInstance();
        UserController.getInstance();
    }

    @Test
    @DisplayName("Adding a user with existing ID throws")
    void addingExistingUserIDThrows() {
        UserController userController = UserController.getInstance();
        userController.addUser(new User(
                "Test User A", "testa", "Testing Faculty", null));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.addUser(new User(
                    "Test User", "TesTa", "Testing Faculty", null));
        });
        assertEquals("User with the same userID already exists!", exception.getMessage());
    }

    @Test
    @DisplayName("Adding a user with unique ID updates the user table")
    void addingUserCorrectlyUpdatesTable() {
        UserController userController = UserController.getInstance();
        userController.addUser(new User(
                "Test User B", "testb", "Testing Faculty", null));
        userController.addUser(new User(
                "Test User C", "testc", "Testing Faculty", null));

        assertNotNull(userController.getUser("testB"));
        assertNotNull(userController.getUser("testC"));
    }

    @Test
    @DisplayName("Initial data is serialized correctly")
    void initialDataSerializedCorrectly() {
        UserController userController = UserController.getInstance();
        String[] initialStaffID = {
                "hukumar", "OURIN", "Upam", "anWiT", "aRVi"
        };
        String[] initialStudentID = {
                "yChern", "KOH1", "BR015", "ct113", "ycn019", "dl007", "doN84", "ELI34", "LE51", "sl22", "AkY013"
        };
        for (String staffID : initialStaffID) {
            assertNotNull(userController.getUser(staffID));
        }
        for (String studentID : initialStudentID) {
            assertNotNull(userController.getUser(studentID));
        }
    }

    @AfterAll
    static void closeControllers() {
        UserController.close();
        AuthController.close();
    }
}

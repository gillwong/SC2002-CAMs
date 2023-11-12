package cams.domain;

public class StaffController {
    private static StaffController staffController;
    private Staff staff;

    private StaffController() {
        staff = null;
    }

    private StaffController(Staff staff) {
        this.staff = staff;
    }

    public static StaffController getInstance() {
        if (staffController == null) {
            staffController = new StaffController();
        }
        return staffController;
    }

    public static StaffController getInstance(Staff staff) {
        if (staffController == null) {
            staffController = new StaffController();
        }
        staffController.setCurrentStaff(staff);
        return staffController;
    }

    public Staff getCurrenStaff() {
        return staff;
    }

    public void setCurrentStaff(Staff staff) {
        this.staff = staff;
    }
}
package cams.camp;

import cams.domain.Staff;
import cams.domain.Student;
import cams.serializer.CampSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code CampController} class manages camps and provides functionality
 * for creating, retrieving, updating, and deleting camps.
 *
 * @author Gillbert Susilo Wong
 * @author Juan Frederick
 * @author Karl Devlin Chau
 * @author Pascalis Pandey
 * @author Trang Nguyen
 * @version 1.0
 * @since 2023-11-09
 */
public class CampController {
    private static CampController campController;
    private final Map<String, Camp> campTable;
    private final String campPath;
    private Camp currentCamp;

    /**
     * Private constructor to ensure singleton pattern.
     */
    private CampController(String campPath) {
        campTable = new HashMap<>();
        this.campPath = campPath;
        initializeCampTable();
        currentCamp = null;
    }

    /**
     * Returns the singleton instance of the {@code CampController}.
     *
     * @return The {@code CampController} instance.
     */
    public static CampController getInstance() {
        if (campController == null) {
            campController = new CampController("camp_list.xlsx");
        }
        return campController;
    }

    /**
     * Returns the singleton instance of the {@code CampController}.
     *
     * @param campPath the path where csv file of camp list is found.
     * @return The {@code CampController} instance.
     */
    public static CampController getInstance(String campPath) {
        if (campController == null) {
            campController = new CampController(campPath);
        }
        return campController;
    }

    /**
     * Closes the current camp controller and serializes the current camp data to the camp list csv.
     */
    public static void close() {
        CampSerializer.serialize(campController.getCampTable(), campController.campPath);
        campController = null;
    }

    /**
     * Initializes the camp table by deserializing camp data from the camp list csv.
     */
    private void initializeCampTable() {
        List<Camp> camps = CampSerializer.deserialize(campPath);
        for (Camp camp : camps)
            createCamp(camp);
    }

    /**
     * Retrieves the whole camp table.
     *
     * @return The camp table {@code HashMap} containing all created camps
     */
    public Map<String, Camp> getCampTable() {
        return campTable;
    }

    /**
     * Creates a new camp and adds it to the camp table.
     *
     * @param campName             The name of the camp
     * @param location             The location of the camp
     * @param description          The description of the camp
     * @param startDate            The start date of the camp
     * @param endDate              The end date of the camp
     * @param registrationDeadline The registration deadline for the camp
     * @param totalSlots           The total available slots for the camp
     * @param isVisible            Indicates whether the camp is visible
     * @param userGroup            The user group associated with the camp
     * @param staffInCharge        the staff in charge of the camp
     */
    public void createCamp(
            String campName, String location, String description,
            LocalDate startDate, LocalDate endDate, LocalDate registrationDeadline,
            int totalSlots, boolean isVisible, String userGroup, Staff staffInCharge)
            throws IllegalArgumentException {
        Camp newCamp = new Camp(campName, location, description, startDate, endDate, registrationDeadline, totalSlots,
                isVisible, userGroup, staffInCharge);
        campTable.put(campName.toLowerCase(), newCamp);
        staffInCharge.addCamp(newCamp);
    }

    /**
     * Adds a new camp to the camp table.
     *
     * @param newCamp the new camp to be added
     */
    public void createCamp(Camp newCamp) {
        campTable.put(newCamp.getCampInfo().getCampName().toLowerCase(), newCamp);
        newCamp.getStaffInCharge().addCamp(newCamp);
    }

    /**
     * Retrieves an array of all camps in the camp table.
     *
     * @return An array of {@code Camp} objects.
     */
    public List<Camp> getAllCamps() {
        List<Camp> campList = new ArrayList<>();
        for (Map.Entry<String, Camp> camp : campTable.entrySet()) {
            campList.add(camp.getValue());
        }
        return campList;
    }

    /**
     * Retrieves a specific camp by name.
     *
     * @param name The name of the camp to retrieve.
     * @return The {@code Camp} object with the specified name, or {@code null} if
     * not found.
     */
    public Camp getCamp(String name) {
        return campTable.get(name.toLowerCase());
    }

    /**
     * Deletes a camp with the specified name from the camp table.
     *
     * @param name The name of the camp to delete.
     */
    public void deleteCamp(String name) throws RuntimeException {
        Camp camp = campTable.get(name.toLowerCase());
        if (camp == null) {
            throw new RuntimeException("No camp found with this name!");
        }
        if (camp.getAttendees().isEmpty()) {
            camp.getStaffInCharge().removeCamp(camp);
            campTable.remove(name.toLowerCase());
        } else
            throw new RuntimeException("Camp must have no attendees to be deleted!");
    }

    /**
     * Gets a performance report for all students in the camps.
     *
     * @param campName name of the camp to obtain the performance report from
     * @return a {@code HashMap} containing the performance report
     */
    public Map<Student, Integer> getPerformanceReport(String campName) {
        return new HashMap<>(
                getCamp(campName).getCommittee());
    }

    /**
     * Gets the attendance list for all students in the camps.
     *
     * @param campName name of the camp to obtain the attendance list from
     * @return a {@code Set} containing the attendance list.
     */
    public List<Student> getAttendanceList(String campName) {
        Camp camp = getCamp(campName);
        return camp.getAttendees();
    }

    /**
     * Gets the current camp of the camp controller.
     *
     * @return the current {@code Camp} set for the controller.
     */
    public Camp getCurrentCamp() {
        return currentCamp;
    }

    /**
     * Sets the current camp of the camp controller.
     *
     * @param camp the {@code Camp} to be set as the current camp of the controller.
     */
    public void setCurrentCamp(Camp camp) {
        currentCamp = camp;
    }
}
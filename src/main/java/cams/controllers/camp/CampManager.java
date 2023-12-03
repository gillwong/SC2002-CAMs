package cams.controllers.camp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import cams.controllers.Manager;
import cams.models.CamsData;
import cams.models.camp.Camp;
import cams.models.user.Staff;

public class CampManager extends Manager {
    public Camp createCamp(
            String name, String location, String description, String startDate,
            String endDate, String registrationDeadline, String totalSlots,
            String committeeSlots, String isVisible, String userGroup)
            throws IllegalArgumentException, DateTimeParseException, NumberFormatException {
        DateTimeFormatter formatter = app.getDateFormmatter();
        Camp newCamp = new Camp(
                name, location, description,
                LocalDate.parse(startDate, formatter),
                LocalDate.parse(endDate, formatter),
                LocalDate.parse(registrationDeadline, formatter),
                Integer.parseInt(totalSlots),
                Integer.parseInt(committeeSlots),
                !isVisible.equalsIgnoreCase("n"),
                userGroup, (Staff) app.getCurrentUser());
        CamsData.getInstance().addCamp(newCamp);
        return newCamp;
    }

    public void deleteCamp(Camp camp) throws IllegalStateException {
        if (!camp.isEmpty())
            throw new IllegalStateException("Camp must be empty");
        CamsData.getInstance().removeCamp(camp);
    }
}

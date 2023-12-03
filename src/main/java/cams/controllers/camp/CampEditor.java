package cams.controllers.camp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import cams.controllers.Manager;
import cams.models.camp.Camp;

public class CampEditor extends Manager {
    private final Camp camp;

    public CampEditor(Camp camp) {
        this.camp = Objects.requireNonNull(camp, "Camp must not be null");
    }

    public Camp getCamp() {
        return camp;
    }

    public CampEditor editName(String name) {
        camp.setName(name);
        return this;
    }

    public CampEditor editDescription(String description) {
        camp.setDescription(description);
        return this;
    }

    public CampEditor editLocation(String location) {
        camp.setLocation(location);
        return this;
    }

    public CampEditor editDates(String startDate, String endDate, String registrationDeadline)
            throws IllegalArgumentException, DateTimeParseException {
        DateTimeFormatter formatter = app.getDateFormmatter();
        camp.setDates(
                LocalDate.parse(startDate, formatter),
                LocalDate.parse(endDate, formatter),
                LocalDate.parse(registrationDeadline, formatter));

        return this;
    }

    public CampEditor editTotalSlots(String totalSlots)
            throws IllegalArgumentException, NumberFormatException {
        camp.setTotalSlots(Integer.parseInt(totalSlots));
        return this;
    }

    public CampEditor toggleVisibility() {
        camp.setVisibility(!camp.isVisible());
        return this;
    }
}

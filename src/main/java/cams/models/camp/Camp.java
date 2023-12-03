package cams.models.camp;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cams.models.CamsData;
import cams.models.repliable.Enquiry;
import cams.models.repliable.RepliableEvent;
import cams.models.repliable.RepliableObserver;
import cams.models.user.Staff;
import cams.models.user.Student;

public class Camp implements Comparable<Camp>, RepliableObserver {
    private final Set<Student> attendees;
    private final Map<Student, Integer> committee;
    private final Set<Enquiry> enquiries;
    private final Set<Student> blacklist;
    private final Staff staff;
    private String name;
    private String location;
    private String description;
    private String userGroup;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationDeadline;
    private int totalSlots;
    private int committeeSlots;
    private boolean visibility;

    public Camp(
            String name, String location, String description,
            LocalDate startDate, LocalDate endDate, LocalDate registrationDeadline,
            int totalSlots, int committeeSlots, boolean isVisible,
            String userGroup, Staff staff) throws IllegalArgumentException {
        if (name.isBlank())
            throw new IllegalArgumentException("Name must not be blank");
        this.name = name;
        this.location = Objects.requireNonNullElse(location, "");
        this.description = Objects.requireNonNullElse(description, "");
        this.userGroup = userGroup.isBlank() ? "NTU" : userGroup.toUpperCase();

        if (Objects.requireNonNull(registrationDeadline, "Registration deadline must not be null")
                .isAfter(Objects.requireNonNull(startDate, "Start date must not be null")))
            throw new IllegalArgumentException("Registration deadline must not be after start date");
        if (startDate.isAfter(Objects.requireNonNull(endDate, "End date must not be null")))
            throw new IllegalArgumentException("Start date must not be after end date");
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;

        this.totalSlots = totalSlots;
        if (committeeSlots > 10)
            throw new IllegalArgumentException("Committee slots must be 10 or below");
        if (totalSlots > committeeSlots)
            throw new IllegalArgumentException("Committee slots must not exceed total slots");
        this.committeeSlots = committeeSlots;
        visibility = isVisible;

        this.staff = Objects.requireNonNull(staff, "Staff must not be null");
        attendees = new HashSet<>();
        committee = new HashMap<>();
        enquiries = new HashSet<>();
        blacklist = new HashSet<>();
        staff.update(CampEvent.COMMITTEE, this);
    }

    public void addAttendee(Student student)
            throws IllegalArgumentException, IllegalStateException {
        if (blacklist.contains(student))
            throw new IllegalArgumentException("Student must not have withdrawn from this camp previously");
        if (isFull())
            throw new IllegalStateException("Camp is full");
        attendees.add(student);
        student.update(CampEvent.REGISTER, this);
    }

    public void removeAttendee(Student student) {
        attendees.remove(student);
        blacklist.add(student);
        student.update(CampEvent.WITHDRAW, this);
    }

    public void addCommittee(Student student)
            throws IllegalArgumentException, IllegalStateException {
        if (student.isCommittee())
            throw new IllegalArgumentException("Student must not already be a committee");
        if (isFull())
            throw new IllegalStateException("Camp is full");
        if (isFullCommittee())
            throw new IllegalStateException("No more committee slots");
        committee.put(student, 0);
        student.update(CampEvent.COMMITTEE, this);
    }

    public Staff getStaff() {
        return staff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;

        // Updates entry in campTable
        CamsData observer = CamsData.getInstance();
        observer.update(CampEvent.NAME_CHANGE, oldName);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = Objects.requireNonNullElse(location, "");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        if (userGroup.isBlank()) {
            this.userGroup = "NTU";
            return;
        }
        this.userGroup = userGroup.toUpperCase();
        for (Student attendee : attendees)
            if (!attendee.getFaculty().equalsIgnoreCase(userGroup))
                attendee.update(CampEvent.WITHDRAW, this);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setDates(LocalDate startDate, LocalDate endDate, LocalDate registrationDeadline)
            throws IllegalArgumentException {
        if (Objects.requireNonNull(registrationDeadline, "Registration deadline must not be null")
                .isAfter(Objects.requireNonNull(startDate, "Start date must not be null")))
            throw new IllegalArgumentException("Registration deadline must not be after start date");
        if (startDate.isAfter(Objects.requireNonNull(endDate, "End date must not be null")))
            throw new IllegalArgumentException("Start date must not be after end date");

        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) throws IllegalArgumentException {
        int tempSlots = this.totalSlots;
        this.totalSlots = totalSlots;
        if (isFull() || isFullCommittee()) {
            this.totalSlots = tempSlots;
            throw new IllegalArgumentException(
                    "The new total slots must not be smaller than the current attendees & committee size");
        }
    }

    public int getCommitteeSlots() {
        return committeeSlots;
    }

    public void setCommitteeSlots(int committeeSlots) throws IllegalArgumentException {
        int tempSlots = this.committeeSlots;
        this.committeeSlots = committeeSlots;
        if (isFull() || isFullCommittee()) {
            this.committeeSlots = tempSlots;
            throw new IllegalArgumentException(
                    "The new committee slots must not be smaller than the current attendees & committee size");
        }
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean isVisible) {
        visibility = isVisible;
    }

    public boolean isFull() {
        return attendees.size() + committee.size() >= totalSlots;
    }

    public boolean isFullCommittee() {
        return committee.size() >= committeeSlots;
    }

    public boolean isEmpty() {
        return attendees.size() + committee.size() == 0;
    }

    public void delete() {
        for (Student attendee : attendees)
            attendee.update(CampEvent.DELETE, this);
        for (Student committeeMember : committee.keySet())
            committeeMember.update(CampEvent.DELETE, this);
        staff.update(CampEvent.DELETE, this);
    }

    @Override
    public int compareTo(Camp o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public void update(RepliableEvent event, Object payload) {
        if (event == RepliableEvent.DELETE_ENQUIRY)
            enquiries.remove((Enquiry) payload);
        if (event == RepliableEvent.DELETE_SUGGESTION)
            ;
        if (event == RepliableEvent.ADD_POINT) {
            Student key = (Student) payload;
            committee.put(key, committee.get(key) + 1);
        }
    }
}

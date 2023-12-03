package cams.model.user;

import java.util.HashSet;
import java.util.Set;

import cams.model.camp.Camp;
import cams.model.camp.CampEvent;

public class Student extends User {
    private final Set<Camp> campsRegistered;
    private Camp committeeFor;

    public Student(String id, String name, String faculty) {
        super(id, name, faculty);
        campsRegistered = new HashSet<>();
        committeeFor = null;
    }

    public boolean isCommittee() {
        return committeeFor != null;
    }

    public Camp getCommitteeCamp() {
        return committeeFor;
    }

    @Override
    public void update(CampEvent event, Object payload) {
        if (event == CampEvent.REGISTER)
            campsRegistered.add((Camp) payload);
        if (event == CampEvent.WITHDRAW)
            campsRegistered.remove((Camp) payload);
        if (event == CampEvent.COMMITTEE)
            committeeFor = (Camp) payload;
    }
}

package cams.model.user;

import java.util.HashSet;
import java.util.Set;

import cams.model.camp.Camp;
import cams.model.camp.CampEvent;

public class Staff extends User {
    private final Set<Camp> campsCreated;

    public Staff(String id, String name, String faculty) {
        super(id, name, faculty);
        campsCreated = new HashSet<>();
    }

    @Override
    public void update(CampEvent event, Object payload) {
        if (event == CampEvent.CREATE)
            campsCreated.add((Camp) payload);
    }
}

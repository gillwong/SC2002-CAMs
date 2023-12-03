package cams.models.repliable;

import cams.models.camp.Camp;
import cams.models.user.Student;

public class Suggestion extends Repliable {
    private boolean approved;

    public Suggestion(String content, Camp camp, Student author)
            throws IllegalArgumentException {
        super(content, camp, author);
        if (author.getCommitteeCamp() != camp)
            throw new IllegalArgumentException("Author must be a committee for the camp");
        approved = false;
    }

    public boolean isApproved() {
        return approved;
    }

    public void approve() {
        approved = true;
        camp.update(RepliableEvent.ADD_POINT, author);
    }

    @Override
    public void delete() {
        camp.update(RepliableEvent.DELETE_SUGGESTION, this);
    }
}

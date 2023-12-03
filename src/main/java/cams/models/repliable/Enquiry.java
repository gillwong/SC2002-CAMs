package cams.model.repliable;

import cams.model.camp.Camp;
import cams.model.user.Student;

public class Enquiry extends Repliable {
    private String reply;

    public Enquiry(String content, Camp camp, Student author) {
        super(content, camp, author);
        reply = "";
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setReply(String reply, Student author) {
        camp.update(RepliableEvent.ADD_POINT, author);
    }

    @Override
    public void delete() {
        camp.update(RepliableEvent.DELETE_ENQUIRY, this);
    }
}

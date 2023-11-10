package domain;

import java.util.Set;
import java.util.HashSet;
import camp.Camp;
import repliable.Enquiry;

import user.User;

public class Student extends User {
    private Camp committeeFor;
    private Set<Camp> campsRegistered;
    private Set<Enquiry> enquiries;

    public Student(String userID, String faculty) {
        super(userID, faculty);
    }

    public Set<Camp> getCamps() {
        return new HashSet<Camp>(campsRegistered);
    }

    public void addCamp(Camp camp) {
        // TODO
    }

    public Set<Enquiry> getEnquiries() {
        return new HashSet<Enquiry>(enquiries);
    }

    public void addEnquiry(Enquiry enquiry) {
        // TODO
    }

    public Camp getCommitteeFor() {
        return committeeFor;
    }

    public void setCommitteeFor(Camp camp) {
        committeeFor = camp;
    }
}

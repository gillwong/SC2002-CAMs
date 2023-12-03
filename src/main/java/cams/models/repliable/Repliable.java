package cams.models.repliable;

import java.util.Objects;

import cams.models.camp.Camp;
import cams.models.camp.CampEvent;
import cams.models.camp.CampObserver;
import cams.models.user.Student;

public abstract class Repliable implements Comparable<Repliable>, CampObserver {
    private String content;
    protected final Camp camp;
    protected final Student author;

    public Repliable(String content, Camp camp, Student author) {
        this.content = Objects.requireNonNullElse(content, "");
        this.camp = Objects.requireNonNull(camp, "Camp must not be null");
        this.author = Objects.requireNonNull(author, "Author must not be null");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public abstract void delete();

    @Override
    public int compareTo(Repliable o) {
        return getContent().compareTo(o.getContent());
    }

    @Override
    public void update(CampEvent event, Object payload) {
        if (event == CampEvent.DELETE)
            delete();
    }
}

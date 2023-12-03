package cams.model.camp;

public interface CampObserver {
    public void update(CampEvent event, Object payload);
}

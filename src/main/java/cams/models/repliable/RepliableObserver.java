package cams.models.repliable;

public interface RepliableObserver {
    public void update(RepliableEvent event, Object payload);
}

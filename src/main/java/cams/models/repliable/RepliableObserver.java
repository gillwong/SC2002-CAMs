package cams.model.repliable;

public interface RepliableObserver {
    public void update(RepliableEvent event, Object payload);
}

package ru.course2.account;

public class Invoker2 implements Saveable2 {
    private Memento2 snapshot;
    public Memento2 getSnapshot() {
        return snapshot;
    }
    public void saveState(Memento2 memento) {
        snapshot=memento;
    }
    public Memento2 restoreState() {
        return snapshot;
    }
    public Memento2 undo(){
        return restoreState();
    };
    public void save(Memento2 memento){
        saveState(memento);
    }
    public Memento2 restore(){
        return restoreState();
    }
    @Override
    public String toString() {
        return "Invoker2{" +
                "snapshot=" + snapshot +
                '}';
    }
}

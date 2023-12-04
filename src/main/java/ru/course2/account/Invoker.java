package ru.course2.account;

public class Invoker implements Saveable {
    private Memento snapshot;

    public Memento getSnapshot() {
        return snapshot;
    }

    public void saveState(Memento memento) {
        snapshot=memento;
    }
    public Memento restoreState() {
        return snapshot;
    }
    public void save(Memento memento){
        saveState(memento);
    }
    public Memento restore(){
        return restoreState();
    }
    public Memento undo(){
        return restoreState();
    };
    @Override
    public String toString() {
        return "Invoker{" +
                "snapshot=" + snapshot +
                '}';
    }
}

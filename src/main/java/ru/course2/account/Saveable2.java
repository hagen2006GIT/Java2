package ru.course2.account;

public interface Saveable2 {
    public void save(Memento2 memento);
    public Memento2 restore();
    public Memento2 undo();
}

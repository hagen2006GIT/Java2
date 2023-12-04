package ru.course2.account;

public interface Saveable {
    public void save(Memento memento);
    public Memento undo();
    public Memento restore();
}

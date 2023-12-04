package ru.course2.account;

public class Memento {
    private final StoredObject state;
    public Memento(StoredObject state) {
        this.state = state;
    }

    public StoredObject getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Memento{" +
                "state=" + state +
                '}';
    }
}


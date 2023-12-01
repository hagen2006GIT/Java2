package ru.course2.account;

public class Memento {
    private final Account state;
    private final String name;

    public Memento(Account state) {
        this.state = state;
        this.name = state.getName();
    }
    public String getName() {
        return name;
    }
    public Account getState() {
        return state;
    }
}

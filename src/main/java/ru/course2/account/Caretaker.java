package ru.course2.account;

import java.util.Stack;

public class Caretaker implements Saveable{
    private final Stack<Memento> mementoStack=new Stack<>();
    public void saveState(Memento memento) {
        mementoStack.push(memento);
    }
    public Memento restoreState() throws IllegalAccessError {
        if (mementoStack.empty()) {
            System.out.println("There were no changes");
            throw new IllegalAccessError();
        }
        return mementoStack.pop();
    }
    public void save(Memento memento){
        saveState(memento);
    }
    public Memento undo() {
        return restoreState();
    }
    public Memento restore() {
        return restoreState();
    }
}


package ru.course2.account;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker implements Saveable2 {
    private final Deque<Memento2> mementoStack=new ArrayDeque<>();
    public void saveState(Memento2 memento) {
        mementoStack.push(memento);
    }
    public Memento2 restoreState() throws IllegalAccessError {
        if (mementoStack.isEmpty()) {
            System.out.println("There were no changes");
            throw new IllegalAccessError();
        }
        return mementoStack.pop();
    }
    public void save(Memento2 memento){
        saveState(memento);
    }
    @Override
    public Memento2 restore() {
        return null;
    }
    public Memento2 undo() {
        return restoreState();
    }
}


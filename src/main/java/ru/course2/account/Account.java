package ru.course2.account;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Account implements Actions{
    Deque<Actions> action=new ArrayDeque<>();
    private Caretaker caretaker=new Caretaker();
    private String name;
    private Map<Currency,Integer> lstCur = new HashMap<>();
    @Override
    public String toString() {
        return "Account{"
                + name + '\''
                + ", " + lstCur +
                '}';
    }
    public String getName() {
        return name;
    }
    public Map<Currency,Integer> getLstCur() {
        return lstCur;
    }
    public void setName(String name) {
        this.name=name;
        action.push(()->this.name=name);
//        action.push(()->save());
        save();
    }
    public void setCurrencyMap(Currency cur, Integer saldo) throws IndexOutOfBoundsException {
        if(saldo<0){
            System.out.println("Parameter 'Saldo' may be positive only");
            throw new IndexOutOfBoundsException();
        }
        this.lstCur.put(cur,saldo);
        action.push(()->this.lstCur.put(cur,saldo));
        save();
    }
    public Account(String name) {
        this.name=name;
//        action.push(()->save());
        action.push(()->this.name=name);
        save();
    }
    public Memento2 save(String state) {
        Memento2 mem=new Memento2(state);
        mem.setName(name);
        mem.setLstCur(lstCur);
        return mem;
    }
    public void save() {
        Memento2 mem=new Memento2("");
        mem.setName(name);
        mem.setLstCur(lstCur);
        caretaker.save(mem);
    }
    public void restoreFromSave(Memento2 memento) {
        this.name=memento.getName();
        this.lstCur=memento.getLstCur();
    }
    public void undo() {
        Memento2 mem=caretaker.undo();
        this.name=mem.getName();
        this.lstCur=mem.getLstCur();
        System.out.println("a'm here");
    }
}
enum Currency {
    RUR,EUR,USD;
}
@FunctionalInterface
interface Actions {
    public void undo();
}

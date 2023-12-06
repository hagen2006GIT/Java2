package ru.course2.account;

import java.util.HashMap;
import java.util.Map;

public class Account {
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
    }
    public void setCurrencyMap(Currency cur, Integer saldo) throws IndexOutOfBoundsException {
        if(saldo<0){
            System.out.println("Parameter 'Saldo' may be positive only");
            throw new IndexOutOfBoundsException();
        }
        this.lstCur.put(cur,saldo);
    }
    public Account(String name) {
        this.name=name;
    }
    public Memento2 save(String state) {
        Memento2 mem=new Memento2(state);
        mem.setName(name);
        mem.setLstCur(lstCur);
        return mem;
    }
    public void restoreFromSave(Memento2 memento) {
        this.name=memento.getName();
        this.lstCur=memento.getLstCur();
    }
}
enum Currency {
    RUR,EUR,USD;
}

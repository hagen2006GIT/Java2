package ru.course2.account;

import java.util.HashMap;
import java.util.Map;

public class Memento2 {
    private final String state;
    private String name;
    private Map<Currency,Integer> lstCur=new HashMap<>();
    public Memento2(String state) {
        this.state=state;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setLstCur(Map<Currency, Integer> lstCur) {
        this.lstCur.putAll(lstCur);
    }
    public String getName() {
        return name;
    }
    public Map<Currency, Integer> getLstCur() {
        return lstCur;
    }
    public String getState() {
        return state;
    }
    @Override
    public String toString() {
        return "Memento2{" +
                "state=" + state +
                ", name=" + name +
                ", lstCur=" + lstCur +
                '}';
    }
}

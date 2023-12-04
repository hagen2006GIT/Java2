package ru.course2.account;

import java.util.ArrayList;
import java.util.List;

public class StoredObject {
    private String name; //храним имя
    private List<CurrencyStorage> lstCur=new ArrayList<>();

    public StoredObject(String name,List<CurrencyStorage> lstCur) {
        this.lstCur = lstCur;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public List<CurrencyStorage> getLstCur() {
        return lstCur;
    }
    @Override
    public String toString() {
        return "StoredObject{" +
                "name='" + name + '\'' +
                ", lstCur=" + lstCur +
                '}';
    }
}

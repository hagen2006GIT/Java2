package ru.course2.account;

import java.util.HashMap;
import java.util.Map;

public class Account implements Cloneable {
    private String name;
    private Map<Currency,Integer> lstCur = new HashMap<>();
    @Override
    protected Account clone() throws CloneNotSupportedException {
        Account account=(Account) super.clone();
        account.lstCur=new HashMap<>();
        account.lstCur.putAll(this.lstCur);
        return account;
    }
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
    public void setName(String name) throws CloneNotSupportedException{
        this.name=name;
    }
    public void setCurrencyMap(Currency cur, Integer saldo) throws IndexOutOfBoundsException, CloneNotSupportedException {
        if(saldo<0){
            System.out.println("Parameter 'Saldo' may be positive only");
            throw new IndexOutOfBoundsException();
        }
        this.lstCur.put(cur,saldo);
    }
    public Account(String name) throws CloneNotSupportedException{
        this.name=name;
    }
    public Memento2 save(String state) {
        Memento2 mem=new Memento2(state);
        mem.setName(name);
        mem.setLstCur(lstCur);
        return mem;
    }
    public void restoreFromSave(Memento2 memento) throws CloneNotSupportedException {
        this.name=memento.getName();
        this.lstCur=memento.getLstCur();
    }
}
enum Currency {
    RUR,EUR,USD;
}

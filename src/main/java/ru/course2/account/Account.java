package ru.course2.account;

import java.util.ArrayList;
import java.util.List;

public class Account implements Cloneable{
    private StoredObject state;
    private String name;
    private List<CurrencyStorage> listCur=new ArrayList<>();

    public List<CurrencyStorage> getListCur() {
        return listCur;
    }

    public CurrencyStorage findCurInList(Currency cur,List<CurrencyStorage> lstCur) {
        for (CurrencyStorage cs:this.listCur) {
            if(cs.getCur().equals(cur)) {
                return cs;
            }
        }
        return null;
    }
    @Override
    protected Account clone() throws CloneNotSupportedException {
        Account account=(Account) super.clone();
        account.listCur=new ArrayList<>();
        CurrencyStorage tmp=new CurrencyStorage();
        for (CurrencyStorage cs:this.listCur) {
            tmp=cs.clone();
            account.listCur.add(tmp);
        }
        return account;
    }
    public Memento saveState() {
        return new Memento(state);
    }
    public void restoreState(Memento memento) {
        state=memento.getState();
    }

    public void restoreSaved(Memento memento) throws CloneNotSupportedException {
        this.name=memento.getState().getName();
        this.listCur=memento.getState().getLstCur();
        setState();
    }
    public void setState() throws CloneNotSupportedException {
        this.state=new StoredObject(this.clone().getName(),this.clone().listCur);
    }
    public StoredObject getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Account{"
                + name + '\''
                + ", " + listCur +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws CloneNotSupportedException{
        this.name=name;
        setState();
    }
    public void setCurrencyList(Currency cur, Integer saldo) throws IndexOutOfBoundsException, CloneNotSupportedException {
        CurrencyStorage cs=new CurrencyStorage();
        if(saldo<0){
            System.out.println("Parameter 'Saldo' may be positive only");
            throw new IndexOutOfBoundsException();
        }
        cs=findCurInList(cur,this.listCur);
        if(cs!=null) {
            cs.setSaldo(saldo);
        } else {
            this.listCur.add(new CurrencyStorage(cur,saldo));
        }
        setState();
    }
    public Account(String name) throws CloneNotSupportedException{
        this.name=name;
        setState();
    }
}
enum Currency {
    RUR,EUR,USD;
}

class CurrencyStorage implements Cloneable{
    private Currency cur;
    private Integer saldo;

    public Currency getCur() {
        return cur;
    }

    public void setSaldo(Integer saldo) {
        this.saldo=saldo;
    }

    @Override
    public String toString() {
        return "{"+cur+";"+saldo+"}";
    }
    public CurrencyStorage() {
    }
    @Override
    public CurrencyStorage clone() throws CloneNotSupportedException {
        return (CurrencyStorage) super.clone();
    }
    public CurrencyStorage(Currency cur, Integer saldo) {
        this.cur=cur;
        this.saldo=saldo;
    }
}

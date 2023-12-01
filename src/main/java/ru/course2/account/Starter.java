package ru.course2.account;

public class Starter {
    public static void main(String[] args) throws IndexOutOfBoundsException,CloneNotSupportedException {
//Часть 1
        Account acc1=new Account("Пушкин Александр Сергеевич");
        acc1.editCurrencyList(Currency.valueOf("RUR"),100);
        acc1.editCurrencyList(Currency.valueOf("EUR"),200);
        System.out.println(acc1.getListCur());
        acc1.editCurrencyList(Currency.valueOf("EUR"),300);
        System.out.println(acc1.getListCur());
        System.out.println("-------------------");

//Часть 2
        Account acc2=new Account("Достоевский Федор Михайлович");
        acc2.editCurrencyList(Currency.valueOf("RUR"),100);
        acc2.setName("Тургенев Иван Сергеевич");
        acc2.editCurrencyList(Currency.valueOf("RUR"),300);
        System.out.println("before undo: "+acc2);

        for (int i = 0; i < 5; i++) {
            try {
                acc2=acc2.undo();
                System.out.println("<<< undo "+i+":"+acc2);
            } catch (IllegalAccessError e){}
        }
        System.out.println("-------------------");

//Часть 3

        Account acc3=new Account("Куприн Александр Иванович");
        Memento m1=new Memento(acc3.clone());
        acc3.editCurrencyList(Currency.valueOf("RUR"),158);
        Memento m2=new Memento(acc3.clone());
        acc3.editCurrencyList(Currency.valueOf("USD"),224);
        System.out.println("before restore state: "+acc3);
        System.out.println("Memento.state 1 = "+m1.getState());
        acc3=m2.getState();
        System.out.println("Memento.state 2 (copy in Object)= "+acc3);
        System.out.println("-------------------");
    }
}
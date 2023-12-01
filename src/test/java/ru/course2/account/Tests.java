package ru.course2.account;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Tests {
//тесты для Части 2 (занятие 1)
    @Test
    public void testPart1_TestClass() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.editCurrencyList(Currency.valueOf("EUR"),158);
        acc1.editCurrencyList(Currency.valueOf("EUR"),200);
    }
    @Test
    public void testPart1_NegativeSaldo() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.editCurrencyList(Currency.valueOf("EUR"),158);
        acc1.editCurrencyList(Currency.valueOf("EUR"),-200);
    }
    @Test
    public void testPart1_Getters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.editCurrencyList(Currency.valueOf("EUR"),158);
        String name=acc1.getName();
        List<CurrencyStorage> lst=acc1.getListCur();
    }
    @Test
    public void testPart1_Setters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.editCurrencyList(Currency.valueOf("EUR"),158);
        acc1.setName("Достоевский Федор Михайлович");
    }

//тесты для Части 2 (занятие 1)
    @Test
    public void testPart2_Undo() throws CloneNotSupportedException {
        Account acc2=new Account("Достоевский Федор Михайлович");
        acc2.editCurrencyList(Currency.valueOf("RUR"),100);
        acc2.setName("Тургенев Иван Сергеевич");
        acc2.editCurrencyList(Currency.valueOf("RUR"),300);
        for (int i = 0; i < 3; i++) {
            acc2=acc2.undo();
            System.out.println("<<< undo "+i+":"+acc2);
        }
    }

    @Test
    public void testPart2_UndoException() throws CloneNotSupportedException {
        Account acc2=new Account("Достоевский Федор Михайлович");
        acc2.editCurrencyList(Currency.valueOf("RUR"),100);
        acc2.setName("Тургенев Иван Сергеевич");
        acc2.editCurrencyList(Currency.valueOf("RUR"),300);
        for (int i = 0; i < 4; i++) {
            acc2=acc2.undo();
            System.out.println("<<< undo "+i+":"+acc2);
        }
    }

    //тесты для Части 3 (занятие 1)
    @Test
    public void testPart3_SaveRestore() throws CloneNotSupportedException {
        Account acc3 = new Account("Куприн Александр Иванович");
        Memento m1 = new Memento(acc3.clone());
        acc3.editCurrencyList(Currency.valueOf("RUR"), 158);
        Memento m2 = new Memento(acc3.clone());
        acc3.editCurrencyList(Currency.valueOf("USD"), 224);
        System.out.println("before restore state: " + acc3);
        System.out.println("Memento.state 1 = " + m1.getState());
        acc3 = m2.getState();
        System.out.println("Memento.state 2 (copy in Object)= " + acc3);
        Memento m3 = new Memento(acc3.clone());
        acc3.setName("Гоголь Николай Васильевич");
        System.out.println("current state: " + acc3);
        acc3.setName(m3.getName());
        System.out.println("restore only name: " + acc3);
    }
}


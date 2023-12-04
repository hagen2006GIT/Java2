package ru.course2.account;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Tests {
//тесты для Части 2 (занятие 1)
    @Test
    public void testPart1_TestClass() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyList(Currency.valueOf("EUR"),158);
        acc1.setCurrencyList(Currency.valueOf("EUR"),200);
    }
    @Test
    public void testPart1_NegativeSaldo() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyList(Currency.valueOf("EUR"),158);
        acc1.setCurrencyList(Currency.valueOf("EUR"),-200);
    }
    @Test
    public void testPart1_Getters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyList(Currency.valueOf("EUR"),158);
        String name=acc1.getName();
        List<CurrencyStorage> lst=acc1.getListCur();
    }
    @Test
    public void testPart1_Setters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyList(Currency.valueOf("EUR"),158);
        acc1.setName("Достоевский Федор Михайлович");
    }

//тесты для Части 2 (занятие 1)
    @Test
    public void testPart2_Undo() throws CloneNotSupportedException {
        Caretaker caretaker = new Caretaker();
        Account acc2=new Account("Петров Петр Петрович");
        caretaker.saveState(acc2.saveState());
        System.out.println("State 1 (saved) = "+acc2.getState());
        acc2.setCurrencyList(Currency.valueOf("RUR"),100);
        caretaker.saveState(acc2.saveState());
        System.out.println("State 2 (saved) = "+acc2.getState());
        acc2.setName("Иванов Василий Иванович");
        caretaker.saveState(acc2.saveState());
        System.out.println("State 3 (saved) = "+acc2.getState());
        acc2.setCurrencyList(Currency.valueOf("RUR"),300);
        System.out.println("State 4 (unsaved) = "+acc2.getState());
        for (int i = 3; i > 0; i--) {
            acc2.restoreState(caretaker.undo());
            System.out.println("State "+i+" (restored) = "+acc2.getState());
        }
    }
    @Test
    public void testPart2_UndoException() throws CloneNotSupportedException {
        Caretaker caretaker = new Caretaker();
        Account acc2=new Account("Петров Петр Петрович");
            caretaker.saveState(acc2.saveState());
            System.out.println("State 1 (saved) = "+acc2.getState());
        acc2.setCurrencyList(Currency.valueOf("RUR"),100);
            caretaker.saveState(acc2.saveState());
            System.out.println("State 2 (saved) = "+acc2.getState());
        acc2.setName("Иванов Василий Иванович");
            caretaker.saveState(acc2.saveState());
            System.out.println("State 3 (saved) = "+acc2.getState());
        acc2.setCurrencyList(Currency.valueOf("RUR"),300);
            System.out.println("State 4 (unsaved) = "+acc2.getState());
        for (int i = 4; i > 0; i--) {
            acc2.restoreState(caretaker.undo());
            System.out.println("State "+i+" (restored) = "+acc2.getState());
        }
    }
    @Test
    public void testPart2_UndoExceptionEmptyStack() throws CloneNotSupportedException {
        Caretaker caretaker = new Caretaker();
        Account acc2=new Account("Петров Петр Петрович");
        acc2.restoreState(caretaker.undo());
    }

    //тесты для Части 3 (занятие 1)
    @Test
    public void testPart3_SaveRestore() throws CloneNotSupportedException {
        Account acc3 = new Account("Куприн Александр Иванович");
        acc3.setCurrencyList(Currency.valueOf("EUR"),158);
        Invoker invoker1 = new Invoker();
        Invoker invoker2 = new Invoker();
        Invoker invoker3 = new Invoker();
        invoker1.saveState(acc3.saveState());
        acc3.setName("Гоголь Николай Васильевич");
        invoker2.save(acc3.saveState());
        acc3.setName("Лукьяненко Сергей Васильевич");
        acc3.setCurrencyList(Currency.valueOf("EUR"),258);
        invoker3.save(acc3.saveState());
        System.out.println("Current State = "+acc3.getState());
        System.out.println("Save 1: "+invoker1.getSnapshot().getState());
        System.out.println("Save 2: "+invoker2.getSnapshot().getState());
        System.out.println("Save 3: "+invoker3.getSnapshot().getState());
        acc3.restoreSaved(invoker2.restore());
        System.out.println("Return to Save2 = "+acc3.getState());
        acc3.restoreSaved(invoker1.restore());
        System.out.println("Return to Save1 = "+acc3.getState());
        acc3.restoreSaved(invoker3.restore());
        System.out.println("Return to Save3 = "+acc3.getState());
    }
}


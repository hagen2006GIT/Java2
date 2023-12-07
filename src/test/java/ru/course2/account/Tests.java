package ru.course2.account;

import org.junit.jupiter.api.Test;

public class Tests {
//тесты для Части 2 (занятие 1)
    @Test
    public void testPart1_TestClass() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyMap(Currency.EUR,158);
        acc1.setCurrencyMap(Currency.EUR,200);
        System.out.println("acc1 = "+acc1);
    }
    @Test
    public void testPart1_NegativeSaldo() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyMap(Currency.EUR,158);
        acc1.setCurrencyMap(Currency.USD,-200);
        System.out.println("acc1 = "+acc1);
    }
    @Test
    public void testPart1_Getters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyMap(Currency.RUR,158);
        acc1.setCurrencyMap(Currency.EUR,258);
        System.out.println("acc1 = "+acc1);
        System.out.println("Name = "+acc1.getName());
        System.out.println("Currency list = "+acc1.getLstCur());
    }
    @Test
    public void testPart1_Setters() throws CloneNotSupportedException {
        Account acc1 = new Account("Пушкин Александр Сергеевич");
        acc1.setCurrencyMap(Currency.valueOf("EUR"),158);
        acc1.setName("Достоевский Федор Михайлович");
        System.out.println("acc1 = "+acc1);
    }

//тесты для Части 2 (занятие 1)
    @Test
    public void testPart2_Undo() throws CloneNotSupportedException {
        Account acc2=new Account("Петров Петр Петрович");
        System.out.println("State 1 (saved) = "+acc2);
        acc2.setCurrencyMap(Currency.RUR,100);
        System.out.println("State 2 (saved) = "+acc2);
        acc2.setName("Иванов Василий Иванович");
        System.out.println("State 3 (saved) = "+acc2);
        acc2.setCurrencyMap(Currency.RUR,300);
        System.out.println("State 4 (saved) = "+acc2);
        for (int i = 4; i > 0; i--) {
            acc2.undo();
//            acc2.action.pop();
            System.out.println("State "+i+" (restored) = "+acc2);
        }
        System.out.println("action Array = "+acc2.action.size());
    }
    @Test
    public void testPart2_UndoExceptionEmptyStack() throws CloneNotSupportedException {
        Account acc2=new Account("Петров Петр Петрович");
        System.out.println("State 1 (saved) = "+acc2);
        acc2.setCurrencyMap(Currency.RUR,100);
        System.out.println("State 2 (saved) = "+acc2);
        acc2.setName("Иванов Василий Иванович");
        System.out.println("State 3 (saved) = "+acc2);
        acc2.setCurrencyMap(Currency.RUR,300);
        System.out.println("State 4 (unsaved) = "+acc2);
        for (int i = 5; i > 0; i--) {
            acc2.undo();
            System.out.println("State "+i+" (restored) = "+acc2);
        }
    }
    @Test
    public void testPart2_UndoFirstAction() throws CloneNotSupportedException {
        Account acc2=new Account("Петров Петр Петрович");
        acc2.undo();
    }

    //тесты для Части 3 (занятие 1)
    @Test
    public void testPart3_SaveRestore2() throws CloneNotSupportedException {
        Account acc3 = new Account("Куприн Александр Иванович");
        acc3.setCurrencyMap(Currency.EUR,158);
        Invoker2 invoker1 = new Invoker2();
        Invoker2 invoker2 = new Invoker2();
        Invoker2 invoker3 = new Invoker2();
        invoker1.saveState(acc3.save("Save 1"));
        acc3.setName("Гоголь Николай Васильевич");
        invoker2.save(acc3.save("Save 2"));
        acc3.setName("Лукьяненко Сергей Васильевич");
        acc3.setCurrencyMap(Currency.EUR,258);
        invoker3.save(acc3.save("Save 3"));
        System.out.println("Current State = "+acc3);
        System.out.println("Save 1: "+invoker1.getSnapshot().getState()+", "+invoker1.getSnapshot().getName()+", "+invoker1.getSnapshot().getLstCur());
        System.out.println("Save 2: "+invoker2.getSnapshot().getState()+", "+invoker2.getSnapshot().getName()+", "+invoker2.getSnapshot().getLstCur());
        System.out.println("Save 3: "+invoker3.getSnapshot().getState()+", "+invoker3.getSnapshot().getName()+", "+invoker3.getSnapshot().getLstCur());
        acc3.restoreFromSave(invoker2.restore());
        System.out.println("Return to Save 2 = "+acc3);
        acc3.restoreFromSave(invoker1.restore());
        System.out.println("Return to Save 1 = "+acc3);
        acc3.restoreFromSave(invoker3.restore());
        System.out.println("Return to Save 3 = "+acc3);
    }
}

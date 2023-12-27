package ru.course2.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class Tests {
    TestClass tc=new TestClass("");
    Able a=Utils.cache(new A());
    Utils u=new Utils(a);
    Able aCached=(Able) Proxy.newProxyInstance(
            a.getClass().getClassLoader()
            ,a.getClass().getInterfaces()
            ,new Utils(a)); //кэшированный объект класса A
    @Test
    public void testRunCachedMethod() {
//тест проверяет, что закэшированный метод запускается только один раз
// , если не было значимых изменений (т.е, если не выполнялся метод, отмеченный аннотацией @SetterMy)
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method(); //первый не кэшированный запуск
        aCached.method(); //второй, уже кэшированный запуск
        Assertions.assertEquals("method was launched", tc.getFlag());
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method(); //второй, уже кэшированный запуск
        Assertions.assertTrue(tc.getFlag().isEmpty());
    }
    @Test
    public void testRunCachedMethodsWithClearCache() {
//тест проверяет, что закэшированный метод запускается после сброса кэша(т.е, если выполнялся метод, отмеченный аннотацией @SetterMy)
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method2(); //первый не кэшированный запуск method2()
        Assertions.assertEquals("method2 was launched", tc.getFlag()); //проверил, что method2() выполнялся
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method2(); //второй, уже кэшированный запуск method2() - он не выполняется
        Assertions.assertTrue(tc.getFlag().isEmpty()); //проверил, что кэшированный запуск не оставляет после себя результата выполнения, т.е, method2() не запускался
        aCached.setValue(); //сбросил кеш, т.е, выполнил метод, который помечен аннотацией @SetterMy (значимые изменения, требующие сохранения объекта)
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method2(); //первый не кэшированный запуск method2()
        Assertions.assertEquals("method2 was launched", tc.getFlag()); //проверил, что method2() "наследил" в тестовом классе, т.е, он запускался, т.к. кэш был сброшен
    }
    @Test
    public void testRunNotCachedMethods() {
//тест проверяет, что не кэшируемый метод запускается без каких-либо ограничений
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method3(); //первый запуск method3(), который не отмечен аннотацией @Cache
        Assertions.assertEquals("method3 was launched", tc.getFlag()); //проверил, что method3() выполнялся
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method3(); //второй запуск method3()
        Assertions.assertEquals("method3 was launched", tc.getFlag()); //проверил, что method3() выполняется безусловно (онг не кэшируется)
        aCached.setValue(); //сбросил кеш
        tc.setFlag(""); //инициализировал flag выполнения методов значением null
        aCached.method3(); //пробуем выполнить method3()
        Assertions.assertEquals("method3 was launched", tc.getFlag()); //проверил, что method3() по прежнему спокойно выполняется
    }
}

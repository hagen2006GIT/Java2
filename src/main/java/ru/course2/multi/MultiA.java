package ru.course2.multi;

import ru.course2.cache.TestClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MultiA {
    @Cache(158)
    public void method(){
        System.out.println("original method running");
        TestClass tc=new TestClass("method was launched");
    }
    @Cache
    public void method2(){
        System.out.println("original method2 running");
        TestClass tc=new TestClass("method2 was launched");
    }
    public void method3(){
        System.out.println("original method3 running");
        TestClass tc=new TestClass("method3 was launched");
    }
    @SetterMy
    public void setValue(){
        System.out.println("setter method was launched");
        TestClass tc=new TestClass("setter method was launched");
    }
}

@Target({ElementType.METHOD})@Retention(RetentionPolicy.RUNTIME)
@interface Cache{
    int value() default 0;
}
@Target({ElementType.TYPE, ElementType.METHOD})@Retention(RetentionPolicy.RUNTIME)
@interface SetterMy {}
@Target({ElementType.TYPE, ElementType.METHOD})@Retention(RetentionPolicy.RUNTIME)
@interface Mutator {}

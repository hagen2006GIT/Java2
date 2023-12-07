package ru.course2.cache;

import lombok.Setter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class A implements Able{
    @Cache
    @Override
    public void method(){
        System.out.println("original method running");
        TestClass tc=new TestClass("method was launched");
    }
    @Cache
    @Override
    public void method2(){
        System.out.println("original method2 running");
        TestClass tc=new TestClass("method2 was launched");
    }
    @Override
    public void method3(){
        System.out.println("original method3 running");
        TestClass tc=new TestClass("method3 was launched");
    }
    @SetterMy
    @Override
    public void setValue(){
        System.out.println("setter method was launched");
        TestClass tc=new TestClass("setter method was launched");
    }
}
@Retention(RetentionPolicy.RUNTIME)
@interface Cache {}
@Retention(RetentionPolicy.RUNTIME)
@interface SetterMy {}

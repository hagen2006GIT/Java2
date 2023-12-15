package ru.course2.multi;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

public class Starter {
    public static void main(String[] args) throws InterruptedException {

//        final List<MethodThread> fractions=new CopyOnWriteArrayList<>();
// arrThread - список потоков вычислений (объектов класса Fraction)
//        Fraction fr=new Fraction(2,3);
//        Fraction fr2=new Fraction(4,5);
//        MultiUtils u=new MultiUtils(fr2);
//        Thread cleaner=new Thread(new CacheCleaner(),"Поток CacheCleaner");
/*        Fractionable num=MultiUtils.cache(fr);
        Fractionable frCached=(Fractionable) Proxy.newProxyInstance(
                num.getClass().getClassLoader()
                ,num.getClass().getInterfaces()
                ,new MultiUtils(num)); //кэшированный объект класса Fraction

        num=MultiUtils.cache(fr2);
        Fractionable frCached2=(Fractionable) Proxy.newProxyInstance(
                num.getClass().getClassLoader()
                ,num.getClass().getInterfaces()
                ,new MultiUtils(num)); //кэшированный объект класса Fraction*/

/*        Thread q1=new Thread (new MethodThread(frCached));
        Thread q2=new Thread (new MethodThread(frCached2));*/
//        final List<Thread> lstThread=new CopyOnWriteArrayList<>();
        // подготовим 2 потока вычислений
/*        Collections.addAll(lstThread
                ,new Thread(new MethodThread(new Fraction(2,3)),"Поток вычислений 1")
//                ,new Thread(new MethodThread(new Fraction(4,5)),"Поток вычислений 2")
        );
        for(Thread th: lstThread) {
            th.start();
//            th.join();
        }*/
/*       for(Thread th: arrThread){
           try{
               th.join();
           }catch (InterruptedException e){
               e.printStackTrace();
           }
       }*/
//        cleaner.join();

/*        Thread q1=new Thread (new MethodThread(fr));
        Thread q2=new Thread (new MethodThread(fr2));
        q1.start();
        q2.start();

        q1.join();
        q2.join();
*/

/*        for(Thread th: lstThread){
            th.interrupt();
}*/
//        cleaner.interrupt();

/*        for(Thread th: lstThread){
            System.out.println(th.getName()+":"+th.getState());
}*/
//        System.out.println(cleaner.getName()+":"+cleaner.getState());
//        System.out.println(Thread.currentThread().getName()+". ---------------------------");
//        System.out.println(Thread.currentThread().getName()+". Размер кэша = "+MethodThread.getCachedMethodsMap().size());
//        System.out.println(Thread.currentThread().getName()+". кэш = "+MethodThread.getCachedMethodsMap());
//        System.out.println(Thread.currentThread().getName()+". кэш = "+MethodThread.getCachedMethodsRunsMap());

//        System.out.println("CacheCleaner.MapHist.size()="+CacheCleaner.MapHist.size());
/*        CacheCleaner.MapHist.forEach(
                (key,value) -> {
                    System.out.println("hist. "+key+"="+value);
                }
        );
*/
/*        MethodThread.getCachedMethodsMap().forEach(
            (key,value) -> {
                System.out.println(Thread.currentThread().getName()+". "+key+"="+value);
            }
        );
*/
    }
}

package ru.course2.multi;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodForTest {
    // cachedMethodsMap - карта кэша состояний объекта
    // (key-само состояние,value-время завершения хранения состояния в кэше
    // т.е. время записи состояния в кэш+время жизни в кэше (из параметра аннотации @Cache)
    @Getter @Setter
    public volatile static Map<String,Long> cachedMethodsMap=new ConcurrentHashMap<>();
    // cachedMethodsRunsMap - карта признаков использования значения
    // т.е. признака того, что значение читали из кэша и чистить его не нужно
    @Getter
    public volatile static Map<String,Integer> cachedMethodsRunsMap=new ConcurrentHashMap<>();
    @Getter
    private final Fractionable F; //кэшированный объект класса Fraction
    MethodForTest(Fraction obj) {
        Fractionable num=MultiUtils.cache(obj);
        this.F=(Fractionable) Proxy.newProxyInstance(
                num.getClass().getClassLoader()
                ,num.getClass().getInterfaces()
                ,new MultiUtils(num));
    }

    public void run() {
        F.doubleValue();
        F.setDenum(F.getDenum()+1);
        F.setNum(F.getNum()+1);
        F.doubleValue();
    }
}

package ru.course2.multi;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiUtils implements InvocationHandler {
    public Fractionable a;
    public MultiUtils(Fractionable a) {
        this.a=a;
    }
    public static Fractionable cache(Fraction able) {
        return able;
    }
    public static int getLifetime(Annotation[] annotations) { //получить их аннотации Cache() параметр "время жизни" кэшированного состояния объекта
        int ret=0;
        try {
            for (Annotation annotation:annotations) {
                if (annotation instanceof Cache) {
                    final Cache cacheParams=(Cache) annotation;
                    ret=cacheParams.value();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    // подготовка/проверка окружения и запуск потока чистильщика кэша
    public void PrepareAndClearCache() {
        if (CacheCleaner.getFlagActivity()==0) {
            Map<String, Long> cachedMethodsMapCopy = new ConcurrentHashMap<>(MethodForTest.cachedMethodsMap);
            Map<String, Integer> cachedMethodsRunsMapCopy = new ConcurrentHashMap<>(MethodForTest.cachedMethodsRunsMap);
            CacheCleaner clr = new CacheCleaner();
            clr.setMaps(new MapsCache(cachedMethodsMapCopy, cachedMethodsRunsMapCopy));
            Thread cleaner = new Thread(clr, "Поток CacheCleaner");
            CacheCleaner.setFlagActivity(1);
            cleaner.start();
        }
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean retDo=true;
        int lifetime=0;
        PrepareAndClearCache(); // подготовка карт и активация потока чистильщика кэша
        Method tmp=a.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes());
        String keyStr=a.getNum()+":"+a.getDenum(); //ключ кэша - состояние объекта Fraction
        if(Arrays.toString(tmp.getAnnotations()).contains("Cache(")){ //если метод кэшируемый, т. е. описан аннотацией Cache
            lifetime=getLifetime(tmp.getAnnotations()); //время жизни состояния в кэше (прочитал из параметра аннотации)
            if(MethodForTest.cachedMethodsMap.get(keyStr)!=null) { //в кэше уже есть состояние
                    retDo = false; //если не время не вышло, то берем состояние из кэша
                    System.out.println("прочитал из кэша: "+keyStr+" / "+MethodForTest.cachedMethodsMap.get(keyStr));
                    MethodForTest.cachedMethodsRunsMap.put(keyStr,1); //зафиксировал, что кэшированное состояние использовалось
                    MethodForTest.cachedMethodsMap.replace(keyStr,System.currentTimeMillis()+lifetime); //обновил дату крайнего использования кэшированного значения
            } else { //ранее это состояние не кэшировалось
                MethodForTest.cachedMethodsMap.put(keyStr,System.currentTimeMillis()+lifetime); //добавил новое состояние в кэш
                System.out.println("добавил в кэш: "+keyStr+" / "+(System.currentTimeMillis()+lifetime));
            }
        } else if (Arrays.toString(tmp.getAnnotations()).contains("Mutator")) { //аннотация методов, меняющих состояние объекта
        }
        return (retDo)?method.invoke(a,args):(double) a.getNum()/a.getDenum();
    }
}

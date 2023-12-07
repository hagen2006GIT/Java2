package ru.course2.cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Utils implements InvocationHandler {
    public Able a;
    public static Map<Integer,String> cachedMetthodsMap=new HashMap<>();
    public Utils(Able a) {
        this.a=a;
    }
    public static Able cache(Able able){
        return able;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean retDo=true;
        Method tmp=a.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes());
        if(Arrays.toString(tmp.getAnnotations()).contains("Cache()")){
            if(cachedMetthodsMap.get(tmp.hashCode())!=null){
                retDo=false;
            }else{
                cachedMetthodsMap.put(tmp.hashCode(), "1");
            }
        } else if (Arrays.toString(tmp.getAnnotations()).contains("SetterMy()")) {
            cachedMetthodsMap.clear();
        }
        return (retDo)?method.invoke(a,args):null;
    }
    public Map<Integer, String> getCachedMetthodsMap() {
        return cachedMetthodsMap;
    }
}

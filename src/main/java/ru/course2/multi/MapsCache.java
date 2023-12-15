package ru.course2.multi;

import java.util.Map;

// класс для работы с временными картами кэша
public class MapsCache {
    private Map<String,Long> MethodsMap; // временная карта кэша состояний
    private Map<String,Integer> MethodsRunsMap; //временная карта признаков чтения состояний из кэша
    public void MethodsMapRemove(String key) {
        MethodsMap.remove(key);
    }
    public void MethodsMapRunsRemove(String key) {
        MethodsRunsMap.remove(key);
    }
    public MapsCache(Map<String, Long> methodsMap, Map<String, Integer> methodsRunsMap) {
        MethodsMap=methodsMap;
        MethodsRunsMap=methodsRunsMap;
    }
    public Map<String, Long> getMethodsMap() {
        return MethodsMap;
    }
    public Map<String, Integer> getMethodsRunsMap() {
        return MethodsRunsMap;
    }
}

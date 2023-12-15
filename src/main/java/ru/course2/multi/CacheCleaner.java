package ru.course2.multi;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheCleaner implements Runnable{
    public volatile static Map<String,Long> MapHist=new ConcurrentHashMap<>(); // временная карта кэша состояний
    @Getter
    private volatile static int flagActivity=0; // флаг активного потока чистидбщика
    @Setter @Getter
    private volatile static boolean lookUsedValue=true; // обращать внимание на использование значения или удалять состояние несмотря на то, что оно было использовано ранее
    public static void setFlagActivity(int flagActivity) {
        CacheCleaner.flagActivity = flagActivity;
    }
    @Setter
    private MapsCache maps;
    private synchronized void SyncMaps() {
        MethodForTest.cachedMethodsMap.clear();
        MethodForTest.cachedMethodsMap.putAll(maps.getMethodsMap());
    }
    public CacheCleaner() {
    }
    @Override
    public void run() {
        int k=maps.getMethodsMap().size(); // размер кэша до его очистки
        maps.getMethodsMap().forEach(
                (key,value) -> {
                    if(value<System.currentTimeMillis()) {
                        if(maps.getMethodsRunsMap().get(key)==null||maps.getMethodsRunsMap().get(key)==0||!lookUsedValue) { //и кэшированное состояние ни разу не использовалось
                            maps.MethodsMapRemove(key);
                            System.out.println("удаление из кэша состояние {"+key+"="+value+"}");
                            MapHist.put(key,value);
                        }
                    }
                }
        );
        if(maps.getMethodsMap().size()!=k) { // если что-то почистили, то обновим кэш
            SyncMaps();
        }
        CacheCleaner.setFlagActivity(0);
    }
}

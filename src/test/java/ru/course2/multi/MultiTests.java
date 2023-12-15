package ru.course2.multi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

public class MultiTests {
    // время жизни состояний в кэше = 2000мс (@Cache(2000))
    @Test
    // проверим, что в кэше сохраняется одно состояние, если время жизние его не вышло
    public void testCacheKeepingOneValue() {
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        mTest.getF().doubleValue(); //чтение значений из метода и запись значений в кэш
        mTest.getF().doubleValue(); //должно прочитаться из кэша, т.к. CacheCleaner не успеет почистить кэш
        // проверим, что в кэше должно остаться 1 значение (размер map'ы кэша = 1)
        Assertions.assertEquals(1, MethodForTest.getCachedMethodsMap().size());
    }
    @Test
    // проверим, что в кэше состояние чистится, если время жизние его прошло и оно не было использовано
    public void testCacheCleaningOneValue() throws InterruptedException {
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        mTest.getF().doubleValue(); //чтение значений из метода и запись значений в кэш
        sleep(3000); // поспим, чтобы вышло время жизни состояния в кэше
        mTest.getF().doClear(); // активирует поток чистильщика кэша через invoke,
                                // который должен удалить из кэша состояние, время жизни которого вышло
                                // и которое не было использовано
        // проверим, что кэш чистый (size()=0)
        sleep(1000); // поспим, чтобы ClearCache успел отработать
        Assertions.assertEquals(0, MethodForTest.getCachedMethodsMap().size());
    }
    @Test
    public void testCacheKeepingTwoValues() {
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        mTest.getF().doubleValue(); //запись нового значения в кэш
        mTest.getF().doubleValue(); //чтение из кэша (удалиться потокм CacheCleaner ещё не должно)
        {
            mTest.getF().setDenum(4);
            mTest.getF().setNum(6);
        }
        mTest.getF().doubleValue(); //запись нового значения в кэш
        // проверим, что в кэше должно остаться 2 значения (размер map'ы кэша = 2)
        Assertions.assertEquals(2, MethodForTest.getCachedMethodsMap().size());
    }
    @Test
    public void testCacheCleaning() throws InterruptedException {
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        mTest.getF().doubleValue(); //запись нового значения в кэш
        mTest.getF().setNum(6);
        mTest.getF().doubleValue(); //чтение из кэша (удалиться потокм CacheCleaner ещё не должно)
        sleep(3000); // поспим, чтобы состояние из кэша почистилось
        mTest.getF().doClear();
        sleep(3000); // поспим, чтобы состояние из кэша почистилось
        mTest.getF().doubleValue(); //запись нового значения в кэш
        // проверим, что в кэше должно остаться 2 значения (размер map'ы кэша = 2)
        Assertions.assertEquals(1, MethodForTest.getCachedMethodsMap().size());
    }
    @Test
    public void testCacheCleaning2() throws InterruptedException {
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        mTest.getF().doubleValue(); //запись нового значения в кэш
        sleep(1000); // поспим, чтобы состояние из кэша почистилось
        mTest.getF().setNum(6);
        mTest.getF().doubleValue(); //чтение из кэша (удалиться потокм CacheCleaner ещё не должно)
        sleep(1000); // поспим, чтобы состояние из кэша почистилось
        mTest.getF().doClear();
        sleep(3000); // поспим, чтобы состояние из кэша почистилось
        // проверим, что в кэше должно остаться 2 значения (размер map'ы кэша = 2)
        Assertions.assertEquals(1, MethodForTest.getCachedMethodsMap().size());
    }
    private long codeBlock(int modeCache) throws InterruptedException {
        long timeInRunning;
        { // почистим кэш
            MethodForTest.getCachedMethodsRunsMap().clear();
            MethodForTest.getCachedMethodsMap().clear();
            System.gc();
        }
        MethodForTest mTest = new MethodForTest(new Fraction(2, 3));
        CacheCleaner.setFlagActivity(modeCache); // включил/отключил чистильщика
        timeInRunning=System.currentTimeMillis(); // фиксирую время начала
        { // блок кода, время выполнения которого засекаю
            for (int i = 0; i < 100; i++) {
                mTest.getF().doubleValue();
                if(i%10==0){ // на каждой десятой итерации меняю состояние объекта
                    mTest.getF().setNum(mTest.getF().getNum()+i);
                    mTest.getF().setDenum(mTest.getF().getDenum()+i);
                }
                sleep(100);
            }
            mTest.getF().setNum(2);
            mTest.getF().setDenum(3);
            sleep(2500);
            mTest.getF().doubleValue();
        }
        return System.currentTimeMillis()-timeInRunning; // время выполнения блока
    }
    @Test
    public void testTimeOfRunWithCache() throws InterruptedException {
        CacheCleaner.setLookUsedValue(true); // включил проверку использованных ранее состояний (т.е используемые состояния из кэша не удаляются)
        long timeWithoutCache=codeBlock(1); // время выполнения с выключенным чистильщиком кэша
        CacheCleaner.setLookUsedValue(false); // выключил проверку использованных ранее состояний (чтобы состояние удалялось из кэша вне зависимости от того, использовалось ли оно ранее)
        long timeWithCache=codeBlock(0); // время выполнения с активностью чистильщика кэша
        System.out.println("timeWithCache="+timeWithCache);
        System.out.println("timeWithoutCache="+timeWithoutCache);
        System.out.println("timeWithoutCache / timeWithCache = "+(double) timeWithoutCache / timeWithCache);
        System.out.println("MapHist="+CacheCleaner.MapHist);
// ожидаемый результат теста:
// 1. время выполнения с включенным кэшированием не увеличивается значительно (менее 10%)
// 2. дополнительно: карта MapHist (в неё пишу все удаляемые из кэша состояния) не пуста, т.е кэш работал, что-то чистилось
        Assertions.assertTrue(((double) timeWithoutCache / timeWithCache < 1.1) && (!CacheCleaner.MapHist.isEmpty()));
    }
}

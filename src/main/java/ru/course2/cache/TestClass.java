package ru.course2.cache;

public class TestClass {
    private static String flag;
    public TestClass(String strFlag) {
        TestClass.flag = strFlag;
    }
    public String getFlag() {
        return TestClass.flag;
    }
    public void setFlag(String strFlag) {
        TestClass.flag = strFlag;
    }
}

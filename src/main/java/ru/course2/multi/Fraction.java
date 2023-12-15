package ru.course2.multi;

public class Fraction implements Fractionable{
    private int num;
    private int denum;
    public Fraction(int num, int denum) {
        this.num=num;
        this.denum=denum;
    }
    @Mutator
    public void setNum(int num) {
        this.num=num;
    }
    @Mutator
    public void setDenum(int denum) {
        this.denum=denum;
    }
    public void doClear() {};
    @Override
    @Cache(2000)
    public double doubleValue() {
        System.out.println("running original method:"+(double) num/denum);
        return (double) num/denum;
    }
    @Override
    public String toString() {
        return num+":"+denum;
    }
    public int getNum() {
        return num;
    }
    public int getDenum() {
        return denum;
    }
}

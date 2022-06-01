package src;

public class regressionResFunc {
    private final MathFunc func1;
    private final MathFunc func2;
    private final MathFunc func3;
    private final float yAverage;

    public regressionResFunc(float yAverage, MathFunc func1, MathFunc func2, MathFunc func3) {
        this.yAverage = yAverage;
        this.func1 = func1;
        this.func2 = func2;
        this.func3 = func3;
    }

    public float calculate(float x1, float x2, float x3) {
        return this.yAverage * func1.calculate(x1) * func2.calculate(x2) * func3.calculate(x3);
    }

    public String toString() {
        return this.yAverage + "*" + func1.toString() + "*" + func2.toString() + "*" + func3.toString();
    }
}

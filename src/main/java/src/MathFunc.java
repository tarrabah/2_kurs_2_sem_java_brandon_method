package src;
public class MathFunc {
    private final float a;
    private final float b;
    private final int type;

    public MathFunc(float a, float b, int type) {
        this.a = a;
        this.b = b;
        this.type = type;
    }

    public float calculate(float x) {
        return switch (type) {
            case 0 -> a * x + b;
            case 1 -> 1 / (a * x + b);
            case 2 -> a / x + b;
            case 3 -> (float) Math.pow(x, a) * b;
            case 4 -> (float) Math.exp(a * x) * b;
            case 5 -> a * (float) Math.log(x) + b;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return switch (type) {
            case 0 -> a + " * x + " + b;
            case 1 -> "1 / (" + a + " * x + " + b + ")";
            case 2 -> a + " / x + " + b;
            case 3 -> "x ^ " + a + " * " + b;
            case 4 -> "e ^ (x * " + a + ") * " + b;
            case 5 -> a + " * log(x) + " + b;
            default -> "";
        };
    }
}


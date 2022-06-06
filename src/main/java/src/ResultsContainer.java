package src;

public class ResultsContainer {
    float yAverage;

    RegressionResFunc ResultFunction;

    MathFunc[][] functions;

    int[] optimalFuncIndex;

    Float[][] yXn;

    int lineNumber;

    ResultsContainer(int lineNumber) {
        optimalFuncIndex = new int[3];
        functions = new MathFunc[3][6];
        this.lineNumber = lineNumber;
        yXn = new Float[3][lineNumber];
    }
}

package src;

public class ResultsContainer {
    float yAverage;

    RegressionResFunc ResultFunction;

    MathFunc[][] functions;

    int[] optimalFuncIndex;

    ResultsContainer(){
        optimalFuncIndex = new int[3];
        functions = new MathFunc[3][6];
    }
}

package src;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

class TabWidget extends JTabbedPane {

    ChartPanel resultPLot;
    ChartPanel firstParameterPLot;
    ChartPanel secondParameterPLot;
    ChartPanel thirdParameterPLot;
    ExperimentalDataPanel showDataPanel;

    XnResultsPanel x1Results;
    XnResultsPanel x2Results;
    XnResultsPanel x3Results;

    JFreeChart chartX1;
    JFreeChart chartX2;
    JFreeChart chartX3;
    JFreeChart chartResult;
    XYSeriesCollection x1Dataset;
    XYSeriesCollection x2Dataset;
    XYSeriesCollection x3Dataset;
    XYSeriesCollection resultDataset;

    XYLineAndShapeRenderer resultPlotRenderer;
    XYPlot resultPlotGotPlot;

    TabWidget(int lineNumber) {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        showDataPanel = new ExperimentalDataPanel(lineNumber);
        //first chart
        x1Dataset = new XYSeriesCollection();
        chartX1 = ChartFactory.createXYLineChart("y(x1))",
                "x1", "y(x1)", x1Dataset, PlotOrientation.VERTICAL, true, false, false);
        firstParameterPLot = new ChartPanel(chartX1);

        x2Dataset = new XYSeriesCollection();
        chartX2 = ChartFactory.createXYLineChart("y(x2))",
                "x2", "y(x2)", x2Dataset, PlotOrientation.VERTICAL, true, false, false);
        secondParameterPLot = new ChartPanel(chartX2);

        x3Dataset = new XYSeriesCollection();
        chartX3 = ChartFactory.createXYLineChart("y(x3))",
                "x3", "y(x3)", x3Dataset, PlotOrientation.VERTICAL, true, false, false);
        thirdParameterPLot = new ChartPanel(chartX3);

        resultDataset = new XYSeriesCollection();
        chartResult = ChartFactory.createScatterPlot("y(n)",
                "n", "y(n)", resultDataset, PlotOrientation.VERTICAL, true, false, false);
        resultPLot = new ChartPanel(chartResult);

        resultPlotGotPlot = (XYPlot) chartResult.getPlot();
        resultPlotRenderer = new XYLineAndShapeRenderer();

        x1Results = new XnResultsPanel(lineNumber);
        x2Results = new XnResultsPanel(lineNumber);
        x3Results = new XnResultsPanel(lineNumber);

        //add panels to tab widget
        add("data", showDataPanel);
        add("x1", firstParameterPLot);
        add("x1 table", x1Results);
        add("x2", secondParameterPLot);
        add("x2 table", x2Results);
        add("x3", thirdParameterPLot);
        add("x3 table", x3Results);
        add("results", resultPLot);
    }

    public void clearCharts() {
        x1Dataset.removeAllSeries();
        x2Dataset.removeAllSeries();
        x3Dataset.removeAllSeries();
        resultDataset.removeAllSeries();
    }
}
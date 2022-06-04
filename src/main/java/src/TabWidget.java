package src;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

class TabWidget extends JTabbedPane
{

    ChartPanel resultPLot;
    ChartPanel firstParameterPLot;
    ChartPanel secondParameterPLot;
    ChartPanel thirdParameterPLot;
    ShowDataPanel showDataPanel;

    ShowXnResultsPanel x1Results;
    ShowXnResultsPanel x2Results;
    ShowXnResultsPanel x3Results;

    JFreeChart chartX1;
    JFreeChart chartX2;
    JFreeChart chartX3;
    JFreeChart chartResult;
    XYSeriesCollection x1Dataset;
    XYSeriesCollection x2Dataset;
    XYSeriesCollection x3Dataset;
    XYSeriesCollection resultDataset;
    TabWidget()
    {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        //TODO https://stackoverflow.com/questions/5290812/jfreechart-scatter-plot-lines
        showDataPanel = new ShowDataPanel();
        //first chart
        x1Dataset = new XYSeriesCollection();
        chartX1 = ChartFactory.createXYLineChart("y(x1))",
                "x1", "y(x1)", x1Dataset, PlotOrientation.VERTICAL, false, false, false);
        firstParameterPLot = new ChartPanel(chartX1);

        x2Dataset = new XYSeriesCollection();
        chartX2 = ChartFactory.createXYLineChart("y(x2))",
                "x2", "y(x2)", x2Dataset, PlotOrientation.VERTICAL, false, false, false);
        secondParameterPLot = new ChartPanel(chartX2);

        x3Dataset = new XYSeriesCollection();
        chartX3 = ChartFactory.createXYLineChart("y(x3))",
                "x3", "y(x3)", x3Dataset, PlotOrientation.VERTICAL, false, false, false);
        thirdParameterPLot = new ChartPanel(chartX3);

        resultDataset = new XYSeriesCollection();
        chartResult = ChartFactory.createScatterPlot("y(n)",
                "n", "y(n)", resultDataset, PlotOrientation.VERTICAL, false, false, false);
        resultPLot = new ChartPanel(chartResult);
        //secondParameterPLot = new ChartPanel();
        //thirdParameterPLot = new ChartPanel();
        //resultPLot = new ChartPanel();


        x1Results = new ShowXnResultsPanel();
        x2Results = new ShowXnResultsPanel();
        x3Results = new ShowXnResultsPanel();

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

    public void clearCharts(){
        x1Dataset.removeAllSeries();
        x2Dataset.removeAllSeries();
        x3Dataset.removeAllSeries();
        resultDataset.removeAllSeries();
    }
}
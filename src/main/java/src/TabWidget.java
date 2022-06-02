package src;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

class TabWidget extends JTabbedPane
{

    ChartPanel resultPLot;
    ChartPanel firstParameterPLot;
    ChartPanel secondParameterPLot;
    ChartPanel thirdParameterPLot;
    ShowDataPanel showDataPanel;

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

        showDataPanel = new ShowDataPanel();
        //first chart
        x1Dataset = new XYSeriesCollection();
        chartX1 = ChartFactory.createXYLineChart("y(x1))",
                "x1", "y(x1)", x1Dataset);
        firstParameterPLot = new ChartPanel(chartX1);

        x2Dataset = new XYSeriesCollection();
        chartX2 = ChartFactory.createXYLineChart("y(x2))",
                "x2", "y(x2)", x2Dataset);
        secondParameterPLot = new ChartPanel(chartX2);

        x3Dataset = new XYSeriesCollection();
        chartX3 = ChartFactory.createXYLineChart("y(x3))",
                "x3", "y(x3)", x3Dataset);
        thirdParameterPLot = new ChartPanel(chartX3);

        resultDataset = new XYSeriesCollection();
        chartResult = ChartFactory.createXYLineChart("y(n))",
                "n", "y(n)", resultDataset);
        resultPLot = new ChartPanel(chartResult);

        //secondParameterPLot = new ChartPanel();
        //thirdParameterPLot = new ChartPanel();
        //resultPLot = new ChartPanel();

        //add panels to tab widget
        add("data", showDataPanel);
        add("x1", firstParameterPLot);
        add("x2", secondParameterPLot);
        add("x3", thirdParameterPLot);
        add("results", resultPLot);
    }
}
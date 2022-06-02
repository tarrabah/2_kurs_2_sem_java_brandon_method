package src;

import javax.swing.*;
import java.awt.*;

class TabWidget extends JTabbedPane
{

    Panel resultPLot;
    Panel firstParameterPLot;
    ShowDataPanel showDataPanel;
    TabWidget()
    {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        showDataPanel = new ShowDataPanel();

        firstParameterPLot = new Panel();
        Panel secondParameterPLot = new Panel();
        Panel thirdParameterPLot = new Panel();
        resultPLot = new Panel();

        //add panels to tab widget
        add("data", showDataPanel);
        add("x1", firstParameterPLot);
        add("x2", secondParameterPLot);
        add("x3", thirdParameterPLot);
        add("results", resultPLot);
    }
}
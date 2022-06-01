package src;

import javax.swing.*;
import java.awt.*;

class TabWidget extends JTabbedPane
{

    Panel resultPLot;
    Panel firstParameterPLot;
    Panel showDataPanel;
    TableModel myTableModel;
    JTable dataTable;
    TabWidget()
    {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        showDataPanel = new Panel();
        showDataPanel.setLayout(new GridLayout());
        String[] columnNames = {
                "N",
                "x1",
                "x2",
                "x3",
                "y_exp",
                "y_calc"
        };

        Object[][] data = new Object[22][6];

        for (int i = 0; i < 22; i++)
        {
            data[i][0] = i + 1;
            for (int j = 1; j < 6; j++)
            {
                data[i][j] = new String(" ");
            }
        }

        //JTable dataTable = new JTable(data, columnNames);
        //dataTable.setModel(new TableModel());
        myTableModel = new TableModel();
        dataTable = new JTable(myTableModel);
        dataTable.setRowHeight(23);
        showDataPanel.add(dataTable);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(5, 10, 300, 150);
        scrollPane.setVisible(true);
        showDataPanel.add(scrollPane);

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
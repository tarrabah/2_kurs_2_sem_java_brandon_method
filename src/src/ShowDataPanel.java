package src;

import javax.swing.*;
import java.awt.*;

class ShowDataPanel extends Panel
{
    JTable dataTable;

    TableModel myTableModel;


    ShowDataPanel() {
        super();
        setLayout(new GridLayout());
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

        myTableModel = new TableModel();
        dataTable = new JTable(myTableModel);
        dataTable.setRowHeight(23);
        add(dataTable);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(5, 10, 300, 150);
        scrollPane.setVisible(true);
        add(scrollPane);
    }
}
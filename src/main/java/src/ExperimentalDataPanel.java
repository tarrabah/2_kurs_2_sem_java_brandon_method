package src;

import javax.swing.*;
import java.awt.*;

class ExperimentalDataPanel extends Panel
{
    JTable dataTable;

    ExperimentDataTableModel myTableModel;


    ExperimentalDataPanel() {
        super();
        setLayout(new GridLayout());

        myTableModel = new ExperimentDataTableModel();
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
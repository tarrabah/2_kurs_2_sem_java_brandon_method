package src;

import javax.swing.*;
import java.awt.*;

public class ShowXnResultsPanel extends Panel{
    ShowXnResultsTableModel myTableModel = new ShowXnResultsTableModel();
    JTable dataTable;

    ShowXnResultsPanel() {
        super();
        setLayout(new GridLayout());

        myTableModel = new ShowXnResultsTableModel();
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

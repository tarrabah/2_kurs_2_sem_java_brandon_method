package src;

import javax.swing.*;
import java.awt.*;

public class XnResultsPanel extends Panel {
    XnResultsTableModel TableModel;
    JTable dataTable;

    XnResultsPanel(int lineNumber) {
        super();
        setLayout(new GridLayout());

        TableModel = new XnResultsTableModel(lineNumber);
        dataTable = new JTable(TableModel);
        dataTable.setRowHeight(23);
        add(dataTable);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(5, 10, 300, 150);
        scrollPane.setVisible(true);
        add(scrollPane);
    }
}

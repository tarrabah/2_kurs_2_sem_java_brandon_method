package src;

import javax.swing.table.AbstractTableModel;

class TableModel extends AbstractTableModel
{

    final private String[] columnNames = {
            "N",
            "x1",
            "x2",
            "x3",
            "y_exp",
            "y_calc"
    };

    final Object[][] data = {
            {1, 170, 13, 22, 58.9, ""},
            {2, 180, 14, 25, 52.4, ""},
            {3, 170, 13, 30, 44.0, ""},
            {4, 160, 18, 21, 46.1, ""},
            {5, 188, 17, 27, 43.8, ""},
            {6, 200, 16, 24, 54.1, ""},
            {7, 210, 19, 22, 53.3, ""},
            {8, 150, 20, 25, 32.1, ""},
            {9, 174, 21, 26, 33.6, ""},
            {10, 182, 21, 26, 35.3, ""},
            {11, 190, 21, 26, 37, ""},
            {12, 170, 18, 26, 39.5, ""},
            {13, 160, 17, 29, 35.4, ""},
            {14, 170, 15, 24, 49.8, ""},
            {15, 180, 15, 24, 52.1, ""},
            {16, 190, 15, 24, 54.3, ""},
            {17, 210, 15, 24, 58.7, ""},
            {18, 225, 16, 22, 64.3, ""},
            {19, 210, 18, 29, 43, ""},
            {20, 150, 18, 19, 47.8, ""},
            {21, 186, 14, 25, 53.7, ""},
            {22, 190, 14, 25, 54.6, ""}
    };

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }


    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col].toString();
    }
}
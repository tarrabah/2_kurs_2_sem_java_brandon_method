package src;

import javax.swing.table.AbstractTableModel;

class ExperimentDataTableModel extends AbstractTableModel
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
            {1, "", "", "", "", ""},
            {2, "", "", "", "", ""},
            {3, "", "", "", "", ""},
            {4, "", "", "", "", ""},
            {5, "", "", "", "", ""},
            {6, "", "", "", "", ""},
            {7, "", "", "", "", ""},
            {8, "", "", "", "", ""},
            {9, "", "", "", "", ""},
            {10, "", "", "", "", ""},
            {11, "", "", "", "", ""},
            {12, "", "", "", "", ""},
            {13, "", "", "", "", ""},
            {14, "", "", "", "", ""},
            {15, "", "", "", "", ""},
            {16, "", "", "", "", ""},
            {17, "", "", "", "", ""},
            {18, "", "", "", "", ""},
            {19, "", "", "", "", ""},
            {20, "", "", "", "", ""},
            {21, "", "", "", "", ""},
            {22, "", "", "", "", ""}
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

    public void insertIntoRow(int rowId, Object[] rowData){
        for (int i = 0; i < this.getRowCount(); i++)
            data[i][rowId] = rowData[i];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (this.isCellEditable(rowIndex, columnIndex)) {
            this.data[rowIndex][columnIndex] = aValue;
        }
    }
}
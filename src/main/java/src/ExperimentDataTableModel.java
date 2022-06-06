package src;

import javax.swing.table.AbstractTableModel;

class ExperimentDataTableModel extends AbstractTableModel {

    final private String[] columnNames = {
            "N",
            "x1",
            "x2",
            "x3",
            "y_exp",
            "y_calc"
    };

    final Object[][] data;

    ExperimentDataTableModel(int lineNumber) {
        super();

        data = new Object[lineNumber][6];

        for (int i = 0; i < lineNumber; i++) {
            data[i][0] = i + 1;
            for (int j = 1; j < 6; j++) {
                data[i][j] = " ";
            }
        }
    }

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

    public void insertIntoRow(int rowId, Object[] rowData) {
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
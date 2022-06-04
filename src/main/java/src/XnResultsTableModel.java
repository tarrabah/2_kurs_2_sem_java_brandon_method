package src;

import javax.swing.table.AbstractTableModel;

public class XnResultsTableModel extends AbstractTableModel {
    final private String[] columnNames = {
            "x",
            "y1",
            "y2",
            "y3",
            "y4",
            "y5",
            "y6"
    };

    //TODO change data to ""
    Object[][] data;

    XnResultsTableModel() {
        super();
        data = new Object[22][7];
        for (int i = 0; i < 22; i++)
        {
            for (int j = 0; j < 7; j++)
            {
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

    public void insertIntoRow(int rowId, Object[] rowData){
        for (int i = 0; i < this.getRowCount(); i++){
            data[i][rowId] = rowData[i];
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (this.isCellEditable(rowIndex, columnIndex)) {
            this.data[rowIndex][columnIndex] = aValue;
        }
    }
}

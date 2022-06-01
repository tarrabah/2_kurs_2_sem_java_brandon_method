package src;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

class PlotFrame extends Frame implements ActionListener
{
    // Конструктор (аргументы - высота и ширина окна);
    private Menu fileMenu;
    private MenuBar mainMenuBar;
    private MenuItem newFileItem;
    private TabWidget tabWidget;

    private String filePath;
    private Program program;

    PlotFrame(int height, int width)
    {
        // Заголовок окна:
        setTitle("kursach");
        setBounds(100,50,width,height);
        setBackground(Color.GRAY);
        setLayout(new GridLayout());

        addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent ve)
                    {
                        System.exit(0);
                    }
                }
        );
        //create menu bar
        mainMenuBar = new MenuBar();
        //create menu
        fileMenu = new Menu("file");
        //create menu item
        newFileItem = new MenuItem("Open", new MenuShortcut(KeyEvent.VK_N));
        newFileItem.setActionCommand("Open");

        //adds listener to menu item
        newFileItem.addActionListener(this);

        fileMenu.add(newFileItem);//adds item to menu

        mainMenuBar.add(fileMenu);//add menu to menu bar

        setMenuBar(mainMenuBar);//sets menu bar for window

        tabWidget = new TabWidget();

        add(tabWidget);
        setResizable(false);
        setVisible(true);   //Отображение окна

        program = new Program();
    }

    public void actionPerformed(ActionEvent e)
    {
        FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
        fd.setVisible(true);
        String path = fd.getDirectory() + fd.getFile();
        if (path != null)
        {
            System.out.println(path);
            float[][] x = new float[3][22];
            float[] y = new float[22];

            try {
                File file = new File(String.format("/%s", path));
                Scanner scanner = new Scanner(file);
                System.out.println("file opened");
                int i = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] splitLine = line.split(" ");
                    x[0][i] = Integer.parseInt(splitLine[0]);
                    x[1][i] = Integer.parseInt(splitLine[1]);
                    x[2][i] = Integer.parseInt(splitLine[2]);

                    y[i] = Float.parseFloat(splitLine[3]);
                    i++;
                }
                scanner.close();

            } catch (FileNotFoundException ex) {
                throw new RuntimeException("Заданный файл не найден!");
            }

            regressionResFunc res = program.calculate(x,y);
            this.tabWidget.myTableModel.insertIntoRow(5, generateResults(res, x));
            this.tabWidget.dataTable.repaint();

            drawResultPlot();
        }
    }


    private void drawResultPlot()
    {
        Graphics resultPlot = this.tabWidget.resultPLot.getGraphics();
        resultPlot.setColor(Color.RED);
        int[] xAxis = new int[22];
        int[] yAxis = new int[22];
        for (int i = 0; i < 22; i++)
        {
            xAxis[i] = i;
            yAxis[i] = i;
        }
        resultPlot.drawPolyline(xAxis, yAxis, 22);
    }

    private Object[] generateResults(regressionResFunc func, float[][] x)
    {
        Object[] result = new Float[22];
        for (int i = 0; i < 22; i++){
            result[i] = func.calculate(x[0][i], x[1][i], x[2][i]);
        }

        return result;

    }
}


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





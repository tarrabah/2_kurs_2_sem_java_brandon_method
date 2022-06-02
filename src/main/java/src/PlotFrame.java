package src;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class PlotFrame extends Frame implements ActionListener
{
    // Конструктор (аргументы - высота и ширина окна);
    private Menu fileMenu;
    private MenuBar mainMenuBar;
    private MenuItem newFileItem;
    private TabWidget tabWidget;

    private String filePath;
    private Program program;

    private int kompKey = 1;

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

        System.out.println(path);
        Float[][] x = new Float[3][22];
        Float[] y = new Float[22];

        try {
            File file = new File(String.format("/%s", path));
            Scanner scanner = new Scanner(file);
            System.out.println("file opened");
            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] splitLine = line.split(" ");
                x[0][i] = Float.parseFloat(splitLine[0]);
                x[1][i] = Float.parseFloat(splitLine[1]);
                x[2][i] = Float.parseFloat(splitLine[2]);

                y[i] = Float.parseFloat(splitLine[3]);
                i++;
            }
            scanner.close();

            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(1, x[0]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(2, x[2]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(3, x[2]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(4, y);

            regressionResFunc res = program.calculate(x,y);

            Float[] yRes = (Float[]) generateResults(res, x);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(5, yRes);
            this.tabWidget.showDataPanel.dataTable.repaint();

            Float[] yX1 = new Float[22];
            Float[] yX2 = new Float[22];
            Float[] yX3 = new Float[22];
            Float[] count = new Float[22];

            for (i = 0; i < 22; i++) {
                yX1[i] = res.getFunc1().calculate(x[0][i]);
                yX2[i] = res.getFunc2().calculate(x[1][i]);
                yX3[i] = res.getFunc3().calculate(x[2][i]);
                count[i] = new Float(i);
            }

            tabWidget.clearCharts();
            kompKey = 1;

            drawPlot(tabWidget.x1Dataset, x[0], yX1);
            drawPlot(tabWidget.x2Dataset, x[1], yX2);
            drawPlot(tabWidget.x3Dataset, x[2], yX3);
            drawPlot(tabWidget.resultDataset, count, yRes);
            drawPlot(tabWidget.resultDataset, count, y);

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException("Заданный файл не найден!");
        }

    }


    private void drawPlot(XYSeriesCollection dataset, Float[] x, Float[] y)
    {
        XYSeries data = new XYSeries(kompKey);
        kompKey ++;
        for (int i = 0; i < 22; i++)
        {
            data.add(x[i], y[i]);
        }
        dataset.addSeries(data);
    }

    private Object[] generateResults(regressionResFunc func, Float[][] x)
    {
        Object[] result = new Float[22];
        for (int i = 0; i < 22; i++){
            result[i] = func.calculate(x[0][i], x[1][i], x[2][i]);
        }

        return result;

    }
}






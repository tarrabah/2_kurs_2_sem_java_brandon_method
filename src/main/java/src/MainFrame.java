package src;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class MainFrame extends Frame implements ActionListener
{
    // Конструктор (аргументы - высота и ширина окна);
    private Menu fileMenu;
    private MenuBar mainMenuBar;
    private MenuItem newFileItem;
    private TabWidget tabWidget;

    private Program program;

    private int kompKey = 1;

    MainFrame(int height, int width)
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

        //System.out.println(path);
        Float[][] x = new Float[3][22];
        Float[] y = new Float[22];

        try {
            File file = new File(String.format("/%s", path));
            Scanner scanner = new Scanner(file);
            //System.out.println("file opened");
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
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(2, x[1]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(3, x[2]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(4, y);

            ResultsContainer resContainer = program.calculate(x,y);
            RegressionResFunc regressionRes = resContainer.ResultFunction;


            Float[] yRes = (Float[]) generateResults(x, regressionRes);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(5, yRes);
            this.tabWidget.showDataPanel.dataTable.repaint();

            Float[] count = new Float[22];

            for (i = 0; i < 22; i++) {
                count[i] = new Float(i);
            }

            tabWidget.clearCharts();
            kompKey = 1;

            drawXnPlots(tabWidget.x1Dataset, x[0], resContainer, 0);
            drawXnPlots(tabWidget.x2Dataset, x[1], resContainer, 1);
            drawXnPlots(tabWidget.x3Dataset, x[2], resContainer, 2);

            drawPlot(tabWidget.resultDataset, count, yRes, "regression");
            drawPlot(tabWidget.resultDataset, count, y, "experiment");
            tabWidget.resultPlotRenderer.setSeriesLinesVisible(0, true);
            tabWidget.resultPlotRenderer.setSeriesLinesVisible(1, false);
            tabWidget.resultPlotGotPlot.setRenderer(tabWidget.resultPlotRenderer);

            tabWidget.x1Results.TableModel.insertIntoRow(0, x[0]);
            tabWidget.x2Results.TableModel.insertIntoRow(0, x[1]);
            tabWidget.x3Results.TableModel.insertIntoRow(0, x[2]);

            for (i = 0; i < 6; i++)
            {
                tabWidget.x1Results.TableModel.insertIntoRow(i + 1, functionResSequence(x[0], resContainer.functions[0][i]));
                tabWidget.x2Results.TableModel.insertIntoRow(i + 1, functionResSequence(x[1], resContainer.functions[1][i]));
                tabWidget.x3Results.TableModel.insertIntoRow(i + 1, functionResSequence(x[2], resContainer.functions[2][i]));
            }

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException("Заданный файл не найден!");
        }

    }

    private void drawPlot(XYSeriesCollection dataset, Float[] x, Float[] y, String kompKey)
    {
        XYSeries data = new XYSeries(kompKey);
        for (int i = 0; i < 22; i++)
        {
            data.add(x[i], y[i]);
        }
        dataset.addSeries(data);
    }


    private void drawXnPlots(XYSeriesCollection dataset, Float[] x, ResultsContainer res, int functionN) //functionN = 0..2
    {
        int optimalFunctionType = res.optimalFuncIndex[functionN];

        for (int i = 0; i < 6; i++){
            String kompKey = "x" + (i + 1);

            if (i == optimalFunctionType)
                kompKey = kompKey + " Optimal";


            drawPlot(dataset, x, functionResSequence(x, res.functions[functionN][i]), kompKey);
        }
    }

    private Object[] generateResults(Float[][] x, RegressionResFunc func)
    {
        Object[] result = new Float[22];
        for (int i = 0; i < 22; i++){
            result[i] = func.calculate(x[0][i], x[1][i], x[2][i]);
        }

        return result;
    }

    private Float[] functionResSequence(Float[] x, MathFunc func)
    {

        Float[] res = new Float[22];

        for (int i = 0; i < 22; i ++){
            res[i] = func.calculate(x[i]);

        }

        return res;
    }
}





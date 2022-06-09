package src;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class MainFrame extends Frame implements ActionListener {
    private int lineNumber = 22; // количество строк
    // Конструктор (аргументы - высота и ширина окна);
    private Menu fileMenu;
    private MenuBar mainMenuBar;
    private MenuItem newFileItem;
    private TabWidget tabWidget;

    private Program program;

    private int kompKey = 1;

    MainFrame(int height, int width) {
        // Заголовок окна:
        setTitle("kursach");
        setBounds(100, 50, width, height);
        setBackground(Color.GRAY);
        setLayout(new GridLayout());

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent ve) {
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

        tabWidget = new TabWidget(lineNumber);

        add(tabWidget);
        setResizable(false);
        setVisible(true);   //Отображение окна

        program = new Program(lineNumber);
    }

    public void actionPerformed(ActionEvent e) {
        FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
        fd.setVisible(true);
        String path = fd.getDirectory() + fd.getFile();

        //System.out.println(path);
        Float[][] x = new Float[3][lineNumber];
        Float[] y = new Float[lineNumber];

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
            //fills tables
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(1, x[0]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(2, x[1]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(3, x[2]);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(4, y);

            ResultsContainer resContainer = program.calculate(x, y);
            RegressionResFunc regressionRes = resContainer.ResultFunction;

            Float[] yRes = (Float[]) generateResults(x, regressionRes);
            this.tabWidget.showDataPanel.myTableModel.insertIntoRow(5, yRes);
            this.tabWidget.showDataPanel.dataTable.repaint();

            Float[] count = new Float[lineNumber];

            for (i = 0; i < lineNumber; i++) {
                count[i] = new Float(i);
            }

            tabWidget.clearCharts();
            kompKey = 1;

            //draws plots for x[i]
            drawXnPlots(tabWidget.x1Dataset, x[0], resContainer, 0);
            drawXnPlots(tabWidget.x2Dataset, x[1], resContainer, 1);
            drawXnPlots(tabWidget.x3Dataset, x[2], resContainer, 2);


            drawPlot(tabWidget.resultDataset, count, yRes, regressionRes.toString());
            drawPlot(tabWidget.resultDataset, count, y, "experiment");

            //sets lines for data series
            tabWidget.resultPlotRenderer.setSeriesLinesVisible(0, true);
            tabWidget.resultPlotRenderer.setSeriesLinesVisible(1, false);
            tabWidget.resultPlotGotPlot.setRenderer(tabWidget.resultPlotRenderer);

            tabWidget.x1Results.TableModel.insertIntoRow(0, x[0]);
            tabWidget.x2Results.TableModel.insertIntoRow(0, x[1]);
            tabWidget.x3Results.TableModel.insertIntoRow(0, x[2]);


            tabWidget.x1Results.TableModel.insertIntoRow(1, resContainer.yXn[0]);
            tabWidget.x2Results.TableModel.insertIntoRow(1, resContainer.yXn[1]);
            tabWidget.x3Results.TableModel.insertIntoRow(1, resContainer.yXn[2]);

            for (i = 2; i < 8; i++)//fills data to xn tables
            {
                tabWidget.x1Results.TableModel.insertIntoRow(i, functionResSequence(x[0], resContainer.functions[0][i - 2]));
                tabWidget.x2Results.TableModel.insertIntoRow(i, functionResSequence(x[1], resContainer.functions[1][i - 2]));
                tabWidget.x3Results.TableModel.insertIntoRow(i, functionResSequence(x[2], resContainer.functions[2][i - 2]));
            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Заданный файл не найден!");
        }

    }

    private void drawPlot(XYSeriesCollection dataset, Float[] x, Float[] y, String kompKey) {
        XYSeries data = new XYSeries(kompKey);
        for (int i = 0; i < lineNumber; i++) {
            data.add(x[i], y[i]);
        }
        dataset.addSeries(data);
    }


    private void drawXnPlots(XYSeriesCollection dataset, Float[] x, ResultsContainer res, int functionN) //functionN = 0..2
    {
        int optimalFunctionType = res.optimalFuncIndex[functionN];

        for (int i = 0; i < 6; i++) {
            String kompKey = "y" + (i + 1) + " = " + res.functions[functionN][i].toString();//label

            if (i == optimalFunctionType)
                kompKey = kompKey + " Optimal";

            drawPlot(dataset, x, functionResSequence(x, res.functions[functionN][i]), kompKey);
        }
    }

    private Object[] generateResults(Float[][] x, RegressionResFunc func) //returns array of function vals
    {
        Object[] result = new Float[lineNumber];

        Float[] line = new Float[3];
        for (int i = 0; i < lineNumber; i++) {
            line[0] = x[0][i];
            line[1] = x[1][i];
            line[2] = x[2][i];
            result[i] = func.calculate(line);
        }

        return result;
    }

    private Float[] functionResSequence(Float[] x, MathFunc func)//almost the same as generateResults, but for MathFunc
    {
        Float[] res = new Float[lineNumber];

        for (int i = 0; i < lineNumber; i++) {
            res[i] = func.calculate(x[i]);

        }

        return res;
    }
}






package src;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

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
        setLayout(null);
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
        String path = fd.getFile();
        if (path != null)
        {

            float[][] x = new float[3][22];
            float[] y = new float[22];

            try {
                File file = new File(String.format("%s", path));
                Scanner scanner = new Scanner(file);
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

            program.calculate(x,y);
            //drawResultPlot();
        }
    }

    /*
    private void drawResultPlot()
    {
        Graphics resultPlot = this.tabWidget.resultPLot.getGraphics();
        resultPlot.setColor(Color.RED);
        int[] xAxis = new int[22];
        int[] yAxis = new int[22];
        for (int i = 0; i < 22; i++)
        {
            xAxis[i] = i;
            yAxis[i] = i*2;
        }
        resultPlot.drawPolyline(xAxis, yAxis, 22);

    }   */
}



class TabWidget extends JTabbedPane
{

    Panel resultPLot;
    TabWidget()
    {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        Panel data = new Panel();
        Panel firstParameterPLot = new Panel();
        Panel secondParameterPLot = new Panel();
        Panel thirdParameterPLot = new Panel();
        resultPLot = new Panel();

        //add panels to tab widget
        add("data", data);
        add("x1", firstParameterPLot);
        add("x2", secondParameterPLot);
        add("x3", thirdParameterPLot);
        add("results", resultPLot);
    }
}



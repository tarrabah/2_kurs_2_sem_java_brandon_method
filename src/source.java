import java.io.File;
import java.util.Scanner;


public class source
{
    public static void main(String[] args)
    {
        program prog = new program();
        prog.calculate();
    }

}

class program
{
    int i, j;
    float x[][];
    float  y[];
    float  matrix_D[][];
    float y_average;
    float y_normalized[];
    float r_yx[];
    int m = 3, n = 22;
    int ryx_order[] = { 0, 1, 2 };
    float coofs_a_b[][];
    int func_type[];
    math_func result_funcs[];


    void calculate()
    {
        x = new float[3][22];
        y = new float[22];
        matrix_D = new float[4][4];
        y_average = 0;
        y_normalized = new float[22];
        r_yx = new float[3];
        coofs_a_b = new float[3][2];
        func_type = new int[3];
        result_funcs = new math_func[3];

        get_data();
        fill_matrix_D();
        fill_ryx();
        sort_ryx();
        function_build();
        print_result_table();
        for (int i = 0; i < 3; i++)
            System.out.println(result_funcs[i]);

    }

    void get_data()
    {
        try
        {
            File file = new File("input.txt");
            Scanner inp = new Scanner(file);
            int i = 0;
            while(inp.hasNextLine())
            {
                String line = inp.nextLine();

                String[] split_line = line.split(" ");
                this.x[0][i] = Integer.parseInt(split_line[0]);
                this.x[1][i] = Integer.parseInt(split_line[1]);
                this.x[2][i] = Integer.parseInt(split_line[2]);

                this.y[i] = Float.parseFloat(split_line[3]);
                this.y_average += this.y[i];
                i++;
            }

            inp.close();

            this.y_average = this.y_average / this.n;

            for (i = 0; i < 22; i++)
                this.y_normalized[i] = this.y[i] / this.y_average;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }
    }


    float coof(float x[], float y[])//22, 22
    {
        float sum_x = 0, sum_y = 0, sum_x_y = 0, sum_x_sq = 0, sum_y_sq = 0, r;

        for (int i = 0; i < 22; i++)
        {
            sum_x += x[i];
            sum_y += y[i];
            sum_x_y += x[i] * y[i];
            sum_x_sq += x[i] * x[i];
            sum_y_sq += y[i] * y[i];
        }

        r = (n * sum_x_y - sum_x * sum_y) / (float)Math.sqrt((n * sum_x_sq - sum_x * sum_x) * (n * sum_y_sq - sum_y * sum_y));
        return r;
    }


    // заполнение матрицы matrix_D
    void fill_matrix_D()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                if (i == j)
                    this.matrix_D[i][j] = 1;
                else if (i == 3)
                    this.matrix_D[i][j] = coof(y, x[j]);
                else
                    this.matrix_D[i][j] = coof(x[i], x[j]);

                this.matrix_D[j][i] = matrix_D[i][j];
            }

        }
    }


    // Считает детерминант
    float det(int a, int b) // строка. столбец
    {
        int ind = 0;
        float d = 0;
        float els[] = new float[9];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if ((i != a) & (j != b))
                {
                    els[ind] = this.matrix_D[i][j];
                    ind++;
                }
            }
        }

        d = els[0] * els[4] * els[8] + els[1] * els[5] * els[6] + els[3] * els[7] * els[2];
        return d;
    }

    void fill_ryx()
    {
        //заполнение corellation_coof_ryx
        for (int k = 0; k < this.m; k++)
        {
            this.r_yx[k] = (float)(Math.abs(det(m, k) / Math.sqrt(det(m, m) * det(k, k))));
        }
    }

    void sort_ryx()// сортировка пузырьком; в order_corellation_coof_ryx сохраняется порядок
    {
        float fbuf;
        int ibuf;
        for (int i = 1; i < 3; i++)
        {
            for (int j = i; j < 3; j++)
            {
                if (this.r_yx[j - 1] < this.r_yx[j])
                {
                    fbuf = this.r_yx[j - 1];
                    this.r_yx[j - 1] = this.r_yx[j];
                    this.r_yx[j] = fbuf;

                    ibuf = this.ryx_order[j - 1];
                    this.ryx_order[j - 1] = this.ryx_order[j];
                    this.ryx_order[j] = ibuf;
                }
            }
        }
    }


    void function_build()
    {
        for (int i = 0; i < 3; i++)
        {
            math_func result = mnk(this.x[this.ryx_order[i]], this.y_normalized);
            this.result_funcs[i] = result;

            for (j = 0; j < 22; j++)
            {
                this.y_normalized[j] = this.y_normalized[j] / result.calc(x[this.ryx_order[i]][j]);
            }
        }

        //string function_as_string = "y = " + to_string(y_average);
    }


    math_func mnk(float x[], float y[])
    {
        float A[] = new float[6];
        float B[] = new float[6];
        float a[] = new float[6];
        float b[] = new float[6];
        float deviation_sum[] = new float[6]; // набор из 6 пар a b, описанных ниже некоторые надо преодразовать в соответствии с уравнениями ниже
	    /*
	    все уравнения приводятся к виду линейному виду: Y = A * X + B
	    function type 1.

		    y = a * x + b

		    A = a => a = A
		    B = b => b = B

	    function type 2.

		    y = 1/(a * x + b) => 1/y = a * x + b

		    Y = 1/y

	    function type 3.

		    y = a/x + b

		    X = 1/x

	    function type 4.

		    y = b * x^a => ln y = ln b + a * ln x

		    Y = ln y
		    B = ln b => b = e^B
		    A = a
		    x = ln x

	    function type 5.

		    y = b * e ^( a * x ) => ln y = ln b + a * x

		    Y = ln y
		    B = ln b => b = e^B

	    function type 6.

		    y = a * ln x + b

		    X = ln x
	    */

        // подготовка массивов ввода по условиям выше
        float y_pow_min_one[] = new float[22];
        float x_pow_min_one[] = new float[22];
        float ln_x[] = new float[22];
        float ln_y[] = new float[22];


        for (int i = 0; i < 22; i++)
        {
            y_pow_min_one[i] = 1 / y[i];
            x_pow_min_one[i] = 1 / x[i];
            ln_x[i] = (float)Math.log(x[i]);
            ln_y[i] = (float)Math.log(y[i]);
        }

        float res[] = new float[2]; //stores function result

        //type 1
        res = coofs_A_B(x, y);
        A[0] = res[0];
        B[0] = res[1];
        //type 2
        res = coofs_A_B(x, y_pow_min_one);
        A[1] = res[0];
        B[1] = res[1];
        //type 3
        res = coofs_A_B(x_pow_min_one, y);
        A[2] = res[0];
        B[2] = res[1];
        //type 4
        res = coofs_A_B(x_pow_min_one, y_pow_min_one);
        A[3] = res[0];
        B[3] = (float)Math.exp(res[1]);
        //type 5
        res = coofs_A_B(x, ln_y);
        A[4] = res[0];
        B[4] = (float)Math.exp(res[1]);
        //type 6
        res = coofs_A_B(ln_x, y);
        A[5] = res[0];
        B[5] = res[1];

        //обратное преобразование коофицентов
        //type 1
        a[0] = A[0];
        b[0] = B[0];
        //type 2
        a[1] = A[1];
        b[1] = B[1];
        //type 3
        a[2] = A[2];
        b[2] = B[2];
        //type 4
        a[3] = A[3];
        b[3] = (float)Math.exp(B[3]);
        //type 5
        a[4] = A[4];
        b[4] = (float)Math.exp(B[4]);
        //type 6
        a[5] = A[5];
        b[5] = B[5];

        //подсчёт сумм модулей (нужны квадраты) отклонений
        for (int i = 0; i < 22; i++)
        {
            deviation_sum[0] += Math.abs(y[i] - (a[0] * x[i] + b[0]));
            deviation_sum[1] += Math.abs(y[i] - 1/(a[1] * x[i] + b[1]));
            deviation_sum[2] += Math.abs(y[i] - (a[2] / x[i] + b[2]));
            deviation_sum[3] += Math.abs(y[i] - (Math.pow(x[i], a[3]) * b[3]));
            deviation_sum[4] += Math.abs(y[i] - (Math.exp(a[4] * x[i]) * b[4]));
            deviation_sum[5] += Math.abs(y[i] - (a[5] * Math.log(x[i]) + b[5]));
        }

        float mn = deviation_sum[0];
        int function_type = 7;

        for (int i = 0; i < 6; i++)
        {
            if (deviation_sum[i] <= mn)
            {
                function_type = i;
                mn = deviation_sum[i];
            }
        }

        return new math_func(a[function_type], b[function_type], function_type);
    }

    float[] coofs_A_B(float x[], float y[]) // returns a and b
    {
        float sum_x = 0, sum_y = 0, sum_x_y = 0, sum_x_sq = 0;
        //подсчёт коофицентов
        for (int i = 0; i < 8; i++)
        {
            sum_x += x[i];
            sum_y += y[i];
            sum_x_y += x[i] * y[i];
            sum_x_sq += x[i] * x[i];
        }

        //вычисление и фактический возврат
        float res[] = new float[2];
        res[0] = (n * sum_x_y - sum_x * sum_y) / (n * sum_x_sq - sum_x * sum_x);
        res[1] = (sum_x_sq * sum_y - sum_x * sum_x_y) / (n * sum_x_sq - sum_x * sum_x);

        return res;
    }

    void print_result_table()
    {
        for (int i = 0; i < 22; i++)
        {
            float y_regression = (y_average * result_funcs[0].calc(x[ryx_order[0]][i]) *
                    result_funcs[1].calc(x[ryx_order[1]][i]) *
                    result_funcs[2].calc(x[ryx_order[2]][i]));

            System.out.println(x[0][i] + " " +  x[1][i] + " " + x[2][i] + " " + y[i] + " " + y_regression);
        }
    }
}

class math_func
{
    float a;
    float b;
    int type;

    public math_func(float a, float b, int type)
    {
        this.a = a;
        this.b = b;
        this.type = type;
    }

    float calc(float x)
    {
        if (type == 0)
            return a * x + b;
        else if (type == 1)
            return 1 / (a * x + b);
        else if (type == 2)
            return a / x + b;
        else if (type == 3)
            return (float)Math.pow(x, a) * b;
        else if (type == 4)
            return (float)Math.exp(a * x) * b;
        else if (type == 5)
            return a * (float)Math.log(x) + b;
        return 0;
    }

    @Override
    public String toString()
    {
       if (type == 0)
           return a + " * x + " + b;
       else if (type == 1)
           return "1 / (" + a + " * x + " + b + ")";
       else if (type == 2)
           return a + " / x + " + b;
       else if (type == 3)
           return "x ^ " + a + " * " + b;
       else if (type == 4)
           return "e ^ (x * " + a + ") * " + b;
       else if (type == 5)
           return a + " * log(x) + " + b;
       return "";
    }
}
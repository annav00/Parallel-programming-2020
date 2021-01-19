package test.ru.spbstu.telematics.java;

import java.time.Duration;
import java.time.Instant;
import ru.spbstu.telematics.java.MatrixHelper;

public class LU_strip {

static int MAX = 100;
static String s="";

public static void luDecomposition(double [][]mat, double []B, int n, int p, double []X){

    double []Y = new double[n];
    double [][]lower = new double[n][n];
    double [][]upper = new double[n][n];

      for (int i = 0; i < n; i++){
     		int min = (n < p + i + 1) ? n : p + i + 1;
             for (int j = i; j < min; j++)
             {
                double sum = 0;
     			int max = (0 > j - p) ? 0 : j - p;
                for (int k = max; k < i; k++){
                System.out.print("K = " + k + " j = " + j + "\n");
                    sum += (lower[i][k] * upper[k][j]);
                 }
                 System.out.print("Upper i = " + i + " j = " + j + "\n");
                upper[i][j] = mat[i][j] - sum;
             }
             System.out.print("new" + "\n");
             for (int j = i; j < min; j++) {
                double sum = 0;
     			int max = (0 > j - p) ? 0 : j - p;
                for (int k = max; k < i; k++)
                    sum += (lower[j][k] * upper[k][i]);
                lower[j][i] = (mat[j][i] - sum) / upper[i][i];
             }
         }

    for(int i = 0; i < n; i++){
        double sum = 0;
        int max = (0 > i - p) ? 0 : i - p;
        for(int k = max; k < i; k++)
            sum += lower[i][k] * Y[k];
        Y[i] = B[i] - sum;
    }

    for(int k = n-1; k >= 0; k--){
        double sum = 0;
        int min = (n < p + k) ? n : p + k;
        for(int j = k; j < min; j++)
            sum += upper[k][j] * X[j];
        X[k] = (Y[k] - sum) / upper[k][k];
    }
}

static String setw(int noOfSpace)
{
    s="";
    for(int i = 0;i<noOfSpace;i++)
        s+=" ";
    return s;
}

public static void main(String arr[])
{
    double mat[][] = { { 2, -1, 0, 0},
                    { -4, 6, 3, 0 },
                    { 3, -2, 8, 3},
                    { 0, 7, 4, 2} };
    double B[] = {1, 1, 7, 2};

    int n = 4;
    int p = 2;
    double []X = new double [n];
    double  matrix[][] = new double[n][n];
    double  matrixB[] = new double[n];
    matrix = MatrixHelper.getRandomMatrix(n, p);
    matrixB = MatrixHelper.getRandomB(n);
    Instant start = Instant.now();
    luDecomposition(mat, B, n, p, X);
    Instant finish = Instant.now();

    /*MatrixHelper.printMatrix(lower, n, "Lower:");
    MatrixHelper.printMatrix(upper, n, "Upper:");*/
    MatrixHelper.printB(X, n, "X:");

    System.out.printf("Working time = %d milliseconds%n", Duration.between(start, finish).toMillis());
}
}
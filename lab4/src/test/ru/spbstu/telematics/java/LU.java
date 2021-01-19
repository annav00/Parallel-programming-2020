package test.ru.spbstu.telematics.java;

public class LU {

static int MAX = 100;
static String s="";

public static void luDecomposition(double [][]mat, double []B, int n, double []X){
    double [][]lower = new double[n][n];
    double [][]upper = new double[n][n];
    double []Y = new double[n];

      for (int i = 0; i < n; i++){
        for (int k = i; k < n; k++){
            double sum = 0;
            for (int j = 0; j < i; j++)
                sum += (lower[i][j] * upper[j][k]);
            upper[i][k] = mat[i][k] - sum;
        }
        for (int k = i; k < n; k++) {
              double sum = 0;
              for (int j = 0; j < i; j++)
                  sum += (lower[k][j] * upper[j][i]);
              lower[k][i] = (mat[k][i] - sum) / upper[i][i];
        }
      }

     for(int i = 0; i < n; i++){
        double sum = 0;
        for(int k = 0; k < i; k++)
            sum += lower[i][k] * Y[k];
        Y[i] = B[i] - sum;
     }

     for(int k = n-1; k >= 0; k--){
        double sum = 0;
        for(int j = k; j < n; j++)
            sum += upper[k][j] * X[j];
        X[k] = (Y[k] - sum) / upper[k][k];
     }

    /*System.out.println(setw(2) + "     Lower Triangular" + setw(10) + "Upper Triangular" + setw(5) + "X");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
            System.out.print(setw(4) + lower[i][j] + "\t");
        System.out.print("\t");

        for (int j = 0; j < n; j++)
            System.out.print(setw(4) + upper[i][j] + "\t");
        System.out.print(setw(4) + X[i] + "\t");
        System.out.print("\n");
    }*/
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
    double []X = new double [n];
    luDecomposition(mat, B, n, X);
}
}

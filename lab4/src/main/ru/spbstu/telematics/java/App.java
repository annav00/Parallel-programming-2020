package main.ru.spbstu.telematics.java;

import java.time.Duration;
import java.time.Instant;
import test.ru.spbstu.telematics.java.LU_strip;
import test.ru.spbstu.telematics.java.LU;

public class App {
public static void main(String arr[]) throws Exception{
    double mat[][] = { { 2, -1, 0, 0},
                    { -4, 6, 3, 0 },
                    { 3, -2, 8, 3},
                    { 0, 7, 4, 2} };
    double B[] = {1, 1, 7, 2};

    /*int n = 4000;
    int p = 600;
    double []X = new double [n];
    double  matrix[][] = new double[n][n];
    double  matrixB[] = new double[n];*/
    int k_max = 2;
	
    for(int n = 50; n < 1500; n += 100){
        System.out.printf("_____________________________\n");
        System.out.printf("n = " + n + "\n");
        for(int p = n/8; p < n/3; p += n/8){
            System.out.printf("*    *    *    *    *    *    *\n");
            System.out.printf("p = " + p + "\n");
            double []X = new double [n];
            double  matrix[][] = new double[n][n];
            double  matrixB[] = new double[n];
            int t1 = 0, t2 = 0;
	
            for(int k = 0; k < k_max; k++){
                matrix = MatrixHelper.getRandomMatrix(n, p);
                matrixB = MatrixHelper.getRandomB(n);

                Instant start = Instant.now();
                Slae.luDecomposition(matrix, matrixB, n, p, X);
                Instant finish = Instant.now();
                //MatrixHelper.printB(X, n, "X:");
                t1 += Duration.between(start, finish).toMillis();
                for(int i = 0; i < n; i++)
                    X[i] = 0;

                start = Instant.now();
                LU.luDecomposition(matrix, matrixB, n, p, X);
                finish = Instant.now();
                //MatrixHelper.printB(X, n, "X:");
                t2 += Duration.between(start, finish).toMillis();
                for(int i = 0; i < n; i++)
                    X[i] = 0;
            }

            System.out.printf("Параллельный: %d milliseconds%n", t1 / k_max);
            System.out.printf("Последовательный: %d milliseconds%n", t2 / k_max);
    /*MatrixHelper.printMatrix(lower, n, "Lower:");
    MatrixHelper.printMatrix(upper, n, "Upper:");*/
}
}

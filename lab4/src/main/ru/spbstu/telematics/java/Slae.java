package main.ru.spbstu.telematics.java;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.CountDownLatch;

public class Slae {
static double sum = 0;

public static void DoThread(double [][]matrix1, double [][]matrix2, int i, int j, int p, int max) throws Exception{
    int min = (0 > j - p) ? 0 : j - p;
    int tmp = (max - min) / 2;
    if(min != max){
        CountDownLatch countDown = new CountDownLatch(2);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        Thread firstThread = new Thread(new Multiplication(i, j, min, min + tmp, matrix1, matrix2, countDown, lock));
        firstThread.start();
        Thread secondThread = new Thread(new Multiplication(i, j, min + tmp, max, matrix1, matrix2, countDown, lock));
        secondThread.start();
        countDown.await();
    }
}

public static void luDecomposition(double [][]mat, double []B, int n, int p, double []X) throws Exception{

    double []Y = new double[n];
    double [][]lower = new double[n][n];
    double [][]upper = new double[n][n];

      for (int i = 0; i < n; i++){
     		int min = (n < p + i + 1) ? n : p + i + 1;
             for (int j = i; j < min; j++)
             {
                sum = 0;
                int max = (0 > j - p) ? 0 : j - p;
                if(max != i)
                    DoThread(lower, upper, i, j, p, i);
                upper[i][j] = mat[i][j] - sum;
             }
             for (int j = i; j < min; j++) {
                sum = 0;
                int max = (0 > j - p) ? 0 : j - p;
                if(max != i)
     			    DoThread(lower, upper, j, i, p, i);
                lower[j][i] = (mat[j][i] - sum) / upper[i][i];
             }
         }
    for(int i = 0; i < n; i++){
        sum = 0;
        int max = (0 > i - p) ? 0 : i - p;
        for(int k = max; k < i; k++)
            sum += lower[i][k] * Y[k];
        Y[i] = B[i] - sum;
    }

    for(int k = n-1; k >= 0; k--){
        sum = 0;
        int min = (n < p + k) ? n : p + k;
        for(int j = k; j < min; j++)
            sum += upper[k][j] * X[j];
        X[k] = (Y[k] - sum) / upper[k][k];
    }
}

    private static class Multiplication implements Runnable {
        private final int i;
        private final int j;
        private final int min;
        private final int max;
        private final double[][] matrix1;
        private final double[][] matrix2;
        CountDownLatch countDown;
        ReentrantReadWriteLock locker;

        Multiplication(int i, int j, int min, int max, double[][] matrix1, double[][] matrix2,
        CountDownLatch countDown, ReentrantReadWriteLock lock) {
            this.i = i;
            this.j = j;
            this.min = min;
            this.max = max;
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.countDown = countDown;
            this.locker = lock;
        }

        public void run() {
            double tmp = 0;
            for (int k = min; k < max; k++)
                tmp += matrix1[i][k] * matrix2[k][j];
            locker.writeLock().lock();
            try{
                sum += tmp;
            }
            finally{
                locker.writeLock().unlock();
            }
            countDown.countDown();
        }
    }
}

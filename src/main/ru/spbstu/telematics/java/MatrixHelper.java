package main.ru.spbstu.telematics.java;

import java.util.Random;


 public class MatrixHelper {
 static int MAX = 100;
 static String s="";

// Создание ленточной матрицы размерности n и полуширины р
 public static double[][] getRandomMatrix(int n, int p) {
         double[][] Matrix = new double[n][n];
         for (int i = 0; i < n; i++) {
             int max = (n < p + i + 1) ? n : p + i + 1;
             int min = (0 > i - p) ? 0 : i - p;
             for (int j = min; j < max; j++) {
                     Matrix[i][j] = new Random().nextInt();
                 }
         }

         return Matrix;
     }
 // Создание вектора значений размерности n
 public static double[] getRandomB(int n) {
        double[] B = new double[n];
        for (int i = 0; i < n; i++)
            B[i] = new Random().nextInt();

        return B;
 }

 // Вывод матрицы размерности n

 public static void printMatrix(double [][]matrix, int n, String s){
    System.out.println(setw(2) + s + "\n");

     for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++)
         {
             String str = String.format("%.3f", matrix[i][j]);
             System.out.print(setw(4) + str + "\t");
         }
         System.out.print("\n");
     }
     System.out.print("\n");
 }

 public static void printB(double []matrix, int n, String s){
    System.out.println(setw(2) + s + "\n");
     for (int i = 0; i < n; i++) {
        String str = String.format("%.3f", matrix[i]);
        System.out.print(str + "\n");
     }
 }
 static String setw(int noOfSpace)
 {
     s="";
     for(int i = 0;i<noOfSpace;i++)
         s+=" ";
     return s;
 }
}

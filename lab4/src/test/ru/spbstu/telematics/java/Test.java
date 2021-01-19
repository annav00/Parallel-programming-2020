package test.ru.spbstu.telematics.java;

import main.ru.spbstu.telematics.java.MatrixHelper;
import main.ru.spbstu.telematics.java.Slae;

import org.junit.*;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class Test {
    @Before
        public void setUp() {
            System.out.println("Code executes before each test method");
        }

    void check(double[] resSerial, double[] resParallel) {
        for (int j = 0; j < resSerial.length; j++)
            assertArrayEquals(resSerial[j],resParallel[j],0.00001);

        if (resSerial.length != resParallel.length) {
            fail();
        }
        else {
            for (int i = 0; i < resSerial.length; i++) {
                        if (Double.compare(resSerial[i],resParallel[i]) != 0)
                            fail();
            }
        }
    }

    @Test
    public void MatrixMulTest() {
        int n = 4;
        int p = 2;
        double mat[][] = { { 2, -1, 0, 0},
                            { -4, 6, 3, 0 },
                            { 3, -2, 8, 3},
                            { 0, 7, 4, 2} };
        double B[] = {1, 1, 7, 2};

        double []Xres = {79/164, -3/82, 43/41, -159/164};
        double []X = new double [n];
        Slae.luDecomposition(matrix, matrixB, n, p, X);

        try() {
            check(X, Xres);
        }
        catch(IOException str_ex) {
            System.out.println(str_ex.getMessage());
        }
    }

    @After
        public void afterMethod() {
            System.out.println("Code executes after each test method");
        }
}

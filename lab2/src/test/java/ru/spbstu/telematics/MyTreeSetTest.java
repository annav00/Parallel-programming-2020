package test.java.ru.spbstu.telematics;

import java.io.*;
import java.util.Random;

import org.junit.*;
import main.java.ru.spbstu.telematics.MyTreeSet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;

public class MyTreeSetTest {
    static int size = 10;
    static TreeSet<Integer> treeSet = new TreeSet<>();
    static MyTreeSet<Integer> myTreeSet = new MyTreeSet<>();



    @Before
    public void beforeMethod() {
        System.out.println("Code executes before each test method");
    }

    @Test
    public void Test0() {

        for (int i = 0; i < size; i++) {
            myTreeSet.add(i*i);
            treeSet.add(i*i);
        }
        assertEquals(myTreeSet.size(), treeSet.size());
    }

    @Test
    public void Test1() {
        TreeSet<Integer>  treeSet1 = new TreeSet<>();
        MyTreeSet<Integer> myTreeSet1 = new MyTreeSet<>();
        assertEquals(myTreeSet1.isEmpty(), treeSet1.isEmpty());
        assertEquals(myTreeSet.isEmpty(), treeSet.isEmpty());
    }

    @Test
    public void Test2() {
        assertEquals(myTreeSet.size(), treeSet.size());
    }

    @Test
    public void Test3() {
        assertEquals(myTreeSet.contains(-1), treeSet.contains(-1));
        assertEquals(myTreeSet.contains(1), treeSet.contains(1));
    }

    @Test
    public void Test4() {
        MyTreeSet<Integer> newMytreeSet = new MyTreeSet<>();
        newMytreeSet.add(1);
        newMytreeSet.add(2);
        newMytreeSet.add(3);
        newMytreeSet.add(4);
        newMytreeSet.add(5);
        newMytreeSet.add(6);

        TreeSet<Integer> expectedSet = new TreeSet<>();
        expectedSet.add(2);
        expectedSet.add(3);
        expectedSet.add(4);
        expectedSet.add(5);

        MyTreeSet<Integer> subSet = newMytreeSet.subSet(2, 6);
        assertEquals(expectedSet, subSet.get());
    }

    @Test
    public void Test5() {
        myTreeSet.add(7);
        treeSet.add(7);
        assertTrue(myTreeSet.contains(7));
        assertEquals(myTreeSet.contains(7), treeSet.contains(7));
    }

    @Test
    public void Test6() {
        myTreeSet.remove(-1);
        treeSet.remove(-1);
        assertTrue(!myTreeSet.contains(-1));
        assertEquals(myTreeSet.contains(-1), treeSet.contains(-1));
    }

    @Test
    public void Test7() {
        assertEquals(myTreeSet.subSet(2, 4).size(), treeSet.subSet(2, 4).size());
    }

    @Test
    public void Test8() {
        myTreeSet.clear();
        treeSet.clear();
        assertTrue(myTreeSet.isEmpty());
    }

    @After
    public void afterMethod() {
        System.out.println("Code executes after each test method");
    }
}

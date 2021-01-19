package test;

import java.io.*;
import java.util.Random;

import org.junit.*;
import main.RewriteToFile;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class RewriteTest {

    private String file = "test_file.txt";
    String str = "";

    @Before
    public void setUp() {
        System.out.println("Code executes before each test method");
    }

    @Test
    public void Test1() {
        Random rand = new Random();
        StringBuilder text = new StringBuilder();
        StringBuilder r_text = new StringBuilder();
        StringBuilder file_name = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int size = alphabet.length();

        for(int i = 0; i < 20; i++)
            text.append(alphabet.charAt(rand.nextInt(size)));
        for(int i = 0; i < 8; i++)
            file_name.append(alphabet.charAt(rand.nextInt(size)));
        file_name.append(".txt");

        RewriteToFile file = new RewriteToFile(file_name.toString());
        file.WriteToFile(text.toString());

        try(FileReader reader = new FileReader(file_name.toString())) {
            int c;
            while((c = reader.read()) != -1) {
                r_text.append((char)c);
            }
        }
        catch(IOException str_ex) {
            System.out.println(str_ex.getMessage());
        }

        assertEquals(text.toString(), r_text.toString());
    }

    @Test
    public void Test2() {
        Random rand = new Random();
        StringBuilder text = new StringBuilder();
        StringBuilder r_text = new StringBuilder();
        StringBuilder file_name = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int size = alphabet.length();

        for(int i = 0; i < 20; i++)
            text.append(alphabet.charAt(rand.nextInt(size)));
        for(int i = 0; i < 8; i++)
            file_name.append(alphabet.charAt(rand.nextInt(size)));
        file_name.append(".txt");

        RewriteToFile file = new RewriteToFile(file_name.toString());
        file.WriteToFile("ABCDabcd");
        file.WriteToFile(text.toString());

        try(FileReader reader = new FileReader(file_name.toString())) {
            int c;
            while((c = reader.read()) != -1) {
                r_text.append((char)c);
            }
        }
        catch(IOException str_ex) {
            System.out.println(str_ex.getMessage());
        }

        assertEquals(text.toString(), r_text.toString());
    }

    @After
    public void afterMethod() {
        System.out.println("Code executes after each test method");
    }
}

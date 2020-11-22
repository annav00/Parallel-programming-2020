package main;

import java.io.*;

public class RewriteToFile {
    public String file_name;

    public RewriteToFile(String file_name) {
        this.file_name = file_name;
    }

    public void WriteToFile(String text) {
        try(FileWriter rewrite = new FileWriter(this.file_name, false)) {
            rewrite.write(text);
        }
        catch (IOException str_ex) {
            System.out.println(str_ex.getMessage());
        }
    }
}

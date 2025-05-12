package org.example;

import org.example.editions.*;
import org.example.workers.*;


import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Demo {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        /*Path path;
        FileWriter writer;
        do{
            System.out.println("Please enter a unique filename that we can write to. Be sure to include the .txt extension");
            writer = new FileWriter("otchet.txt");


            path = Paths.get(String.valueOf(writer));
            System.out.printf("The file name provided %s", Files.exists(path) ? "already exists":"does not exists");
        } while (Files.exists(path));

        System.out.println("Made it past getting the file name.");
        Formatter outputStream = new Formatter(writer);
        String userInput;
        do{
            System.out.println("Enter some text that will go in the text file: ");
            userInput = scanner.nextLine();

            if(!userInput.equalsIgnoreCase("-1")){
                outputStream.format(userInput + "\n");
            }
        }while (userInput.equalsIgnoreCase("-1"));

        outputStream.close();*/
        try {

            File tempFile = File.createTempFile("_temp_", ".txt");

            System.out.println("Temporary file created: " + tempFile.getAbsolutePath());
            tempFile.canWrite();
            tempFile.canRead();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write("Something added.");
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile);
            } else {
                System.out.println("Desktop suck at this system.");
            }
                tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
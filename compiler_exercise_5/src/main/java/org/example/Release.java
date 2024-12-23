package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Release {

    public static void main(String args[]) throws IOException, InterruptedException {

        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");

        String sourceFile = "input.c";
        // T: named correctly the output file (START)
        String outputFile = "a.out";
        if(isWindows) {
            outputFile = "a.exe";
        }
        // T: named correctly the output file (END)


        
        ProcessBuilder processBuilder = new ProcessBuilder("gcc", sourceFile, "-o", outputFile);

        // T: redirect the error stream
        processBuilder.redirectErrorStream(true);

        try {
            // T: Start the process
            Process process = processBuilder.start();

            // T: read the output from the shell
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // T: wait until the preocess end
            int exitCode = process.waitFor();
            System.out.println("Process terminated with exitCoded: " + exitCode);

            if (exitCode == 0) {
                System.out.println("Compilation completed with success.");
            } else {
                System.out.println("Error during compilation.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

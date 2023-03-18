import java.io.*;
import java.util.ArrayList;

// Logger class
public class Log {
    protected static ArrayList<String> messages = new ArrayList<>();
    static File fileName;

    protected Log(String name, int d) {
        // Creating the file with the day
        fileName = new File("Logger-" + d + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("\t\t\t*** " + name + " Day " + d + " ***");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void log(String message) {
        messages.add(message);
    }

    protected static void addObject(String name, int d) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> oldFile = new ArrayList<>();
            while(reader.ready()) {
                oldFile.add(reader.readLine());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for(String m:oldFile) {
                    writer.write(m);
                    writer.newLine();
                }
                writer.newLine();
                writer.write("\t\t\t*** " + name + " Day " + d + " ***");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    protected static void simulateResults() {
        ArrayList<String> files = new ArrayList<>();
        for(int i = 1;i < 32;i++) {
            try(BufferedReader reader = new BufferedReader(new FileReader("Logger-"+i+".txt"))) {
                while(reader.ready()) {
                    files.add(reader.readLine());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("SimResults.txt"))) {
            for(String s:files) {
                writer.write(s);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void close() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> oldFile = new ArrayList<>();
            while(reader.ready()) {
                oldFile.add(reader.readLine());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for(String m:oldFile) {
                    writer.write(m);
                    writer.newLine();
                }
                for(String m: messages) {
                    writer.write(m);
                    writer.newLine();
                }
                messages.clear();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
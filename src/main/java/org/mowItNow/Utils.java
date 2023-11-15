package org.mowItNow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static List<String> readContent(String fileName) {
        String folder = "mowerTasksConfigurations";
        InputStream fileStream = Utils.class.getClassLoader().getResourceAsStream(STR."\{folder}/\{fileName}");
        List<String> content =  new ArrayList<>();

        logger.debug(STR."Content of file \{fileName} ");
        try (Scanner scanner = new Scanner(fileStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                content.add(line);
            }
        } catch (Exception e) {
            logger.error(STR."There is an issue when reading the file  \{fileName}" );
        }
        return content;
    }

    public static List<String> getFilesFromResources(String folderPath) {
        InputStream folderStream = Utils.class.getClassLoader().getResourceAsStream(folderPath);

        List<String> fileNames = new ArrayList<>();

        try (Scanner scanner = new Scanner(folderStream)) {
            while (scanner.hasNextLine()) {
                String fileName = scanner.nextLine();
                fileNames.add(fileName);
            }
        } catch (Exception e) {
            logger.error(STR."There was an issue when reading folder \{ folderPath }" , e);

        }

        return fileNames;
    }

    public static void writeToFile(String fileName, String content) throws IOException {
        // Create a FileWriter and write content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(fileName).toFile()))) {
            writer.write(content);
        } catch (IOException e) {
            logger.error(STR."There was an issue wirtting result of \{ fileName }" , e);
        }
        logger.info(STR."File written to: \{ fileName }" );
    }
}

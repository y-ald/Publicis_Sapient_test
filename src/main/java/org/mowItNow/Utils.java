package org.mowItNow;

import org.mowItNow.mower.service.MowerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static List<Path> getFilesFromResources(String folderPath) throws IOException, URISyntaxException {
        // Use the ClassLoader to obtain resources from the classpath
        ClassLoader classLoader = Utils.class.getClassLoader();
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(folderPath));

        Path folderPathInResources = Paths.get(resourceUrl.toURI());
        return Files.walk(folderPathInResources)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }

    public static void writeToFile(File filePath, String content) throws IOException {
        // Create a FileWriter and write content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            logger.error(STR. "There was an issue wirtting result of \{ filePath }" , e);
        }
        logger.info(STR. "File written to: \{ filePath }" );
    }
}

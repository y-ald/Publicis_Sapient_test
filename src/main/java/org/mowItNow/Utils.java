package org.mowItNow;

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
    public static List<Path> getFilesFromResources(String folderPath) throws IOException, URISyntaxException {
        // Use the ClassLoader to obtain resources from the classpath
        ClassLoader classLoader = Utils.class.getClassLoader();
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(folderPath));

        // Get the Path for the resource folder
        Path folderPathInResources = Paths.get(resourceUrl.toURI());

        // List all files in the folder
        return Files.walk(folderPathInResources)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }
}

package org.mowItNow;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SpringBootApplication
public class Hello {
    private static Logger logger = LoggerFactory.getLogger(Hello.class);
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        logger.debug("Example log from {}", Hello.class);
        /*Runnable task = () -> {
            for(int i = 0; i < 5; i++) {
                logger.info("inside Runnable {}", Thread.currentThread());
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream is = classloader.getResourceAsStream("test.txt");
                try {
                    logger.info("reading file {}", is.read());
                    logger.info("inside file thread {}", Thread.currentThread());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread virtualThread =  Thread.ofVirtual()
                .unstarted(task);
        virtualThread.start();
        virtualThread.join();*/
        List<Path> configFiles = getFilesFromResources("mowerTasksConfigurations");

        try {

            // Create a fixed thread pool
            ExecutorService executor = Executors.newFixedThreadPool(Math.min(configFiles.size(), 10));

            // Create a list to store CompletableFuture instances
            List<CompletableFuture<Void>> futures = configFiles.stream()
                    .map(path -> CompletableFuture.runAsync(() -> processFile(path), executor))
                    .collect(Collectors.toList());

            // Combine all CompletableFuture instances into a single CompletableFuture
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            // Wait for all tasks to complete
            allOf.get();

            // Shutdown the executor
            executor.shutdown();

        } catch ( InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        SpringApplication.run(MowerMove.class, args);

    }

    private static void processFile(Path filePath) {
        // Your file processing logic goes here
        // For example, you can read the contents of the file
        try {
            List<String> lines = Files.readAllLines(filePath);
            // Perform your processing on 'lines'
            System.out.println("Processing file: " + filePath.getFileName());
            logger.info("line  .............; {}", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> getFilesFromResources(String folderPath) throws IOException, URISyntaxException, URISyntaxException {
        // Use the ClassLoader to obtain resources from the classpath
        ClassLoader classLoader = Hello.class.getClassLoader();
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(folderPath));

        // Get the Path for the resource folder
        Path folderPathInResources = Paths.get(resourceUrl.toURI());

        // List all files in the folder
        return Files.walk(folderPathInResources)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }


}
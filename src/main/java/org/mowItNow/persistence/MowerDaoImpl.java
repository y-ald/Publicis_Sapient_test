package org.mowItNow.persistence;

import org.mowItNow.models.Mower;
import org.mowItNow.models.MowerTask;
import org.mowItNow.models.Orientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MowerDaoImpl implements MowerDao{
    private static Logger logger = LoggerFactory.getLogger(MowerDaoImpl.class);

    @Override
    public List<MowerTask> getAllMowerTasks(Path filePath) throws IllegalFormatException {
        try {
            List<String> lines = Files.readAllLines(filePath);
            if(lines.size()%2 != 1) {
                throw new RuntimeException("the file is not well formatted");
            }
            List<Integer> lawnSize = getLawnSize(lines.get(0));
            logger.info("************LawnSize: {}", lawnSize);
            return getMowerInstruction(
                    lines.subList(1, lines.size()),
                    lawnSize.get(0),
                    lawnSize.get(1)
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MowerTask> getMowerInstruction(List<String> controls, int x_lawn_corner, int y_lawn_corner) {
        List<MowerTask> mowerControls = new ArrayList<>();
        Mower mower = null;
        int index = 0;
        for (String line: controls) {
            if (index % 2 == 0) {
                String[] lineChar = line.split(" ");
                mower = new Mower(
                        UUID.randomUUID(),
                        Integer.parseInt(lineChar[0]),
                        Integer.parseInt(lineChar[1]),
                        Orientation.valueOf(lineChar[2]),
                        x_lawn_corner,
                        y_lawn_corner
                );
                logger.info("************  Mower created : {}", mower);

            } else {
                if (mower != null) {
                    mowerControls.add(new MowerTask(mower, line));
                    logger.info("************  mowerControls created : {}", mowerControls);

                    mower = null;
                }
            }
            index += 1;
        }

        return mowerControls;
    }

    private List<Integer> getLawnSize(String line) {
        return Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}

//import java.io.File;
//        import java.io.FileWriter;
//        import java.io.IOException;
//        import java.io.PrintWriter;
//
//public class WriteToFileInResourceDirectory {
//    public static void main(String[] args) {
//        try {
//            // Get the resource directory path
//            String resourceDirectory = System.getProperty("user.dir") + "/src/main/resources/";
//
//            // Specify the file name
//            String fileName = "example.txt";
//
//            // Create the file object
//            File file = new File(resourceDirectory + fileName);
//
//            // Check if the file already exists
//            if (file.exists()) {
//                System.out.println("File already exists.");
//            } else {
//                // Create a FileWriter and PrintWriter to write to the file
//                FileWriter fileWriter = new FileWriter(file);
//                PrintWriter printWriter = new PrintWriter(fileWriter);
//
//                // Write some content to the file
//                printWriter.println("Hello, this is a test!");
//
//                // Close the resources
//                printWriter.close();
//                fileWriter.close();
//
//                System.out.println("File created successfully.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

//import java.io.File;
//        import java.io.FileWriter;
//        import java.io.IOException;
//        import java.net.URL;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//        import java.nio.file.StandardCopyOption;
//
//public class WriteToResourceDirectory {
//
//    public static void main(String[] args) {
//        try {
//            // Get the URL of the resources directory
//            URL resourceUrl = WriteToResourceDirectory.class.getClassLoader().getResource("resources");
//
//            if (resourceUrl == null) {
//                System.out.println("Resource directory not found");
//                return;
//            }
//
//            // Convert the URL to a Path
//            Path resourcePath = Path.of(resourceUrl.toURI());
//
//            // Write a file to the resource directory
//            writeToFile(resourcePath.resolve("example.txt").toString(), "Hello, World!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void writeToFile(String filePath, String content) throws IOException {
//        // Create a FileWriter and write content to the file
//        try (FileWriter writer = new FileWriter(filePath)) {
//            writer.write(content);
//        }
//
//        System.out.println("File written to: " + filePath);
//    }
//}
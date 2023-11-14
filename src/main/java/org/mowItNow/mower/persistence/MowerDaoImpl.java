package org.mowItNow.mower.persistence;

import org.mowItNow.Utils;
import org.mowItNow.mower.models.Mower;
import org.mowItNow.mower.models.MowerTask;
import org.mowItNow.mower.models.Orientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import java.io.IOException;

@Component
public class MowerDaoImpl implements MowerDao {
    private static Logger logger = LoggerFactory.getLogger(MowerDaoImpl.class);

    @Override
    public List<MowerTask> getAllMowerTasks(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            if (lines.size() % 2 != 1) {
                throw new RuntimeException("The file is not well formatted");
            }
            List<Integer> lawnSize = getLawnSize(lines.get(0));
            logger.info(STR. "Config file  \{ filePath.getFileName() } - Lawn size: \{ lawnSize }" );
            return getMowerInstruction(
                    filePath.getFileName(),
                    lines.subList(1, lines.size()),
                    lawnSize.get(0),
                    lawnSize.get(1)
            );

        } catch (IOException e) {
            throw new RuntimeException(STR. "There was an issue wirtting result of \{ filePath.getFileName() }" , e);
        }
    }

    @Override
    public void save(String content, Path configFilePath) {
        try {
            // Get the URL of the resources directory
            String resourcesPath = MowerDaoImpl.class.getClassLoader().getResource("").getPath();

            String folderName = "results";
            File folder = new File(resourcesPath + folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = STR. "\{ configFilePath.getFileName().toString().split("\\.")[0] }_result.txt" ;
            File file = new File(folder, fileName);

            Utils.writeToFile(file, content);

        } catch (IOException e) {
            logger.error(STR. "There was an issue wirtting result of \{ configFilePath.getFileName() }" , e);
        }
    }

    private List<MowerTask> getMowerInstruction(Path configFileName, List<String> controls, int x_lawn_corner, int y_lawn_corner) {
        List<MowerTask> mowerControls = new ArrayList<>();
        Mower mower = null;
        int index = 0;
        for (String line : controls) {
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
                logger.debug(STR. "Config file  \{ configFileName } - Mower created : \{ mower }" );

            } else {
                if (mower != null) {
                    mowerControls.add(new MowerTask(mower, line));
                    logger.debug(STR. "Config file  \{ configFileName } - MowerControls created : \{ mowerControls }" );
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
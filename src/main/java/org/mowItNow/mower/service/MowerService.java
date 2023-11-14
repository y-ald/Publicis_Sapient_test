package org.mowItNow.mower.service;

import java.nio.file.Path;
import java.util.List;

public interface MowerService {

    void executeMowerTask(Path filePath);

    void executeAllMowerTask(List<Path> filesPath);

}

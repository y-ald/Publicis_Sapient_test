package org.mowItNow.service;

import java.nio.file.Path;

public interface MowerService {
    void executeMowerTask(Path filePath);
    void executeAllMowerTask();
}

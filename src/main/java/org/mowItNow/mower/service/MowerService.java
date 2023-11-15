package org.mowItNow.mower.service;

import java.util.List;

public interface MowerService {

    void executeMowerTask(String fileName);

    void executeAllMowerTask(List<String> fileNames);

}

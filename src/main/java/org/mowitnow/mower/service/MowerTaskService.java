package org.mowitnow.mower.service;

import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.models.MowerTask;

import java.util.List;

public interface MowerTaskService {
    void executeAllMowerTask(MowerTasksDto mowerTasksDto);

    List<MowerTask> getAllMowerTask();

    MowerTask getMowerTask(String mowerTaskId);
}

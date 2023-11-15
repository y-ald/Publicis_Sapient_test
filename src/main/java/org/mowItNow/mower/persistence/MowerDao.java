package org.mowItNow.mower.persistence;

import org.mowItNow.mower.models.MowerTask;

import java.nio.file.Path;
import java.util.List;

public interface MowerDao {

    List<MowerTask> getAllMowerTasks(String fileName);

    void save(String content, String filePath);

}

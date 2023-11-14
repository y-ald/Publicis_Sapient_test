package org.mowItNow.mower.persistence;

import org.mowItNow.mower.models.Mower;
import org.mowItNow.mower.models.MowerTask;

import java.nio.file.Path;
import java.util.List;

public interface MowerDao {

    List<MowerTask> getAllMowerTasks(Path path);

    void save(String content, Path configFilePath);

}

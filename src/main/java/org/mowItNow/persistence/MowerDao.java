package org.mowItNow.persistence;

import org.mowItNow.models.MowerTask;

import java.nio.file.Path;
import java.util.IllegalFormatException;
import java.util.List;

public interface MowerDao {
    List<MowerTask> getAllMowerTasks(Path path) throws IllegalFormatException;
}

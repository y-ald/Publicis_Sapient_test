package org.mowitnow.mower.persistence;

import org.mowitnow.mower.models.MowerTask;

import java.util.List;
import java.util.Optional;

public interface MowerTaskDao {
    MowerTask save(MowerTask mowerTask);

    List<MowerTask> findAll();

    Optional<MowerTask> findById(String mowerTaskId);
}

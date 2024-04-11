package org.mowitnow.mower.persistence;

import org.mowitnow.mower.models.MowerTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MowerTaskDaoImpl extends MowerTaskDao, JpaRepository<MowerTask, String> {
}

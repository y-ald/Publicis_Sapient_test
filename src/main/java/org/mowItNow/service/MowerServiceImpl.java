package org.mowItNow.service;

import org.mowItNow.models.InstructionEnum;
import org.mowItNow.models.Mower;
import org.mowItNow.models.MowerTask;
import org.mowItNow.persistence.MowerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@Component
public class MowerServiceImpl implements MowerService {
    private static Logger logger = LoggerFactory.getLogger(MowerServiceImpl.class);

    MowerDao mowerDao;
    public MowerServiceImpl(MowerDao mowerDao) {
        this.mowerDao = mowerDao;
    }

    @Override
    public void executeMowerTask(Path filePath) {
        List<MowerTask> mowerTasks = mowerDao.getAllMowerTasks(filePath);
        Consumer<MowerTask> runMowerTask = (mowerTask) -> {
            mowerTask.instructions().chars()
                    .mapToObj(i -> (char) i)
                    .map(letter -> InstructionEnum.valueOf(letter.toString()))
                    .forEach(instruction -> {
                        processInstructions(mowerTask.mower(), instruction);
                    });
            logger.info("******************************************* this is result  of instruction *********************");
            logger.info(mowerTask.mower().toString());
        };
        mowerTasks.forEach(runMowerTask);
    }

    @Override
    public void executeAllMowerTask() {

    }

    private void processInstructions(Mower mower, InstructionEnum instructionEnum) {
            switch (instructionEnum) {
                case D:
                    mower.rotateRight();
                    break;
                case G:
                    mower.rotateLeft();
                    break;
                case A:
                    mower.moveForward();
                    break;
                case B:
                    mower.moveBackward();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction: " + instructionEnum);
            }
    }

}

package org.mowitnow.mower.presentation;

import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.models.MowerTask;
import org.mowitnow.mower.service.MowerTaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mower/tasks")
public class MowerApi {
    private final MowerTaskService mowerTaskService;

    public MowerApi(MowerTaskService mowerTaskService) {
        this.mowerTaskService = mowerTaskService;
    }

    @PostMapping()
    public void addMower(@RequestBody MowerTasksDto mowerTasksDto) {
        mowerTaskService.executeAllMowerTask(mowerTasksDto);
    }

    @GetMapping()
    public List<MowerTask> getallMowerTask() {
        return mowerTaskService.getAllMowerTask();
    }

    @GetMapping("/{mowerTaskId}")
    public MowerTask getMowerTask(@PathVariable() String mowerTaskId) {
        return mowerTaskService.getMowerTask(mowerTaskId);
    }
}

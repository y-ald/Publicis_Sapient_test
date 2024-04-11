package org.mowitnow.mower.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.models.MowerTask;
import org.mowitnow.mower.persistence.MowerTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MowerApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MowerTaskDao mowerTaskDao;

    @Test
    public void testAddMower() throws Exception {
        MowerTasksDto mowerTasksDto = new MowerTasksDto("5 5", List.of("1 2 N;GAGAGAGAA", "3 3 E;AADAADADDA"), List.of("1", "2"));

        mockMvc.perform(post("/api/mower/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mowerTasksDto)))
                .andExpect(status().isOk());

        List<MowerTask> tasks = mowerTaskDao.findAll();
        assertEquals(2, tasks.size());
        assertThat(DBUtils.getMowerTasks())
                .usingRecursiveComparison()
                .ignoringFields("mower")
                .isEqualTo(tasks);
    }

    @Test
    public void testGetAllMowerTask() throws Exception {
        MowerTask task1 = DBUtils.getMowerTasks().get(0);
        MowerTask task2 = DBUtils.getMowerTasks().get(1);
        mowerTaskDao.save(task2);
        mowerTaskDao.save(task1);

        // Perform GET request
        mockMvc.perform(get("/api/mower/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        List<MowerTask> tasks = mowerTaskDao.findAll();
        assertEquals(2, tasks.size());
        assertThat(DBUtils.getMowerTasks())
                .usingRecursiveComparison()
                .ignoringFields("mower")
                .isEqualTo(tasks);
    }

    @Test
    public void testGetMowerTask() throws Exception {
        MowerTask task = DBUtils.getMowerTasks().get(0);
        mowerTaskDao.save(task);

        mockMvc.perform(get("/api/mower/tasks/{taskId}", task.getId()))
                .andExpect(status().isOk());

        assertThat(DBUtils.getMowerTasks().get(0))
                .usingRecursiveComparison()
                .ignoringFields("mower")
                .isEqualTo(task);
    }

}


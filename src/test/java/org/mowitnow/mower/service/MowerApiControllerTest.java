package org.mowitnow.mower.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.models.MowerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MowerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MowerTaskService mowerTaskService;

    @Captor
    ArgumentCaptor<MowerTasksDto> mowerTasksDtoCaptor;

    @Test
    public void testAddMower() throws Exception {
        MowerTasksDto mowerTasksDto = new MowerTasksDto("5 5", List.of("1 2 N;GAGAGAGAA", "3 3 E;AADAADADDA"), List.of("1", "2"));

        mockMvc.perform(post("/api/mower/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mowerTasksDto)))
                .andExpect(status().isOk());

        verify(mowerTaskService, times(1)).executeAllMowerTask(mowerTasksDtoCaptor.capture());
        assertThat(mowerTasksDtoCaptor.getValue()).isEqualToComparingFieldByField(mowerTasksDto);
    }

    @Test
    public void testGetAllMowerTask() throws Exception {
        List<MowerTask> mowerTasks = DBUtils.getMowerTasks();

        when(mowerTaskService.getAllMowerTask()).thenReturn(mowerTasks);

        mockMvc.perform(get("/api/mower/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mowerTasks.size())));
    }

    @Test
    public void testGetMowerTask() throws Exception {
        MowerTask mowerTask = DBUtils.getMowerTasks().get(0);

        when(mowerTaskService.getMowerTask(anyString())).thenReturn(mowerTask);

        mockMvc.perform(get("/api/mower/tasks/{mowerTaskId}", "testId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(mowerTask.getId())));
    }
}


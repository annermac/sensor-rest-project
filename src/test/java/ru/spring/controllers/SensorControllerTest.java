package ru.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.spring.dto.SensorDTO;
import ru.spring.mapper.SensorMapper;
import ru.spring.services.SensorServiceImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = SensorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorServiceImpl sensorService;

    @Autowired
    private ObjectMapper objectMapper;

    private SensorDTO sensorNew;

    @BeforeEach
    public void setUp() {
        sensorNew = SensorDTO.builder()
                .name("Сенсор 1")
                .build();
    }

    @Test
    public void testAddSensor() throws Exception {
        when(sensorService.add(Mockito.any(SensorDTO.class))).thenReturn(SensorMapper.mapToSensor(sensorNew));

        ResultActions response = mockMvc.perform(post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorNew)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
package ru.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.spring.dto.MeasurementDTO;
import ru.spring.mapper.MeasurementMapper;
import ru.spring.models.Sensor;
import ru.spring.services.MeasurementServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MeasurementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementServiceImpl measurementService;

    @Autowired
    private ObjectMapper objectMapper;
    private MeasurementDTO measurementNew;

    private Sensor sensorNew;

    @BeforeEach
    void setUp() {
        sensorNew = Sensor.builder()
                .name("Сенсор 1")
                .build();

        measurementNew = MeasurementDTO.builder()
                .value(50.5)
                .raining(true)
                .sensor(sensorNew)
                .build();
    }

    @Test
    void testAddMeasurement() throws Exception {
        when(measurementService.add(measurementNew)).thenReturn(MeasurementMapper.mapToMeasurement(measurementNew, sensorNew));
        ResultActions response = mockMvc.perform(post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementNew)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetAllMeasurements() throws Exception {
        List<MeasurementDTO> measurementList = new ArrayList<>();
        measurementList.add(measurementNew);
        when(measurementService.getAll()).thenReturn(measurementList);

        ResultActions response = mockMvc.perform(get("/measurements")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(measurementList.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getRainyDaysCount() throws Exception {
        List<MeasurementDTO> measurementList = new ArrayList<>();
        measurementList.add(measurementNew);

        when(measurementService.getAll()).thenReturn(measurementList);

        int expectedCount = 0;

        ResultActions response = mockMvc.perform(get("/measurements/rainyDaysCount")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(expectedCount)))
                .andDo(MockMvcResultHandlers.print());

    }
}
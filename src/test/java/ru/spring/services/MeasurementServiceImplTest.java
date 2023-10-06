package ru.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spring.dto.MeasurementDTO;
import ru.spring.mapper.MeasurementMapper;
import ru.spring.models.Measurement;
import ru.spring.models.Sensor;
import ru.spring.repositories.MeasurementRepository;
import ru.spring.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {
    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private MeasurementServiceImpl measurementService;
    @Mock
    private SensorRepository sensorRepository;

    private Measurement measurementNew;
    private Measurement measurementNew2;
    private Sensor sensorNew;

    @BeforeEach
    public void setUp() {
        sensorNew = Sensor.builder()
                .name("Сенсор 1")
                .build();

        sensorRepository.save(sensorNew);

        measurementNew = Measurement.builder()
                .id(null)
                .value(50.5)
                .raining(true)
                .sensor(sensorNew)
                .created(LocalDateTime.now())
                .build();

        measurementNew2 = Measurement.builder()
                .id(null)
                .value(45.5)
                .raining(true)
                .sensor(sensorNew)
                .created(LocalDateTime.now())
                .build();

        measurementRepository.save(measurementNew);

    }

    @Test
    public void testAddMeasurement() {
        when(measurementRepository.save(Mockito.any(Measurement.class))).thenReturn(measurementNew);

        Measurement savedMeasurement = measurementService.add(MeasurementMapper.mapToMeasurementDTO(measurementNew));

        assertNotNull(savedMeasurement);
    }

    @Test
    public void testGetAllMeasurements() {
        List<Measurement> expectedMeasurementList = new ArrayList<>();
        expectedMeasurementList.add(measurementNew);
        expectedMeasurementList.add(measurementNew2);

        when(measurementRepository.findAll()).thenReturn(expectedMeasurementList);

        List<MeasurementDTO> actualMeasurementList = measurementService.getAll();

        assertEquals(2, actualMeasurementList.size());
        assertEquals(expectedMeasurementList.get(0).getValue(), actualMeasurementList.get(0).getValue());
        assertEquals(expectedMeasurementList.get(0).getRaining(), actualMeasurementList.get(0).getRaining());
    }

    @Test
    public void testGetRainyDaysCount() {
        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(measurementNew);
        measurementList.add(measurementNew2);

        when(measurementRepository.findAll()).thenReturn(measurementList);

        int expectedCount = 2;
        int actualCount = measurementService.getRainyDaysCount();

        assertEquals(expectedCount, actualCount);
    }
}
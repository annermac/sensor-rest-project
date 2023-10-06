package ru.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spring.mapper.SensorMapper;
import ru.spring.models.Sensor;
import ru.spring.repositories.SensorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    private Sensor sensorNew;

    @BeforeEach
    public void setUp() {
        sensorNew = Sensor.builder()
                .name("Сенсор 1")
                .build();

        sensorRepository.save(sensorNew);
    }

    @Test
    public void testAddSensor() {
        when(sensorRepository.save(Mockito.any(Sensor.class))).thenReturn(sensorNew);

        Sensor savedSensor = sensorService.add(SensorMapper.mapToDTO(sensorNew));

        assertNotNull(savedSensor);
    }
}
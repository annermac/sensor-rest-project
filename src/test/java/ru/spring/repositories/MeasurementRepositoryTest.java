package ru.spring.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.spring.dto.MeasurementDTO;
import ru.spring.mapper.MeasurementMapper;
import ru.spring.models.Measurement;
import ru.spring.models.Sensor;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeasurementRepositoryTest {
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    SensorRepository sensorRepository;

    private MeasurementDTO measurementNew;

    @BeforeEach
    public void setUp() {
        measurementNew = MeasurementDTO.builder()
                .value(50.5)
                .raining(true)
                .sensor("Сенсор 1")
                .created(LocalDateTime.now())
                .build();

        Sensor sensorNew = Sensor.builder()
                .name("Сенсор 1")
                .build();
        sensorRepository.save(sensorNew);
        measurementRepository.save(MeasurementMapper.mapToMeasurement(measurementNew, sensorNew));

    }
    @Test
    public void testAddMeasurement(){
        Sensor sensor = sensorRepository.findByNameIgnoreCase(measurementNew.getSensor());
        Measurement measurementSaved = measurementRepository.save(MeasurementMapper.mapToMeasurement(measurementNew, sensor));
        assertEquals(measurementSaved.getValue(), measurementNew.getValue());
        assertEquals(measurementSaved.getRaining(), measurementNew.getRaining());
        assertEquals(measurementSaved.getSensor().getName(), measurementNew.getSensor());
    }

    @Test
    public void testGetAllMeasurements(){
        List<Measurement> measurementList = measurementRepository.findAll();
        assertFalse(measurementList.isEmpty());
    }

}
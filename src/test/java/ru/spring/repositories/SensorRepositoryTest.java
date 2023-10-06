package ru.spring.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.spring.models.Sensor;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SensorRepositoryTest {
    @Autowired
    SensorRepository sensorRepository;

    Sensor sensorNew;

    @BeforeEach
    public void setUp() {
        sensorNew = Sensor.builder()
                .name("Сенсор 1")
                .build();
        sensorRepository.save(sensorNew);
    }

    @Test
    public void findByNameIgnoreCase() {
        Sensor actualSensor = sensorRepository.findByNameIgnoreCase(sensorNew.getName());
        assertNotNull(actualSensor);
    }

    @Test
    public void getAddSensor() {
        Sensor actualSensor = sensorRepository.save(sensorNew);
        assertEquals(sensorNew.getId(), actualSensor.getId());
        assertEquals(sensorNew.getName(), actualSensor.getName());
    }
}
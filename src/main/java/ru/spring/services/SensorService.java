package ru.spring.services;

import ru.spring.dto.SensorDTO;
import ru.spring.models.Sensor;

public interface SensorService {
    Sensor add(SensorDTO sensorDTO);
}

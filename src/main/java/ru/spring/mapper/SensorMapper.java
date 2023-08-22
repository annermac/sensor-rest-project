package ru.spring.mapper;

import ru.spring.dto.SensorDTO;
import ru.spring.models.Sensor;

public class SensorMapper {
    public static SensorDTO mapToDTO(Sensor sensor){
        return new SensorDTO(sensor.getName());
    }

    public static Sensor mapToSensor(SensorDTO sensorDTO){
        return new Sensor(null, sensorDTO.getName());
    }
}

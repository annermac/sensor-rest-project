package ru.spring.mapper;

import ru.spring.dto.MeasurementDTO;
import ru.spring.models.Measurement;
import ru.spring.models.Sensor;

import java.time.LocalDateTime;

public class MeasurementMapper {
    public static Measurement mapToMeasurement(MeasurementDTO measurementDTO, Sensor sensor) {
        return new Measurement(null,
                measurementDTO.getValue(),
                measurementDTO.getRaining(),
                sensor,
                LocalDateTime.now());
    }

    public static MeasurementDTO mapToMeasurementDTO(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getValue(),
                measurement.getRaining(),
                measurement.getSensor());
    }
}

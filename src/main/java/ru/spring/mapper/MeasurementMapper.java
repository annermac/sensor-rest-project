package ru.spring.mapper;

import ru.spring.dto.MeasurementDTO;
import ru.spring.dto.MeasurementShortDTO;
import ru.spring.dto.SensorDTO;
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

    public static MeasurementShortDTO mapToMeasurementShortNotDTO(Measurement measurement, SensorDTO sensorDTO) {
        return new MeasurementShortDTO(
                measurement.getValue(),
                measurement.getRaining(),
                sensorDTO);
    }
}

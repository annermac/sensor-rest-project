package ru.spring.services;

import ru.spring.dto.MeasurementDTO;
import ru.spring.models.Measurement;

import java.util.List;

public interface MeasurementService {
    Measurement add(MeasurementDTO measurementDTO);

    List<MeasurementDTO> getAll();

    Integer getRainyDaysCount();
}

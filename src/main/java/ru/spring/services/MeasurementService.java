package ru.spring.services;

import ru.spring.dto.MeasurementDTO;
import ru.spring.dto.MeasurementShortDTO;

import java.util.List;

public interface MeasurementService {
    void add(MeasurementDTO measurementDTO);

    List<MeasurementShortDTO> getAll();

    Integer getRainyDaysCount();
}

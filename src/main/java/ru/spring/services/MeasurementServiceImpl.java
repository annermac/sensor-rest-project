package ru.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.spring.dto.MeasurementDTO;
import ru.spring.mapper.MeasurementMapper;
import ru.spring.models.Measurement;
import ru.spring.models.Sensor;
import ru.spring.repositories.MeasurementRepository;
import ru.spring.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    @Transactional
    public Measurement add(MeasurementDTO measurementDTO) {
        Sensor sensor = sensorRepository.findByNameIgnoreCase(measurementDTO.getSensor().getName());
        try {
            return measurementRepository.save(MeasurementMapper.mapToMeasurement(measurementDTO, sensor));
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<MeasurementDTO> getAll() {
        List<Measurement> measurementList = measurementRepository.findAll();
        List<MeasurementDTO> measurementDTOList = new ArrayList<>();
        for (Measurement measurement : measurementList) {
            measurementDTOList.add(MeasurementMapper.mapToMeasurementDTO(measurement));
        }
        return measurementDTOList;
    }

    @Override
    public Integer getRainyDaysCount() {
        List<Measurement> measurementList = measurementRepository.findAll();
        int countDays = 0;
        for (Measurement measurement : measurementList) {
            if (measurement.getRaining())
                countDays++;
        }
        return countDays;
    }
}

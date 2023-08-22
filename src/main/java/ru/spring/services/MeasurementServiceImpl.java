package ru.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.spring.dto.MeasurementDTO;
import ru.spring.dto.MeasurementShortDTO;
import ru.spring.mapper.MeasurementMapper;
import ru.spring.mapper.SensorMapper;
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
    public void add(MeasurementDTO measurementDTO) {
        Sensor sensor = sensorRepository.findByNameIgnoreCase(measurementDTO.getSensor());
        try {
            measurementRepository.save(MeasurementMapper.mapToMeasurement(measurementDTO, sensor));
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<MeasurementShortDTO> getAll() {
        List<Measurement> measurementList = measurementRepository.findAll();
        Sensor sensor;
        List<MeasurementShortDTO> measurementShortDTOList = new ArrayList<>();
        for (Measurement measurement : measurementList) {
            sensor = sensorRepository.findByNameIgnoreCase(measurement.getSensor().getName());
            measurementShortDTOList.add(MeasurementMapper.mapToMeasurementShortNotDTO(measurement, SensorMapper.mapToDTO(sensor)));
        }
        return measurementShortDTOList;
    }

    @Override
    public Integer getRainyDaysCount() {
        List<MeasurementShortDTO> measurementList = this.getAll();
        int countDays = 0;
        for (MeasurementShortDTO measurement : measurementList) {
            if (measurement.getRaining())
                countDays++;
        }
        return countDays;
    }
}

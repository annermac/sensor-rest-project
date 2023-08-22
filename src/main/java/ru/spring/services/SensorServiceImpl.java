package ru.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.spring.dto.SensorDTO;
import ru.spring.mapper.SensorMapper;
import ru.spring.models.Sensor;
import ru.spring.repositories.SensorRepository;

@Service
@Transactional(readOnly = true)
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public Sensor add(SensorDTO sensorDTO) {
        try {
            return sensorRepository.save(SensorMapper.mapToSensor(sensorDTO));
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}

package ru.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.dto.SensorDTO;
import ru.spring.services.SensorServiceImpl;

import static ru.spring.util.ErrorHandler.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorServiceImpl sensorServiceImpl;

    @Autowired
    public SensorController(SensorServiceImpl sensorServiceImpl) {
        this.sensorServiceImpl = sensorServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        sensorServiceImpl.add(sensorDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}

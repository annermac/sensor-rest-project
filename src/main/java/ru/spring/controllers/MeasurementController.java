package ru.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.dto.MeasurementDTO;
import ru.spring.services.MeasurementServiceImpl;

import java.util.List;

import static ru.spring.util.ErrorHandler.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementServiceImpl measurementServiceImpl;

    @Autowired
    public MeasurementController(MeasurementServiceImpl measurementServiceImpl) {
        this.measurementServiceImpl = measurementServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        measurementServiceImpl.add(measurementDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getAll() {
        return new ResponseEntity<>(measurementServiceImpl.getAll(), HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDaysCount() {
        return new ResponseEntity<>(measurementServiceImpl.getRainyDaysCount(), HttpStatus.OK);
    }

}

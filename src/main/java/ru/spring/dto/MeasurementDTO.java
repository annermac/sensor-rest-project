package ru.spring.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.spring.models.Sensor;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
    @NotNull
    @Min(value = -100, message = "Значение должно быть минимум -100")
    @Max(value = 100, message = "Значение должно быть максимум 100")
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private Sensor sensor;
}

package ru.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {
    @NotEmpty(message = "Название не должно быть пустым!")
    @Size(min = 3, max = 100)
    private String name;
}

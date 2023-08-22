package ru.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurement", schema = "public")
public class Measurement {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    @Min(value = -100, message = "Значение должно быть минимум -100")
    @Max(value = 100, message = "Значение должно быть максимум 100")
    private Double value;

    @Column
    @NotNull
    private Boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @NotNull
    private Sensor sensor;

    @Column
    private LocalDateTime created;
}

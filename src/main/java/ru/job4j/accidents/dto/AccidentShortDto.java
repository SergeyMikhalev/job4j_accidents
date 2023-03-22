package ru.job4j.accidents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.job4j.accidents.model.AccidentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentShortDto {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String description;
    private String address;
    private AccidentType type;
}

package ru.laboratory.blps.model.essay.dto;

import ru.laboratory.blps.model.essay.EssayType;
import lombok.Data;

@Data
public class EssayUpdateDTO {
    private Long id;
    private String name;
    private String category;
    private EssayType type;
    private String text;
}

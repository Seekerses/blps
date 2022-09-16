package ru.laboratory.blps.essay.dto;

import ru.laboratory.blps.essay.EssayType;
import lombok.Data;

@Data
public class EssayUploadDTO {
    private String name;
    private String category;
    private EssayType type;
    private String text;
}

package ru.laboratory.blps.validator;

import lombok.Data;

@Data
public class Essay {
    private Long id;
    private String text;
    private EssayStatus status;
}

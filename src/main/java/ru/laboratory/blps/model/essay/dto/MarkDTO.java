package ru.laboratory.blps.model.essay.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.model.essay.Mark;

@Builder
@Data
public class MarkDTO {

    private int value;
    private long count;

    public static MarkDTO create(Mark mark){
        return MarkDTO.builder()
                .value(mark.getValue())
                .count(mark.getCount())
                .build();
    }
}

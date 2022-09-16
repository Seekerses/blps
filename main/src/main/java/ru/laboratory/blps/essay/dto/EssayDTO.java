package ru.laboratory.blps.essay.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.essay.Essay;
import ru.laboratory.blps.essay.EssayStatus;
import ru.laboratory.blps.essay.EssayType;

import java.time.LocalDateTime;

@Builder
@Data
public class EssayDTO {
    private long id;
    private String name;
    private String category;
    private EssayType type;
    private EssayStatus status;
    private LocalDateTime creationDate;
    private String text;
    private InteractionDTO interaction;

    public static EssayDTO create(Essay essay){
        return EssayDTO.builder()
                .id(essay.getId())
                .name(essay.getName())
                .type(essay.getType())
                .creationDate(essay.getCreationDate())
                .text(essay.getText())
                .interaction(InteractionDTO.create(essay.getInteraction()))
                .build();
    }
}

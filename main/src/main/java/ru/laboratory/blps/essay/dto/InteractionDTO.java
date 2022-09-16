package ru.laboratory.blps.essay.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.essay.Interaction;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class InteractionDTO {
    private long views;
    private float totalMark;
    private List<MarkDTO> marks;
    private List<CommentDTO> comments;

    public static InteractionDTO create(Interaction interaction){
        List<MarkDTO> marks = interaction.getMarks().stream()
                .map(MarkDTO::create).collect(Collectors.toList());
        List<CommentDTO> comments = interaction.getComments().stream()
                .map(CommentDTO::create).collect(Collectors.toList());

        return InteractionDTO.builder()
                .views(interaction.getViews())
                .totalMark(interaction.getTotalMark())
                .marks(marks)
                .comments(comments)
                .build();
    }
}

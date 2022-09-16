package ru.laboratory.blps.essay.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.essay.Comment;
import ru.laboratory.blps.auth.dto.UserDTO;

@Builder
@Data
public class CommentDTO {
    private String text;
    private ru.laboratory.blps.auth.dto.UserDTO commentator;

    public static CommentDTO create(Comment comment){
        return CommentDTO.builder()
                .text(comment.getText())
                .commentator(UserDTO.create(comment.getCommentator()))
                .build();
    }
}

package ru.laboratory.blps.model.essay.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.model.essay.Comment;
import ru.laboratory.blps.model.user.dto.UserDTO;

@Builder
@Data
public class CommentDTO {
    private String text;
    private UserDTO commentator;

    public static CommentDTO create(Comment comment){
        return CommentDTO.builder()
                .text(comment.getText())
                .commentator(UserDTO.create(comment.getCommentator()))
                .build();
    }
}

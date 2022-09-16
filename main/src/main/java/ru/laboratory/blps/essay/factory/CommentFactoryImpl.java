package ru.laboratory.blps.essay.factory;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.essay.Comment;
import ru.laboratory.blps.essay.dto.CommentCreateDTO;
import org.springframework.stereotype.Service;

@Service
public class CommentFactoryImpl implements CommentFactory {
    @Override
    public Comment create(CommentCreateDTO createDTO, User user) {
        return Comment.builder()
                .text(createDTO.getText())
                .commentator(user)
                .build();
    }
}

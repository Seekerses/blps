package ru.laboratory.blps.model.essay.factory;

import ru.laboratory.blps.model.essay.Comment;
import ru.laboratory.blps.model.essay.dto.CommentCreateDTO;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.model.user.User;

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

package com.example.blps.service.factory;

import com.example.blps.model.referat.Comment;
import com.example.blps.model.referat.dto.CommentCreateDTO;
import org.springframework.stereotype.Service;

@Service
public class CommentFactoryImpl implements CommentFactory {
    @Override
    public Comment create(CommentCreateDTO createDTO) {
        return Comment.builder()
                .name(createDTO.getName())
                .text(createDTO.getText())
                .build();
    }
}

package com.example.blps.service.factory;

import com.example.blps.model.referat.Essay;
import com.example.blps.model.referat.Interaction;
import com.example.blps.model.referat.dto.EssayUploadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EssayFactoryImpl implements EssayFactory{

    private final InteractionFactory interactionFactory;

    @Override
    public Essay create(EssayUploadDTO uploadDTO){

        Interaction interaction = interactionFactory.create();

        return Essay.builder()
                .name(uploadDTO.getName())
                .category(uploadDTO.getCategory())
                .creationDate(LocalDateTime.now())
                .text(uploadDTO.getText())
                .interaction(interaction)
                .build();
    }
}

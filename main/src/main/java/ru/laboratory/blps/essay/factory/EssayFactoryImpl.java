package ru.laboratory.blps.essay.factory;

import ru.laboratory.blps.essay.Essay;
import ru.laboratory.blps.essay.EssayStatus;
import ru.laboratory.blps.essay.Interaction;
import ru.laboratory.blps.essay.dto.EssayUploadDTO;
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
                .type(uploadDTO.getType())
                .text(uploadDTO.getText())
                .status(EssayStatus.UNDER_REVIEW)
                .interaction(interaction)
                .build();
    }
}

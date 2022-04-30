package com.example.blps.service.essay;

import com.example.blps.model.referat.Comment;
import com.example.blps.model.referat.Essay;
import com.example.blps.model.referat.Interaction;
import com.example.blps.model.referat.dto.CommentCreateDTO;
import com.example.blps.model.referat.dto.EssayUpdateDTO;
import com.example.blps.model.referat.dto.EssayUploadDTO;
import com.example.blps.model.referat.repository.EssayRepository;
import com.example.blps.service.factory.CommentFactory;
import com.example.blps.service.factory.EssayFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class EssayServiceImpl implements EssayService{

    private final EssayRepository repository;
    private final EssayFactory essayFactory;
    private final CommentFactory commentFactory;

    @Override
    public void createEssay(EssayUploadDTO uploadDTO){
        Essay essay = essayFactory.create(uploadDTO);
        repository.saveAndFlush(essay);
    }

    @Override
    public Essay getEssay(long id){
        Essay essay = repository.getById(id);
        essay.getInteraction().setViews(essay.getInteraction().getViews() +1 );
        return repository.saveAndFlush(essay);
    }

    @Override
    public void deleteEssay(long id){
        repository.deleteById(id);
    }

    @Override
    public Essay updateEssay(EssayUpdateDTO updateDTO){
        Essay old = repository.getById(updateDTO.getId());
        old.setCategory(updateDTO.getCategory());
        old.setName(updateDTO.getName());
        old.setType(updateDTO.getType());
        old.setText(updateDTO.getText());
        return repository.saveAndFlush(old);
    }

    @Override
    public void commentEssay(long essayId, CommentCreateDTO createDTO){
        Comment comment = commentFactory.create(createDTO);
        Essay essay = repository.getById(essayId);
        essay.getInteraction().getComments().add(comment);
        repository.saveAndFlush(essay);

    }

    @Override
    public void markEssay(long essayId, int mark){
        Essay essay = repository.getById(essayId);
        essay.getInteraction().getMarks().get(mark-1).
                setCount(essay.getInteraction().getMarks().get(mark-1).getCount()+1);
        essay.getInteraction().setTotalMark(recalculateMarks(essay.getInteraction()));
        repository.saveAndFlush(essay);
    }

    private float recalculateMarks(Interaction interaction){
        AtomicReference<Float> totalMark = new AtomicReference<>((float) 0);
        AtomicReference<Float> totalMarkCount = new AtomicReference<>((float) 0);
        interaction.getMarks().forEach((v) -> {
                totalMark.updateAndGet(v1 -> v1 + v.getValue() * v.getCount());
                totalMarkCount.updateAndGet(v1 -> v1 + v.getCount());
                });
        return totalMark.get() / totalMarkCount.get();
    }
}

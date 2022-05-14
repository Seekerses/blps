package ru.laboratory.blps.model.essay.service;

import ru.laboratory.blps.model.essay.Comment;
import ru.laboratory.blps.model.essay.Essay;
import ru.laboratory.blps.model.essay.Interaction;
import ru.laboratory.blps.model.essay.dto.CommentCreateDTO;
import ru.laboratory.blps.model.essay.dto.EssayUpdateDTO;
import ru.laboratory.blps.model.essay.dto.EssayUploadDTO;
import ru.laboratory.blps.model.essay.repository.EssayRepository;
import ru.laboratory.blps.model.essay.factory.CommentFactory;
import ru.laboratory.blps.model.essay.factory.EssayFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.exceptions.UserNotFound;
import ru.laboratory.blps.model.user.service.UserService;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class EssayServiceImpl implements EssayService{

    private final EssayRepository repository;
    private final EssayFactory essayFactory;
    private final CommentFactory commentFactory;
    private final UserService userService;

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
    public void commentEssay(long essayId, CommentCreateDTO createDTO, Long userId) throws UserNotFound {
        User user = userService.getById(userId);
        Comment comment = commentFactory.create(createDTO, user);
        Essay essay = repository.getById(essayId);
        essay.getInteraction().getComments().add(comment);
        repository.saveAndFlush(essay);
    }

    @Override
    public void markEssay(long essayId, int mark){
        Essay essay = repository.getById(essayId);
        essay.getInteraction().getMarks().forEach((v) -> {
            if (v.getValue() == mark){
                v.setCount(v.getCount() + 1);
            }
        });
        essay.getInteraction().setTotalMark(recalculateMarks(essay.getInteraction()));
        repository.saveAndFlush(essay);
    }

    @Override
    public List<Essay> getByCategory(String category) {
        return repository.getAllByCategory(category);
    }

    @Override
    public List<Essay> getAll(){
        return repository.findAll();
    }

    @Override
    public List<Essay> searchByPhrase(String phrase) {
        return repository.searchByPhrase(phrase);
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

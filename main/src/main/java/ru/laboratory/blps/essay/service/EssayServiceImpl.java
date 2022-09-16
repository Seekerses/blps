package ru.laboratory.blps.essay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.exceptions.UserNotFound;
import ru.laboratory.blps.auth.service.UserService;
import ru.laboratory.blps.configuration.broker.MqttPublisher;
import ru.laboratory.blps.essay.Comment;
import ru.laboratory.blps.essay.Essay;
import ru.laboratory.blps.essay.Interaction;
import ru.laboratory.blps.essay.dto.CommentCreateDTO;
import ru.laboratory.blps.essay.dto.EssayUpdateDTO;
import ru.laboratory.blps.essay.dto.EssayUploadDTO;
import ru.laboratory.blps.essay.factory.CommentFactory;
import ru.laboratory.blps.essay.factory.EssayFactory;
import ru.laboratory.blps.essay.repository.EssayRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@RequiredArgsConstructor
@Service
public class EssayServiceImpl implements EssayService{
//    private final MqttPublisher.MQTTGateway gateway;

    private final EssayRepository repository;
    private final EssayFactory essayFactory;
    private final CommentFactory commentFactory;
    private final UserService userService;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void createEssay(EssayUploadDTO uploadDTO){
        Essay essay = essayFactory.create(uploadDTO);
        transactionTemplate.executeWithoutResult(status -> repository.saveAndFlush(essay));
    }

    @Override
    public Essay getEssay(long id){
        Essay essay = repository.getOne(id);
        essay.getInteraction().setViews(essay.getInteraction().getViews() +1 );
        return repository.saveAndFlush(essay);
    }

    @Override
    public void deleteEssay(long id){
        repository.deleteById(id);
    }

    @Override
    public Essay updateEssay(EssayUpdateDTO updateDTO){
        Essay old = repository.getOne(updateDTO.getId());
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
        Essay essay = repository.getOne(essayId);
        essay.getInteraction().getComments().add(comment);
        repository.saveAndFlush(essay);
    }

    @Override
    public void markEssay(long essayId, int mark){
        Essay essay = repository.getOne(essayId);
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

//    @EventListener(ApplicationReadyEvent.class)
//    public void publishTestMessage(){
//        gateway.sendToMqtt("Raising Hope");
//    }
}

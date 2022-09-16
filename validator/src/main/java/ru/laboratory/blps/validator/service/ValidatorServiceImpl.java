package ru.laboratory.blps.validator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.validator.Restriction;
import ru.laboratory.blps.validator.dto.RestrictionDTO;
import ru.laboratory.blps.validator.repository.RestrictionRepository;
import ru.laboratory.blps.validator.service.factory.RestrictionFactory;
import sun.jvm.hotspot.runtime.Bytes;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@RequiredArgsConstructor
@Service
public class ValidatorServiceImpl implements ValidatorService {

    private final RestrictionRepository repository;
    private final RestrictionFactory restrictionFactory;
    private final Map<String, Restriction> restrictionMap = new HashMap<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(15);
    private final Integer partSize = 250;
    private final Integer maxWeight = 100;

    @Override
    public boolean validate(String text) {
        int parts = text.length() / partSize;
        AtomicInteger finalWeight = new AtomicInteger();
        List<Future<Integer>> tasks = new ArrayList<>();
        while (parts > 0){
            int finalParts = parts;
            tasks.add(threadPool.submit(() -> {
                if ((finalParts + 1) * partSize < text.length()) return validatePartTask(text.substring((finalParts - 1) * 250, finalParts * 250));
                else return validatePartTask(text.substring((finalParts - 1) * 250, text.length() -1));
            }));
            parts--;
        }
        tasks.forEach(task -> {
            try {
                finalWeight.addAndGet(task.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        return finalWeight.get() <= maxWeight;
    }

    private int validatePartTask(String part){
        AtomicInteger restrictionWeight = new AtomicInteger();
        part = part.toLowerCase();
        List<String> words = Arrays.asList(part.split("\\W+"));
        words.forEach(v -> {
            if (restrictionMap.containsKey(v)) restrictionWeight.addAndGet(restrictionMap.get(v).getWeight());
        });
        return restrictionWeight.get();
    }

    @Override
    public Restriction addRestriction(RestrictionDTO restriction) {
        Restriction newRestriction = repository.saveAndFlush(restrictionFactory.create(restriction));
        restrictionMap.put(newRestriction.getWord(), newRestriction);
        return newRestriction;
    }

    @Override
    public void deleteRestriction(Long restrictionID) {
        Restriction restriction  = repository.getById(restrictionID);
        restrictionMap.remove(restriction.getWord());
        repository.delete(restriction);
    }

    @Override
    public Restriction updateRestriction(RestrictionDTO restriction) {
        Restriction old = repository.getById(restriction.getId());
        old.setWord(restriction.getWord());
        old.setWeight(restriction.getWeight());
        old = repository.saveAndFlush(old);
        restrictionMap.replace(old.getWord(), old);
        return old;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadRestrictions(){
        List<Restriction> restrictions = repository.findAll();
        restrictions.forEach( v -> restrictionMap.put(v.getWord(), v));
        log.info("Restrictions loaded.");
    }

    @JmsListener(destination = "test", containerFactory = "BLPSFactory")
    public void consumeRequest(BytesMessage msg){
        byte[] body;
        try {
            body = new byte[(int) msg.getBodyLength()];
            msg.readBytes(body);
            log.info(new String(body));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.example.blps.service.factory;

import com.example.blps.model.referat.Mark;
import org.springframework.stereotype.Service;

@Service
public class MarkFactoryImpl implements MarkFactory {
    @Override
    public Mark create(int mark) {
        return Mark.builder()
                .value(mark)
                .count(0)
                .build();
    }
}

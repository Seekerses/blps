package com.example.blps.model.referat.dto;

import com.example.blps.model.referat.EssayType;
import lombok.Data;

@Data
public class EssayUploadDTO {
    private String name;
    private String category;
    private EssayType type;
    private String text;
}
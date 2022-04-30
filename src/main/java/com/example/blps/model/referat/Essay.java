package com.example.blps.model.referat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Essay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String name;
    private String category;
    private EssayType type;
    private LocalDateTime creationDate;
    private String text;
    @OneToOne(cascade = CascadeType.ALL)
    private Interaction interaction;
}

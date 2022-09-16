package ru.laboratory.blps.essay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long views;
    private float totalMark;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
}

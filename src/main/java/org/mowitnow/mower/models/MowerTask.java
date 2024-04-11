package org.mowitnow.mower.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mower_tasks")
public class MowerTask {
    @Id
    private String id;
    @Column
    private String instructions;
    @Column
    private String initalPosition;
    @Column
    private String finalPosition;
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Mower mower;
}

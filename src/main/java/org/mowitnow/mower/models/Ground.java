package org.mowitnow.mower.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor()
@Table(name = "grounds")
public class Ground {
    @Id
    private final String id = UUID.randomUUID().toString();
    @Column
    private Integer topRightCorner;
    @Column
    private Integer lowerLeftCorner;

    @OneToMany(mappedBy = "ground")
    @JsonIgnore
    private final List<Mower> mowers = new ArrayList<>();
}

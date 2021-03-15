package com.avaloq.diceroll.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for persisting dice roll simulation data
 *  @author sonny
 */
@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dice_roll_simulation")
public class DiceRollSimulationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @OneToMany(targetEntity=DiceRollGroupEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "simulation")
    private List<DiceRollGroupEntity> diceRollGroupList;

    private Integer sideCount;

    private Integer diceCount;

    private Integer rollCount;

    public void addDiceRollGroup(DiceRollGroupEntity diceRollGroupEntity) {
        diceRollGroupList.add(diceRollGroupEntity);
        diceRollGroupEntity.setSimulation(this);
    }
}

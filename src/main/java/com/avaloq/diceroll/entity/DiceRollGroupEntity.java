package com.avaloq.diceroll.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for persisting dice roll group data
 * A dice roll group contains N number of dice per roll
 * @author sonny
 */
@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dice_roll_group")
public class DiceRollGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @ManyToOne(targetEntity=DiceRollSimulationEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name="companyId")
    private DiceRollSimulationEntity simulation;

    @ElementCollection
    @CollectionTable(name = "dice_roll", joinColumns = @JoinColumn(name = "dice_group_id"))
    @Column(name = "dice_side")
    private List<Integer> diceRolls;

    private Integer sum;
}

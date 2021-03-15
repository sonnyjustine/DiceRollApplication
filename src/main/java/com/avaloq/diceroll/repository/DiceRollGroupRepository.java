package com.avaloq.diceroll.repository;

import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiceRollGroupRepository extends JpaRepository<DiceRollGroupEntity, Long> {
    List<DiceRollGroupEntity> findBySimulationDiceCountAndSimulationSideCount(int diceCount, int sideCount);
}

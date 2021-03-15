package com.avaloq.diceroll.repository;

import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiceRollSimulationRepository extends JpaRepository<DiceRollSimulationEntity, Long> {
    List<DiceRollSimulationEntity> findByDiceCountAndSideCount(int diceCount, int sideCount);
}

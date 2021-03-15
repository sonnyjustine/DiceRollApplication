package com.avaloq.diceroll.factory;

import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;

/**
 * Factory interface for creating dice roll entities
 * @author sonny
 */
public interface DiceRollSimulationFactory {

    /**
     * Create entity for the entire dice roll simulation
     * @param diceCount number of dice used per roll
     * @param sideCount number of sides of each dice
     * @param rollCount number of rolls
     * @return DiceRollSimulationEntity
     */
    DiceRollSimulationEntity createDiceRollSimulation(int diceCount, int sideCount, int rollCount);

    /**
     * Create entity for each dice roll
     * param diceCount number of dice used per roll
     * @param sideCount number of sides of each dice
     * @return DiceRollGroupEntity
     */
    DiceRollGroupEntity createDiceRollGroup(int diceCount, int sideCount);
}

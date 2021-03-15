package com.avaloq.diceroll.service;

import com.avaloq.diceroll.dto.CountSummaryDTO;
import com.avaloq.diceroll.dto.FrequencyDTO;
import com.avaloq.diceroll.dto.RelativeDistributionDTO;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;

import java.util.List;

/**
 * Service interface for managing dice roll data
 * @author sonny
 */
public interface DiceRollSimulationService {

    /**
     * Simulate dice rolling
     * @param diceCount number of dice used per roll
     * @param sideCount number of sides of each dice
     * @param rollCount number of rolls
     * @return
     */
    DiceRollSimulationEntity rollDice(int diceCount, int sideCount, int rollCount);

    /**
     * Count how many times each sum has been rolled
     * @param diceRollSimulationEntity the simulation entity
     * @return List of FrequencyDTO objects that contain sumValue and its number of occurrences in a given simulation
     */
    List<FrequencyDTO> getFrequencyList(DiceRollSimulationEntity diceRollSimulationEntity);

    /**
     * Count how many simulations and rolls match a given dice number-dice side combination
     * @param diceCount number of dice used per roll
     * @param sideCount number of sides of each dice
     * @return CountSummaryDTO that contains count for simulations and rolls
     */
    CountSummaryDTO getCountSummary(int diceCount, int sideCount);

    /**
     * Compute for relative distribution of each occurrences of sum value for a given dice number-dice side combination
     * @param diceCount number of dice used per roll
     * @param sideCount number of sides of each dice
     * @return
     */
    RelativeDistributionDTO getRelativeDistribution(int diceCount, int sideCount);
}

package com.avaloq.diceroll.factory;

import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DiceRollSimulationFactoryImpl implements DiceRollSimulationFactory {

    @Override
    public DiceRollSimulationEntity createDiceRollSimulation(int diceCount, int sideCount, int rollCount) {
        DiceRollSimulationEntity diceRollSimulation = DiceRollSimulationEntity.builder()
                .diceCount(diceCount)
                .sideCount(sideCount)
                .rollCount(rollCount)
                .diceRollGroupList(new ArrayList<>())
                .build();

        for(int i = 0; i < rollCount; i++) {
            diceRollSimulation.addDiceRollGroup(this.createDiceRollGroup(sideCount, diceCount));
        }

        return diceRollSimulation;
    }

    @Override
    public DiceRollGroupEntity createDiceRollGroup(int diceCount, int sideCount) {
        List<Integer> diceRolls = new Random()
                .ints(diceCount,1,sideCount+1)
                .boxed().collect(Collectors.toList());

        int sum = diceRolls.stream().reduce(Integer::sum).get();

        DiceRollGroupEntity diceRollGroup = DiceRollGroupEntity.builder()
                .diceRolls(diceRolls)
                .sum(sum)
                .build();
        return diceRollGroup;
    }
}

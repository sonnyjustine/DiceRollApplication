package com.avaloq.diceroll.factory;

import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiceRollSimulationFactoryImplTest {

    private DiceRollSimulationFactory diceRollSimulationFactory;

    @BeforeEach
    void setUp() {
        diceRollSimulationFactory = new DiceRollSimulationFactoryImpl();
    }

    @Test
    void whenCreateDiceRollSimulation_thenReturnEntity() {
        DiceRollSimulationEntity result = diceRollSimulationFactory.createDiceRollSimulation(5,6,100);

        assertEquals(5, result.getDiceCount());
        assertEquals(6, result.getSideCount());
        assertEquals(100, result.getRollCount());

        for(DiceRollGroupEntity diceRollGroupEntity : result.getDiceRollGroupList()) {
            int sum = diceRollGroupEntity.getSum();
            assertTrue(sum < 30 && sum > 5);
        }
    }

    @Test
    void createDiceRollGroup_thenReturnEntity() {
        DiceRollGroupEntity result = diceRollSimulationFactory.createDiceRollGroup(9, 10);
        List<Integer> diceRolls = result.getDiceRolls();

        assertEquals(9, result.getDiceRolls().size());
        for(Integer value : diceRolls) {
            assertTrue(value <= 10);
        }
    }
}
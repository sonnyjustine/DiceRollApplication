package com.avaloq.diceroll.repository;

import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import com.avaloq.diceroll.factory.DiceRollSimulationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
class DiceRollGroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DiceRollGroupRepository diceRollGroupRepository;

    @Autowired
    private DiceRollSimulationRepository diceRollSimulationRepository;

    @Autowired
    private DiceRollSimulationFactory diceRollSimulationFactory;

    private DiceRollSimulationEntity entity1, entity2, entity3;

    @BeforeEach
    void setUp() {
        entity1 = diceRollSimulationFactory.createDiceRollSimulation(5, 5, 10);
        entity2 = diceRollSimulationFactory.createDiceRollSimulation(5, 5, 20);
        entity3 = diceRollSimulationFactory.createDiceRollSimulation(20, 5, 20);

        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.persist(entity3);
    }

    @Test
    void whenFindBySimulationDiceCountAndSimulationSideCount_thenReturnDiceRollGroupList() {
        List<DiceRollGroupEntity> result = diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(5,5);
        List<DiceRollGroupEntity> result1 = diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(20,5);

        assertEquals(30,result.size());
        assertEquals(20,result1.size());
    }

    @Test
    void whenFindBySimulationDiceCountAndSimulationSideCount_thenReturnEmptyList() {
        List<DiceRollGroupEntity> result = diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(10,5);
        assertEquals(0,result.size());
    }
}
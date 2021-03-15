package com.avaloq.diceroll.service;

import com.avaloq.diceroll.dto.CountSummaryDTO;
import com.avaloq.diceroll.dto.FrequencyDTO;
import com.avaloq.diceroll.dto.RelativeDistributionDTO;
import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import com.avaloq.diceroll.factory.DiceRollSimulationFactory;
import com.avaloq.diceroll.repository.DiceRollGroupRepository;
import com.avaloq.diceroll.repository.DiceRollSimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DiceRollSimulationServiceImplTest {

    @Autowired
    private DiceRollSimulationService diceRollSimulationService;

    @MockBean
    private DiceRollSimulationFactory diceRollSimulationFactory;

    @MockBean
    private DiceRollSimulationRepository diceRollSimulationRepository;

    @MockBean
    private DiceRollGroupRepository diceRollGroupRepository;

    private DiceRollSimulationEntity diceRollSimulation;

    @BeforeEach
    void setUp() {
        diceRollSimulation = DiceRollSimulationEntity.builder()
                .diceCount(3)
                .sideCount(6)
                .rollCount(60)
                .diceRollGroupList(new ArrayList<>())
                .build();

        // 20 dice rolls with value 3,3,3
        for(int i = 1; i <= 20; i++) {
            DiceRollGroupEntity diceRollGroup = DiceRollGroupEntity.builder()
                    .diceRolls(Arrays.asList(3,3,3))
                    .sum(3+3+3)
                    .id((short) i)
                    .build();

            diceRollSimulation.addDiceRollGroup(diceRollGroup);
        }

        // 30 dice rolls with value 1,1,1
        for(int i = 21; i <= 50; i++) {
            DiceRollGroupEntity diceRollGroup = DiceRollGroupEntity.builder()
                    .diceRolls(Arrays.asList(1,1,1))
                    .sum(1+1+1)
                    .id((short) i)
                    .build();

            diceRollSimulation.addDiceRollGroup(diceRollGroup);
        }

        // 10 dice rolls with value 5,5,5
        for(int i = 51; i <= 60; i++) {
            DiceRollGroupEntity diceRollGroup = DiceRollGroupEntity.builder()
                    .diceRolls(Arrays.asList(5,5,5))
                    .sum(5+5+5)
                    .id((short) i)
                    .build();

            diceRollSimulation.addDiceRollGroup(diceRollGroup);
        }
    }

    @Test
    void whenRollDice_thenReturnDiceRollSimulationEntity() {
        when(diceRollSimulationFactory.createDiceRollSimulation(3,6,100))
                .thenReturn(diceRollSimulation);

        DiceRollSimulationEntity savedEntity = diceRollSimulation;
        savedEntity.setId((short) 1);
        when(diceRollSimulationRepository.save(any())).thenReturn(savedEntity);

        DiceRollSimulationEntity getSavedEntity = diceRollSimulationService.rollDice(3,6,100);
        assertEquals(savedEntity, getSavedEntity);
    }

    @Test
    void whenGetFrequencyMap_thenReturnFrequencyDTOList() {
        List<FrequencyDTO> expected = new ArrayList<>();
        expected.add(FrequencyDTO.of(3,30));
        expected.add(FrequencyDTO.of(9,20));
        expected.add(FrequencyDTO.of(15,10));

        List<FrequencyDTO> result = diceRollSimulationService.getFrequencyList(diceRollSimulation);
        assertEquals(expected, result);
    }

    @Test
    void whenGetCountSummary_thenReturnCountSummaryDTO() {
        List<DiceRollSimulationEntity> list = new ArrayList<>();
        list.add(diceRollSimulation);
        list.add(diceRollSimulation);
        list.add(diceRollSimulation);

        when(diceRollSimulationRepository.findByDiceCountAndSideCount(3,6))
                .thenReturn(list);

        CountSummaryDTO result = diceRollSimulationService.getCountSummary(3,6);

        assertEquals(3, result.getSimulationCount());
        assertEquals(180, result.getRollCount());
    }

    @Test
    void whenGetCountSummary_thenReturnCountSummaryDTOWithZeroCount() {
        when(diceRollSimulationRepository.findByDiceCountAndSideCount(12,6))
                .thenReturn(new ArrayList<>());

        CountSummaryDTO result = diceRollSimulationService.getCountSummary(12,6);

        assertEquals(0, result.getSimulationCount());
        assertEquals(0, result.getRollCount());
    }

    @Test
    void whenGetRelativeDistribution_thenReturnRelativeDistributionDTO() {
        when(diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(3,6))
                .thenReturn(diceRollSimulation.getDiceRollGroupList());

        RelativeDistributionDTO result = diceRollSimulationService.getRelativeDistribution(3,6);

        assertEquals(60, result.getTotalFrequency());
        assertEquals(3, result.getRelativeDistributionTable().size());

        assertEquals(3, result.getRelativeDistributionTable().get(0).getSum());
        assertEquals(30, result.getRelativeDistributionTable().get(0).getFrequency());
        assertEquals(50.00, result.getRelativeDistributionTable().get(0).getRelativeDistribution());

        assertEquals(9, result.getRelativeDistributionTable().get(1).getSum());
        assertEquals(20, result.getRelativeDistributionTable().get(1).getFrequency());
        assertEquals(33.33, result.getRelativeDistributionTable().get(1).getRelativeDistribution());

        assertEquals(15, result.getRelativeDistributionTable().get(2).getSum());
        assertEquals(10, result.getRelativeDistributionTable().get(2).getFrequency());
        assertEquals(16.67, result.getRelativeDistributionTable().get(2).getRelativeDistribution());

        assertEquals(100.00, result.getRelativeDistributionTable().get(0).getRelativeDistribution() +
                result.getRelativeDistributionTable().get(1).getRelativeDistribution() + result.getRelativeDistributionTable().get(2).getRelativeDistribution());
    }

    @Test
    void whenGetRelativeDistribution_thenReturnRelativeDistributionDTOWithZeroCount() {
        when(diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(3,6))
                .thenReturn(new ArrayList<>());

        RelativeDistributionDTO result = diceRollSimulationService.getRelativeDistribution(10,6);

        assertEquals(0, result.getTotalFrequency());
        assertEquals(0, result.getRelativeDistributionTable().size());
    }
}
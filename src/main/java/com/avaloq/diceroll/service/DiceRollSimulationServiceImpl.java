package com.avaloq.diceroll.service;

import com.avaloq.diceroll.dto.CountSummaryDTO;
import com.avaloq.diceroll.dto.FrequencyDTO;
import com.avaloq.diceroll.dto.RelativeDistributionDTO;
import com.avaloq.diceroll.dto.RelativeDistributionItemDTO;
import com.avaloq.diceroll.entity.DiceRollGroupEntity;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import com.avaloq.diceroll.factory.DiceRollSimulationFactory;
import com.avaloq.diceroll.repository.DiceRollGroupRepository;
import com.avaloq.diceroll.repository.DiceRollSimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiceRollSimulationServiceImpl implements DiceRollSimulationService {

    @Autowired
    private DiceRollSimulationFactory diceRollSimulationFactory;

    @Autowired
    private DiceRollSimulationRepository diceRollSimulationRepository;

    @Autowired
    private DiceRollGroupRepository diceRollGroupRepository;

    @Override
    public DiceRollSimulationEntity rollDice(int diceCount, int sideCount, int rollCount) {
        DiceRollSimulationEntity diceRollSimulationEntity = diceRollSimulationFactory.createDiceRollSimulation(diceCount, sideCount, rollCount);
        return diceRollSimulationRepository.save(diceRollSimulationEntity);
    }

    @Override
    public List<FrequencyDTO> getFrequencyList(DiceRollSimulationEntity diceRollSimulationEntity) {
        List<DiceRollGroupEntity> diceRollGroupList = diceRollSimulationEntity.getDiceRollGroupList();
        Map<Integer, Long> frequencyPerSum = diceRollGroupList.stream().collect(
                Collectors.groupingBy(
                        DiceRollGroupEntity::getSum,
                        Collectors.counting()
                )
        );

        List<FrequencyDTO> result = new ArrayList<>();
        frequencyPerSum.forEach((sum, count) -> {
            result.add(FrequencyDTO.of(sum,count.intValue()));
        });

        return result;
    }

    @Override
    public CountSummaryDTO getCountSummary(int diceCount, int sideCount) {
        List<DiceRollSimulationEntity> list = diceRollSimulationRepository.findByDiceCountAndSideCount(diceCount, sideCount);
        int simulationCount = list.size();
        int rollCount = list.stream()
                .mapToInt(s -> s.getDiceRollGroupList().size())
                .sum();

        return CountSummaryDTO.of(simulationCount, rollCount);
    }

    @Override
    public RelativeDistributionDTO getRelativeDistribution(int diceCount, int sideCount) {
        List<DiceRollGroupEntity> diceRollGroupList = diceRollGroupRepository.findBySimulationDiceCountAndSimulationSideCount(diceCount, sideCount);

        // Count the number of occurence of each sum value in the diceRollGroupList
        Map<Integer, Long> rollCountPerSum = diceRollGroupList.stream().collect(
                Collectors.groupingBy(
                        DiceRollGroupEntity::getSum,
                        Collectors.counting()
                )
        );

        // Compute for percentage / relative distribution and create DTO
        int totalCount = diceRollGroupList.size();
        List<RelativeDistributionItemDTO> items = new ArrayList<>();
        rollCountPerSum.forEach((sum, count) -> {
            items.add(RelativeDistributionItemDTO.of(sum, count.intValue(), (float)count/totalCount * 100));
        });

        return RelativeDistributionDTO.of(totalCount, items);
    }
}

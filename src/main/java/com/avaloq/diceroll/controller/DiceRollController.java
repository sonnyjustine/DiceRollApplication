package com.avaloq.diceroll.controller;

import com.avaloq.diceroll.dto.FrequencyDTO;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import com.avaloq.diceroll.service.DiceRollSimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * API for managing dice rolls
 * @author sonny
 */
@RestController
@Validated
@RequestMapping("api/roll/")
public class DiceRollController {

    @Autowired
    private DiceRollSimulationService diceRollSimulationService;

    @Operation(summary = "Simulate dice roll. Return number of times each sum occurred in a simulation")
    @PostMapping(value="")
    public ResponseEntity<List<FrequencyDTO>> rollDice(@Parameter(description = "Number of dice per roll")
                                                           @RequestParam(defaultValue = "3") @Min(1) int diceCount,
                                                       @Parameter(description = "Number of sides per dice")
                                                           @RequestParam(defaultValue = "6") @Min(4) int sideCount,
                                                       @Parameter(description = "Number of rolls per simulation")
                                                           @RequestParam(defaultValue = "100") @Min(1) int rollCount) {
        DiceRollSimulationEntity simulation = diceRollSimulationService.rollDice(diceCount, sideCount, rollCount);
        return new ResponseEntity<>(diceRollSimulationService.getFrequencyList(simulation), HttpStatus.CREATED);
    }
}

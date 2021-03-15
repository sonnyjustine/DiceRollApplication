package com.avaloq.diceroll.controller;

import com.avaloq.diceroll.dto.CountSummaryDTO;
import com.avaloq.diceroll.dto.RelativeDistributionDTO;
import com.avaloq.diceroll.service.DiceRollSimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API for managing dice roll reports
 * @author sonny
 */
@RestController
@RequestMapping("api/roll/report")
public class DiceRollReportController {

    @Autowired
    private DiceRollSimulationService diceRollSimulationService;

    @Operation(summary = "Get total simulations & rolls on a given dice number-dice side combination")
    @GetMapping(value="count-summary/{diceCount}/{sideCount}")
    public ResponseEntity<CountSummaryDTO> countSummary(@Parameter(description = "Number of dice per roll")
                                                        @PathVariable int diceCount,
                                                        @Parameter(description = "Number of sides per dice")
                                                        @PathVariable int sideCount) {
        CountSummaryDTO countSummary = diceRollSimulationService.getCountSummary(diceCount, sideCount);
        return new ResponseEntity<>(countSummary, HttpStatus.OK);
    }

    @Operation(summary = "Get relative distribution of each sum occurrences on a given dice number-dice side combination")
    @GetMapping(value="relative-distribution/{diceCount}/{sideCount}")
    public ResponseEntity<RelativeDistributionDTO> relativeDistribution(@Parameter(description = "Number of dice per roll")
                                                                        @PathVariable int diceCount,
                                                                        @Parameter(description = "Number of sides per dice")
                                                                        @PathVariable int sideCount) {
        RelativeDistributionDTO relativeDistribution = diceRollSimulationService.getRelativeDistribution(diceCount, sideCount);
        return new ResponseEntity<>(relativeDistribution, HttpStatus.OK);
    }
}
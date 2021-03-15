package com.avaloq.diceroll.dto;

import lombok.Value;

/**
 * Count summary representation
 * @author sonny
 */
@Value(staticConstructor = "of")
public class CountSummaryDTO {
    private int simulationCount;
    private int rollCount;
}

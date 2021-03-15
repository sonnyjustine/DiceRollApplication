package com.avaloq.diceroll.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

/**
 * Represents relative distribution of a given sum value
 * @author sonny
 */
@Value(staticConstructor = "of")
public class RelativeDistributionItemDTO {
    private int sum;
    private int frequency;

    @JsonIgnore
    private float percentage;

    public double getRelativeDistribution() {
        // Round percentage to nearest hundredth
        return Math.round(percentage * 100.0) / 100.0;
    }
}
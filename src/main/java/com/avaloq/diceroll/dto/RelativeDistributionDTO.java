package com.avaloq.diceroll.dto;

import lombok.Value;

import java.util.List;

/**
 * Relative distribution data representation
 */
@Value(staticConstructor = "of")
public class RelativeDistributionDTO {
    private int totalFrequency;
    private List<RelativeDistributionItemDTO> relativeDistributionTable;
}

package com.avaloq.diceroll.dto;

import lombok.Value;

/**
 * Frequency per sum value representation
 * @author sonny
 */
@Value(staticConstructor = "of")
public class FrequencyDTO {
    private int sum;
    private int count;
}

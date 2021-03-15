package com.avaloq.diceroll.config;

import com.avaloq.diceroll.factory.DiceRollSimulationFactory;
import com.avaloq.diceroll.factory.DiceRollSimulationFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dice Roll app configurations
 * @author sonny
 */
@Configuration
public class AppConfig {

    @Bean
    public DiceRollSimulationFactory diceRollSimulationFactory() {
        return new DiceRollSimulationFactoryImpl();
    }
}

package com.avaloq.diceroll.controller;

import com.avaloq.diceroll.exception.ErrorResponse;
import com.avaloq.diceroll.exception.RestExceptionHandler;
import com.avaloq.diceroll.dto.FrequencyDTO;
import com.avaloq.diceroll.entity.DiceRollSimulationEntity;
import com.avaloq.diceroll.service.DiceRollSimulationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DiceRollController.class)
class DiceRollControllerTest {
    private static String BASE_PATH = "http://localhost/api/roll/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiceRollSimulationService diceRollSimulationService;

    @Autowired
    private JacksonTester<List<FrequencyDTO>> jacksonTesterFrequencyDTOList;

    @Autowired
    private JacksonTester<ErrorResponse> jacksonTesterErrorResponse;

    private DiceRollSimulationEntity diceRollSimulation;

    private List<FrequencyDTO> dtoList;

    @BeforeEach
    void setUp() {
        diceRollSimulation = DiceRollSimulationEntity.builder()
                .diceCount(3)
                .sideCount(6)
                .rollCount(60)
                .diceRollGroupList(new ArrayList<>())
                .build();

        dtoList = new ArrayList<>();
        dtoList.add(FrequencyDTO.of(3,30));
        dtoList.add(FrequencyDTO.of(9,20));
        dtoList.add(FrequencyDTO.of(15,10));
    }

    @Test
    void whenRollDice_noRequestParameter_thenReturnFrequencyDTOList() throws Exception {
        when(diceRollSimulationService.rollDice(3,6,100))
                .thenReturn(diceRollSimulation);

        when(diceRollSimulationService.getFrequencyList(diceRollSimulation))
                .thenReturn(dtoList);

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(),HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), jacksonTesterFrequencyDTOList.write(dtoList).getJson());
    }

    @Test
    void whenRollDice_withValidRequestParameter_thenReturnFrequencyDTOList() throws Exception {
        DiceRollSimulationEntity diceRollSimulationEntity = diceRollSimulation;
        diceRollSimulationEntity.setDiceCount(5);

        when(diceRollSimulationService.rollDice(5,6,100))
                .thenReturn(diceRollSimulationEntity);

        when(diceRollSimulationService.getFrequencyList(diceRollSimulationEntity))
                .thenReturn(dtoList);

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_PATH + "?diceCount=5&siceCount=6&rollCount=100")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(),HttpStatus.CREATED.value());
        assertEquals(response.getContentAsString(), jacksonTesterFrequencyDTOList.write(dtoList).getJson());
    }

    @Test
    void whenRollDice_withInvalidDiceCount_thenReturnErrorResponse() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("rollDice.diceCount: must be greater than or equal to 1");

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_PATH + "?diceCount=-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(),HttpStatus.BAD_REQUEST.value());
        assertEquals(response.getContentAsString(), jacksonTesterErrorResponse.write(errorResponse).getJson());
    }

    @Test
    void whenRollDice_withInvalidSideCount_thenReturnErrorResponse() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("rollDice.sideCount: must be greater than or equal to 4");

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_PATH + "?sideCount=-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(),HttpStatus.BAD_REQUEST.value());
        assertEquals(response.getContentAsString(), jacksonTesterErrorResponse.write(errorResponse).getJson());
    }

    @Test
    void whenRollDice_withInvalidRollCount_thenReturnErrorResponse() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("rollDice.rollCount: must be greater than or equal to 1");

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_PATH + "?rollCount=-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(),HttpStatus.BAD_REQUEST.value());
        assertEquals(response.getContentAsString(), jacksonTesterErrorResponse.write(errorResponse).getJson());
    }
}
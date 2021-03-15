package com.avaloq.diceroll.controller;

import com.avaloq.diceroll.dto.CountSummaryDTO;
import com.avaloq.diceroll.dto.RelativeDistributionDTO;
import com.avaloq.diceroll.dto.RelativeDistributionItemDTO;
import com.avaloq.diceroll.service.DiceRollSimulationService;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DiceRollReportController.class)
class DiceRollReportControllerTest {
    private static String BASE_PATH = "http://localhost/api/roll/report/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiceRollSimulationService diceRollSimulationService;

    @Autowired
    private JacksonTester<CountSummaryDTO> jacksonTesterCountSummaryDTO;

    @Autowired
    private JacksonTester<RelativeDistributionDTO> jacksonTesterRelativeDistributionDTO;

    private RelativeDistributionDTO relativeDistributionDTO;

    @Test
    void whenCountSummary_thenReturnCountSummaryDTO() throws Exception {
        CountSummaryDTO countSummaryDTO = CountSummaryDTO.of(5, 500);
        when(diceRollSimulationService.getCountSummary(3,6)).thenReturn(countSummaryDTO);

        MockHttpServletResponse response = mockMvc.perform(
                get(BASE_PATH + "count-summary/3/6")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jacksonTesterCountSummaryDTO.write(countSummaryDTO).getJson());
    }

    @Test
    void whenRelativeDistribution_thenReturnRelativeDistributionDTO() throws Exception {
        RelativeDistributionItemDTO item = RelativeDistributionItemDTO.of(3, 20, 33.33f);
        RelativeDistributionItemDTO item1 = RelativeDistributionItemDTO.of(9, 30, 50f);
        RelativeDistributionItemDTO item2 = RelativeDistributionItemDTO.of(15, 10, 16.66f);
        List<RelativeDistributionItemDTO> items = new ArrayList<RelativeDistributionItemDTO>(Arrays.asList(item, item1, item2));
        RelativeDistributionDTO relativeDistributionDTO = RelativeDistributionDTO.of(5, items);
        when(diceRollSimulationService.getRelativeDistribution(3,6)).thenReturn(relativeDistributionDTO);

        MockHttpServletResponse response = mockMvc.perform(
                get(BASE_PATH + "relative-distribution/3/6")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jacksonTesterRelativeDistributionDTO.write(relativeDistributionDTO).getJson());
    }
}
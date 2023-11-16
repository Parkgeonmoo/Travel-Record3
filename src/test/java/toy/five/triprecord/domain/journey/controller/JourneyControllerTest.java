package toy.five.triprecord.domain.journey.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import toy.five.triprecord.domain.jouney.controller.JourneyController;
import toy.five.triprecord.domain.jouney.dto.request.JourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.request.MoveJourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.response.JourneyCreateResponse;
import toy.five.triprecord.domain.jouney.dto.response.MoveJourneyCreateResponse;
import toy.five.triprecord.domain.jouney.entity.JourneyType;
import toy.five.triprecord.domain.jouney.repository.LodgmentJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.MoveJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.VisitJourneyRepository;
import toy.five.triprecord.domain.jouney.service.JourneyService;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.security.repository.TokenRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(JourneyController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ComponentScan(basePackages = {"toy.five.triprecord.global.security", "toy.five.triprecord.domain.user.repository"})
public class JourneyControllerTest {

    private MockMvc mvc;

    @MockBean
    JourneyService journeyService;

    @MockBean
    private MoveJourneyRepository moveJourneyRepository;

    @MockBean
    private LodgmentJourneyRepository lodgmentJourneyRepository;

    @MockBean
    private VisitJourneyRepository visitJourneyRepository;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .build();
    }

    protected MediaType contentType =
            new MediaType(
                    MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8
            );


    @WithMockUser
    @Test
    @DisplayName("여정 생성 테스트")
    public void giveJourneys_saveJourneys() throws Exception {
        // given
        String name = "이동01";
        String vehicle = "이동수단01";
        String startPoint = "서울";
        String endPoint = "부산";
        String startTime = "2023-10-26T10:00:00";
        String endTime = "2023-10-26T12:00:00";
        JourneyType type = JourneyType.MOVE;

        MoveJourneyCreateRequest moveJourneyCreateRequest = MoveJourneyCreateRequest.builder()
                .name(name)
                .vehicle(vehicle)
                .startPoint(startPoint)
                .endPoint(endPoint)
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .type(type)
                .build();

        JourneyCreateRequest journeyCreateRequest = JourneyCreateRequest.builder()
                .moves(List.of(moveJourneyCreateRequest))
                .build();


        MoveJourneyCreateResponse moveJourneyCreateResponse = MoveJourneyCreateResponse.builder()
                .name(name)
                .vehicle(vehicle)
                .startPoint(startPoint)
                .endPoint(endPoint)
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .type(type)
                .tripId(1L)
                .build();

        JourneyCreateResponse journeyCreateResponse = JourneyCreateResponse.builder()
                .moves(List.of(moveJourneyCreateResponse))
                .build();

        given(journeyService.saveJourneys(any(Long.class), any(JourneyCreateRequest.class)))
                .willReturn(journeyCreateResponse);

        String requestBody = mapper.writeValueAsString(journeyCreateRequest);

        mvc.perform(MockMvcRequestBuilders.post("/journeys/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.moves[0].name").value(moveJourneyCreateResponse.getName()));

    }
}

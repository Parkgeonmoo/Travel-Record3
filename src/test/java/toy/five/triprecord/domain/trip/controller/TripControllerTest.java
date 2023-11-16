package toy.five.triprecord.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import toy.five.triprecord.domain.trip.dto.request.TripCreateRequest;
import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.dto.response.TripCreateResponse;
import toy.five.triprecord.domain.trip.dto.response.TripDetailResponse;
import toy.five.triprecord.domain.trip.service.TripService;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.security.repository.TokenRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static toy.five.triprecord.domain.trip.entity.Domestic.DOMESTIC;

@WebMvcTest(TripController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ComponentScan(basePackages = {"toy.five.triprecord.global.security", "toy.five.triprecord.domain.user.repository"})
class TripControllerTest {

    private MockMvc mvc;

    @MockBean
    private TripService tripService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EntityManagerFactory emFactory;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .defaultRequest(put("/**").with(csrf()))
                .defaultRequest(delete("/**").with(csrf()))
                .build();
    }

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    private final TripDetailResponse defaultTripResponse =
            TripDetailResponse.builder()
                    .id(1L)
                    .name("여행")
                    .startTime(LocalDateTime.of(2022, 1, 1, 0, 0))
                    .endTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                    .domestic(DOMESTIC)
                    .journeys(null)
                    .wishCount(0L)
                    .build();

    @Test
    @WithMockUser
    void createTrip_success() throws Exception {
        //given
        TripCreateRequest tripRequest =
                TripCreateRequest.builder()
                        .name(defaultTripResponse.getName())
                        .domestic(defaultTripResponse.getDomestic())
                        .startTime(defaultTripResponse.getStartTime())
                        .endTime(defaultTripResponse.getEndTime())
                        .build();
        TripCreateResponse tripResponse =
                TripCreateResponse.builder()
                        .id(1L)
                        .name(defaultTripResponse.getName())
                        .domestic(defaultTripResponse.getDomestic())
                        .startTime(defaultTripResponse.getStartTime())
                        .endTime(defaultTripResponse.getEndTime())
                        .wishCount(0L)
                        .build();

        given(tripService.createTrip(any(TripCreateRequest.class), any(String.class)))
                .willReturn(tripResponse);

        //when
        String body = mapper.writeValueAsString(tripRequest);

        //then
        mvc.perform(post("/trips").contentType(contentType).content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value(defaultTripResponse.getName()))
                .andExpect(jsonPath("$.data.domestic").value(String.valueOf(defaultTripResponse.getDomestic())))
                .andExpect(jsonPath("$.data.wish_count").value(0L));
    }

    @Test
    @WithMockUser
    void getAllTripsBySearch_success() throws Exception {
        //given
        TripSearchCond cond =
                TripSearchCond.builder()
                        .tripName("조건")
                        .minStartTime(LocalDateTime.of(2022,01,01,0,0,0))
                        .maxEndTime(LocalDateTime.of(2023,01,01,0,0,0))
                        .domestic(DOMESTIC)
                        .minWishCount(10L)
                        .build();

        TripDetailResponse tripResponse =
                TripDetailResponse.builder()
                        .name("조건 : 여행")
                        .startTime(LocalDateTime.of(2022,1,1,0,0,0))
                        .endTime(LocalDateTime.of(2023,1,1,0,0,0))
                        .domestic(DOMESTIC)
                        .wishCount(10L)
                        .build();

        given(tripService.getAllTripsBySearchCond(any(TripSearchCond.class)))
                .willReturn(List.of(tripResponse));

        //when
        String body = mapper.writeValueAsString(cond);

        //then
        mvc.perform(get("/trips/search").contentType(contentType).content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value(tripResponse.getName()))
                .andExpect(jsonPath("$.data[0].domestic").value(tripResponse.getDomestic().name()))
                .andExpect(jsonPath("$.data[0].wish_count").value(tripResponse.getWishCount()));

    }

}
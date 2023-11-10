package toy.five.triprecord.domain.jouney.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.five.triprecord.domain.jouney.dto.request.*;
import toy.five.triprecord.domain.jouney.dto.response.JourneyCreateResponse;
import toy.five.triprecord.domain.jouney.dto.response.LodgmentJourneyUpdateResponse;
import toy.five.triprecord.domain.jouney.dto.response.MoveJourneyUpdateResponse;
import toy.five.triprecord.domain.jouney.dto.response.VisitJourneyUpdateResponse;
import toy.five.triprecord.domain.jouney.service.JourneyService;
import toy.five.triprecord.global.exception.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

import static toy.five.triprecord.domain.jouney.entity.JourneyType.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/journeys")
@RestController
public class JourneyController {

    private JourneyService journeyService;

    @Autowired
    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllJourneysByTrip(@RequestParam Long tripId) {

        return journeyService.getAllJourneysByTripId(tripId);

    }

    @PostMapping("/{tripId}")
    public ResponseEntity<ApiResponse> createJourney(
            @PathVariable Long tripId,
            @RequestBody @Valid JourneyCreateRequest request
    ) {
        return journeyService.saveJourneys(tripId, request);
    }

    @PutMapping("/move/{journeyId}")
    public ResponseEntity<ApiResponse> updateMoveJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid MoveJourneyUpdateRequest updateRequest
    ) {
        return journeyService.modifyMoveJourney(journeyId, updateRequest);
    }

    @PutMapping("/lodgment/{journeyId}")
    public ResponseEntity<ApiResponse> updateLodgmentJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid LodgmentJourneyUpdateRequest updateRequest
    ) {
        return journeyService.modifyLodgmentJourney(journeyId, updateRequest);
    }

    @PutMapping("/visit/{journeyId}")
    public ResponseEntity<ApiResponse> updateVisitJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid VisitJourneyUpdateRequest updateRequest
    ) {

        return journeyService.modifyVisitJourney(journeyId, updateRequest);
    }



//    @PostConstruct
    public void init() {
        MoveJourneyCreateRequest move =
                MoveJourneyCreateRequest.builder()
                        .name("이동11")
                        .vehicle("대중 교통")
                        .startPoint("서울")
                        .endPoint("대전")
                        .type(MOVE)
                        .startTime(LocalDateTime.now())
                        .build();

        LodgmentJourneyCreateRequest lodgment =
                LodgmentJourneyCreateRequest.builder()
                        .name("숙박11")
                        .dormitoryName("야놀자호텔")
                        .type(LODGMENT)
                        .startTime(LocalDateTime.now())
                        .build();

        VisitJourneyCreateRequest visit =
                VisitJourneyCreateRequest.builder()
                        .name("체류11")
                        .location("관악구")
                        .type(VISIT)
                        .startTime(LocalDateTime.of(2022, 10,10,10,10))
                        .build();

        JourneyCreateRequest journeyRequest = JourneyCreateRequest.builder()
                .moves(List.of(move))
                .lodgments(List.of(lodgment))
                .visits(List.of(visit))
                .build();

        journeyService.saveJourneys(2L, journeyRequest);

    }

}

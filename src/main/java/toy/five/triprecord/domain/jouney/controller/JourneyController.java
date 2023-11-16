package toy.five.triprecord.domain.jouney.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.five.triprecord.domain.jouney.dto.request.*;
import toy.five.triprecord.domain.jouney.dto.response.*;
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

    private final JourneyService journeyService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllJourneysByTrip(@RequestParam Long tripId) {

        List<JourneyDetailResponse> journeyDetailResponses = journeyService.getAllJourneysByTripId(tripId);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(journeyDetailResponses)
                        .build());
    }

    @PostMapping("/{tripId}")
    public ResponseEntity<ApiResponse> createJourney(
            @PathVariable Long tripId,
            @RequestBody @Valid JourneyCreateRequest request
    ) {
        JourneyCreateResponse journeyCreateResponse = journeyService.saveJourneys(tripId, request);

        return ResponseEntity.ok(
                ApiResponse.builder()
                    .status("Success")
                    .code(HttpStatus.OK.value())
                    .data(journeyCreateResponse)
                    .build());
    }

    @PutMapping("/move/{journeyId}")
    public ResponseEntity<ApiResponse> updateMoveJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid MoveJourneyUpdateRequest updateRequest
    ) {
        MoveJourneyUpdateResponse moveJourneyUpdateResponse = journeyService.modifyMoveJourney(journeyId, updateRequest);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(moveJourneyUpdateResponse)
                        .build());
    }

    @PutMapping("/lodgment/{journeyId}")
    public ResponseEntity<ApiResponse> updateLodgmentJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid LodgmentJourneyUpdateRequest updateRequest
    ) {
        LodgmentJourneyUpdateResponse lodgmentJourneyUpdateResponse = journeyService.modifyLodgmentJourney(journeyId, updateRequest);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(lodgmentJourneyUpdateResponse)
                        .build());
    }

    @PutMapping("/visit/{journeyId}")
    public ResponseEntity<ApiResponse> updateVisitJourney(
            @PathVariable Long journeyId,
            @RequestBody @Valid VisitJourneyUpdateRequest updateRequest
    ) {

        VisitJourneyUpdateResponse visitJourneyUpdateResponse = journeyService.modifyVisitJourney(journeyId, updateRequest);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(visitJourneyUpdateResponse)
                        .build());
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

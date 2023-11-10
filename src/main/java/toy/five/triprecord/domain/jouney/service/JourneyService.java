package toy.five.triprecord.domain.jouney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.jouney.dto.request.JourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.request.LodgmentJourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.request.MoveJourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.request.VisitJourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.response.*;
import toy.five.triprecord.domain.jouney.dto.request.LodgmentJourneyUpdateRequest;
import toy.five.triprecord.domain.jouney.dto.request.MoveJourneyUpdateRequest;
import toy.five.triprecord.domain.jouney.dto.request.VisitJourneyUpdateRequest;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.entity.VisitJourney;
import toy.five.triprecord.domain.jouney.repository.LodgmentJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.MoveJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.VisitJourneyRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.global.exception.ApiResponse;
import toy.five.triprecord.global.exception.BaseException;

import java.util.*;
import java.util.stream.Collectors;

import static toy.five.triprecord.global.exception.ErrorCode.JOURNEY_NO_EXIST;
import static toy.five.triprecord.global.exception.ErrorCode.TRIP_NO_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class JourneyService {

    private TripRepository tripRepository;
    private MoveJourneyRepository moveJourneyRepository;
    private VisitJourneyRepository visitJourneyRepository;
    private LodgmentJourneyRepository lodgmentJourneyRepository;

    @Autowired
    public JourneyService(TripRepository tripRepository,
                          MoveJourneyRepository moveJourneyRepository,
                          VisitJourneyRepository visitJourneyRepository,
                          LodgmentJourneyRepository lodgmentJourneyRepository) {
        this.tripRepository = tripRepository;
        this.moveJourneyRepository = moveJourneyRepository;
        this.visitJourneyRepository = visitJourneyRepository;
        this.lodgmentJourneyRepository = lodgmentJourneyRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAllJourneysByTripId(Long tripId) {

        List<JourneyDetailResponse> journeyResponses = new ArrayList<>();

        moveJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        lodgmentJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        visitJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);

        journeyResponses.sort(Comparator.comparing(JourneyDetailResponse::getStartTime));

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(journeyResponses)
                        .build());
    }

    private Trip findTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new BaseException(TRIP_NO_EXIST));
    }
    
    @Transactional
    public ResponseEntity<ApiResponse> saveJourneys(Long tripId, JourneyCreateRequest request) {

        List<MoveJourneyCreateRequest> moveJourneyDtos = request.getMoves();//이동
        List<LodgmentJourneyCreateRequest> lodgmentJourneyDtos = request.getLodgments();//숙박
        List<VisitJourneyCreateRequest> visitJourneyDtos = request.getVisits();//체류

        // 여기다 출발시간, 도착 시간 추가 필요
        List<MoveJourney> moveJourneys =
                moveJourneysToReponses(tripId, moveJourneyDtos);
        List<LodgmentJourney> lodgmentJourneys =
                lodgmentJourneysToResponses(tripId, lodgmentJourneyDtos);
        List<VisitJourney> visitJourneys = 
                visitJourneysToResponses(tripId, visitJourneyDtos);

        List<MoveJourney> savedMoveJourneys =
                moveJourneyRepository.saveAll(moveJourneys);
        List<LodgmentJourney> savedLodgmentJourneys =
                lodgmentJourneyRepository.saveAll(lodgmentJourneys);
        List<VisitJourney> savedVisitJourneys =
                visitJourneyRepository.saveAll(visitJourneys);

        List<MoveJourneyCreateResponse> moveJourneyCreateResponses =
                savedMoveJourneys.stream().map(MoveJourneyCreateResponse::fromEntity).toList();
        List<LodgmentJourneyCreateResponse> lodgmentJourneyCreateResponses =
                savedLodgmentJourneys.stream().map(LodgmentJourneyCreateResponse::fromEntity).toList();
        List<VisitJourneyCreateResponse> visitJourneyCreateResponses =
                savedVisitJourneys.stream().map(VisitJourneyCreateResponse::fromEntity).toList();

        JourneyCreateResponse journeyCreateResponse = JourneyCreateResponse.of(
                moveJourneyCreateResponses,
                visitJourneyCreateResponses,
                lodgmentJourneyCreateResponses
        );

        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(journeyCreateResponse).build());


    }

    @Transactional
    public ResponseEntity<ApiResponse> modifyMoveJourney (
            Long journeyId,
            MoveJourneyUpdateRequest updateRequest
    ){
        MoveJourney findJourney = moveJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        findJourney.setName(updateRequest.getName());
        findJourney.setVehicle(updateRequest.getVehicle());
        findJourney.setStartPoint(updateRequest.getStartPoint());
        findJourney.setEndPoint(updateRequest.getEndPoint());
        findJourney.setStartTime(updateRequest.getStartTime());
        findJourney.setEndTime(updateRequest.getEndTime());

        MoveJourneyUpdateResponse moveJourneyUpdateResponse = MoveJourneyUpdateResponse.fromEntity(findJourney);

        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(moveJourneyUpdateResponse).build());

    }

    @Transactional
    public ResponseEntity<ApiResponse> modifyLodgmentJourney (
            Long journeyId,
            LodgmentJourneyUpdateRequest updateRequest
    ){
        LodgmentJourney findJourney = lodgmentJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        findJourney.setName(updateRequest.getName());
        findJourney.setDormitoryName(updateRequest.getDormitoryName());
        findJourney.setStartTime(updateRequest.getStartTime());
        findJourney.setEndTime(updateRequest.getEndTime());

        LodgmentJourneyUpdateResponse lodgmentJourneyUpdateResponse = LodgmentJourneyUpdateResponse.fromEntity(findJourney);

        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(lodgmentJourneyUpdateResponse).build());

    }

    @Transactional
    public ResponseEntity<ApiResponse> modifyVisitJourney (
            Long journeyId,
            VisitJourneyUpdateRequest updateRequest
    ){

        log.info("Called : JousrenyService.modifyVisitJourney");
        VisitJourney findJourney = visitJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        findJourney.setName(updateRequest.getName());
        findJourney.setLocation(updateRequest.getLocation());
        findJourney.setStartTime(updateRequest.getStartTime());
        findJourney.setEndTime(updateRequest.getEndTime());

        VisitJourneyUpdateResponse visitJourneyUpdateResponse = VisitJourneyUpdateResponse.fromEntity(findJourney);

        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(visitJourneyUpdateResponse).build());

    }

    private List<VisitJourney> visitJourneysToResponses(Long tripId, List<VisitJourneyCreateRequest> visitJourneyDtos) {
        List<VisitJourney> visitJourneys =
                Optional.ofNullable(visitJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                        VisitJourney.builder()
                                .trip(findTripById(tripId))
                                .name(journeyRequest.getName())
                                .location(journeyRequest.getLocation())
                                .type(journeyRequest.getType())
                                .startTime(journeyRequest.getStartTime())
                                .endTime(journeyRequest.getEndTime())
                                .build()
                        ).collect(Collectors.toList());
        return visitJourneys;
    }

    private List<LodgmentJourney> lodgmentJourneysToResponses(Long tripId, List<LodgmentJourneyCreateRequest> lodgmentJourneyDtos) {
        List<LodgmentJourney> lodgmentJourneys =
                Optional.ofNullable(lodgmentJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                        LodgmentJourney.builder()
                                .trip(findTripById(tripId))
                                .name(journeyRequest.getName())
                                .dormitoryName(journeyRequest.getDormitoryName())
                                .type(journeyRequest.getType())
                                .startTime(journeyRequest.getStartTime())
                                .endTime(journeyRequest.getEndTime())
                                .build()
                        ).collect(Collectors.toList());
        return lodgmentJourneys;
    }

    private List<MoveJourney> moveJourneysToReponses(Long tripId, List<MoveJourneyCreateRequest> moveJourneyDtos) {
        List<MoveJourney> moveJourneys =
                Optional.ofNullable(moveJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                                MoveJourney.builder()
                                    .trip(findTripById(tripId))
                                    .name(journeyRequest.getName())
                                    .vehicle(journeyRequest.getVehicle())
                                    .startPoint(journeyRequest.getStartPoint())
                                    .endPoint(journeyRequest.getEndPoint())
                                    .type(journeyRequest.getType())
                                    .startTime(journeyRequest.getStartTime())
                                    .endTime(journeyRequest.getEndTime())
                                    .build()
                        ).collect(Collectors.toList());
        return moveJourneys;
    }

}

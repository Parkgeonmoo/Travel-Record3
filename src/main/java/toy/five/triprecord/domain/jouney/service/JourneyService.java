package toy.five.triprecord.domain.jouney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import toy.five.triprecord.domain.jouney.dto.request.*;
import toy.five.triprecord.domain.jouney.dto.response.*;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.entity.VisitJourney;
import toy.five.triprecord.domain.jouney.repository.LodgmentJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.MoveJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.VisitJourneyRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

import java.util.*;
import java.util.stream.Collectors;

import static toy.five.triprecord.global.exception.ErrorCode.JOURNEY_NO_EXIST;
import static toy.five.triprecord.global.exception.ErrorCode.TRIP_NO_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class JourneyService {
    private static final String API_URL = "https://worker-polished-lab-7314.wwscan3.workers.dev/";

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
    public List<JourneyDetailResponse> getAllJourneysByTripId(Long tripId) {

        List<JourneyDetailResponse> journeyResponses = new ArrayList<>();

        moveJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        lodgmentJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        visitJourneyRepository.findAllByTripId(tripId).stream()
                .map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);

        journeyResponses.sort(Comparator.comparing(JourneyDetailResponse::getStartTime));

        return journeyResponses;
    }

    private Optional<Trip> findTripById(Long tripId) {
        return tripRepository.findById(tripId);
    }
    
    @Transactional
    public JourneyCreateResponse saveJourneys(Long tripId, JourneyCreateRequest request) {

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

        return JourneyCreateResponse.of(
                moveJourneyCreateResponses,
                visitJourneyCreateResponses,
                lodgmentJourneyCreateResponses
        );


    }

    @Transactional
    public MoveJourneyUpdateResponse modifyMoveJourney (
            Long journeyId,
            MoveJourneyUpdateRequest updateRequest
    ){
        MoveJourney findJourney = moveJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        LocationRequest updateStartLocation = searchLocation(updateRequest.getStartPoint());
        LocationRequest updateEndPointLocation = searchLocation(updateRequest.getEndPoint());
        findJourney.setUpdateColumns(updateRequest);
        findJourney.setUpdateEndPointLocation(updateEndPointLocation);
        findJourney.setUpdateStartLocation(updateStartLocation);

        return MoveJourneyUpdateResponse.fromEntity(findJourney);

    }

    @Transactional
    public LodgmentJourneyUpdateResponse modifyLodgmentJourney (
            Long journeyId,
            LodgmentJourneyUpdateRequest updateRequest
    ){
        LodgmentJourney findJourney = lodgmentJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        LocationRequest updateLodgeLocation = searchLocation(updateRequest.getDormitoryName());
        findJourney.setUpdateColumns(updateRequest);
        findJourney.setUpdateLodgeLocation(updateLodgeLocation);

        return LodgmentJourneyUpdateResponse.fromEntity(findJourney);

    }

    @Transactional
    public VisitJourneyUpdateResponse modifyVisitJourney (
            Long journeyId,
            VisitJourneyUpdateRequest updateRequest
    ){

        log.info("Called : JousrenyService.modifyVisitJourney");
        VisitJourney findJourney = visitJourneyRepository.findById(journeyId)
                .orElseThrow(() -> new BaseException(JOURNEY_NO_EXIST));

        LocationRequest updateVisitLocation = searchLocation(updateRequest.getLocation());
        findJourney.setUpdateColumns(updateRequest);
        findJourney.setUpdateVisitLocation(updateVisitLocation);

        return VisitJourneyUpdateResponse.fromEntity(findJourney);

    }

    private List<VisitJourney> visitJourneysToResponses(Long tripId, List<VisitJourneyCreateRequest> visitJourneyDtos) {
        List<VisitJourney> visitJourneys =
                Optional.ofNullable(visitJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                        VisitJourney.builder()
                                .trip(findTripById(tripId)
                                        .orElseThrow(() -> new BaseException(TRIP_NO_EXIST)))
                                .name(journeyRequest.getName())
                                .location(journeyRequest.getLocation())
                                .type(journeyRequest.getType())
                                .startTime(journeyRequest.getStartTime())
                                .endTime(journeyRequest.getEndTime())
                                .build()
                        ).collect(Collectors.toList());

        visitJourneys.forEach(visitJourney -> {
            LocationRequest updateVisitLocation = searchLocation(visitJourney.getLocation());
            visitJourney.setUpdateVisitLocation(updateVisitLocation);
        });
        return visitJourneys;
    }

    private List<LodgmentJourney> lodgmentJourneysToResponses(Long tripId, List<LodgmentJourneyCreateRequest> lodgmentJourneyDtos) {
        List<LodgmentJourney> lodgmentJourneys =
                Optional.ofNullable(lodgmentJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                        LodgmentJourney.builder()
                                .trip(findTripById(tripId)
                                        .orElseThrow(() -> new BaseException(TRIP_NO_EXIST)))
                                .name(journeyRequest.getName())
                                .dormitoryName(journeyRequest.getDormitoryName())
                                .type(journeyRequest.getType())
                                .startTime(journeyRequest.getStartTime())
                                .endTime(journeyRequest.getEndTime())
                                .build()
                        ).collect(Collectors.toList());

        lodgmentJourneys.forEach(lodgmentJourney -> {
            LocationRequest updateLodgementLocation = searchLocation(lodgmentJourney.getDormitoryName());
            lodgmentJourney.setUpdateLodgeLocation(updateLodgementLocation);
        });
        return lodgmentJourneys;
    }

    private List<MoveJourney> moveJourneysToReponses(Long tripId, List<MoveJourneyCreateRequest> moveJourneyDtos) {
        List<MoveJourney> moveJourneys =
                Optional.ofNullable(moveJourneyDtos)
                        .orElseGet(Collections::emptyList)
                        .stream().map(journeyRequest ->
                                MoveJourney.builder()
                                    .trip(findTripById(tripId)
                                            .orElseThrow(() -> new BaseException(TRIP_NO_EXIST)))
                                    .name(journeyRequest.getName())
                                    .vehicle(journeyRequest.getVehicle())
                                    .startPoint(journeyRequest.getStartPoint())
                                    .endPoint(journeyRequest.getEndPoint())
                                    .type(journeyRequest.getType())
                                    .startTime(journeyRequest.getStartTime())
                                    .endTime(journeyRequest.getEndTime())
                                    .build()
                        ).collect(Collectors.toList());

        moveJourneys.forEach(moveJourney -> {
            LocationRequest updateStartLocation = searchLocation(moveJourney.getStartPoint());
            LocationRequest updateEndPointLocation = searchLocation(moveJourney.getEndPoint());
            moveJourney.setUpdateStartLocation(updateStartLocation);
            moveJourney.setUpdateEndPointLocation(updateEndPointLocation);
        });
        return moveJourneys;
    }

    private LocationRequest searchLocation(String query) {
        RestTemplate restTemplate = new RestTemplate();

        String body = restTemplate.getForEntity(
            API_URL + "?query=" + query,
            String.class
        ).getBody();

        if (body == null) {
            throw new BaseException(ErrorCode.NO_RESULT_SEARCH_LOCATION);
        }

        JSONObject jsonObject = new JSONObject(body);
        JSONArray documents = jsonObject.getJSONArray("documents");

        if (documents.isEmpty()) {
            throw new BaseException(ErrorCode.NO_RESULT_SEARCH_LOCATION);
        }

        JSONObject document = documents.getJSONObject(0);
        return LocationRequest.builder()
            .placeName(document.getString("place_name"))
            .addressName(document.getString("address_name"))
            .roadAddressName(document.getString("road_address_name"))
            .categoryName(document.getString("category_name"))
            .x(document.getString("x"))
            .y(document.getString("y"))
            .build();
    }
}

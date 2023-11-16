package toy.five.triprecord.domain.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.jouney.dto.response.JourneyDetailResponse;
import toy.five.triprecord.domain.trip.dto.response.TripDetailResponse;
import toy.five.triprecord.domain.trip.dto.request.TripCreateRequest;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.domain.trip.dto.request.TripUpdateRequest;
import toy.five.triprecord.domain.trip.dto.response.TripCreateResponse;
import toy.five.triprecord.domain.trip.dto.response.TripPatchResponse;
import toy.five.triprecord.domain.trip.dto.response.TripUpdateResponse;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.validation.patch.TripPatchTimeValidatorUtils;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

import java.util.*;

import static toy.five.triprecord.global.exception.ErrorCode.TRIP_NO_EXIST;
import static toy.five.triprecord.global.exception.ErrorCode.USER_NO_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final TripPatchTimeValidatorUtils tripPatchTimeValidatorUtils;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TripDetailResponse getTripById(Long tripId) {

        Trip findTrip = findTripById(tripId);
        List<JourneyDetailResponse> journeyResponses = getJourneysFromTripBySorted(findTrip);

        return TripDetailResponse.fromEntity(findTrip, journeyResponses);
    }

    @Transactional(readOnly = true)
    public List<TripDetailResponse> getAllTripsPaging(Pageable pageable) {
        return tripRepository.findAll(pageable).stream()
                .map(
                        trip -> TripDetailResponse.fromEntity(trip, getJourneysFromTripBySorted(trip))
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<TripDetailResponse> getMyAllTripsPaging(Pageable pageable, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new BaseException(USER_NO_EXIST));
        return tripRepository.findAllByUser(user, pageable).stream()
                .map(
                        trip -> TripDetailResponse.fromEntity(trip, getJourneysFromTripBySorted(trip))
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<TripDetailResponse> getAllTripsBySearchCond(TripSearchCond cond) {
        return tripRepository.findAllBySearchCond(cond).stream()
                .map(
                        trip -> TripDetailResponse.fromEntity(trip, getJourneysFromTripBySorted(trip))
                ).toList();
    }

    @Transactional
    public TripCreateResponse createTrip(TripCreateRequest tripCreateRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new BaseException(USER_NO_EXIST));

        Trip newTrip = Trip.builder()
                .user(user)
                .name(tripCreateRequest.getName())
                .startTime(tripCreateRequest.getStartTime())
                .endTime(tripCreateRequest.getEndTime())
                .domestic(tripCreateRequest.getDomestic())
                .build();

        return TripCreateResponse.fromEntity(tripRepository.save(newTrip));
    }

    @Transactional
    public TripUpdateResponse updateTrip(Long tripId,TripUpdateRequest tripUpdateRequest) {

        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BaseException(ErrorCode.TRIP_NO_EXIST));


        existingTrip.updateAllColumns(tripUpdateRequest);

        return TripUpdateResponse.fromEntity(existingTrip);

    }

    @Transactional
    public TripPatchResponse patchTrip(Long tripId, TripPatchRequest tripPatchRequest) {

        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BaseException(ErrorCode.TRIP_NO_EXIST));

        TripPatchRequest updateRequest = TripPatchRequest.builder()
                .name(existingTrip.getName())
                .startTime(existingTrip.getStartTime())
                .endTime(existingTrip.getEndTime())
                .domestic(existingTrip.getDomestic())
                .build();

        if (tripPatchRequest.getStartTime() == null || tripPatchRequest.getEndTime() == null) {
            tripPatchTimeValidatorUtils.startTimeCheckFromPatchRequest(tripPatchRequest,updateRequest.getEndTime());
            tripPatchTimeValidatorUtils.endTimeCheckFromPatchRequest(tripPatchRequest,updateRequest.getStartTime());
        }


        updateRequest.PatchFromTripPatchRequest(tripPatchRequest);
        existingTrip.updateColumns(updateRequest);

        return TripPatchResponse.fromEntity(existingTrip);

    }

    private List<JourneyDetailResponse> getJourneysFromTripBySorted(Trip findTrip) {
        List<JourneyDetailResponse> journeyResponses = new ArrayList<>();

        Optional.ofNullable(findTrip.getMoveJourneys()).orElseGet(Collections::emptyList)
                .stream().map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        Optional.ofNullable(findTrip.getLodgmentJourneys()).orElseGet(Collections::emptyList)
                .stream().map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);
        Optional.ofNullable(findTrip.getVisitJourneys()).orElseGet(Collections::emptyList)
                .stream().map(JourneyDetailResponse::fromEntity).forEach(journeyResponses::add);

        journeyResponses.sort(Comparator.comparing(JourneyDetailResponse::getStartTime));

        return journeyResponses;
    }

    private Trip findTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new BaseException(TRIP_NO_EXIST));
    }
}


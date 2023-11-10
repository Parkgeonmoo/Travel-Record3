package toy.five.triprecord.domain.trip.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.five.triprecord.domain.trip.dto.response.TripDetailResponse;
import toy.five.triprecord.domain.trip.dto.request.TripCreateRequest;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.domain.trip.dto.request.TripUpdateRequest;
import toy.five.triprecord.domain.trip.dto.response.TripCreateResponse;
import toy.five.triprecord.domain.trip.dto.response.TripPatchResponse;
import toy.five.triprecord.domain.trip.dto.response.TripUpdateResponse;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.service.TripService;
import toy.five.triprecord.global.exception.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse> getTrip(@PathVariable final Long tripId) {

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(tripService.getTripById(tripId))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTrips(
            @PageableDefault(size=5, sort = "startTime", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("Success")
                        .code(HttpStatus.OK.value())
                        .data(tripService.getAllTripsPaging(pageable))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTrip(@Valid @RequestBody TripCreateRequest tripCreateRequest) {
        TripCreateResponse savedTrip = tripService.createTrip(tripCreateRequest);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(savedTrip).build());
    }


    @PutMapping("/{tripId}")
    public ResponseEntity<ApiResponse> updateTrip(@NotNull @PathVariable Long tripId, @Valid @RequestBody TripUpdateRequest tripUpdateRequest) {
        TripUpdateResponse savedTrip = tripService.updateTrip(tripId,tripUpdateRequest);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(savedTrip).build());

    }

    @PatchMapping("/{tripId}")
    public ResponseEntity<ApiResponse> PatchTrip(@NotNull @PathVariable Long tripId, @Valid @RequestBody TripPatchRequest tripPatchRequest) {
        TripPatchResponse savedTrip = tripService.patchTrip(tripId,tripPatchRequest);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").code(HttpStatus.OK.value()).data(savedTrip).build());
    }

//    @PostConstruct
    public void init() {
        tripService.createTrip(
                TripCreateRequest.builder()
                        .name("여행1")
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now())
                        .domestic(Domestic.ABROAD)
                        .build()
        );

        tripService.createTrip(
                TripCreateRequest.builder()
                        .name("여행2")
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now())
                        .domestic(Domestic.DOMESTIC)
                        .build()
        );
    }

}
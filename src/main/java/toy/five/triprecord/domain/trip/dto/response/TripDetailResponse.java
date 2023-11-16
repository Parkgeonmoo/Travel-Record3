package toy.five.triprecord.domain.trip.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.jouney.dto.response.JourneyDetailResponse;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.entity.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripDetailResponse {

    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private Domestic domestic;
    private List<JourneyDetailResponse> journeys;
    private Long wishCount;

    public static TripDetailResponse fromEntity(
            Trip trip,
            List<JourneyDetailResponse> journeyResponses
    ) {
        return TripDetailResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startTime(trip.getStartTime())
                .endTime(trip.getEndTime())
                .domestic(trip.getDomestic())
                .journeys(Optional.ofNullable(journeyResponses).orElseGet(Collections::emptyList))
                .wishCount(trip.getWishCount())
                .build();
    }
}

package toy.five.triprecord.domain.jouney.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.jouney.entity.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyDetailResponse {

    private Long tripId;
    private String name;
    private String dormitoryName;
    private JourneyType type;
    private String location;
    private String vehicle;
    private String startPoint;
    private String endPoint;
    private Location visitLocation;
    private Location lodgementLocation;
    private Location startLocation;
    private Location endPointLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static JourneyDetailResponse fromEntity(MoveJourney entity) {
        return JourneyDetailResponse.builder()
                .tripId(entity.getTrip().getId())
                .name(entity.getName())
                .vehicle(entity.getVehicle())
                .startPoint(entity.getStartPoint())
                .endPoint(entity.getEndPoint())
                .type(entity.getType())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .startLocation(entity.getStartLocation())
                .endPointLocation(entity.getEndPointLocation())
                .build();
    }

    public static JourneyDetailResponse fromEntity(LodgmentJourney entity) {
        return JourneyDetailResponse.builder()
                .tripId(entity.getTrip().getId())
                .name(entity.getName())
                .dormitoryName(entity.getDormitoryName())
                .type(entity.getType())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .lodgementLocation(entity.getLodgmentLocation())
                .build();
    }

    public static JourneyDetailResponse fromEntity(VisitJourney entity) {
        return JourneyDetailResponse.builder()
                .tripId(entity.getTrip().getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .type(entity.getType())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .visitLocation(entity.getVisitLocation())
                .build();
    }

}

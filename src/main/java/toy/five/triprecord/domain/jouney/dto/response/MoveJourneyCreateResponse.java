package toy.five.triprecord.domain.jouney.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import toy.five.triprecord.domain.jouney.entity.JourneyType;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MoveJourneyCreateResponse {

    private Long tripId;
    private String name;
    private String vehicle;
    private String startPoint;
    private String endPoint;
    private JourneyType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public static MoveJourneyCreateResponse fromEntity(MoveJourney entity) {
        return MoveJourneyCreateResponse.builder()
                .tripId(entity.getTrip().getId())
                .name(entity.getName())
                .vehicle(entity.getVehicle())
                .startPoint(entity.getStartPoint())
                .endPoint(entity.getEndPoint())
                .type(entity.getType())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    @Override
    public String toString() {
        return "MoveJourneyCreateResponse{" +
                "tripId=" + tripId +
                ", name='" + name + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

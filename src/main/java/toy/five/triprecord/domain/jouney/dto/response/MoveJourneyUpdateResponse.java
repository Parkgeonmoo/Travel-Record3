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
public class MoveJourneyUpdateResponse {

    private Long tripId;
    private String name;
    private String vehicle;
    private String startPoint;
    private String endPoint;
    private JourneyType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public static MoveJourneyUpdateResponse fromEntity(MoveJourney entity) {
        return MoveJourneyUpdateResponse.builder()
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
}

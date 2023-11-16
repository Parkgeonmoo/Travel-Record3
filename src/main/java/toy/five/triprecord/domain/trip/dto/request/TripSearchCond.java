package toy.five.triprecord.domain.trip.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.trip.entity.Domestic;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripSearchCond {

    private String tripName;
    private LocalDateTime minStartTime;
    private LocalDateTime maxEndTime;
    private Domestic domestic;
    private Long minWishCount;
}
